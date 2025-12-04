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
public class controlador_historial_prod implements Initializable {

    @FXML
    private ImageView foto;
    @FXML
    private Label histoname;
    @FXML
    private Label cost;
    @FXML
    private Button irproducto;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void agregarproducto(producto n){
        histoname.setText(n.nombre);
        cost.setText(String.valueOf(n.precio)+"$");
        Image img = new Image(getClass().getResource(n.imagen).toExternalForm(),160,111,true,true);
        foto.setImage(img);
    }

    public Button btnirproducto() {
    return irproducto;
  }

    
}
