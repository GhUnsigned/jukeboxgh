package com.example.jukeboxghTask;

public class Artists_Items {
	private int artist_id;
	private String name = "";
	private String genre = "";
	private boolean checked = false;
	
	public Artists_Items() {
		
	}
	
	public Artists_Items(int artist_id, String name, String genre, boolean checked) {
		this.name = name;
		this.checked = checked;
		this.genre = genre;
		this.artist_id = artist_id;
	}
	
	public String getGenre() {
		return this.genre;
	}	
	//  Haven't done a setter yet
	
	public int getID() {
		return this.artist_id;
	}
	// Haven't done a setter yet 
	
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
