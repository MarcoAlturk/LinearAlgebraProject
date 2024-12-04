package com.example.linearalgebrasolver;

import com.example.linearalgebrasolver.OperationsMatrices.DeterminantCalculator;
import com.example.linearalgebrasolver.OperationsMatrices.InverseCalculator;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BuilMatrixOperations {
    int matrixCount = 0; // To track created matrices (A, B, C, D)
    //List<SimpleMatrix> matrices = new ArrayList<>(); // Store matrices for determinant calculation

    public HBox buildLayoutForMatrixWithCreateButton() {
        HBox layout = new HBox(10);
        layout.setAlignment(Pos.CENTER);

        Button createMatrixButton = new Button("Create New Matrix");
        createMatrixButton.setStyle("-fx-background-color: #3C99DC; -fx-text-fill: white; -fx-font-weight: bolder");

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

    public HBox createMatrixCalculations() {
        // Create two independent GridPanes for each matrix
        GridPane matrixGrid1 = new GridPane();
        matrixGrid1.setGridLinesVisible(true); // To visualize the matrix cells
        matrixGrid1.setAlignment(Pos.CENTER);

        GridPane matrixGrid2 = new GridPane();
        matrixGrid2.setGridLinesVisible(true); // To visualize the matrix cells
        matrixGrid2.setAlignment(Pos.CENTER);

        // Track rows and columns explicitly
        int initialRows = 2;
        int initialCols = 2;

        // Populate both matrix grids with default rows and columns
        populateMatrixGrid(matrixGrid1, initialRows, initialCols);
        populateMatrixGrid(matrixGrid2, initialRows, initialCols);

        // Create controls for matrix adjustments
        VBox controls1 = createMatrixControls(matrixGrid1);
        VBox controls2 = createMatrixControls(matrixGrid2);

        // Create ComboBox for selecting operations (multiplication, addition, subtraction)
        ComboBox<String> operationsComboBox = new ComboBox<>();
        operationsComboBox.getItems().addAll("x", "+", "-");
        operationsComboBox.setValue("x");  // Default value

        // Button to calculate the result
        Button calculateBtn = new Button("Calculate");
        calculateBtn.setStyle("-fx-font-family: Arial; -fx-font-weight: bolder");

        // Event handler for calculation button
        calculateBtn.setOnAction(e -> {
            String operation = operationsComboBox.getValue();

            // Get matrix sizes
            int rows1 = matrixGrid1.getRowCount();
            int cols1 = matrixGrid1.getColumnCount();
            int rows2 = matrixGrid2.getRowCount();
            int cols2 = matrixGrid2.getColumnCount();

            if (operation.equals("x")) {
                // Check if multiplication is valid (rows1 == cols2)
                if (cols1 != rows2) {
                    showError("Matrix multiplication requires the number of columns of Matrix 1 to equal the number of rows of Matrix 2.");
                } else {
                    // Perform multiplication logic here and show result
                    GridPane resultMatrix = multiplyMatrices(matrixGrid1, matrixGrid2);
                    showMatrixResult(resultMatrix);
                }
            } else if (operation.equals("+") || operation.equals("-")) {
                // Check if addition/subtraction is valid (matrices must have the same dimensions)
                if (rows1 != rows2 || cols1 != cols2) {
                    showError("Matrix addition/subtraction requires both matrices to have the same dimensions.");
                } else {
                    // Perform addition or subtraction logic here and show result
                    GridPane resultMatrix = addOrSubtractMatrices(matrixGrid1, matrixGrid2, operation);
                    showMatrixResult(resultMatrix);
                }
            }
        });

        // Create VBox for the operation controls
        VBox operationBox = new VBox(10);
        operationBox.setAlignment(Pos.CENTER);
        operationBox.getChildren().addAll(operationsComboBox, calculateBtn);

        // Create VBox for each matrix
        VBox matrixBox1 = new VBox(10);
        matrixBox1.setAlignment(Pos.CENTER);
        matrixBox1.getChildren().addAll(matrixGrid1, controls1);

        VBox matrixBox2 = new VBox(10);
        matrixBox2.setAlignment(Pos.CENTER);
        matrixBox2.getChildren().addAll(matrixGrid2, controls2);

        // Create HBox to hold the two matrices and operation controls side by side
        HBox matrices = new HBox(10);
        matrices.setAlignment(Pos.CENTER);
        matrices.getChildren().addAll(matrixBox1, operationBox, matrixBox2);

        return matrices;
    }

    private VBox createMatrixControls(GridPane matrixGrid) {
        // Controls for adding/removing rows and columns
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

        // Arrange row controls
        controlsRows.setAlignment(Pos.CENTER);
        controlsRows.getChildren().addAll(addRowBtn, removeRowBtn);

        // Arrange column controls
        controlsColumns.setAlignment(Pos.CENTER);
        controlsColumns.getChildren().addAll(addColBtn, removeColBtn);

        // Add row and column controls together
        VBox controls = new VBox(2);
        controls.getChildren().addAll(controlsRows, controlsColumns);

        // Event handlers for row and column adjustments
        addRowBtn.setOnAction(e -> addRow(matrixGrid));
        removeRowBtn.setOnAction(e -> removeRow(matrixGrid));
        addColBtn.setOnAction(e -> addColumn(matrixGrid));
        removeColBtn.setOnAction(e -> removeColumn(matrixGrid));

        return controls;
    }

    private void showMatrixResult(GridPane resultMatrix) {
        // Ensure the resultMatrix has been populated properly
        if (resultMatrix.getChildren().isEmpty()) {
            // If the result matrix is empty, show an error
            showError("No result to display. Please check the matrix operation.");
            return;
        }

        // Create a new stage to display the result matrix
        Stage resultStage = new Stage();
        resultStage.setTitle("Result Matrix");

        // Set up a VBox to contain the result matrix and place it in the center
        VBox resultBox = new VBox(20); // Increased spacing between elements
        resultBox.setAlignment(Pos.CENTER);
        resultBox.getChildren().add(resultMatrix);

        // Create a scene for the result stage and set it
        Scene resultScene = new Scene(resultBox, 400, 300); // Wider and taller popup
        resultStage.setScene(resultScene);

        // Customize the VBox style (for background, padding, etc.)
        resultBox.setStyle("-fx-background-color: #ffffff; -fx-padding: 20px; -fx-border-radius: 10px;");

        // Optionally, add styling to the matrix itself (if needed)
        resultMatrix.setStyle("-fx-font-size: 16px; -fx-background-color: #ffffff; -fx-border-color: #dcdcdc; -fx-border-width: 1px; -fx-padding: 10px;");

        // Show the result window
        resultStage.show();
    }




    // Placeholder methods for matrix operations (multiply, add/subtract)
    private GridPane multiplyMatrices(GridPane matrix1, GridPane matrix2) {
        // Get the number of rows and columns for both matrices
        int rows1 = matrix1.getRowCount();
        int cols1 = matrix1.getColumnCount();
        int rows2 = matrix2.getRowCount();
        int cols2 = matrix2.getColumnCount();

        if (cols1 != rows2) {
            showError("Matrix multiplication requires the number of columns of Matrix 1 to equal the number of rows of Matrix 2.");
            return new GridPane(); // Return an empty GridPane on error
        }

        // Create a GridPane to store the result matrix
        GridPane resultMatrix = new GridPane();
        resultMatrix.setGridLinesVisible(true);
        resultMatrix.setAlignment(Pos.CENTER);

        // Perform matrix multiplication and populate resultMatrix
        for (int i = 0; i < rows1; i++) {
            for (int j = 0; j < cols2; j++) {
                double sum = 0;
                for (int k = 0; k < cols1; k++) {
                    // Get values from matrix1 and matrix2
                    double value1 = getCellValue(matrix1, i, k);
                    double value2 = getCellValue(matrix2, k, j);
                    sum += value1 * value2;
                }
                // Add the result to the resultMatrix
                resultMatrix.add(new Label(String.valueOf(sum)), j, i);
            }
        }

        return resultMatrix;
    }


    private GridPane addOrSubtractMatrices(GridPane matrix1, GridPane matrix2, String operation) {
        // Get the number of rows and columns for both matrices
        int rows1 = matrix1.getRowCount();
        int cols1 = matrix1.getColumnCount();
        int rows2 = matrix2.getRowCount();
        int cols2 = matrix2.getColumnCount();

        if (rows1 != rows2 || cols1 != cols2) {
            showError("Matrix addition/subtraction requires both matrices to have the same dimensions.");
            return new GridPane(); // Return an empty GridPane on error
        }

        // Create a GridPane to store the result matrix
        GridPane resultMatrix = new GridPane();
        resultMatrix.setGridLinesVisible(true);
        resultMatrix.setAlignment(Pos.CENTER);

        // Perform matrix addition or subtraction and populate resultMatrix
        for (int i = 0; i < rows1; i++) {
            for (int j = 0; j < cols1; j++) {
                double value1 = getCellValue(matrix1, i, j);
                double value2 = getCellValue(matrix2, i, j);
                double result = operation.equals("+") ? value1 + value2 : value1 - value2;
                resultMatrix.add(new Label(String.valueOf(result)), j, i);
            }
        }

        return resultMatrix;
    }

    private double getCellValue(GridPane grid, int row, int col) {
        Node node = getNodeByRowColumnIndex(row, col, grid);
        if (node instanceof TextField) {
            TextField cell = (TextField) node;
            try {
                return Double.parseDouble(cell.getText());
            } catch (NumberFormatException e) {
                showError("Invalid matrix value! Please enter numeric values.");
            }
        }
        return 0; // Default value in case of error
    }

    // Helper method to get node by row and column index
    private Node getNodeByRowColumnIndex(final int row, final int column, GridPane grid) {
        Node result = null;
        for (Node node : grid.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }
        return result;
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
        Button calculateDeterminantBtn = new Button("Calculate Determinant");
        calculateDeterminantBtn.setMaxWidth(Double.MAX_VALUE);
        calculateDeterminantBtn.setStyle("-fx-font-family: Arial; -fx-font-weight: bolder");
        Button calculateInverseBtn = new Button("Calculate Inverse");
        calculateInverseBtn.setStyle("-fx-font-family: Arial; -fx-font-weight: bolder");
        calculateInverseBtn.setMaxWidth(Double.MAX_VALUE);
        controlsRows.setAlignment(Pos.CENTER);
        controlsRows.getChildren().addAll(addRowBtn, removeRowBtn);

        controlsColumns.setAlignment(Pos.CENTER);
        controlsColumns.getChildren().addAll(addColBtn, removeColBtn);

        controls.getChildren().addAll(controlsRows, controlsColumns);

        controls.getChildren().add(calculateDeterminantBtn);
        controls.getChildren().add(calculateInverseBtn);


        // Event handlers
        addRowBtn.setOnAction(e -> addRow(matrixGrid));
        removeRowBtn.setOnAction(e -> removeRow(matrixGrid));
        addColBtn.setOnAction(e -> addColumn(matrixGrid));
        removeColBtn.setOnAction(e -> removeColumn(matrixGrid));
        calculateInverseBtn.setOnAction(e -> calculateMatrixInverse(matrixGrid));
        calculateDeterminantBtn.setOnAction(e -> {
            double[][] matrixData = getMatrixData(matrixGrid);
            if (matrixData[0].length != matrixData.length) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("The Matrix must be a square matrix!");
                alert.showAndWait();
            } else {
                calculateMatrixDeterminant(matrixGrid);
            }

        });

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

    }


    // Remove the last column
    private void removeColumn(GridPane grid) {
        int cols = grid.getColumnCount();
        if (cols > 1) { // Keep at least one column
            // Remove the last column from the grid
            grid.getChildren().removeIf(node -> GridPane.getColumnIndex(node) == cols - 1);

        }
    }



    private void restrictToNumbers(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Allow empty input or a single '-' at the start
            if (newValue.isEmpty() || newValue.equals("-")) {
                textField.setText(newValue);  // Keep the dash or empty
            }
            // Allow a negative number or a positive number with digits only
            else if (newValue.matches("-?\\d+")) {
                textField.setText(newValue);  // Keep digits or negative number
            } else {
                // Revert to the old value if the input is invalid
                textField.setText(oldValue);
            }
        });
    }

    public double[][] getMatrixData(GridPane grid) {
        int rows = grid.getRowCount();
        int cols = grid.getColumnCount();
        double[][] matrixData = new double[rows][cols];


        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                // Get the TextField for the current cell
                TextField cell = (TextField) getNodeByRowColumnIndex(row, col, grid);
                try {
                    // Parse the text in the TextField and assign it to the matrix
                    matrixData[row][col] = Double.parseDouble(cell.getText());
                } catch (NumberFormatException e) {
                    // Handle invalid input (you could show an alert or use a default value)
                    matrixData[row][col] = 0.0; // You can handle this differently depending on your needs
                }
            }
        }
        return matrixData;
    }



    private void calculateMatrixDeterminant(GridPane matrixGrid) {
        double[][] matrixData = getMatrixData(matrixGrid); // Extract matrix data
        double determinant = DeterminantCalculator.calculateDeterminant(matrixData); // Call the determinant method

        // Create a new stage to display the determinant
        Stage resultStage = new Stage();
        resultStage.setTitle("Matrix Determinant");

        // Set up a VBox to contain the result and place it in the center
        VBox resultBox = new VBox(20);
        resultBox.setAlignment(Pos.CENTER);

        // Add the result label for the determinant
        Label resultLabel = new Label("Determinant: " + determinant);
        resultLabel.setStyle("-fx-font-size: 16px; -fx-font-family: Arial; -fx-text-fill: #333;");

        resultBox.getChildren().add(resultLabel);

        // Create a scene for the result stage and set it
        Scene resultScene = new Scene(resultBox, 400, 200); // Larger popup for better readability
        resultStage.setScene(resultScene);

        // Set the style for the VBox (white background, padding)
        resultBox.setStyle("-fx-background-color: #ffffff; -fx-padding: 20px; -fx-border-radius: 10px;");

        // Show the result window
        resultStage.show();
    }


    private void calculateMatrixInverse(GridPane matrixGrid) {
        double[][] matrixData = getMatrixData(matrixGrid); // Extract matrix data
        double[][] inverse = InverseCalculator.calculateInverse(matrixData);

        // Check if the matrix is square
        if (matrixData[0].length != matrixData.length) {
            // Show error popup if not square
            showError("The Matrix must be a square matrix!");
            return;
        }

        // Check if the determinant is 0
        if (DeterminantCalculator.calculateDeterminant(matrixData) == 0) {
            // Show error popup if determinant is 0
            showError("The Matrix's determinant must be different than 0!");
            return;
        }

        // Build the result string for the inverse matrix
        StringBuilder result = new StringBuilder("Inverse Matrix:\n");
        for (double[] row : inverse) {
            for (double value : row) {
                if (Math.abs(value) < 1e-10) {
                    value = 0.0;
                }
                result.append(String.format("%8.2f", value)).append(" ");
            }
            result.append("\n");
        }

        // Create a new stage to display the result matrix
        Stage resultStage = new Stage();
        resultStage.setTitle("Matrix Inverse");

        // Set up a VBox to contain the result and place it in the center
        VBox resultBox = new VBox(20);
        resultBox.setAlignment(Pos.CENTER);

        // Add the result text
        Label resultLabel = new Label(result.toString());
        resultLabel.setStyle("-fx-font-size: 16px; -fx-font-family: Arial; -fx-text-fill: #333;");

        resultBox.getChildren().add(resultLabel);

        // Create a scene for the result stage and set it
        Scene resultScene = new Scene(resultBox, 400, 300); // Larger popup for better readability
        resultStage.setScene(resultScene);

        // Set the style for the VBox (white background, padding)
        resultBox.setStyle("-fx-background-color: #ffffff; -fx-padding: 20px; -fx-border-radius: 10px;");

        // Show the result window
        resultStage.show();
    }

    private void showError(String message) {
        // Create an error popup with a clean, styled design
        Stage errorStage = new Stage();
        errorStage.setTitle("Error");

        // Set up the VBox layout for the error message
        VBox errorBox = new VBox(20);
        errorBox.setAlignment(Pos.CENTER);

        // Add the error message label
        Label errorLabel = new Label(message);
        errorLabel.setStyle("-fx-font-size: 16px; -fx-font-family: Arial; -fx-text-fill: #d32f2f;");
        errorBox.getChildren().add(errorLabel);

        // Create the scene for the error popup and set it
        Scene errorScene = new Scene(errorBox, 400, 200);
        errorStage.setScene(errorScene);

        // Set the style for the error box
        errorBox.setStyle("-fx-background-color: #ffffff; -fx-padding: 20px; -fx-border-radius: 10px;");

        // Show the error window
        errorStage.show();
    }
}

