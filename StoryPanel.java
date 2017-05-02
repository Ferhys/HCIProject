package kanban;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import model.Story;

public class StoryPanel extends Panel {

	public StoryPanel(Story story) {

		VerticalLayout vl = new VerticalLayout();
		HorizontalLayout hl = new HorizontalLayout();
		
		//name and date
		Label name = new Label(story.getName());
		name.setStyleName(ValoTheme.LABEL_BOLD);
		Label startDate = new Label("Start date: " + story.getStartDate().toString());
		startDate.setStyleName(ValoTheme.LABEL_LIGHT);

		Label description = new Label(story.getDescription());
		description.setStyleName(ValoTheme.LABEL_SMALL);
		
		hl.addComponents(name, startDate);
		vl.addComponents(hl, description);
		hl.setSpacing(true);
		setContent(hl);

		this.addStyleName(ValoTheme.PANEL_WELL);

	}
}
