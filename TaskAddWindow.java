package projectNav;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.converter.StringToDoubleConverter;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import model.Task;

public class TaskAddWindow extends Window{

	final TextField taskName;
	final TextField plannedHours;
	final TextField completedHours;
	final ComboBox<String> combo;
	final ComboBox<String> statCombo;
	
	ArrayList<String> storyNames;
	public boolean complete;
	int index;
	
	public TaskAddWindow(ArrayList<String> storyNames) {
		this.storyNames = storyNames;
		
		complete = false;
		
		VerticalLayout mainVL = new VerticalLayout();
		
		taskName = new TextField("Task Name: ");
		taskName.focus();
		plannedHours = new TextField("Planned Hours: ");
		completedHours = new TextField("Completed Hours: ");
		statCombo = new ComboBox<String>("Status");
		List<String> statList = new ArrayList<String>();
		statList.add("TO DO");
		statList.add("DOING");
		statList.add("DONE");
		
		statCombo.setItems(statList);
	
		combo = new ComboBox<String>("Parent Story: ");
		combo.setItems(storyNames);

		Button enter = new Button("Add Task");
		
		enter.addClickListener(e-> {
			if (taskName.getValue().length() < 1 ){
				Notification.show("Please include task name");
			}
			else if (combo.getValue() == null) {
				Notification.show("Please choose Story");
			}
			else if (statCombo.getValue() == null) {
				Notification.show("Please choose a status");
			}
			else {
				index = storyNames.indexOf(combo.getValue());
				
				complete = true;
				close();
			}
		});
		
		setHeight(taskName.getHeight() * 6.5 + "px");
		setWidth(taskName.getWidth() *1.2 + "px");
		
		mainVL.addComponents(taskName, plannedHours, completedHours, combo, statCombo, enter);
		setContent(mainVL);
	}
	
	public Task getTask(){
		Task t = new Task();
		
		Binder<Task> binder = new Binder<>();
		binder.bind(taskName, Task::getName, Task::setName);
		binder.bind(statCombo, Task::getStatus, Task::setStatus);
		binder.forField(plannedHours).withConverter(new StringToDoubleConverter("Must Enter a number")).bind(Task::getPlannedHours, Task::setPlannedHours);
		binder.forField(completedHours).withConverter(new StringToDoubleConverter("Must Enter a number")).bind(Task::getCompletedHours, Task::setCompletedHours);
		
		try {
			binder.writeBean(t);
		}
		catch (ValidationException e) {
			Notification.show("ERROR WRITING Task");
		}
		
		return t;
	}
	
	public int returnIndex(){
		return index;
	}
	
	public void reset() {
		taskName.setValue("");
		plannedHours.setValue("0");
		completedHours.setValue("0");
		combo.setValue(storyNames.get(storyNames.size()-1));
		complete = false;
	}

	public void updateStories(ArrayList<String> projectsNames ) {
		this.storyNames = projectsNames;
		combo.setValue(storyNames.get(storyNames.size()-1));
	}
}
