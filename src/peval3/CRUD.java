package peval3;

import org.neodatis.odb.ODB;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.And;
import org.neodatis.odb.core.query.criteria.ICriterion;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;
import org.neodatis.odb.impl.core.query.values.ValuesCriteriaQuery;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Scanner;

/**
 * Clase utilizada para realizar las operaciones CRUD (Create, Read, Update y Delete)
 */
public class CRUD {

    /**
     * Objeto que referencia a la base de datos Neodatis
     */
    public static ODB odb;

    /**
     * Escáner para leer los datos introducidos por teclado
     */
    private static final Scanner teclado = new Scanner(System.in);


    // --------------------------------- APARTADO INSERCIONES ---------------------------------

    /**
     * Método para crear un libro con los datos introducidos por teclado
     */
    public static void registrarLibro() {
        //Obtenemos el código máximo almacenado en la base de datos como objeto y lo pasamos a string para luego pasarlo a tipo entero
        int codigoMax = Integer.parseInt(odb.getValues(new ValuesCriteriaQuery(Libro.class).max("codigoLibro")).getFirst().getByAlias("codigoLibro").toString());


        //Declaramos las variables para rellenar para registrar un libro
        String nombreLibro, editorial, autor, genero, paisAutor;
        int numPaginas, anyoEdicion;
        double precioLibro;

        //Bucle para que no se repita el libro introducido
        do {
            nombreLibro = Auxiliar.leerCadena("Introduce el nombre del libro");
            editorial = Auxiliar.leerCadena("Introduce la editorial del libro");
        } while (comprobarLibroDuplicado(nombreLibro, editorial));

        //Solicitamos los datos del nuevo libro
        autor = Auxiliar.leerCadena("Introduce el autor del libro");
        genero = Auxiliar.leerCadena("Introduce el género del libro");
        paisAutor = Auxiliar.leerCadena("Introduce el país del autor del libro");
        numPaginas = Auxiliar.solicitarEnteroEnUnRango(1, 3500, "Introduce el número de páginas del libro: ");
        anyoEdicion = Auxiliar.solicitarEnteroEnUnRango(1000, LocalDateTime.now().getYear(), "Introduce el año de edición del libro");
        precioLibro = Auxiliar.solicitarDoubleEnUnRango(0, Double.MAX_VALUE, "Introduce el precio del libro");

        System.out.println("-------------- El libro se ha registrado --------------");

        //Almacenamos el libro solicitado en la base de datos
        odb.store(new Libro(codigoMax, nombreLibro, editorial, autor, genero, paisAutor, numPaginas, anyoEdicion, precioLibro));
    }

    /**
     * Método que devuelve true si el nombre del libro y el de la editorial ya existen o false si no existe.
     *
     * @param nombreLibro (Parámetro tipo String que define el nombre del libro a comprobar)
     * @param editorial   (Parámetro tipo String que define el nombre de la editorial a comprobar)
     * @return (Devuelve un boolean true si está repetido o false si no lo está)
     */
    private static boolean comprobarLibroDuplicado(String nombreLibro, String editorial) {
        boolean repetido = false;

        IQuery queryNombreLibro = new CriteriaQuery(Libro.class, Where.equal("nombreLibro", nombreLibro));
        IQuery queryEditorial = new CriteriaQuery(Libro.class, Where.equal("editorial", editorial));

        if (odb.getObjects(queryNombreLibro).size() > 0 && odb.getObjects(queryEditorial).size() > 0) {
            System.err.println("El libro " + nombreLibro + " de la editorial " + editorial + " ya existe");
            repetido = true;
        }
        return repetido;
    }

    // --------------------------------- APARTADO BORRADOS ---------------------------------

    /**
     * Método para borrar un usuario enviado por parámetro
     *
     * @param usuario (Objeto tipo usuario que se desea borrar de la base de datos)
     */
    public static void borrarUsuario(Usuario usuario) {
        System.out.println("Teclee 1 si desea borrar a " + usuario.getNombre() + " con DNI: " + usuario.getDni());
        int respuestaBorrado = teclado.nextInt();

        //Si el usuario introduce 1 es porque está seguro de que desea borrar al usuario y lo borramos, si no introduce 1 no lo borramos
        if (respuestaBorrado == 1) {

            //Filtramos los préstamos según el usuario seleccionado anteriormente
            IQuery queryPrestamosUsuario = odb.criteriaQuery(Prestamo.class, Where.equal("usuario", usuario));
            Objects<Prestamo> prestamosABorrar = odb.getObjects(queryPrestamosUsuario);

            //Bucle para eliminar todos los préstamos relacionados con el usuario
            for (Prestamo prestamo : prestamosABorrar) {
                odb.delete(prestamo);
            }

            //Eliminamos finalmente el usuario
            odb.delete(usuario);
            odb.commit();

            System.out.println("Se ha borrado el usuario y los préstamos relacionados con él");
        } else {
            System.out.println("El usuario no se ha borrado");
        }
    }

