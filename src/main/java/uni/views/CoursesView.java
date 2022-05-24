package uni.views;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import uni.App;
import uni.controllers.CourseController;
import uni.models.Course;

public class CoursesView extends Accordion {
    private static CoursesView instance;

    private CoursesView() {
    }

    public static CoursesView getInstance() {
        if (instance == null)
            instance = new CoursesView();
        return instance;
    }

    public void update() throws IOException {
        this.getPanes().clear();
        for (Course course : Course.getCourses())
            this.getPanes().add(Pane(course));
    }

    private TitledPane Pane(Course course) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("courses" + ".fxml"));
        TitledPane parent = fxmlLoader.load();
        ((CourseController) fxmlLoader.getController()).Initialize(course);
        return parent;
    }
}
