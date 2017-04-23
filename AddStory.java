package kanban;

import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class AddStory {
	
	
	static Window newStory(GridLayout GL) {
		//Add a new story to the label. 
     //	Story st = new Story();
     	Panel panel = new Panel();
     	panel.addClickListener(e->{
     		//If you click on the story, should cause the storie's tasks to show. 
     		
     	});
     	panel.setWidth("300px");
     	
      	Window sW = new Window();
    	VerticalLayout subContent = new VerticalLayout();
    	HorizontalLayout btnContent = new HorizontalLayout();
    	subContent.setStyleName("v-align-center");
    	btnContent.setStyleName("v-align-center");
        sW.setContent(subContent);
        sW.setHeight("200px");
        sW.setWidth("400px");
        TextField nameInput = new TextField("Enter Story Name");
        nameInput.focus();
        Button addBtn = new Button("Add");
        panel.setStyleName("valo-animate-in-fade");
        addBtn.addClickListener(e -> {
        	//st.setName(nameInput.getValue());
        	panel.setStyleName("v-align-center");
        	Label lbl = new Label(nameInput.getValue());
        	HorizontalLayout stLayout = new HorizontalLayout();
        	Button addTask = new Button("+");
        	addTask.setHeight("25px");
        	stLayout.addComponents(addTask, lbl);
        	panel.setContent(stLayout);
        	GL.addComponent(panel,1,1);
        	sW.close();
        });
        Button cancelBtn = new Button("Cancel");
        cancelBtn.addClickListener(e -> {
        	sW.close();
        });
        
        
        // Put some components in it
        subContent.addComponents(new Label("New Story"), nameInput, btnContent);
        btnContent.addComponents(addBtn, cancelBtn);

        // Center it in the browser window
        sW.center();
        
        // Open it in the UI
       	return sW;

    	
		
	}
	
	
}