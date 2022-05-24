package uni.views;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import uni.App;
import uni.controllers.CourseController;
import uni.models.Course;

public class CoursesView extends Accordion {
    private static CoursesView instance, studentInstance;
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

    public CoursesView update() throws IOException {
        this.getPanes().clear();
        courses.clear();
        for (Course course : Course.getCourses()) {
            TitledPane pane = Pane(course);
            this.getPanes().add(pane);
            courses.put(pane, course);
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
