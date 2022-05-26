package uni.controllers;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import uni.models.AcademicRank;
import uni.models.Course;
import uni.models.Department;
import uni.models.GradeReport;
import uni.models.Professor;
import uni.models.Student;
import uni.views.CoursesView;
import uni.views.DepartmentManagerView;
import uni.views.DepartmentView;
import uni.views.ProfessorView;
import uni.views.StudentsView;

public class PrimaryController {

    @FXML
    AnchorPane studentLogin, studentView, professorLogin, professorView, departmentLogin, departmentView;
    @FXML
    TextField studentName, studentNumber, professorName, professorDepartment, departmentField, submitScore;
    @FXML
    Label departmentError, studentError, courseError, profError, studentLoginError, teacherLoginError, submitScoreError,
            currentStudentScore, departmentLoginError;
    @FXML
    TextField studentIdField, studentNameField, studentDepartmentField, profNameField, profDepartmentField,
            courseNameField, courseDepartmentField, courseCreditsField, courseInstructorField, departmentNameField,
            departmentIdField;
    @FXML
    DatePicker profBirth, studentBirth;
    @FXML
    SplitMenuButton profRank;
    @FXML
    ScrollPane studentPane, profPane, departmentPane, coursePane, allCourses1, allColleagues1, currentStudentCourses,
            professorOfferedCourses, courseStudents;
    @FXML
    VBox studentDetails, professorDetails;

    private static boolean zeshtCode;
    private static Course selectedCourse = new Course("");
    private static Student loggedStudent, selectedStudent = new Student("");
    private static Professor loggedTeacher;
    private static boolean updated;

    public void initialize() throws IOException {
        departmentView.getChildren().add(DepartmentManagerView.getInstance().loadFXML());
        studentPane.setContent(StudentsView.getInstance());
        departmentPane.setContent(DepartmentView.getInstance());
        profPane.setContent(ProfessorView.getInstance());
        coursePane.setContent(CoursesView.getInstance());
        allCourses1.setContent(CoursesView.getStudentInstance());
        allColleagues1.setContent(ProfessorView.getProfessorInstance());
        currentStudentCourses.setContent(CoursesView.getCurrentStudentCoursesInstance());
        professorOfferedCourses.setContent(CoursesView.getOfferedCourses());
        courseStudents.setContent(StudentsView.getCourseInstance());
        Timer expansionTimer = new Timer();
        expansionTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Course last = selectedCourse;
                Student lasStudent = selectedStudent;
                for (TitledPane pane : CoursesView.getStudentInstance().getPanes())
                    if (pane.isExpanded() && (loggedStudent != null))
                        selectedCourse = (CoursesView.getStudentInstance().getCourses().get(pane));
                for (TitledPane pane : CoursesView.getOfferedCourses().getPanes())
                    if (pane.isExpanded() && (loggedTeacher != null))
                        selectedCourse = CoursesView.getOfferedCourses().getCourses().get(pane);
                for (TitledPane pane : CoursesView.getDepartmentOfferedCourses().getPanes())
                    if (pane.isExpanded() && (DepartmentManagerView.getInstance().getDepartment() != null))
                        selectedCourse = CoursesView.getDepartmentOfferedCourses().getCourses().get(pane);
                for (TitledPane pane : StudentsView.getCourseInstance().getPanes())
                    if (pane.isExpanded() && (loggedTeacher != null))
                        selectedStudent = StudentsView.getCourseInstance().getStudents().get(pane);
                for (TitledPane pane : StudentsView.getDepartmentInstance().getPanes())
                    if (pane.isExpanded() && (DepartmentManagerView.getInstance().getDepartment() != null))
                        selectedStudent = StudentsView.getDepartmentInstance().getStudents().get(pane);
                if (lasStudent != null)
                    if (!lasStudent.equals(selectedStudent))
                        update();
                if (last != null)
                    if (!last.equals(selectedCourse))
                        Platform.runLater(() -> {
                            try {
                                StudentsView.getCourseInstance().update(selectedCourse.getStudents());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
            }
        }, 0, 100);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (updated)
                        Platform.runLater(() -> {
                            try {
                                updated = false;
                                StudentsView.getInstance().update();
                                DepartmentView.getInstance().update();
                                CoursesView.getInstance().update();
                                CoursesView.getStudentInstance().update();
                                ProfessorView.getInstance().update();
                                ProfessorView.getProfessorInstance().update();
                                StudentsView.getDepartmentInstance().update();
                                if (loggedStudent != null)
                                    updateStudent();
                                if (loggedTeacher != null)
                                    updateTeacher();
                                if (DepartmentManagerView.getInstance().getDepartment() != null)
                                    updateDepartment();
                                if (zeshtCode) {
                                    zeshtCode = false;
                                    logToDepartment();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                }
            }
        });
        t.setDaemon(true);
        t.start();
    }

    private void updateStudent() throws IOException {
        CoursesView.getCurrentStudentCoursesInstance().update(loggedStudent.getCourses());
        CoursesView.getCurrentStudentCoursesInstance().addScore(loggedStudent);
        studentDetails.getChildren().clear();
        studentDetails.getChildren()
                .addAll(((VBox) StudentsView.getInstance().Pane(loggedStudent).getContent()).getChildren());
    }

