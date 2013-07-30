package com.jasonwjones.hyperion.parentinferrer;

public interface Options {

	/**
	 * The normal behavior of processing is to not allow members that are
	 * advanced too far before their parent shows up. In other words, we don't
	 * want to allow a grandchild member to be allowed before its parent is
	 * introduced. This will happen (or appear to happen) if a child entry is
	 * spaced too far with respect to its parent.
	 * 
	 * This indicates a poorly formed file, but if for whatever reason we want
	 * to treat these as okay, this can be set to true to allow it to be okay
	 * and assume that the member is just a normal child of the parent.
	 * 
	 * The default is false.
	 * 
	 * @return true if allowed, false otherwise
	 */
	boolean allowOveradvancedLevels();

}
