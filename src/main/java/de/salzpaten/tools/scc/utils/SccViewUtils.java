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

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.FillRule;
import javafx.scene.shape.SVGPath;

/**
 * Utility for building view elements
 *
 * @author Ronny Krammer
 *
 */
public final class SccViewUtils {

	/**
	 * Builds the SVG Icon
	 *
	 * @param item Name of Icon
	 * @return {@link SVGPath}
	 */
	public static SVGPath buildSVG(String item) {
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
		case "Remove":
			svgPath.setContent(
					"M10 18a8 8 0 100-16 8 8 0 000 16zM7 9a1 1 0 000 2h6a1 1 0 100-2H7z");
			svgPath.setFill(Paint.valueOf("#000000"));
			break;
		case "Link":
			svgPath.setContent(
					"M11 3a1 1 0 100 2h2.586l-6.293 6.293a1 1 0 101.414 1.414L15 6.414V9a1 1 0 102 0V4a1 1 0 00-1-1h-5z");
			svgPath.setFill(Paint.valueOf("#000000"));
			break;
		case "Plus":
			svgPath.setContent(
					"M10 5a1 1 0 011 1v3h3a1 1 0 110 2h-3v3a1 1 0 11-2 0v-3H6a1 1 0 110-2h3V6a1 1 0 011-1z");
			svgPath.setFill(Paint.valueOf("#000000"));
			break;
		case "Minus":
			svgPath.setContent(
					"M3 10a1 1 0 011-1h12a1 1 0 110 2H4a1 1 0 01-1-1z");
			svgPath.setFill(Paint.valueOf("#000000"));
			break;
		case "Dots":
			svgPath.setContent(
					"M6 10a2 2 0 11-4 0 2 2 0 014 0zM12 10a2 2 0 11-4 0 2 2 0 014 0zM16 12a2 2 0 100-4 2 2 0 000 4z");
			svgPath.setFill(Paint.valueOf("#000000"));
			break;
		default:
			svgPath.setContent("M10 18a8 8 0 100-16 8 8 0 000 16zM7 9a1 1 0 000 2h6a1 1 0 100-2H7z");
			svgPath.setFill(Paint.valueOf("#ff5630"));
			break;
		}

		return svgPath;
	}

	/**
	 * Initialize of focused change listener on a text-field and change the style
	 * class on parent pane
	 *
	 * @param textfield       {@link TextField}
	 * @param pane            {@link Pane}
	 * @param styleClass      style class
	 * @param focusStyleClass style class on focus
	 */
	public static void initTextfieldPaneFocus(TextField textfield, Pane pane, String styleClass,
			String focusStyleClass) {
		textfield.focusedProperty().addListener(c -> {
			if (c instanceof ReadOnlyBooleanProperty) {
				ReadOnlyBooleanProperty bo = (ReadOnlyBooleanProperty) c;
				if (bo.get()) {
					pane.getStyleClass().clear();
					pane.getStyleClass().add(focusStyleClass);
				} else {
					pane.getStyleClass().clear();
					pane.getStyleClass().add(styleClass);
				}

			}
		});
	}

	private SccViewUtils() {
		super();
	}
}
