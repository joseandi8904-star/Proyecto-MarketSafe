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
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author BENJAMIN
 */
public class contenedor_historial implements Initializable {

    @FXML
    private Label fecha;
    @FXML
    private Label valor;
    @FXML
    private VBox contenedor;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void agregarhistorial(historial h, metodos_generales padre) {
    
    for (String id :h.articulos ) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/historial_prod.fxml"));
            HBox ContenedorHBox = loader.load();
            controlador_historial_prod controller = loader.getController();
            producto prod =padre.BuscarCatalogo(id).dato;
            controller.agregarproducto(prod);
            controller.btnirproducto().setOnAction(e -> {
                padre.datosProducto("/Vistas/vista_infoproducto.fxml", prod,padre);
                Stage ventanaActual = (Stage) ((Node) e.getSource()).getScene().getWindow();
                ventanaActual.close();
            });
            fecha.setText(h.fecha);
            valor.setText(String.valueOf(h.total));
            contenedor.getChildren().add(ContenedorHBox);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
    
}
