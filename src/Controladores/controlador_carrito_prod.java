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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author BENJAMIN
 */
public class controlador_carrito_prod implements Initializable {

    @FXML
    private ImageView foto;
    @FXML
    private Label cost;
    @FXML
    private Button delete;
    @FXML
    private Button menos;
    @FXML
    private TextField cantidad;
    @FXML
    private Button mas;
    
    public producto date;
    public controlador_carrito padre;
    
    @FXML
    private Label name;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void restar(ActionEvent event) {
        int actualQty = Integer.parseInt(cantidad.getText());
    if (actualQty > 0) {
        actualQty--;
        cantidad.setText(String.valueOf(actualQty));
        date.cantidad=actualQty;
        padre.cargarcarrito();
        padre.actualizarTotal();
        System.out.println("-1");
    }
    }

    @FXML
    private void sumar(ActionEvent event) {
        producto cant =padre.modelo.buscarProductoPorID(date.idp);
        int actualQty = Integer.parseInt(cantidad.getText());
    if (actualQty < cant.cantidad) {
        actualQty++;
        cantidad.setText(String.valueOf(actualQty));
        date.cantidad=actualQty;
        padre.cargarcarrito();
        padre.actualizarTotal();
        System.out.println("+1");
    }
        }
    
    public void agregarencarrito(producto n, controlador_carrito dad){
        name.setText(n.nombre);
        cost.setText(String.valueOf(n.precio)+"$");
        Image img = new Image(getClass().getResource(n.imagen).toExternalForm());
        foto.setImage(img);
        cantidad.setText(String.valueOf(n.cantidad));
        date = n;
        padre=dad;
    }

    @FXML
    private void borrar(ActionEvent event) {
        padre.modelo.eliminarProdCarrito(date.idp);
        padre.cargarcarrito();
        padre.actualizarTotal();
    }
    
    public void defecto(){
        name.setText("No hay productos agregados");
    }
    
}
