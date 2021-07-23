package search.web;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import search.utility.In;

public class Cache {

	public static void Addcache(String Url) throws IOException
	{
		FileWriter fstream = new FileWriter("Cache.txt");
		BufferedWriter out = new BufferedWriter(fstream);
		out.append(Url);
		out.newLine();
		out.flush();
		fstream.flush();
			
	}
	
	public static void deleteCache(String Url) throws IOException
	{
		File fstream = new File("CacheTemp.txt");
		BufferedWriter out = new BufferedWriter(new FileWriter(fstream));
		try {
			In in = new In("Cache.txt");
			//System.out.println(in.toString());
			while (!in.isEmpty()) {
				String s = in.readLine();
			//	System.out.println(s);
				if (!s.equals(Url)) {
					out.append(Url);
					out.newLine();
				}
			}
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}

		out.flush();
		out.close();
		
		FileWriter fstream1 = new FileWriter("Cache.txt");
		BufferedWriter out1 = new BufferedWriter(fstream1);
		
		try {
			In in = new In("CacheTemp.txt");
		//	System.out.println(in.toString());
			while (!in.isEmpty()) {
				String s = in.readLine();
		
					out1.write(Url);
				
			}
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}
		out1.flush();
		fstream1.flush();
		out1.close();
		
		File f = new File("TextFiles//");
		FileUtils.cleanDirectory(f);
		f.delete();
		System.out.println("Cache has been sucesfully deleted");
	}   
	
	public static Boolean isAvailable(String Url)
	{
	    Boolean flag =false;
		try {
			In in = new In("Cache.txt");

			while (!in.isEmpty()) {
				String s = in.readLine();
				if (s.equals(Url))
					flag =true;
			}
		//	System.out.println();
		} catch (Exception e) {
			System.out.println(e);
		}
		return flag;
	}
	
}
