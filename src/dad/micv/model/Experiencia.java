package dad.micv.model;

import java.time.LocalDate;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@XmlType
public class Experiencia {
	
	private ObjectProperty<LocalDate> desde;
	private ObjectProperty<LocalDate> hasta;
	private StringProperty denominacion;
	private StringProperty empleador;
	
	public Experiencia() {
		desde = new SimpleObjectProperty<>(this, "desde");
		hasta = new SimpleObjectProperty<>(this, "hasta");
		denominacion = new SimpleStringProperty(this, "denominacion");
		empleador = new SimpleStringProperty(this, "empleador");
	}

	public ObjectProperty<LocalDate> desdeProperty() {
		return this.desde;
	}
	
	@XmlAttribute
	@XmlJavaTypeAdapter(value = LocalDateAdapter.class)
	public LocalDate getDesde() {
		return this.desdeProperty().get();
	}
	
	public void setDesde(final LocalDate desde) {
		this.desdeProperty().set(desde);
	}
	
	public ObjectProperty<LocalDate> hastaProperty() {
		return this.hasta;
	}
	
	@XmlAttribute
	@XmlJavaTypeAdapter(value = LocalDateAdapter.class)
	public LocalDate getHasta() {
		return this.hastaProperty().get();
	}
	
	public void setHasta(final LocalDate hasta) {
		this.hastaProperty().set(hasta);
	}
	
	public StringProperty denominacionProperty() {
		return this.denominacion;
	}
	
	@XmlElement
	public String getDenominacion() {
		return this.denominacionProperty().get();
	}
	
	public void setDenominacion(final String denominacion) {
		this.denominacionProperty().set(denominacion);
	}
	
	public StringProperty empleadorProperty() {
		return this.empleador;
	}
	
	@XmlElement
	public String getEmpleador() {
		return this.empleadorProperty().get();
	}
	
	public void setEmpleador(final String empleador) {
		this.empleadorProperty().set(empleador);
	}
}
