package Preguntas;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Clase que realiza comprobaciones de enteros y dobles para evitar errores al
 * introducir datos por teclado u obligar al usuario a introducir el número y/o
 * cadena que se desee.
 *
 * @author Víctor Jurado Usón
 * @version 1.00, 10/02/2014
 */
public class ValidarOpcion {

    /**
     * Comprueba si es un entero parseándolo.
     *
     * @param num Recibe una <b>String</b>,para que devuelva true la cadena debe
     * ser un número entero o doble. Ej: 5 o 5.3
     * @return <b>boolean</b> - true o false.
     */
    public static boolean IsDouble(String num) {
        try {
            Double.parseDouble(num);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Comprueba si es un doble parseándolo.
     *
     * @param num Recibe una <b>String</b>,para que devuelva true la cadena debe
     * ser estrictamente un número entero. Ej: 5
     * @return <b>boolean</b> - true o false.
     */
    public static boolean IsInteger(String num) {
        try {
            Integer.parseInt(num);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * <p align=justify>Pide por teclado un entero hasta que este se comprenda
     * entre el rango establecido. Cabe destacar que si el primer parámetro no
     * es menor que el segundo el programa no dará errores pero jamás saldrá del
     * bucle.</p>
     *
     * @param mensaje Recibe una <b>String</b> que mostrará en cada vuelta del
     * bucle si el usuario no introduce la eleccion correcta.
     * @param inicio Recibe un <b>int</b> límite mínimo de la eleccion, incluído
     * el propio número entero.
     * @param fin Recibe un <b>int</b> límite máximo de la elección, incluído el
     * propio número entero.
     * @return <b>int</b> <i>eleccion</i> - Elección del usuario introducida por
     * teclado, debe ser un entero comprendido entre los dos números anteriores
     * obligatoriamente.
     */
    public static int ValidarEleccionEntero(String mensaje, int inicio, int fin) {
        int eleccion;
        String eleccionCadena;
        do {
            eleccion = Integer.MAX_VALUE;
            System.out.print(mensaje);
            eleccionCadena = Leer.cadena();
            if (IsInteger(eleccionCadena)) {
                eleccion = Integer.parseInt(eleccionCadena);
            }
        } while (eleccion < inicio || eleccion > fin);
        return eleccion;
    }

    /**
     * <p align=justify>Pide por teclado un doble hasta que este se comprenda
     * entre el rango establecido. Cabe destacar que si el primer parámetro no
     * es menor que el segundo el programa no dará errores pero jamás saldrá del
     * bucle.</p>
     *
     * @param mensaje Recibe una <b>String</b> que mostrará en cada vuelta del
     * bucle si el usuario no introduce la eleccion correcta.
     * @param inicio Recibe un <b>double</b> límite mínimo de la eleccion,
     * incluído este.
     * @param fin Recibe un <b>double</b> límite máximo de la elección, incluído
     * este.
     * @return <b>double</b> <i>eleccion</i> - Elección del usuario introducida
     * por teclado.
     */
    public static double ValidarEleccionDoble(String mensaje, double inicio, double fin) {
        double eleccion;
        String eleccionCadena;
        do {
            eleccion = Double.MAX_VALUE;
            System.out.print(mensaje);
            eleccionCadena = Leer.cadena();
            if (IsDouble(eleccionCadena)) {
                eleccion = Double.parseDouble(eleccionCadena);
            }
        } while (eleccion < inicio || eleccion > fin);
        return eleccion;
    }

    /**
     * <p align=justify>Pide por teclado una cadena hasta que esta coincide con
     * alguna de las que contenga el vector pasado como parámetro. Admite
     * mayúsculas y minúsculas tanto en el vector como en la elección puesto que
     * transforma todo a minúsculas.</p>
     *
     * @param mensaje Recibe una <b>String</b> que mostrará en cada vuelta del
     * bucle si el usuario no introduce la cadena correcta.
     * @param cadenas Recibe un vector <b>String[]</b> unidimensional con todas
     * las opciones que el usuario podrá teclear.
     * @return <b>String</b> <i>eleccion</i> - La <b>String</b> que el usuario a
     * introducido por teclado y que coincide con alguna de las que contiene el
     * vector pasado como parámetro.
     */
    public static String ValidarEleccionCadenas(String mensaje, String[] cadenas) {
        String eleccion = "";
        int i;
        boolean encontrado = false;
        while (encontrado == false) {
            i = 0;
            System.out.print(mensaje);
            eleccion = Leer.linea();
            eleccion = eleccion.toLowerCase();
            while (i < cadenas.length && encontrado == false) {
                if (eleccion.compareTo(cadenas[i].toLowerCase()) == 0) {
                    encontrado = true;
                }
                i++;
            }
        }
        return eleccion;
    }
}