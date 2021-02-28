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
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.FillRule;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;

/**
 * Utility for building view elements
 *
 * @author Ronny Krammer
 *
 */
public final class SccViewUtils {

	/**
	 * Color of the icons
	 */
	private static final String SVGPATH_FILL_COLOR = "#565656";


	/**
	 * Build a SVG add icon
	 *
	 * @return {@link SVGPath}
	 */
	public static SVGPath addIcon() {
		SVGPath svgPath = new SVGPath();
		svgPath.setContent(
				"M10 5a1 1 0 011 1v3h3a1 1 0 110 2h-3v3a1 1 0 11-2 0v-3H6a1 1 0 110-2h3V6a1 1 0 011-1zM10 5a1 1 0 011 1v3h3a1 1 0 110 2h-3v3a1 1 0 11-2 0v-3H6a1 1 0 110-2h3V6a1 1 0 011-1z");
		svgPath.setFill(Paint.valueOf(SVGPATH_FILL_COLOR));
		svgPath.setStrokeWidth(1.0);
		return svgPath;
	}

	/**
	 * Build a SVG backend icon
	 *
	 * @return {@link SVGPath}
	 */
	public static SVGPath backendIcon() {
		SVGPath svgPath = new SVGPath();
		svgPath.setContent(
				"M5.5 16a3.5 3.5 0 01-.369-6.98 4 4 0 117.753-1.977A4.5 4.5 0 1113.5 16h-8zM5.5 16a3.5 3.5 0 01-.369-6.98 4 4 0 117.753-1.977A4.5 4.5 0 1113.5 16h-8z");
		svgPath.setFill(Paint.valueOf("#e2dcdd"));
		svgPath.setFillRule(FillRule.EVEN_ODD);

		svgPath.setStroke(Paint.valueOf(SVGPATH_FILL_COLOR));
		svgPath.setStrokeWidth(1.0);
		svgPath.setStrokeLineCap(StrokeLineCap.ROUND);
		svgPath.setStrokeLineJoin(StrokeLineJoin.ROUND);
		return svgPath;
	}

	/**
	 * Build a SVG frontend icon
	 *
	 * @return {@link SVGPath}
	 */
	public static SVGPath frontendIcon() {
	SVGPath svgPath = new SVGPath();
	svgPath.setContent(
			"M3 5a2 2 0 012-2h10a2 2 0 012 2v8a2 2 0 01-2 2h-2.22l.123.489.804.804A1 1 0 0113 18H7a1 1 0 01-.707-1.707l.804-.804L7.22 15H5a2 2 0 01-2-2V5zm5.771 7H5V5h10v7H8.771zM3 5a2 2 0 012-2h10a2 2 0 012 2v8a2 2 0 01-2 2h-2.22l.123.489.804.804A1 1 0 0113 18H7a1 1 0 01-.707-1.707l.804-.804L7.22 15H5a2 2 0 01-2-2V5zm5.771 7H5V5h10v7H8.771z");
	svgPath.setFill(Paint.valueOf("#e2dcdd"));
	svgPath.setFillRule(FillRule.EVEN_ODD);

	svgPath.setStroke(Paint.valueOf(SVGPATH_FILL_COLOR));
	svgPath.setStrokeWidth(1.0);
	svgPath.setStrokeLineCap(StrokeLineCap.ROUND);
	svgPath.setStrokeLineJoin(StrokeLineJoin.ROUND);
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
	/**
     * Sets the top, button and left anchor for the child when contained by an anchor pane.
     * If set, the anchor pane will maintain the child's size and position so
     * that it's top is always offset by that amount from the anchor pane's top
     * content edge.
     * Setting the value to null will remove the constraint.
     * @param child the child node of an anchor pane
     * @param top the offset from the top of the anchor pane
     * @param bottom the offset from the bottom of the anchor pane
     * @param left the offset from the left of the anchor pane
     */
	public static void setIconAnchorPositions(Node child, double top, double bottom, double left) {
		AnchorPane.setTopAnchor(child, top);
		AnchorPane.setBottomAnchor(child, bottom);
		AnchorPane.setLeftAnchor(child, left);
	}

	private SccViewUtils() {
		super();
	}
}
