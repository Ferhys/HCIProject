package kanban;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
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
import projectNav.ProjectNavigatorView;
import scrumProject.project_CAT_ATTACK.User;

public class KanbanView extends VerticalLayout implements View {

	public static final String VIEW_NAME = "kanbanView";
	
	private ProjectNavigatorView projNavView; 
	private Navigator nav;
	
	private Sprint sprint;
	private Project project;
	private User user;

	private static final int STORY_COLUMN = 0;
	private static final int TO_DO_COLUMN = 1;
	
	
    private Label sprintName;
    private Label projectName;
    
	public KanbanView(Navigator navigator, User u) {
		this.nav = navigator;
		this.user = u;
		this.project = new Project();
		this.sprint = new Sprint();
		
		//format the grid.
        final HorizontalLayout hl1 = new HorizontalLayout();
        final HorizontalLayout hl2 = new HorizontalLayout();
        final GridLayout gl1 = new GridLayout(10,10);
        
       	/**
       	 * Main Header
       	 */
        final Label appName = new Label("CATattack");
        appName.addStyleName(ValoTheme.LABEL_H2);
        appName.addStyleName(ValoTheme.LABEL_COLORED);
        
        final Label userLabel = new Label(user.getName());
        userLabel.setWidth("900px");
        userLabel.addStyleName("v-align-right");
        userLabel.addStyleName(ValoTheme.LABEL_H2);
        
        final Button acc = new Button("Account");
        acc.setHeight("45px");
        
        final Button logOut = new Button("Log Out");
        logOut.setHeight("45px");
        
        /**
         * Sub Header
         */
        Label taskBoard = new Label("Task Board: ");
        taskBoard.addStyleName(ValoTheme.LABEL_H3);
        
        Panel projectNamePanel = new Panel();
        
        projectName = new Label("");
        projectName.addStyleName(ValoTheme.LABEL_H3);
        
        //take back to project navigator view
        projectNamePanel.setContent(projectName);
        projectNamePanel.addClickListener(e -> {
        	projNavView.updateProject(); //TODO: fix this
        	navigator.navigateTo(projNavView.VIEW_NAME);
        });
        projectNamePanel.setDescription("Click to return to Project Navigator");
        
        final Label arrow = new Label(" > ");
        arrow.addStyleName(ValoTheme.LABEL_H3);
        
        //TODO: maybe make this a combo box instead??
        sprintName = new Label("");
        sprintName.addStyleName(ValoTheme.LABEL_H3);
        sprintName.setWidth("725px");

        /**
         * Next: grid layout for stories and sprints
         */
        final Panel column1 = new Panel("STORY");
        final Panel column2 = new Panel("TODO");
        final Panel column3 = new Panel("DOING");
        final Panel column4 = new Panel("DONE");
        
        column1.setStyleName(ValoTheme.PANEL_WELL);

        //Add a story
        final Button plusBtn = new Button("+");
        StoryAddWindow storyWindow = new StoryAddWindow();
        plusBtn.setHeight("25px");
        plusBtn.addClickListener(e -> {
        	storyWindow.center();
        	getUI().addWindow(storyWindow);
        	//AddStoryWindow -> returns story object
        	//pass story object -> StoryPanel
        	//sticky gets added to UI
        });
        
          storyWindow.addCloseListener(e -> {
        	if(storyWindow.complete == true){
        		
        		sprint.addStory(storyWindow.getStory());
        		int index = sprint.getStoryIndex(storyWindow.getStory().getName());
        		VerticalLayout dummyLayout = new VerticalLayout();
        		StoryPanel sticky = new StoryPanel(storyWindow.getStory());
        		sticky.setWidth("300px");
        	
        		gl1.addComponent(sticky, STORY_COLUMN, index+1);
        		
        		gl1.addComponent(dummyLayout, TO_DO_COLUMN, index+1);
        		gl1.replaceComponent(gl1.getComponent(STORY_COLUMN, index+1), sticky);
        		gl1.setComponentAlignment(sticky, Alignment.TOP_RIGHT);
        		storyWindow.reset();
        		
        		
        	}
        });
        
        column1.setWidth("300px");
        column2.setWidth("300px");
        column3.setWidth("300px");
        column4.setWidth("300px");

        column1.setStyleName("v-align-center");
       	column2.setStyleName("v-align-center");
        column3.setStyleName("v-align-center");
        column4.setStyleName("v-align-center");
        
        HorizontalLayout hlStory = new HorizontalLayout();
        hlStory.addComponents(plusBtn, column1);
        
        gl1.addComponent(hlStory);
        gl1.addComponent(column2);
        gl1.addComponent(column3);
        gl1.addComponent(column4);
        
        hl1.addComponents(appName, userLabel, acc, logOut);
        hl2.addComponents(taskBoard, projectNamePanel, arrow, sprintName);   

        addComponents(hl1, hl2, gl1);
	}
	
	public void setProjNavView(ProjectNavigatorView projNavView) {
		this.projNavView = projNavView;
	}
	
	public void setData(Project project, Sprint sprint) {
		this.project = project;
		this.sprint = sprint;
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
        sprintName.setValue(sprint.getName());
        projectName.setValue(project.getName());
	}


}
