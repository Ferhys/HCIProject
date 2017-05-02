package projectNav;

import java.time.LocalDate;
import java.util.ArrayList;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import model.Sprint;

public class SprintAddWindow extends Window{
	
	final TextField sprintName;
	final TextField description;
	final DateField startDate;
	final DateField endDate;
	final ComboBox<String> combo;
	ArrayList<String> projectNames;
	public boolean complete;
	int index;
	
	public SprintAddWindow(ArrayList<String> projectsNames) {
		this.projectNames = projectsNames;
		complete = false;
		
		VerticalLayout mainVL = new VerticalLayout();
		
		sprintName = new TextField("Sprint Name: ");
		sprintName.focus();
		startDate = new DateField("Start Date: ");
		endDate = new DateField("End Date: ");
		description = new TextField("Sprint description: ");
		startDate.setValue(LocalDate.now());
		endDate.setValue(LocalDate.now());
		combo = new ComboBox<String>("Parent Project: ");
		combo.setItems(projectNames);

		Button enter = new Button("Add Sprint");
		
		enter.addClickListener(e-> {
			if (sprintName.getValue().length() < 1 ){
				Notification.show("Please include sprint name");
			}
			else if (endDate.getValue() == null) {
				Notification.show("Please set end date");
			}
			else if (endDate.getValue().isBefore(startDate.getValue())) {
				Notification.show("Please choose an end date that is later than the start date.");
			}
			else if (combo.getValue() == null) {
				Notification.show("Please choose Project");
			}
			else {
				index = projectNames.indexOf(combo.getValue());
				complete = true;
				close();
			}
		});
		
		setHeight(sprintName.getHeight() * 6.5 + "px");
		setWidth(sprintName.getWidth() *1.2 + "px");
		
		mainVL.addComponents(sprintName, startDate, endDate, combo, description, enter);
		setContent(mainVL);
	}
	
	public Sprint getSprint(){
		Sprint s = new Sprint();
		
		Binder<Sprint> binder = new Binder<>();
		binder.bind(sprintName, Sprint::getName, Sprint::setName);
		binder.bind(description, Sprint::getDescription, Sprint::setDescription);
		binder.bind(startDate, Sprint::getStartDate, Sprint::setStartDate);
		binder.bind(endDate, Sprint::getEndDate, Sprint::setEndDate);
		
		try {
			binder.writeBean(s);
		}
		catch (ValidationException e) {
			Notification.show("ERROR WRITING SPRINT");
		}
		
		return s;
	}
	
	public int returnProjectIndex(){
		return index;
	}
	
	public void reset() {
		sprintName.setValue("");
		description.setValue("");
		startDate.setValue(LocalDate.now());
		endDate.setValue(LocalDate.now());
		combo.setValue(projectNames.get(projectNames.size()-1));
		complete = false;
	}

	public void updateProjects(ArrayList<String> projectsNames ) {
		this.projectNames = projectsNames;
		combo.setValue(projectNames.get(projectNames.size()-1));
	}
}
