package kanban;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
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
	
	public TaskGrid(Story story, KanbanView parentView, double width) {
		
		int todo = 0;
		int doing = 0;
		int done = 0;
		String panelWidth = width + "px";	
		
		this.setColumns(3);
		this.setSizeFull();
		this.setMargin(false);
		this.setSpacing(false);
		
		for (Task task:story.getTaskList()) {
			Panel panel = new Panel();
			HorizontalLayout hl1 = new HorizontalLayout();
			HorizontalLayout hl2 = new HorizontalLayout();
			VerticalLayout vl = new VerticalLayout();
			
			Label name = new Label(task.getName());
			name.addStyleName(ValoTheme.LABEL_BOLD);
			Label leftHours = new Label("Hours remaining: " + task.getRemainingHours());
			leftHours.addStyleName(ValoTheme.LABEL_SMALL);
			
			ComboBox<String> combo = new ComboBox<String>("Status");
			combo.setItems("TO DO", "DOING", "DONE");
			Button update = new Button("Update");
			update.addClickListener(e -> {
				task.setStatus(combo.getValue());
				story.updateTask(task);
				parentView.updateTask(story);
			});
			combo.addStyleName(ValoTheme.COMBOBOX_SMALL);
			update.addStyleName(ValoTheme.BUTTON_TINY);
			
			hl1.addComponents(name, leftHours);
			hl2.addComponents(combo, update);
			hl2.setComponentAlignment(update, Alignment.BOTTOM_RIGHT);
			vl.addComponents(hl1, hl2);
			vl.setSpacing(false);
			panel.setContent(vl);
			panel.setWidth(hl2.getWidth() + "px");
			
			if (task.getStatus().equalsIgnoreCase("to do")) {
				this.addComponent(panel, TODO_COLUMN, todo);
				this.setComponentAlignment(panel, Alignment.MIDDLE_LEFT);
				todo++;
			}
			else if (task.getStatus().equalsIgnoreCase("doing")){
				this.addComponent(panel, DOING_COLUMN, doing);
				//this.setComponentAlignment(panel, Alignment.MIDDLE_LEFT);
				doing++;
			}
			else if (task.getStatus().equalsIgnoreCase("done")){
				this.addComponent(panel, DONE_COLUMN, done);
				this.setComponentAlignment(panel, Alignment.MIDDLE_RIGHT);
				done++;
			}
			combo.setValue(task.getStatus());
			this.insertRow(Math.max(todo, Math.max(doing, done)));
		}
		
	}
	
}
