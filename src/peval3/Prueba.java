//package peval3;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.time.format.DateTimeParseException;
//import java.util.Date;
//import java.util.Scanner;
//
//public class Prueba {
//    private static final String formato = "dd/MM/yyyy";
//    private static final SimpleDateFormat formatter = new SimpleDateFormat(formato);
//
//    public static void main(String[] args) {
//        System.out.println(solicitarFecha("Introduce la fecha"));
//    }
//
//    public static Date solicitarFecha(String msg) {
//        Scanner teclado = new Scanner(System.in);
//        Date fecha = null;
//        boolean sigue = true;
//        while (sigue) {
//            try {
//                System.out.println(msg);
//                String fechaTeclado = teclado.nextLine();
//                fecha = formatter.parse(fechaTeclado);
//                sigue = false;
//            } catch (ParseException e) {
//                System.err.println("La fecha introducida es incorrecta, debe cumplir el formato (" + formato + ")");
//            }
//        }
//        return fecha;
//    }
//
//
//    public static String obtenerFechaString() {
//        Scanner teclado = new Scanner(System.in);
//        LocalDate fecha = null;
//        boolean sigue = true;
//        while (sigue) {
//            try {
//                String fechaTeclado = teclado.nextLine();
//                fecha = LocalDate.parse(fechaTeclado, formatter);
//                sigue = false;
//            } catch (DateTimeParseException e) {
//                System.err.println("La fecha introducida es incorrecta, debe cumplir el formato (" + formato + ")");
//            }
//        }
//        return fecha.format(formatter);
//    }
//
//    /**
//     * Método para transformar las fechas tipo String a LocalDate para comparar si se ha entregado tarde un préstamo
//     *
//     * @param fechaString (La fecha en formato String que se quiere pasar a LocalDate siguiendo el formato indicado)
//     * @return (Devuelve un objeto LocalDate que referencia la misma fecha que la introducida como parámetro)
//     */
//    public static LocalDate obtenerFechaLocalDate(String fechaString) {
//        return LocalDate.parse(fechaString, formatter);
//    }
//}
