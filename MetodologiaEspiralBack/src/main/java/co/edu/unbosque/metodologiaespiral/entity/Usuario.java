package co.edu.unbosque.metodologiaespiral.entity;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entidad que representa un usuario en el sistema.
 */
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String correo;

    private String contrasenia;

    @Enumerated(EnumType.STRING)
    private Role role;

    public Usuario() {
    }

    public Usuario(String correo, String contrasenia) {
        this.correo = correo;
        this.contrasenia = contrasenia;
    }

    public Usuario(String nombre, String correo, String contrasenia, Role role,
                   String codigoVerificacion, boolean validarCodigo) {
        this.nombre = nombre;
        this.correo = correo;
        this.contrasenia = contrasenia;
        this.role = role;
    }

    public Usuario(Long id, String nombre, String correo, String contrasenia,
                   Role role, String codigoVerificacion, boolean validarCodigo) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasenia = contrasenia;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Usuario [id=" + id
                + ", nombre=" + nombre
                + ", correo=" + correo
                + ", role=" + role + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(contrasenia, correo, id, nombre, role);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (getClass() != obj.getClass())
            return false;

        Usuario other = (Usuario) obj;

        return Objects.equals(contrasenia, other.contrasenia)
                && Objects.equals(correo, other.correo)
                && Objects.equals(id, other.id)
                && Objects.equals(nombre, other.nombre)
                && role == other.role;
    }
}