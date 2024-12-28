module frogger.refactor.coursework.main {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens uk.ac.nott.cs.comp2013.froggergame.controller to javafx.fxml;
    opens uk.ac.nott.cs.comp2013.froggergame.view to javafx.fxml;

    exports uk.ac.nott.cs.comp2013.froggergame.controller;
    exports uk.ac.nott.cs.comp2013.froggergame.view;
}
