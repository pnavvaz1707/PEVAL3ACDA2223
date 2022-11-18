package peval3;

import org.neodatis.odb.*;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.And;
import org.neodatis.odb.core.query.criteria.ICriterion;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;
import org.neodatis.odb.impl.core.query.values.ValuesCriteriaQuery;

import java.util.Date;

public class Main {

    /**
     * Lista de opciones del menú del programa
     */
    private static final String[] MENU_OPCIONES = {
            "Pasar base de datos de SQL a Neodatis",
            "Registrar un libro",
            "Eliminar un usuario",
            "Modificar un préstamo",
            "Ver los préstamos entregados tarde por un usuario específico",
            "Ver los libros de un género y precio determinados",
            "Ver los préstamos realizados en una provincia y período de tiempo determinados",
            "Salir"
    };

    /**
     * Objeto ODB para referenciar la base de datos Neodatis
     */
    private static final ODB odb = ODBFactory.open(Datos.RUTA_BIBLIOTECA_NEODATIS);

    public static void main(String[] args) {
        int respuesta;
        do {
            Auxiliar.crearMenu(MENU_OPCIONES);
            respuesta = Auxiliar.solicitarEnteroEnUnRango(1, MENU_OPCIONES.length, "Seleccione una opción");

            System.out.println("Has seleccionado --> " + MENU_OPCIONES[respuesta - 1]);

            //Le pasamos la base de datos Neodatis a la clase en la que realizaremos las operaciones CRUD por si el usuario no selecciona la opción uno
            CRUD.odb = odb;

            switch (respuesta) {
                case 1 -> Conversor.convertirSQLtoODB("biblioteca", "root", "", odb);

                case 2 -> {
                    CRUD.registrarLibro();
                    odb.commit();
                }

                case 3 -> {
                    CRUD.mostrarUsuarios(odb.getObjects(Usuario.class));
                    try {
                        Usuario usuario = CRUD.obtenerUsuario("Introduce el código del usuario que desees borrar");

                        CRUD.borrarUsuario(usuario);
                    } catch (IndexOutOfBoundsException e) {
                        System.err.println("No existe un usuario con ese código");
                    }
                }

                case 4 -> {
                    CRUD.mostrarPrestamos(odb.getObjects(Prestamo.class));
                    CRUD.modificarPrestamo();
                }

                case 5 -> {
                    CRUD.mostrarUsuarios(odb.getObjects(Usuario.class));
                    Usuario usuario = CRUD.obtenerUsuario("Selecciona el código de usuario del cual desea consultar sus préstamos tardíos");

                    CRUD.obtenerPrestamosTardios(usuario);
                }

                case 6 -> CRUD.consultarPorGenerosYPrecio();

                case 7 -> CRUD.consultarPrestamosPorProvinciaYFechas();
            }
        } while (respuesta != MENU_OPCIONES.length);
    }
}