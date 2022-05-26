package uni.views;

import java.io.IOException;
import java.util.Collection;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import uni.App;
import uni.controllers.ProfessorController;
import uni.models.Professor;

public class ProfessorView extends Accordion {
    private static ProfessorView instance, professorInstance, facultyInstance;

    private ProfessorView() {
    }

    public static ProfessorView getInstance() {
        if (instance == null)
            instance = new ProfessorView();
        return instance;
    }

    public static ProfessorView getProfessorInstance() {
        if (professorInstance == null)
            professorInstance = new ProfessorView();
        return professorInstance;
    }

    public static ProfessorView getFacultyInstance() {
        if (facultyInstance == null)
            facultyInstance = new ProfessorView();
        return facultyInstance;
    }

    public static ProfessorView copy() throws IOException {
        return new ProfessorView().update();
    }

    public ProfessorView update() throws IOException {
        return update(Professor.getProfessors());
    }

    public ProfessorView update(Collection<Professor> professors) throws IOException {
        this.getPanes().clear();
        for (Professor professor : professors)
            this.getPanes().add(Pane(professor));
        return this;
    }

    public TitledPane Pane(Professor professor) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("professor" + ".fxml"));
        TitledPane parent = fxmlLoader.load();
        ProfessorController controller = fxmlLoader.getController();
        controller.initialize(professor);
        return parent;
    }
}
