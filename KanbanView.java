package kanban;

import java.util.ArrayList;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.themes.ValoTheme;

import model.Story;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import projectNav.ProjectNavigatorView;
import scrumProject.project_CAT_ATTACK.User;

@SuppressWarnings("serial")
public class KanbanView extends VerticalLayout implements View {

	public static final String VIEW_NAME = "kanbanView";

	//project navigator
	private ProjectNavigatorView projNavView; 
	private Navigator nav;

	//current project and user
	private int pIndex;
	private int sIndex;
	private User user;

	//grid layout
	private GridLayout gl1;
	private static final int STORY_COLUMN = 0;
	private static final int TASK_COLUMN = 1;
	
	//labels etc.
	private Button plusBtn;
	private Button plsTaskButton;
	private Label sprintName;
	private Label projectName;

	//keep track of story names
	private ArrayList<String> storyNameList;
	private double widthTk;

	public KanbanView(Navigator navigator, User u) {
		this.nav = navigator;
		this.user = u;
		this.pIndex = 0;
		this.sIndex = 0;
		
		this.storyNameList = new ArrayList<String>();
		
		//width of current page
		double widthSt = Page.getCurrent().getBrowserWindowWidth()*0.234375;
		widthTk = Page.getCurrent().getBrowserWindowWidth()*0.20703125;

		//format the grid.
		final HorizontalLayout header		= new HorizontalLayout(); //first header
		final HorizontalLayout subheader 	= new HorizontalLayout(); //second header
		gl1 								= new GridLayout(4,10); //grid layout
		

		float num = (float) .25;
		gl1.setColumnExpandRatio(STORY_COLUMN, num);
		num = (float) .75;
		gl1.setColumnExpandRatio(TASK_COLUMN, num);
		gl1.setSpacing(true);
		gl1.setStyleName("spacingex");
		
		/**
		 * Main Header
		 */
		final Label appName = new Label("CATattack");
		appName.addStyleName(ValoTheme.LABEL_H2);
		appName.addStyleName(ValoTheme.LABEL_COLORED);

		final Label userLabel = new Label(user.getName());
		userLabel.addStyleName("v-align-right");
		userLabel.addStyleName(ValoTheme.LABEL_H2);

		final Button acc = new Button("Account");
		acc.setHeight("45px");
		final Button logOut = new Button("Log Out");
		logOut.setHeight("45px");
		logOut.addClickListener(e -> {
			navigator.navigateTo("loginView");
		}); 

		HorizontalLayout leftHeader = new HorizontalLayout();
		HorizontalLayout rightHeader = new HorizontalLayout();
		leftHeader.addComponent(appName);
		rightHeader.addComponents(userLabel,acc,logOut);

		rightHeader.setComponentAlignment(userLabel, Alignment.TOP_RIGHT);
		rightHeader.setComponentAlignment(acc, Alignment.TOP_RIGHT);
		rightHeader.setComponentAlignment(logOut, Alignment.TOP_RIGHT);
		
		header.addComponents(leftHeader, rightHeader);
		header.setComponentAlignment(leftHeader, Alignment.TOP_LEFT);
		header.setComponentAlignment(rightHeader, Alignment.TOP_RIGHT);
		header.setWidth("100%");

		/**
		 * Sub Header
		 */
		Label taskBoard = new Label("Task Board: ");
		taskBoard.addStyleName(ValoTheme.LABEL_H3);

		Panel projectNamePanel = new Panel();
		projectName = new Label("");
		projectName.addStyleName(ValoTheme.LABEL_H3);

				
		Button backButton = new Button();
		backButton.setIcon(new ThemeResource("back_button.png"));
		backButton.setStyleName("backButton");
		backButton.setHeight("60px");
		backButton.setDescription("Click to return to Project Navigator");
		backButton.addClickListener(e -> {
			projNavView.updateUser(user);
				nav.navigateTo(projNavView.VIEW_NAME);
		}); 
		
		final Label arrow = new Label(" > ");
		arrow.addStyleName(ValoTheme.LABEL_H3);

		sprintName = new Label("");
		sprintName.addStyleName(ValoTheme.LABEL_H3);
		sprintName.setWidth("725px");
		
		subheader.addComponents(backButton, taskBoard, arrow, sprintName);  
		subheader.setComponentAlignment(taskBoard, Alignment.MIDDLE_LEFT);
		subheader.setComponentAlignment(backButton, Alignment.MIDDLE_LEFT);
		subheader.setComponentAlignment(arrow, Alignment.MIDDLE_LEFT);
		subheader.setComponentAlignment(sprintName, Alignment.MIDDLE_LEFT);
		
		/**
		 * add a story
		 */
		plusBtn = new Button("+");
		plsTaskButton = new Button("+");
		
		StoryAddWindow storyWindow = new StoryAddWindow();
		plsTaskButton.setEnabled(false);

		plusBtn.addClickListener(e -> {
			storyWindow.center();
			getUI().addWindow(storyWindow);
			//AddStoryWindow -> returns story object
			//pass story object -> StoryPanel
			//sticky gets added to UI
		});

		storyWindow.addCloseListener(e -> {
			if(storyWindow.complete == true){
				user.getProject(pIndex).getSprint(sIndex).addStory(storyWindow.getStory());
				
				int index = user.getProject(pIndex).getSprint(sIndex).getStoryIndex(storyWindow.getStory().getName());

				VerticalLayout dummyLayout = new VerticalLayout();

				StoryPanel sticky = new StoryPanel(storyWindow.getStory());
//				String width = ((widthSt)+ "px");
//				sticky.setWidth(width);

				storyNameList.add(storyWindow.getStory().getName());

				gl1.addComponent(sticky, STORY_COLUMN, index+1);
				storyWindow.reset();
				gl1.addComponent(dummyLayout, TASK_COLUMN, index+1);
				gl1.setComponentAlignment(sticky, Alignment.TOP_RIGHT);
				
				plsTaskButton.setEnabled(true);	
			}
		});

		/**
		 * add a task
		 */
		TaskAddWindow taskWindow = new TaskAddWindow(storyNameList);
		plsTaskButton.setDescription("Start a story to add tasks");
		plsTaskButton.addClickListener(e -> {
			taskWindow.updateStories(storyNameList);
			taskWindow.center();
			getUI().addWindow(taskWindow);
		});

		taskWindow.addCloseListener(e -> {
			if(taskWindow.complete == true) {
				int index = taskWindow.returnIndex();
				user.getProject(pIndex).getSprint(sIndex).getStory(index).addTask(taskWindow.getTask());

				TaskGrid tg = new TaskGrid(user.getProject(pIndex).getSprint(sIndex).getStory(index), this);
				gl1.replaceComponent(gl1.getComponent(TASK_COLUMN, index+1), tg);
				
				taskWindow.reset();
			}
		});
		

		addComponents(header, subheader, gl1);
	}

