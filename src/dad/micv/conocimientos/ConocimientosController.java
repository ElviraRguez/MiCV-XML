package dad.micv.conocimientos;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.micv.main.MiCVApp;
import dad.micv.model.Conocimiento;
import dad.micv.model.Idioma;
import dad.micv.model.Nivel;
import javafx.beans.Observable;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

public class ConocimientosController implements Initializable{
	
	// VIEW
	@FXML private BorderPane view;
	@FXML private TableView<Conocimiento> conocimientoTable;
	@FXML private TableColumn<Conocimiento, String> denominacionConoColumn;
	@FXML private TableColumn<Conocimiento, Nivel> nivelConoColumn;
	
	 //MODEL
    private ListProperty<Conocimiento> conocimientoList = new SimpleListProperty<>(this, "conocimientoList", FXCollections.observableArrayList());
    private ObjectProperty<Conocimiento> conocimientoSelected = new SimpleObjectProperty<>(this, "conocimientoSelected");

	public ConocimientosController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ConocimientosView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		conocimientoList.addListener((o, ov, nv) -> onConocimientoChanged(o, ov, nv));
		
		//BINDING
		conocimientoSelected.bind(conocimientoTable.getSelectionModel().selectedItemProperty());
				
		//FACTORIES
		denominacionConoColumn.setCellValueFactory(value -> value.getValue().denominacionProperty());
		denominacionConoColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		
		nivelConoColumn.setCellValueFactory(value -> value.getValue().nivelProperty());
		nivelConoColumn.setCellFactory(ComboBoxTableCell.forTableColumn(Nivel.values()));
	}
	
	private void onConocimientoChanged(Observable o, ObservableList<Conocimiento> ov, ObservableList<Conocimiento> nv) {
		if(ov != null) {
			conocimientoTable.itemsProperty().unbindBidirectional(conocimientoList);
		}
		
		if(nv != null) {
			conocimientoTable.itemsProperty().bindBidirectional(conocimientoList);
		}
	}

	@FXML
	void onNuevoConocimientoButtonAction(ActionEvent e) throws IOException {
		NuevoConocimientoController controller = new NuevoConocimientoController();
		Conocimiento nuevo = controller.show(MiCVApp.getPrimaryStage());	
		if(nuevo != null) {
			conocimientoList.add(nuevo);
		}
	}
	
	@FXML
	void onNuevoIdiomaButtonAction(ActionEvent e) throws IOException {
		NuevoIdiomaController controller = new NuevoIdiomaController();
		Idioma nuevo = controller.show(MiCVApp.getPrimaryStage());	
		if(nuevo != null) {
			conocimientoList.add(nuevo);
		}
	}
	
	@FXML
	void onEliminarConocimientoButtonAction(ActionEvent e) {
		String data = conocimientoSelected.get().getDenominacion();
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Eliminar");
		alert.setHeaderText(null);
		alert.setContentText("¿Esta seguro que desea eliminar el conocimiento " + data + "?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK)
			conocimientoList.remove(conocimientoSelected.get());
	}

	public BorderPane getView() {
		return view;
	}

	public ListProperty<Conocimiento> conocimientoListProperty() {
		return this.conocimientoList;
	}
	
	public ObservableList<Conocimiento> getConocimientoList() {
		return this.conocimientoListProperty().get();
	}
	
	public void setConocimientoList(final ObservableList<Conocimiento> conocimientoList) {
		this.conocimientoListProperty().set(conocimientoList);
	}
}
