package co.edu.unbosque.metodologiaespiral.dto;

import co.edu.unbosque.metodologiaespiral.entity.Estado;

public class TareaDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private String fechaEntrega;
    private String fechaInicio;
    private Estado estado;

    public TareaDTO() {
    }

    public TareaDTO(Long id, String nombre, String descripcion,
                    String fechaEntrega, String fechaInicio,
                    Estado estado) {

        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaEntrega = fechaEntrega;
        this.fechaInicio = fechaInicio;
        this.estado = estado;
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

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}