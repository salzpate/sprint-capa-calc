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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.salzpaten.tools.scc.service.impl.JiraProperties;

/**
 * Loading and setting Properties
 *
 * @author Ronny Krammer
 *
 */
public class PropertyLoader {

	private static final Logger LOGGER = Logger.getLogger(PropertyLoader.class.getName());

	private JiraProperties jiraProperties = new JiraProperties();

	public PropertyLoader(String file) {
		init(file);
	}

	public JiraProperties getJiraProperties() {
		return jiraProperties;
	}

	/**
	 * Load and set properties
	 */
	private void init(String file) {

		Properties prop = new Properties();
		try (InputStream inStream = new FileInputStream(new File(file))) {
			prop.load(inStream);
			jiraProperties = new JiraProperties();
			jiraProperties.setUrl(prop.getProperty("jira.url"));
			jiraProperties.setApiVersion(prop.getProperty("jira.api.version"));
			jiraProperties.setApiProject(prop.getProperty("jira.api.project", ""));
			jiraProperties.setEnabled(Boolean.valueOf(prop.getProperty("jira.enabled")));
			jiraProperties.setAuthMethod(prop.getProperty("jira.auth.method"));
			jiraProperties.setAuthToken(prop.getProperty("jira.auth.token"));
			jiraProperties.setFieldName(prop.getProperty("jira.field.name", "summary"));
			jiraProperties.setFieldPersonDays(prop.getProperty("jira.field.personDays", ""));
			jiraProperties.setAgileVersion(prop.getProperty("jira.agile.version"));
			jiraProperties.setBrowser(prop.getProperty("jira.browser"));
			String boardId = prop.getProperty("jira.agile.board", null);
			if (boardId != null) {
				try {
					jiraProperties.setAgileBoard(Integer.parseInt(boardId));
				} catch (NumberFormatException e) {
					jiraProperties.setAgileBoard(null);
				}
			}
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, e.getMessage(), e);
			jiraProperties = new JiraProperties();
		}
	}

}
