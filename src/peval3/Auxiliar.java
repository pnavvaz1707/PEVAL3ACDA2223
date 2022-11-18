package peval3;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Clase donde almaceno métodos útiles para la aplicación entero
 */
public class Auxiliar {

    /**
     * Método para crear el menú según las opciones pasadas por parámetro
     *
     * @param MENU_OPCIONES (Lista de opciones a imprimir como menú)
     */
    public static void crearMenu(String[] MENU_OPCIONES) {
        for (int i = 0; i < MENU_OPCIONES.length; i++) {
            System.out.println((i + 1) + ". " + MENU_OPCIONES[i]);
        }
    }

    /**
     * Método para solicitar al usuario un número entero en un rango determinado
     *
     * @param limiteInferior (Límite inferior del rango que debe cumplir el número solicitado al usuario)
     * @param limiteSuperior (Límite superior del rango que debe cumplir el número solicitado al usuario)
     * @param msg            (Pregunta que le realizará al programa al usuario para pedirle el número)
     * @return (Devuelve el número introducido por el usuario)
     */
    public static int solicitarEnteroEnUnRango(int limiteInferior, int limiteSuperior, String msg) {
        Scanner teclado = new Scanner(System.in);
        boolean sigue = true;
        int num = 0;

        while (sigue) {
            try {
                System.out.println(msg);

                num = teclado.nextInt();
                if (num < limiteInferior || num > limiteSuperior) {
                    throw new Exception("El número debe estar comprendido en el siguiente rango [" + limiteInferior + "," + limiteSuperior + "]");
                }
                sigue = false;

            } catch (InputMismatchException e) {
                System.err.println("Debe introducir un número entero");
                teclado.nextLine();

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return num;
    }

    /**
     * Método para solicitar al usuario una cadena de texto
     *
     * @param msg (Pregunta que le realizará al programa al usuario para pedirle la cadena de texto)
     * @return (Devuelve la cadena de texto introducida por el usuario)
     */
    public static String leerCadena(String msg) {
        Scanner teclado = new Scanner(System.in);
        String respuesta = "";
        boolean sigue = true;

        while (sigue) {
            try {
                System.out.println(msg);
                respuesta = teclado.nextLine();

                if (respuesta.equals("")) {
                    throw new Exception("No puedes dejar el campo vacío");
                }
                sigue = false;

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return respuesta;
    }

    /**
     * Método para solicitar al usuario un número tipo double en un rango determinado
     *
     * @param limiteInferior (Límite inferior del rango que debe cumplir el número solicitado al usuario)
     * @param limiteSuperior (Límite superior del rango que debe cumplir el número solicitado al usuario)
     * @param msg            (Pregunta que le realizará al programa al usuario para pedirle el número)
     * @return (Devuelve el número introducido por el usuario)
     */
    public static double solicitarDoubleEnUnRango(double limiteInferior, double limiteSuperior, String msg) {
        Scanner teclado = new Scanner(System.in);
        boolean sigue = true;
        double num = 0;

        while (sigue) {
            try {
                System.out.println(msg);

                num = teclado.nextDouble();
                if (num < limiteInferior || num > limiteSuperior) {
                    throw new Exception("El número debe estar comprendido en el siguiente rango [" + limiteInferior + "," + limiteSuperior + "]");
                }
                sigue = false;

            } catch (InputMismatchException e) {
                System.err.println("Debe introducir un número (si es decimal debe llevar el separador ',')");
                teclado.nextLine();

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return num;
    }

    /**
     * Método que pregunta al usuario si desea ver los siguientes 10 elementos
     *
     * @return (Devuelve un booleano de valor true si el usuario desea seguir o false si no lo desea)
     */
    public static boolean sigPagina() {
        Scanner teclado = new Scanner(System.in);
        boolean sigue = false;
        boolean sigPagina = false;
        do {
            System.out.println("¿Desea ver los siguientes 10 números?");
            String respuesta = teclado.nextLine().toLowerCase();
            if (respuesta.equals("si") || respuesta.equals("sí")) {
                sigPagina = true;
                sigue = false;
            } else if (respuesta.equals("no")) {
                sigue = false;
            } else {
                System.err.println("Debe introducir la palabra 'si' o 'no'");
                sigue = true;
            }
        } while (sigue);
        return sigPagina;
    }
}
