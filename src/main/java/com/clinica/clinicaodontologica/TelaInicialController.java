package com.clinica.clinicaodontologica;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class TelaInicialController {

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnSignIn;

    @FXML
    private TextField fieldLogin;

    @FXML
    private PasswordField fieldSenha;

    @FXML
    private Label txtFinja;

    @FXML
    private Label txtTitulo;

    @FXML
    void login(ActionEvent event) {
        if(!fieldLogin.getText().isEmpty() && !fieldSenha.getText().isEmpty())
        {
            txtFinja.setText("finja q vc deu login como \""+fieldLogin.getText()+"\".");
            fieldLogin.clear();
            fieldSenha.clear();
        }else{
            txtFinja.setText("Preencha os campos.");
        }
    }

    @FXML
    void signIn(ActionEvent event) {
        System.err.println("se cadastra uau");
    }

}
