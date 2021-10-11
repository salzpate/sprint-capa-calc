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
package de.salzpaten.tools.scc.service.impl;

import java.io.Serializable;

/**
 * Properties for Jira integration
 *
 * @author Ronny Krammer
 *
 */
public class JiraProperties implements Serializable {

	private static final String DEFAULT_KEY = "key";

	private static final String DEFAULT_NAME = "summary";

	private static final String DEFAULT_VERSION = "latest";

	private static final String REST_AGILE = "rest/agile/";

	private static final String REST_API = "rest/api/";

	private static final long serialVersionUID = -2605780182236023908L;

	private Integer agileBoard;

	private String agileVersion;

	private String apiProject;

	private String apiVersion;

	private String authMethod;

	private String authToken;

	private String browser;

	private boolean enabled;

	private String fieldKey;

	private String fieldName;

	private String fieldPersonDays;

	private String url;

	public Integer getAgileBoard() {
		return agileBoard;
	}

	public String getAgileUrlWithVersion() {
		StringBuilder urlBuilder = new StringBuilder();
		if (url != null && !"".equals(url.trim())) {
			urlBuilder.append(url.trim());
			if (!url.trim().endsWith("/")) {
				urlBuilder.append("/");
			}
			urlBuilder.append(REST_AGILE);
			if (agileVersion == null || "".equals(agileVersion)) {
				urlBuilder.append(DEFAULT_VERSION).append("/");
			} else {
				urlBuilder.append(agileVersion).append("/");
			}
		}
		return urlBuilder.toString();
	}

	public String getAgileVersion() {
		return agileVersion;
	}

	public String getApiProject() {
		return apiProject;
	}

	public String getApiUrlWithVersion() {
		StringBuilder urlBuilder = new StringBuilder();
		if (url != null && !"".equals(url.trim())) {
			urlBuilder.append(url.trim());
			if (!url.trim().endsWith("/")) {
				urlBuilder.append("/");
			}
			urlBuilder.append(REST_API);
			if (apiVersion == null || "".equals(apiVersion)) {
				urlBuilder.append(DEFAULT_VERSION).append("/");
			} else {
				urlBuilder.append(apiVersion).append("/");
			}
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

	public String getBrowser() {
		return browser;
	}

	public String getFieldKey() {
		final String key;
		if (fieldKey == null || "".equals(fieldKey)) {
			key = DEFAULT_KEY;
		} else {
			key = fieldKey;
		}
		return key;
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

	public String getUrl() {
		return url;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setAgileBoard(Integer agileBoard) {
		this.agileBoard = agileBoard;
	}

	public void setAgileVersion(String agileVersion) {
		this.agileVersion = agileVersion;
	}

	public void setApiProject(String apiProject) {
		this.apiProject = apiProject;
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

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setFieldKey(String fieldKey) {
		this.fieldKey = fieldKey;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public void setFieldPersonDays(String fieldPersonDays) {
		this.fieldPersonDays = fieldPersonDays;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "JiraProperties [agileBoard=" + agileBoard + ", agileVersion=" + agileVersion + ", apiProject="
				+ apiProject + ", apiVersion=" + apiVersion + ", authMethod=" + authMethod + ", authToken=" + authToken
				+ ", browser=" + browser + ", enabled=" + enabled + ", fieldKey=" + fieldKey + ", fieldName="
				+ fieldName + ", fieldPersonDays=" + fieldPersonDays + ", url=" + url + "]";
	}

}
