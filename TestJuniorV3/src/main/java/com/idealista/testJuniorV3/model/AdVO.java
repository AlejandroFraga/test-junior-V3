package com.idealista.testJuniorV3.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdVO {

	private int id;

	private String description = "";

	private Typology typology = Typology.NONE;

	private int houseSize = 0;

	private int gardenSize = 0;

	private List<Integer> pictures = new ArrayList<Integer>();
	
	private int rating = 0;
	
	private Date ratingDate = null;

	/**
	 * Constructor
	 */
	public AdVO(Ad ad) {
		super();
		this.id = ad.getId();
		this.description = ad.getDescription();
		this.typology = ad.getTypology();
		this.houseSize = ad.getHouseSize();
		this.gardenSize = ad.getGardenSize();
		for(Picture picture : ad.getPictures()) {
			this.pictures.add(picture.getId());
		}
		this.rating = ad.getRating();
		this.ratingDate = ad.getRatingDate();
	}

	// Getters and setters

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Typology getTypology() {
		return typology;
	}

	public void setTypology(Typology typology) {
		this.typology = typology;
	}

	public int getHouseSize() {
		return houseSize;
	}

	public void setHouseSize(int houseSize) {
		this.houseSize = houseSize;
	}

	public int getGardenSize() {
		return gardenSize;
	}

	public void setGardenSize(int gardenSize) {
		this.gardenSize = gardenSize;
	}

	public List<Integer> getPictures() {
		return pictures;
	}

	public void setPictures(List<Integer> pictures) {
		if (pictures != null) {
			this.pictures = pictures;
		}
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public Date getRatingDate() {
		return ratingDate;
	}

	public void setRatingDate(Date ratingDate) {
		this.ratingDate = ratingDate;
	}
}
