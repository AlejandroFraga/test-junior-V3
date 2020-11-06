package com.idealista.testJuniorV3.controller;

import java.util.List;

import com.google.gson.Gson;
import com.idealista.testJuniorV3.model.Ad;
import com.idealista.testJuniorV3.model.AdVO;
import com.idealista.testJuniorV3.util.FileUtils;
import com.idealista.testJuniorV3.view.Printer;

/**
 * Facade that implements the facade pattern
 */
public class Facade {

	/**
	 * Files methods
	 */
	FileUtils fileUtils = new FileUtils();

	/**
	 * Gson class for Json objects conversions
	 */
	Gson gson = new Gson();

	/**
	 * Data access object for the ads
	 */
	AdsDAO adsDAO = new AdsDAO(this, gson);

	/**
	 * Data access object for the pictures
	 */
	PicturesDAO picturesDAO = new PicturesDAO(gson);

	/**
	 * Method that initializes both DAOs
	 */
	public void initialize(String[] args) {
		int argsLength = args.length;

		if (argsLength == 1) {
			picturesDAO.initialize();
			adsDAO.initialize(args[0]);
			
		} else if (argsLength == 2) {
			picturesDAO.initialize(args[1]);
			adsDAO.initialize(args[0]);
			
		} else {
			picturesDAO.initialize();
			adsDAO.initialize();
		}
	}

	/**
	 * Method that returns all the adsVO stored in the system
	 * 
	 * @return the adsVO
	 */
	private List<AdVO> getAdsVO() {
		return adsDAO.getAdsVO();
	}

	/**
	 * Method that assigns all the pictures associated to the list of ids to the ad
	 * indicated
	 * 
	 * @return the pictures
	 */
	void assignPictures(Ad ad, List<Integer> pictures) {
		picturesDAO.assignPictures(ad, pictures);
	}

	/**
	 * Print all the ads for the quality manager, showing the irrelevant ones
	 */
	public void printAdsQuality() {
		Printer.printAdsQuality(getAdsVO());
	}

	/**
	 * Print all the ads for the users, not showing the irrelevant ones
	 */
	public void printAdsUsers() {
		Printer.printAdsUsers(getAdsVO());
	}
}