    private void updateTeacher() throws IOException {
        CoursesView.getOfferedCourses().update(loggedTeacher.getCourse());
        StudentsView.getCourseInstance().update(selectedCourse.getStudents());
        professorDetails.getChildren().clear();
        professorDetails.getChildren()
                .addAll(((VBox) ProfessorView.getInstance().Pane(loggedTeacher).getContent()).getChildren());
        for (GradeReport report : selectedStudent.getGradeReport())
            if (report.getCourse().equals(selectedCourse)) {
                currentStudentScore.setText("score: " + report.getGrade());
                return;
            } else
                currentStudentScore.setText("no score available");
    }

    private void updateDepartment() throws IOException {
        ProfessorView.getFacultyInstance().update(DepartmentManagerView.getInstance().getDepartment().getFaculty());
        CoursesView.getDepartmentOfferedCourses()
                .update(DepartmentManagerView.getInstance().getDepartment().getCourses());
    }

    @FXML
    private void close() {
        System.exit(0);
    }

    @FXML
    private void logToStudent() throws IOException {
        update();
        if (loggedStudent == null) {
            if ((loggedStudent = getStudent(studentName.getText(), studentNumber.getText())) == null) {
                studentLoginError.setText("no such student");
                return;
            }
        } else
            loggedStudent = null;
        studentLoginError.setText("");
        CoursesView.getStudentInstance().update();
        switchVisible(studentLogin, studentView);
    }

    @FXML
    private void logToProfessor() throws IOException {
        update();
        if (loggedTeacher == null) {
            if ((loggedTeacher = getProfessor(professorName.getText(), professorDepartment.getText())) == null) {
                teacherLoginError.setText("no such professor");
                return;
            }
        } else
            loggedTeacher = null;
        teacherLoginError.setText("");
        ProfessorView.getProfessorInstance().update();
        switchVisible(professorLogin, professorView);
    }

    @FXML
    private void logToDepartment() {
        update();
        selectedStudent = new Student("");
        if (DepartmentManagerView.getInstance().getDepartment() == null) {
            if ((DepartmentManagerView.getInstance()
                    .setLoggedDepartment(getDepartment(departmentField.getText()))) == null) {
                departmentLoginError.setText("no such department");
                return;
            }
        } else
            DepartmentManagerView.getInstance().setLoggedDepartment(null);
        departmentLoginError.setText("");
        switchVisible(departmentLogin, departmentView);
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
            update();
            (new Department(departmentNameField.getText())).setId(departmentIdField.getText()).add();
            departmentError.setText("");
        } catch (Exception e) {
            departmentError.setText(e.getMessage());
        }
    }

    @FXML
    private void addStudent() throws IOException {
        try {
            update();
            new Student(studentIdField.getText()).setName(studentNameField.getText())
                    .setDepartment(Department.get(studentDepartmentField.getText()))
                    .setBirthDate(studentBirth.getValue()).addStudent();
            studentError.setText("");
        } catch (Exception e) {
            studentError.setText(e.getMessage());
        }
    }

    @FXML
    private void addCourse() throws IOException {
        try {
            update();
            new Course(courseNameField.getText()).setCredits(Integer.parseInt(courseCreditsField.getText()))
                    .setDepartment(Department.get(courseDepartmentField.getText()))
                    .setInstructor(Professor.get(courseInstructorField.getText())).add();
            courseError.setText("");
        } catch (Exception e) {
            courseError.setText(e.getMessage());
            if (e.getMessage().startsWith("For input"))
                courseError.setText("enter a valid number");

        }
    }

    @FXML
    private void switchToSecondary() throws IOException {
        update();
    }

    @FXML
    private void addProfessor() {
        try {
            update();
            new Professor(profNameField.getText()).setDepartment(Department.get(profDepartmentField.getText()))
                    .setBirthDate(profBirth.getValue()).setRank(getRank(profRank.getText())).add();
            profError.setText("");
        } catch (Exception e) {
            profError.setText(e.getMessage());
        }
    }

    @FXML
    private void takeCourse() {
        update();
        loggedStudent.takeCourse(selectedCourse);
    }

    @FXML
    private void submitScore() {
        try {
            update();
            selectedStudent.finishCourse(selectedCourse, Double.parseDouble(submitScore.getText()));
            submitScoreError.setText("");
        } catch (Exception e) {
            submitScoreError.setText(e.getMessage());
            if (e.getMessage().startsWith("For input"))
                submitScoreError.setText("enter a valid number");
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

    private Department getDepartment(String text) {
        for (Department department : Department.getDepartments())
            if (department.getName().equals(text))
                return department;
        return null;
    }

    public static void update() {
        updated = true;
    }

    public static void zesht() {
        zeshtCode = true;
        update();
    }

    public static Course getSelectedCourse() {
        return selectedCourse;
    }

    public static Student getSelectedStudent() {
        return selectedStudent;
    }
}
