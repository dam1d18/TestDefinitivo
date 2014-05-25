/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Preguntas;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Víctor Jurado Usón
 *
 * El programa además de dejar elegir al usuario el número de preguntas que
 * desea realizar, genera las preguntas aleatoriamente y sin repetición y cambia
 * el orden aleatoriamente de las respuestas de cada pregunta. También cuenta
 * con un cronómetro el tiempo en realizar el test, entre demás osrpresas.
 */
public class Preguntas_Main {

    public static void IniciarTemporizador() {
        TimerTask timerTask = new TimerTask() {

            public void run() {
                cont += 0.01;
            }
        };
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, 0, 10);
    }

    public static void Conectar(String database) {
        bd = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), database);
    }
    static ObjectContainer bd;
    static int eleccion;
    static String eleccioncadena;
    static int aciertos = 0;
    static int nsnc = 0;
    static int i;
    static double cont = 0;

    public static void main(String[] args) throws FileNotFoundException, IOException, Exception {
        Conectar("preguntas_v2.db4o");
        Preguntas.BorrarTodo(bd);
        Preguntas.IntroducirDatos(bd);
        System.out.println("*********************************************************");
        System.out.println("            BIENVENIDO AL TEST DEFINITIVO");
        System.out.println("*********************************************************");
        System.out.println("");
        String[] lista = Preguntas.NombreFicherosDirectorio("Preguntas");
        int totalpreguntas = Preguntas.numpreg;
        int num_preg = ValidarOpcion.ValidarEleccionEntero("Elija de cuantas preguntas quiere el examen: (1-" + totalpreguntas + "): ", 1, totalpreguntas);
        System.out.println("El examen constará de " + num_preg + " preguntas.");
        System.out.println("");
        double resta_fallo = (10 / (double) num_preg) * 0.3333333334;
        double suma_acierto = 10 / (double) num_preg;
        System.out.println("Cada acierto valdrá " + Metodos.Redondear(suma_acierto, 3) + " puntos.");
        System.out.println("Cada fallo restará " + Metodos.Redondear(resta_fallo, 3) + " sobre la puntuación obtenida de los aciertos.");
        System.out.println("Lee con atención y suerte.");
        System.out.println("");
        int[] numeros = new int[num_preg];
        int[] elecciones = new int[5];
        String[] letras = Metodos.EleccionPregunta();
        int j;
        boolean salir;
        int k;
        Preguntas v;
        double tempinicio;
        double tempfinal;
        double[] tiempos = new double[num_preg];
        IniciarTemporizador();
        for (i = 0; i < num_preg; i++) {
            tempinicio = cont;
            System.out.println("Pregunta " + (i + 1));
            j = Metodos.Random(totalpreguntas);
            do {
                k = 0;
                salir = true;
                while (k < numeros.length) {
                    if (numeros[k] == j) {
                        if (j < totalpreguntas) {
                            j++;
                        } else {
                            j = 1;
                        }
                        salir = false;
                    }
                    k++;
                }
            } while (!salir);
            numeros[i] = j;
            v = Preguntas.consultaPreguntas(bd, j);
            System.out.println("n) NS/NC");
            eleccioncadena = ValidarOpcion.ValidarEleccionCadenas("Elija una opción: ", letras);
            eleccion = Metodos.ConvertirLetraNumero(eleccioncadena);
            elecciones[eleccion - 1]++;
            tempfinal = cont;
            tiempos[i] = tempfinal - tempinicio;
            if (eleccion != 5) {
                if (v.getsolucion() == eleccion) {
                    aciertos++;
                }
            } else {
                nsnc++;
            }
              
            switch(v.getsolucion()){
 case 1: 
     System.out.println("La respuesta era la A");
     break;
 case 2: 
     System.out.println("La respuesta era la B");
     break;
 case 3: 
    System.out.println("La respuesta era la C");
     break;
 case 4: 
    System.out.println("La respuesta era la D");
     break;
            
            }
            System.out.println("");
        }
        bd.close();
        System.out.println("********************");
        System.out.println("RESULTADO DEL EXAMEN");
        System.out.println("********************");
        System.out.println("Cada acierto vale " + Metodos.Redondear(suma_acierto, 3) + " puntos.");
        System.out.println("Cada fallo resta " + Metodos.Redondear(resta_fallo, 3) + " sobre la puntuación obtenida de los aciertos.");
        System.out.println("Aciertos: " + aciertos);
        int fallos = num_preg - aciertos - nsnc;
        System.out.println("Fallos: " + (fallos));
        double fallos2 = ((10 / (double) num_preg) * 0.3333333334) * (double) fallos;
        double puntosacierto = ((double) aciertos / num_preg) * 10;
        double calif = puntosacierto - (fallos2);
        System.out.println("NS/NC: " + nsnc);
        if (calif >= 5) {
            System.out.println("Has aprobado el examen.");
        } else {
            System.out.println("Has suspendido el examen.");
        }
        System.out.println("Calificación: " + Metodos.ComprobarNota(calif) + ", " + Metodos.Redondear(calif, 2));
        System.out.println("******************");
        System.out.println("Datos de interés: ");
        System.out.println("******************");
        System.out.println("Has tardado " + Metodos.Redondear(cont, 2) + " segundos en completar el test.");
        double tiempomedio = cont / (double) num_preg;
        System.out.println("El tiempo medio de respuesta ha sido de: " + Metodos.Redondear(tiempomedio, 2) + " segundos.");
        System.out.println("La respuesta más rápida ha sido de: " + Metodos.Redondear(Metodos.Minimo(tiempos), 2) + " segundos.");
        System.out.println("La respuesta más lenta ha sido de: " + Metodos.Redondear(Metodos.Maximo(tiempos), 2) + " segundos.");
        System.out.println("Porcentaje de opciones elegidas:");
        double porcentaje = ((double) elecciones[0] / (double) num_preg) * 100;
        System.out.println("a): " + Metodos.Redondear(porcentaje, 2) + "%");
        porcentaje = ((double) elecciones[1] / (double) num_preg) * 100;
        System.out.println("b): " + Metodos.Redondear(porcentaje, 2) + "%");
        porcentaje = ((double) elecciones[2] / (double) num_preg) * 100;
        System.out.println("c): " + Metodos.Redondear(porcentaje, 2) + "%");
        porcentaje = ((double) elecciones[3] / (double) num_preg) * 100;
        System.out.println("d): " + Metodos.Redondear(porcentaje, 2) + "%");
        porcentaje = ((double) elecciones[4] / (double) num_preg) * 100;
        System.out.println("NS/NC: " + Metodos.Redondear(porcentaje, 2) + "%");
        System.exit(0);
    }
}
