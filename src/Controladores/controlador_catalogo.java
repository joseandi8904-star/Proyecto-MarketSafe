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
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author BENJAMIN
 */
public class controlador_catalogo implements Initializable {
        
    public metodos_generales modelo;
    
    @FXML
    private Button Options;
    @FXML
    private ContextMenu H;
    @FXML
    private TextField busqueda;
    @FXML
    private VBox resultados;
    @FXML
    private FlowPane contCatalogo;
    @FXML
    private ScrollPane contenedor;
    @FXML
    private ComboBox<String> filtro;
    @FXML
    private ComboBox<String> filtro2;
    
    

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
    cargarcatalogo("Todos","A-Z");
    inicializarCategorias();
    inicializarOrdenes();
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
    private void abrircarrito(ActionEvent event) {
        modelo.cambioventana("/Vistas/vista_carrito.fxml", event,this.modelo);
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

    public void cargarcatalogo(String category, String orden) {
        contCatalogo.getChildren().clear();
        List<producto> productos = modelo.obtenerProductosFiltrados(category, orden);
        try{
            for (producto prod : productos) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/producto.fxml"));
                VBox productoVBox = loader.load();
                productoVBox.setOnMouseClicked(e -> {
                modelo.datosProducto("/Vistas/vista_infoproducto.fxml", prod,modelo);
                Stage ventanaActual = (Stage) ((Node) e.getSource()).getScene().getWindow();
                ventanaActual.close();
            });
                controlador_producto controller = loader.getController();
                controller.agregarproducto(prod, modelo);
                productoVBox.setPrefWidth(130);
                contCatalogo.getChildren().add(productoVBox);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void cambio(ActionEvent event) {
        String select = filtro.getValue();
        String orden = filtro2.getValue();
        cargarcatalogo(select,orden); 
    }
    
    public void inicializarCategorias() {
    List<String> categorias = modelo.obtenerCategorias();
    filtro.getItems().add("Todos");
    filtro.getItems().addAll(categorias);
    filtro.setValue("Todos");

    filtro.setStyle("-fx-background-color: transparent; -fx-border-color: #cccccc; -fx-border-width: 1px; -fx-border-radius: 20px;");
    
    // Personalizar cómo se muestra el texto seleccionado
    filtro.setButtonCell(new ListCell<String>() {
        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
            } else {
                setText(item);
                setTextFill(Color.GREEN); 
                setStyle("-fx-background-color: transparent;");
            }
        }
    });
    
    // Personalizar las opciones del dropdown
    filtro.setCellFactory(listView -> new ListCell<String>() {
        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
            } else {
                setText(item);
                setTextFill(Color.GREEN);
            }
        }
    });
}

    @FXML
    private void cambio2(ActionEvent event) {
        String select = filtro.getValue();
        String orden = filtro2.getValue();
        cargarcatalogo(select,orden); 
    }
    
    public void inicializarOrdenes() {
    filtro2.getItems().add("A-Z");
    filtro2.getItems().add("Z-A");
    filtro2.getItems().add("Mayor precio");
    filtro2.getItems().add("Menor precio");
    filtro2.getItems().add("Favoritos");
    filtro2.setValue("A-Z");

    filtro2.setStyle("-fx-background-color: transparent; -fx-border-color: #cccccc; -fx-border-width: 1px; -fx-border-radius: 20px;");
    
    // Personalizar cómo se muestra el texto seleccionado
    filtro2.setButtonCell(new ListCell<String>() {
        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
            } else {
                setText(item);
                setTextFill(Color.GREEN); 
                setStyle("-fx-background-color: transparent;");
            }
        }
    });
    
    // Personalizar las opciones del dropdown
    filtro2.setCellFactory(listView -> new ListCell<String>() {
        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
            } else {
                setText(item);
                setTextFill(Color.GREEN);
            }
        }
    });
}
    
}
