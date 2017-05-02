package projectNav;

import java.util.ArrayList;

import com.vaadin.navigator.Navigator;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import kanban.KanbanView;
import model.Project;
import model.Sprint;
import scrumProject.project_CAT_ATTACK.User;

public class SprintPanel extends VerticalLayout {

	final ArrayList<Sprint> sprintList;
	
	public SprintPanel(Project project, Navigator nav, KanbanView nextView, double size, ProjectNavigatorView parentView) {
		this.sprintList = project.getSprintList();
		
		for (Sprint sprint:sprintList) {
			
			//layout
			Panel panel = new Panel();
			HorizontalLayout hl = new HorizontalLayout();
			VerticalLayout vl = new VerticalLayout();
			hl.setWidth(size + "px");
			
			//name and dates
			Label name = new Label(sprint.getName());
			name.setStyleName(ValoTheme.LABEL_BOLD);
			Label startDate = new Label("Start date: " + sprint.getStartDate().toString());
			Label endDate =   new Label("- " + sprint.getEndDate().toString());
			startDate.setStyleName(ValoTheme.LABEL_LIGHT);
			endDate.setStyleName(ValoTheme.LABEL_LIGHT);
			
			//edit and delete features
			Button del = new Button("del");
			del.addClickListener(e -> {
				parentView.removeSprint(project, sprint);
			});
			
			//description
			Label description = new Label(sprint.getDescription());
			description.setStyleName(ValoTheme.LABEL_SMALL);
			description.setWidth(size + "px");
			
			
			//add to layout
			hl.addComponents(name, startDate, del);
			hl.setComponentAlignment(del, Alignment.TOP_RIGHT);
			vl.addComponents(hl, description);
			vl.setSpacing(false);
			
			hl.setSpacing(false);
			panel.setContent(vl);
			
			panel.addStyleName(ValoTheme.PANEL_WELL);
			addComponent(panel);
			
			//click the panel to go to task board view
			panel.setDescription("Click for Task Board View");
			panel.addClickListener(e -> {
				nextView.setData(project, sprint);
				nav.navigateTo(nextView.VIEW_NAME);
			});
		}

		setSpacing(true);
		setMargin(false);

	}


}
