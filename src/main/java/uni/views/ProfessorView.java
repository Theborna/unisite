package uni.views;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import uni.App;
import uni.controllers.ProfessorController;
import uni.models.Professor;

public class ProfessorView extends Accordion {
    private static ProfessorView instance;

    private ProfessorView() {
    }

    public static ProfessorView getInstance() {
        if (instance == null)
            instance = new ProfessorView();
        return instance;
    }

    public void update() throws IOException {
        this.getPanes().clear();
        for (Professor professor : Professor.getProfessors())
            this.getPanes().add(Pane(professor));
    }

    public TitledPane Pane(Professor professor) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("professor" + ".fxml"));
        TitledPane parent = fxmlLoader.load();
        ProfessorController controller = fxmlLoader.getController();
        controller.initialize(professor);
        return parent;
    }
}
