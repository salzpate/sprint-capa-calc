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

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Data for the table view
 *
 * @author Ronny Krammer
 *
 */
public class CalcTableData {

	/**
	 * Is active
	 */
	private BooleanProperty active = new SimpleBooleanProperty(true);

	/**
	 * Person days of back-end members
	 */
	private DoubleProperty backend = new SimpleDoubleProperty(0);

	/**
	 * Needed person days of front-end members
	 */
	private DoubleProperty frontend = new SimpleDoubleProperty(0);

	/**
	 * Name of issue type
	 */
	private StringProperty issuetypeName = new SimpleStringProperty("");

	/**
	 * Key of issue
	 */
	private StringProperty key = new SimpleStringProperty("");

	/**
	 * Name of issue
	 */
	private StringProperty name = new SimpleStringProperty("");

	/**
	 * Needed person days
	 */
	private DoubleProperty personDays = new SimpleDoubleProperty(0);

	/**
	 * Name of priority
	 */
	private StringProperty priorityName = new SimpleStringProperty("");

	public CalcTableData() {
		super();
	}

	public CalcTableData(String name, double personDays, double backend, double frontend, boolean active) {
		this.name.set(name);
		this.personDays.set(personDays);
		this.backend.set(backend);
		this.frontend.set(frontend);
		this.active.set(active);
	}

	public BooleanProperty activeProperty() {
		return active;
	}

	public DoubleProperty backendProperty() {
		return backend;
	}

	public DoubleProperty frontendProperty() {
		return frontend;
	}

	public double getBackend() {
		return backend.get();
	}

	public double getFrontend() {
		return frontend.get();
	}

	public String getIssuetypeName() {
		return issuetypeName.get();
	}

	public String getKey() {
		return key.get();
	}

	public String getName() {
		return name.get();
	}

	public double getPersonDays() {
		return personDays.get();
	}

	public String getPriorityName() {
		return priorityName.get();
	}

	public boolean isActive() {
		return active.get();
	}

	public StringProperty issuetypeNameProperty() {
		return issuetypeName;
	}

	public StringProperty keyProperty() {
		return key;
	}

	public StringProperty nameProperty() {
		return name;
	}

	public DoubleProperty personDaysProperty() {
		return personDays;
	}

	public StringProperty priorityNameProperty() {
		return priorityName;
	}

	public void setActive(boolean active) {
		this.active.set(active);
	}

	public void setBackend(double backend) {
		this.backend.set(backend);
	}

	public void setFrontend(double frontend) {
		this.frontend.set(frontend);
	}

	public void setIssuetypeName(String issuetypeName) {
		this.issuetypeName.set(issuetypeName);
	}

	public void setKey(String key) {
		this.key.set(key);
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public void setPersonDays(double personDays) {
		this.personDays.set(personDays);
	}

	public void setPriorityName(String priorityName) {
		this.priorityName.set(priorityName);
	}

	@Override
	public String toString() {
		return "CalcTableData [active=" + active.get() + ", backend=" + backend.get() + ", frontend=" + frontend.get()
				+ ", issuetypeName=" + issuetypeName.get() + ", key=" + key.get() + ", name=" + name.get()
				+ ", personDays=" + personDays.get() + ", priorityName=" + priorityName.get() + "]";
	}

}
