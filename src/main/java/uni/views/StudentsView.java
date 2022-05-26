package uni.views;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import uni.App;
import uni.controllers.StudentController;
import uni.models.Student;

public class StudentsView extends Accordion {
    private static StudentsView instance, courseInstance, departmentInstance;
    private Map<TitledPane, Student> students = new HashMap<>();

    private StudentsView() {
    }

    public static StudentsView getInstance() {
        if (instance == null)
            instance = new StudentsView();
        return instance;
    }

    public static StudentsView getCourseInstance() {
        if (courseInstance == null)
            courseInstance = new StudentsView();
        return courseInstance;
    }

    public static StudentsView getDepartmentInstance() {
        if (departmentInstance == null)
            departmentInstance = new StudentsView();
        return departmentInstance;
    }

    public Map<TitledPane, Student> getStudents() {
        return students;
    }

    public void update() throws IOException {
        update(Student.getStudents());
    }

    public void update(Collection<Student> students) throws IOException {
        this.getPanes().clear();
        this.students.clear();
        for (Student student : students) {
            TitledPane pane = Pane(student);
            this.getPanes().add(pane);
            this.students.put(pane, student);
        }
    }

    public TitledPane Pane(Student student) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("student" + ".fxml"));
        TitledPane parent = fxmlLoader.load();
        StudentController controller = fxmlLoader.getController();
        controller.initialize(student);
        return parent;
    }
}
