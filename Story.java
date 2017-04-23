package model;

import java.util.ArrayList;

public class Story {
	
	public String name;
	private ArrayList<Task> taskList;
	public String description;
	
	public Story(){
		taskList = new ArrayList<>();
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public void addTask(Task task) {
		taskList.add(task);
	}
	
	public void deleteTask(Task task) {
		taskList.remove(task);
	}
	
	public Task getTask(int i) {
		return taskList.get(i);
	}
	
}
