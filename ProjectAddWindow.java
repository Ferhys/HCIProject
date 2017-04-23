package projectNav;

import java.time.LocalDate;

import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import model.Project;
import scrumProject.project_CAT_ATTACK.User;

public class ProjectAddWindow extends Window {

	Project p;
	final TextField projectName;
	final TextField description;
	final DateField startDate;
	
	public ProjectAddWindow() {
		VerticalLayout mainVL = new VerticalLayout();
		projectName = new TextField("Project Name: ");
		projectName.focus();
		startDate = new DateField();
		startDate.setValue(LocalDate.now());
		description = new TextField("Project Description: ");
		
		p = new Project();
		Button enter = new Button("Add Project");
		enter.addClickListener(e-> {
			p.setName(projectName.getValue());
			p.setStartDate(startDate.getValue());
			p.setDescription(description.getValue());
			
			close();
		});
		
		mainVL.addComponents(projectName, startDate, description, enter);
		setContent(mainVL);
	}
	
	public Project getProject() {
		return p;
	}
	
	public void reset() {
		p.setName(null);
		projectName.setValue("");
		description.setValue("");
		startDate.setValue(LocalDate.now());

	}
	
}
