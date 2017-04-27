package projectNav;

import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import model.Project;

public class ProjectPanel extends Panel {

	public ProjectPanel(Project project) {
		
		VerticalLayout vl = new VerticalLayout();
		HorizontalLayout hl = new HorizontalLayout();
		
		//Name and date
		Label name = new Label(project.getName());
		name.setStyleName(ValoTheme.LABEL_BOLD);
		Label date = new Label("Start date: " + project.getStartDate().toString());
		date.setStyleName(ValoTheme.LABEL_LIGHT);
		
		//edit or delete
		Button del = new Button("del");
		del.addClickListener(e -> {
			Notification.show("deleting project.");
		});
		
		//description
		Label description = new Label(project.getDescription());
		description.setStyleName(ValoTheme.LABEL_SMALL);
		description.setWidth((Page.getCurrent().getBrowserWindowWidth() -100) / 2 + "px");
	

		hl.addComponents(name, date, del);
		//TODO: set width. that's why the button doesn't go to the top right.
		hl.setComponentAlignment(del, Alignment.TOP_RIGHT);
		vl.addComponents(hl, description);
		vl.setSpacing(false);
		
		this.setContent(vl);
		this.addStyleName(ValoTheme.PANEL_WELL);
		
	}
	
}
