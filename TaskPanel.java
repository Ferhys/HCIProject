package kanban;

import java.util.ArrayList;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import model.Story;
import model.Task;

public class TaskPanel extends VerticalLayout {

	final ArrayList<Task> taskList;
	
	public TaskPanel(Story story, double size, int index) {
		this.taskList = story.getTaskList();
		
	
			//layout
			Panel panel = new Panel();
			HorizontalLayout hl = new HorizontalLayout();
			VerticalLayout vl = new VerticalLayout();
			hl.setWidth(size + "px");
			
			//name and hours
			Label name = new Label(taskList.get(index).getName());
			name.setStyleName(ValoTheme.LABEL_BOLD);
			Label hoursP = new Label("Planned Hours: " + taskList.get(index).getPlannedHours());
			Label hoursC =   new Label("/nCompleted Hours: " + taskList.get(index).getCompletedHours());
			hoursP.setStyleName(ValoTheme.LABEL_LIGHT);
			hoursC.setStyleName(ValoTheme.LABEL_LIGHT);
			
			//edit and delete features
			Button del = new Button("del");
			del.addClickListener(e -> {
				Notification.show("Deleting Task.");
			});
	
			
			//add to layout
			hl.addComponents(name, hoursP, del);
			hl.setComponentAlignment(del, Alignment.TOP_RIGHT);
			vl.addComponents(hl);
			vl.setSpacing(false);
			
			hl.setSpacing(false);
			panel.setContent(vl);
			
			panel.addStyleName(ValoTheme.PANEL_WELL);
			addComponent(panel);

		

		setSpacing(true);
		setMargin(false);



	}
	
	


}
