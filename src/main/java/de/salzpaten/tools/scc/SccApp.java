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
package de.salzpaten.tools.scc;

import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.salzpaten.tools.scc.controller.MainController;
import de.salzpaten.tools.scc.service.impl.JiraDataServiceImpl;
import de.salzpaten.tools.scc.utils.PropertyLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Sprint capacity calculation application start
 *
 * @author Ronny Krammer
 *
 */
public class SccApp extends Application {

	/**
	 * Standard Logger for GraalVM
	 */
	private static final Logger LOGGER = Logger.getLogger(SccApp.class.getName());

	/**
	 * Version Number
	 */
	private static final String VERSION = "1.1.0";

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) {
		try {
			FXMLLoader loader = new FXMLLoader(MainController.class.getResource("main.fxml"),
					ResourceBundle.getBundle("de.salzpaten.tools.scc.controller.main"));
			Pane root = loader.load();

			String userDir = System.getProperty("user.dir");
			PropertyLoader propertyLoader = new PropertyLoader(userDir + File.separator + "scc.properties");
			MainController controller = loader.getController();
			controller.setDataService(new JiraDataServiceImpl(propertyLoader.getJiraProperties(), getHostServices()));

			Scene scene = new Scene(root, 800, 600);
			scene.getStylesheets().add("app.css");
			stage.setTitle("Sprint Calculator " + VERSION);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Application does not start", e);
		}
	}

}
