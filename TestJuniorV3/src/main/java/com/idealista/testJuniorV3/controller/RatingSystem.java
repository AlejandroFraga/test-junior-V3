package com.idealista.testJuniorV3.controller;

public class RatingSystem {

	public static void main(String[] args) {
		
		//We create and initialize the facade
		final Facade facade = new Facade();
		facade.initialize(args);

		//We print the ads for the quality managers and the users
		facade.printAdsQuality();
		facade.printAdsUsers();
	}
}