	//sets project navigator view
	public void setProjNavView(ProjectNavigatorView projNavView) {
		this.projNavView = projNavView;
	}

	//sets the content of this iteration.
	public void setData(User user, int pIndex, int sIndex) {
		this.user = user;
		this.pIndex = pIndex;
		this.sIndex = sIndex;

	}

	@Override
	public void enter(ViewChangeEvent event) {

		sprintName.setValue(user.getProject(pIndex).getSprint(sIndex).getName());
		projectName.setValue(user.getProject(pIndex).getName());
		initialize(); 
	}

	//only for entering -- resets grid layout
	private void initialize() {
		
		gl1.removeAllComponents();
		storyNameList.clear();
		
		//TODO TODO here is where the width is declared -- used in line 273
		double widthSt = Page.getCurrent().getBrowserWindowWidth()*0.234375;
		widthTk = Page.getCurrent().getBrowserWindowWidth()*0.20703125;
		
		/**
		 * Next: grid layout for stories and sprints
		 */
		final Panel column1 = new Panel("STORY");
		final Panel column2 = new Panel("TODO");
		final Panel column3 = new Panel("DOING");
		final Panel column4 = new Panel("DONE");

		column1.setStyleName(ValoTheme.PANEL_WELL);

		/**
		 * add a story
		 */
		String sizeStr = ((widthSt) - (int) plusBtn.getWidth()) + "px";
		String sizeStrTk = ((widthTk) - (int) plusBtn.getWidth()) + "px";

		HorizontalLayout hlStory = new HorizontalLayout();
		hlStory.addComponents(plusBtn, column1);
		hlStory.setMargin(false);
		hlStory.setSpacing(false);
		hlStory.setExpandRatio(plusBtn, 0);
		hlStory.setExpandRatio(column1, 1);
		
		gl1.addComponent(hlStory);
		hlStory.setSizeFull();
		float num = (float) .25;
		gl1.setColumnExpandRatio(0, num);
		

		HorizontalLayout hlTask = new HorizontalLayout();
		hlTask.addComponents(plsTaskButton, column2, column3, column4);
		hlTask.setMargin(false);
		hlTask.setSpacing(false);
		hlTask.setSizeFull();
		num = (float) .333;
		hlTask.setExpandRatio(plsTaskButton, 0);
		hlTask.setExpandRatio(column2, num);
		hlTask.setExpandRatio(column3, num);
		hlTask.setExpandRatio(column4, num);
		
		gl1.addComponents(hlTask);
		gl1.setSizeFull();
		gl1.setSpacing(true);
		num = (float) .75;
		gl1.setColumnExpandRatio(1, num);
		
		//set stories
		int index = 1;
		for (Story story:user.getProject(pIndex).getSprint(sIndex).getStoryList()) {
			VerticalLayout dummyLayout = new VerticalLayout();

			StoryPanel sticky = new StoryPanel(story);
//			String width = ((widthSt)+ "px");
			sticky.setWidth("100%");

			storyNameList.add(story.getName());

			gl1.addComponent(sticky, STORY_COLUMN, index);
			gl1.addComponent(dummyLayout, TASK_COLUMN, index);
			gl1.setComponentAlignment(sticky, Alignment.TOP_RIGHT);	
			
			//set tasks
			TaskGrid tg = new TaskGrid(story, this);
			gl1.replaceComponent(gl1.getComponent(TASK_COLUMN, index), tg);
			index++;
		}
		
	}
	
	public void updateTask(Story story) {
		int row = user.getProject(pIndex).getSprint(sIndex).getStoryIndex(story.getName());
		user.getProject(pIndex).getSprint(sIndex).updateStory(row, story);
		TaskGrid newGrid = new TaskGrid(story, this);
		gl1.replaceComponent(gl1.getComponent(TASK_COLUMN, row+1), newGrid);

	}


}
