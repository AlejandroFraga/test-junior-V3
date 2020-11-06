package com.idealista.testJuniorV3.view;

import java.util.Collection;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.idealista.testJuniorV3.model.AdVO;
import com.idealista.testJuniorV3.model.Picture;

/**
 * This class is used to print different elements of the system It's inside the
 * view package because it should be substituted if an GUI is implemented
 */
public class Printer {

	private static Gson gsonPretty = new GsonBuilder().setPrettyPrinting().create();

	/**
	 * Converts the AdVO into a String pretty printed in JSON format
	 * 
	 * @param adVO the adVO to print
	 * @return the adVO printed
	 */
	public static String printAdVO(AdVO adVO) {
		return gsonPretty.toJson(adVO);
	}

	/**
	 * Converts the picture into a String pretty printed in JSON format
	 * 
	 * @param picture the picture to print
	 * @return the picture printed
	 */
	public static String printPicture(Picture picture) {
		return gsonPretty.toJson(picture);
	}

	/**
	 * Converts the Collection of AdsVO into a String pretty printed in JSON format
	 * 
	 * @param adsVO the adsVO to print
	 * @return the adsVO printed
	 */
	public static String printAdsVO(Collection<AdVO> adVOs) {
		return gsonPretty.toJson(adVOs);
	}

	/**
	 * Converts the Collection of pictures into a String pretty printed in JSON format
	 * 
	 * @param pictures the pictures to print
	 * @return the pictures printed
	 */
	public static String printPictures(Collection<Picture> pictures) {
		return gsonPretty.toJson(pictures);
	}

	/**
	 * Prints the Collection of AdsVO into a String pretty printed in JSON format
	 * which have a puntiation of less than 40 so a quality manager can review them
	 * 
	 * @param adsVO the adsVO to print
	 */
	public static void printAdsQuality(List<AdVO> adsVO) {
		System.out.println("------------------------\nADS FOR QUALITY SERVICE:\n------------------------\n\n");

		for (AdVO adVO : adsVO) {
			if (adVO.getRating() < 40) {
				System.out.println(printAdVO(adVO) + "\n");
			}
		}
	}

	/**
	 * Prints the Collection of AdsVO into a String pretty printed in JSON format
	 * which have a puntiation of more than 40 so an user can review them
	 * 
	 * @param adsVO the adsVO to print
	 */
	public static void printAdsUsers(List<AdVO> adsVO) {
		System.out.println("--------------\nADS FOR USERS:\n--------------\n\n");

		for (AdVO adVO : adsVO) {
			if (adVO.getRating() >= 40) {
				System.out.println(printAdVO(adVO) + "\n");
			}
		}
	}
}
