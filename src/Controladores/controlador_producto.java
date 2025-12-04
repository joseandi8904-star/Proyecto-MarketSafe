/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

import Modelo.producto;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author BENJAMIN
 */
public class controlador_producto implements Initializable {

    @FXML
    private ImageView foto;
    @FXML
    private Label prodname;
    @FXML
    private Label cost;
    
    public metodos_generales padre;
    public producto date;
    @FXML
    private Button botonfav;
    @FXML
    private ImageView cambio;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void agregarproducto(producto n, metodos_generales dad){
        prodname.setText(n.nombre);
        cost.setText(String.valueOf(n.precio)+"$");
        Image img = new Image(getClass().getResource(n.imagen).toExternalForm());
        foto.setImage(img);
        cambio.setImage(new Image("/Imagenes/Iconos/nofavorito.png"));
        date=n;
        padre=dad;
        if(padre.actual!=null){
            actualizarEstadoFavorito();
        }
    }

    
    private void actualizarEstadoFavorito() {
    if (padre.estaEnFavoritos(date.idp)) {
        cambio.setImage(new Image("/Imagenes/Iconos/favorito.png"));
    } else {
        cambio.setImage(new Image("/Imagenes/Iconos/nofavorito.png"));
    }
}

    @FXML
    private void estado(ActionEvent event) {
        if(padre.actual!=null){
            padre.toggleFavorito(date);
            actualizarEstadoFavorito();
        }
    }

}
