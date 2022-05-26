package uni.views;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import uni.App;
import uni.controllers.DepartmentManagerController;
import uni.models.Department;

public class DepartmentManagerView extends AnchorPane {

    private static DepartmentManagerView instance;
    private AnchorPane pane;
    private DepartmentManagerController controller;
    private Department loggedDepartment;

    private DepartmentManagerView() {
        super();
    }

    public static DepartmentManagerView getInstance() {
        if (instance == null)
            instance = new DepartmentManagerView();
        return instance;
    }

    public AnchorPane getPane() {
        return pane;
    }

    public AnchorPane loadFXML() throws IOException {
        FXMLLoader fxml = new FXMLLoader(App.class.getResource("departmentView.fxml"));
        this.pane = fxml.load();
        instance.getChildren().add(pane);
        AnchorPane.setTopAnchor(pane, 0.0);
        AnchorPane.setBottomAnchor(pane, 0.0);
        AnchorPane.setRightAnchor(pane, 0.0);
        AnchorPane.setLeftAnchor(pane, 0.0);
        controller = ((DepartmentManagerController) fxml.getController());
        controller.initialize();
        return pane;
    }

    public Department getDepartment() {
        return loggedDepartment;
    }

    public Department setLoggedDepartment(Department loggedDepartment) {
        this.loggedDepartment = loggedDepartment;
        return loggedDepartment;
    }

}
