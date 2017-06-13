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
import java.io.StringReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

public class ParentInferrer {

	private ParentLevels parentLevels = new ParentLevels();

	private int currentLevel = -1;

	private Options options = new BasicOptions();

	public static void main(String[] args) throws Exception {
		Command command = new Command();
		JCommander commander = new JCommander(command);

		try {
			commander.parse(args);
		} catch (ParameterException e) {
			commander.usage();
			System.exit(1);
		}

		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(command.getInputFile());
		} catch (FileNotFoundException e1) {
			System.err.println("Input file not found.");
			System.exit(1);
		}

		BasicOptions options = new BasicOptions();
		if (command.getIndentCharacter() != null && command.getIndentCharacter().length() > 0) {
			if (command.getIndentCharacter().equals("tab")) {
				options.setIndentCharacter('\t');
			} else {
				options.setIndentCharacter(command.getIndentCharacter().charAt(0));
			}
		}
		options.setNoParentText(command.getNoParentText());

		ParentInferrer pi = new ParentInferrer(options);

		try {
			if (command.getOutputFile() == null) {
				pi.process(inputStream); // if only one arg, use stdout
			} else {
				pi.process(inputStream, new FileOutputStream(command.getOutputFile()));
			}
		} catch (Exception e) {
			System.err.println("There was an error during processing: " + e.getMessage());
			System.exit(1);
		} finally {
			inputStream.close();
		}
	}

	public ParentInferrer(Options options) {
		this.options = options;
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
				int level = lengthOfPrefix(line, options.getIndentCharacter());
				String member = textWithoutPrefix(line, options.getIndentCharacter());

				if (isLevelAcceptable(level)) {
					currentLevel = level;
					parentLevels.setParentForLevel(member, level);
					String parent = parentLevels.getParentForLevel(level - 1);
					printWriter.printf("%s,%s%n", parent == null ? options.getNoParentText() : parent, member);
				} else {
					System.err.printf("Level of processed member %s is invalid: %d (currentLevel = %d)%n", member,
							level, currentLevel);
				}
			}
		} finally {
			bufferedReader.close();
			printWriter.close();
		}
	}

	/**
	 * Processes a string that as a hierarchy and returns a map containing a
	 * parent to children mapping. All members in the processed text that have
	 * children will have an entry in the returned map. By default, members
	 * without children will not be in the map.
	 * 
	 * @param text the text to process
	 * @return a map containing parent to children mappings
	 * @throws ParentInferrerProcessingException if a processing exception occurs
	 */
	public Map<String, List<String>> processString(String text) throws ParentInferrerProcessingException {
		Map<String, List<String>> children = new HashMap<String, List<String>>();
		BufferedReader bufferedReader = new BufferedReader(new StringReader(text));

		try {
			for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
				int level = lengthOfPrefix(line, options.getIndentCharacter());
				String member = textWithoutPrefix(line, options.getIndentCharacter());

				if (isLevelAcceptable(level)) {
					currentLevel = level;
					parentLevels.setParentForLevel(member, level);
					String parent = parentLevels.getParentForLevel(level - 1);
					if (parent == null)
						parent = options.getNoParentText();
					if (!children.containsKey(parent)) {
						children.put(parent, new ArrayList<String>());
					}
					children.get(parent).add(member);
					if (options.isCreateEntriesForChildless()) {
						if (!children.containsKey(member)) {
							children.put(member, new ArrayList<String>());
						}
					}
				} else {
					throw new ParentInferrerProcessingException(String.format("Level of processed member %s is invalid: %d (currentLevel = %d)%n", member, level, currentLevel));
				}
			}
		} catch (IOException e) {
			throw new ParentInferrerProcessingException(e);
		} finally {
			try {
				bufferedReader.close();
			} catch (IOException e) {
				throw new ParentInferrerProcessingException(e);
			}
		}

		return children;
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

	public static String textWithoutPrefix(String text, char prefixChar) {
		return text.substring(lengthOfPrefix(text, prefixChar));
	}

}
