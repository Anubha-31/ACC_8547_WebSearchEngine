package search.web;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.StringTokenizer;

import search.utility.TST;

public class FindWord {

	private static final String DIR_PATH = "TextFiles/";

	/*
	 * public static void main(String[] args) {
	 * 
	 * readAllFiles(); }
	 */

	/**
	 * Method used to read all text files from any directory
	 *
	 */
	public static void readAllFiles() {
		// create instance of directory
		File dir = new File(DIR_PATH);
		Scanner s = new Scanner(System.in);
		String restart;

		// Get list of all the files in form of String Array
		String[] fileNames = dir.list();

		do {
			System.out.println("Enter word to be searched: ");
			String wordToBeSearched = s.nextLine(); // Read user input

			// loop for reading the contents of all the files
			for (String fileName : Objects.requireNonNull(fileNames)) {

				String file = DIR_PATH + fileName;
				File currfile = new File(file);
				if (currfile.exists() && currfile.isFile() && currfile.canRead()) {
					Path path = Paths.get(file);

					numberOfOccurrence(path, wordToBeSearched);
				}
			}
			System.out.println("Do you want to search another word? Y/N");
			restart = s.nextLine();
		} while (restart.equals("y") || restart.equals("Y"));

		if (restart.equals("N") || restart.equals("n"))
			System.out.println("Thank you for using our program, I hope we get 100 marks!");

	}

	/**
	 * Method used to find the number of occurrences of a string/word
	 *
	 * @param path
	 * @param wordToBeSearched
	 */
	private static void numberOfOccurrence(Path path, String wordToBeSearched) {

		int totalNumber;

		TST<Integer> integerTST = new TST<Integer>();

		List<String> lines = null;
		try {
			lines = Files.readAllLines(path, StandardCharsets.ISO_8859_1); // wrapping with try catch if file get null
		} catch (IOException e) {
			e.printStackTrace();
		}

		// running loop until null
		for (String line : Objects.requireNonNull(lines)) {

			StringTokenizer stringTokenizer = new StringTokenizer(line);
			while (stringTokenizer.hasMoreTokens()) {
				String Token = stringTokenizer.nextToken();
				if (integerTST.get(Token) == null) {
					integerTST.put(Token, 1);
				} else {
					integerTST.put(Token, integerTST.get(Token) + 1);
				}
			}
		}

		if (integerTST.get(wordToBeSearched) != null)
			totalNumber = integerTST.get(wordToBeSearched);
		else
			totalNumber = 0;

		// printing total occurrences
		System.out.println("The total number of occurrences of '" + wordToBeSearched + "' in '" + path.getFileName()
				+ "' is " + totalNumber);

	}
}
