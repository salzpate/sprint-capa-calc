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
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.paint.Paint;
import javafx.scene.shape.FillRule;
import javafx.scene.shape.SVGPath;

/**
 * SVG Image TableCell.
 *
 * @author Ronny Krammer
 *
 */
public class PrioSvgImageCell extends TextFieldTableCell<CalcTableData, String> {

	/**
	 * Builds the SVG Icon
	 *
	 * @param item Name of Icon
	 * @return {@link SVGPath}
	 */
	private SVGPath buildSVG(String item) {
		SVGPath svgPath = new SVGPath();
		svgPath.setFillRule(FillRule.EVEN_ODD);
		switch (item) {
		case "Highest":
			svgPath.setContent(
					"M4.293 15.707a1 1 0 010-1.414l5-5a1 1 0 011.414 0l5 5a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414 0zm0-6a1 1 0 010-1.414l5-5a1 1 0 011.414 0l5 5a1 1 0 01-1.414 1.414L10 5.414 5.707 9.707a1 1 0 01-1.414 0z");
			svgPath.setFill(Paint.valueOf("#ff5630"));
			break;
		case "High":
			svgPath.setContent(
					"M14.707 12.707a1 1 0 01-1.414 0L10 9.414l-3.293 3.293a1 1 0 01-1.414-1.414l4-4a1 1 0 011.414 0l4 4a1 1 0 010 1.414z");
			svgPath.setFill(Paint.valueOf("#ff5630"));
			break;
		case "Medium":
			svgPath.setContent(
					"M3 7a1 1 0 011-1h12a1 1 0 110 2H4a1 1 0 01-1-1zM3 13a1 1 0 011-1h12a1 1 0 110 2H4a1 1 0 01-1-1z");
			svgPath.setFill(Paint.valueOf("#ff9a1f"));
			break;
		case "Low":
			svgPath.setContent(
					"M5.293 7.293a1 1 0 011.414 0L10 10.586l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z");
			svgPath.setFill(Paint.valueOf("#2684ff"));
			break;
		case "Lowest":
			svgPath.setContent(
					"M15.707 4.293a1 1 0 010 1.414l-5 5a1 1 0 01-1.414 0l-5-5a1 1 0 011.414-1.414L10 8.586l4.293-4.293a1 1 0 011.414 0zm0 6a1 1 0 010 1.414l-5 5a1 1 0 01-1.414 0l-5-5a1 1 0 111.414-1.414L10 14.586l4.293-4.293a1 1 0 011.414 0z");
			svgPath.setFill(Paint.valueOf("#2684ff"));
			break;
		default:
			svgPath.setContent("M10 18a8 8 0 100-16 8 8 0 000 16zM7 9a1 1 0 000 2h6a1 1 0 100-2H7z");
			svgPath.setFill(Paint.valueOf("#ff5630"));
			break;
		}

		return svgPath;
	}

	@Override
	public void updateItem(String item, boolean empty) {
		setStyle("-fx-alignment: center;");

		if (item == null || empty) {
			setGraphic(null);
			setTooltip(null);
		} else {
			setTooltip(new Tooltip(item));
			setGraphic(buildSVG(item));
		}
	}
}