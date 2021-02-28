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
package de.salzpaten.tools.scc.utils.converter;

import de.salzpaten.tools.scc.utils.SccUtils;
import javafx.util.StringConverter;

/**
 * {@link StringConverter} implementation for {@link Double} (and double
 * primitive) values with a numeric check. If it is not a number value and is
 * null, 0 is returned.
 *
 * @author Ronny Krammer
 */
public class DoubleStringIfNumericConverter extends StringConverter<Double> {

	@Override
	public Double fromString(String value) {
		if (value == null) {
			return 0d;
		}

		if (SccUtils.isNumeric(value.trim())) {
			return Double.valueOf(value);
		}

		return 0d;
	}

	@Override
	public String toString(Double value) {
		if (value == null) {
			return "";
		}

		return Double.toString(value.doubleValue());
	}
}
