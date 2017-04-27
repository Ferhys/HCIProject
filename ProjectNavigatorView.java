package projectNav;

import java.util.ArrayList;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import kanban.KanbanView;
import model.Project;
import scrumProject.project_CAT_ATTACK.User;

public class ProjectNavigatorView extends VerticalLayout implements View {

	public static final String VIEW_NAME = "projectNavigatorView";
	private static final int PROJECT_COLUMN = 0;
	private static final int SPRINT_COLUMN = 1;
	private final Navigator navigator;
	
	private final User user;
	private ArrayList<String> projectNameList;
	
	public ProjectNavigatorView(Navigator navigator, KanbanView kanbanView, User u) {
		this.navigator = navigator;
		this.user = u;
		this.projectNameList = new ArrayList<String>();
		
		Label label = new Label("This is the Project Navigator.");
		Button next = new Button("Go to Kanban Board");
		next.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				navigator.navigateTo(kanbanView.VIEW_NAME);
			}
		});

		//remember, gridlayout is supposed to be in xy coordinates
		final GridLayout gl = new GridLayout(2,5);
		
		final HorizontalLayout h1 = new HorizontalLayout();
		final HorizontalLayout h2 = new HorizontalLayout();
		
		final Panel pProject = new Panel("PROJECTS");
		final Panel pSprint = new Panel("SPRINTS");
		
		final Button addProject = new Button("+");
		final Button addSprint = new Button("+");
		
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
				ProjectPanel pl = new ProjectPanel(user.getLastProject());
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
				SprintPanel sp = new SprintPanel(user.getProject(index), navigator, kanbanView);

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
		
		double width = Page.getCurrent().getBrowserWindowWidth() * 0.42;
		String sizeStr = ((width) - (int) addProject.getWidth()) + "px";
		pProject.setWidth(sizeStr);
		pSprint.setWidth(sizeStr);

		gl.addComponents(h1, h2);
		gl.setSpacing(true);
	
		addComponents(label, next, gl);
	}
	
	
	@Override
	public void enter(ViewChangeEvent event) {


	}
	
	public void authenticate() {
		navigator.navigateTo("loginView");
	}

	
}
