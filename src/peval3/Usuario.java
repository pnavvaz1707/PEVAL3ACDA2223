package peval3;

public class Usuario {
    private int codigoUsuario;
    private String nombre;
    private String apellidos;
    private String dni;
    private String domicilio;
    private String poblacion;
    private String provincia;
    private String fechaNac;

    /**
     * Constructor parametrizado de la clase Usuario
     *
     * @param codigoUsuario (Parámetro tipo int que define el código del usuario)
     * @param nombre        (Parámetro tipo String que define el nombre del usuario)
     * @param apellidos     (Parámetro tipo String que define los apellidos del usuario)
     * @param dni           (Parámetro tipo String que define el dni del usuario)
     * @param domicilio     (Parámetro tipo String que define el domicilio del usuario)
     * @param poblacion     (Parámetro tipo String que define la población del usuario)
     * @param provincia     (Parámetro tipo String que define la provincia del usuario)
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
}
