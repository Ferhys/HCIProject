package scrumProject.project_CAT_ATTACK;

import java.util.ArrayList;

import model.Project;

public class User {
	public String name;
	public String userName;
	private String password;
	private ArrayList<Project> projectList;
	
	public User() {
		projectList = new ArrayList<Project>();
		
	}
	
	public String getName() {
		return name;
	}
	
	
	public String getUserName() {
		return userName;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String pw) {
		this.password = pw;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public void addProject(Project proj) {
		projectList.add(proj);
	}
	
	public void removeProject(Project proj) {
		projectList.remove(proj);
	}
	
	public Project getProject(int i) {
		return projectList.get(i);
	}
	
	public Project getLastProject() {
		return projectList.get(projectList.size()-1);
	}

	public ArrayList<Project> getProjectList() {
		// TODO Auto-generated method stub
		return projectList;
	}
	
}
