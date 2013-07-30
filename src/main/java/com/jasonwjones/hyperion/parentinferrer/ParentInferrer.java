package com.jasonwjones.hyperion.parentinferrer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;

public class ParentInferrer {

	private ParentLevels parentLevels = new ParentLevels();
	private int currentLevel = -1;

	// private Options options = new BasicOptions();

	public static void main(String[] args) throws Exception {
		if (args.length == 0) {
			usage();
			System.exit(1);
		}

		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(args[0]);
		} catch (FileNotFoundException e1) {
			System.err.println("Input file not found.");
			System.exit(1);
		}

		ParentInferrer pi = new ParentInferrer();

		try {
			if (args.length == 1) {
				pi.process(inputStream); // if only one arg, use stdout
			} else {
				pi.process(inputStream, new FileOutputStream(args[1]));
			}
		} catch (Exception e) {
			System.err.println("There was an error during processing: " + e.getMessage());
			System.exit(1);
		} finally {
			inputStream.close();
		}
	}

	private static void usage() {
		System.out.println("Usage:");
	}

	public void reset() {
		currentLevel = -1;
		parentLevels = new ParentLevels();
	}

	public void process(String inputFile) throws FileNotFoundException, IOException {
		process(new FileInputStream(inputFile));
	}

	public void process(InputStream inputStream) throws IOException {
		process(inputStream, System.out);
	}

	public void process(InputStream inputStream, OutputStream outputStream) throws IOException {
		process(new InputStreamReader(inputStream), new OutputStreamWriter(outputStream));
	}

	public void process(Reader reader, Writer writer) throws IOException, ParentInferrerProcessingException {
		PrintWriter printWriter = new PrintWriter(writer);
		BufferedReader bufferedReader = new BufferedReader(reader);

		try {
			for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
				int level = lengthOfPrefix(line);
				String member = textWithoutPrefix(line);

				if (isLevelAcceptable(level)) {
					currentLevel = level;
					parentLevels.setParentForLevel(member, level);
					String parent = parentLevels.getParentForLevel(level - 1);
					printWriter.printf("%s,%s%n", parent, member);
				} else {
					System.err.printf("Level of processed member %s is invalid: %d (currentLevel = %d)%n", member, level, currentLevel);
				}
			}
		} finally {
			bufferedReader.close();
			printWriter.close();
		}
	}

	/**
	 * Checks if the level of a given member is valid with respect to the
	 * current processing level that we are tracking. During processing, it is
	 * permissible to go down a level (level + 1) or to go back any number of
	 * levels.
	 * 
	 * @param level the level to check for acceptability
	 * @return true if it's okay, false otherwise
	 */
	private boolean isLevelAcceptable(int level) {
		return level <= currentLevel + 1;
	}

	public static int lengthOfPrefix(String text) {
		return lengthOfPrefix(text, ' ');
	}

	public static int lengthOfPrefix(String text, char prefixChar) {
		StringBuilder sb = new StringBuilder();
		for (int index = 0; index < text.length(); index++) {
			if (!text.startsWith(sb.toString())) {
				return index - 1;
			}
			sb.append(prefixChar);
		}
		return text.length();
	}

	public static String textWithoutPrefix(String text) {
		return text.substring(lengthOfPrefix(text));
	}

	public static String textWithoutPrefix(String text, char prefixChar) {
		return text.substring(lengthOfPrefix(text), prefixChar);
	}

}
