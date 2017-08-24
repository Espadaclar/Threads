import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * --------------------------------SINCRONIZACIÓN DE THREADS. ---------------------------------
 * 
 * EJECUTANDO EL PROGRAMA SE VE QUE NO IMPRIME LOS HILOS EN EL ORDEN EN QUE ESTÁN COLOCADOS EN EL CÓDIGO.
 * (el orden de mostrar los hilos en pantalla depende del SO y de los recursos que esté utilizando el 
 * ordenador en ese momento.)
 * 
 * HABRA OCOASIONES EN QUE TENGAMOS QUE UTILIZAR UN HILO ANTES QUE se efecute OTRO, PARA ELLO SE 
 * NECESITA -------- SINCRONIZAR LOS HILOS. -------
 * 
 * -------------------- utilizamos el mt 'join()'
 * @author Usuario
 */
public class SincronizandoHilos {
    public static void main(String[] args) {
        HilosVarios hilo1 = new HilosVarios();
        HilosVarios hilo2 = new HilosVarios();
        hilo1.start();
        // --APLICANDO EL MT, 'join()' A CADA UNO DE LOS HILOS CONSEGUIMOS QUE SE SINCRONICEN, 
        // --HASTA QUE NO QUEDE EN ESTADO DE MUERTE EL 1º HILO CON EL MT, NO PASA A EJECUTARSE EL SIGUIENTE.
        try {
            hilo1.join();// join() ejecuta el hilo hasta que quede en estado de muerte.
        } catch (InterruptedException ex) {
            Logger.getLogger(SincronizandoHilos.class.getName()).log(Level.SEVERE, null, ex);
        }
         hilo2.start();
        try {
            hilo2.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(SincronizandoHilos.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Hola, qué hay, cómo estamos??");
    }  
}

class HilosVarios extends Thread {
    /**
     * mt que ejecuta el código que tiene dentro de su cuerpo.
     * para que lo ejecute de forma lenta, que pueda apreciarlo el ojoHumano, le aplicamos el mt sleep()
     */
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println("Ejecutando hilo " +getName());
            try {
                Thread.sleep(400);
            } catch (InterruptedException ex) {
                Logger.getLogger(HilosVarios.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
