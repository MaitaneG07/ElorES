package elorESClient.modelo.entities;

import java.io.Serializable;

public class Modulos implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String nombre;
    private String nombreEus;
    private Integer horas;
    private Ciclos ciclos;
    private Integer curso;

    // Constructores
    public Modulos() {
    }

    public Modulos(Integer id, String nombre, String nombreEus, Integer horas, Ciclos ciclos, Integer curso) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.nombreEus = nombreEus;
		this.horas = horas;
		this.ciclos = ciclos;
		this.curso = curso;
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

    public String getNombreEus() {
        return nombreEus;
    }

    public void setNombreEus(String nombreEus) {
        this.nombreEus = nombreEus;
    }

    public Integer getHoras() {
        return horas;
    }

    public void setHoras(Integer horas) {
        this.horas = horas;
    }

    public Integer getCurso() {
        return curso;
    }

    public void setCurso(Integer curso) {
        this.curso = curso;
    }

    public Ciclos getCiclos() {
        return ciclos;
    }

    public void setCiclos(Ciclos ciclos) {
        this.ciclos = ciclos;
    }

}
