package uni.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import uni.App;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}