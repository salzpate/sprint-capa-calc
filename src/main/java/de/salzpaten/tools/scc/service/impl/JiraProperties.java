package de.salzpaten.tools.scc.service.impl;

import java.io.Serializable;

/**
 * Properties for Jira integration
 *
 * @author Ronny Krammer
 *
 */
public class JiraProperties implements Serializable {

	private static final String DEFAULT_NAME = "summary";

	private static final String DEFAULT_VERSION = "latest";

	private static final long serialVersionUID = 5562412694011018927L;

	private String apiUrl;

	private String apiVersion;

	private String authMethod;

	private String authToken;

	private boolean enabled;

	private String fieldName;

	private String fieldPersonDays;

	public String getApiUrl() {
		return apiUrl;
	}

	public String getApiUrlWithVersion() {
		StringBuilder urlBuilder = new StringBuilder(apiUrl.trim());
		if (!apiUrl.trim().endsWith("/")) {
			urlBuilder.append("/");
		}
		if (apiVersion == null || "".equals("")) {
			urlBuilder.append(DEFAULT_VERSION).append("/");
		} else {
			urlBuilder.append(apiVersion).append("/");
		}

		return urlBuilder.toString();
	}

	public String getApiVersion() {
		return apiVersion;
	}

	public String getAuthMethod() {
		return authMethod;
	}

	public String getAuthorizationHeader() {
		return authMethod + " " + authToken;
	}

	public String getAuthToken() {
		return authToken;
	}

	public String getFieldName() {
		final String name;
		if (fieldName == null || "".equals(fieldName)) {
			name = DEFAULT_NAME;
		} else {
			name = fieldName;
		}
		return name;
	}

	public String getFieldPersonDays() {
		return fieldPersonDays;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}

	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}

	public void setAuthMethod(String authMethod) {
		this.authMethod = authMethod;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public void setFieldPersonDays(String fieldPersonDays) {
		this.fieldPersonDays = fieldPersonDays;
	}

	@Override
	public String toString() {
		return "JiraProperties [apiUrl=" + apiUrl + ", apiVersion=" + apiVersion + ", authMethod=" + authMethod
				+ ", authToken=" + authToken + ", enabled=" + enabled + "]";
	}

}
