package uni.views;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import uni.App;
import uni.controllers.DepartmentController;
import uni.models.Department;

public class DepartmentView extends Accordion {
    private static DepartmentView instance;

    private DepartmentView() {
    }

    public static DepartmentView getInstance() {
        if (instance == null)
            instance = new DepartmentView();
        return instance;
    }

    public void update() throws IOException {
        this.getPanes().clear();
        for (Department department : Department.getDepartments())
            this.getPanes().add(Pane(department));
    }

    private TitledPane Pane(Department department) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("department" + ".fxml"));
        TitledPane parent = fxmlLoader.load();
        ((DepartmentController) fxmlLoader.getController()).initialize(department);
        return parent;
    }
}