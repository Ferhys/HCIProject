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
		final Label appNameHeader = new Label("CATattack");
		appNameHeader.addStyleName(ValoTheme.LABEL_COLORED);
		appNameHeader.addStyleName(ValoTheme.LABEL_H2);
		
		final Label userHeader = new Label(user.getName());
		userHeader.setWidth("900px");
		userHeader.addStyleName("v-align-right");
		userHeader.addStyleName(ValoTheme.LABEL_H2);

		final Button acc = new Button("Account");
		acc.setHeight("45px");

		final Button logOut = new Button("Log Out");
		logOut.setHeight("45px");
		
		header.addComponents(appNameHeader, userHeader, acc, logOut);
		
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
		
		h1.addComponents(addProject, pProject);
		h2.addComponents(addSprint, pSprint);

		h1.setMargin(false);
		h1.setSpacing(false);
		h2.setMargin(false);
		h2.setSpacing(false);

		String sizeStr = ((width) - (int) addProject.getWidth()) + "px";
		pProject.setWidth(sizeStr);
		pSprint.setWidth(sizeStr);

		gl.addComponents(h1, h2);
		gl.setSpacing(true);

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
