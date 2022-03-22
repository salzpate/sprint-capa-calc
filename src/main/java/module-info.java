/*
 * Copyright 2021 Ronny Krammer
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

/**
 * Module implementing Sprint Capacity Calculator
 */
module de.salzpaten.tools.scc {
	requires java.logging;

	requires javafx.base;
	requires transitive javafx.graphics;
	requires javafx.controls;
	requires javafx.fxml;
	requires java.net.http;
	requires com.google.gson;

	opens de.salzpaten.tools.scc to javafx.fxml;
	opens de.salzpaten.tools.scc.controller to javafx.fxml;
	opens de.salzpaten.tools.scc.domain to javafx.base;
	opens de.salzpaten.tools.scc.table to javafx.controls;
	opens de.salzpaten.tools.scc.utils to javafx.controls;

	exports de.salzpaten.tools.scc;
	exports de.salzpaten.tools.scc.controller;
	exports de.salzpaten.tools.scc.domain;
	exports de.salzpaten.tools.scc.service;
	exports de.salzpaten.tools.scc.service.impl;
}