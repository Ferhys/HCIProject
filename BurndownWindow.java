package burndown;

import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import model.Sprint;

public class BurndownWindow extends Window {

	public BurndownWindow(Sprint sprint) {
		final VerticalLayout vl = new VerticalLayout();
		vl.setMargin(true);
		
		//Chart chart = new Chart();
		
		
		
		
		setContent(vl);
	}
}
