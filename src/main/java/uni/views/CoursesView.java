package uni.views;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import uni.App;
import uni.controllers.CourseController;
import uni.models.Course;
import uni.models.GradeReport;
import uni.models.Student;

public class CoursesView extends Accordion {
    private static CoursesView instance, studentInstance, currentStudentCoursesInstance, offeredCourses,
            departmentOfferedCourses;
    private Map<TitledPane, Course> courses = new HashMap<>();

    private CoursesView() {

    }

    public Map<TitledPane, Course> getCourses() {
        return courses;
    }

    public static CoursesView getInstance() {
        if (instance == null)
            instance = new CoursesView();
        return instance;
    }

    public static CoursesView getStudentInstance() {
        if (studentInstance == null)
            studentInstance = new CoursesView();
        return studentInstance;
    }

    public static CoursesView getCurrentStudentCoursesInstance() {
        if (currentStudentCoursesInstance == null)
            currentStudentCoursesInstance = new CoursesView();
        return currentStudentCoursesInstance;
    }

    public static CoursesView getOfferedCourses() {
        if (offeredCourses == null)
            offeredCourses = new CoursesView();
        return offeredCourses;
    }

    public static CoursesView getDepartmentOfferedCourses() {
        if (departmentOfferedCourses == null)
            departmentOfferedCourses = new CoursesView();
        return departmentOfferedCourses;
    }

    public CoursesView update() throws IOException {
        return update(Course.getCourses());
    }

    public CoursesView update(Collection<Course> courses) throws IOException {
        this.getPanes().clear();
        this.courses.clear();
        for (Course course : courses) {
            TitledPane pane = Pane(course);
            this.getPanes().add(pane);
            this.courses.put(pane, course);
        }
        return this;
    }

    public CoursesView addScore(Student student) {
        for (TitledPane pane : getPanes()) {
            Label item = new Label("score"), score = new Label();
            for (GradeReport report : student.getGradeReport())
                if (report.getCourse().equals(courses.get(pane))) {
                    score.setText(String.valueOf(report.getGrade()));
                    break;
                }
            item.setOpacity(0.65);
            ((VBox) pane.getContent()).getChildren().addAll(item, score);
        }
        return this;
    }

    private TitledPane Pane(Course course) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("courses" + ".fxml"));
        TitledPane parent = fxmlLoader.load();
        ((CourseController) fxmlLoader.getController()).Initialize(course);
        return parent;
    }
}
