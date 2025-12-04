/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

import Modelo.Nodo_LS;
import Modelo.historial;
import Modelo.producto;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 */
public class controlador_historial implements Initializable {

    @FXML
    private VBox histolist;
    public metodos_generales modelo;
    @FXML
    private Button Options;
    @FXML
    private ContextMenu H;
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
    
    public void ModeloCompartido(metodos_generales modelo) {
    this.modelo = modelo;
    cargarHistorial(); 
}
    
    private void cargarHistorial() {
    histolist.getChildren().clear();
    Nodo_LS <historial> historiales = modelo.cab_h;
    
    while (historiales!=null ) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/contenedor_historial.fxml"));
            VBox ContenedorVBox = loader.load();
            contenedor_historial controller = loader.getController();
            controller.agregarhistorial(historiales.dato, modelo);
            histolist.getChildren().add(ContenedorVBox);
            historiales=historiales.sig;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


    @FXML
    private void mostraropciones(ActionEvent event) {
        H.show(Options, Side.BOTTOM,0,0);
    }

    @FXML
    private void crearproducto(ActionEvent event) {
    TextInputDialog dialogo = new TextInputDialog();
    dialogo.setTitle("Código de Acceso");
    dialogo.setHeaderText("Ingrese el código para continuar");
    dialogo.setContentText("Código:");

    Optional<String> resultado = dialogo.showAndWait();

    if (resultado.isPresent()) {
        String codigoIngresado = resultado.get();
        String codigoCorrecto = "1234";

        if (codigoIngresado.equals(codigoCorrecto)) {
            modelo.cambioventana("/Vistas/vista_admin.fxml", event,this.modelo);
        } else {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error de acceso");
            alerta.setHeaderText(null);
            alerta.setContentText("Código incorrecto. Intente nuevamente.");
            alerta.showAndWait();
        }
    }
    }

    @FXML
    private void salir(ActionEvent event) {
        modelo.cerrarsesion();
        modelo.cambioventana("/Vistas/vista_principal.fxml", event,this.modelo);
    }

    @FXML
    private void abrir(ActionEvent event) {
        modelo.cambioventana("/Vistas/vista_deseos.fxml", event,this.modelo);
    }

    @FXML
    private void inicio(ActionEvent event) {
        modelo.cambioventana("/Vistas/vista_usuario.fxml", event, this.modelo);
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
