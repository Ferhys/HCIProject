package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Story {
	
	public String name;
	private ArrayList<Task> taskList;
	public String description;
	public int status;
	public LocalDate startDate;
	public LocalDate endDate;
	
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
	
	public ArrayList<Task> getTaskList(){
		return taskList;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public LocalDate getStartDate() {
		return startDate;
	}
	
	public void setStartDate(LocalDate localDate) {
		this.startDate = localDate;
	}
	
	public LocalDate getEndDate() {
		return endDate;
	}
	
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	
	public Task getLastTask(){
		return taskList.get(taskList.size()-1);
		
	}
	
	public void updateTask(Task task) {
		for (Task task1:taskList) {
			if (task1.getName().equals(task.getName())) {
				task1.setStatus(task.getStatus());
			}
		}
	}
	
}
