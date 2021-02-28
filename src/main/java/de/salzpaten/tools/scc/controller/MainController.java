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
package de.salzpaten.tools.scc.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import de.salzpaten.tools.scc.domain.CalcTableData;
import de.salzpaten.tools.scc.domain.CapaData;
import de.salzpaten.tools.scc.table.SumDoubleCell;
import de.salzpaten.tools.scc.table.SumStringCell;
import de.salzpaten.tools.scc.utils.SccUtils;
import de.salzpaten.tools.scc.utils.SccViewUtils;
import de.salzpaten.tools.scc.utils.converter.DoubleStringIfNumericConverter;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.SVGPath;

/**
 * Main controller of the FXML Application
 *
 * @author Ronny Krammer
 *
 */
public class MainController implements Initializable {

	private CapaData capaData = new CapaData(0d, 0d, 0d);

	@FXML
	private CheckBox cbCalc;

	private ObjectBinding<Double> freeCapaBackendBinding;

	private ObjectBinding<Double> freeCapaFrontendBinding;

	private ObjectBinding<Double> freeCapaPersonDaysBinding;

	private CalcTableData freeCapaTableData = new CalcTableData("Free capacity", 0d, 0d, 0d, true);

	@FXML
	private TableView<CalcTableData> mainTable;

	private ObjectBinding<Double> plannedCapaBackendBinding;

	private ObjectBinding<Double> plannedCapaFrontendBinding;

	private ObjectBinding<Double> plannedCapaPersonDaysBinding;

	private CalcTableData plannedCapaTableData = new CalcTableData("Planned capacity", 0d, 0d, 0d, true);

	@FXML
	private TableView<CalcTableData> sumTable;

	private ObservableList<CalcTableData> tableData;

	@FXML
	private TableColumn<CalcTableData, Double> tcBackend;

	@FXML
	private TableColumn<CalcTableData, Double> tcFrontend;

	@FXML
	private TableColumn<CalcTableData, Boolean> tcActive;

	@FXML
	private TableColumn<CalcTableData, String> tcName;

	@FXML
	private TableColumn<CalcTableData, Double> tcPersonDays;

	@FXML
	private TableColumn<CalcTableData, Double> tcSumBackend;

	@FXML
	private TableColumn<CalcTableData, Double> tcSumFrontend;

	@FXML
	private TableColumn<CalcTableData, String> tcSumName;

	@FXML
	private TableColumn<CalcTableData, Double> tcSumPersonDays;

	@FXML
	private TextField tfCapaBackend;

	@FXML
	private TextField tfCapaFrontend;

	@FXML
	private TextField tfName;

	@FXML
	private AnchorPane tfNamePane;

	@FXML
	private AnchorPane tfBackendPane;

	@FXML
	private AnchorPane tfFrontendPane;

	/**
	 * Bind all table fields
	 */
	private void bind() {
		Platform.runLater(() -> {
			unBind();
			initBinding();
			plannedCapaTableData.personDaysProperty().bind(plannedCapaPersonDaysBinding);
			plannedCapaTableData.backendProperty().bind(plannedCapaBackendBinding);
			plannedCapaTableData.frontendProperty().bind(plannedCapaFrontendBinding);

			freeCapaTableData.personDaysProperty().bind(freeCapaPersonDaysBinding);
			freeCapaTableData.backendProperty().bind(freeCapaBackendBinding);
			freeCapaTableData.frontendProperty().bind(freeCapaFrontendBinding);
		});
	}

	/**
	 * Initialize binding of table field
	 */
	private void initBinding() {
		plannedCapaPersonDaysBinding = Bindings.createObjectBinding(
				() -> tableData.stream().collect(Collectors.summingDouble(CalcTableData::getPersonDays)), tableData);
		plannedCapaBackendBinding = Bindings.createObjectBinding(
				() -> tableData.stream().collect(Collectors.summingDouble(CalcTableData::getBackend)), tableData);
		plannedCapaFrontendBinding = Bindings.createObjectBinding(
				() -> tableData.stream().collect(Collectors.summingDouble(CalcTableData::getFrontend)), tableData);

		freeCapaPersonDaysBinding = Bindings
				.createObjectBinding(() -> BigDecimal.valueOf(capaData.backendProperty().get())
						.add(BigDecimal.valueOf(capaData.frontendProperty().get())
								.subtract(BigDecimal.valueOf(tableData.stream().filter(CalcTableData::isActive)
										.collect(Collectors.summingDouble(CalcTableData::getPersonDays)))))
						.setScale(1, RoundingMode.HALF_UP).doubleValue(), tableData);
		freeCapaBackendBinding = Bindings.createObjectBinding(() -> BigDecimal.valueOf(capaData.backendProperty().get())
				.subtract(BigDecimal.valueOf(tableData.stream().filter(CalcTableData::isActive)
						.collect(Collectors.summingDouble(CalcTableData::getBackend))))
				.setScale(1, RoundingMode.HALF_UP).doubleValue(), tableData);
		freeCapaFrontendBinding = Bindings
				.createObjectBinding(() -> BigDecimal.valueOf(capaData.frontendProperty().get())
						.subtract(BigDecimal.valueOf(tableData.stream().filter(CalcTableData::isActive)
								.collect(Collectors.summingDouble(CalcTableData::getFrontend))))
						.setScale(1, RoundingMode.HALF_UP).doubleValue(), tableData);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tfName.setOnAction(e -> onActionAdd());

		capaData.backendProperty().bind(Bindings.createDoubleBinding(() -> {
			if (SccUtils.isNumeric(tfCapaBackend.textProperty().get())) {
				return Double.valueOf(tfCapaBackend.textProperty().get());
			} else {
				return 0d;
			}
		}, tfCapaBackend.textProperty()));
		tfCapaBackend.textProperty().addListener((observable, oldValue, newValue) -> bind());
		capaData.frontendProperty().bind(Bindings.createDoubleBinding(() -> {
			if (SccUtils.isNumeric(tfCapaFrontend.textProperty().get())) {
				return Double.valueOf(tfCapaFrontend.textProperty().get());
			} else {
				return 0d;
			}
		}, tfCapaFrontend.textProperty()));
		tfCapaFrontend.textProperty().addListener((observable, oldValue, newValue) -> bind());

		initTfBackendPane();
		initTfFrontendPane();
		initTfNamePane();
		initMainTable();
		initSumTable();
		bind();
		Platform.runLater(() -> tfName.requestFocus());
	}

