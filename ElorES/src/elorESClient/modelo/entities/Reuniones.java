package elorESClient.modelo.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Reuniones implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer idReunion;
    private String estado;
    private String estadoEus;
    private Users profesor;
    private Users alumno;
    private String idCentro;
    private String titulo;
    private String asunto;
    private String aula;
    private LocalDateTime fecha;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructores
    public Reuniones() {
    }

    public Reuniones(Integer idReunion, String estado, String estadoEus, Users profesor, Users alumno, String idCentro,
			String titulo, String asunto, String aula, LocalDateTime fecha, LocalDateTime createdAt,
			LocalDateTime updatedAt) {
		super();
		this.idReunion = idReunion;
		this.estado = estado;
		this.estadoEus = estadoEus;
		this.profesor = profesor;
		this.alumno = alumno;
		this.idCentro = idCentro;
		this.titulo = titulo;
		this.asunto = asunto;
		this.aula = aula;
		this.fecha = fecha;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	// Getters y Setters
    public Integer getIdReunion() {
        return idReunion;
    }

    public void setIdReunion(Integer idReunion) {
        this.idReunion = idReunion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEstadoEus() {
        return estadoEus;
    }

    public void setEstadoEus(String estadoEus) {
        this.estadoEus = estadoEus;
    }

    public String getIdCentro() {
        return idCentro;
    }

    public void setIdCentro(String idCentro) {
        this.idCentro = idCentro;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getAula() {
        return aula;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
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

    public Users getProfesores() {
        return profesor;
    }

    public void setProfesores(Users profesor) {
        this.profesor = profesor;
    }

    public Users getAlumnos() {
        return alumno;
    }

    public void setAlumnos(Users alumno) {
        this.alumno = alumno;
    }

}
