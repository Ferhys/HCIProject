package scrumProject.project_CAT_ATTACK;

import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

import kanban.KanbanView;
import login.LoginView;
import projectNav.ProjectNavigatorView;

@SuppressWarnings("serial")
@Theme("mytheme")
public class CAT_ATTACK_MAIN extends UI {

	public static final int TEST_INT = 5;
	
    @Override
    protected void init(VaadinRequest vaadinRequest) {
    	
    	//TODO: make this a method or something -- how to add new users?
    	ArrayList<User> userList = new ArrayList<User>();
    	User user1 = new User();
    	user1.setName("John Doe");
    	user1.setUserName("jdoe@email.com");
    	user1.setPassword("password");
    	userList.add(user1);
    	
    	//set up navigator and views
    	final Navigator navigator = new Navigator(this, this);
    	final KanbanView kanbanView = new KanbanView(navigator, user1);
    	final ProjectNavigatorView projNavView = new ProjectNavigatorView(navigator, kanbanView, user1);
    	kanbanView.setProjNavView(projNavView);
    	final LoginView loginView = new LoginView(navigator, projNavView.VIEW_NAME, userList);
    	
    	navigator.addView("projectNavigatorView", projNavView);
    	navigator.addView("loginView", loginView);
    	navigator.addView("kanbanView", kanbanView);
    	projNavView.authenticate();
        
    }
    

	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = CAT_ATTACK_MAIN.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
	
    public void testMethod() {
    	
    }
	
}