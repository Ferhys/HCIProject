package projectNav;


import java.time.LocalDate;
import java.util.ArrayList;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
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
	final ArrayList<String> projectNames;
	int index;
	
	public SprintAddWindow(ArrayList<String> projectNames) {
		this.projectNames = projectNames;
		
		VerticalLayout mainVL = new VerticalLayout();
		
		sprintName = new TextField("Sprint Name: ");
		startDate = new DateField("Start Date: ");
		endDate = new DateField("End Date: ");
		description = new TextField("Sprint description: ");
		startDate.setValue(LocalDate.now());
		combo = new ComboBox<String>("Parent Project: ");
		combo.setItems(projectNames);
		
		Button enter = new Button("Add Sprint");
		
		enter.addClickListener(e-> {
			index = projectNames.indexOf(combo.getValue());
			close();
		});
		
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
	}

}