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

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import de.salzpaten.tools.scc.domain.CalcTableData;
import de.salzpaten.tools.scc.domain.CapaData;
import de.salzpaten.tools.scc.domain.ComboBoxItem;
import de.salzpaten.tools.scc.service.DataService;
import de.salzpaten.tools.scc.table.PersonDaysCell;
import de.salzpaten.tools.scc.table.PrioSvgImageCell;
import de.salzpaten.tools.scc.table.SumDoubleCell;
import de.salzpaten.tools.scc.table.SumStringCell;
import de.salzpaten.tools.scc.table.TypeSvgImageCell;
import de.salzpaten.tools.scc.utils.SccUtils;
import de.salzpaten.tools.scc.utils.SccViewUtils;
import de.salzpaten.tools.scc.utils.converter.DoubleStringIfNumericConverter;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

/**
 * Main controller of the FXML Application
 *
 * @author Ronny Krammer
 *
 */
public class MainController implements Initializable {

	/**
	 * Standard Logger for GraalVM
	 */
	private static final Logger LOGGER = Logger.getLogger(MainController.class.getName());

	@FXML
	private Button bClear;

	@FXML
	private Button bCopyAsList;

	@FXML
	private Button bCopyAsMarkdown;

	@FXML
	private Button bCopyAsTable;

	@FXML
	private Button bSprintImport;

	private CapaData capaData = new CapaData(0d, 0d, 0d);

	@FXML
	private CheckBox cbCalc;

	private KeyCombination commandMinus = new KeyCodeCombination(KeyCode.SLASH, KeyCombination.SHORTCUT_DOWN);

	private KeyCombination commandPlus = new KeyCodeCombination(KeyCode.CLOSE_BRACKET, KeyCombination.SHORTCUT_DOWN);

	private KeyCombination commandZero = new KeyCodeCombination(KeyCode.NUMPAD0, KeyCombination.SHORTCUT_DOWN);

	private ContextMenu contextMenu;

	private DataService dataService;

	private ObjectBinding<Double> freeCapaBackendBinding;

	private ObjectBinding<Double> freeCapaFrontendBinding;

	private ObjectBinding<Double> freeCapaPersonDaysBinding;

	private CalcTableData freeCapaTableData = new CalcTableData("Free capacity", 0d, 0d, 0d, true);

	@FXML
	private Label lImport;

	@FXML
	private TableView<CalcTableData> mainTable;

	private MenuItem openInBrowser;

	@FXML
	private ProgressIndicator pJiraIndicator;

	private ObjectBinding<Double> plannedCapaBackendBinding;

	private ObjectBinding<Double> plannedCapaFrontendBinding;

	private ObjectBinding<Double> plannedCapaPersonDaysBinding;

	private CalcTableData plannedCapaTableData = new CalcTableData("Planned capacity", 0d, 0d, 0d, true);

	private RadioMenuItem prioHighestMenuItem = new RadioMenuItem("Highest", SccViewUtils.buildSVG("Highest"));

	private RadioMenuItem prioHighMenuItem = new RadioMenuItem("High", SccViewUtils.buildSVG("High"));

	private RadioMenuItem prioLowestMenuItem = new RadioMenuItem("Lowest", SccViewUtils.buildSVG("Lowest"));

	private RadioMenuItem prioLowMenuItem = new RadioMenuItem("Low", SccViewUtils.buildSVG("Low"));

	private RadioMenuItem prioMediumMenuItem = new RadioMenuItem("Medium", SccViewUtils.buildSVG("Medium"));

	@FXML
	private ProgressIndicator pSprintIndicator;

	private ComboBox<ComboBoxItem> sprintComboBox;

	private Dialog<ComboBoxItem> sprintDialog;

	@FXML
	private TableView<CalcTableData> sumTable;

	private ObservableList<CalcTableData> tableData;

	@FXML
	private TableColumn<CalcTableData, Boolean> tcActive;

	@FXML
	private TableColumn<CalcTableData, Double> tcBackend;

	@FXML
	private TableColumn<CalcTableData, Double> tcFrontend;

	@FXML
	private TableColumn<CalcTableData, String> tcKey;

	@FXML
	private TableColumn<CalcTableData, String> tcName;

	@FXML
	private TableColumn<CalcTableData, Double> tcPersonDays;

