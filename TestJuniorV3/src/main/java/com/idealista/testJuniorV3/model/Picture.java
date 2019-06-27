package com.idealista.testJuniorV3.model;

public class Picture {

	private int id;

	private String url = "";

	private Quality quality = Quality.NONE;

	// Getters and setters

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Quality getQuality() {
		return quality;
	}

	public void setQuality(Quality quality) {
		this.quality = quality;
	}
}
