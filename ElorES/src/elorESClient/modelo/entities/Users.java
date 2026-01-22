package elorESClient.modelo.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String email;
    private String username;
    private String password;
    private String nombre;
    private String apellidos;
    private String dni;
    private String direccion;
    private String telefono1;
    private String telefono2;
    private Tipos tipos;
    private String argazkiaUrl;

    public Users() {
    }

    public Users(Integer id, String email, String username, String password, String nombre, String apellidos,
			String dni, String direccion, String telefono1, String telefono2, Tipos tipos, String argazkiaUrl) {
		super();
		this.id = id;
		this.email = email;
		this.username = username;
		this.password = password;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.dni = dni;
		this.direccion = direccion;
		this.telefono1 = telefono1;
		this.telefono2 = telefono2;
		this.tipos = tipos;
		this.argazkiaUrl = argazkiaUrl;
	}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono1() {
        return telefono1;
    }

    public void setTelefono1(String telefono1) {
        this.telefono1 = telefono1;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public String getArgazkiaUrl() {
        return argazkiaUrl;
    }

    public void setArgazkiaUrl(String argazkiaUrl) {
        this.argazkiaUrl = argazkiaUrl;
    }

    public Tipos getTipos() {
        return tipos;
    }

    public void setTipos(Tipos tipos) {
        this.tipos = tipos;
    }

}