	@FXML
	private TableColumn<CalcTableData, String> tcPrio;

	@FXML
	private TableColumn<CalcTableData, Double> tcSumBackend;

	@FXML
	private TableColumn<CalcTableData, Double> tcSumFrontend;

	@FXML
	private TableColumn<CalcTableData, String> tcSumName;

	@FXML
	private TableColumn<CalcTableData, Double> tcSumPersonDays;

	@FXML
	private TableColumn<CalcTableData, String> tcType;

	@FXML
	private AnchorPane tfBackendPane;

	@FXML
	private TextField tfCapaBackend;

	@FXML
	private TextField tfCapaFrontend;

	@FXML
	private AnchorPane tfFrontendPane;

	@FXML
	private TextField tfName;

	@FXML
	private AnchorPane tfNamePane;

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
	 * Generates a task that loads sprints from Jira
	 *
	 * @param jqlText jqlText
	 * @return Task
	 */
	private Task<Void> buildJiraSprintTask() {
		return new Task<Void>() {

			private List<ComboBoxItem> sprintList;

			@Override
			protected Void call() throws Exception {
				Platform.runLater(() -> pSprintIndicator.setVisible(true));
				try {
					sprintList = dataService.getOpenSprints();
				} catch (IOException | InterruptedException e) {
					LOGGER.log(Level.SEVERE, e.getMessage(), e);
					Platform.runLater(() -> showErrorAlert(e, "Could not load sprints"));
					throw e;
				}
				return null;
			}

			@Override
			protected void done() {
				Platform.runLater(() -> pSprintIndicator.setVisible(false));
			}

			@Override
			protected void succeeded() {
				Platform.runLater(() -> {
					sprintComboBox.getItems().clear();
					sprintComboBox.getItems().addAll(sprintList);
					sprintComboBox.getSelectionModel().select(0);
				});
			}
		};
	}

	/**
	 * Generates a task that loads sprints from Jira
	 *
	 * @param jqlText jqlText
	 * @return Task
	 */
	private Task<Void> updateJiraIssuePriority(String key, String priorityName, CalcTableData data) {
		return new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				Platform.runLater(() -> pSprintIndicator.setVisible(true));
				try {
					dataService.changePriority(key, priorityName);;
				} catch (IOException | InterruptedException e) {
					LOGGER.log(Level.SEVERE, e.getMessage(), e);
					Platform.runLater(() -> showErrorAlert(e, "Error updating priority"));
					throw e;
				}
				return null;
			}

			@Override
			protected void done() {
				Platform.runLater(() -> pSprintIndicator.setVisible(false));
			}

