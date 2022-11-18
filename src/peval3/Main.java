package peval3;

import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
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
                    mostrarUsuarios(odb.getObjects(Usuario.class));

                    try {
                        Usuario usuario = obtenerUsuario("Introduce el código del usuario que desees borrar");

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
                    mostrarPrestamos(odb.getObjects(Prestamo.class));

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
                            try {
                                mostrarUsuarios(odb.getObjects(Usuario.class));
                                Usuario usuario = obtenerUsuario("Selecciona el código de usuario al que desea asignarle este préstamo");
                                prestamo.setUsuario(usuario);
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
                    mostrarUsuarios(odb.getObjects(Usuario.class));
                    Usuario usuario = obtenerUsuario("Selecciona el código de usuario del cual desea consultar sus préstamos tardíos");
                    IQuery queryPrestamosUsuario = new CriteriaQuery(Prestamo.class, Where.equal("usuario", usuario));

                    Objects<Prestamo> prestamosUsuario = odb.getObjects(queryPrestamosUsuario);

                    for (Prestamo prestamo : prestamosUsuario) {
                        IQuery queryPrestamosTardios = new CriteriaQuery(Prestamo.class, Where.gt("fechaDevolucion", prestamo.getFechaMaxDevolucion()));
                        Prestamo prestamo1 = (Prestamo) odb.getObjects(queryPrestamosTardios).getFirst();
                        System.err.println(prestamo1);
                    }
                    break;
                case 6:
                    String genero = Utilidades.leerCadena("Introduce el género");
                    double precio = Utilidades.solicitarDoubleEnUnRango(0, Double.MAX_VALUE, "Introduce el precio");

                    ICriterion criterionLibroPrecioGenero = new And().add(Where.equal("genero", genero)).add(Where.equal("precioLibro", precio));
                    IQuery query = new CriteriaQuery(Libro.class, criterionLibroPrecioGenero);

                    Objects<Libro> libros = odb.getObjects(query);

                    mostrarLibros(libros);
                    break;
                case 7:
                    String provincia = Utilidades.leerCadena("Introduce la provincia");
                    Date fechaInicio, fechaFinal;
                    do {
                        fechaInicio = Conversor.solicitarFecha("Introduce la primera fecha");
                        fechaFinal = Conversor.solicitarFecha("Introduce la segunda fecha");

                    } while (fechaInicio.after(fechaFinal));

                    ICriterion criterionPrestamosProvinciaPrecio = new And().add(Where.equal("usuario.provincia", provincia)).add(Where.gt("fechaSalida", fechaInicio)).add(Where.lt("fechaSalida", fechaFinal));
                    IQuery queryPrestamosProvinciaPrecio = odb.criteriaQuery(Prestamo.class, criterionPrestamosProvinciaPrecio);

                    Objects<Prestamo> prestamos = odb.getObjects(queryPrestamosProvinciaPrecio);

                    mostrarPrestamos(prestamos);
                    break;
            }
        } while (respuesta != MENU_OPCIONES.length);
    }

    private static Usuario obtenerUsuario(String texto) {
        System.out.println(texto);

        int codigoUsuarioSel = teclado.nextInt();

        IQuery queryUsuarioSel = new CriteriaQuery(Usuario.class, Where.equal("codigoUsuario", codigoUsuarioSel));
        return (Usuario) odb.getObjects(queryUsuarioSel).getFirst();
    }

    private static void mostrarLibros(Objects<Libro> libros) {
        Libro libro;

        int contador = 0;
        boolean sigue = true;

        while (libros.hasNext() && sigue) {
            libro = libros.next();

            System.out.println(libro);
            System.out.println("////////////////////////////////////////////////////////////////////////////////");
            contador++;

            if (contador == 10) {
                sigue = Utilidades.sigPagina();
                contador = 0;
            }
        }
    }

    private static void mostrarUsuarios(Objects<Usuario> usuarios) {
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

    private static void mostrarPrestamos(Objects<Prestamo> prestamos) {
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

        String nombreLibro, editorial, autor, genero, paisAutor;
        int numPaginas, anyoEdicion;
        double precioLibro;

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
        precioLibro = Utilidades.solicitarDoubleEnUnRango(0, Double.MAX_VALUE, "Introduce el precio del libro");

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