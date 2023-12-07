package com.example.crud_api.Entidad;

public class Alumno {
    private int idAlumno;
    private String nombre;
    private String apellido;
    private String correo;
    private int edad;
    private String direccion;

    public Alumno() {
    }

    public Alumno(int idAlumno, String nombre, String apellido, String correo, int edad, String direccion) {
        this.idAlumno = idAlumno;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.edad = edad;
        this.direccion = direccion;
    }

    public int getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return
                "Id=" + idAlumno +"\n" +
                        "Nombre: " + nombre + "\n" +
                        "Apellido: " + apellido + "\n" +
                        "Correo: " + correo + "\n" +
                        "Edad: " + edad + "\n" +
                        "Dirección: " + direccion;
    }
}
