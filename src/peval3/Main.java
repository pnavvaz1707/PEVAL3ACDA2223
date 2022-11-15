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
                    int codigoMax = Integer.parseInt(odb.getValues(new ValuesCriteriaQuery(Libro.class).max("codigoLibro")).getFirst().getByAlias("codigoLibro").toString());
                    String nombreLibro = Utilidades.leerCadena("Introduce el nombre del libro");
                    String editorial = Utilidades.leerCadena("Introduce la editorial del libro");
                    String autor = Utilidades.leerCadena("Introduce el autor del libro");
                    String genero = Utilidades.leerCadena("Introduce el género del libro");
                    String paisAutor = Utilidades.leerCadena("Introduce el país del autor del libro");
                    int numPaginas = Utilidades.solicitarEnteroEnUnRango(1, 3500, "Introduce el número de páginas del libro: ");
                    int anyoEdicion = Utilidades.solicitarEnteroEnUnRango(868, LocalDateTime.now().getYear(), "Introduce el año de edición del libro");
                    String precioLibro = Utilidades.leerCadena("Introduce el precio del libro");
                    break;
                case 3:
                    Objects<Usuario> usuarios = odb.getObjects(Usuario.class);
                    int contador = 0;
                    Usuario usuario;
                    boolean sigue = true;
                    while (usuarios.hasNext() && sigue) {
                        usuario = usuarios.next();
                        System.out.println("Código de usuario: " + usuario.getCodigoUsuario());
                        System.out.println("Nombre: " + usuario.getNombre());
                        System.out.println("Apellidos: " + usuario.getApellidos());
                        System.out.println("DNI: " + usuario.getDni());
                        System.out.println("Domicilio: " + usuario.getDomicilio());
                        System.out.println("Población: " + usuario.getPoblacion());
                        System.out.println("Provincia: " + usuario.getProvincia());
                        System.out.println("Fecha de nacimiento (DD/MM/YYYY): " + usuario.getFechaNac());
                        System.out.println("////////////////////////////////////////////////////////////////////////////////");
                        contador++;
                        if (contador == 10) {
                            sigue = sigPagina();
                            contador = 0;
                        }
                    }
                    System.out.println("Introduce el código del usuario que desees borrar");
                    int codigoSeleccionado = teclado.nextInt();
                    IQuery query = new CriteriaQuery(Usuario.class, Where.equal("codigoUsuario", codigoSeleccionado));
                    usuario = (Usuario) odb.getObjects(query).getFirst();
                    System.out.println("Código del usuario seleccionado: " + usuario.getCodigoUsuario());
                    System.out.println("Nombre del usuario seleccionado: " + usuario.getNombre());

                    break;
                case 4:
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

    public static boolean sigPagina() {
        boolean sigue = false;
        boolean sigPagina = false;
        do {
            System.out.println("¿Desea ver los siguientes 10 números?");
            String respuesta = teclado.nextLine();
            if (respuesta.toLowerCase().equals("si")) {
                sigPagina = true;
                sigue = false;
            } else if (respuesta.toLowerCase().equals("no")) {
                sigPagina = false;
                sigue = false;
            } else {
                System.err.println("Debe introducir la palabra 'si' o 'no'");
                sigue = true;
            }
        } while (sigue);
        return sigPagina;
    }
}