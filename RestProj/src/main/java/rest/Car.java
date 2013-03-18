package rest;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Car implements Serializable {

	private static final long serialVersionUID = 1L;

	public Car() {
	}

	public Car(Integer iId, String iName) {
		super();
		_id = iId;
		_name = iName;
	}

	private Integer _id;
	private String _name;

	// jaxson jersey annotations
	// @JsonProperty("_id")
	public Integer getId() {
		return _id;
	}

	public void setId(Integer iId) {
		_id = iId;
	}

	// @JsonProperty("_name")
	public String getName() {
		return _name;
	}

	public void setName(String iName) {
		_name = iName;
	}

	@Override
	public String toString() {
		return "Car [" + (_id != null ? "_id=" + _id + ", " : "") + (_name != null ? "_name=" + _name : "") + "]";
	}

}
