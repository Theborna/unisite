package uni.controllers;

import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import uni.models.Student;

public class StudentController {
    @FXML
    Label name, id, department;
    @FXML
    DatePicker birth;
    @FXML
    TitledPane pane;

    public void initialize(Student student) {
        name.setText(student.getName());
        id.setText(student.getStudent());
        String departmentName;
        if ((student.getDepartment() == null) || (departmentName = student.getDepartment().getName()) == null)
            departmentName = "null";
        department.setText(departmentName);
        LocalDate birthDate = student.getBirthDate();
        if (birthDate != null)
            birth.setValue(birthDate);
        else
            birth.setPromptText("null");
        pane.setText("student: " + student.getStudent());
    }
}
