package uni.views;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import uni.App;
import uni.controllers.StudentController;
import uni.models.Student;

public class StudentsView extends Accordion {
    private static StudentsView instance;

    private StudentsView() {
    }

    public static StudentsView getInstance() {
        if (instance == null)
            instance = new StudentsView();
        return instance;
    }

    public void update() throws IOException {
        this.getPanes().clear();
        for (Student student : Student.getStudents())
            this.getPanes().add(Pane(student));
    }

    public TitledPane Pane(Student student) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("student" + ".fxml"));
        TitledPane parent = fxmlLoader.load();
        StudentController controller = fxmlLoader.getController();
        controller.initialize(student);
        return parent;
    }
}
