package com.jasonwjones.hyperion.parentinferrer;

import java.io.File;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.converters.FileConverter;

@Parameters(separators = "=")
public class Command {

	@Parameter(names = { "-i", "--indent-character" }, required = false, description = "Single character that indents, specify 'tab' to use tab. Default is single space")
	private String indentCharacter = " ";

	@Parameter(names = { "-f", "--input-file" }, required = true, converter = FileConverter.class)
	private File inputFile;

	@Parameter(names = { "-o", "--output-file" }, required = false, converter = FileConverter.class)
	private File outputFile;
	
	@Parameter(names = {"--no-parent-text" }, required = false)
	private String noParentText;
	
	public String getIndentCharacter() {
		return indentCharacter;
	}

	public void setIndentCharacter(String indentCharacter) {
		this.indentCharacter = indentCharacter;
	}

	public File getInputFile() {
		return inputFile;
	}

	public void setInputFile(File inputFile) {
		this.inputFile = inputFile;
	}

	public File getOutputFile() {
		return outputFile;
	}

	public void setOutputFile(File outputFile) {
		this.outputFile = outputFile;
	}

	public String getNoParentText() {
		return noParentText;
	}

	public void setNoParentText(String noParentText) {
		this.noParentText = noParentText;
	}
	
}
