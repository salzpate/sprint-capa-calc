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
package de.salzpaten.tools.scc.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import de.salzpaten.tools.scc.domain.CalcTableData;

/**
 * Commons utility
 *
 * @author Ronny Krammer
 *
 */
public final class SccUtils {

	private static final int DEFAULT_FONT_SIZE = 13;
	/**
	 * Numeric pattern
	 */
	private static final Pattern PATTERN = Pattern.compile("-?\\d+(\\.\\d+)?");

	/**
	 * Creates a string as list text from the table data
	 *
	 * @param list List of {@link CalcTableData}
	 * @return String as list text
	 */
	public static String buildTableDataAsListText(List<CalcTableData> list) {
		return Optional.ofNullable(list).orElseGet(Collections::emptyList).stream().filter(Objects::nonNull)
				.map(d -> String.format("* %s%s (%.1f|%.1f|%.1f) PD %s", emptyStringIfNullOrWithSpace(d.getKey()),
						emptyStringIfNull(d.getName()), d.getPersonDays(), d.getBackend(), d.getFrontend(),
						trueOrFalseString(d.isActive(), "", "not active")))
				.collect(Collectors.joining("\n"));
	}

	/**
	 * Creates a string as markdown table from the table data
	 *
	 * @param list List of {@link CalcTableData}
	 * @return String as markdown table
	 */
	public static String buildTableDataAsMarkdown(List<CalcTableData> list) {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append(String.format("| %s | %s | %s | %s |%n", "Task", "PD", "BE", "FE"));
		strBuilder.append("| --- | --- | --- | --- |\n");
		strBuilder.append(
				Optional.ofNullable(list).orElseGet(Collections::emptyList).stream().filter(Objects::nonNull).map(d -> {
					if (d.isActive()) {
						return String.format("| %s%s | %.1f | %.1f | %.1f |", emptyStringIfNullOrWithSpace(d.getKey()),
								emptyStringIfNull(d.getName()), d.getPersonDays(), d.getBackend(), d.getFrontend());
					} else {
						return String.format("| ~~%s%s~~ | ~~%.1f~~ | ~~%.1f~~ | ~~%.1f~~ |",
								emptyStringIfNullOrWithSpace(d.getKey()), emptyStringIfNull(d.getName()),
								d.getPersonDays(), d.getBackend(), d.getFrontend());
					}
				}).collect(Collectors.joining("\n")));
		return strBuilder.toString();
	}

	/**
	 * Creates a string as fixed length table from the table data
	 *
	 * @param list List of {@link CalcTableData}
	 * @return String as fixed length table table
	 */
	public static String buildTableDataAsTable(List<CalcTableData> list) {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append(String.format("%-54s %s %s %s%n", "Task", "  PD", "  BE", "  FE"));
		strBuilder.append("---------------------------------------------------------------------\n");
		strBuilder.append(Optional.ofNullable(list).orElseGet(Collections::emptyList).stream().filter(Objects::nonNull)
				.map(d -> String.format("%s %-50s %4.1f %4.1f %4.1f", trueOrFalseString(d.isActive(), "[x]", "[ ]"),
						emptyStringIfNullOrWithSpace(d.getKey()) + emptyStringIfNull(d.getName()), d.getPersonDays(),
						d.getBackend(), d.getFrontend()))
				.collect(Collectors.joining("\n")));
		return strBuilder.toString();
	}

	/**
	 * Returns an empty string if null, otherwise the original value is returned
	 *
	 * @param value String value
	 * @return original value or empty string
	 */
	public static String emptyStringIfNull(String value) {
		final String notNullString;
		if (value == null) {
			notNullString = "";
		} else {
			notNullString = value;
		}
		return notNullString;
	}

	/**
	 * Returns an empty string if null, otherwise the original value is returned
	 *
	 * @param value String value
	 * @return original value or empty string
	 */
	public static String emptyStringIfNullOrWithSpace(String value) {
		final String notNullString;
		if (value == null || "".equals(value.trim())) {
			notNullString = "";
		} else {
			notNullString = value + " ";
		}
		return notNullString;
	}

	/**
	 * Get font size From Style
	 *
	 * @param style Style
	 * @return font size
	 */
	public static int getFontSizeFromStyle(String style) {
		if (style != null && !"".equals(style.trim())) {
			int beginIndex = style.lastIndexOf(':');
			if (beginIndex > -1) {
				try {
					return Integer.parseInt(style.substring(beginIndex + 1).replace(";", "").trim());
				} catch (NumberFormatException e) {
					return DEFAULT_FONT_SIZE;
				}
			}
		}
		return DEFAULT_FONT_SIZE;
	}

	/**
	 * Is value numeric
	 *
	 * @param strNum value
	 * @return is numeric
	 */
	public static boolean isNumeric(String strNum) {
		if (strNum == null) {
			return false;
		}
		return PATTERN.matcher(strNum).matches();
	}

	/**
	 * Scales a value with 1
	 *
	 * @param value Value
	 * @return Scaled value
	 */
	public static Double scaledDouble(Double value) {
		if (value == null) {
			return 0d;
		}
		return BigDecimal.valueOf(value).setScale(1, RoundingMode.HALF_UP).doubleValue();
	}

	/**
	 * Subtracts and returns a positive value. If the result is less than 0, 0 is
	 * returned
	 *
	 * @param minuend    Minuend
	 * @param subtrahend Subtrahend
	 * @return A positive difference value
	 */
	public static Double subtractDouble(Double minuend, Double subtrahend) {
		Double differenceValue;
		if (minuend == null || subtrahend == null) {
			differenceValue = 0d;
		} else {
			differenceValue = BigDecimal.valueOf(minuend).subtract(BigDecimal.valueOf(subtrahend)).doubleValue();
			if (differenceValue < 0) {
				differenceValue = 0d;
			}
		}
		return differenceValue;
	}

	/**
	 * Returns the true or false string
	 *
	 * @param test      boolean Value
	 * @param whenTrue  When test is true string
	 * @param whenFalse When test ist false string
	 * @return the true or false string
	 */
	public static String trueOrFalseString(boolean test, String whenTrue, String whenFalse) {
		final String retValue;
		if (test) {
			retValue = whenTrue;
		} else {
			retValue = whenFalse;
		}
		return retValue;
	}

	private SccUtils() {
		super();
	}
}
