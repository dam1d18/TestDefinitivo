package Preguntas;

import java.util.Scanner;

/**
 * Clase que permite leer por teclado distintos tipos de datos.
 *
 * @author Víctor Jurado Usón
 * @version 1.00, 15/10/2013
 */
public class Leer {

    /**
     * Solo admite la introducción de números enteros por teclado.
     *
     * @return <b>int</b <i>n</i> - El número entero introducido.
     */
    public static int entero() {
        Scanner teclado = new Scanner(System.in);
        int n;
        n = teclado.nextInt();
        return n;
    }

    /**
     * Solo admite la introducción de números dobles por teclado.
     *
     * @return <b>double</b> <i>n</i> - El número doble introducido.
     */
    public static double doble() {
        Scanner teclado = new Scanner(System.in);
        double n;
        n = teclado.nextDouble();
        return n;
    }

    /**
     * <p align=justify>Admite la introducción de una cadena no espaciada, si
     * contiene espacios solo coge el primer conjunto de caracteres hasta el
     * primer espacio.</p>
     *
     * @return <b>String</b> <i>n</i> - La cadena introducida, una
     * <b>String</b>.
     */
    public static String cadena() {
        Scanner teclado = new Scanner(System.in);
        String n;
        n = teclado.next();
        return n;
    }

    /**
     * <p align=justify>Admite la introducción de una cadena de caracteres de
     * cualquier tipo.</p>
     *
     * @return <b>String</b> <i>n</i> - La cadena introducida, una
     * <b>String</b>.
     */
    public static String linea() {
        Scanner teclado = new Scanner(System.in);
        String n;
        n = teclado.nextLine();
        return n;
    }

    /**
     * <p align=justify>Admite una cadena de caracteres pero solo cortará el
     * primer caracter que encuentre.</p>
     *
     * @return <b>char</b> <i>n</i> - Un <b>char</b>, el primero de la cadena.
     */
    public static char caracter() {
        Scanner teclado = new Scanner(System.in);
        String m;
        char n;
        m = teclado.next();
        n = m.charAt(0);
        return n;
    }
}
