package kanban;

import java.util.ArrayList;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import model.Sprint;
import model.Story;

public class AddStorySticky extends VerticalLayout {

	final ArrayList<Story> storyList;
	
	public AddStorySticky(Sprint sprint) {
		this.storyList = sprint.getStoryList();
		
		for (Story story:storyList) {
			
			Panel panel = new Panel();
			HorizontalLayout hl = new HorizontalLayout();
			Label name = new Label(story.getName());
			name.setStyleName(ValoTheme.LABEL_BOLD);
			Label startDate = new Label("Start date: " + sprint.getStartDate().toString());
			//	Label endDate =   new Label("End date:   " + sprint.getEndDate().toString());
			startDate.setStyleName(ValoTheme.LABEL_LIGHT);
			
			hl.addComponents(name, startDate);
			hl.setSpacing(true);
			panel.setContent(hl);
			
			
			panel.addStyleName(ValoTheme.PANEL_WELL);
			addComponent(panel);
			
		}

		setSpacing(true);
		setMargin(false);



	}


}