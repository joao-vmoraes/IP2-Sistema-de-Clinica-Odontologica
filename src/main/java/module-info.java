module clinica {
    requires javafx.controls;
    requires javafx.fxml;
    // Adicionado para suportar salvamento e PDF
    requires java.desktop;
    requires com.github.librepdf.openpdf;

    opens clinica.view.UIController to javafx.fxml;

    exports clinica;
    exports clinica.controller;
    exports clinica.model;
    exports clinica.repository;
    exports clinica.enums;
}