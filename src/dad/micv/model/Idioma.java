package dad.micv.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@XmlType
public class Idioma extends Conocimiento {
	
	private StringProperty certificacion;
	
	public Idioma() {
		super();
		certificacion = new SimpleStringProperty(this, "certificacion");
	}

	public StringProperty certificacionProperty() {
		return this.certificacion;
	}
	
	@XmlAttribute
	public String getCertificacion() {
		return this.certificacionProperty().get();
	}
	
	public void setCertificacion(final String certificacion) {
		this.certificacionProperty().set(certificacion);
	}
}
