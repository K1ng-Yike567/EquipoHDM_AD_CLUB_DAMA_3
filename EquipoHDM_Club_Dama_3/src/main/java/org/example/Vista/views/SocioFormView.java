package org.example.Vista.views;

import jakarta.persistence.PersistenceException;
import org.example.Entidades.Socio;
import org.example.Servicio.ClubService;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.function.Consumer;

public class SocioFormView extends GridPane {
    public SocioFormView(ClubService club) {
        setPadding(new Insets(12));
        setHgap(8);
        setVgap(8);

        TextField id = new TextField();
        TextField dni = new TextField();
        TextField nombre = new TextField();
        TextField apellidos = new TextField();
        TextField tel = new TextField();
        TextField email = new TextField();
        Button crear = new Button("Crear");

        addRow(0, new Label("idSocio*"), id);
        addRow(1, new Label("DNI"), dni);
        addRow(2, new Label("Nombre"), nombre);
        addRow(3, new Label("Apellidos"), apellidos);
        addRow(4, new Label("Teléfono"), tel);
        addRow(5, new Label("Email"), email);
        add(crear, 1, 6);

        crear.setOnAction(e -> {
            try {
                Socio s = new Socio(id.getText(), dni.getText(), nombre.getText(), apellidos.getText(), tel.getText(), email.getText());
                if (club.insertarSocio(s).equals("inserción validada")) {
                    showInfo("Socio insertado en la base de datos con éxito");
                } else if (club.insertarSocio(s).equals("ID vacío")) {
                    showError("Error. El campo de 'idSocio' no puede estar vacío");
                }
            } catch (PersistenceException ex) {
                showError("Error al subir el socio a la base de datos");
            }
        });
    }
    private void showError(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        a.setHeaderText("Error");
        a.showAndWait();
    }
    private void showInfo(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        a.setHeaderText(null);
        a.showAndWait();
    }
}
