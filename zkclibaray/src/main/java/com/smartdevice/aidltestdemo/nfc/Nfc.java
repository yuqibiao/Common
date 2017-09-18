package com.smartdevice.aidltestdemo.nfc;

import android.R.integer;
 

public class Nfc {
	private int id ;
	public int type;
	private String content;
	private int SectorId;
	
	public int getSectorId() {
		return SectorId;
	}
	public void setSectorId(int sectorId) {
		SectorId = sectorId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Nfc(int id, int type, String content, int sectorId) {
		super();
		this.id = id;
		this.type = type;
		this.content = content;
		SectorId = sectorId;
	}
	 
	
}
