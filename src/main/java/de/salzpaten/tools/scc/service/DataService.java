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
package de.salzpaten.tools.scc.service;

import java.io.IOException;
import java.util.List;

import de.salzpaten.tools.scc.domain.CalcTableData;
import de.salzpaten.tools.scc.domain.ComboBoxItem;

/**
 * Service for getting table data from an external System
 *
 * @author Ronny Krammer
 *
 */
public interface DataService {

	/**
	 * Build the open tasks from Sprint JQL
	 *
	 * @param id ID from the sprint
	 * @return JQL
	 */
	String buildOpenTaskFromSprintJql(int id);

	/**
	 * Getting {@link CalcTableData} by Id
	 *
	 * @param id Id
	 * @return {@link CalcTableData}
	 * @throws IOException          IOException
	 * @throws InterruptedException InterruptedException
	 */
	CalcTableData getCalcTableData(String id) throws IOException, InterruptedException;

	/**
	 * Getting a List of {@link CalcTableData} by the given value
	 *
	 * @param value Value to get the list
	 * @return List of {@link CalcTableData}
	 * @throws IOException          IOException
	 * @throws InterruptedException InterruptedException
	 */
	List<CalcTableData> getCalcTableDataList(String value) throws IOException, InterruptedException;

	/**
	 * Get Open Sprints
	 *
	 * @return {@link ComboBoxItem} of open Sprints
	 * @throws IOException          IOException
	 * @throws InterruptedException InterruptedException
	 */
	List<ComboBoxItem> getOpenSprints() throws IOException, InterruptedException;

	/**
	 * Is the data service enabled
	 *
	 * @return data service enabled
	 */
	boolean isEnabled();

	/**
	 * Is the data service for sprint enabled
	 *
	 * @return data service for sprint enabled
	 */
	boolean isSprintEnabled();

}
