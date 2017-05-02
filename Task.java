package model;

public class Task {

	public String name;
	public double plannedHours;
	public double completedHours;
	public String status;
	
	public Task() {
		
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPlannedHours() {
		return plannedHours;
	}

	public void setPlannedHours(double plannedHours) {
		this.plannedHours = plannedHours;
	}

	public double getCompletedHours() {
	
		return completedHours;
	}

	public void setCompletedHours(double completedHours) {
		this.completedHours = completedHours;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		
		this.status = status;
	}
	
	public double getRemainingHours() {
		return plannedHours - completedHours;
	}


}
