package peval3;

/**
 * Clase Usuario que simula la tabla USUARIO
 */
public class Usuario {
    /**
     * Campo tipo int que define el código del usuario
     */
    private int codigoUsuario;

    /**
     * Campo tipo String que define el nombre del usuario
     */
    private String nombre;

    /**
     * Campo tipo String que define los apellidos del usuario
     */
    private String apellidos;

    /**
     * Campo tipo String que define el dni del usuario
     */
    private String dni;

    /**
     * Campo tipo String que define el domicilio del usuario
     */
    private String domicilio;

    /**
     * Campo tipo String que define el nombre del lugar donde reside el usuario
     */
    private String poblacion;

    /**
     * Campo tipo String que define el nombre de la provincia del usuario
     */
    private String provincia;

    /**
     * Campo tipo String que define la fecha de nacimiento del usuario
     */
    private String fechaNac;

    /**
     * Constructor parametrizado de la clase Usuario
     *
     * @param codigoUsuario (Parámetro tipo int que define el código del usuario)
     * @param nombre        (Parámetro tipo String que define el nombre del usuario)
     * @param apellidos     (Parámetro tipo String que define los apellidos del usuario)
     * @param dni           (Parámetro tipo String que define el dni del usuario)
     * @param domicilio     (Parámetro tipo String que define el domicilio del usuario)
     * @param poblacion     (Parámetro tipo String que define el nombre del lugar donde reside el usuario)
     * @param provincia     (Parámetro tipo String que define el nombre la provincia del usuario)
     * @param fechaNac      (Parámetro tipo String que define la fecha de nacimiento del usuario)
     */
    public Usuario(int codigoUsuario, String nombre, String apellidos, String dni, String domicilio, String poblacion, String provincia, String fechaNac) {
        this.codigoUsuario = codigoUsuario;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.domicilio = domicilio;
        this.poblacion = poblacion;
        this.provincia = provincia;
        this.fechaNac = fechaNac;
    }

    /**
     * MÉTODOS GETTERS Y SETTERS
     */

    public int getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(int codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
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

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(String fechaNac) {
        this.fechaNac = fechaNac;
    }


    /**
     * Método toString de la clase Usuario
     */
    @Override
    public String toString() {
        return "Código de usuario: " + this.getCodigoUsuario() +
                "\n\tNombre: " + this.getNombre() +
                "\t\tApellidos: " + this.getApellidos() +
                "\n\tDNI: " + this.getDni() +
                "\n\tDomicilio: " + this.getDomicilio() +
                "\n\tPoblación: " + this.getPoblacion() +
                "\t\tProvincia: " + this.getProvincia() +
                "\n\tFecha de nacimiento: " + this.getFechaNac();
    }
}
