package projectNav;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import model.Project;
import model.Sprint;

public class SprintAddWindow extends Window{
	Sprint s; 
	final TextField sprintName;
	final DateField startDate;
	final DateField endDate;
	final ComboBox<Project> combo;
	int temp;
	
	public SprintAddWindow(ArrayList<Project> projectList){
		VerticalLayout mainVL = new VerticalLayout();
		sprintName = new TextField("Sprint Name: ");
		startDate = new DateField();
		endDate = new DateField();
		startDate.setValue(LocalDate.now());
		
//		if (projectList.size() > 0) {
//			Label test = new Label(projectList.get(0).getName());
//		}
		
		s = new Sprint();
		Button enter = new Button("Add Sprint");
		
		combo = new ComboBox<Project>();
		List projects = projectList;
//		List projectNames = new ArrayList<String>();
//		for (Project p : projectList) {
//			projectNames.add(p.getName());
//		}
		combo.setItems(projects);
		combo.setItemCaptionGenerator(Project::getName);
		
		enter.addClickListener(e-> {
			s.setName(sprintName.getValue());
			s.setStartDate(startDate.getValue());
			s.setEndDate(endDate.getValue());
			/*
			for(int i = 0; i < projectList.size(); i++){
				if(combo.getValue().getName() == projectList.get(i).getName()){
					temp = i;
				}
			}
			*/
			
			
			close();
		});
		
		mainVL.addComponents(sprintName, startDate, endDate, combo, enter, test);
		setContent(mainVL);
	}
	
	public Sprint getSprint(){
		return s;
	}
	
	public int returnProjectIndex(){
		return temp;
	}

}