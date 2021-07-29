package search.web;

import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import search.utility.Trie;
import search.utility.TrieNode;

public class SpellCorrector {

	private static final String DIR_PATH = "TextFiles/";

	Trie trie = new Trie();
	Map<String, Integer> dictWordCount = new HashMap<>();

	public void loadSpellCorrector() {

		File fileFolder = new File(DIR_PATH);

		File[] files = fileFolder.listFiles();

		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {

				try {
					storeInDictionary(files[i]);
				} catch (IOException e) {

					e.printStackTrace();
				}
			}
		}
	}

	public void storeInDictionary(File fileName) throws IOException {

		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferReader = new BufferedReader(fileReader);

			String line = null;

			while ((line = bufferReader.readLine()) != null) {
				String word = line.toLowerCase();

				if (!line.contains(" ")) {
					word=word.toLowerCase();
					dictWordCount.put(word, dictWordCount.getOrDefault(word, 0) + 1);
					trie.addWord(word);
				} else {
					String[] sWords = line.split("\\s");
					for (String sWord : sWords) {
						sWord=sWord.toLowerCase();
						dictWordCount.put(sWord, dictWordCount.getOrDefault(sWord, 0) + 1);
						trie.addWord(sWord);
					}
				}
			}

			fileReader.close();
			bufferReader.close();
		} catch (Exception e) {
			// System.out.println(e);
		}
	}

	public Set<String> findSimilarWord(String sInput) {
		TreeSet<String> result = new TreeSet<String>();
		if (sInput.length() == 0 || sInput == null)
			return result;

		String sLowerInput = sInput.toLowerCase();
		TreeMap<Integer, String> m3 = null;

		TreeMap<Integer, TreeMap<Integer, TreeSet<String>>> map = new TreeMap<>();

		TrieNode node = trie.search(sLowerInput);

		if (node == null) {
			for (String word : dictWordCount.keySet()) {
				int distance = search.utility.Sequences.editDistance(word, sLowerInput);
				TreeMap<Integer, TreeSet<String>> similarWords = map.getOrDefault(distance, new TreeMap<>());

				int iFrequency = dictWordCount.get(word);
				TreeSet<String> set = similarWords.getOrDefault(iFrequency, new TreeSet<>());

				set.add(word);
				similarWords.put(iFrequency, set);
				map.put(distance, similarWords);
			}

			// Get a set of the entries
			Set set = map.entrySet();

			// Get an iterator

			Iterator it = set.iterator();
			int count = 0;
			while (it.hasNext()) {
				count++;
				if (count == 3) {
					break;
				}
				Map.Entry me = (Map.Entry) it.next();
				System.out.print("Key is: " + me.getKey() + " & ");
				System.out.println("Value is: " + me.getValue());
				if (me != null && me.getKey() != null && me.getValue() != null) {
					m3.put((Integer) me.getKey(), me.getValue().toString());
				}
				// m3= (TreeMap<Integer, TreeSet<String>>) me.getValue();
			}

//			result = map.firstEntry().getValue().lastEntry().getValue().first();
		}

		System.out.println(m3.toString());
//		else if (node !=null) 
//			result = sLowerInput;

		return null;
	}

	public ArrayList autocomplete(String sInput) {
		ArrayList<String> ab = new ArrayList<String>();

		if (sInput.length() == 0 || sInput == null)
			return ab;

		String sLowerInput = sInput.toLowerCase();
		

		TrieNode node = trie.search(sLowerInput);
//		sLowerInput=" "+sLowerInput;

		if (node == null) {
			for (String word : dictWordCount.keySet()) {
				System.out.println(" word is"+word+"");
				if(!word.isEmpty())
				{
				if(word.startsWith(sLowerInput))
				{
					ab.add(word);
				}
				}
			}
		}

		return ab;

	}

}
