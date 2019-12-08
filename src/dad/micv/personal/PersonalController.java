package dad.micv.personal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.micv.main.MiCVApp;
import dad.micv.model.Nacionalidad;
import dad.micv.model.Personal;
import javafx.beans.Observable;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class PersonalController implements Initializable{
	
	//VIEW
	@FXML private GridPane view;
	@FXML private TextField dniText, nombreText, apellidosText, codPostalText, localidadText;
    @FXML private DatePicker fechaNacPicker;
    @FXML private TextArea direcionText;
    @FXML private ComboBox<String> paisCombo;
    @FXML private ListView<Nacionalidad> nacionalidadList;
    @FXML private Button eliminarNacionalidadButton;
	
	//MODEL
	private ObjectProperty<Personal> personal = new SimpleObjectProperty<>();
	
	private ListProperty<String> listPaises = new SimpleListProperty<>(this, "listPaises", FXCollections.observableArrayList());
	
	public PersonalController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("PersonalView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		personal.addListener((o, ov, nv) -> onPersonalChanged(o, ov, nv));
		
		//BINDINGS
		paisCombo.itemsProperty().bind(listPaises);				
		cargarPaises();
	}
	
	private void onPersonalChanged(Observable o, Personal ov, Personal nv) {
		if (ov != null) {
			dniText.textProperty().bindBidirectional(ov.identificacionProperty());
			nombreText.textProperty().bindBidirectional(ov.nombreProperty());
			apellidosText.textProperty().bindBidirectional(ov.apellidosProperty());
			codPostalText.textProperty().bindBidirectional(ov.codigoPostalProperty());
			localidadText.textProperty().bindBidirectional(ov.localidadProperty());
			fechaNacPicker.valueProperty().bindBidirectional(ov.fechaNacimientoProperty());
			direcionText.textProperty().bindBidirectional(ov.direccionProperty());
			paisCombo.valueProperty().bindBidirectional(ov.paisProperty());
			nacionalidadList.itemsProperty().bindBidirectional(ov.nacionalidadesProperty());
		}
		if (nv != null) {
			dniText.textProperty().bindBidirectional(nv.identificacionProperty());
			nombreText.textProperty().bindBidirectional(nv.nombreProperty());
			apellidosText.textProperty().bindBidirectional(nv.apellidosProperty());
			codPostalText.textProperty().bindBidirectional(nv.codigoPostalProperty());
			localidadText.textProperty().bindBidirectional(nv.localidadProperty());
			fechaNacPicker.valueProperty().bindBidirectional(nv.fechaNacimientoProperty());
			direcionText.textProperty().bindBidirectional(nv.direccionProperty());
			paisCombo.valueProperty().bindBidirectional(nv.paisProperty());
			nacionalidadList.itemsProperty().bindBidirectional(nv.nacionalidadesProperty());
		}
	}
	
	@FXML
	public void onNuevaNacionalidadAction(ActionEvent e) {
		List<String> nacionalidades = cargarNacionalidades();
		ChoiceDialog<String> dialog = new ChoiceDialog<>(nacionalidades.get(0), nacionalidades);
		dialog.setTitle("Nueva nacionalidad");
		dialog.setHeaderText("Añadir nacionalidad");
		dialog.setContentText("Seleccione una nacionalidad");
		
		Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
		stage.getIcons().setAll(MiCVApp.getPrimaryStage().getIcons());

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
			Nacionalidad nacionalidad = new Nacionalidad();
			nacionalidad.setDenominacion(result.get());
		    nacionalidadList.getItems().add(nacionalidad);
		}
	}
	
	@FXML
	public void onEliminarNacionalidadAction(ActionEvent e) {
		nacionalidadList.getItems().remove(nacionalidadList.getSelectionModel().getSelectedItem());
	}
	
	private void cargarPaises() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("paises.csv")));
			String linea;
			while ((linea = br.readLine()) != null) {
				listPaises.add(linea);			
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private List<String> cargarNacionalidades() {
		List<String> choices = new ArrayList<>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("nacionalidades.csv")));
			String linea;
			while ((linea = br.readLine()) != null) {
				choices.add(linea);			
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return choices;
	}
	
	public GridPane getView() {
		return view;
	}

	public ObjectProperty<Personal> personalProperty() {
		return this.personal;
	}	

	public Personal getPersonal() {
		return this.personalProperty().get();
	}	

	public void setPersonal(final Personal personal) {
		this.personalProperty().set(personal);
	}
}
