import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Para sincronizar hilos entre si, En el vd, anterior se consigue sincronizar
 * los hilos de forma manual, es decir, ordenamos que se ejecute un hilo hasta
 * su muerte y después que se ejecute el siguiente.
 *
 * Ocurre que habrá ocasiones en que mientras se esté ejecutando uno de los
 * hilos, tenga que haber otro que esté ejecutando su tarea a la vez. Tenemos
 * que sincronizar los hilos entre sí sin necesidad de utilizar el mt ‘join()’,
 * -- EJEMPLO; Cuando utilizamos una interface gráfica y está realizando tareas,
 * la interface gráfica debe quedar libre, (mientras realiza una tarea tienen
 * que poder manejarse los menús de la Inte_G).
 *
 * Una manera sencilla de hacerlo sería trabajar con dos hilos, y decirle a uno
 * de los hilos que espere a que el hilo que le pasemos por parámetro termine su
 * tarea.
 *
 * @author Usuario
 */
public class SincronizandoHilos2 {

    public static void main(String[] args) {

        HilosVarios hilo1 = new HilosVarios();
        HilosVarios2 hilo2 = new HilosVarios2(hilo1);
        hilo1.setName("Hilo_1");
        hilo2.setName("Hilo_2");
        hilo2.start();
        hilo1.start();

        System.out.println("Hola, qué hay, cómo estamos??");
    }
}

/**
 * CREA UN HILO DE EJECUCIÓN QUE IMPRIME EN PANTALLA UN MENSJE VARIAS VECES.
 */
class HilosVarios extends Thread {// ----------- ------------------------------------------- CLASE HilosVarios 

    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println("Ejecutando hilo " + getName());
            try {
                Thread.sleep(400);
            } catch (InterruptedException ex) {
                Logger.getLogger(HilosVarios.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

/**
 * EJECUTA 1º UN HILO QUE SE LE PASA COMO PARAMETRO EN EL CONSTRUCTOR, Y LUEGO
 * EJECUTA OTRO HILO QUE CREA LA CLASE MISMA.
 */
class HilosVarios2 extends Thread {// ----------- ------------------------------------------- CLASE HilosVarios2

    private Thread hilo;// --------------- almacena el Thread que le pasamos como parámetro.

    public HilosVarios2(Thread hilo) {
        this.hilo = hilo;
    }

    public void run() {
        try {
            hilo.join();// --------------- ejecución del hilo pasado en el constructor.
        } catch (InterruptedException ex) {
            Logger.getLogger(HilosVarios2.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (int i = 0; i < 5; i++) {
            System.out.println("Ejecutando hilo " + getName());
            try {
                Thread.sleep(400);
            } catch (InterruptedException ex) {
                Logger.getLogger(HilosVarios.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
