package projectNav;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import model.Project;
import model.Sprint;

public class SprintPanel extends VerticalLayout {
	
	public SprintPanel(Project project) {
		
		for (Sprint sprint:project.getSprintList()) {
			Panel panel = new Panel();
			HorizontalLayout hl = new HorizontalLayout();
			Label name = new Label(sprint.getName());
			name.setStyleName(ValoTheme.LABEL_BOLD);
			Label startDate = new Label("Start date: " + sprint.getStartDate().toString());
		//	Label endDate =   new Label("End date:   " + sprint.getEndDate().toString());
			startDate.setStyleName(ValoTheme.LABEL_LIGHT);
			hl.addComponents(name);
			hl.setSpacing(true);
			panel.setContent(hl);
			panel.addStyleName(ValoTheme.PANEL_WELL);
			addComponent(panel);
		}
		
		setSpacing(true);
		setMargin(false);
		 
		 
		
	}
	
	
}
