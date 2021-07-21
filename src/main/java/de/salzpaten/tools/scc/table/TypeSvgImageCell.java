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
		case "New Feature":
			svgPath.setContent("M10 18a8 8 0 100-16 8 8 0 000 16zm1-11a1 1 0 10-2 0v2H7a1 1 0 100 2h2v2a1 1 0 102 0v-2h2a1 1 0 100-2h-2V7z");
			svgPath.setFill(Paint.valueOf("#36b37f"));
			break;
		case "Improvement":
			svgPath.setContent("M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-8.707l-3-3a1 1 0 00-1.414 0l-3 3a1 1 0 001.414 1.414L9 9.414V13a1 1 0 102 0V9.414l1.293 1.293a1 1 0 001.414-1.414z");
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
		case "PM Task":
		case "Concept":
			svgPath.setContent(
					"M3 5a1 1 0 011-1h12a1 1 0 110 2H4a1 1 0 01-1-1zM3 10a1 1 0 011-1h12a1 1 0 110 2H4a1 1 0 01-1-1zM3 15a1 1 0 011-1h6a1 1 0 110 2H4a1 1 0 01-1-1z");
			svgPath.setFill(Paint.valueOf("#6554C0"));
			break;
		case "Vulnerability":
			svgPath.setContent(
					"M5 2a2 2 0 00-2 2v14l3.5-2 3.5 2 3.5-2 3.5 2V4a2 2 0 00-2-2H5zm2.5 3a1.5 1.5 0 100 3 1.5 1.5 0 000-3zm6.207.293a1 1 0 00-1.414 0l-6 6a1 1 0 101.414 1.414l6-6a1 1 0 000-1.414zM12.5 10a1.5 1.5 0 100 3 1.5 1.5 0 000-3z");
			svgPath.setFill(Paint.valueOf("#ff5630"));
			break;
		case "QA Task":
			svgPath.setContent(
					"M3.172 5.172a4 4 0 015.656 0L10 6.343l1.172-1.171a4 4 0 115.656 5.656L10 17.657l-6.828-6.829a4 4 0 010-5.656z");
			svgPath.setFill(Paint.valueOf("#2684ff"));
			break;
		case "Test":
			svgPath.setContent(
					"M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-2 0c0 .993-.241 1.929-.668 2.754l-1.524-1.525a3.997 3.997 0 00.078-2.183l1.562-1.562C15.802 8.249 16 9.1 16 10zm-5.165 3.913l1.58 1.58A5.98 5.98 0 0110 16a5.976 5.976 0 01-2.516-.552l1.562-1.562a4.006 4.006 0 001.789.027zm-4.677-2.796a4.002 4.002 0 01-.041-2.08l-.08.08-1.53-1.533A5.98 5.98 0 004 10c0 .954.223 1.856.619 2.657l1.54-1.54zm1.088-6.45A5.974 5.974 0 0110 4c.954 0 1.856.223 2.657.619l-1.54 1.54a4.002 4.002 0 00-2.346.033L7.246 4.668zM12 10a2 2 0 11-4 0 2 2 0 014 0z");
			svgPath.setFill(Paint.valueOf("#36b37f"));
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