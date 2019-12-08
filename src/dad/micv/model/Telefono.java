package dad.micv.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@XmlType
public class Telefono {
	
	private StringProperty numero;
	private ObjectProperty<TipoTelefono> tipo;
	
	public Telefono() {
		numero = new SimpleStringProperty(this, "numero");
		tipo = new SimpleObjectProperty<>(this, "tipo", TipoTelefono.DOMICILIO);
	}

	public StringProperty numeroProperty() {
		return this.numero;
	}
	
	@XmlAttribute
	public String getNumero() {
		return this.numeroProperty().get();
	}
	
	public void setNumero(final String numero) {
		this.numeroProperty().set(numero);
	}
	
	public ObjectProperty<TipoTelefono> tipoProperty() {
		return this.tipo;
	}
	
	@XmlAttribute
	public TipoTelefono getTipo() {
		return this.tipoProperty().get();
	}
	
	public void setTipo(final TipoTelefono tipo) {
		this.tipoProperty().set(tipo);
	}
}
