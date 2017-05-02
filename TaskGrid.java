package kanban;

import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import model.Story;
import model.Task;

public class TaskGrid extends GridLayout{

	private final int TODO_COLUMN = 0;
	private final int DOING_COLUMN = 1;
	private final int DONE_COLUMN = 2;
	
	public TaskGrid(Story story, double width) {
		
		int todo = 0;
		int doing = 0;
		int done = 0;
		String panelWidth = width + "px";
		
		Label dummy1 = new Label("");
		dummy1.setWidth(panelWidth);
		Label dummy2 = new Label("");
		dummy2.setWidth(panelWidth);
		Label dummy3 = new Label("");
		dummy3.setWidth(panelWidth);
		
		this.setColumns(3);
		this.setSizeFull();
		
		for (Task task:story.getTaskList()) {
			Panel panel = new Panel();
			VerticalLayout vl = new VerticalLayout();
			
			Label name = new Label(task.getName());
			name.addStyleName(ValoTheme.LABEL_BOLD);
			Label leftHours = new Label("Hours remaining: " + task.getRemainingHours());
			leftHours.addStyleName(ValoTheme.LABEL_SMALL);
			
			vl.addComponents(name, leftHours);
			vl.setSpacing(false);
			panel.setContent(vl);
			panel.setWidth(panelWidth);
			
			if (task.getStatus().equalsIgnoreCase("to do")) {
				this.addComponent(panel, TODO_COLUMN, todo);
				todo++;
			}
			else if (task.getStatus().equalsIgnoreCase("doing")){
				this.addComponent(panel, DOING_COLUMN, doing);
				doing++;
			}
			else if (task.getStatus().equalsIgnoreCase("done")){
				this.addComponent(panel, DONE_COLUMN, done);
				done++;
			}
			this.insertRow(Math.max(todo, Math.max(doing, done)));
		}
		
	}
	
}
