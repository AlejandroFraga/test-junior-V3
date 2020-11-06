package com.idealista.testJuniorV3.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.idealista.testJuniorV3.util.CollectionUtils;

public class Ad implements Comparable<Ad> {

	private int id;

	private String description = "";

	private Typology typology = Typology.NONE;

	private int houseSize = 0;

	private int gardenSize = 0;

	private List<Picture> pictures = new ArrayList<Picture>();

	private int rating = 0;

	private Date ratingDate = null;

	/**
	 * Constructor
	 */
	public Ad(AdVO adJson) {
		super();
		this.id = adJson.getId();
		this.description = adJson.getDescription();
		this.typology = adJson.getTypology();
		this.houseSize = adJson.getHouseSize();
		this.gardenSize = adJson.getGardenSize();
		this.houseSize = adJson.getHouseSize();
		this.gardenSize = adJson.getGardenSize();
		this.rating = adJson.getRating();
		this.ratingDate = adJson.getRatingDate();
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
		if (description != null) {
			this.description = description;
		}
	}

	public Typology getTypology() {
		return typology;
	}

	public void setTypology(Typology typology) {
		if (typology != null) {
			this.typology = typology;
		}
	}

	public int getHouseSize() {
		return houseSize;
	}

	public void setHouseSize(int houseSize) {
		if (houseSize > 0) {
			this.houseSize = houseSize;
		}
	}

	public int getGardenSize() {
		return gardenSize;
	}

	public void setGardenSize(int gardenSize) {
		if (gardenSize > 0) {
			this.gardenSize = gardenSize;
		}
	}

	public List<Picture> getPictures() {
		return pictures;
	}

	public void setPictures(List<Picture> pictures) {
		if (pictures != null) {
			this.pictures = pictures;
		}
	}

	public void addPicture(Picture picture) {
		pictures.add(picture);
	}

	public void removePicture(Picture picture) {
		pictures.remove(picture);
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		if(rating > 100) {
			this.rating = 100;
		} else if (rating > 0) {
			this.rating = rating;
		}
	}

	public Date getRatingDate() {
		return ratingDate;
	}

	public void setRatingDate(Date ratingDate) {
		this.ratingDate = ratingDate;
	}

	// Other methods

	public boolean hasHouseSize() {
		return houseSize > 0;
	}

	public boolean hasGardenSize() {
		return gardenSize > 0;
	}

	public boolean hasHouseAndGardenSize() {
		return gardenSize > 0;
	}

	public boolean hasSizeData() {
		return (isGarage()
				|| isFlat() && hasHouseSize()
				|| isChalet() && hasHouseAndGardenSize());
	}

	public boolean isGarage() {
		return Typology.GARAGE.equals(typology);
	}

	public boolean isFlat() {
		return Typology.FLAT.equals(typology);
	}

	public boolean isChalet() {
		return Typology.CHALET.equals(typology);
	}

	public boolean isHouse() {
		return isFlat() || isChalet();
	}

	public boolean hasDescription() {
		return !CollectionUtils.isNullOrEmpty(description);
	}

	public boolean hasPictures() {
		return !pictures.isEmpty();
	}

	public boolean isComplete() {
		return hasPictures() && ((hasDescription() && hasSizeData()) || isGarage());
	}

	@Override
	public int compareTo(Ad o) {
		if (o != null) {
			return o.rating - this.rating;
		}
		return 0;
	}
}
