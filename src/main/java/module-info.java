module clinica {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires jakarta.mail;
    // Adicionado para suportar salvamento e PDF
    requires com.github.librepdf.openpdf;

    opens clinica.view.UIController to javafx.fxml;

    exports clinica;
    exports clinica.controller;
    exports clinica.model;
    exports clinica.repository;
    exports clinica.enums;
}