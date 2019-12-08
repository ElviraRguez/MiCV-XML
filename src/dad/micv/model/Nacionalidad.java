package dad.micv.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@XmlType
public class Nacionalidad {
	
	private StringProperty denominacion;
	
	public Nacionalidad() {
		denominacion = new SimpleStringProperty(this, "denominacion");
	}

	public StringProperty denominacionProperty() {
		return this.denominacion;
	}
	
	@XmlAttribute
	public String getDenominacion() {
		return this.denominacionProperty().get();
	}
	
	public void setDenominacion(final String denominacion) {
		this.denominacionProperty().set(denominacion);
	}
	
	@Override
	public String toString() {
		return denominacion.get();
	}
}
