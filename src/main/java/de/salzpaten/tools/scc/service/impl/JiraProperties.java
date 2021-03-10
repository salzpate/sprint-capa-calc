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

	private static final String DEFAULT_NAME = "summary";

	private static final String DEFAULT_VERSION = "latest";

	private static final long serialVersionUID = 677357818442914548L;

	private Integer agileBoard;

	private String agileUrl;

	private String agileVersion;

	private String apiProject;

	private String apiUrl;

	private String apiVersion;

	private String authMethod;

	private String authToken;

	private boolean enabled;

	private String fieldName;

	private String fieldPersonDays;

	public Integer getAgileBoard() {
		return agileBoard;
	}

	public String getAgileUrl() {
		return agileUrl;
	}

	public String getAgileUrlWithVersion() {
		StringBuilder urlBuilder = new StringBuilder();
		if (agileUrl != null && !"".equals(agileUrl.trim())) {
			urlBuilder.append(agileUrl.trim());
			if (!agileUrl.trim().endsWith("/")) {
				urlBuilder.append("/");
			}
			if (agileVersion == null || "".equals("")) {
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

	public String getApiUrl() {
		return apiUrl;
	}

	public String getApiUrlWithVersion() {
		StringBuilder urlBuilder = new StringBuilder();
		if (apiUrl != null && !"".equals(apiUrl.trim())) {
			urlBuilder.append(apiUrl.trim());
			if (!apiUrl.trim().endsWith("/")) {
				urlBuilder.append("/");
			}
			if (apiVersion == null || "".equals("")) {
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

	public void setAgileBoard(Integer agileBoard) {
		this.agileBoard = agileBoard;
	}

	public void setAgileUrl(String agileUrl) {
		this.agileUrl = agileUrl;
	}

	public void setAgileVersion(String agileVersion) {
		this.agileVersion = agileVersion;
	}

	public void setApiProject(String apiProject) {
		this.apiProject = apiProject;
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
		return "JiraProperties [agileBoard=" + agileBoard + ", agileUrl=" + agileUrl + ", agileVersion=" + agileVersion
				+ ", apiProject=" + apiProject + ", apiUrl=" + apiUrl + ", apiVersion=" + apiVersion + ", authMethod="
				+ authMethod + ", authToken=" + authToken + ", enabled=" + enabled + ", fieldName=" + fieldName
				+ ", fieldPersonDays=" + fieldPersonDays + "]";
	}

}