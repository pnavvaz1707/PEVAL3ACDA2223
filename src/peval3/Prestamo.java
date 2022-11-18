package peval3;

import java.time.LocalDate;
import java.util.Date;

public class Prestamo {
    private int codigoPrestamo;
    private Libro libro;
    private Usuario usuario;
    private Date fechaSalida;
    private Date fechaMaxDevolucion;
    private Date fechaDevolucion;

    /**
     * Constructor parametrizado de la clase Prestamo
     *
     * @param codigoPrestamo     (Parámetro tipo int que define el código del préstamo)
     * @param libro              (Parámetro tipo Libro que sirve para referenciar el libro al que está relacionado el préstamo)
     * @param usuario            (Parámetro tipo Usuario que sirve para referenciar el usuario al que está relacionado el préstamo)
     * @param fechaSalida        (Parámetro tipo LocalDate que define la fecha de salida del prestamo)
     * @param fechaMaxDevolucion (Parámetro tipo LocalDate que define la fecha máxima de devolución del prestamo)
     * @param fechaDevolucion    (Parámetro tipo LocalDate que define la fecha de devolución del prestamo)
     */
    public Prestamo(int codigoPrestamo, Libro libro, Usuario usuario, Date fechaSalida, Date fechaMaxDevolucion, Date fechaDevolucion) {
        this.codigoPrestamo = codigoPrestamo;
        this.libro = libro;
        this.usuario = usuario;
        this.fechaSalida = fechaSalida;
        this.fechaMaxDevolucion = fechaMaxDevolucion;
        this.fechaDevolucion = fechaDevolucion;
    }

    public int getCodigoPrestamo() {
        return codigoPrestamo;
    }

    public void setCodigoPrestamo(int codigoPrestamo) {
        this.codigoPrestamo = codigoPrestamo;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public Date getFechaMaxDevolucion() {
        return fechaMaxDevolucion;
    }

    public void setFechaMaxDevolucion(Date fechaMaxDevolucion) {
        this.fechaMaxDevolucion = fechaMaxDevolucion;
    }

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    @Override
    public String toString() {
        return "Código del préstamo: " + this.getCodigoPrestamo() +
                "\n\tNombre del libro: " + this.getLibro().getNombreLibro() +
                "\t\tEditorial del libro: " + this.getLibro().getEditorial() +
                "\n\tNombre del usuario: " + this.getUsuario().getNombre() +
                "\t\tDNI del usuario: " + this.getUsuario().getDni() +
                "\n\tFecha salida: " + this.getFechaSalida() +
                "\n\tFecha máxima de devolución: " + this.getFechaMaxDevolucion() +
                "\t\tFecha de devolución: " + this.getFechaDevolucion();
    }
}
