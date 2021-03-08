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
import de.salzpaten.tools.scc.service.DataService;

/**
 *
 * Implementation of a service for getting table data from Jira
 *
 * @author Ronny Krammer
 *
 */
public class JiraDataServiceImpl implements DataService {

	private JiraProperties jiraProperties;

	public JiraDataServiceImpl(JiraProperties jiraProperties) {
		this.jiraProperties = jiraProperties;
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
			for (JsonElement item : issues) {
				CalcTableData calcTableData = new CalcTableData();
				calcTableData.setActive(true);
				JsonObject fields = item.getAsJsonObject().get("fields").getAsJsonObject();
				calcTableData.setName(fields.get(jiraProperties.getFieldName()).getAsString());
				if (jiraProperties.getFieldPersonDays() != null
						&& !"".equals(jiraProperties.getFieldPersonDays().trim())
						&& fields.get(jiraProperties.getFieldPersonDays()) != null) {
					calcTableData.setPersonDays(fields.get(jiraProperties.getFieldPersonDays()).getAsDouble());
				}
				dataList.add(calcTableData);
			}
		}
		return dataList;
	}

	@Override
	public boolean isEnabled() {
		return jiraProperties.isEnabled();
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
