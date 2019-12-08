package dad.micv.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@XmlType
public class Email {
	
	private StringProperty direccion;
	
	public Email() {
		direccion = new SimpleStringProperty(this, "direccion");
	}

	public StringProperty direccionProperty() {
		return this.direccion;
	}

	@XmlAttribute
	public String getDireccion() {
		return this.direccionProperty().get();
	}
	
	public void setDireccion(final String direccion) {
		this.direccionProperty().set(direccion);
	}
}
