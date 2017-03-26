package Hci.myapplication;
import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * @author stephsparks
 *
 * This UI is the application entry point. A UI may either represent a browser window
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();

        final TextField name = new TextField();
        name.setCaption("SCRUM TOOL FOR THE WIN.\n What's your name?");

        Button button = new Button("Are you a winner?");
        button.addClickListener( e -> {
            layout.addComponent(new Label("yes, " + name.getValue() + ", you are a winner. In fact, you win the lottery."));
            Button button2 = new Button("How much did you win?");
            button2.addClickListener(e2 -> {
            	layout.addComponent(new Label("You won all the money in the world"));;
            });
            layout.addComponent(button2);
        });

        Button button3 = new Button("Or ARE YOU A LOSER!");
        button3.addClickListener(e3->{
            layout.addComponent(new Label(name.getValue() + ", you need to work on your self-esteem."
                                  + "You're baaaaeautiful, and still a winner. No matter what you do."));
             });
        layout.addComponents(name, button, button3);

        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
