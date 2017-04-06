package scrumProject.project_CAT_ATTACK;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

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
        final HorizontalLayout hl3 = new HorizontalLayout();
        
        // Stuff going in the first horizontal layout. 
        final Label appName = new Label("CATattack");
        appName.setStyleName(ValoTheme.LABEL_H2);
        
        final Label user = new Label("UserName");
        user.setWidth("900px");
        user.addStyleName("v-align-right");
        user.addStyleName(ValoTheme.LABEL_H2);
        
        final Button acc = new Button("Account");
        acc.setHeight("45px");
        
        final Button logOut = new Button("Log Out");
        logOut.setHeight("45px");
        
        
        //stuff for second horizontal layout 
        
        final Label project = new Label("PROJECT NAME ");
        project.addStyleName(ValoTheme.LABEL_H3);
        
        final Label arrow = new Label(" > ");
        arrow.addStyleName(ValoTheme.LABEL_H3);
        
        final Label sprintName = new Label(" SPRINT NAME");
        sprintName.addStyleName(ValoTheme.LABEL_H3);
        sprintName.setWidth("725px");
        
        final Button bDButton = new Button("Burndown Chart");
        bDButton.setHeight("45px");
        bDButton.addClickListener(event -> {
        	
        	Window subWindow = new Window();
        	VerticalLayout subContent = new VerticalLayout();
            subWindow.setContent(subContent);
            subWindow.setHeight("600px");
            subWindow.setWidth("700px");
            // Put some components in it
            subContent.addComponent(new Label("Burndown Chart"));

            // Center it in the browser window
            subWindow.center();

            // Open it in the UI
            addWindow(subWindow);
        	
        });
        //stuff for third horizontal layout
        final Panel column1 = new Panel();
        final Panel column2 = new Panel();
        final Panel column3 = new Panel();
        final Panel column4 = new Panel();
        
        final Label header1 = new Label("STORY");
        final Label header2 = new Label("TO DO");
        final Label header3 = new Label("DOING");
        final Label header4 = new Label("DONE");
        
        final Button plusBtn = new Button("+");
        
        column1.setWidth("300px");
        column2.setWidth("300px");
        column3.setWidth("300px");
        column4.setWidth("300px");
        
        column1.setContent(header1);
        column2.setContent(header2);
        column3.setContent(header3);
        column4.setContent(header4);
        
        column1.setStyleName("v-align-center");
       	column2.setStyleName("v-align-center");
        column3.setStyleName("v-align-center");
        column4.setStyleName("v-align-center");
        
        
        
        hl1.addComponents(appName, user, acc, logOut);
        hl2.addComponents(project, arrow, sprintName, bDButton);
        hl3.addComponents(plusBtn, column1, column2, column3, column4);
        
        mainVL.addComponents(hl1, hl2, hl3);
        
        setContent(mainVL);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = CAT_ATTACK_MAIN.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
