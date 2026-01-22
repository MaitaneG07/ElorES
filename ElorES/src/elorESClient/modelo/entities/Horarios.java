package elorESClient.modelo.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Horarios implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String dia;
    private Integer hora;
    private Users users;
    private Modulos modulos;
    private String aula;
    private String observaciones;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructores
    public Horarios() {
    }

    public Horarios(Integer id, String dia, Integer hora, Users users, Modulos modulos, String aula,
			String observaciones, LocalDateTime createdAt, LocalDateTime updatedAt) {
		super();
		this.id = id;
		this.dia = dia;
		this.hora = hora;
		this.users = users;
		this.modulos = modulos;
		this.aula = aula;
		this.observaciones = observaciones;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	// Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public Integer getHora() {
        return hora;
    }

    public void setHora(Integer hora) {
        this.hora = hora;
    }

    public String getAula() {
        return aula;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Modulos getModulos() {
        return modulos;
    }

    public void setModulos(Modulos modulos) {
        this.modulos = modulos;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

}
