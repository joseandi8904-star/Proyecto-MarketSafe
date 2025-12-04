/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

import Modelo.Nodo_LS;
import Modelo.producto;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 */
public class controlador_admin implements Initializable {

    @FXML
    private Button logo;
    @FXML
    private Pane zona;
    @FXML
    private ImageView preview;
    @FXML
    private TextField ruta;
    @FXML
    private Button aggproducto;
    @FXML
    private TextField nombre;
    @FXML
    private TextArea desc;
    @FXML
    private TextField cash;
    @FXML
    private TextField stock;
    List<String> elecciones;
    private metodos_generales modelo;
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
    @FXML
    private ComboBox<String> categorias;

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
    private void over(DragEvent event) {
        if (event.getGestureSource() != zona && event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.COPY);
        }
        event.consume();
    }

    @FXML
    private void arrastrar(DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = false;

        if (db.hasFiles()) {
            File archivo = db.getFiles().get(0); 
            String path = archivo.getAbsolutePath();

            if (path.endsWith(".png") || path.endsWith(".jpg") || path.endsWith(".jpeg")) {
                try{
                String name =archivo.getName();
                Path dest = Paths.get("src/Imagenes/Productos",name);
                Files.copy(archivo.toPath(), dest, StandardCopyOption.REPLACE_EXISTING);
                String rutaRelativa = "/Imagenes/Productos/" + name;
                ruta.setText(rutaRelativa);
                preview.setImage(new Image(archivo.toURI().toString()));
                success = true;
                }catch (IOException e){
                    e.printStackTrace();
                }
            } else {
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setHeaderText(null);
                alerta.setTitle("Error");
                alerta.setContentText("Archivo no valido");
                alerta.showAndWait();
            }
        }
        event.setDropCompleted(success);
        event.consume();
    }

    @FXML
    private void crear(ActionEvent event) {
        String categoriaSeleccionada = categorias.getEditor().getText().trim();

    if (categoriaSeleccionada.isEmpty()) {
        modelo.mostrarError("Debes ingresar o seleccionar una categoría.");
        return;
    }

    if (!categorias.getItems().contains(categoriaSeleccionada)) {
        categorias.getItems().add(categoriaSeleccionada);
        modelo.agregarCategoria(categoriaSeleccionada);
    }
        String id = modelo.generarIDUnico(modelo.obtenerIDsExistentes("src/Archivos/listaproductos.txt"), "producto");
        producto nuevo= new producto(id, nombre.getText(),Float.parseFloat(cash.getText()),ruta.getText(),Integer.parseInt(stock.getText()),desc.getText(),categoriaSeleccionada);
        modelo.agregarSen(nuevo);
        modelo.guardarProducto(nuevo);
        exito();
    }
    
    public void ModeloCompartido(metodos_generales modelo) {
    this.modelo = modelo;
    elecciones = modelo.obtenerCategorias();
    categorias.getItems().addAll(elecciones);
}
    
    public void exito(){
        ruta.setText("");
        desc.setText("");
        stock.setText("");
        nombre.setText("");
        cash.setText("");
        String defecto="C:\\Users\\ADMIN\\Documents\\NetBeansProjects\\MarketSafe\\src\\Imagenes\\Productos\\imagen.jpg";
        preview.setImage(new Image("file:"+defecto));
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setHeaderText(null);
            alerta.setTitle("Exito");
            alerta.setContentText("Producto agregado de manera exitosa");
            alerta.showAndWait();
    }

    @FXML
    private void abrirhistorial(ActionEvent event) {
        modelo.cambioventana("/Vistas/vista_historial.fxml", event,this.modelo);
    }

    @FXML
    private void mostraropciones(ActionEvent event) {
        H.show(Options, Side.BOTTOM,0,0);
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
        if(modelo.actual!=null){
        modelo.cambioventana("/Vistas/vista_usuario.fxml", event, this.modelo);
        }else{
            modelo.cambioventana("/Vistas/vista_principal.fxml", event, this.modelo);
        }
    }

    @FXML
    private void abrircarrito(ActionEvent event) {
        modelo.cambioventana("/Vistas/vista_carrito.fxml", event,this.modelo);
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
