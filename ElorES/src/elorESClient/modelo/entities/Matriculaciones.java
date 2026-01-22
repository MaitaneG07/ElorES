package elorESClient.modelo.entities;

import java.io.Serializable;
import java.time.LocalDate;

public class Matriculaciones implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Users users;
    private Ciclos ciclos;
    private Integer curso;
    private LocalDate fecha;

    // Constructores
    public Matriculaciones() {
    }

    public Matriculaciones(Integer id, Users users, Ciclos ciclos, Integer curso, LocalDate fecha) {
		super();
		this.id = id;
		this.users = users;
		this.ciclos = ciclos;
		this.curso = curso;
		this.fecha = fecha;
	}

	// Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCurso() {
        return curso;
    }

    public void setCurso(Integer curso) {
        this.curso = curso;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Ciclos getCiclos() {
        return ciclos;
    }

    public void setCiclos(Ciclos ciclos) {
        this.ciclos = ciclos;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

}
