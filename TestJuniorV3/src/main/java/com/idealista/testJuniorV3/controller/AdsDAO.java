package com.idealista.testJuniorV3.controller;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.idealista.testJuniorV3.model.Ad;
import com.idealista.testJuniorV3.model.AdVO;
import com.idealista.testJuniorV3.util.FileUtils;
import com.idealista.testJuniorV3.util.RatingUtils;
import com.idealista.testJuniorV3.view.Printer;

public class AdsDAO {

	/**
	 * Name of the resource file from which new information can be read
	 */
	private String readFileName = null;

	/**
	 * Name of the resource file that stores all the ads information
	 */
	private String databaseFileName = "ads";

	/**
	 * Facade class
	 */
	private Facade facade;

	/**
	 * Gson class for Json objects conversions
	 */
	private Gson gson;

	/**
	 * List of ads in the system
	 */
	private List<Ad> ads = new ArrayList<Ad>();

	/**
	 * List of ads in the system
	 */
	private List<AdVO> adsVO = new ArrayList<AdVO>();

	/**
	 * Constructor
	 */
	public AdsDAO(Facade facade, Gson gson) {
		this.facade = facade;
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
		
		// We update the ratings and the AdVO List
		updateRatingsAndAdsVO();

		// We save all the pictures again in the resources file
		save();
	}

	/**
	 * Loads the data of the system and from the file indicated in args[0]
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
		String adsVODBString = FileUtils.getStringFile(databaseFileName);
		AdVO[] adsVODB = gson.fromJson(adsVODBString, AdVO[].class);

		// We get all the ads and assign them their pictures by the ids in the VO
		if (adsVODB != null) {
			for (AdVO adVO : adsVODB) {
				Ad ad = new Ad(adVO);
				facade.assignPictures(ad, adVO.getPictures());
				addOrUpdateAd(ad);
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
		String adsVOReadString = FileUtils.getStringFile(readFileName);
		AdVO[] adsVORead = gson.fromJson(adsVOReadString, AdVO[].class);

		// We get all the ads and assign them their pictures by the ids in the VO
		if (adsVORead != null) {
			for (AdVO adVO : adsVORead) {
				Ad ad = new Ad(adVO);
				facade.assignPictures(ad, adVO.getPictures());
				addOrUpdateAd(ad);
			}
		}
	}
	


	/**
	 * Updates the ratings and the AdVO List
	 */
	private void updateRatingsAndAdsVO() {
		// We update the ratings in the system
		// This would be called when a change in a ad is detected
		RatingUtils.updateRatings(ads);
	
		// We do the inverse process so we get again VO,
		// which can be converted and saved with Gson with JSON format
		for (Ad ad : ads) {
			addOrUpdateAdVO(new AdVO(ad));
		}
	}

	/**
	 * Function that retrieves a ad by it's id
	 * 
	 * @param adId id of the ad
	 * @return ad with the id passed
	 */
	private Ad getAd(Integer adId) {
		if (adId != null) {
			for (Ad ad : ads) {
				if (ad != null && ad.getId() == adId) {
					return ad;
				}
			}
		}
		return null;
	}

	/**
	 * Function that retrieves a adVO by it's id
	 * 
	 * @param adVOId id of the ad
	 * @return adVO with the id passed
	 */
	private AdVO getAdVO(Integer adVOId) {
		if (adVOId != null) {
			for (AdVO adVO : adsVO) {
				if (adVO != null && adVO.getId() == adVOId) {
					return adVO;
				}
			}
		}
		return null;
	}

	/**
	 * Function that puts or updates the ad depending on if it was already inside
	 * the List or not
	 * 
	 * @param ad to put or update
	 */
	private void addOrUpdateAd(Ad ad) {
		if (ad != null) {
			Integer adId = ad.getId();
			Ad adStored = getAd(adId);

			if (adStored != null) {
				ads.remove(adStored);
			}
			ads.add(ad);
		}
	}

	/**
	 * Function that puts or updates the adVO depending on if it was already inside
	 * the List or not
	 * 
	 * @param adVO to put or update
	 */
	private void addOrUpdateAdVO(AdVO adVO) {
		if (adVO != null) {
			Integer adVOId = adVO.getId();
			AdVO adVOStored = getAdVO(adVOId);

			if (adVOStored != null) {
				adsVO.remove(adVOStored);
			}
			adsVO.add(adVO);
		}
	}

	/**
	 * Get all the adsVO
	 * 
	 * @return the adsVO
	 */
	List<AdVO> getAdsVO() {
		return adsVO;
	}

	/**
	 * Save all the ads in the resources file
	 */
	void save() {
		FileUtils.writeFile(databaseFileName, Printer.printAdsVO(adsVO));
	}
}