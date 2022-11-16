package peval3;

import org.neodatis.odb.*;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.IValuesQuery;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;
import org.neodatis.odb.impl.core.query.values.ValuesCriteriaQuery;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {

    static String[] MENU_OPCIONES = {
            "Pasar base de datos de SQL a Neodatis",
            "Registrar un libro",
            "Eliminar un usuario",
            "Modificar un préstamo",
            "Ver los préstamos entregados tarde por un usuario específico",
            "Ver los libros de un género y precio determinados",
            "Ver los préstamos realizados en una provincia y período de tiempo determinados",
            "Salir"
    };

    static Scanner teclado = new Scanner(System.in);

    static ODB odb = ODBFactory.open(Datos.RUTA_BIBLIOTECA_NEODATIS);

    public static void main(String[] args) {
        int respuesta;
        do {
            Utilidades.crearMenu(MENU_OPCIONES);
            respuesta = Utilidades.solicitarEnteroEnUnRango(1, MENU_OPCIONES.length, "Seleccione una opción");
            System.out.println("Has seleccionado --> " + MENU_OPCIONES[respuesta - 1]);
            switch (respuesta) {
                case 1:
                    Conversor.convertirSQLtoODB("biblioteca", "root", "", odb);
                    break;
                case 2:
                    registrarLibro();
                    break;
                case 3:
                    mostrarUsuarios();

                    try {
                        System.out.println("Introduce el código del usuario que desees borrar");
                        Usuario usuario = obtenerUsuario();

                        System.out.println("Teclee 1 si desea borrar a " + usuario.getNombre() + " con DNI: " + usuario.getDni());
                        int respuestaBorrado = teclado.nextInt();

                        if (respuestaBorrado == 1) {

                            IQuery queryPrestamosUsuario = odb.criteriaQuery(Prestamo.class, Where.equal("usuario", usuario));

                            Objects<Prestamo> prestamosABorrar = odb.getObjects(queryPrestamosUsuario);

                            for (Prestamo prestamo : prestamosABorrar) {
                                System.out.println(prestamo);
                                System.out.println("//////////////////////////////////////////////////////////////////////////////");
                                odb.delete(prestamo);
                            }
                            odb.delete(usuario);
                            odb.commit();

                            System.out.println("Se ha borrado el usuario y los préstamos relacionados con él");
                        } else {
                            System.out.println("El usuario no se ha borrado");
                        }
                    } catch (IndexOutOfBoundsException e) {
                        System.err.println("No existe un usuario con ese código");
                    }
                    break;
                case 4:
                    mostrarPrestamos();

                    System.out.println("Introduce el código del préstamo que desees modificar");
                    int codigoPrestamoSel = teclado.nextInt();

                    IQuery queryPrestamoSel = new CriteriaQuery(Prestamo.class, Where.equal("codigoPrestamo", codigoPrestamoSel));

                    try {
                        Prestamo prestamo = (Prestamo) odb.getObjects(queryPrestamoSel).getFirst();

                        System.out.println("Este es el préstamo seleccionado: ");
                        System.out.println(prestamo);

                        System.out.println("Teclee 1 si seguro desea modificar el usuario de este préstamo");
                        int respuestaModificacion = teclado.nextInt();

                        if (respuestaModificacion == 1) {
                            mostrarUsuarios();
                            System.out.println("Selecciona el código de usuario al que desea asignarle este préstamo");
                            try {
                                Usuario usuario = obtenerUsuario();
                                prestamo.setUsuario(usuario);
                                odb.delete(prestamo);
                                odb.store(prestamo);
                                odb.commit();
                            } catch (IndexOutOfBoundsException e) {
                                System.err.println("El usuario seleccionado no existe");
                            }

                        } else {
                            System.out.println("El usuario no se ha borrado");
                        }
                    } catch (IndexOutOfBoundsException e) {
                        System.err.println("No existe un usuario con ese código");
                    }
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    break;
            }
        } while (respuesta != MENU_OPCIONES.length);
    }

    private static Usuario obtenerUsuario() {
        int codigoUsuarioSel = teclado.nextInt();

        IQuery queryUsuarioSel = new CriteriaQuery(Usuario.class, Where.equal("codigoUsuario", codigoUsuarioSel));
        Usuario usuario = (Usuario) odb.getObjects(queryUsuarioSel).getFirst();
        return usuario;
    }

    private static void mostrarUsuarios() {
        Objects<Usuario> usuarios = odb.getObjects(Usuario.class);
        Usuario usuario;

        int contador = 0;
        boolean sigue = true;

        while (usuarios.hasNext() && sigue) {
            usuario = usuarios.next();

            System.out.println(usuario);
            System.out.println("////////////////////////////////////////////////////////////////////////////////");
            contador++;

            if (contador == 10) {
                sigue = Utilidades.sigPagina();
                contador = 0;
            }
        }
    }

    private static void mostrarPrestamos() {
        Objects<Prestamo> prestamos = odb.getObjects(Prestamo.class);
        Prestamo prestamo;

        int contador = 0;
        boolean sigue = true;

        while (prestamos.hasNext() && sigue) {
            prestamo = prestamos.next();

            System.out.println(prestamo);
            System.out.println("////////////////////////////////////////////////////////////////////////////////");
            contador++;

            if (contador == 10) {
                sigue = Utilidades.sigPagina();
                contador = 0;
            }
        }
    }

    private static void registrarLibro() {
        int codigoMax = Integer.parseInt(odb.getValues(new ValuesCriteriaQuery(Libro.class).max("codigoLibro")).getFirst().getByAlias("codigoLibro").toString());

        String nombreLibro, editorial, autor, genero, paisAutor, precioLibro;
        int numPaginas, anyoEdicion;

        //Bucle para que no se repita el libro introducido
        do {
            nombreLibro = Utilidades.leerCadena("Introduce el nombre del libro");
            editorial = Utilidades.leerCadena("Introduce la editorial del libro");
        } while (comprobarLibroDuplicado(nombreLibro, editorial));

        autor = Utilidades.leerCadena("Introduce el autor del libro");
        genero = Utilidades.leerCadena("Introduce el género del libro");
        paisAutor = Utilidades.leerCadena("Introduce el país del autor del libro");
        numPaginas = Utilidades.solicitarEnteroEnUnRango(1, 3500, "Introduce el número de páginas del libro: ");
        anyoEdicion = Utilidades.solicitarEnteroEnUnRango(1000, LocalDateTime.now().getYear(), "Introduce el año de edición del libro");
        precioLibro = String.valueOf(Utilidades.solicitarDoubleEnUnRango(0, Double.MAX_VALUE, "Introduce el precio del libro"));

        System.out.println("-------------- El libro se ha registrado --------------");
        odb.store(new Libro(codigoMax, nombreLibro, editorial, autor, genero, paisAutor, numPaginas, anyoEdicion, precioLibro));
        odb.commit();
    }

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
}