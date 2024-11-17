package com.example.linearalgebrasolver;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BuilMatrixOperations {

  //  public  VBox buildLayoutForOperations(){


       // VBox vBoxRoot = createMatrixControl("A");
       // return vBoxRoot;

    //} private
    int matrixCount = 0; // To track created matrices (A, B, C, D)

    public HBox buildLayoutForMatrixWithCreateButton() {
        HBox layout = new HBox(10);
        layout.setAlignment(Pos.CENTER);

        Button createMatrixButton = new Button("Create New Matrix");
        createMatrixButton.setOnAction(e -> createNewMatrix(layout));

        layout.getChildren().add(createMatrixButton);
        return layout;
    }

    private void createNewMatrix(HBox layout) {
        if (matrixCount < 4) { // Limit to 4 matrices
            char matrixName = (char) ('A' + matrixCount); // Determine the next matrix letter
            VBox newMatrix = createMatrixControl(String.valueOf(matrixName));
            layout.getChildren().add(newMatrix);
            matrixCount++;
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Maximum of 4 matrices created.");
            alert.showAndWait();
        }
    }
    private VBox createMatrixControl(String matrixName) {
        Label titleLabel = new Label(matrixName);
        titleLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");

        GridPane matrixGrid = new GridPane();
        matrixGrid.setGridLinesVisible(true); // To visualize the matrix cells
        matrixGrid.setAlignment(Pos.CENTER);

        // Track rows and columns explicitly
        int initialRows = 2;
        int initialCols = 2;

        // Add default rows and columns
        populateMatrixGrid(matrixGrid, initialRows, initialCols);

        // Buttons to adjust rows and columns
        VBox controls = new VBox(2);
        HBox controlsRows = new HBox(2);
        HBox controlsColumns = new HBox(2);
        Button addRowBtn = new Button("+ Row");
        Button removeRowBtn = new Button("- Row");
        Button addColBtn = new Button("+ Column");
        Button removeColBtn = new Button("- Column");

        controlsRows.setAlignment(Pos.CENTER);
        controlsRows.getChildren().addAll(addRowBtn, removeRowBtn);

        controlsColumns.setAlignment(Pos.CENTER);
        controlsColumns.getChildren().addAll(addColBtn, removeColBtn);

        controls.getChildren().addAll(controlsRows, controlsColumns);


        // Event handlers
        addRowBtn.setOnAction(e -> addRow(matrixGrid));
        removeRowBtn.setOnAction(e -> removeRow(matrixGrid));
        addColBtn.setOnAction(e -> addColumn(matrixGrid));
        removeColBtn.setOnAction(e -> removeColumn(matrixGrid));

        VBox matrixBox = new VBox(10);
        matrixBox.setAlignment(Pos.CENTER);
        matrixBox.getChildren().addAll(titleLabel, matrixGrid, controls);

        return matrixBox;
    }

    // Helper to populate the grid with TextFields
    private void populateMatrixGrid(GridPane grid, int rows, int cols) {
        grid.getChildren().clear(); // Clear previous contents
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                TextField cell = new TextField("0");
                cell.setPrefSize(40, 40);
                restrictToNumbers(cell);
                grid.add(cell, col, row);
            }
        }
    }

    // Add a new row to the matrix
    private void addRow(GridPane grid) {
        int rows = grid.getRowCount();
        int cols = grid.getColumnCount();
        for (int col = 0; col < cols; col++) {
            TextField cell = new TextField("0");
            cell.setPrefSize(40, 40);
            grid.add(cell, col, rows); // Add to the next row
        }
    }

    // Remove the last row
    private void removeRow(GridPane grid) {
        int rows = grid.getRowCount();
        if (rows > 1) { // Keep at least one row
            grid.getChildren().removeIf(node -> GridPane.getRowIndex(node) == rows - 1);
        }
    }

    // Add a new column to the matrix
    private void addColumn(GridPane grid) {
        int rows = grid.getRowCount();
        int cols = grid.getColumnCount();
        for (int row = 0; row < rows; row++) {
            TextField cell = new TextField("0");
            cell.setPrefSize(40, 40);
            grid.add(cell, cols, row); // Add to the next column
        }
    }

    // Remove the last column
    private void removeColumn(GridPane grid) {
        int cols = grid.getColumnCount();
        if (cols > 1) { // Keep at least one column
            grid.getChildren().removeIf(node -> GridPane.getColumnIndex(node) == cols - 1);
        }
    }
    private void restrictToNumbers(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) { // Allow only digits
                textField.setText(oldValue); // Revert to the previous valid value
            }
        });
    }
}
