package com.jasonwjones.hyperion.parentinferrer;

/**
 * A simple implementation of the Options interface.
 * 
 * @author jasonwjones
 *
 */
public class BasicOptions implements Options {

	private char indentCharacter = DEFAULT_INDENT_CHARACTER;

	private String noParentText = "null";
	
	private boolean createEntriesForChildless = false;
	
	public static final char DEFAULT_INDENT_CHARACTER = ' ';

	public boolean allowOveradvancedLevels() {
		return false;
	}

	@Override
	public char getIndentCharacter() {
		return indentCharacter;
	}

	public void setIndentCharacter(char indentCharacter) {
		this.indentCharacter = indentCharacter;
	}

	@Override
	public String getNoParentText() {
		return noParentText;
	}

	public void setNoParentText(String noParentText) {
		this.noParentText = noParentText;
	}

	@Override
	public boolean isCreateEntriesForChildless() {
		return createEntriesForChildless;
	}

	public void setCreateEntriesForChildless(boolean createEntriesForChildless) {
		this.createEntriesForChildless = createEntriesForChildless;
	}

}
