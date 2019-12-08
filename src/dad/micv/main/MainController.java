package dad.micv.main;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.xml.bind.JAXBException;

import dad.micv.conocimientos.ConocimientosController;
import dad.micv.contacto.ContactoController;
import dad.micv.experiencia.ExperienciaController;
import dad.micv.formacion.FormacionController;
import dad.micv.model.CV;
import dad.micv.personal.PersonalController;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class MainController implements Initializable {
	
	//MODEL
	private ObjectProperty<CV> cv = new SimpleObjectProperty<>();
	private File fichero;
	
	//VIEW
	@FXML private BorderPane view;
	@FXML private Tab personalTab;
	@FXML private Tab contactoTab;
    @FXML private Tab formacionTab;
    @FXML private Tab experienciaTab;
    @FXML private Tab conocimientosTab;
    
    //CONTROLLERS
    private PersonalController personalController;
    private ContactoController contactoController;
    private FormacionController formacionController;
    private ExperienciaController experienciaController;
    private ConocimientosController conocimientosController;
    	
	public MainController() throws IOException {
		personalController = new PersonalController();
		contactoController = new ContactoController();
		formacionController = new FormacionController();
		experienciaController = new ExperienciaController();
		conocimientosController = new ConocimientosController();
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("MainView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cv.addListener((o, ov, nv) -> onCVChanged(o, ov, nv));
	}
	
	private void onCVChanged(Observable o, CV ov, CV nv) {
		personalController.setPersonal(nv.getPersonal());
		contactoController.setContacto(nv.getContacto());
		formacionController.setFormacionList(nv.getFormacion());
		experienciaController.setExperienciaList(nv.getExperiencias());
		conocimientosController.setConocimientoList(nv.getHabilidades());
	}
	
	private void cargarContentTabs() {
		personalTab.setContent(personalController.getView());
		contactoTab.setContent(contactoController.getView());
		formacionTab.setContent(formacionController.getView());
		experienciaTab.setContent(experienciaController.getView());
		conocimientosTab.setContent(conocimientosController.getView());
	}
	
	@FXML
	void onNuevoMenuAction(ActionEvent e) {
		cv.set(new CV());
		cargarContentTabs();
	}
	
	@FXML
	void onAbrirMenuAction(ActionEvent e) {
		cv.set(new CV());
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Fichero XML", "*.xml"));
		fichero = fileChooser.showOpenDialog(MiCVApp.getPrimaryStage());
		
		try {
			if(fichero != null) {
				cv.set(CV.load(fichero));
				cargarContentTabs();
			}
		} catch (JAXBException e1) {
			e1.printStackTrace();
		}
	}
	
	@FXML
	void onGuardarMenuAction(ActionEvent e) {
		try {
			if (comprobarNacionalidad()) {
				if (fichero != null)
					cv.get().save(fichero);
				else
					onGuardarComoMenuAction(e);
			}
			else 
				errorNacionalidadVacia();
		} catch (JAXBException e1) {
			e1.printStackTrace();
		}
	}
	
	@FXML
	void onGuardarComoMenuAction(ActionEvent e) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Fichero XML", "*.xml"));
		
		try {
			if (comprobarNacionalidad()) {
				fichero = fileChooser.showSaveDialog(MiCVApp.getPrimaryStage());
				if (fichero != null) 
					cv.get().save(fichero);
			}
			else 
				errorNacionalidadVacia();
		} catch (JAXBException e1) {
			e1.printStackTrace();
		}		
	}
	
	@FXML
	void onSalirMenuAction(ActionEvent e) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Salir");
		alert.setHeaderText(null);
		alert.setContentText("¿Esta seguro que desea salir?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			MiCVApp.getPrimaryStage().close();
		}		
	}
	
	private boolean comprobarNacionalidad() {
		return cv.get().getPersonal().getNacionalidades().size() != 0;
	}
	
	private void errorNacionalidadVacia() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(null);
		alert.setHeaderText(null);
		alert.setContentText("Nacionalidad no puede estar vacía.");

		alert.showAndWait();
	}
	
	public BorderPane getView() {
		return view;
	}
}
