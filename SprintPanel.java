package projectNav;

import java.util.ArrayList;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import kanban.KanbanView;
import model.Project;
import model.Sprint;

public class SprintPanel extends VerticalLayout {

	final ArrayList<Sprint> sprintList;
	
	public SprintPanel(Project project, Navigator nav, KanbanView nextView) {
		this.sprintList = project.getSprintList();
		
		for (Sprint sprint:sprintList) {
			
			Panel panel = new Panel();
			HorizontalLayout hl = new HorizontalLayout();
			Label name = new Label("Sprint " + i + ": " + sprint.getName());
			name.setStyleName(ValoTheme.LABEL_BOLD);
			Label startDate = new Label("Start date: " + sprint.getStartDate().toString());
			//	Label endDate =   new Label("End date:   " + sprint.getEndDate().toString());
			startDate.setStyleName(ValoTheme.LABEL_LIGHT);
			
			hl.addComponents(name, startDate);
			hl.setSpacing(true);
			panel.setContent(hl);
			
			
			panel.addStyleName(ValoTheme.PANEL_WELL);
			addComponent(panel);
			
			panel.addClickListener(e -> {
				nextView.setSprint(sprint);
				nav.navigateTo(nextView.VIEW_NAME);
			});
		}

		setSpacing(true);
		setMargin(false);



	}


}
