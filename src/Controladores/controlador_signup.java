/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

import Modelo.usuario;
import java.io.BufferedWriter;
import java.io.FileWriter;
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
 * @author BENJAMIN
 */
public class controlador_signup implements Initializable {

    @FXML
    private TextField email;
    @FXML
    private PasswordField contraseña;
    @FXML
    private Button crear;
    @FXML
    private TextField nombres;
    @FXML
    private TextField apellidos;
    
    private metodos_generales modelo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void creacionCuenta(ActionEvent event) throws IOException {
        String mail, n, lastn, pass, id;
        mail=email.getText();
        n=nombres.getText();
        lastn=apellidos.getText();
        pass=contraseña.getText();
        id=modelo.generarIDUnico(modelo.obtenerIDsExistentes("src/Archivos/usuarios.txt"), "usuario");
        modelo.actual=modelo.guardarDatos(mail, pass, n, lastn, id);
        if(modelo.actual!=null){
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setHeaderText(null);
            alerta.setTitle("Exito");
            alerta.setContentText("Bienvenido "+n+" "+lastn);
            alerta.showAndWait();
            modelo.cargarFavoritos(modelo.actual.idu);
            modelo.cambioventana("/Vistas/vista_usuario.fxml", event,this.modelo);
        }else{
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setHeaderText(null);
            alerta.setTitle("Error");
            alerta.setContentText("Correo en uso");
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
    