	/**
	 * Adds the add icon to the name text-field. Cannot be done directly in FXML,
	 * otherwise compilation errors will occur with the Graalvm compiler
	 */
	private void initTfNamePane() {
		SVGPath svgIcon = SccViewUtils.addIcon();
		SccViewUtils.setIconAnchorPositions(svgIcon, 8, 0, 10);

		tfNamePane.getChildren().add(svgIcon);
		SccViewUtils.initTextfieldPaneFocus(tfName, tfNamePane, "main-textfield", "main-textfield-focus");
	}

	/**
	 * Adds the back-end icon to the name text-field. Cannot be done directly in
	 * FXML, otherwise compilation errors will occur with the Graalvm compiler
	 */
	private void initTfBackendPane() {
		SVGPath svgIcon = SccViewUtils.backendIcon();
		SccViewUtils.setIconAnchorPositions(svgIcon, 6, 0, 8);

		tfBackendPane.getChildren().add(svgIcon);
		SccViewUtils.initTextfieldPaneFocus(tfCapaBackend, tfBackendPane, "sidebar-textfield",
				"sidebar-textfield-focus");
	}

	/**
	 * Adds the front-end icon to the name text-field. Cannot be done directly in
	 * FXML, otherwise compilation errors will occur with the Graalvm compiler
	 */
	private void initTfFrontendPane() {
		SVGPath svgIcon = SccViewUtils.frontendIcon();
		SccViewUtils.setIconAnchorPositions(svgIcon, 5, 0, 8);

		tfFrontendPane.getChildren().add(svgIcon);
		SccViewUtils.initTextfieldPaneFocus(tfCapaFrontend, tfFrontendPane, "sidebar-textfield",
				"sidebar-textfield-focus");
	}

