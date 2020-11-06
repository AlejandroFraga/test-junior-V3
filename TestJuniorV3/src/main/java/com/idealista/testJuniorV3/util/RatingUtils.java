package com.idealista.testJuniorV3.util;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.idealista.testJuniorV3.model.Ad;
import com.idealista.testJuniorV3.model.Picture;

public abstract class RatingUtils {

	/**
	 * Maximal rating possible
	 */
	private static final int MAX_RATING = 100;

	/**
	 * Minimal rating possible
	 */
	private static final int MIN_RATING = 0;

	/**
	 * Updates the ratings of a list of ads
	 * 
	 * @param ads list of ads to update its ratings
	 */
	public static void updateRatings(List<Ad> ads) {
		for (Ad ad : ads) {
			int value = RatingUtils.getRating(ad);
			if (ad.getRatingDate() == null || ad.getRating() != value) {
				ad.setRating(value);
				ad.setRatingDate(new Date());
			}
		}
		Collections.sort(ads);
	}

	/**
	 * Gets the rating from an ad
	 * 
	 * @param ad to be rated
	 * 
	 * @return the rate
	 */
	public static int getRating(Ad ad) {
		int rating = 0;

		if (ad != null) {
			rating += picturesRating(ad) + descriptionRating(ad);

			if (ad.isComplete()) {
				rating += 40;
			}
		}
		return correctRating(rating);
	}

	/**
	 * Gets the rating from an ad, for its pictures
	 * 
	 * @param ad to be rated
	 * 
	 * @return the rate
	 */
	private static int picturesRating(Ad ad) {
		int rating = 0;

		if (ad != null) {
			if (!ad.hasPictures()) {
				rating -= 10;

			} else {
				for (Picture picture : ad.getPictures()) {
					if (picture != null) {
						switch (picture.getQuality()) {
						case HD:
							rating += 20;
							break;
						default:
							rating += 10;
							break;
						}
					}
				}
			}
		}
		return rating;
	}

	/**
	 * Gets the rating from an ad, for its description
	 * 
	 * @param ad to be rated
	 * 
	 * @return the rate
	 */
	private static int descriptionRating(Ad ad) {
		int rating = 0;

		if (ad != null && ad.hasDescription()) {
			rating += 5 + appearancesRating(ad) + wordsCountRating(ad);
		}
		return rating;
	}

	/**
	 * Gets the rating from an ad, for its appearances
	 * 
	 * @param ad to be rated
	 * 
	 * @return the rate
	 */
	private static int appearancesRating(Ad ad) {
		int rating = 0;
		
		if(ad != null && ad.hasDescription()) {
			String[] wordsToCount = { "Luminoso", "Nuevo", "Céntrico", "Reformado", "Ático" };
			int countAppearances = numberOfAppearances(ad.getDescription(), wordsToCount);
			rating += 5 * countAppearances;
		}
		return rating;
	}

	/**
	 * Gets the rating from an ad, for its words count
	 * 
	 * @param ad to be rated
	 * 
	 * @return the rate
	 */
	private static int wordsCountRating(Ad ad) {
		int rating = 0;
		
		int countWords = countWordsUsingSplit(ad.getDescription());
		switch (ad.getTypology()) {
		case FLAT:
			if (countWords > 50) {
				rating += 30;
			} else if (countWords > 20) {
				rating += 10;
			}
			break;

		case CHALET:
			if (countWords > 50) {
				rating += 20;
			}
			break;

		default:
			break;
		}
		
		return rating;
	}

	/**
	 * Gets the number of appearances
	 * 
	 * @param string containing the appearances
	 * @param words strings to be counted
	 * 
	 * @return the number of appearances
	 */
	private static int numberOfAppearances(String string, String... words) {
		int count = 0;
		if (words != null) {
			for (String word : words) {
				if (!CollectionUtils.isNullOrEmpty(word)) {

					// FIXME External
					Pattern p = Pattern.compile(word);
					Matcher m = p.matcher(string);
					while (m.find()) {
						count++;
					}
				}
			}
		}
		return count;
	}

	/**
	 * Gets the number of words
	 * 
	 * @param string containing the words
	 * 
	 * @return the number of words
	 */
	private static int countWordsUsingSplit(String string) {
		if (CollectionUtils.isNullOrEmpty(string)) {
			return 0;
		}

		String[] words = string.split("\\s+");
		return words.length;
	}

	/**
	 * Normalizes the rating to be between the maximum and the minimum
	 * 
	 * @param the rating to be corrected
	 * 
	 * @return the rating corrected
	 */
	private static int correctRating(int rating) {
		if (rating < MIN_RATING) {
			return MIN_RATING;
		} else if (rating > MAX_RATING) {
			return MAX_RATING;
		}
		return rating;
	}
}
