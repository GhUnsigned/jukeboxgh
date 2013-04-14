package com.example.jukeboxghTask;

public class ArtistDetails {
	public int icon;
	public String artistName;
	public String songTitle;
	String songURL;
	
	
	public int getIcon(){
		return icon;
	}
	public void setIcon(int icon){
		this.icon=icon;
	}
	
	public String getArtistName(){
		return artistName;
	}
	
	public void setSongURL(String songURL){
		this.songURL=songURL;
	}
	public String getSongURL(){
		return songURL;
	}
	
	public void setArtistName(String artistName){
		this.artistName=artistName;
	}
	
	public String getSongTittel(){
		return songTitle;
	}
	
	public void setSongTitle(String songTitle){
		this.songTitle=songTitle;
	}
}
