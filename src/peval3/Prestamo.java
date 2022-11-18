package peval3;

import java.util.Date;

/**
 * Clase Prestamo que simula la tabla PRESTAMOS
 */
public class Prestamo {
    /**
     * Campo tipo entero que define el código del préstamo
     */
    private int codigoPrestamo;

    /**
     * Campo tipo Libro que referencia al libro al que está asignado este préstamo
     */
    private Libro libro;

    /**
     * Campo tipo Usuario que referencia al usuario al que se le asigna este préstamo
     */
    private Usuario usuario;

    /**
     * Campo tipo Date que define la fecha en la que se inició el préstamo
     */
    private Date fechaSalida;

    /**
     * Campo tipo Date que define la fecha máxima en la que se puede devolver el préstamo
     */
    private Date fechaMaxDevolucion;

    /**
     * Campo tipo Date que define la fecha en la que se realizó la devolución del préstamo
     */
    private Date fechaDevolucion;

    /**
     * Constructor parametrizado de la clase Prestamo
     *
     * @param codigoPrestamo     (Parámetro tipo int que define el código del préstamo)
     * @param libro              (Parámetro tipo Libro que sirve para referenciar el libro al que está relacionado el préstamo)
     * @param usuario            (Parámetro tipo Usuario que sirve para referenciar el usuario al que está relacionado el préstamo)
     * @param fechaSalida        (Parámetro tipo Date que define la fecha de salida del prestamo)
     * @param fechaMaxDevolucion (Parámetro tipo Date que define la fecha máxima de devolución del prestamo)
     * @param fechaDevolucion    (Parámetro tipo Date que define la fecha de devolución del prestamo)
     */
    public Prestamo(int codigoPrestamo, Libro libro, Usuario usuario, Date fechaSalida, Date fechaMaxDevolucion, Date fechaDevolucion) {
        this.codigoPrestamo = codigoPrestamo;
        this.libro = libro;
        this.usuario = usuario;
        this.fechaSalida = fechaSalida;
        this.fechaMaxDevolucion = fechaMaxDevolucion;
        this.fechaDevolucion = fechaDevolucion;
    }

    /**
     * MÉTODOS GETTERS Y SETTERS
     */

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

    /**
     * Método toString de la clase Prestamo
     */
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
