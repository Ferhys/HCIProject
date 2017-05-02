package kanban;

import java.time.LocalDate;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import model.Story;

@SuppressWarnings("serial")
public class StoryAddWindow extends Window {
	
	final TextField storyName;
	final TextField description;
	final DateField startDate;
	final DateField endDate;
	public boolean complete;
	
	public StoryAddWindow() {
		//Add a new story to the label. 
    	VerticalLayout main = new VerticalLayout();
    	HorizontalLayout btnContent = new HorizontalLayout();
    	main.setStyleName("v-align-center");
    	btnContent.setStyleName("v-align-center");
        storyName = new TextField("Story Name");
        storyName.focus();
        description = new TextField("Story description: ");
        startDate = new DateField("Start Date");
        startDate.setValue(LocalDate.now());
        endDate = new DateField("End Date");
        endDate.setValue(LocalDate.now());
        
        storyName.focus();
        complete = false;
        Button addBtn = new Button("Add");
    
        addBtn.addClickListener(e -> {
        
    			if (storyName.getValue().length() < 1 ){
    				Notification.show("Please include story name");
    			}
    			else if (endDate.getValue() == null) {
    				Notification.show("Please set end date");
    			}
    			else if (endDate.getValue().isBefore(startDate.getValue())) {
    				Notification.show("Please choose an end date that is later than the start date.");
    			}
    			else{
    				complete = true;
    				close();
    			}
    	
        });
        
        Button cancelBtn = new Button("Cancel");
        cancelBtn.addClickListener(e -> {
        	close();
        });
        
        
        // Put some components in it
        btnContent.addComponents(addBtn, cancelBtn);
        main.addComponents(new Label("New Story"), storyName, startDate,endDate, description, btnContent);
        setContent(main);
        this.setModal(true);
	}
	
	public Story getStory(){
		Story st = new Story();
		
		Binder<Story> binder = new Binder<>();
		binder.bind(storyName, Story::getName, Story::setName);
		binder.bind(description, Story::getDescription, Story::setDescription);
		binder.bind(startDate, Story::getStartDate, Story::setStartDate);
		binder.bind(endDate, Story::getEndDate, Story::setEndDate);
		
		try{
			binder.writeBean(st);
		}
		catch(ValidationException e){
			Notification.show("ERROR WRITING STORY");
		}
		return st;
	}
	
	public void reset(){
		storyName.setValue("");
		storyName.focus();
		description.setValue("");
		startDate.setValue(LocalDate.now());
		endDate.setValue(LocalDate.now());
		complete = false;
	}
	
	
}
