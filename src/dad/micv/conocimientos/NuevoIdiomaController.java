package dad.micv.conocimientos;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.micv.model.Idioma;
import dad.micv.model.Nivel;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class NuevoIdiomaController implements Initializable {
	
	//VIEW
	private Stage stage;
		
	@FXML private BorderPane view;
    @FXML private TextField denominacionText;
    @FXML private ComboBox<Nivel> nivelCombo;
    @FXML private TextField certificacionText;
    
    //MODEL
  	private Idioma nuevo, devuelto;
  	private ListProperty<Nivel> listNiveles = new SimpleListProperty<>(this, "listNiveles", FXCollections.observableArrayList());
	
	public NuevoIdiomaController() throws IOException {
		nuevo = new Idioma();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("NuevoIdiomaView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		nuevo.denominacionProperty().bind(denominacionText.textProperty());
		nuevo.certificacionProperty().bind(certificacionText.textProperty());
		
		nivelCombo.itemsProperty().bind(listNiveles);		
		nuevo.nivelProperty().bind(nivelCombo.valueProperty());
		listNiveles.addAll(Nivel.values());
	}
	
	@FXML
	void onLimpiarButtonAction(ActionEvent e) {
//		nivelCombo.getSelectionModel().clearSelection();
		listNiveles.get().clear();
	}
	
	@FXML
	void onCrearButtonAction(ActionEvent e) {
		devuelto = new Idioma();
		devuelto.setDenominacion(nuevo.getDenominacion());
		devuelto.setNivel(nuevo.getNivel());
		stage.close();
	}
	
	@FXML
	void onCancelarButtonAction(ActionEvent e) {
		devuelto = null;
		stage.close();
	}
	
	public Idioma show(Stage parentStage) {
		stage = new Stage();
		if(parentStage != null) {
			stage.initOwner(parentStage);
			stage.getIcons().setAll(parentStage.getIcons());
		}
		stage.initModality(Modality.WINDOW_MODAL);
		stage.setTitle("Nuevo conocimiento");
		stage.setResizable(false);
		stage.setScene(new Scene(view));
		stage.showAndWait();
		return devuelto;
	}
}
