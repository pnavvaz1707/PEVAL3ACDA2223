package peval3;

import org.neodatis.odb.ODB;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * Clase utilizada para realizar distintas conversiones como la conversión de la base de datos SQL a Neodatis o
 * las conversiones de las fechas tipo String a LocalDate y viceversa.
 */
public class Conversor {

    /**
     * Objeto tipo Connection que referencia la conexión con la base de datos SQL
     */
    private static Connection conexion;

    /**
     * Cadena que indica el formato que deben cumplir las fechas
     */
    private static final String formato = "dd/MM/yyyy";

    /**
     * Objeto tipo SimpleDateFormat que permite formatear las fechas según el formato indicado
     */
    private static final SimpleDateFormat formatter = new SimpleDateFormat(formato);

    /**
     * Método que se conecta a una base de datos SQL con los datos enviados como parámetro (bdNombre,
     * usuario, password) y la convierte en una base de datos Neodatis que se almacena en el objeto ODB
     * enviado como parámetro
     *
     * @param bdNombre (Nombre de la base de datos SQL)
     * @param usuario  (Nombre del usuario de la base de datos SQL)
     * @param password (Contraseña del usuario de la base de datos SQL)
     * @param odb      (Objeto que referncia a la base de datos Neodatis)
     */
    public static void convertirSQLtoODB(String bdNombre, String usuario, String password, ODB odb) {
        try {
            //Intentamos cargar el driver para utilizado para conectarse a base de datos SQL desde Java y nos intentamos conectar con los datos enviados como parámetro
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + bdNombre, usuario, password);

            //Intentamos pasar los libros de la base de datos SQL a la base de datos Neodatis
            try {
                crearLibros(odb);
            } catch (SQLException e) {
                System.err.println("Ha habido un error en la creación de libros");
            }

            //Intentamos pasar los usuarios de la base de datos SQL a la base de datos Neodatis
            try {
                crearUsuarios(odb);
            } catch (SQLException e) {
                System.err.println("Ha habido un error en la creación de usuarios");
            }

            //Intentamos pasar los préstamos de la base de datos SQL a la base de datos Neodatis
            try {
                crearPrestamos(odb);
            } catch (SQLException e) {
                System.err.println("Ha habido un error en la creación de préstamos");
            } catch (ParseException e) {
                System.err.println("Ha habido un error al convertir las fechas");
            }

            //Pasamos el objeto que referencia a la base de datos Neodatis al resto de clases para las operaciones CRUD
            CRUD.odb = odb;

        } catch (ClassNotFoundException e) {
            System.err.println("No se ha encontrado el driver mysql");
        } catch (SQLException e) {
            System.err.println("Ha habido un error en la conexión de la base de datos (" + e.getMessage() + ")");
        }
    }

    /**
     * Método para crear los libros obteniendo los datos de la base de datos MySQL
     *
     * @param odb (Objeto que referencia la base de datos Neodatis)
     * @throws SQLException (Excepción que ocurre en caso de que haya un error en la sentencia SQL)
     */
    private static void crearLibros(ODB odb) throws SQLException {
        //Consultamos todos los datos de la tabla libros de la base de datos a la que nos hemos conectado anteriormente
        ResultSet rs = consultarTablaEntera("LIBROS");

        //Declaramos las variables de los datos que vamos a recoger
        int codigoLibro, numPaginas, anyoEdicion;
        String nombreLibro, editorial, autor, genero, paisAutor;
        double precioLibro;

        //Leemos la tabla libros entera recogiendo los datos necesarios para crear los libros en la base de datos enviada como parámetro
        while (rs.next()) {
            codigoLibro = rs.getInt(1);
            nombreLibro = rs.getString(2);
            editorial = rs.getString(3);
            autor = rs.getString(4);
            genero = rs.getString(5);
            paisAutor = rs.getString(6);
            numPaginas = rs.getInt(7);
            anyoEdicion = rs.getInt(8);
            precioLibro = Double.parseDouble(rs.getString(9).replace(".", "").replace(",", "."));

            //Almacenamos el libro y hacemos un commit
            odb.store(new Libro(codigoLibro, nombreLibro, editorial, autor, genero, paisAutor, numPaginas, anyoEdicion, precioLibro));
            odb.commit();
        }
    }

    /**
     * Método para crear los usuarios obteniendo los datos de la base de datos MySQL
     *
     * @param odb (Objeto que referencia la base de datos Neodatis)
     * @throws SQLException (Excepción que ocurre en caso de que haya un error en la sentencia SQL)
     */
    private static void crearUsuarios(ODB odb) throws SQLException {
        //Consultamos todos los datos de la tabla usuarios de la base de datos a la que nos hemos conectado anteriormente
        ResultSet rs = consultarTablaEntera("USUARIO");

        //Declaramos las variables de los datos que vamos a recoger
        int codigoUsuario;
        String nombre, apellidos, dni, domicilio, poblacion, provincia, fechaNac;

        //Leemos la tabla libros entera recogiendo los datos necesarios para crear los usuarios en la base de datos enviada como parámetro
        while (rs.next()) {
            codigoUsuario = rs.getInt(1);
            nombre = rs.getString(2);
            apellidos = rs.getString(3);
            dni = rs.getString(4);
            domicilio = rs.getString(5);
            poblacion = rs.getString(6);
            provincia = rs.getString(7);
            fechaNac = rs.getString(8);

            //Almacenamos el usuario y hacemos un commit
            odb.store(new Usuario(codigoUsuario, nombre, apellidos, dni, domicilio, poblacion, provincia, fechaNac));
            odb.commit();
        }
    }

    /**
     * Método para crear los préstamos obteniendo los datos de la base de datos MySQL
     *
     * @param odb (Objeto que referencia la base de datos Neodatis)
     * @throws SQLException (Excepción que ocurre en caso de que haya un error en la sentencia SQL)
     */
    private static void crearPrestamos(ODB odb) throws SQLException, ParseException {
        //Consultamos todos los datos de la tabla préstamos de la base de datos a la que nos hemos conectado anteriormente
        ResultSet rs = consultarTablaEntera("PRESTAMOS");

        //Declaramos las variables de los datos que vamos a recoger
        int codigoPrestamo;
        Libro libro;
        Usuario usuario;
        Date fechaSalida, fechaMaxDevolucion, fechaDevolucion;

        IQuery query;


        //Leemos la tabla préstamos entera recogiendo los datos necesarios para crear los préstamos en la base de datos enviada como parámetro
        while (rs.next()) {
            codigoPrestamo = rs.getInt(1);

            query = new CriteriaQuery(Libro.class, Where.equal("codigoLibro", rs.getInt(2)));
            libro = (Libro) odb.getObjects(query).getFirst();

            query = new CriteriaQuery(Usuario.class, Where.equal("codigoUsuario", rs.getInt(3)));
            usuario = (Usuario) odb.getObjects(query).getFirst();

            //Transformamos las fechas de tipo String a tipo Date
            fechaSalida = formatter.parse((rs.getString(4)));
            fechaMaxDevolucion = formatter.parse((rs.getString(5)));
            fechaDevolucion = formatter.parse((rs.getString(6)));

            //Almacenamos el préstamo y hacemos un commit
            odb.store(new Prestamo(codigoPrestamo, libro, usuario, fechaSalida, fechaMaxDevolucion, fechaDevolucion));
            odb.commit();
        }
    }

    /**
     * Método para consultar todos los datos de una tabla enviada como parámetro
     *
     * @param tabla (Parámetro tipo cadena que define el nombre de la tabla de la que queremos consultar todos los datos)
     * @return (Devuelve un ResultSet con el contenido de toda la tabla enviada como parámetro)
     * @throws SQLException (Excepción que ocurrirá cuando haya un error en la sentencia SQL, probablemente porque no exista la tabla introducida como parámetro)
     */
    private static ResultSet consultarTablaEntera(String tabla) throws SQLException {
        Statement sentencia = conexion.createStatement();
        String sql = "SELECT * FROM " + tabla.toUpperCase();
        return sentencia.executeQuery(sql);
    }

    /**
     * Método para leer una fecha por teclado y que cumpla el formato indicado
     *
     * @return (Devuelve la fecha en tipo String para poder almacenarla en la base de datos)
     */
    public static Date solicitarFecha(String msg) {
        Scanner teclado = new Scanner(System.in); //Escáner para leer por teclado la fecha

        //Declaramos las variables que vamos a usar
        Date fecha = null;
        boolean sigue = true;

        while (sigue) {
            try {
                System.out.println(msg);

                String fechaTeclado = teclado.nextLine();
                fecha = formatter.parse(fechaTeclado);

                sigue = false;
            } catch (ParseException e) {
                System.err.println("La fecha introducida es incorrecta, debe cumplir el formato (" + formato + ")");
            }
        }
        return fecha;
    }
}
