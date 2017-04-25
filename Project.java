package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Project {
	public String name;
	private String description;
	public ArrayList<Sprint> sprintList;
	//TODO: add User class
	//public ArrayList<User> userList;
	private int status;
	private LocalDate startDate;
	
	public Project() {
		
	}
	
	public Project(String name) {
		this.name = name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public int getStatus() {
		return status;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void addSprint(Sprint sprint) {
		sprintList.add(sprint);
	}
	
	public void deleteSprint(Sprint sprint) {
		sprintList.remove(sprint);
	}
	
	public void setStartDate(LocalDate localDate) {
		this.startDate = localDate;
	}
	
	public LocalDate getStartDate(){
		return startDate;
	}
	
	public ArrayList<Sprint> getSprintList(){
		return sprintList;
	}
	
	public Sprint getSprint(int index){
		return sprintList.get(index);
	}
}
