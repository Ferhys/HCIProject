package projectNav;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import kanban.KanbanView;
import scrumProject.project_CAT_ATTACK.User;

public class ProjectNavigatorView extends VerticalLayout implements View {

	public static final String VIEW_NAME = "projectNavigatorView";
	private final Navigator navigator;
	private int row; 
	
	@SuppressWarnings("deprecation")
	public ProjectNavigatorView(Navigator navigator, KanbanView kanbanView, User user) {
		this.navigator = navigator;
		this.user = user;
		projectList = new ArrayList<String>();
		row = 1;
		
		Label label = new Label("This is the Project Navigator.");
		Button next = new Button("Go to Kanban Board");
		next.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				navigator.navigateTo(kanbanView.VIEW_NAME);
			}
		});

		//remember, gridlayout is supposed to be in xy coordinates
		final GridLayout gl = new GridLayout(3,10);
		
		final HorizontalLayout h1 = new HorizontalLayout();
		final HorizontalLayout h2 = new HorizontalLayout();
		final VerticalLayout h3 = new VerticalLayout();
		final Panel pProject = new Panel("PROJECTS");
		final Panel pSprint = new Panel("SPRINTS");
		final Panel pTask = new Panel("STORIES");
		
		/**
		 * add a project
		 */
		ProjectAddWindow projectWindow = new ProjectAddWindow();
		final Button addProject = new Button("+");
		addProject.addClickListener(e-> {
			projectWindow.center();
			getUI().addWindow(projectWindow);
		});
		
		projectWindow.addCloseListener(e -> {
			if (projectWindow.getProject().getName() != null) {
				user.addProject(projectWindow.getProject());
				//now, add label for project and for future sprints
				ProjectPanel pl = new ProjectPanel(user.getLastProject());
				VerticalLayout vl = new VerticalLayout();
				vl.setMargin(false);
				projectList.add(user.getLastProject().getName());
				projectWindow.reset();
				gl.addComponent(pl, 0, row);
				gl.addComponent(vl, 1, row);
				gl.setComponentAlignment(pl, Alignment.TOP_CENTER);
				gl.setComponentAlignment(vl, Alignment.TOP_CENTER);
				row++;

			}	
		});
		
		/**
		 * add sprints to project
		 */
		SprintAddWindow sprintWindow = new SprintAddWindow(projectList);
		final Button addSprint = new Button("+");
		addSprint.addClickListener(e -> {
			sprintWindow.center();
			getUI().addWindow(sprintWindow);
		});
		
		sprintWindow.addCloseListener(e -> {
			if(sprintWindow.getSprint().getName() != null){
				int index = sprintWindow.returnProjectIndex();
				//user.getProject(index).addSprint(sprintWindow.getSprint());;
				//now, add sprint panel
				SprintPanel sp = new SprintPanel(sprintWindow.getSprint());
				((VerticalLayout) gl.getComponent(1, index+1)).addComponent(sp);
			//	gl.setComponentAlignment(sp, Alignment.TOP_CENTER);
				sprintWindow.reset();
			}
		});
		
		h1.addComponents(addProject, pProject);
		h2.addComponents(addSprint, pSprint);
		h3.addComponent(pTask);

		h1.setMargin(false);
		h1.setSpacing(false);
		h2.setMargin(false);
		h2.setSpacing(false);
		h3.setMargin(false);
		
		String sizeStr = (400 - (int) addProject.getWidth()) + "px";
		pProject.setWidth(sizeStr);
		pSprint.setWidth(sizeStr);
		h3.setWidth("400px");

		gl.addComponents(h1, h2, h3);
		//gl.setMargin(false);
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
