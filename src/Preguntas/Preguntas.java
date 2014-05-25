/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Preguntas;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;
import java.io.*;

/**
 *
 * @author Victor
 */
public class Preguntas {

    private static Preguntas v;
    private String pregunta;
    private String respuesta1;
    private String respuesta2;
    private String respuesta3;
    private String respuesta4;
    private int solucion;
    private int num_interno;
    private static ObjectContainer bdp;
    public static int numpreg;

    public Preguntas(String p, String r1, String r2, String r3, String r4, int s, int n) {
        this.pregunta = p;
        this.respuesta1 = r1;
        this.respuesta2 = r2;
        this.respuesta3 = r3;
        this.respuesta4 = r4;
        this.solucion = s;
        this.num_interno = n;
    }

    public void setpregunta(String n) {
        this.pregunta = n;
    }

    public String getpregunta() {
        return this.pregunta;
    }

    public void setrespuesta1(String n) {
        this.respuesta1 = n;
    }

    public String getrespuesta1() {
        return this.respuesta1;
    }

    public void setrespuesta2(String n) {
        this.respuesta2 = n;
    }

    public String getrespuesta2() {
        return this.respuesta2;
    }

    public void setrespuesta3(String n) {
        this.respuesta3 = n;
    }

    public String getrespuesta3() {
        return this.respuesta3;
    }

    public void setrespuesta4(String n) {
        this.respuesta4 = n;
    }

    public String getrespuesta4() {
        return this.respuesta4;
    }

    public void setsolucion(int n) {
        this.solucion = n;
    }

    public int getsolucion() {
        return this.solucion;
    }

    public void setnum_interno(int n) {
        this.num_interno = n;
    }

    public int getnum_interno() {
        return this.num_interno;
    }

    public String toString() {
        String cadena = this.pregunta + "\r\n";
        boolean salir;
        boolean unavez = true;
        int k;
        int[] respuestas = new int[4];
        String letra = "";
        for (int i = 0; i < 4; i++) {
            //int r = Metodos.Random(4);
            int r = i;
            do {
                k = 0;
                salir = true;
                while (k < respuestas.length) {
                    if (respuestas[k] == r) {
                        if (r < 4) {
                            r++;
                        } else {
                            r = 1;
                        }
                        salir = false;
                    }
                    k++;
                }
            } while (!salir);
            respuestas[i] = r;
            if (r == this.solucion && unavez) {
                ObjectSet res = bdp.queryByExample(new Preguntas(null, null, null, null, null, 0, this.num_interno));
                Preguntas p = (Preguntas) res.next();
                p.setsolucion(i + 1);
                bdp.store(p);
                unavez = false;
            }
            switch (i) {
                case 0:
                    letra = "a";
                    break;
                case 1:
                    letra = "b";
                    break;
                case 2:
                    letra = "c";
                    break;
                case 3:
                    letra = "d";
                    break;
                default:
                    break;
            }
            cadena += letra + ") ";
            switch (r) {
                case 1:
                    cadena += this.respuesta1;
                    break;
                case 2:
                    cadena += this.respuesta2;
                    break;
                case 3:
                    cadena += this.respuesta3;
                    break;
                case 4:
                    cadena += this.respuesta4;
                    break;
            }
            if (i != 3) {
                cadena += "\r\n";
            }
        }
        return cadena;
    }

    public static Preguntas MostrarResultado(ObjectSet res) {
        while (res.hasNext()) {
            v = (Preguntas) res.next();
            System.out.println(v);
        }
        return v;
    }

    public static void MostrarTodo(ObjectContainer bd) {
        Preguntas p = new Preguntas(null, null, null, null, null, 0, 0);
        ObjectSet res = bd.queryByExample(p);
        MostrarResultado(res);
    }

    public static void BorrarTodo(ObjectContainer bd) {
        ObjectSet res = bd.queryByExample(new Preguntas(null, null, null, null, null, 0, 0));
        Preguntas p;
        while (res.hasNext()) {
            p = (Preguntas) res.next();
            bd.delete(p);
        }
    }

    public static Preguntas consultaPreguntas(ObjectContainer bd, int num) {
        bdp = bd;
        Query query = bd.query();
        query.constrain(Preguntas.class);
        query.descend("num_interno").constrain(num);
        ObjectSet result = query.execute();
        v = MostrarResultado(result);
        return v;
    }

    public static String[] NombreFicherosDirectorio(String directorio) {
        File direct = new File(directorio);
        String[] lista = direct.list();
        return lista;
    }

    public static int NumeroRegistros(String fichero) {
        String linealeida;
        int numeroLineas = 0;
        try {
            BufferedReader lectura = new BufferedReader(new FileReader(fichero));
            while ((linealeida = lectura.readLine()) != null) {
                numeroLineas++;
            }
            lectura.close();
        } catch (IOException iox) {
            System.out.println("Error: " + iox);
        }
        return numeroLineas;
    }

    public static String[] MeterFicheroEnMatriz(String fichero) throws FileNotFoundException, IOException {
        String linea;
        int i = 0;
        String[] Matriz = new String[NumeroRegistros(fichero)];
        BufferedReader lectura = new BufferedReader(new FileReader(fichero));
        while ((linea = lectura.readLine()) != null) {
            Matriz[i] = linea;
            i++;
        }
        lectura.close();
        return Matriz;
    }

    public static void IntroducirDatos(ObjectContainer bd) throws FileNotFoundException, IOException, Exception {
        String[] lista = NombreFicherosDirectorio("Preguntas");
        Preguntas pregunta;
        int l = 1;
        int lon;
        double calculo;
        int solucion;
        numpreg = 0;
        for (int i = 0; i < lista.length; i++) {
            String[] Pregs = MeterFicheroEnMatriz("Preguntas/" + lista[i]);
            int j = 0;
            calculo = ((double) Pregs.length + 1) / 7;
            if (calculo % 1 != 0) {
                String ex = "El fichero <" + lista[i] + "> está mal formado, por favor revíselo antes de ejecutar de nuevo el programa.";
                throw new Exception(ex);
            }

            lon = (Pregs.length + 1) / 7;

            numpreg += lon;
            for (int k = 0; k < lon; k++) {
                solucion = Integer.parseInt(Pregs[j + 5]);
                if (solucion < 1 || solucion > 4) {
                    String ex = "El fichero <" + lista[i] + "> tiene una solución imposible en la pregunta " + (k + 1) + ", "
                            + "por favor soluciónelo antes de ejecutar de nuevo el programa.";
                    throw new Exception(ex);
                }
                pregunta = new Preguntas(Pregs[j], Pregs[j + 1], Pregs[j + 2], Pregs[j + 3], Pregs[j + 4], Integer.parseInt(Pregs[j + 5]), l);
                j += 7;
                l++;
                bd.store(pregunta);
            }
        }
    }
}
