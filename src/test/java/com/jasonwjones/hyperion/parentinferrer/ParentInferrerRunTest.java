package com.jasonwjones.hyperion.parentinferrer;

import java.io.File;

public class ParentInferrerRunTest {

	public static void main(String[] args) throws Exception {
		File infile = new File("./src/test/resources", "time-tabs.txt");
		String[] inferrerArgs = {"-f", infile.getAbsolutePath(), "-i", "tab", "--no-parent-text", ""};
		ParentInferrer.main(inferrerArgs);
	}

}
