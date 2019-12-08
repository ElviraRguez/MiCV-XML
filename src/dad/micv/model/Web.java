package dad.micv.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@XmlType
public class Web {
	
	private StringProperty url;
	
	public Web() {
		url = new SimpleStringProperty(this, "url");
	}

	public StringProperty urlProperty() {
		return this.url;
	}
	
	@XmlAttribute
	public String getUrl() {
		return this.urlProperty().get();
	}
	
	public void setUrl(final String url) {
		this.urlProperty().set(url);
	}
}
