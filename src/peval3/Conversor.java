package peval3;

import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Conversor {
    private static Connection conexion;

    private static final String formato = "dd/MM/yyyy";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formato);

    public static void convertirSQLtoODB(String bdNombre, String usuario, String password, ODB odb) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("No se ha encontrado el driver mysql");
        }
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + bdNombre, usuario, password);
        } catch (SQLException e) {
            System.err.println("Ha habido un error en la conexión de la base de datos (" + e.getMessage() + ")");
        }

        try {
            crearLibros(odb);
        } catch (SQLException e) {
            System.err.println("Ha habido un error en la creación de libros");
        }
        try {
            crearUsuarios(odb);
        } catch (SQLException e) {
            System.err.println("Ha habido un error en la creación de usuarios");
        }
        try {
            crearPrestamos(odb);
        } catch (SQLException e) {
            System.err.println("Ha habido un error en la creación de prestamos (" + e.getMessage() + ")");
        }
    }

    private static void crearLibros(ODB odb) throws SQLException {

        Statement sentencia = conexion.createStatement();
        String sql = "SELECT * FROM LIBROS";
        ResultSet rs = sentencia.executeQuery(sql);

        int codigoLibro, numPaginas, anyoEdicion;
        String nombreLibro, editorial, autor, genero, paisAutor, precioLibro;

        while (rs.next()) {
            codigoLibro = rs.getInt(1);
            nombreLibro = rs.getString(2);
            editorial = rs.getString(3);
            autor = rs.getString(4);
            genero = rs.getString(5);
            paisAutor = rs.getString(6);
            numPaginas = rs.getInt(7);
            anyoEdicion = rs.getInt(8);
            precioLibro = rs.getString(9);

            odb.store(new Libro(codigoLibro, nombreLibro, editorial, autor, genero, paisAutor, numPaginas, anyoEdicion, precioLibro));
            odb.commit();
        }
    }

    private static void crearUsuarios(ODB odb) throws SQLException {
        Statement sentencia = conexion.createStatement();
        String sql = "SELECT * FROM USUARIO";
        ResultSet rs = sentencia.executeQuery(sql);

        int codigoUsuario;
        String nombre, apellidos, dni, domicilio, poblacion, provincia, fechaNac;

        while (rs.next()) {
            codigoUsuario = rs.getInt(1);
            nombre = rs.getString(2);
            apellidos = rs.getString(3);
            dni = rs.getString(4);
            domicilio = rs.getString(5);
            poblacion = rs.getString(6);
            provincia = rs.getString(7);
            fechaNac = rs.getString(8);

            odb.store(new Usuario(codigoUsuario, nombre, apellidos, dni, domicilio, poblacion, provincia, fechaNac));
            odb.commit();
        }
    }

    private static void crearPrestamos(ODB odb) throws SQLException {
        Statement sentencia = conexion.createStatement();
        String sql = "SELECT * FROM PRESTAMOS";
        ResultSet rs = sentencia.executeQuery(sql);

        int codigoPrestamo;
        Libro libro;
        Usuario usuario;
        String fechaSalida, fechaMaxDevolucion, fechaDevolucion;

        IQuery query;


        while (rs.next()) {
            codigoPrestamo = rs.getInt(1);

            query = new CriteriaQuery(Libro.class, Where.equal("codigoLibro", rs.getInt(2)));
            libro = (Libro) odb.getObjects(query).getFirst();

            query = new CriteriaQuery(Usuario.class, Where.equal("codigoUsuario", rs.getInt(3)));
            usuario = (Usuario) odb.getObjects(query).getFirst();

            fechaSalida = rs.getString(4);
            fechaMaxDevolucion = rs.getString(5);
            fechaDevolucion = rs.getString(6);

            odb.store(new Prestamo(codigoPrestamo, libro, usuario, fechaSalida, fechaMaxDevolucion, fechaDevolucion));
            odb.commit();
        }
    }

    public static String obtenerFechaString() {
        Scanner teclado = new Scanner(System.in);
        LocalDate fecha = null;
        boolean sigue = true;
        while (sigue) {
            try {
                String fechaTeclado = teclado.nextLine();
                fecha = LocalDate.parse(fechaTeclado, formatter);
                sigue = false;
            } catch (DateTimeParseException e) {
                System.err.println("La fecha introducida es incorrecta, debe cumplir el formato (" + formato + ")");
            }
        }
        return fecha.format(formatter);
    }

    public static LocalDate obtenerFechaLocalDate(String fechaString) {
        return LocalDate.parse(fechaString, formatter);
    }
}
