package projectNav;

import java.time.LocalDate;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import model.Project;

public class ProjectAddWindow extends Window {

	//Project p;
	final TextField projectName;
	final TextField description;
	final DateField startDate;
	final Label nameStatus;
	public boolean complete;
	
	public ProjectAddWindow() {
		VerticalLayout mainVL = new VerticalLayout();
		projectName = new TextField("Project Name: ");
		projectName.focus();
		startDate = new DateField();
		startDate.setValue(LocalDate.now());
		description = new TextField("Project Description: ");
		nameStatus = new Label();
		complete = false;
		
		Button enter = new Button("Add Project");
		
		enter.addClickListener(e-> {
			Binder<Project> binder = new Binder<>();
			
			binder.forField(projectName).withValidator(
					projectName -> projectName.length() >= 1, "project name can't be blank")
					.withValidationStatusHandler(status -> {
						nameStatus.setValue(status.getMessage().orElse(""));
						nameStatus.setVisible(status.isError());
					});
			
			if (projectName.getValue().length() < 1) {
				Notification.show("Please include project name");
			}
			else {
				complete = true;
				close();
			}

		});
		
		mainVL.addComponents(projectName, startDate, description, enter, nameStatus);
		setContent(mainVL);
		setWidth(projectName.getWidth() * 1.3 + "px");
		setHeight(Page.getCurrent().getBrowserWindowHeight() * 0.5 + "px");

	}
	
	public Project getProject() {
		Project p = new Project();

		Binder<Project> binder = new Binder<>();
		
		binder.bind(projectName, Project::getName, Project::setName);
		binder.bind(startDate, Project::getStartDate, Project::setStartDate);
		binder.bind(description, Project::getDescription, Project::setDescription);
		try {
				binder.writeBean(p);
		}
		catch (ValidationException e) {
			Notification.show("ERROR OR SOMETHING");
		}
		
		return p;
	}
	
	public void reset() { 
		projectName.setValue("");
		description.setValue("");
		startDate.setValue(LocalDate.now());
		complete = false;
	}

}
