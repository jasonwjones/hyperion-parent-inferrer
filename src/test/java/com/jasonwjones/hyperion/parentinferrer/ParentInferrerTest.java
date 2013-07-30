package com.jasonwjones.hyperion.parentinferrer;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ParentInferrerTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testProcess() throws Exception {
		ParentInferrer pi = new ParentInferrer();
		pi.process(getClass().getResourceAsStream("/time.txt"));
	}

	@Test
	public void testLengthOfPrefix() {
		String memberLine1 = "   Member";
		assertEquals(3, ParentInferrer.lengthOfPrefix(memberLine1));

		String memberLine2 = "         ";
		assertEquals(9, ParentInferrer.lengthOfPrefix(memberLine2));

		String memberLine3 = "Member";
		assertEquals(0, ParentInferrer.lengthOfPrefix(memberLine3));

		String memberLine4 = "";
		assertEquals(0, ParentInferrer.lengthOfPrefix(memberLine4));
	}

	@Test
	public void testTextWithoutPrefix() {
		String text = "   apple";
		assertEquals("apple", ParentInferrer.textWithoutPrefix(text));
	}
}
