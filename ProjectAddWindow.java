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
	public boolean complete;
	
	public ProjectAddWindow() {
		VerticalLayout mainVL = new VerticalLayout();
		projectName = new TextField("Project Name: ");
		projectName.focus();
		startDate = new DateField("Start Date: ");
		startDate.setValue(LocalDate.now());
		description = new TextField("Project Description: ");
		complete = false;
		
		Button enter = new Button("Add Project");
		
		enter.addClickListener(e-> {
			
			if (projectName.getValue().length() < 1) {
				Notification.show("Please include project name");
			}
			else {
				complete = true;
				close();
			}

		});
		
		mainVL.addComponents(projectName, startDate, description, enter);
		setContent(mainVL);
		setWidth(projectName.getWidth() * 1.3 + "px");
		setHeight(projectName.getHeight() * 4.5 + "px");

		this.setModal(true);
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
