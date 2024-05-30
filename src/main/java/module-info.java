module engcalc {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens engcalc to javafx.fxml;

    exports engcalc;
}