	/**
	 * Initialize the main table and its columns
	 */
	private void initMainTable() {
		tcActive.setCellValueFactory(cellData -> cellData.getValue().activeProperty());
		tcActive.setCellFactory(CheckBoxTableCell.forTableColumn(p -> {
			bind();
			return tableData.get(p).activeProperty();
		}));
		tcActive.setOnEditCommit(t -> {
			t.getRowValue().setActive(t.getNewValue());
			bind();
		});
		tcName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		tcName.setCellFactory(TextFieldTableCell.forTableColumn());
		tcPersonDays.setCellValueFactory(cellData -> cellData.getValue().personDaysProperty().asObject());
		tcPersonDays.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringIfNumericConverter()));
		tcPersonDays.setOnEditCommit(t -> {
			t.getRowValue().setPersonDays(SccUtils.scaledDouble(t.getNewValue()));
			bind();
		});
		tcFrontend.setCellValueFactory(cellData -> cellData.getValue().frontendProperty().asObject());
		tcFrontend.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringIfNumericConverter()));
		tcFrontend.setOnEditCommit(t -> {
			Double newValue = SccUtils.scaledDouble(t.getNewValue());
			CalcTableData rowValue = t.getRowValue();
			rowValue.setFrontend(newValue);
			if (cbCalc.isSelected()) {
				rowValue.setBackend(SccUtils.subtractDouble(rowValue.getPersonDays(), newValue));
			}
			bind();
		});
		tcBackend.setCellValueFactory(cellData -> cellData.getValue().backendProperty().asObject());
		tcBackend.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringIfNumericConverter()));
		tcBackend.setOnEditCommit(t -> {
			Double newValue = SccUtils.scaledDouble(t.getNewValue());
			CalcTableData rowValue = t.getRowValue();
			rowValue.setBackend(newValue);
			if (cbCalc.isSelected()) {
				rowValue.setFrontend(SccUtils.subtractDouble(rowValue.getPersonDays(), newValue));
			}
			bind();
		});

		MenuItem headItem = new MenuItem("Actions");
		headItem.getStyleClass().clear();
		headItem.getStyleClass().add("head-menu-item");

		MenuItem removeItem = new MenuItem("Remove item");
		removeItem.setOnAction(e -> onActionRemove());

		MenuItem textListItem = new MenuItem("Copy items as list");
		textListItem.setOnAction(e -> onActionCopyTableDataAsTextList());

		MenuItem copyItem = new MenuItem("Copy items as table");
		copyItem.setOnAction(e -> onActionCopyTableData());

		MenuItem copyMarkdownItem = new MenuItem("Copy items as markdown");
		copyMarkdownItem.setOnAction(e -> onActionCopyTableDataAsMarkdown());

		ContextMenu contextMenu = new ContextMenu(headItem, removeItem, new SeparatorMenuItem(), textListItem, copyItem,
				copyMarkdownItem);

		tableData = FXCollections.observableArrayList();
		mainTable.setItems(tableData);
		mainTable.setContextMenu(contextMenu);
	}

	/**
	 * Initialize the summary table and its columns
	 */
	private void initSumTable() {
		tcSumName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		tcSumName.setCellFactory(column -> new SumStringCell());
		tcSumPersonDays.setCellValueFactory(cellData -> cellData.getValue().personDaysProperty().asObject());
		tcSumPersonDays.setCellFactory(column -> new SumDoubleCell());
		tcSumFrontend.setCellValueFactory(cellData -> cellData.getValue().frontendProperty().asObject());
		tcSumFrontend.setCellFactory(column -> new SumDoubleCell());
		tcSumBackend.setCellValueFactory(cellData -> cellData.getValue().backendProperty().asObject());
		tcSumBackend.setCellFactory(column -> new SumDoubleCell());

		ObservableList<CalcTableData> sumTableData = FXCollections
				.observableArrayList(List.of(plannedCapaTableData, freeCapaTableData));
		sumTable.setItems(sumTableData);
		sumTable.setSelectionModel(null);
	}

	/**
	 * Adds an item to the table with the specified text
	 */
	private void onActionAdd() {
		Platform.runLater(() -> {
			tableData.add(new CalcTableData(tfName.getText(), 0d, 0d, 0d, true));
			tfName.setText("");
		});
		bind();
	}

	/**
	 * Removes the selected item
	 */
	private void onActionRemove() {

		final String name;
		if (mainTable.getSelectionModel().getSelectedItem().getName() != null
				&& mainTable.getSelectionModel().getSelectedItem().getName().length() > 21) {
			name = mainTable.getSelectionModel().getSelectedItem().getName().substring(0, 21);
		} else {
			name = mainTable.getSelectionModel().getSelectedItem().getName();
		}

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Remove Item");
		alert.setHeaderText(String.format("Remove '%s'", name));

		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			Platform.runLater(() -> tableData.remove(mainTable.getSelectionModel().getSelectedIndex()));
		}
	}

	/**
	 * Copy table as fixed length text to clip-board
	 */
	private void onActionCopyTableData() {
		Platform.runLater(() -> {
			final ClipboardContent content = new ClipboardContent();
			content.putString(SccUtils.buildTableDataAsTable(tableData));
			Clipboard.getSystemClipboard().setContent(content);
		});
	}

	/**
	 * Copy table as markdown table to clip-board
	 */
	private void onActionCopyTableDataAsMarkdown() {
		Platform.runLater(() -> {
			final ClipboardContent content = new ClipboardContent();
			content.putString(SccUtils.buildTableDataAsMarkdown(tableData));
			Clipboard.getSystemClipboard().setContent(content);
		});
	}

	/**
	 * Copy table as markdown table to clip-board
	 */
	private void onActionCopyTableDataAsTextList() {
		Platform.runLater(() -> {
			final ClipboardContent content = new ClipboardContent();
			content.putString(SccUtils.buildTableDataAsListText(tableData));
			Clipboard.getSystemClipboard().setContent(content);
		});
	}

	/**
	 * Unbind the table field
	 */
	private void unBind() {
		if (plannedCapaPersonDaysBinding != null) {
			Bindings.unbindContent(plannedCapaTableData, plannedCapaPersonDaysBinding);
		}
		if (plannedCapaBackendBinding != null) {
			Bindings.unbindContent(plannedCapaTableData, plannedCapaBackendBinding);
		}
		if (plannedCapaFrontendBinding != null) {
			Bindings.unbindContent(plannedCapaTableData, plannedCapaFrontendBinding);
		}
		if (freeCapaPersonDaysBinding != null) {
			Bindings.unbindContent(freeCapaTableData, freeCapaPersonDaysBinding);
		}
		if (freeCapaBackendBinding != null) {
			Bindings.unbindContent(freeCapaTableData, freeCapaBackendBinding);
		}
		if (freeCapaFrontendBinding != null) {
			Bindings.unbindContent(freeCapaTableData, freeCapaFrontendBinding);
		}
	}

}
