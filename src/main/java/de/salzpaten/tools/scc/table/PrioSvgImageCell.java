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
import de.salzpaten.tools.scc.utils.SccViewUtils;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.TextFieldTableCell;

/**
 * SVG Image TableCell.
 *
 * @author Ronny Krammer
 *
 */
public class PrioSvgImageCell extends TextFieldTableCell<CalcTableData, String> {

	@Override
	public void updateItem(String item, boolean empty) {
		setStyle("-fx-alignment: center;");

		if (item == null || empty) {
			setGraphic(null);
			setTooltip(null);
		} else {
			setTooltip(new Tooltip(item));
			setGraphic(SccViewUtils.buildSVG(item));
		}
	}
}