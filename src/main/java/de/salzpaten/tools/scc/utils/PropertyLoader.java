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
			jiraProperties.setApiUrl(prop.getProperty("jira.api.url"));
			jiraProperties.setApiVersion(prop.getProperty("jira.api.version"));
			jiraProperties.setEnabled(Boolean.valueOf(prop.getProperty("jira.enabled")));
			jiraProperties.setAuthMethod(prop.getProperty("jira.auth.method"));
			jiraProperties.setAuthToken(prop.getProperty("jira.auth.token"));
			jiraProperties.setFieldName(prop.getProperty("jira.field.name", "summary"));
			jiraProperties.setFieldPersonDays(prop.getProperty("jira.field.personDays", ""));
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, e.getMessage(), e);
			jiraProperties = new JiraProperties();
		}
	}

}
