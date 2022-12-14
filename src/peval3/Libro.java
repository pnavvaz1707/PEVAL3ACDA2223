package peval3;

/**
 * Clase Libro que simula la tabla LIBROS
 */
public class Libro {
    /**
     * Campo tipo int que define el código del libro
     */
    private int codigoLibro;

    /**
     * Campo tipo String que define el nombre del libro
     */
    private String nombreLibro;

    /**
     * Campo tipo String que define la editorial del libro
     */
    private String editorial;

    /**
     * Campo tipo String que define el nombre del autor del libro
     */
    private String autor;

    /**
     * Campo tipo String que define el género del libro
     */
    private String genero;

    /**
     * Campo tipo String que define el país del autor del libro
     */
    private String paisAutor;

    /**
     * Campo tipo entero que define el número de páginas del libro
     */
    private int numPaginas;

    /**
     * Campo tipo entero que define el año de edición del libro
     */
    private int anyoEdicion;

    /**
     * Campo tipo double que define el precio del libro
     */
    private double precioLibro;

    /**
     * Constructor parametrizado de la clase Libro
     *
     * @param codigoLibro (Parámetro tipo int que define el código del libro)
     * @param nombreLibro (Parámetro tipo String que define el nombre del libro)
     * @param editorial   (Parámetro tipo String que define la editorial del libro)
     * @param autor       (Parámetro tipo String que define el nombre del autor del libro)
     * @param genero      (Parámetro tipo String que define el género del libro)
     * @param paisAutor   (Parámetro tipo String que define el pais del autor del libro)
     * @param numPaginas  (Parámetro tipo int que define el número de páginas que tiene el libro)
     * @param anyoEdicion (Parámetro tipo int que define el año que se editó el libro)
     * @param precioLibro (Parámetro tipo double que define el precio del libro)
     */
    public Libro(int codigoLibro, String nombreLibro, String editorial, String autor, String genero, String paisAutor, int numPaginas, int anyoEdicion, double precioLibro) {
        this.codigoLibro = codigoLibro;
        this.nombreLibro = nombreLibro;
        this.editorial = editorial;
        this.autor = autor;
        this.genero = genero;
        this.paisAutor = paisAutor;
        this.numPaginas = numPaginas;
        this.anyoEdicion = anyoEdicion;
        this.precioLibro = precioLibro;
    }

    /**
     * MÉTODOS GETTERS Y SETTERS
     */
    public int getCodigoLibro() {
        return codigoLibro;
    }

    public void setCodigoLibro(int codigoLibro) {
        this.codigoLibro = codigoLibro;
    }

    public String getNombreLibro() {
        return nombreLibro;
    }

    public void setNombreLibro(String nombreLibro) {
        this.nombreLibro = nombreLibro;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getPaisAutor() {
        return paisAutor;
    }

    public void setPaisAutor(String paisAutor) {
        this.paisAutor = paisAutor;
    }

    public int getNumPaginas() {
        return numPaginas;
    }

    public void setNumPaginas(int numPaginas) {
        this.numPaginas = numPaginas;
    }

    public int getAnyoEdicion() {
        return anyoEdicion;
    }

    public void setAnyoEdicion(int anyoEdicion) {
        this.anyoEdicion = anyoEdicion;
    }

    public double getPrecioLibro() {
        return precioLibro;
    }

    public void setPrecioLibro(double precioLibro) {
        this.precioLibro = precioLibro;
    }

    /**
     * Método toString de la clase Libro
     */
    @Override
    public String toString() {
        return "Código de libro: " + this.getCodigoLibro() +
                "\n\tNombre: " + this.getNombreLibro() +
                "\t\tEditorial: " + this.getEditorial() +
                "\n\tGénero: " + this.getGenero() +
                "\n\tAutor: " + this.getAutor() +
                "\t\tPaís del autor: " + this.getPaisAutor() +
                "\n\tNúmero de páginas: " + this.getNumPaginas() +
                "\n\tAño de edición: " + this.getAnyoEdicion() +
                "\n\tPrecio: " + this.getPrecioLibro();
    }
}
