package uni.controllers;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import uni.models.AcademicRank;
import uni.models.Course;
import uni.models.GradeReport;
import uni.models.Professor;
import uni.models.Student;
import uni.views.CoursesView;
import uni.views.DepartmentManagerView;
import uni.views.ProfessorView;
import uni.views.StudentsView;
import javafx.scene.control.MenuItem;

public class DepartmentManagerController {

    @FXML
    ScrollPane Faculty, offeredCourses, studentsPane;
    @FXML
    TextField profName, courseName, courseCredits, courseInstructor, studentName, studentId;
    @FXML
    DatePicker profBirth, studentBirth;
    @FXML
    SplitMenuButton profRank;
    @FXML
    Label studentError, profError, courseError, studentStatus;
    @FXML
    Button studentButton;

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
    private void addStudent() throws IOException {
        try {
            PrimaryController.update();
            new Student(studentId.getText()).setName(studentName.getText())
                    .setDepartment(DepartmentManagerView.getInstance().getDepartment())
                    .setBirthDate(studentBirth.getValue()).addStudent();
            studentError.setText("");
        } catch (Exception e) {
            studentError.setText(e.getMessage());
        }
    }

    @FXML
    private void addCourse() throws IOException {
        try {
            PrimaryController.update();
            new Course(courseName.getText()).setCredits(Integer.parseInt(courseCredits.getText()))
                    .setDepartment(DepartmentManagerView.getInstance().getDepartment())
                    .setInstructor(Professor.get(courseInstructor.getText())).add();
            courseError.setText("");
        } catch (Exception e) {
            courseError.setText(e.getMessage());
            if (e.getMessage().startsWith("For input"))
                courseError.setText("enter a valid number");

        }
    }

    @FXML
    private void addProfessor() {
        try {
            PrimaryController.update();
            new Professor(profName.getText()).setDepartment(DepartmentManagerView.getInstance().getDepartment())
                    .setBirthDate(profBirth.getValue()).setRank(getRank(profRank.getText())).add();
            profError.setText("");
        } catch (Exception e) {
            profError.setText(e.getMessage());
        }
    }

    @FXML
    private void studentAction(ActionEvent event) {
        String operation = ((Button) event.getSource()).getText();
        if (operation.equals("add student"))
            PrimaryController.getSelectedStudent().takeCourse(PrimaryController.getSelectedCourse());
        else if (operation.equals("remove student"))
            GradeReport.getGrades().removeIf(i -> i.getStudent().equals(PrimaryController.getSelectedStudent()));
    }

    @FXML
    private void logToDepartment() {
        PrimaryController.zesht();
    }

    public void initialize() {
        Faculty.setContent(ProfessorView.getFacultyInstance());
        offeredCourses.setContent(CoursesView.getDepartmentOfferedCourses());
        studentsPane.setContent(StudentsView.getDepartmentInstance());
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    String prompt = null;
                    if (PrimaryController.getSelectedStudent() == null) {
                        prompt = "please select a student";
                        studentButton.setText("");
                    } else if (PrimaryController.getSelectedCourse() == null) {
                        prompt = "no course selected";
                        studentButton.setText("");
                    } else if (PrimaryController.getSelectedStudent().getCourses()
                            .contains(PrimaryController.getSelectedCourse())) {
                        prompt = PrimaryController.getSelectedStudent().getName() + " score: ";
                        studentButton.setText("remove student");
                        for (GradeReport report : PrimaryController.getSelectedStudent().getGradeReport())
                            if (report.getCourse().equals(PrimaryController.getSelectedCourse()))
                                prompt += report.getGrade();
                    } else {
                        prompt = PrimaryController.getSelectedStudent().getName() + " does not have this course";
                        studentButton.setText("add student");
                    }
                    studentStatus.setText(prompt);
                });
            }
        }, 0, 100);
    }

}
