package peval3;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Utilidades {

    public static void crearMenu(String[] MENU_OPCIONES) {
        for (int i = 0; i < MENU_OPCIONES.length; i++) {
            System.out.println((i + 1) + ". " + MENU_OPCIONES[i]);
        }
    }

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

    public static boolean sigPagina() {
        Scanner teclado = new Scanner(System.in);
        boolean sigue = false;
        boolean sigPagina = false;
        do {
            System.out.println("¿Desea ver los siguientes 10 números?");
            String respuesta = teclado.nextLine();
            if (respuesta.toLowerCase().equals("si")) {
                sigPagina = true;
                sigue = false;
            } else if (respuesta.toLowerCase().equals("no")) {
                sigue = false;
            } else {
                System.err.println("Debe introducir la palabra 'si' o 'no'");
                sigue = true;
            }
        } while (sigue);
        return sigPagina;
    }
}
