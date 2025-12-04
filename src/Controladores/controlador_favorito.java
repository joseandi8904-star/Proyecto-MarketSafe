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
public class controlador_favorito implements Initializable {

    @FXML
    private ImageView foto;
    @FXML
    private Label favname;
    @FXML
    private Label cost;
    @FXML
    private Button agg;
    @FXML
    private Button delete;
    
    public controlador_deseos padre;
    public producto date;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void agregarfavorito(producto n, controlador_deseos dad){
        favname.setText(n.nombre);
        cost.setText(String.valueOf(n.precio)+"$");
        Image img = new Image(getClass().getResource(n.imagen).toExternalForm());
        foto.setImage(img);
        date = n;
        padre=dad;
    }
    
    public void defecto (){
        favname.setText("No hay productos agregados");
    }
    
    @FXML
    private void borrar(ActionEvent event) {
        padre.modelo.eliminarfavorito(date.idp);
        padre.cargarFavs();
    }
    
}
