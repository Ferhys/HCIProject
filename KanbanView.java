package kanban;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

import model.Project;
import model.Sprint;

import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import projectNav.ProjectNavigatorView;

public class KanbanView extends VerticalLayout implements View {

	public static final String VIEW_NAME = "kanbanView";
	private ProjectNavigatorView projNavView; 
	private Sprint sprint;
	private Project project;
	private Navigator nav;
	private static final int STORY_COLUMN = 1;
	
	public KanbanView(Navigator navigator) {
		this.nav = navigator;
		project = new Project();
		sprint = new Sprint();
	}
	
	public void setProjNavView(ProjectNavigatorView projNavView) {
		this.projNavView = projNavView;
	}
	
	public void setSprint(Sprint sprint) {
		this.sprint = sprint;
	}
	
	public void setProject(Project project){
		this.project = project;
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		this.removeAllComponents();

		//Label label = new Label("SPRINT IS: " + sprint.getName());
		Button back = new Button("Back to Project Navigator");
		back.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				nav.navigateTo(projNavView.VIEW_NAME);
			}
		});
		addComponents(back);
		
        final VerticalLayout mainVL = new VerticalLayout();
        final HorizontalLayout hl1 = new HorizontalLayout();
        final HorizontalLayout hl2 = new HorizontalLayout();
        final HorizontalLayout hl3 = new HorizontalLayout();
        final GridLayout gl1 = new GridLayout(10,10);
        
        // Stuff going in the first horizontal layout. 
        final Label appName = new Label("CATattack");
        appName.setStyleName(ValoTheme.LABEL_H2);
        
        final Label user = new Label("UserName");
        user.setWidth("900px");
        user.addStyleName("v-align-right");
        user.addStyleName(ValoTheme.LABEL_H2);
        
        final Button acc = new Button("Account");
        acc.setHeight("45px");
        
        final Button logOut = new Button("Log Out");
        logOut.setHeight("45px");
        
        //stuff for second horizontal layout 
        
        final Label projectName = new Label(project.getName());
        projectName.addStyleName(ValoTheme.LABEL_H3);
        
        
        final Label arrow = new Label(" > ");
        arrow.addStyleName(ValoTheme.LABEL_H3);
        
        final Label sprintName = new Label(sprint.getName());
        sprintName.addStyleName(ValoTheme.LABEL_H3);
        sprintName.setWidth("725px");
        
        final Button bDButton = new Button("Burndown Chart");
        bDButton.setHeight("45px");
        
        bDButton.addClickListener(e -> {
        	
        	Window subWindow = new Window();
        	VerticalLayout subContent = new VerticalLayout();
            subWindow.setContent(subContent);
            subWindow.setHeight("600px");
            subWindow.setWidth("700px");
            // Put some components in it
            subContent.addComponent(new Label("Burndown Chart"));

            //TODO make burndown chart here
            
            // Center it in the browser window
            subWindow.center();

            // Open it in the UI
            getUI().addWindow(subWindow);
        	
        });
        //stuff for third horizontal layout
        final Panel column1 = new Panel();
        final Panel column2 = new Panel();
        final Panel column3 = new Panel();
        final Panel column4 = new Panel();
        
        final Label header1 = new Label("STORY");
        final Label header2 = new Label("TO DO");
        final Label header3 = new Label("DOING");
        final Label header4 = new Label("DONE");
        
        //TODO: fix AddStory
        final Button plusBtn = new Button("+");
        AddStory storyWindow = new AddStory();
        plusBtn.setHeight("25px");
        plusBtn.addClickListener(e -> {
        	storyWindow.center();
        	getUI().addWindow(storyWindow);
        	//AddStoryWindow -> returns story object
        	//pass story object -> AddStorySticky
        	//sticky gets added to UI
        });
        
        storyWindow.addCloseListener(e -> {
        	if(storyWindow.getStory().getName() != ""){
        		
        		sprint.addStory(storyWindow.getStory());
        		int index = sprint.getStoryIndex(storyWindow.getStory().getName());
        		System.out.println(index);
        		
        		
        		
        	}
        });
        
        column1.setWidth("300px");
        column2.setWidth("300px");
        column3.setWidth("300px");
        column4.setWidth("300px");
        
        column1.setContent(header1);
        column2.setContent(header2);
        column3.setContent(header3);
        column4.setContent(header4);
        
        column1.setStyleName("v-align-center");
       	column2.setStyleName("v-align-center");
        column3.setStyleName("v-align-center");
        column4.setStyleName("v-align-center");
        
        gl1.addComponent(plusBtn);
        gl1.addComponent(column1);
        gl1.addComponent(column2);
        gl1.addComponent(column3);
        gl1.addComponent(column4);
        
        hl1.addComponents(appName, user, acc, logOut);
        hl2.addComponents(projectName, arrow, sprintName, bDButton);
        hl3.addComponent(gl1);     
        
        mainVL.addComponents(hl1, hl2, hl3);
        addComponent(mainVL);
	}

}