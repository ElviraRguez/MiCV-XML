package dad.micv.formacion;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.micv.main.MiCVApp;
import dad.micv.model.Titulo;
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

public class FormacionController implements Initializable {
	
	//VIEW
	@FXML private BorderPane view;
	@FXML private TableView<Titulo> formacionTable;
	@FXML private TableColumn<Titulo, LocalDate> desdeFormColumn;
    @FXML private TableColumn<Titulo, LocalDate> hastaFormColumn;
    @FXML private TableColumn<Titulo, String> denominacioFormColumn;
    @FXML private TableColumn<Titulo, String> organizadorFormColumn;
    
    //MODEL
    private ListProperty<Titulo> formacionList = new SimpleListProperty<>(this, "formacionList", FXCollections.observableArrayList());
    private ObjectProperty<Titulo> formacionSelected = new SimpleObjectProperty<>(this, "formacionSelected");
    
	public FormacionController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("FormacionView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		formacionList.addListener((o, ov, nv) -> onFormacionChanged(o, ov, nv));
		
		//BINDINGS
		formacionSelected.bind(formacionTable.getSelectionModel().selectedItemProperty());
				
		//FACTORIES
		desdeFormColumn.setCellValueFactory(value -> value.getValue().desdeProperty());
		desdeFormColumn.setCellFactory(DatePickerTableCell.forTableColumn());
		
		hastaFormColumn.setCellValueFactory(value -> value.getValue().hastaProperty());
		hastaFormColumn.setCellFactory(DatePickerTableCell.forTableColumn());
		
		denominacioFormColumn.setCellValueFactory(value -> value.getValue().denominacionProperty());
		denominacioFormColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		
		organizadorFormColumn.setCellValueFactory(value -> value.getValue().organizadorProperty());
		organizadorFormColumn.setCellFactory(TextFieldTableCell.forTableColumn());
	}
	
	private void onFormacionChanged(Observable o, ObservableList<Titulo> ov, ObservableList<Titulo> nv) {
		if (ov != null) {
			formacionTable.itemsProperty().unbindBidirectional(formacionList);
		}
		
		if (nv != null) {
			formacionTable.itemsProperty().bindBidirectional(formacionList);
		}
	}

	@FXML
	public void onAgregarFormacionButtonAction(ActionEvent e) throws IOException {
		NuevaFormacionController controller = new NuevaFormacionController();
		Titulo nuevo = controller.show(MiCVApp.getPrimaryStage());	
		if(nuevo != null) {
			formacionList.add(nuevo);
		}
	}
	
	@FXML
	public void onEliminarFormacionButtonAction(ActionEvent e) {
		String data = formacionSelected.get().getDenominacion();
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Eliminar");
		alert.setHeaderText(null);
		alert.setContentText("¿Esta seguro que desea eliminar la formación " + data + "?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK)
			formacionList.remove(formacionSelected.get());
	}

	public BorderPane getView() {
		return view;
	}

	public ListProperty<Titulo> formacionListProperty() {
		return this.formacionList;
	}
	
	public ObservableList<Titulo> getFormacionList() {
		return this.formacionListProperty().get();
	}

	public void setFormacionList(final ObservableList<Titulo> formacionList) {
		this.formacionListProperty().set(formacionList);
	}
}
