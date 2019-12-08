package dad.micv.experiencia;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.micv.model.Experiencia;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class NuevaExperienciaController implements Initializable{
	
	//MODEL
	private Experiencia nuevo, devuelto;
	
	//VIEW
	private Stage stage;
	
	@FXML private BorderPane view;
    @FXML private TextField denominacionText;
    @FXML private TextField empleadorText;
    @FXML private DatePicker desdePicker;
    @FXML private DatePicker hastaPicker;
	
	public NuevaExperienciaController() throws IOException {
		nuevo = new Experiencia();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("NuevaExperienciaView.fxml"));
		loader.setController(this);
		loader.load();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		nuevo.denominacionProperty().bind(denominacionText.textProperty());
		nuevo.empleadorProperty().bind(empleadorText.textProperty());
		nuevo.desdeProperty().bind(desdePicker.valueProperty());
		nuevo.hastaProperty().bind(hastaPicker.valueProperty());
	}
	
	@FXML
	private void onCrearButtonAction(ActionEvent e) {
		devuelto = new Experiencia();
		devuelto.setDenominacion(nuevo.getDenominacion());
		devuelto.setEmpleador(nuevo.getEmpleador());
		devuelto.setDesde(nuevo.getDesde());
		devuelto.setHasta(nuevo.getHasta());
		stage.close();
	}
	
	@FXML
	private void onCancelarButtonAction(ActionEvent e) {
		devuelto = null;
		stage.close();
	}
	
	public Experiencia show(Stage parentStage) {
		stage = new Stage();
		if(parentStage != null) {
			stage.initOwner(parentStage);
			stage.getIcons().setAll(parentStage.getIcons());
		}
		stage.initModality(Modality.WINDOW_MODAL);
		stage.setTitle("Nuevo experiencia");
		stage.setResizable(false);
		stage.setScene(new Scene(view));
		stage.showAndWait();
		return devuelto;
	}
	
}
