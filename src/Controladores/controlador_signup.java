/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

import Modelo.usuario;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
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
        
    }
}
