ppackage kanban;

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
import com.vaadin.ui.Window;

import projectNav.ProjectNavigatorView;
import scrumProject.project_CAT_ATTACK.User;

public class KanbanView extends VerticalLayout implements View {

	public static final String VIEW_NAME = "kanbanView";
	
	private ProjectNavigatorView projNavView; 
	private Sprint sprint;
	private Project project;
	private User user;
	private Navigator nav;
	
	private static final int STORY_COLUMN = 1;
	private static final int TASK_COLUMN = 2;
	
    private Label sprintName;
    private Label projectName;
    
	public KanbanView(Navigator navigator) {
		this.nav = navigator;
		project = new Project();
		sprint = new Sprint();
		
		/*Take this out plz
		//Label label = new Label("SPRINT IS: " + sprint.getName());
		Button back = new Button("Back to Project Navigator");
		back.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				nav.navigateTo(projNavView.VIEW_NAME);
			}
		});

//TODO: Figure this out
		//addComponents(back);

		addComponents(back);
		*/

		
		//format the grid.
        final VerticalLayout mainVL = new VerticalLayout();
    //    final HorizontalLayout hl1 = new HorizontalLayout();
        final HorizontalLayout hl2 = new HorizontalLayout();
    //    final HorizontalLayout hl3 = new HorizontalLayout();
        final GridLayout gl1 = new GridLayout(10,10);
        
        // Stuff going in the first horizontal layout - plz take out
        /*
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
        */
        
        //Project -> sprint links
        Panel projectNamePanel = new Panel();
        projectName = new Label("");
        projectName.addStyleName(ValoTheme.LABEL_H3);
        
        //take back to project navigator view
        projectNamePanel.setContent(projectName);
        projectNamePanel.addClickListener(e -> {
        	navigator.navigateTo(projNavView.VIEW_NAME);
        });
        projectNamePanel.setDescription("Click to return to Project Navigator");
        
        final Label arrow = new Label(" > ");
        arrow.addStyleName(ValoTheme.LABEL_H3);
        
        //TODO: maybe make this a combo box instead??
        sprintName = new Label("");
        sprintName.addStyleName(ValoTheme.LABEL_H3);
        sprintName.setWidth("725px");
        
        //TODO: Take out burndown chart...
        /*
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
        */
        
        //Next: grid layout for stories and sprints
        final Panel column1 = new Panel("STORY");
        final Panel column2 = new Panel("TODO");
        final Panel column3 = new Panel("DOING");
        final Panel column4 = new Panel("DONE");
        
        column1.setStyleName(ValoTheme.PANEL_WELL);
        
//        final Label header1 = new Label("STORY");
//        final Label header2 = new Label("TO DO");
//        final Label header3 = new Label("DOING");
//        final Label header4 = new Label("DONE");
        

//        //TODO: fix AddStory
//        final Button plusBtn = new Button("+");
//        StoryAddWindow storyWindow = new StoryAddWindow();
//        plusBtn.setHeight("25px");
//        plusBtn.addClickListener(e -> {
//        	storyWindow.center();
//        	getUI().addWindow(storyWindow);
//        	//AddStoryWindow -> returns story object
//        	//pass story object -> AddStorySticky
//        	//sticky gets added to UI
//        });
//        
//          storyWindow.addCloseListener(e -> {
//        	if(storyWindow.getStory().getName() != ""){
//        		
//        		sprint.addStory(storyWindow.getStory());
//        		int index = sprint.getStoryIndex(storyWindow.getStory().getName());
//        		VerticalLayout dummyLayout = new VerticalLayout();
//        		StoryPanel sticky = new StoryPanel(storyWindow.getStory());
//        		gl1.addComponent(sticky, STORY_COLUMN, index+1);
//        		gl1.addComponent(dummyLayout, TASK_COLUMN, index+1);
//        		//gl1.replaceComponent(gl1.getComponent(STORY_COLUMN, index+1), sticky);
//        		gl1.setComponentAlignment(sticky, Alignment.TOP_CENTER);
//        		storyWindow.reset();
//        		
//        		
//        	}
//        });

  
  
        //TODO: fix AddStory
        final Button plusBtn = new Button("+");
        StoryAddWindow storyWindow = new StoryAddWindow();
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
        		VerticalLayout dummyLayout = new VerticalLayout();
        		StoryPanel sticky = new StoryPanel(storyWindow.getStory());
        		gl1.addComponent(sticky, STORY_COLUMN, index+1);
        		gl1.addComponent(dummyLayout, TASK_COLUMN, index+1);
        		//gl1.replaceComponent(gl1.getComponent(STORY_COLUMN, index+1), sticky);
        		gl1.setComponentAlignment(sticky, Alignment.TOP_CENTER);
        		storyWindow.reset();
        		
        		
        	}
        });
        

        
        column1.setWidth("300px");
        column2.setWidth("300px");
        column3.setWidth("300px");
        column4.setWidth("300px");
        
//        column1.setContent(header1);
//        column2.setContent(header2);
//        column3.setContent(header3);
//        column4.setContent(header4);
        
        column1.setStyleName("v-align-center");
       	column2.setStyleName("v-align-center");
        column3.setStyleName("v-align-center");
        column4.setStyleName("v-align-center");
        
        //gl1.addComponent(plusBtn);
        gl1.addComponent(column1);
        gl1.addComponent(column2);
        gl1.addComponent(column3);
        gl1.addComponent(column4);
        
//        hl1.addComponents(appName, user, acc, logOut);
        hl2.addComponents(projectNamePanel, arrow, sprintName);
   //     hl3.addComponent(gl1);     

  //TODO: look at this
        mainVL.addComponents(back, hl2, hl3);
        addComponents(hl1, mainVL);

  //      mainVL.addComponents(hl1, hl2, hl3);
  //      mainVL.addComponents(hl2, gl1);
  //      addComponent(mainVL);

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
	
	public void setData(User user, Project project, Sprint sprint) {
		this.user = user;
		this.project = project;
		this.sprint = sprint;
	}
	
	@Override
	public void enter(ViewChangeEvent event) {

        sprintName.setValue(sprint.getName());
        projectName.setValue(project.getName());
	}

}