    // --------------------------------- APARTADO MODIFICACIONES ---------------------------------

    /**
     * Método para modificar el préstamo que el usuario desee
     */
    public static void modificarPrestamo() {
        //Solicitamos al usuario que seleccione el código de un préstamo
        System.out.println("Introduce el código del préstamo que desees modificar");
        int codigoPrestamoSel = teclado.nextInt();

        try {
            //Filtramos según préstamo seleccionado anteriormente
            IQuery queryPrestamoSel = new CriteriaQuery(Prestamo.class, Where.equal("codigoPrestamo", codigoPrestamoSel));
            Prestamo prestamo = (Prestamo) odb.getObjects(queryPrestamoSel).getFirst();

            System.out.println("Este es el préstamo seleccionado: ");
            System.out.println(prestamo);

            System.out.println("Teclee 1 si seguro desea modificar el usuario de este préstamo");
            int respuestaModificacion = teclado.nextInt();

            //Si el usuario introduce 1 es porque modificamos el préstamo, si no introduce 1 no lo modificamos
            if (respuestaModificacion == 1) {
                try {
                    //Listamos los usuarios
                    mostrarUsuarios(odb.getObjects(Usuario.class));

                    //Solicitamos que seleccione un usuario para que se le asigne el préstamo
                    Usuario usuario = obtenerUsuario("Selecciona el código de usuario al que desea asignarle este préstamo");

                    //Le asignamos al préstamo el usuario seleccionado y lo almacenamos
                    prestamo.setUsuario(usuario);
                    odb.store(prestamo);
                    odb.commit();
                } catch (IndexOutOfBoundsException e) {
                    System.err.println("El usuario seleccionado no existe");
                }

            } else {
                System.out.println("El usuario no se ha modificado");
            }
        } catch (IndexOutOfBoundsException e) {
            System.err.println("No existe un préstamo con ese código");
        }
    }

    // --------------------------------- APARTADO CONSULTAS ---------------------------------

    /**
     * Método para mostrar todos los libros enviados por parámetro
     *
     * @param libros (Lista de libros que se desean consultar)
     */
    public static void mostrarLibros(Objects<Libro> libros) {
        Libro libro;

        int contador = 0;
        boolean sigue = true;

        while (libros.hasNext() && sigue) {
            libro = libros.next();

            System.out.println(libro);
            System.out.println("////////////////////////////////////////////////////////////////////////////////");
            contador++;

            if (contador == 10) {
                sigue = Auxiliar.sigPagina();
                contador = 0;
            }
        }
    }

    /**
     * Método para mostrar todos los usuarios enviados por parámetro
     *
     * @param usuarios (Lista de usuarios que se desean consultar)
     */
    public static void mostrarUsuarios(Objects<Usuario> usuarios) {
        Usuario usuario;

        int contador = 0;
        boolean sigue = true;

        while (usuarios.hasNext() && sigue) {
            usuario = usuarios.next();

            System.out.println(usuario);
            System.out.println("////////////////////////////////////////////////////////////////////////////////");
            contador++;

            if (contador == 10) {
                sigue = Auxiliar.sigPagina();
                contador = 0;
            }
        }
    }

    /**
     * Método para mostrar todos los préstamos enviados por parámetro
     *
     * @param prestamos (Lista de préstamos que se desean consultar)
     */
    public static void mostrarPrestamos(Objects<Prestamo> prestamos) {
        Prestamo prestamo;

        int contador = 0;
        boolean sigue = true;

        while (prestamos.hasNext() && sigue) {
            prestamo = prestamos.next();

            System.out.println(prestamo);
            System.out.println("////////////////////////////////////////////////////////////////////////////////");
            contador++;

            if (contador == 10) {
                sigue = Auxiliar.sigPagina();
                contador = 0;
            }
        }
    }

