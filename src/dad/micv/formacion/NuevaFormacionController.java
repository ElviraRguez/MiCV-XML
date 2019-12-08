package dad.micv.formacion;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.micv.model.Titulo;
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

public class NuevaFormacionController implements Initializable{
	
	//MODEL
	private Titulo nuevo, devuelto;
	
	//VIEW
	private Stage stage;
	
	@FXML private BorderPane view;
    @FXML private TextField denominacionText;
    @FXML private TextField organizadorText;
    @FXML private DatePicker desdePicker;
    @FXML private DatePicker hastaPicker;
	
	public NuevaFormacionController() throws IOException {
		nuevo = new Titulo();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("NuevaFormacionView.fxml"));
		loader.setController(this);
		loader.load();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		nuevo.denominacionProperty().bind(denominacionText.textProperty());
		nuevo.organizadorProperty().bind(organizadorText.textProperty());
		nuevo.desdeProperty().bind(desdePicker.valueProperty());
		nuevo.hastaProperty().bind(hastaPicker.valueProperty());
	}
	
	@FXML
	private void onCrearButtonAction(ActionEvent e) {
		devuelto = new Titulo();
		devuelto.setDenominacion(nuevo.getDenominacion());
		devuelto.setOrganizador(nuevo.getOrganizador());
		devuelto.setDesde(nuevo.getDesde());
		devuelto.setHasta(nuevo.getHasta());
		stage.close();
	}
	
	@FXML
	private void onCancelarButtonAction(ActionEvent e) {
		devuelto = null;
		stage.close();
	}
	
	public Titulo show(Stage parentStage) {
		stage = new Stage();
		if(parentStage != null) {
			stage.initOwner(parentStage);
			stage.getIcons().setAll(parentStage.getIcons());
		}
		stage.initModality(Modality.WINDOW_MODAL);
		stage.setTitle("Nuevo Titulo");
		stage.setResizable(false);
		stage.setScene(new Scene(view));
		stage.showAndWait();
		return devuelto;
	}
	
}
