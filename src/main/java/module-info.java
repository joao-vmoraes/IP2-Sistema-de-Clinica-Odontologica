module clinica {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires com.github.librepdf.openpdf;

    // 1. Abre o pacote do Controller da UI para o JavaFX (Reflection/FXML)
    // Se o seu FXML tenta criar uma instância do controller, ele PRECISA estar aberto.
    opens clinica.view.UIController to javafx.fxml;

    // 2. Exporta o pacote principal (Onde está o AppFX.java)
    exports clinica;

    // 3. Exporta pacotes de Serviço/Regra de Negócio
    exports clinica.controller;
    exports clinica.model;
    exports clinica.repository;
    exports clinica.enums;
}