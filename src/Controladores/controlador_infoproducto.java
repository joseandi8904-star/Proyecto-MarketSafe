/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

import Modelo.producto;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 */
public class controlador_infoproducto implements Initializable {

    @FXML
    private ImageView image;
    @FXML
    private Label title;
    @FXML
    private Label price;
    @FXML
    private TextArea description;
    @FXML
    private Label stock;
    
    private metodos_generales modelo;
    
    private producto data;
    @FXML
    private Button compra;
    @FXML
    private TextField cant;
    @FXML
    private Button b_carrito;
    @FXML
    private Button logo;
    @FXML
    private ContextMenu H;
    @FXML
    private Button Options;
    @FXML
    private ImageView icono;
    @FXML
    private Button botonfav;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void ModeloCompartido(metodos_generales modelo, producto p) {
    this.modelo = modelo;
    this.data=p;
    cargarinfo();
}
    
    private void cargarinfo(){
        Image img = new Image(getClass().getResource(data.imagen).toExternalForm());
        image.setImage(img);
        title.setText(data.nombre);
        price.setText(String.valueOf(data.precio)+"$");
        description.setText("");
        stock.setText(String.valueOf(data.cantidad));
        if(data.cantidad==0){
            cant.setText("No stock");
            cant.setDisable(true);
        }
        description.setText(data.descripcion);
        actualizarEstadoFavorito();
    }

    @FXML
    private void abrirformulario(ActionEvent event) throws IOException {
        
    }

    @FXML
    private void agregarcarrito(ActionEvent event) {
        if (modelo.actual!=null){
            producto copia =new producto (data.idp,data.nombre,data.precio,data.imagen,0);
            modelo.agregarCarrito(copia);
            System.out.println("Producto agregado");
        }else{
            modelo.cambioventana("/Vistas/vista_signup.fxml", event,this.modelo);
        }
    }

    @FXML
    private void inicio(ActionEvent event) {
        if(modelo.actual!=null){
        modelo.cambioventana("/Vistas/vista_usuario.fxml", event, this.modelo);
        }else{
            modelo.cambioventana("/Vistas/vista_principal.fxml", event, this.modelo);
        }
    }

        @FXML
    private void abrirhistorial(ActionEvent event) {
     
    }

    @FXML
    private void mostraropciones(ActionEvent event) {
        H.show(Options, Side.BOTTOM,0,0);
    }

    @FXML
    private void crearproducto(ActionEvent event) {
    
    }

    @FXML
    private void salir(ActionEvent event) {
        modelo.cerrarsesion();
        modelo.cambioventana("/Vistas/vista_principal.fxml", event,this.modelo);
    }



    @FXML
    private void estado(ActionEvent event) {
        if(modelo.actual!=null){
            modelo.toggleFavorito(data);
            actualizarEstadoFavorito();
        }
    }
    
    private void actualizarEstadoFavorito() {
    if (modelo.estaEnFavoritos(data.idp)) {
        icono.setImage(new Image("/Imagenes/Iconos/favorito.png"));
    } else {
        icono.setImage(new Image("/Imagenes/Iconos/nofavorito.png"));
    }
}

    
}
