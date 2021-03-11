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
public class TypeSvgImageCell extends TextFieldTableCell<CalcTableData, String> {

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
		case "Story":
			svgPath.setContent("M3 5a2 2 0 012-2h10a2 2 0 012 2v10a2 2 0 01-2 2H5a2 2 0 01-2-2V5zm11 1H6v8l4-2 4 2V6z");
			svgPath.setFill(Paint.valueOf("#36b37f"));
			break;
		case "Bug":
			svgPath.setContent(
					"M10 18a8 8 0 100-16 8 8 0 000 16zM8 7a1 1 0 00-1 1v4a1 1 0 001 1h4a1 1 0 001-1V8a1 1 0 00-1-1H8z");
			svgPath.setFill(Paint.valueOf("#ff5630"));
			break;
		case "Impediment":
			svgPath.setContent(
					"M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7 4a1 1 0 11-2 0 1 1 0 012 0zm-1-9a1 1 0 00-1 1v4a1 1 0 102 0V6a1 1 0 00-1-1z");
			svgPath.setFill(Paint.valueOf("#ff9a1f"));
			break;
		case "Epic":
			svgPath.setContent(
					"M11.3 1.046A1 1 0 0112 2v5h4a1 1 0 01.82 1.573l-7 10A1 1 0 018 18v-5H4a1 1 0 01-.82-1.573l7-10a1 1 0 011.12-.38z");
			svgPath.setFill(Paint.valueOf("#6454c0"));
			break;
		case "Sub-task":
			svgPath.setContent(
					"M4 4a2 2 0 00-2 2v4a2 2 0 002 2V6h10a2 2 0 00-2-2H4zm2 6a2 2 0 012-2h8a2 2 0 012 2v4a2 2 0 01-2 2H8a2 2 0 01-2-2v-4zm6 4a2 2 0 100-4 2 2 0 000 4z");
			svgPath.setFill(Paint.valueOf("#2684ff"));
			break;
		default:
			svgPath.setContent(
					"M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z");
			svgPath.setFill(Paint.valueOf("#2684ff"));
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