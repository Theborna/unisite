package uni.controllers;

import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.text.Text;
import uni.models.Professor;

public class ProfessorController {
    @FXML
    Label name, rank, department;
    @FXML
    DatePicker birth;
    @FXML
    TitledPane pane;
    @FXML
    Text courses;

    public void initialize(Professor professor) {
        name.setText(professor.getName());
        rank.setText(professor.getRank().toString());
        String departmentName;
        if ((departmentName = professor.getDepartment().getName()) == null)
            departmentName = "null";
        department.setText(departmentName);
        LocalDate birthDate = professor.getBirthDate();
        if (birthDate != null)
            birth.setValue(birthDate);
        else
            birth.setPromptText("null");
        pane.setText("professor: " + professor.getName());
        courses.setText(professor.getCourse().toString().replaceAll("\\[|\\]", ""));
        if (courses.getText().length() == 0)
            courses.setText("none");
    }
}
