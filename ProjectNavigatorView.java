package projectNav;

import java.util.ArrayList;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import kanban.KanbanView;
import model.Project;
import model.Sprint;
import scrumProject.project_CAT_ATTACK.User;

public class ProjectNavigatorView extends VerticalLayout implements View {

	public static final String VIEW_NAME = "projectNavigatorView";
	
	private static GridLayout gl;
	private static final int PROJECT_COLUMN = 0;
	private static final int SPRINT_COLUMN = 1;
	final double width;

	private final Navigator navigator;
	private final KanbanView kanbanView;
	private User user;

	private ArrayList<String> projectNameList;

	public ProjectNavigatorView(Navigator navigator, KanbanView kv, User u) {
		this.navigator = navigator;
		this.kanbanView = kv;
		this.user = u;
		this.projectNameList = new ArrayList<String>();
		
		//view layout 
		final HorizontalLayout header 		= new HorizontalLayout();
		final HorizontalLayout subHeader	= new HorizontalLayout();
		gl 									= new GridLayout(2,5);
		final HorizontalLayout h1 			= new HorizontalLayout();
		final HorizontalLayout h2 			= new HorizontalLayout();
		
		width = Page.getCurrent().getBrowserWindowWidth() * 0.42;

		/**
		 *  Stuff going in the header 
		 */
		final Label appName = new Label("CATattack");
		appName.addStyleName(ValoTheme.LABEL_H2);
		appName.addStyleName(ValoTheme.LABEL_COLORED);

		final Label userLabel = new Label(user.getName());
		userLabel.addStyleName("v-align-right");
		userLabel.addStyleName(ValoTheme.LABEL_H2);

		final Button acc = new Button("Account");
		acc.setHeight("45px");
		acc.addClickListener(e -> {
			Window accountSettings = new Window();
		}); 
		
		
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
		 *  subHeader - creates consistency between views
		 */
		final Label viewName = new Label("Project Navigator");
		viewName.setStyleName(ValoTheme.LABEL_H3);
		subHeader.addComponent(viewName);

		//grid layout headers
		final Panel pProject 			= new Panel("PROJECTS");
		final Panel pSprint 			= new Panel("SPRINTS");

		final Button addProject 		= new Button("+");
		final Button addSprint 			= new Button("+");
		
		/**
		 * add a project
		 */
		ProjectAddWindow projectWindow = new ProjectAddWindow();

		addProject.addClickListener(e-> {
			projectWindow.center();
			getUI().addWindow(projectWindow);
		});

		projectWindow.addCloseListener(e -> {
			if (projectWindow.complete == true) {
				user.addProject(projectWindow.getProject());
				//now, add panel for Project and future sprints in a VerticalLayout
				ProjectPanel pl = new ProjectPanel(user.getLastProject(), width, this);
				VerticalLayout dummyLayout = new VerticalLayout();
				projectNameList.add(user.getLastProject().getName());
				projectWindow.reset();

				gl.addComponent(pl, PROJECT_COLUMN, user.getProjectList().size());
				gl.addComponent(dummyLayout, SPRINT_COLUMN, user.getProjectList().size());
				gl.setComponentAlignment(pl, Alignment.TOP_CENTER);
				gl.insertRow(gl.getRows());
				addSprint.setEnabled(true);
				addSprint.setDescription("add a sprint to a project");
			}	
		});

		/**
		 * add sprints to project
		 */
		SprintAddWindow sprintWindow = new SprintAddWindow(projectNameList);
		addSprint.setEnabled(false);
		addSprint.setDescription("Start a Project to add sprints");
		addSprint.addClickListener(e -> {
			sprintWindow.updateProjects(projectNameList);
			sprintWindow.center();
			getUI().addWindow(sprintWindow);
		});

		sprintWindow.addCloseListener(e -> {
			if(sprintWindow.complete == true) {
				int index = sprintWindow.returnProjectIndex();
				user.getProject(index).addSprint(sprintWindow.getSprint());;

				//now, add sprint panel
				SprintPanel sp = new SprintPanel(user, index, navigator, kanbanView, width, this);

				gl.replaceComponent(gl.getComponent(SPRINT_COLUMN, index+1), sp);
				gl.setComponentAlignment(sp, Alignment.TOP_CENTER);
				sprintWindow.reset();
			}
		});
		
		//pProject.setWidth("100%");
		
		h1.addComponents(addProject, pProject);
		h1.setExpandRatio(addProject, 0);
		h1.setExpandRatio(pProject, 1);
		h1.setWidth("100%");
		
		h2.addComponents(addSprint, pSprint);
		h2.setExpandRatio(addSprint, 0);
		h2.setExpandRatio(pSprint, 1);
		h2.setWidth("100%");

//		h1.setMargin(false);
//		h1.setSpacing(false);
//		h2.setMargin(false);
//		h2.setSpacing(false);

//		String sizeStr = ((width) - (int) addProject.getWidth()) + "px";
//		pProject.setWidth(sizeStr);
//		pSprint.setWidth(sizeStr);
		
		float num = (float) .5;
		
		gl.addComponents(h1, h2);
		gl.setColumnExpandRatio(0, num);
		gl.setColumnExpandRatio(1, num);
		gl.setSpacing(true);
		gl.setWidth("100%");

		addComponents(header, subHeader, gl);
	}


	@Override
	public void enter(ViewChangeEvent event) {

	}

	public void authenticate() {
		navigator.navigateTo("loginView");
	}
	
	//removes a project with delete button
	public void removeProject(Project project) {
		int index = user.getProjectIndex(project);
		user.removeProject(project);
		projectNameList.remove(index);
		gl.removeRow(index + 1);
	}
	
	public void removeSprint(Project project, Sprint sprint) {
		int pindex = user.getProjectIndex(project);
		user.getProject(pindex).deleteSprint(sprint);
		SprintPanel sp = new SprintPanel(user, pindex, navigator, kanbanView, width, this);
		gl.replaceComponent(gl.getComponent(SPRINT_COLUMN, pindex+1), sp);
	}


	//keeps user updated
	public void updateUser(User user2) {
		this.user = user2;
	}


}
