module uni {
    requires transitive javafx.controls;
    requires transitive javafx.fxml;
    requires transitive javafx.graphics;

    opens uni.controllers to javafx.fxml;

    exports uni;
}
