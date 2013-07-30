package com.jasonwjones.hyperion.parentinferrer;

import java.util.HashMap;
import java.util.Map;

public class ParentLevels {

	private Map<Integer, String> parentLevels = new HashMap<Integer, String>();

	public void setParentForLevel(String parent, int level) {
		parentLevels.put(level, parent);
	}

	public String getParentForLevel(int level) {
		if (parentLevels.containsKey(level)) {
			return parentLevels.get(level);
		} else {
			return null;
		}
	}

}