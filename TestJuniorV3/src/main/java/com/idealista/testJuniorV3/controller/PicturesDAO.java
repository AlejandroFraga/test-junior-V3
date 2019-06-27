package com.idealista.testJuniorV3.controller;

import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.idealista.testJuniorV3.model.Ad;
import com.idealista.testJuniorV3.model.Picture;
import com.idealista.testJuniorV3.util.FileUtils;
import com.idealista.testJuniorV3.view.Printer;

public class PicturesDAO {

	/**
	 * Name of the resource file from which new information can be read
	 */
	private String readFileName = null;

	/**
	 * Name of the resource file that stores all the pictures information
	 */
	private String databaseFileName = "pictures";

	/**
	 * Gson class for Json objects conversions
	 */
	private Gson gson;

	/**
	 * HashMap of pictures in the system
	 */
	private HashMap<Integer, Picture> pictures = new HashMap<Integer, Picture>();

	/**
	 * Constructor
	 */
	PicturesDAO(Gson gson) {
		this.gson = gson;
	}

	/**
	 * Loads the data of the system
	 */
	void initialize() {

		// We load all the pictures from the DB into the HashMap
		initializeFromDB();

		// We repeat the same process but with readFileName, to load the new pictures
		// If a read file is indicated
		if(readFileName != null) {
			initializeFromFile();
		}

		// We save all the pictures again in the resources file
		save();
	}

	/**
	 * Loads the data of the system and from the file indicated in args[1]
	 */
	void initialize(String readFileName) {
		this.readFileName = readFileName;
		initialize();
	}

	/**
	 * Function that loads all the data from the database
	 */
	private void initializeFromDB() {
		// We load the file from resources as String
		// This String can be converted into a array of objects through Gson
		// as they are formated with JSON
		String picturesDBString = FileUtils.getStringFile(databaseFileName);
		Picture[] picturesDB = gson.fromJson(picturesDBString, Picture[].class);

		// We put or update all of them inside the HashMap
		if (picturesDB != null) {
			for (Picture picture : picturesDB) {
				putOrUpdatePicture(picture);
			}
		}
	}

	/**
	 * Function that loads all the data from the file indicated
	 */
	private void initializeFromFile() {
		// We load the file from resources as String
		// This String can be converted into a array of objects through Gson
		// as they are formated with JSON
		String picturesReadString = FileUtils.getStringFile(readFileName);
		Picture[] picturesRead = gson.fromJson(picturesReadString, Picture[].class);

		// We put or update all of them inside the HashMap
		if (picturesRead != null) {
			for (Picture picture : picturesRead) {
				putOrUpdatePicture(picture);
			}
		}
	}

	/**
	 * Function that retrieves a picture by it's id
	 * 
	 * @param pictureId id of the picture
	 * @return picture with the id passed
	 */
	private Picture getPicture(Integer pictureId) {
		if (pictureId != null) {
			return pictures.get(pictureId);
		}
		return null;
	}

	/**
	 * Function that puts or updates the picture depending on if it was already
	 * inside the HashMap or not
	 * 
	 * @param picture to put or update
	 */
	private void putOrUpdatePicture(Picture picture) {
		if (picture != null) {
			Integer pictureId = picture.getId();
			Picture pictureStored = getPicture(pictureId);

			if (pictureStored != null) {
				pictures.remove(pictureId);
			}
			pictures.put(pictureId, picture);
		}
	}

	/**
	 * Assign pictures to an ad from their ids
	 */
	void assignPictures(Ad ad, List<Integer> picturesIds) {
		for (Integer pictureId : picturesIds) {
			Picture picture = getPicture(pictureId);
			if (picture != null) {
				ad.addPicture(picture);
			}
		}
	}

	/**
	 * Save all the ads in the resources file
	 */
	void save() {
		FileUtils.writeFile(databaseFileName, Printer.printPictures(pictures.values()));
	}
}