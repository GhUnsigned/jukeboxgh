package com.example.jukeboxghTask;

public class Artists_Items {
	private String name = "";
	private boolean checked = false;
	
	public Artists_Items() {
		
	}
	
	public Artists_Items(String name, boolean checked) {
		this.name = name;
		this.checked = checked;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isChecked() {
		return this.checked;
	}
	
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	public String toString() {
		return name;
	}
	
	public void toggleChecked() {
		checked = !checked;
	}
}
