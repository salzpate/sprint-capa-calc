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
package de.salzpaten.tools.scc.domain;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * Data for capacity calculation
 *
 * @author Ronny Krammer
 *
 */
public class CapaData {

	/**
	 * Person days of back-end members
	 */
	private DoubleProperty backend = new SimpleDoubleProperty(0);

	/**
	 * Person days of front-end members
	 */
	private DoubleProperty frontend = new SimpleDoubleProperty(0);

	/**
	 * Person days of all members
	 */
	private DoubleProperty personDays = new SimpleDoubleProperty(0);

	public CapaData() {
		super();
	}

	public CapaData(Double personDays, Double backend, Double frontend) {
		this.personDays.set(personDays);
		this.backend.set(backend);
		this.frontend.set(frontend);
	}

	public DoubleProperty backendProperty() {
		return backend;
	}

	public DoubleProperty frontendProperty() {
		return frontend;
	}

	public Double getBackend() {
		return backend.get();
	}

	public Double getFrontend() {
		return frontend.get();
	}

	public Double getPersonDays() {
		return personDays.get();
	}

	public DoubleProperty personDaysProperty() {
		return personDays;
	}

	public void setBackend(Double backend) {
		this.backend.set(backend);
	}

	public void setFrontend(Double frontend) {
		this.frontend.set(frontend);
	}

	public void setPersonDays(Double personDays) {
		this.personDays.set(personDays);
	}

}
