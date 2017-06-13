package com.jasonwjones.hyperion.parentinferrer;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class ParentInferrerTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testProcess() throws Exception {
		ParentInferrer pi = new ParentInferrer(new BasicOptions());
		pi.process(getClass().getResourceAsStream("/time.txt"));
	}

	@Test
	public void testLengthOfPrefix() {
		String memberLine1 = "   Member";
		assertEquals(3, ParentInferrer.lengthOfPrefix(memberLine1, ' '));

		String memberLine2 = "         ";
		assertEquals(9, ParentInferrer.lengthOfPrefix(memberLine2, ' '));

		String memberLine3 = "Member";
		assertEquals(0, ParentInferrer.lengthOfPrefix(memberLine3, ' '));

		String memberLine4 = "";
		assertEquals(0, ParentInferrer.lengthOfPrefix(memberLine4, ' '));
	}

	@Test
	public void testTextWithoutPrefix() {
		String text = "   apple";
		assertEquals("apple", ParentInferrer.textWithoutPrefix(text, ' '));
	}
	
	@Test
	public void testProcessString() {
		String hier = "Time\n Qtr1\n Qtr2\n  Jan\n  Feb\n  Mar\n";
		ParentInferrer pi = new ParentInferrer(new BasicOptions());
		Map<String, List<String>> children = pi.processString(hier);
		assertEquals(3, children.size());
		assertEquals(2, children.get("Time").size());
	}
	
	@Test
	public void testProcessStringCreateChildEntriesWhenNoChildren() {
		String hier = "Time\n Qtr1\n Qtr2\n  Jan\n  Feb\n  Mar\n";
		BasicOptions options = new BasicOptions();
		options.setCreateEntriesForChildless(true);
		ParentInferrer pi = new ParentInferrer(options);
		
		Map<String, List<String>> children = pi.processString(hier);
		assertEquals(7, children.size());
		assertEquals(2, children.get("Time").size());
		assertEquals(0, children.get("Jan").size());
	}
	
}
