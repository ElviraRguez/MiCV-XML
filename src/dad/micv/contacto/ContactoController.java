package dad.micv.contacto;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.micv.main.MiCVApp;
import dad.micv.model.Contacto;
import dad.micv.model.Email;
import dad.micv.model.Telefono;
import dad.micv.model.TipoTelefono;
import dad.micv.model.Web;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ContactoController implements Initializable{
	
	//VIEW
	@FXML private BorderPane view;
	@FXML private TableView<Telefono> telefonosTable;
    @FXML private TableView<Email> emailTable;
    @FXML private TableView<Web> webTable;
    @FXML private TableColumn<Telefono, String> numTelfColumn;
    @FXML private TableColumn<Telefono, TipoTelefono> tipoTelfColumn;
    @FXML private TableColumn<Email, String> emailColumn;
    @FXML private TableColumn<Web, String> urlColumn;
	
	//MODEL
	private ObjectProperty<Contacto> contacto =  new SimpleObjectProperty<>();
	
	private ObjectProperty<Telefono> telefonoSelected = new SimpleObjectProperty<>(this, "telefonoSelected");
	private ObjectProperty<Email> emailSelected = new SimpleObjectProperty<>(this, "emailSelected");
	private ObjectProperty<Web> webSelected = new SimpleObjectProperty<>(this, "webSelected");
	
	public ContactoController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ContactoView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		contacto.addListener((o, ov, nv) -> onContactoChanged(o, ov, nv));
		
		//BINDINGS
		telefonoSelected.bind(telefonosTable.getSelectionModel().selectedItemProperty());
		emailSelected.bind(emailTable.getSelectionModel().selectedItemProperty());
		webSelected.bind(webTable.getSelectionModel().selectedItemProperty());
		
		//FACTORIES
		numTelfColumn.setCellValueFactory(value -> value.getValue().numeroProperty());
		numTelfColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		
		tipoTelfColumn.setCellValueFactory(value -> value.getValue().tipoProperty());
		tipoTelfColumn.setCellFactory(ComboBoxTableCell.forTableColumn(TipoTelefono.values()));
		
		emailColumn.setCellValueFactory(value -> value.getValue().direccionProperty());
		emailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		
		urlColumn.setCellValueFactory(value -> value.getValue().urlProperty());
		urlColumn.setCellFactory(TextFieldTableCell.forTableColumn());
	}
	
	private void onContactoChanged(Observable o, Contacto ov, Contacto nv) {
		if (ov != null) {
			telefonosTable.itemsProperty().unbindBidirectional(ov.telefonosProperty());
			emailTable.itemsProperty().unbindBidirectional(ov.emailsProperty());
			webTable.itemsProperty().unbindBidirectional(ov.websProperty());
		}
		if (nv != null) {
			telefonosTable.itemsProperty().bindBidirectional(nv.telefonosProperty());
			emailTable.itemsProperty().bindBidirectional(nv.emailsProperty());
			webTable.itemsProperty().bindBidirectional(nv.websProperty());
		}
	}

	@FXML
	public void onAgregarTelefonoButtonAction(ActionEvent e) {
		Dialog<Telefono> dialog = new Dialog<>();
		dialog.setTitle("Nuevo Teléfono");
		dialog.setHeaderText("Introduzca el nuevo número de teléfono");
		
		alertCustom(dialog);
		
		Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
		stage.getIcons().setAll(MiCVApp.getPrimaryStage().getIcons());
			
		dialog.showAndWait();
		if (dialog.getResult() != null)
			contacto.get().getTelefonos().add(dialog.getResult());
	}
	
	@FXML
	public void onEliminarTelefonoButtonAction(ActionEvent e) {
		String data = telefonoSelected.get().getNumero();
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Eliminar");
		alert.setHeaderText(null);
		alert.setContentText("¿Esta seguro que desea eliminar el teléfono " + data + "?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK)
			contacto.get().getTelefonos().remove(telefonoSelected.get());
	}
		
	private void alertCustom(Dialog<Telefono> dialog) {
		ButtonType addButtonType = new ButtonType("Añadir", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);
		
		GridPane panel = new GridPane();
		panel.setHgap(10);
		panel.setVgap(10);
		panel.setPadding(new Insets(20, 150, 10, 10));
		
		TextField numeroTelf = new TextField();
		numeroTelf.setPromptText("Número de teléfono");
		
		ComboBox<TipoTelefono> listTypes = new ComboBox<>();
		listTypes.getItems().addAll(
			TipoTelefono.DOMICILIO,
			TipoTelefono.MOVIL
		);
		listTypes.setPromptText("Seleccione un tipo");
		
		panel.add(new Label("Número:"), 0, 0);
		panel.add(numeroTelf, 1, 0);
		panel.add(new Label("Tipo:"), 0, 1);
		panel.add(listTypes, 1, 1);
		
		dialog.getDialogPane().setContent(panel);
		
		dialog.setResultConverter(dialogButton -> {
		    if (dialogButton == addButtonType) {
		    	Telefono telf = new Telefono();
		    	telf.setNumero(numeroTelf.getText());
		    	if (listTypes.getValue() != null)
		    		telf.setTipo(listTypes.getValue());
		    	return telf;
		    }
			return null;
		});
	}
	
	@FXML
	public void onAgregarEmailButtonAction() {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Nuevo e-mail");
		dialog.setHeaderText("Crear una nueva dirección de correo.");
		dialog.setContentText("Email:");
		
		Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
		stage.getIcons().setAll(MiCVApp.getPrimaryStage().getIcons());

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
		    Email email = new Email();
		    email.setDireccion(result.get());
		    contacto.get().getEmails().add(email);
		}
	}
	
	@FXML
	public void onEliminarEmailButtonAction() {
		String data = emailSelected.get().getDireccion();
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Eliminar");
		alert.setHeaderText(null);
		alert.setContentText("¿Esta seguro que desea eliminar el e-mail " + data + "?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK)
			contacto.get().getEmails().remove(emailSelected.get());
	}

	@FXML
	public void onAgregarWebButtonAction() {
		TextInputDialog dialog = new TextInputDialog("http://");
		dialog.setTitle("Nueva web");
		dialog.setHeaderText("Crear una nueva dirección web.");
		dialog.setContentText("URL:");
		
		Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
		stage.getIcons().setAll(MiCVApp.getPrimaryStage().getIcons());

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
		    Web web = new Web();
		    web.setUrl(result.get());
		    contacto.get().getWebs().add(web);
		}
	}
	
	@FXML
	public void onEliminarWebButtonAction() {
		String data = webSelected.get().getUrl();
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Eliminar");
		alert.setHeaderText(null);
		alert.setContentText("¿Esta seguro que desea eliminar la web " + data + "?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK)
			contacto.get().getWebs().remove(webSelected.get());
	}
	
	public BorderPane getView() {
		return view;
	}

	public ObjectProperty<Contacto> contactoProperty() {
		return this.contacto;
	}
	
	public Contacto getContacto() {
		return this.contactoProperty().get();
	}
	
	public void setContacto(final Contacto contacto) {
		this.contactoProperty().set(contacto);
	}
}
