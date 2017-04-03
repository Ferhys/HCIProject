package scrumProject.project_CAT_ATTACK;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class CAT_ATTACK_MAIN extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout mainVL = new VerticalLayout();
        final HorizontalLayout hl1 = new HorizontalLayout();
        final HorizontalLayout hl2 = new HorizontalLayout();
        
        final Label appName = new Label("CATattack");
        hl1.addComponent(appName);
        
               final Panel column1 = new Panel();
        final Panel column2 = new Panel();
        final Panel column3 = new Panel();
        final Panel column4 = new Panel();
        
        final Label header1 = new Label("Story");
        final Label header2 = new Label("TO DO");
        final Label header3 = new Label("DOING");
        final Label header4 = new Label("DONE");
        
        column1.setWidth("300px");
        column2.setWidth("300px");
        column3.setWidth("300px");
        column4.setWidth("300px");
        
        column1.setContent(header1);
        column2.setContent(header2);
        column3.setContent(header3);
        column4.setContent(header4);
        
        hl2.addComponents(column1, column2, column3, column4);
        
        final GridLayout grid1 = new GridLayout();
       column1.setWidth(8, Unit.CM);
       column2.setWidth(8, Unit.CM);
       column3.setWidth(8, Unit.CM);
       column4.setWidth(8, Unit.CM);
        
        mainVL.addComponents(hl1, hl2);
        
        setContent(mainVL);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = CAT_ATTACK_MAIN.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
