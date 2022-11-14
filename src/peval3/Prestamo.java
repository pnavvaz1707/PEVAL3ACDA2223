package peval3;

public class Prestamo {
    private int codigoPrestamo;
    private Libro libro;
    private Usuario usuario;
    private String fechaSalida;
    private String fechaMaxDevolucion;
    private String fechaDevolucion;

    /**
     * Constructor parametrizado de la clase Prestamo
     *
     * @param codigoPrestamo     (Parámetro tipo int que define el código del préstamo)
     * @param libro              (Parámetro tipo Libro que sirve para referenciar el libro al que está relacionado el préstamo)
     * @param usuario            (Parámetro tipo Usuario que sirve para referenciar el usuario al que está relacionado el préstamo)
     * @param fechaSalida        (Parámetro tipo String que define la fecha de salida del prestamo)
     * @param fechaMaxDevolucion (Parámetro tipo String que define la fecha máxima de devolución del prestamo)
     * @param fechaDevolucion    (Parámetro tipo String que define la fecha de devolución del prestamo)
     */
    public Prestamo(int codigoPrestamo, Libro libro, Usuario usuario, String fechaSalida, String fechaMaxDevolucion, String fechaDevolucion) {
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

    public String getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(String fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public String getFechaMaxDevolucion() {
        return fechaMaxDevolucion;
    }

    public void setFechaMaxDevolucion(String fechaMaxDevolucion) {
        this.fechaMaxDevolucion = fechaMaxDevolucion;
    }

    public String getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(String fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }
}
