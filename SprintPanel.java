package projectNav;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import model.Sprint;

public class SprintPanel extends Panel{

	public SprintPanel(Sprint sprint) {
		
		HorizontalLayout hl = new HorizontalLayout();
		Label name = new Label(sprint.getName());
		name.setStyleName(ValoTheme.LABEL_BOLD);
		Label startDate = new Label("Start date: " + sprint.getStartDate().toString());
	//	Label endDate =   new Label("End date:   " + sprint.getEndDate().toString());
		startDate.setStyleName(ValoTheme.LABEL_LIGHT);
		hl.addComponents(name, startDate);
		
		this.setContent(hl);
		this.addStyleName(ValoTheme.PANEL_WELL);
	}
	
	
}
