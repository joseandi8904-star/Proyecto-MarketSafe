/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * FXML Controller class
 *
 */
public class controlador_login implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    @FXML
    private Button ingresar;
    
    private metodos_generales modelo;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML
    private void iniciar(ActionEvent event) throws IOException {
        String em = email.getText();
        String pas = password.getText();
        modelo.actual=modelo.ConsultarArchivo("src/Archivos/usuarios.txt",em,pas);
        if (modelo.actual!=null){
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setHeaderText(null);
        alerta.setTitle("Exito");
        alerta.setContentText("iniciando sesion");
        alerta.showAndWait();
        modelo.cargarFavoritos(modelo.actual.idu);
        modelo.cambioventana("/Vistas/vista_usuario.fxml", event,this.modelo);
        }else{
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setHeaderText(null);
        alerta.setTitle("Error");
        alerta.setContentText("Usuario/Contraseña incorrecta");
        alerta.showAndWait();
        }
    }
    
    public void ModeloCompartido(metodos_generales modelo) {
    this.modelo = modelo;
}   

    @FXML
    private void volver(ActionEvent event) {
        modelo.cambioventana("/Vistas/vista_principal.fxml", event, this.modelo);
    }
    
}
