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
package sprintcapacalcde.salzpaten.tools.scc.utils.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.salzpaten.tools.scc.domain.CalcTableData;
import de.salzpaten.tools.scc.utils.SccUtils;

/**
 * Testing of {@link SccUtils}
 *
 * @author Ronny Krammer
 *
 */
class SccUtilsTest {

	private List<CalcTableData> calcTableDataList;

	@BeforeEach
	void setUp() {
		calcTableDataList = new ArrayList<>();
		calcTableDataList.add(new CalcTableData("Item 1", 10, 5, 5, true));
		calcTableDataList.add(new CalcTableData("Item 2", 5, 3, 2, true));
		calcTableDataList.add(new CalcTableData("Item 3", 4, 1, 3, false));
		calcTableDataList.add(new CalcTableData(null, 2, 1, 1, false));
	}

	@Test
	void testBuildTableDataAsListText() {
		String expected = "* Item 1 (10,0|5,0|5,0) PD \n* Item 2 (5,0|3,0|2,0) PD \n* Item 3 (4,0|1,0|3,0) PD not active\n*  (2,0|1,0|1,0) PD not active";
		assertEquals(expected, SccUtils.buildTableDataAsListText(calcTableDataList));
		calcTableDataList.add(null);
		assertEquals(expected, SccUtils.buildTableDataAsListText(calcTableDataList));
		assertEquals("", SccUtils.buildTableDataAsListText(null));
		assertEquals("", SccUtils.buildTableDataAsListText(Collections.emptyList()));
	}

	@Test
	void testBuildTableDataAsMarkdown() {
		String expected = "| Task | PD | BE | FE |\n| --- | --- | --- | --- |\n| Item 1 | 10,0 | 5,0 | 5,0 |\n| Item 2 | 5,0 | 3,0 | 2,0 |\n| ~~Item 3~~ | ~~4,0~~ | ~~1,0~~ | ~~3,0~~ |\n| ~~~~ | ~~2,0~~ | ~~1,0~~ | ~~1,0~~ |";
		String expectedEmpty = "| Task | PD | BE | FE |\n| --- | --- | --- | --- |\n";
		assertEquals(expected, SccUtils.buildTableDataAsMarkdown(calcTableDataList));
		calcTableDataList.add(null);
		assertEquals(expected, SccUtils.buildTableDataAsMarkdown(calcTableDataList));
		assertEquals(expectedEmpty, SccUtils.buildTableDataAsMarkdown(null));
		assertEquals(expectedEmpty, SccUtils.buildTableDataAsMarkdown(Collections.emptyList()));
	}

	@Test
	void testBuildTableDataAsTable() {
		String expected = "Task                                                     PD   BE   FE\n---------------------------------------------------------------------\n[x] Item 1                                             10,0  5,0  5,0\n[x] Item 2                                              5,0  3,0  2,0\n[ ] Item 3                                              4,0  1,0  3,0\n[ ]                                                     2,0  1,0  1,0";
		String expectedEmpty = "Task                                                     PD   BE   FE\n---------------------------------------------------------------------\n";
		assertEquals(expected, SccUtils.buildTableDataAsTable(calcTableDataList));
		calcTableDataList.add(null);
		assertEquals(expected, SccUtils.buildTableDataAsTable(calcTableDataList));
		assertEquals(expectedEmpty, SccUtils.buildTableDataAsTable(null));
		assertEquals(expectedEmpty, SccUtils.buildTableDataAsTable(Collections.emptyList()));
	}

	@Test
	void testEmptyStringIfNull() {
		assertEquals("test", SccUtils.emptyStringIfNull("test"));
		assertEquals("", SccUtils.emptyStringIfNull(null));
		assertEquals("", SccUtils.emptyStringIfNull(""));
	}

	@Test
	void testIsNumeric() {
		assertTrue(SccUtils.isNumeric("100"));
		assertTrue(SccUtils.isNumeric("10.0"));
		assertFalse(SccUtils.isNumeric(null));
		assertFalse(SccUtils.isNumeric(""));
		assertFalse(SccUtils.isNumeric("10,0"));
		assertFalse(SccUtils.isNumeric("string"));
	}

	@Test
	void testScaledDouble() {
		assertEquals(Double.valueOf(100.0d), SccUtils.scaledDouble(100d), 1d);
		assertEquals(Double.valueOf(100.5d), SccUtils.scaledDouble(100.5434d), 0.1d);
		assertEquals(Double.valueOf(0.0d), SccUtils.scaledDouble(null), 0.1d);
	}

	@Test
	void testSubtractDouble() {
		assertEquals(Double.valueOf(50.0d), SccUtils.subtractDouble(100d, 50d), 1d);
		assertEquals(Double.valueOf(0.0d), SccUtils.subtractDouble(50d, 100d), 1d);
		assertEquals(Double.valueOf(0.0d), SccUtils.subtractDouble(50d, 50d), 1d);
		assertEquals(Double.valueOf(0.0d), SccUtils.subtractDouble(null, 50d), 1d);
		assertEquals(Double.valueOf(0.0d), SccUtils.subtractDouble(50d, null), 1d);
		assertEquals(Double.valueOf(0.0d), SccUtils.subtractDouble(null, null), 1d);
	}

	@Test
	void testTrueOrFalseString() {
		assertEquals("true", SccUtils.trueOrFalseString(true, "true", "false"));
		assertEquals("false", SccUtils.trueOrFalseString(false, "true", "false"));
		assertEquals(null, SccUtils.trueOrFalseString(true, null, "false"));
		assertEquals(null, SccUtils.trueOrFalseString(false, "true", null));
		assertEquals(null, SccUtils.trueOrFalseString(true, null, null));

	}
}
