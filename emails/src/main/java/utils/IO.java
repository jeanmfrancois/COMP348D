/**
 * File Name: IO.java<br>
 * I declare that this assignment is my own work and that all material
 * previously written or published in any source by any other person has been
 * duly acknowledged in the assignment. I have not submitted this work, or a
 * significant part thereof, previously as part of any academic program. In
 * submitting this assignment I give permission to copy it for assessment
 * purposes only. Jean-francois Nepton<br>
 * COMP 348 Network Programming<br>
 * Cordinator: Richard Huntrods<br>
 * Student ID# 2358976<br>
 * Created: Jan 22, 2015
 */
package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * IO (description of class)
 * <p>
 * (description of core fields)
 * <p>
 * (description of core methods)
 * 
 * @author Jean-francois Nepton
 * @version %I%, %G%
 * @since 1.0
 */
public class IO {

	/**
	 * Used to read a ACII text file and return a collection of Strings
	 * corresponding to lines in the file.
	 * 
	 * @param filePath
	 *            the path of the ASCII file to be used.
	 * @return A list of Strings which correspond to lines in the file.
	 */
	public static List<String> readFile(String filePath) {
		List<String> lines = new ArrayList<>();
		Path path = Paths.get(filePath);
		Charset charset = Charset.forName("ASCII");
		String line;
		try (BufferedReader br = Files.newBufferedReader(path, charset)) {
			line = null;
			while ((line = br.readLine()) != null) {
				lines.add(line);
			}
		} catch (IOException e) {
			System.out.println("There was an error opening and reading the file.");
			// System.err.format("IOException: %s%n", x);
		}
		return lines;
	}

	/**
	 * Used to parse a collection of strings into key/ value pairs to be used
	 * for obtaining properties in a text file where the key is present before
	 * the delimeter ":" and the value is any text afterwards. Will include
	 * following line if no delimeter character is found. Does not support the
	 * use of the delimeter in a multiline value.
	 * 
	 * @param collection
	 *            Strings to be used for property parsing
	 * @param delimeter
	 *            The delimeter to be used in parsing. Must be strickly used
	 *            only as a delimeter.
	 * @return A HashMap of key and values to signafy properties
	 */
	public static HashMap<String, String> createProperties(List<String> collection, String delimeter) {
		HashMap<String, String> properties = new HashMap<>();
		String prevKey = null;
		try {
			for (String s : collection) {
				String[] pair = s.split(delimeter);
				String key = null;
				String value = null;
				if (pair.length == 0) {
					throw new MalformedIOFile();
				} else if (pair.length == 1) {
					if (prevKey != null) {
						String newValue = properties.get(prevKey);
						newValue += pair[0];
						properties.put(prevKey.toUpperCase(), newValue);
					} else {
						throw new MalformedIOFile();
					}
				} else if (pair.length == 2) {
					key = pair[0].trim().toUpperCase();
					value = pair[1].trim();
					prevKey = key;
					properties.put(key, value);
				} else {
					key = pair[0].trim().toUpperCase();
					for (int i = 1; i < pair.length; i++) {
						System.out.println("3+>");
						value += pair[i];
					}
					properties.put(key, value);
				}
			}
		} catch (MalformedIOFile e) {
			e.getMessage();
			e.printStackTrace();
		}
		return properties;
	}

	public static void main(String[] args) {
		List<String> lines = IO.readFile("send-email-example.txt");
		HashMap<String, String> props = IO.createProperties(lines, ":");
		props.toString();
	}
}

class MalformedIOFile extends Exception {

	@Override
	public String getMessage() {
		return "Malformed IO File: Does not meet file format specification, please review supplied text file.";
	}
}
