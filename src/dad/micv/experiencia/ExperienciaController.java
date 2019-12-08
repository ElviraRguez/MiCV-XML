package dad.micv.experiencia;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.micv.main.MiCVApp;
import dad.micv.model.Experiencia;
import dad.micv.resources.DatePickerTableCell;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;

public class ExperienciaController implements Initializable {
	
	// VIEW
	@FXML private BorderPane view;
	@FXML private TableView<Experiencia> expTable;
    @FXML private TableColumn<Experiencia, LocalDate> desdeExpColumn;
    @FXML private TableColumn<Experiencia, LocalDate> hastaExpColumn;
    @FXML private TableColumn<Experiencia, String> denominacionExpColumn;
    @FXML private TableColumn<Experiencia, String> empleadorExpColumn;
    
    //MODEL
    private ListProperty<Experiencia> experienciaList = new SimpleListProperty<>(this, "experienciaList", FXCollections.observableArrayList());
    private ObjectProperty<Experiencia> experienciaSelected = new SimpleObjectProperty<>(this, "experienciaSelected");

	public ExperienciaController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ExperienciaView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		experienciaList.addListener((o, ov, nv) -> onExperienciaChanged(o, ov, nv));
		
		//BINDINGS
		experienciaSelected.bind(expTable.getSelectionModel().selectedItemProperty());
		
		//FACTORIES
		desdeExpColumn.setCellValueFactory(value -> value.getValue().desdeProperty());
		desdeExpColumn.setCellFactory(DatePickerTableCell.forTableColumn());
		
		hastaExpColumn.setCellValueFactory(value -> value.getValue().hastaProperty());
		hastaExpColumn.setCellFactory(DatePickerTableCell.forTableColumn());
		
		denominacionExpColumn.setCellValueFactory(value -> value.getValue().denominacionProperty());
		denominacionExpColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		
		empleadorExpColumn.setCellValueFactory(value -> value.getValue().empleadorProperty());
		empleadorExpColumn.setCellFactory(TextFieldTableCell.forTableColumn());
	}
	
	private void onExperienciaChanged(Observable o, ObservableList<Experiencia> ov, ObservableList<Experiencia> nv) {
		if(ov != null) {
			expTable.itemsProperty().unbindBidirectional(experienciaList);
		}
		
		if(nv != null) {
			expTable.itemsProperty().bindBidirectional(experienciaList);
		}
	}

	@FXML
	public void onAgregarExpButtonAction(ActionEvent e) throws IOException {
		NuevaExperienciaController controller = new NuevaExperienciaController();
		Experiencia nuevo = controller.show(MiCVApp.getPrimaryStage());	
		if(nuevo != null) {
			experienciaList.add(nuevo);
		}
	}
	
	@FXML
	public void onEliminarExpButtonAction(ActionEvent e) {
		String data = experienciaSelected.get().getDenominacion();
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Eliminar");
		alert.setHeaderText(null);
		alert.setContentText("¿Esta seguro que desea eliminar la experiencia " + data + "?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK)
			experienciaList.remove(experienciaSelected.get());
	}

	public BorderPane getView() {
		return view;
	}

	public ListProperty<Experiencia> experienciaListProperty() {
		return this.experienciaList;
	}
	
	public ObservableList<Experiencia> getExperienciaList() {
		return this.experienciaListProperty().get();
	}
	
	public void setExperienciaList(final ObservableList<Experiencia> experienciaList) {
		this.experienciaListProperty().set(experienciaList);
	}
}
