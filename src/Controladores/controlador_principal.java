/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

import Modelo.Nodo_LS;
import Modelo.producto;
import java.io.IOException;
import java.net.URL;
import java.util.List;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * FXML Controller class
 *
 * @author BENJAMIN
 */
public class controlador_principal implements Initializable {

    @FXML
    private HBox homecatalogo;
    
    private metodos_generales modelo;
    private int inicio = 0;
    private final int ELEMENTOS_POR_PAGINA = 5;
    
    @FXML
    private Button existente;
    @FXML
    private Button nuevo;
    @FXML
    private Button derecha;
    @FXML
    private Button izquierda;
    @FXML
    private TextField busqueda;
    @FXML
    private ScrollPane contenedor;
    @FXML
    private VBox resultados;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        busqueda.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.trim().isEmpty()) {
                mostrarResultados(newVal.trim());
            } else {
                ocultarResultados();
            }
        });
    }
    
    @FXML
    private void entrar(ActionEvent event) throws IOException{
        modelo.cambioventana("/Vistas/vista_login.fxml", event,this.modelo);
    }
    @FXML
    private void crear(ActionEvent event) throws IOException{
        modelo.cambioventana("/Vistas/vista_signup.fxml", event,this.modelo);
    }

    @FXML
    private void siguiente(ActionEvent event) {
        if (inicio + ELEMENTOS_POR_PAGINA < modelo.tamañoListaSen()) {
        inicio += ELEMENTOS_POR_PAGINA;
        cargarPagina();
        System.out.println("Avanzando...");
    }
    }

    @FXML
    private void anterior(ActionEvent event) {
       if (inicio >= ELEMENTOS_POR_PAGINA) {
        inicio -= ELEMENTOS_POR_PAGINA;
        cargarPagina();
        System.out.println("Retrocediendo...");
    }
    }
    
    private void cargarPagina() {
    homecatalogo.getChildren().clear();
    List<producto> productosPagina = modelo.obtenerProductosPagina(inicio, ELEMENTOS_POR_PAGINA);
    
    for (producto prod : productosPagina) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/producto.fxml"));
            VBox productoVBox = loader.load();
            controlador_producto controller = loader.getController();
            controller.agregarproducto(prod, modelo);
            productoVBox.setOnMouseClicked(e -> {
                modelo.datosProducto("/Vistas/vista_infoproducto.fxml", prod,modelo);
                Stage ventanaActual = (Stage) ((Node) e.getSource()).getScene().getWindow();
                ventanaActual.close();
            });
            homecatalogo.getChildren().add(productoVBox);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
    
    public void ModeloCompartido(metodos_generales modelo) {
    this.modelo = modelo;
    modelo.antiduplicados();
    cargarPagina(); 
}

    @FXML
    private void buscarcatalogo(KeyEvent event) {
    String texto = busqueda.getText().toLowerCase();
    resultados.getChildren().clear();

    Nodo_LS<producto> aux = modelo.cab_s;
    while (aux != null) {
        producto p = aux.dato;
        if (p.nombre.toLowerCase().contains(texto)) {
            Label resultado = new Label(p.nombre);
            resultado.setStyle("-fx-padding: 5; -fx-font-size: 14px;");
            resultados.getChildren().add(resultado);
        }
        aux = aux.sig;
    }
    }

    private void mostrarResultados(String texto) {
    resultados.getChildren().clear();
    boolean coincidencia= false;

    Nodo_LS <producto> existencias = modelo.cab_s;
    while (existencias!=null) {
        if (existencias.dato.nombre.toLowerCase().contains(texto.toLowerCase())) {
            coincidencia=true;
            Label item = new Label(existencias.dato.nombre);
            item.wrapTextProperty();
            item.setStyle("-fx-cursor: hand; -fx-background-color: #f0f0f0; -fx-padding: 5;");
            producto prod = modelo.buscarProductoPorID(existencias.dato.idp);
            item.setOnMouseClicked(e -> {
                modelo.datosProducto("/Vistas/vista_infoproducto.fxml", prod,modelo);
                Stage ventanaActual = (Stage) ((Node) e.getSource()).getScene().getWindow();
                ventanaActual.close();
            });
            resultados.getChildren().add(item);
        }
        
        existencias=existencias.sig;
    }

    contenedor.setVisible(coincidencia);
    contenedor.setManaged(coincidencia);
}

private void ocultarResultados() {
    resultados.getChildren().clear();
    contenedor.setVisible(false);
    contenedor.setManaged(false);
}   

    @FXML
    private void catalogo(ActionEvent event) {
        modelo.cambioventana("/Vistas/vista_catalogo.fxml", event,this.modelo);
    }
    
}
