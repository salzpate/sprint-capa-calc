/*
 * Copyright 2021 Ronny Krammer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package sprintcapacalcde.salzpaten.tools.scc.utils.converter.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.salzpaten.tools.scc.utils.converter.DoubleStringIfNumericConverter;

/**
 * Testing of {@link DoubleStringIfNumericConverter}
 *
 * @author Ronny Krammer
 *
 */
class DoubleStringIfNumericConverterTest {

	private DoubleStringIfNumericConverter converter;

	@BeforeEach
	void setUp() {
		converter = new DoubleStringIfNumericConverter();
	}

	@Test
	void testFromString() {
		assertEquals(100d, converter.fromString("100"));
		assertEquals(10.0d, converter.fromString("10.0"));
		assertEquals(10.5d, converter.fromString("10.5"));
		assertEquals(0d, converter.fromString(null));
		assertEquals(0d, converter.fromString(""));
		assertEquals(0d, converter.fromString("10,5"));
		assertEquals(0d, converter.fromString("string"));
	}

	@Test
	void testToString() {
		assertEquals("100.0", converter.toString(100d));
		assertEquals("10.0", converter.toString(10.0d));
		assertEquals("10.5", converter.toString(10.5d));
		assertEquals("", converter.toString(null));
	}

}
