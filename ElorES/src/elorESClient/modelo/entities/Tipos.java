package elorESClient.modelo.entities;

import java.io.Serializable;

public class Tipos implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private String nameEu;

    // Constructores
    public Tipos() {
    }

    public Tipos(Integer id, String name, String nameEu) {
		super();
		this.id = id;
		this.name = name;
		this.nameEu = nameEu;
	}

	// Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameEu() {
        return nameEu;
    }

    public void setNameEu(String nameEu) {
        this.nameEu = nameEu;
    }

}