    /**
     * Método para solicitar al usuario que introduzca un número y si corresponde a un objeto Usuario existente lo devolvemos
     *
     * @param texto (Cadena que define la pregunta que le realizará el programa al usuario)
     * @return (El objeto Usuario seleccionado por el usuario)
     */
    public static Usuario obtenerUsuario(String texto) {
        System.out.println(texto);

        int codigoUsuarioSel = teclado.nextInt();

        IQuery queryUsuarioSel = new CriteriaQuery(Usuario.class, Where.equal("codigoUsuario", codigoUsuarioSel));
        return (Usuario) odb.getObjects(queryUsuarioSel).getFirst();
    }

    /**
     * Método para obtener los préstamos devueltos tarde de un usuario determinado
     *
     * @param usuario (Usuario al que consultaremos sus préstamos tardíos)
     */
    public static void obtenerPrestamosTardios(Usuario usuario) {
        //Obtenemos los préstamos del usuario seleccionado
        IQuery queryPrestamosUsuario = odb.criteriaQuery(Prestamo.class, Where.equal("usuario", usuario));
        Objects<Prestamo> prestamosUsuario = odb.getObjects(queryPrestamosUsuario);

        //Por cada préstamo del usuario comprobamos si la fecha de devolución es después de la fecha máxima de devolución y lo imprimimos
        for (Prestamo prestamo : prestamosUsuario) {
            IQuery queryPrestamosTardios = new CriteriaQuery(Prestamo.class, Where.gt("fechaDevolucion", prestamo.getFechaMaxDevolucion()));
            Prestamo prestamo1 = (Prestamo) odb.getObjects(queryPrestamosTardios).getFirst();
            System.out.println(prestamo1);
            System.out.println("////////////////////////////////////////////////////////////////////////////////////////////");
        }
    }

    /**
     * Método para consultar los préstamos según una provincia y un plazo de fechas introducidos por teclado
     */
    public static void consultarPrestamosPorProvinciaYFechas() {
        //Obtenemos la provincia sobre la cual queremos filtrar
        String provincia = Auxiliar.leerCadena("Introduce la provincia");

        //Obtenemos el intervalo de tiempo sobre el que deseamos buscar
        Date fechaInicio, fechaFinal;
        do {
            fechaInicio = Conversor.solicitarFecha("Introduce la primera fecha");
            fechaFinal = Conversor.solicitarFecha("Introduce la segunda fecha");

        } while (fechaInicio.after(fechaFinal));

        System.out.println("\nPréstamos realizados en " + provincia + " entre " + fechaInicio + " y " + fechaFinal + "\n");

        //Filtramos según la provincia del usuario y el intervalo de fechas introducidos anteriormente
        ICriterion criterionPrestamosProvinciaPrecio = new And().add(Where.equal("usuario.provincia", provincia)).add(Where.gt("fechaSalida", fechaInicio)).add(Where.lt("fechaSalida", fechaFinal));
        IQuery queryPrestamosProvinciaPrecio = odb.criteriaQuery(Prestamo.class, criterionPrestamosProvinciaPrecio);

        //Mostramos los resultados tras usar el filtro
        Objects<Prestamo> prestamos = odb.getObjects(queryPrestamosProvinciaPrecio);
        CRUD.mostrarPrestamos(prestamos);
    }

    /**
     * Método para consultar los libros por un género y precio determinado
     */
    public static void consultarPorGenerosYPrecio() {
        /*
        Comento esta parte del código porque no me funciona y no sé arreglarlo

        //Mostramos los géneros posibles
        Values librosP = odb.getValues(new ValuesCriteriaQuery(Libro.class).groupBy("genero"));

        System.out.println(librosP.toString());

        for (ObjectValues objectValues : librosP) {
            System.out.println(objectValues.getByIndex(0));
        }

        */

        //Recogemos los datos para filtrar
        String genero = Auxiliar.leerCadena("Introduce el género");
        double precio = Auxiliar.solicitarDoubleEnUnRango(0, Double.MAX_VALUE, "Introduce el precio");

        //Filtramos según el género del libro y el precio
        ICriterion criterionLibroPrecioGenero = new And().add(Where.equal("genero", genero)).add(Where.equal("precioLibro", precio));
        IQuery query = new CriteriaQuery(Libro.class, criterionLibroPrecioGenero);

        Objects<Libro> libros = odb.getObjects(query);
        CRUD.mostrarLibros(libros);
    }
}
