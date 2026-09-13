package co.edu.unbosque.metodologiaespiral.dto;

import java.util.ArrayList;

public class ProyectoDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private ArrayList<TareaDTO> tareas;
    private ArrayList<UsuarioDTO> usuarios;

    public ProyectoDTO() {

        this.tareas = new ArrayList<>();
        this.usuarios = new ArrayList<>();
    }

    public ProyectoDTO(Long id, String nombre, String descripcion,
                       ArrayList<TareaDTO> tareas,
                       ArrayList<UsuarioDTO> usuarios) {

        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tareas = tareas;
        this.usuarios = usuarios;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ArrayList<TareaDTO> getTareas() {
        return tareas;
    }

    public void setTareas(ArrayList<TareaDTO> tareas) {
        this.tareas = tareas;
    }

    public ArrayList<UsuarioDTO> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(ArrayList<UsuarioDTO> usuarios) {
        this.usuarios = usuarios;
    }
}