package login;


import java.util.ArrayList;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import scrumProject.project_CAT_ATTACK.User;

public class LoginView extends VerticalLayout implements View {

	protected static final String VIEW_NAME = "loginView";
	
	private final TextField userField;
	private final PasswordField pwField;
	private final Navigator navigator;
	private final String nextView;
	private final ArrayList<User> userList;
	
	public LoginView(Navigator navigator, String nextView, ArrayList<User> userList) {
		userField = new TextField("User: ");
		pwField = new PasswordField("Password: ");
		this.navigator = navigator;
		this.nextView = nextView;
		this.userList = userList;
		
		initialize();
	}

	private void initialize() {
		final Label welcomeLabel = new Label("THIS IS LOGIN SCREEN");
		
		User logMeIn = new User();
		Label userStatus = new Label();
		Binder<User> binder = new Binder<User>();
		Button enter = new Button ("Log In");
		
		//Bind and validate user name
		binder.forField(userField).withValidator(
				userName -> userName.length() >= 3, "User name must be at least 3 characters")
				.withValidationStatusHandler(status -> {
					userStatus.setValue(status.getMessage().orElse(""));
					userStatus.setVisible(status.isError());
				})
				.bind(User::getUserName, User::setUserName);
		binder.bind(pwField, User::getPassword, User::setPassword);

		//Pass
		enter.addClickListener(e -> { 
			try {
				binder.writeBean(logMeIn);
				//search userList for person
				for (int i = 0; i < userList.size(); i++) {
					if (userList.get(i).getUserName().equals(logMeIn.getUserName())) {
						//find password
						if (userList.get(i).getPassword().equals(logMeIn.getPassword())){
							//ENTER!!
							Notification.show("YOU MAY ENTER");
							navigator.navigateTo(nextView);
						}
						else {
							Notification.show("Incorrect Password.");
						}
					}
					else {
						Notification.show("That user doesn't exist.");
					}
				}
			}
			catch (ValidationException event) {
				Notification.show("NOPE");
			}
		});
		
		addComponents(welcomeLabel, userField, userStatus, pwField, enter);
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		userField.focus();
		
	}
	
}
