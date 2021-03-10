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
package de.salzpaten.tools.scc.table;

import java.math.BigDecimal;

import de.salzpaten.tools.scc.domain.CalcTableData;
import de.salzpaten.tools.scc.utils.converter.DoubleStringIfNumericConverter;
import javafx.scene.control.cell.TextFieldTableCell;

/**
 * Summary Double TableCell. The last row is bold and if the value is less than
 * zero the color is red.
 *
 * @author Ronny Krammer
 *
 */
public class PersonDaysCell extends TextFieldTableCell<CalcTableData, Double> {

	public PersonDaysCell() {
		super(new DoubleStringIfNumericConverter());
	}

	@Override
	public void updateItem(Double item, boolean empty) {
		super.updateItem(item, empty);
		setStyle("");

		if (item == null || empty) {
		} else {
			CalcTableData data = getTableRow().getItem();

			if (data != null && data.getPersonDays() > 0 && BigDecimal.valueOf(data.getBackend())
					.add(BigDecimal.valueOf(data.getFrontend())).doubleValue() < data.getPersonDays()) {
				setStyle("-fx-text-fill: red");
			}
		}
	}
}