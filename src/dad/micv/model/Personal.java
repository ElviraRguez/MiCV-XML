package dad.micv.model;

import java.time.LocalDate;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@XmlType
public class Personal {

	private StringProperty identificacion;
	private StringProperty nombre;
	private StringProperty apellidos;
	private ObjectProperty<LocalDate> fechaNacimiento;
	private StringProperty direccion;
	private StringProperty codigoPostal;
	private StringProperty localidad;
	private StringProperty pais;
	private ListProperty<Nacionalidad> nacionalidades;
	
	public Personal() {
		identificacion = new SimpleStringProperty(this, "identificacion");
		nombre = new SimpleStringProperty(this, "nombre");
		apellidos = new SimpleStringProperty(this, "apellidos");
		fechaNacimiento = new SimpleObjectProperty<>(this, "fechaNacimiento");
		direccion = new SimpleStringProperty(this, "direccion");
		codigoPostal = new SimpleStringProperty(this, "codigoPostal");
		localidad = new SimpleStringProperty(this, "localidad");
		pais = new SimpleStringProperty(this, "pais");
		nacionalidades = new SimpleListProperty<>(this, "nacionalidades", FXCollections.observableArrayList());
	}

	public StringProperty identificacionProperty() {
		return this.identificacion;
	}
	
	@XmlAttribute
	public String getIdentificacion() {
		return this.identificacionProperty().get();
	}
	
	public void setIdentificacion(final String identificacion) {
		this.identificacionProperty().set(identificacion);
	}
	
	public StringProperty nombreProperty() {
		return this.nombre;
	}
	
	@XmlElement
	public String getNombre() {
		return this.nombreProperty().get();
	}
	
	public void setNombre(final String nombre) {
		this.nombreProperty().set(nombre);
	}
	
	public StringProperty apellidosProperty() {
		return this.apellidos;
	}
	
	@XmlElement
	public String getApellidos() {
		return this.apellidosProperty().get();
	}
	
	public void setApellidos(final String apellidos) {
		this.apellidosProperty().set(apellidos);
	}
	
	public ObjectProperty<LocalDate> fechaNacimientoProperty() {
		return this.fechaNacimiento;
	}
	
	@XmlElement
	@XmlJavaTypeAdapter(value = LocalDateAdapter.class)
	public LocalDate getFechaNacimiento() {
		return this.fechaNacimientoProperty().get();
	}

	public void setFechaNacimiento(final LocalDate fechaNacimiento) {
		this.fechaNacimientoProperty().set(fechaNacimiento);
	}

	public StringProperty direccionProperty() {
		return this.direccion;
	}
	
	@XmlElement
	public String getDireccion() {
		return this.direccionProperty().get();
	}

	public void setDireccion(final String direccion) {
		this.direccionProperty().set(direccion);
	}

	public StringProperty codigoPostalProperty() {
		return this.codigoPostal;
	}
	
	@XmlElement
	public String getCodigoPostal() {
		return this.codigoPostalProperty().get();
	}
	
	public void setCodigoPostal(final String codigoPostal) {
		this.codigoPostalProperty().set(codigoPostal);
	}
	
	public StringProperty localidadProperty() {
		return this.localidad;
	}
	
	@XmlElement
	public String getLocalidad() {
		return this.localidadProperty().get();
	}
	
	public void setLocalidad(final String localidad) {
		this.localidadProperty().set(localidad);
	}
	
	public StringProperty paisProperty() {
		return this.pais;
	}

	@XmlElement
	public String getPais() {
		return this.paisProperty().get();
	}
	
	public void setPais(final String pais) {
		this.paisProperty().set(pais);
	}
	
	public ListProperty<Nacionalidad> nacionalidadesProperty() {
		return this.nacionalidades;
	}
	
	public ObservableList<Nacionalidad> getNacionalidades() {
		return this.nacionalidadesProperty().get();
	}
	
	public void setNacionalidades(final ObservableList<Nacionalidad> nacionalidades) {
		this.nacionalidadesProperty().set(nacionalidades);
	}
}
