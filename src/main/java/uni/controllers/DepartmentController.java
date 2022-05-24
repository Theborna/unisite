package uni.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import uni.models.Department;

public class DepartmentController {
    @FXML
    Label name, id;
    @FXML
    TitledPane pane;

    public void initialize(Department department) {
        name.setText(department.getName());
        id.setText(department.getId());
        pane.setText("department: " + name.getText());
    }
}
