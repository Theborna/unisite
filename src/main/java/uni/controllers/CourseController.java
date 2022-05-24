package uni.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import uni.models.Course;

public class CourseController {
    @FXML
    Label name, credits, department, instructor;
    @FXML
    TitledPane pane;

    public void Initialize(Course course) {
        name.setText(course.getName());
        credits.setText(String.valueOf(course.getCredits()));
        department.setText(course.getDepartment().getName());
        instructor.setText(course.getInstructor().getName());
        pane.setText("course: " + name.getText());
    }
}