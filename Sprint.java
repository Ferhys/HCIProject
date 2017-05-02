package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Sprint {

	public String name;
	public String description;
	public int status;
	public LocalDate startDate;
	public LocalDate endDate;
	private ArrayList<Story> storyList;
	
	public Sprint() {
		storyList = new ArrayList<>();
		
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
	
	public void setEndDate(LocalDate localDate) {
		this.endDate = localDate;
	}

	public void addStory(Story story) {
		storyList.add(story);
	}
	
	public void deleteStory(Story story) {
		storyList.remove(story);
	}
	
	public Story getStory(int i) {
		return storyList.get(i);
	}
	
	public int getStoryIndex(String name){
		for(int i = 0; i < storyList.size(); i++){
			if(name == storyList.get(i).getName()){
				return i;
			}
			
		}
		return -1;
	}

	public ArrayList<Story> getStoryList() {
		return storyList;
	}
	
	public void updateStory(int index, Story story){
		storyList.add(index, story);
		storyList.remove(index+1);
	}
	
}
