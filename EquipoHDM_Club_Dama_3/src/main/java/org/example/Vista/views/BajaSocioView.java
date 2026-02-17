package org.example.Vista.views;

import servicio.ClubDeportivo;
import modelo.*;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.sql.SQLException;
import java.util.function.Consumer;

public class BajaSocioView extends GridPane {
    public BajaSocioView(ClubDeportivo club) throws SQLException {
        setPadding(new Insets(12));
        setHgap(8); setVgap(8);

        ComboBox<String> id = new ComboBox<>();
        Button baja = new Button("Dar de baja");

        //llamada al metodo del modelo para cargar los socios en el combobox
        id.getItems().addAll(club.cargarSociosCombobox());

        addRow(0, new Label("Socio"), id);
        add(baja, 1, 1);

        baja.setOnAction(e -> {
            try {
                if (club.bajaSocio(id.getValue()).equals("Baja validada")) {
                    showInfo("Socio dado de baja con éxito");
                } else if (club.bajaSocio(id.getValue()).equals("Socio con reservas activas")) {
                    showError("El socio no se ha podido dar de baja. Comprueba que no está asignado a ninguna reserva");
                } else if (club.bajaSocio(id.getValue()).equals("Socio no seleccionado")) {
                    showError("No ha seleccionado ningún socio");
                }

            } catch (SQLException ex) {
                throw new RuntimeException(ex);
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
