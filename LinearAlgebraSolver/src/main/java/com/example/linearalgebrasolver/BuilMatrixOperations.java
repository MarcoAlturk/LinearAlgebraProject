package com.example.linearalgebrasolver;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.ejml.simple.SimpleMatrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BuilMatrixOperations {
    int matrixCount = 0; // To track created matrices (A, B, C, D)
    List<SimpleMatrix> matrices = new ArrayList<>(); // Store matrices for determinant calculation
    GridPane gridPaneValues = new GridPane(); // Grid to display determinants

    public HBox buildLayoutForMatrixWithCreateButton() {
        HBox layout = new HBox(10);
        layout.setAlignment(Pos.CENTER);

        Button createMatrixButton = new Button("Create New Matrix");
        createMatrixButton.setStyle("-fx-background-color: #3C99DC; -fx-text-fill: black; -fx-font-weight: bolder");
        createMatrixButton.setOnAction(e -> createNewMatrix(layout));

        layout.getChildren().add(createMatrixButton);
        return layout;
    }

    private void createNewMatrix(HBox layout) {
        if (matrixCount < 4) { // Limit to 4 matrices
            char matrixName = (char) ('A' + matrixCount); // Determine the next matrix letter
            VBox newMatrix = createMatrixControl(String.valueOf(matrixName));

            // Add a default 2x2 identity matrix as a placeholder
            matrices.add(new SimpleMatrix(2, 2));
            layout.getChildren().add(newMatrix);
            matrixCount++;
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Maximum of 4 matrices created.");
            alert.showAndWait();
        }
    }

    private VBox createMatrixControl(String matrixName) {
        Label titleLabel = new Label(matrixName);
        titleLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bolder; -fx-font-family: SansSerif");

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
        addRowBtn.setStyle("-fx-font-family: Arial; -fx-font-weight: bolder");
        Button removeRowBtn = new Button("- Row");
        removeRowBtn.setStyle("-fx-font-family: Arial; -fx-font-weight: bolder");
        Button addColBtn = new Button("+ Column");
        addColBtn.setStyle("-fx-font-family: Arial; -fx-font-weight: bolder");
        Button removeColBtn = new Button("- Column");
        removeColBtn.setStyle("-fx-font-family: Arial; -fx-font-weight: bolder");

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

        // Add new cells to the new row in the grid
        for (int col = 0; col < cols; col++) {
            TextField cell = new TextField("0");
            cell.setPrefSize(40, 40);
            restrictToNumbers(cell);
            grid.add(cell, col, rows); // Add to the next row
        }

        // Update the corresponding SimpleMatrix
        int index = getMatrixIndexForGrid(grid);
        if (index != -1) {
            SimpleMatrix matrix = matrices.get(index);

            // Create a new matrix with one more row and the same number of columns
            SimpleMatrix newMatrix = new SimpleMatrix(matrix.numRows() + 1, matrix.numCols());

            // Copy existing values to the new matrix
            for (int i = 0; i < matrix.numRows(); i++) {
                for (int j = 0; j < matrix.numCols(); j++) {
                    newMatrix.set(i, j, matrix.get(i, j)); // Copy existing values
                }
            }

            // Optionally, you can fill the new row with 0 (or any default value)
            for (int j = 0; j < matrix.numCols(); j++) {
                newMatrix.set(matrix.numRows(), j, 0); // New row filled with 0
            }

            matrices.set(index, newMatrix); // Update the matrix list
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

        // Add new cells to the new column
        for (int row = 0; row < rows; row++) {
            TextField cell = new TextField("0");
            cell.setPrefSize(40, 40);
            restrictToNumbers(cell);
            grid.add(cell, cols, row); // Add to the next column
        }

        // Update the corresponding SimpleMatrix
        int index = getMatrixIndexForGrid(grid);
        if (index != -1) {
            SimpleMatrix matrix = matrices.get(index);

            // Create a new matrix with the same number of rows and one more column
            SimpleMatrix newMatrix = new SimpleMatrix(matrix.numRows(), matrix.numCols() + 1);

            // Copy existing values to the new matrix
            for (int i = 0; i < matrix.numRows(); i++) {
                for (int j = 0; j < matrix.numCols(); j++) {
                    newMatrix.set(i, j, matrix.get(i, j)); // Copy existing values
                }
            }

            // Optionally, you can fill the new column with 0 (or any default value)
            for (int i = 0; i < matrix.numRows(); i++) {
                newMatrix.set(i, matrix.numCols(), 0); // New column filled with 0
            }

            matrices.set(index, newMatrix); // Update the matrix list
        }
    }







    // Remove the last column
    private void removeColumn(GridPane grid) {
        int cols = grid.getColumnCount();
        if (cols > 1) { // Keep at least one column
            // Remove the last column from the grid
            grid.getChildren().removeIf(node -> GridPane.getColumnIndex(node) == cols - 1);

            // Update the corresponding SimpleMatrix
            int index = getMatrixIndexForGrid(grid);
            if (index != -1) {
                SimpleMatrix matrix = matrices.get(index);
                matrix = matrix.extractMatrix(0, matrix.numRows(), 0, matrix.numCols() - 1); // Remove last column
                matrices.set(index, matrix); // Update the matrix list
            }
        }
    }

    private int getMatrixIndexForGrid(GridPane grid) {
        // Iterate over the matrices and check which one corresponds to the given grid
        for (int i = 0; i < matrices.size(); i++) {
            SimpleMatrix matrix = matrices.get(i);

            // Check if this matrix corresponds to the grid by matching the number of rows and columns
            if (matrix.numRows() == grid.getRowCount() && matrix.numCols() == grid.getColumnCount()) {
                return i; // Return the index if the dimensions match
            }
        }
        return -1; // Return -1 if no matching matrix found
    }
    private void restrictToNumbers(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("-?\\d*(\\.\\d*)?")) { // Allow numbers with optional negative sign and decimal
                textField.setText(oldValue);
            } else {
                int row = GridPane.getRowIndex(textField);
                int col = GridPane.getColumnIndex(textField);
                GridPane parentGrid = (GridPane) textField.getParent();

                // Update SimpleMatrix
                int index = getMatrixIndexForGrid(parentGrid);
                if (index != -1) {
                    SimpleMatrix matrix = matrices.get(index);
                    double value = newValue.isEmpty() ? 0 : Double.parseDouble(newValue);
                    matrix.set(row, col, value);
                }
            }
        });
    }


    public GridPane gridPaneButtons() {
        Button buttonDet = new Button("Determinants");
        buttonDet.setStyle("-fx-font-family: cursive; -fx-font-weight: bolder");

        // Action to calculate and display determinants
        buttonDet.setOnAction(e -> displayDeterminants());

        GridPane gridPane = new GridPane();
        gridPane.add(buttonDet, 0, 0);
        return gridPane;
    }

    public VBox containerForButtonsAndOutput() {
        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(gridPaneButtons(), gridPaneValues);
        return vBox;
    }

    private void displayDeterminants() {
        gridPaneValues.getChildren().clear();
        for (int i = 0; i < matrices.size(); i++) {
            SimpleMatrix matrix = matrices.get(i);
            double determinant = matrix.determinant();

            Label detLabel = new Label("det(" + (char) ('A' + i) + "): ");
            Label detValue = new Label(String.format("%.2f", determinant));

            gridPaneValues.add(detLabel, 0, i);
            gridPaneValues.add(detValue, 1, i);
        }
    }
}
