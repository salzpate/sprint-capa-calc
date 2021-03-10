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

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import de.salzpaten.tools.scc.domain.CalcTableData;
import de.salzpaten.tools.scc.domain.ComboBoxItem;
import de.salzpaten.tools.scc.service.DataService;

/**
 *
 * Implementation of a service for getting table data from Jira
 *
 * @author Ronny Krammer
 *
 */
public class JiraDataServiceImpl implements DataService {

	private static final String OPEN_ISSUES_FROM_SPRINT_JQL = "Project=%s AND sprint=%s AND resolution = Unresolved AND issuetype not in subTaskIssueTypes()";

	private JiraProperties jiraProperties;

	public JiraDataServiceImpl(JiraProperties jiraProperties) {
		this.jiraProperties = jiraProperties;
	}

	/**
	 * Builds a agile {@link HttpRequest}
	 *
	 * @param uri part of the uri
	 * @return {@link HttpRequest}
	 */
	private HttpRequest buildAgileHttpRequest(String uri) {
		return HttpRequest.newBuilder().uri(URI.create(jiraProperties.getAgileUrlWithVersion() + uri))
				.timeout(Duration.ofMinutes(1)).header("Content-Type", "application/json")
				.setHeader("Authorization", jiraProperties.getAuthorizationHeader()).GET().build();
	}

	/**
	 * Builds a {@link HttpRequest}
	 *
	 * @param uri part of the uri
	 * @return {@link HttpRequest}
	 */
	private HttpRequest buildHttpRequest(String uri) {
		return HttpRequest.newBuilder().uri(URI.create(jiraProperties.getApiUrlWithVersion() + uri))
				.timeout(Duration.ofMinutes(1)).header("Content-Type", "application/json")
				.setHeader("Authorization", jiraProperties.getAuthorizationHeader()).GET().build();
	}

	@Override
	public String buildOpenTaskFromSprintJql(int id) {
		return String.format(OPEN_ISSUES_FROM_SPRINT_JQL, jiraProperties.getApiProject(), id);
	}

	@Override
	public CalcTableData getCalcTableData(String id) throws IOException, InterruptedException {
		CalcTableData calcTableData = new CalcTableData();
		calcTableData.setActive(true);
		final String issueUri;
		if (jiraProperties.getFieldPersonDays() == null || "".equals(jiraProperties.getFieldPersonDays().trim())) {
			issueUri = String.format("issue/%s?fields=%s", id, jiraProperties.getFieldName());
		} else {
			issueUri = String.format("issue/%s?fields=%s,%s", id, jiraProperties.getFieldName(),
					jiraProperties.getFieldPersonDays());
		}
		HttpResponse<String> response = send(buildHttpRequest(issueUri));
		if (response.statusCode() == 200) {
			JsonElement root = JsonParser.parseString(response.body());
			JsonObject fields = root.getAsJsonObject().get("fields").getAsJsonObject();
			calcTableData.setName(fields.get(jiraProperties.getFieldName()).getAsString());
			if (jiraProperties.getFieldPersonDays() != null && !"".equals(jiraProperties.getFieldPersonDays().trim())
					&& fields.get(jiraProperties.getFieldPersonDays()) != null) {
				calcTableData.setPersonDays(fields.get(jiraProperties.getFieldPersonDays()).getAsDouble());
			}
		} else {
			calcTableData.setName(id);
		}
		return calcTableData;
	}

	@Override
	public List<CalcTableData> getCalcTableDataList(String value) throws IOException, InterruptedException {
		List<CalcTableData> dataList = new ArrayList<>();
		final String issueUri;
		if (jiraProperties.getFieldPersonDays() == null || "".equals(jiraProperties.getFieldPersonDays().trim())) {
			issueUri = String.format("search/?jql=%s&fields=%s", URLEncoder.encode(value, StandardCharsets.UTF_8),
					jiraProperties.getFieldName());
		} else {
			issueUri = String.format("search/?jql=%s&fields=%s,%s", URLEncoder.encode(value, StandardCharsets.UTF_8),
					jiraProperties.getFieldName(), jiraProperties.getFieldPersonDays());
		}
		HttpResponse<String> response = send(buildHttpRequest(issueUri));
		if (response.statusCode() == 200) {
			JsonElement root = JsonParser.parseString(response.body());
			JsonArray issues = root.getAsJsonObject().get("issues").getAsJsonArray();
			try {
				for (JsonElement item : issues) {
					CalcTableData calcTableData = new CalcTableData();
					calcTableData.setActive(true);
					JsonObject fields = item.getAsJsonObject().get("fields").getAsJsonObject();
					calcTableData.setName(fields.get(jiraProperties.getFieldName()).getAsString());
					if (jiraProperties.getFieldPersonDays() != null
							&& !"".equals(jiraProperties.getFieldPersonDays().trim())
							&& fields.get(jiraProperties.getFieldPersonDays()) != null
							&& !fields.get(jiraProperties.getFieldPersonDays()).isJsonNull()) {
						calcTableData.setPersonDays(fields.get(jiraProperties.getFieldPersonDays()).getAsDouble());
					}
					dataList.add(calcTableData);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dataList;
	}

	@Override
	public List<ComboBoxItem> getOpenSprints() throws IOException, InterruptedException {
		List<ComboBoxItem> dataList = new ArrayList<>();
		final String agileUri = String.format("board/%s/sprint?state=active,future", jiraProperties.getAgileBoard());
		HttpResponse<String> response = send(buildAgileHttpRequest(agileUri));
		if (response.statusCode() == 200) {
			JsonElement root = JsonParser.parseString(response.body());
			JsonArray values = root.getAsJsonObject().get("values").getAsJsonArray();
			for (JsonElement item : values) {
				ComboBoxItem comboBoxItem = new ComboBoxItem();
				comboBoxItem.setKey(item.getAsJsonObject().get("id").getAsInt());
				comboBoxItem.setName(item.getAsJsonObject().get("name").getAsString());
				dataList.add(comboBoxItem);
			}
		}
		return dataList;
	}

	@Override
	public boolean isEnabled() {
		return jiraProperties.isEnabled();
	}

	@Override
	public boolean isSprintEnabled() {
		return jiraProperties.isEnabled() && jiraProperties.getAgileBoard() != null
				&& jiraProperties.getAgileBoard() > -1;
	}

	/**
	 * Send the HttpRequest and get a HttpResponse
	 *
	 * @param request {@link HttpRequest}
	 * @return {@link HttpResponse}
	 * @throws IOException          IOException
	 * @throws InterruptedException InterruptedException
	 */
	private HttpResponse<String> send(HttpRequest request) throws IOException, InterruptedException {
		return HttpClient.newBuilder().build().send(request, BodyHandlers.ofString());
	}

}
