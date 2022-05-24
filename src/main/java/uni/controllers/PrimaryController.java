package uni.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import uni.App;
import uni.models.AcademicRank;
import uni.models.Course;
import uni.models.Department;
import uni.models.Professor;
import uni.models.Student;
import uni.views.CoursesView;
import uni.views.DepartmentView;
import uni.views.ProfessorView;
import uni.views.StudentsView;

public class PrimaryController {

    @FXML
    AnchorPane studentLogin, studentView, professorLogin, departmentLogin;
    @FXML
    TextField studentName, studentNumber, professorName, professorDepartment, departmentField;
    @FXML
    Label departmentError, studentError, courseError, profError;
    @FXML
    TextField studentIdField, studentNameField, studentDepartmentField, profNameField, profDepartmentField,
            courseNameField, courseDepartmentField, courseCreditsField, courseInstructorField, departmentNameField,
            departmentIdField;
    @FXML
    DatePicker profBirth, studentBirth;
    @FXML
    SplitMenuButton profRank;
    @FXML
    ScrollPane studentPane, profPane, departmentPane, coursePane;

    public void initialize() throws IOException {
        studentPane.setContent(StudentsView.getInstance());
        profPane.setContent(ProfessorView.getInstance());
        departmentPane.setContent(DepartmentView.getInstance());
        coursePane.setContent(CoursesView.getInstance());
    }

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    @FXML
    private void close() {
        System.exit(0);
    }

    @FXML
    private void logToStudent() {
        if (getStudent(studentName.getText(), studentNumber.getText()) != null) {
            return;
        }
        switchVisible(studentLogin, studentView);
    }

    @FXML
    private void setProfRank(ActionEvent event) {
        String next = ((MenuItem) event.getSource()).getText();
        profRank.setText(next);
    }

    private AcademicRank getRank(String rankString) {
        for (AcademicRank rank : AcademicRank.values())
            if (rank.toString().replace("_", "").equalsIgnoreCase(rankString.replaceAll(" ", "")))
                return rank;
        return null;
    }

    @FXML
    private void addDepartment() throws IOException {
        try {
            (new Department(departmentNameField.getText())).setId(departmentIdField.getText());
            DepartmentView.getInstance().update();
        } catch (Exception e) {
            departmentError.setText(e.getMessage());
        }
    }

    @FXML
    private void addStudent() throws IOException {
        try {
            new Student(studentIdField.getText()).setName(studentNameField.getText())
                    .setDepartment(Department.get(studentDepartmentField.getText()))
                    .setBirthDate(studentBirth.getValue());
            StudentsView.getInstance().update();
        } catch (Exception e) {
            studentError.setText(e.getMessage());
        }
    }

    @FXML
    private void addCourse() throws IOException {
        try {
            Course.add(
                    (new Course(courseNameField.getText())).setCredits(Integer.parseInt(courseCreditsField.getText()))
                            .setDepartment(Department.get(courseDepartmentField.getText()))
                            .setInstructor(Professor.get(courseInstructorField.getText())));
            System.out.println(Course.getCourses());
            CoursesView.getInstance().update();
        } catch (Exception e) {
            courseError.setText(e.getMessage());
        }
    }

    @FXML
    private void addProfessor() {
        try {
            new Professor(profNameField.getText()).setDepartment(Department.get(profDepartmentField.getText()))
                    .setBirthDate(profBirth.getValue()).setRank(getRank(profRank.getText()));
            ProfessorView.getInstance().update();
        } catch (Exception e) {
            profError.setText(e.getMessage());
        }
    }

    private void switchVisible(Pane pane1, Pane pane2) {
        if (pane1.isVisible()) {
            pane1.setVisible(false);
            pane2.setVisible(true);
        } else if (pane2.isVisible()) {
            pane1.setVisible(true);
            pane2.setVisible(false);
        }
    }

    private Student getStudent(String name, String number) {
        return null;
    }
}
