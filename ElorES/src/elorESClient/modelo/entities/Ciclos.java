package elorESClient.modelo.entities;

import java.io.Serializable;

public class Ciclos implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String nombre;

    // Constructores
    public Ciclos() {
    }
    
    public Ciclos(Integer id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}

	// Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
