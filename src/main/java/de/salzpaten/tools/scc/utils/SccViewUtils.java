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

/**
 * Utility for building view elements
 *
 * @author Ronny Krammer
 *
 */
public final class SccViewUtils {

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
