package projectNav;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.themes.ValoTheme;

import model.Project;

public class ProjectPanel extends Panel {

	public ProjectPanel(Project project) {
		HorizontalLayout hl = new HorizontalLayout();
		Label name = new Label(project.getName());
		name.setStyleName(ValoTheme.LABEL_BOLD);
		Label date = new Label("Start date: " + project.getStartDate().toString());
		date.setStyleName(ValoTheme.LABEL_LIGHT);
		hl.addComponents(name, date);
		
		this.setContent(hl);
		this.addStyleName(ValoTheme.PANEL_WELL);
		
	}
	
}