			@Override
			protected void succeeded() {
				Platform.runLater(() -> {
					data.setPriorityName(priorityName);
				});
			}
		};
	}

	/**
	 * Generates a task that loads items from Jira using an ID or a JQL query
	 *
	 * @param jqlText jqlText
	 * @return Task
	 */
	private Task<Void> buildJiraTask(String jqlText) {
		return new Task<Void>() {

			private List<CalcTableData> filteredList;

			private boolean isJql = jqlText.contains("=");

			@Override
			protected Void call() throws Exception {
				Platform.runLater(() -> pJiraIndicator.setVisible(true));
				try {
					List<CalcTableData> calcTableDataList;
					if (isJql) {
						calcTableDataList = dataService.getCalcTableDataList(jqlText.trim());
					} else {
						calcTableDataList = List.of(dataService.getCalcTableData(jqlText.trim()));
					}
					filteredList = calcTableDataList.stream()
							.filter(c -> tableData.stream().noneMatch(t -> c.getName().equals(t.getName())))
							.collect(Collectors.toList());
				} catch (IOException | InterruptedException e) {
					LOGGER.log(Level.SEVERE, e.getMessage(), e);
					Platform.runLater(() -> showErrorAlert(e, String.format("Could not load data with '%s'", jqlText)));
					throw e;
				}
				return null;
			}

			@Override
			protected void done() {
				Platform.runLater(() -> pJiraIndicator.setVisible(false));
			}

			@Override
			protected void failed() {
				if (!isJql) {
					tableData.add(new CalcTableData(jqlText, 0d, 0d, 0d, true));
				}
			}

			@Override
			protected void succeeded() {
				Platform.runLater(() -> {
					if (filteredList != null || !filteredList.isEmpty()) {
						tableData.addAll(filteredList);
					} else if (!isJql) {
						tableData.add(new CalcTableData(jqlText, 0d, 0d, 0d, true));
					}
					bind();
				});
			}
		};
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
		bClear.visibleProperty().bind(tfName.textProperty().isNotEmpty());
		bClear.setOnAction(e -> Platform.runLater(() -> {
			tfName.setText("");
			tfName.requestFocus();
		}));

		bSprintImport.setOnAction(e -> onActionSprintImport());

		bCopyAsList.setOnAction(e -> onActionCopyTableDataAsTextList());
		bCopyAsMarkdown.setOnAction(e -> onActionCopyTableDataAsMarkdown());
		bCopyAsTable.setOnAction(e -> onActionCopyTableData());

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
		initSprintAlert();
		bind();
		Platform.runLater(() -> tfName.requestFocus());
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
		tcType.setCellValueFactory(cellData -> cellData.getValue().issuetypeNameProperty());
		tcType.setCellFactory(cell -> new TypeSvgImageCell());
		tcKey.setCellValueFactory(cellData -> cellData.getValue().keyProperty());
		tcKey.setCellFactory(TextFieldTableCell.forTableColumn());
		tcName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		tcName.setCellFactory(TextFieldTableCell.forTableColumn());
		tcPrio.setCellValueFactory(cellData -> cellData.getValue().priorityNameProperty());
		tcPrio.setCellFactory(cell -> new PrioSvgImageCell());
		tcPersonDays.setCellValueFactory(cellData -> cellData.getValue().personDaysProperty().asObject());
		tcPersonDays.setCellFactory(cell -> new PersonDaysCell());
		tcPersonDays.setOnEditCommit(t -> {
			t.getRowValue().setPersonDays(SccUtils.scaledDouble(t.getNewValue()));
			bind();
			mainTable.refresh();
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
			mainTable.refresh();
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
			mainTable.refresh();
		});

		MenuItem headItem = new MenuItem("Actions");
		headItem.getStyleClass().clear();
		headItem.getStyleClass().add("head-menu-item");

		MenuItem removeItem = new MenuItem("Remove item", SccViewUtils.buildSVG("Remove"));
		removeItem.setOnAction(e -> onActionRemove());

		MenuItem increaseFontSize = new MenuItem("Increase font size", SccViewUtils.buildSVG("Plus"));
		increaseFontSize.setAccelerator(new KeyCodeCombination(KeyCode.ADD, KeyCombination.SHORTCUT_DOWN));
		increaseFontSize.setOnAction(e -> onActionIncreaseFontSize());

		MenuItem decreaseFontSize = new MenuItem("Decrease font size", SccViewUtils.buildSVG("Minus"));
		decreaseFontSize.setAccelerator(new KeyCodeCombination(KeyCode.SUBTRACT, KeyCombination.SHORTCUT_DOWN));
		decreaseFontSize.setOnAction(e -> onActionDecreaseFontSize());

		MenuItem defaultFontSize = new MenuItem("Default font size", SccViewUtils.buildSVG("Dots"));
		defaultFontSize.setAccelerator(new KeyCodeCombination(KeyCode.DIGIT0, KeyCombination.SHORTCUT_DOWN));
		defaultFontSize.setOnAction(e -> onActionDefaultFontSize());

		contextMenu = new ContextMenu(headItem, removeItem, new SeparatorMenuItem(), increaseFontSize, defaultFontSize,
				decreaseFontSize);
		contextMenu.setOnShowing(e -> onShowingContext());

		tableData = FXCollections.observableArrayList();
		mainTable.setItems(tableData);
		mainTable.setContextMenu(contextMenu);
		mainTable.setOnKeyPressed(e -> {
			if (!mainTable.getSelectionModel().isEmpty() && e.getCode().equals(KeyCode.DELETE)) {
				onActionRemove();
			}
			if (commandPlus.match(e)) {
				onActionIncreaseFontSize();
			}
			if (commandMinus.match(e)) {
				onActionDecreaseFontSize();
			}
			if (commandZero.match(e)) {
				onActionDefaultFontSize();
			}
		});
	}

	private void initSprintAlert() {
		sprintComboBox = new ComboBox<ComboBoxItem>(FXCollections.observableArrayList());
		pSprintIndicator = new ProgressIndicator(ProgressIndicator.INDETERMINATE_PROGRESS);
		pSprintIndicator.setVisible(false);
		pSprintIndicator.setMaxHeight(24d);
		pSprintIndicator.setMaxWidth(24d);

		sprintDialog = new Dialog<>();
		sprintDialog.setTitle("Import from JIRA");
		sprintDialog.setHeaderText("Choose a Sprint");

		ButtonType importButtonType = new ButtonType("Import", ButtonData.OK_DONE);
		sprintDialog.getDialogPane().getButtonTypes().addAll(importButtonType, ButtonType.CANCEL);

		Label label = new Label("Sprint:");

		AnchorPane anchorPane = new AnchorPane();
		AnchorPane.setTopAnchor(label, 0d);
		AnchorPane.setLeftAnchor(label, 0d);
		AnchorPane.setBottomAnchor(label, 0d);

		AnchorPane.setTopAnchor(sprintComboBox, 0d);
		AnchorPane.setLeftAnchor(sprintComboBox, 48.0);
		AnchorPane.setRightAnchor(sprintComboBox, 38.0);
		AnchorPane.setBottomAnchor(sprintComboBox, 0d);

		AnchorPane.setTopAnchor(pSprintIndicator, 0d);
		AnchorPane.setRightAnchor(pSprintIndicator, 0d);
		AnchorPane.setBottomAnchor(pSprintIndicator, 0d);

		anchorPane.getChildren().addAll(label, sprintComboBox, pSprintIndicator);
		anchorPane.setPrefSize(620, 27);

		Node okButton = sprintDialog.getDialogPane().lookupButton(importButtonType);
		okButton.setDisable(true);
		sprintComboBox.valueProperty()
				.addListener((observable, oldValue, newValue) -> okButton.setDisable(newValue == null));

		sprintDialog.getDialogPane().setContent(anchorPane);
		Platform.runLater(() -> sprintComboBox.requestFocus());

		sprintDialog.setResultConverter(dialogButton -> {
			if (dialogButton == importButtonType) {
				return sprintComboBox.getSelectionModel().getSelectedItem();
			}
			return null;
		});
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
	 * Initialize backend text-field
	 */
	private void initTfBackendPane() {
		SccViewUtils.initTextfieldPaneFocus(tfCapaBackend, tfBackendPane, "sidebar-textfield",
				"sidebar-textfield-focus");
	}

	/**
	 * Initialize frontend text-field
	 */
	private void initTfFrontendPane() {
		SccViewUtils.initTextfieldPaneFocus(tfCapaFrontend, tfFrontendPane, "sidebar-textfield",
				"sidebar-textfield-focus");
	}

	/**
	 * Initialize name text-field
	 */
	private void initTfNamePane() {
		SccViewUtils.initTextfieldPaneFocus(tfName, tfNamePane, "main-textfield", "main-textfield-focus");
	}

	/**
	 * Adds an item to the table with the specified text
	 */
	private void onActionAdd() {
		String value = tfName.getText();

		if (dataService.isEnabled() && value != null && value.startsWith("jql:")) {
			String jql = value.substring(4).trim();
			new Thread(buildJiraTask(jql)).start();
			Platform.runLater(() -> tfName.setText(""));
		} else if (dataService.isEnabled() && value != null && value.contains("-") && !value.trim().contains(" ")) {
			new Thread(buildJiraTask(value.trim())).start();
			Platform.runLater(() -> tfName.setText(""));
		} else if (value != null && !"".equals(value)) {
			Platform.runLater(() -> {
				tableData.add(new CalcTableData(value, 0d, 0d, 0d, true));
				tfName.setText("");
				bind();
			});
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

	private void onActionDecreaseFontSize() {
		int size = SccUtils.getFontSizeFromStyle(mainTable.getStyle()) - 2;
		if (size > 0) {
			mainTable.setStyle("-fx-font-size: " + size);
		}
	}

	private void onActionDefaultFontSize() {
		mainTable.setStyle("");
	}

	private void onActionIncreaseFontSize() {
		int size = SccUtils.getFontSizeFromStyle(mainTable.getStyle()) + 2;
		mainTable.setStyle("-fx-font-size: " + size);
	}

	private void onActionOpenInBrowser() {
		if (mainTable.getSelectionModel().getSelectedItem() != null) {
			CalcTableData data = mainTable.getSelectionModel().getSelectedItem();
			dataService.openIdInBrowser(data.getKey());
		}
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

	private void onActionSprintImport() {
		new Thread(buildJiraSprintTask()).start();
		Optional<ComboBoxItem> result = sprintDialog.showAndWait();
		result.ifPresent(item -> {
			new Thread(buildJiraTask(dataService.buildOpenTaskFromSprintJql(item.getKey()))).start();
		});
	}

	private void onChangePriorityName(String priorityName) {
		if (mainTable.getSelectionModel().getSelectedItem() != null) {
			CalcTableData data = mainTable.getSelectionModel().getSelectedItem();
			if (!priorityName.equals(data.getPriorityName())) {
				new Thread(updateJiraIssuePriority(data.getKey(), priorityName, data)).start();
			}
		}
	}

	private void onShowingContext() {
		if (mainTable.getSelectionModel().getSelectedItem() != null) {
			CalcTableData data = mainTable.getSelectionModel().getSelectedItem();
			switch (data.getPriorityName()) {
			case "Highest":
				prioHighestMenuItem.setSelected(true);
				break;
			case "High":
				prioHighMenuItem.setSelected(true);
				break;
			case "Low":
				prioLowMenuItem.setSelected(true);
				break;
			case "Lowest":
				prioLowestMenuItem.setSelected(true);
				break;
			default:
				prioMediumMenuItem.setSelected(true);
				break;
			}
		} else {
			prioHighestMenuItem.setSelected(false);
			prioHighMenuItem.setSelected(false);
			prioLowMenuItem.setSelected(false);
			prioLowestMenuItem.setSelected(false);
			prioMediumMenuItem.setSelected(false);
		}
	}

	public void setDataService(DataService dataService) {
		this.dataService = dataService;
		if (dataService.isEnabled()) {
			tfName.setPromptText("Add Item or Jira ID or jql: Query");
		}
		if (dataService.isSprintEnabled()) {
			lImport.setVisible(true);
			bSprintImport.setVisible(true);
			tcKey.setVisible(true);
			tcType.setVisible(true);
			tcPrio.setVisible(true);

			openInBrowser = new MenuItem("Open in Browser", SccViewUtils.buildSVG("Link"));
			openInBrowser.setOnAction(e -> onActionOpenInBrowser());
			contextMenu.getItems().add(1, new SeparatorMenuItem());
			contextMenu.getItems().add(1, openInBrowser);

			ToggleGroup prioToggleGroup = new ToggleGroup();
			prioHighestMenuItem.setToggleGroup(prioToggleGroup);
			prioHighestMenuItem.setOnAction(e -> onChangePriorityName("Highest"));
			prioHighMenuItem.setToggleGroup(prioToggleGroup);
			prioHighMenuItem.setOnAction(e -> onChangePriorityName("High"));
			prioMediumMenuItem.setToggleGroup(prioToggleGroup);
			prioMediumMenuItem.setOnAction(e -> onChangePriorityName("Medium"));
			prioLowMenuItem.setToggleGroup(prioToggleGroup);
			prioLowMenuItem.setOnAction(e -> onChangePriorityName("Low"));
			prioLowestMenuItem.setToggleGroup(prioToggleGroup);
			prioLowestMenuItem.setOnAction(e -> onChangePriorityName("Lowest"));

			contextMenu.getItems().add(4, new SeparatorMenuItem());
			contextMenu.getItems().add(5, prioLowestMenuItem);
			contextMenu.getItems().add(5, prioLowMenuItem);
			contextMenu.getItems().add(5, prioMediumMenuItem);
			contextMenu.getItems().add(5, prioHighMenuItem);
			contextMenu.getItems().add(5, prioHighestMenuItem);
			contextMenu.setOnShowing(e -> onShowingContext());
		}
	}

	private void showErrorAlert(Throwable e, String contentText) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("JIRA Service");
		alert.setHeaderText("Error importing data");
		alert.setContentText(contentText);
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		String exceptionText = sw.toString();

		Label label = new Label("The exception stacktrace was:");

		TextArea textArea = new TextArea(exceptionText);
		textArea.setEditable(false);
		textArea.setWrapText(true);

		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(label, 0, 0);
		expContent.add(textArea, 0, 1);

		alert.getDialogPane().setExpandableContent(expContent);
		alert.showAndWait();
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
