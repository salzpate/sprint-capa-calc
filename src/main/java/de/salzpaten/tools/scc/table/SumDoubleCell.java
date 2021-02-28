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

import de.salzpaten.tools.scc.domain.CalcTableData;
import javafx.scene.control.TableCell;
import javafx.scene.paint.Color;

/**
 * Summary Double TableCell. The last row is bold and if the value is less than
 * zero the color is red.
 *
 * @author Ronny Krammer
 *
 */
public class SumDoubleCell extends TableCell<CalcTableData, Double> {

	@Override
	protected void updateItem(Double item, boolean empty) {
		super.updateItem(item, empty);
		setTextFill(Color.BLACK);

		if (getTableView().getItems().size() - 1 == getTableRow().getIndex()) {
			setStyle("-fx-font-weight: bold");
		} else {
			setStyle("-fx-font-weight: normal");
		}

		if (item == null || empty) {
			setText(null);
		} else {
			setText(item.toString());
			if (item < 0) {
				setTextFill(Color.RED);
			}
		}
	}
}