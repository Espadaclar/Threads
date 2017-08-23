import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * --------------------------------SINCRONIZACIÓN DE THREADS. ---------------------------------
 * 
 * EJECUTANDO EL PROGRAMA SE VE QUE NO IMPRIME LOS HILOS EN EL ORDEN EN QUE ESTÁN COLOCADOS EN EL CÓDIGO.
 * (el orden de mostrar los hilos en pantalla depende del SO y de los recursos que esté utilizando el 
 * ordenador en ese momento.)
 * 
 * HABRA OCOASIONES EN QUE TENGAMOS QUE UTILIZAR UN HILO ANTES QUE OTRO, PARA ELLO SE 
 * NECESITA -------- SINCRONIZAR LOS HILOS. -------
 * 
 * @author Usuario
 */
public class SincronizandoHilos {
    public static void main(String[] args) {
        HilosVarios hilo1 = new HilosVarios();
        HilosVarios hilo2 = new HilosVarios();
        HilosVarios hilo3 = new HilosVarios();
        hilo1.start();
        hilo2.start ();
        hilo3.start();
    }  
}

class HilosVarios extends Thread {
    public void run() {
        for (int i = 0; i < 15; i++) {
            System.out.println("Ejecutando hilo " +getName());
            try {
                Thread.sleep(400);
            } catch (InterruptedException ex) {
                Logger.getLogger(HilosVarios.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
