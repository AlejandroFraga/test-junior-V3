package com.idealista.testJuniorV3.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {

	/**
	 * Returns the content of a file as a String
	 * 
	 * @param fileName the name of the file to open and/or create
	 * 
	 * @return the file as a String
	 */
	public static String getStringFile(String fileName) {
		try {
			String fileString = "";
			
			File file = new File(fileName);
			
			// We create the file if it doesn't exist yet
			file.createNewFile();
			
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = reader.readLine()) != null) {
				fileString += line;
			}
			reader.close();
			return fileString;
			
		} catch (Exception e) {
			System.err.format("Exception occurred trying to read '%s'.", fileName);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Writes into a file the content indicated
	 * 
	 * @param fileName the name of the file to write and/or create
	 * @param fileContent the new content of the file
	 */
	public static void writeFile(String fileName, String fileContent) {
		try {
			File file = new File(fileName);

			// We create the file if it doesn't exist yet
			file.createNewFile();
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		    writer.write(fileContent);
		    writer.close();
		    
		} catch (IOException e) {
			System.err.format("Exception occurred trying to write '%s'.", fileName);
			e.printStackTrace();
		}
	}
}
