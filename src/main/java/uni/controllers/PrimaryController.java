package uni.controllers;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
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
    AnchorPane studentLogin, studentView, professorLogin, professorView, departmentLogin, departmentView,
            studentCourseDetails;
    @FXML
    TextField studentName, studentNumber, professorName, professorDepartment, departmentField;
    @FXML
    Label departmentError, studentError, courseError, profError, studentLoginError, teacherLoginError;
    @FXML
    TextField studentIdField, studentNameField, studentDepartmentField, profNameField, profDepartmentField,
            courseNameField, courseDepartmentField, courseCreditsField, courseInstructorField, departmentNameField,
            departmentIdField;
    @FXML
    DatePicker profBirth, studentBirth;
    @FXML
    SplitMenuButton profRank;
    @FXML
    ScrollPane studentPane, profPane, departmentPane, coursePane, allCourses1, allColleagues1;

    private Course selectedCourse = new Course("");
    private Student loggedStudent;
    private Professor loggedTeacher;

    public void initialize() throws IOException {
        studentPane.setContent(StudentsView.getInstance());
        departmentPane.setContent(DepartmentView.getInstance());
        profPane.setContent(ProfessorView.getInstance());
        coursePane.setContent(CoursesView.getInstance());
        allCourses1.setContent(CoursesView.getStudentInstance());
        allColleagues1.setContent(ProfessorView.getProfessorInstance());
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                for (TitledPane pane : CoursesView.getStudentInstance().getPanes())
                    if (pane.isExpanded())
                        selectedCourse = (CoursesView.getStudentInstance().getCourses().get(pane));
            }
        }, 0, 50);
    }

    @FXML
    private void switchToSecondary() throws IOException {
        System.out.println("kiramo bokhor");
    }

    @FXML
    private void close() {
        System.exit(0);
    }

    @FXML
    private void logToStudent() throws IOException {
        if ((loggedStudent = getStudent(studentName.getText(), studentNumber.getText())) == null) {
            studentLoginError.setText("no such student");
            return;
        }
        studentLoginError.setText("");
        CoursesView.getStudentInstance().update();
        switchVisible(studentLogin, studentView);
    }

    @FXML
    private void logToProfessor() throws IOException {
        if ((loggedTeacher = getProfessor(professorName.getText(), professorDepartment.getText())) == null) {
            teacherLoginError.setText("no such professor");
            return;
        }
        teacherLoginError.setText("");
        ProfessorView.getProfessorInstance().update();
        switchVisible(professorLogin, professorView);

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
            departmentError.setText("");
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
            studentError.setText("");
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
            CoursesView.getInstance().update();
            courseError.setText("");
        } catch (Exception e) {
            courseError.setText(e.getMessage());
        }
    }

    @FXML
    private void addProfessor() {
        try {
            Professor.add(
                    new Professor(profNameField.getText()).setDepartment(Department.get(profDepartmentField.getText()))
                            .setBirthDate(profBirth.getValue()).setRank(getRank(profRank.getText())));
            ProfessorView.getInstance().update();
            profError.setText("");
        } catch (Exception e) {
            profError.setText(e.getMessage());
        }
    }

    @FXML
    private void takeCourse() {
        loggedStudent.takeCourse(selectedCourse);
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
        for (Student student : Student.getStudents())
            if (student.getName().equals(name) && student.getStudent().equals(number))
                return student;
        return null;
    }

    private Professor getProfessor(String name, String department) {
        for (Professor professor : Professor.getProfessors())
            if (professor.getName().equals(name) && professor.getDepartment().getName().equals(department))
                return professor;
        return null;
    }
}
