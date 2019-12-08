package dad.micv.model;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@XmlRootElement
@XmlType
public class CV {
	
	private ObjectProperty<Personal> personal;
	private ListProperty<Conocimiento> habilidades;
	private ListProperty<Experiencia> experiencias;
	private ListProperty<Titulo> formacion;
	private ObjectProperty<Contacto> contacto;
	
	public CV() {
		personal = new SimpleObjectProperty<>(this, "personal", new Personal());
		habilidades = new SimpleListProperty<>(this, "habilidades", FXCollections.observableArrayList());
		experiencias = new SimpleListProperty<>(this, "experiencias", FXCollections.observableArrayList());
		formacion = new SimpleListProperty<>(this, "formacion", FXCollections.observableArrayList());
		contacto = new SimpleObjectProperty<>(this, "contacto", new Contacto());
	}

	public ObjectProperty<Personal> personalProperty() {
		return this.personal;
	}

	@XmlElement
	public Personal getPersonal() {
		return this.personalProperty().get();
	}
	
	public void setPersonal(final Personal personal) {
		this.personalProperty().set(personal);
	}
	
	public ListProperty<Conocimiento> habilidadesProperty() {
		return this.habilidades;
	}
	
	@XmlElement
	public ObservableList<Conocimiento> getHabilidades() {
		return this.habilidadesProperty().get();
	}
	
	public void setHabilidades(final ObservableList<Conocimiento> habilidades) {
		this.habilidadesProperty().set(habilidades);
	}
	
	public ListProperty<Experiencia> experienciasProperty() {
		return this.experiencias;
	}
	
	@XmlElement
	public ObservableList<Experiencia> getExperiencias() {
		return this.experienciasProperty().get();
	}

	public void setExperiencias(final ObservableList<Experiencia> experiencias) {
		this.experienciasProperty().set(experiencias);
	}
	
	public ListProperty<Titulo> formacionProperty() {
		return this.formacion;
	}
	
	@XmlElement
	public ObservableList<Titulo> getFormacion() {
		return this.formacionProperty().get();
	}

	public void setFormacion(final ObservableList<Titulo> formacion) {
		this.formacionProperty().set(formacion);
	}

	public ObjectProperty<Contacto> contactoProperty() {
		return this.contacto;
	}
	
	@XmlElement
	public Contacto getContacto() {
		return this.contactoProperty().get();
	}
	
	public void setContacto(final Contacto contacto) {
		this.contactoProperty().set(contacto);
	}
	
	public void save(File file) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(CV.class);
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.marshal(this, file);		
	}
	
	public static CV load(File file) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(CV.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		return (CV) unmarshaller.unmarshal(file);		
	}
}
