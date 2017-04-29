package kanban;

import java.util.ArrayList;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import model.Sprint;
import model.Story;

public class StoryPanel extends Panel {

	
	
	public StoryPanel(Story story) {
			
		
	
			HorizontalLayout hl = new HorizontalLayout();
			Label name = new Label(story.getName());
			name.setStyleName(ValoTheme.LABEL_BOLD);
			Label startDate = new Label("Start date: " + story.getStartDate().toString());
			//	Label endDate =   new Label("End date:   " + sprint.getEndDate().toString());
			startDate.setStyleName(ValoTheme.LABEL_LIGHT);
			
			hl.addComponents(name, startDate);
			hl.setSpacing(true);
			setContent(hl);

			this.addStyleName(ValoTheme.PANEL_WELL);
		
	}
