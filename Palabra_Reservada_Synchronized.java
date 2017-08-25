import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Uso de la palabra reservada Synchronized
 *Lo que hace es establecer bloqueos y una condición, de una forma más sencilla, aunque tiene alguna limitación. 
 *A veces merece la pena utilizarla.
 *
 *La diferencia es que solo admite una condición, en nuestro caso valdría porque solo es comprobar que el saldo 
 *es menor que la transferencia, para que el hilo quede a la espera.
 *NO SE PUEDEN PONER MÁS CONDICIONES.
 */
public class Palabra_Reservada_Synchronized
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Banco banco = new Banco();
        for (int i = 0; i < 100; i++) {
            EjecucionTransferencias r = new EjecucionTransferencias(banco, i, 2000);
            Thread t = new Thread(r);
            t.start();
        }
    }
}

/**
 * creamos la cl Banco, para crear 100 Cuentas Corrientes, y cargar en cada una
 * de ellas 2000€.
 *
 * @author Usuario
 */
class Banco {

    private final double[] cuentas;

    public Banco() {
        cuentas = new double[100];

        for (int i = 0; i < cuentas.length; i++) {
            cuentas[i] = 2000;
        }
    }

    // REALIZA TRANSFERENCIAS ENTRE DOS CUENTAS.
    /**
     * wait() y notifyAll() son dos mt de la cl Cosmica Object.
     */
    public synchronized void transferencia(int cuentaOrigen, int cuentaDestino, double cantidad) throws InterruptedException{

        //1º comprobamos si la cuentaOrigen tien saldo suficiente, si no lo tiene ponemos al 
        //   hilo a la espera.
        while(cuentas[cuentaOrigen] < cantidad) {
            wait();  // pone al hilo a la espera.
        }           
        //2º muestra en pantalla el Thread que va ha hacer la transferencia.
        System.out.println("\nHilo.- " + Thread.currentThread());
        //3º comprueba si los nº de cuenta existen y hace la transferencia.
        if (cuentaOrigen >= 0 && cuentaOrigen < 100 && cuentaDestino >= 0 && cuentaDestino < 100) {
            //System.out.printf("%10.2f € de cuenta %d para cuenta %d", cantidad, cuentaOrigen, cuentaDestino);
            cuentas[cuentaOrigen] = cuentas[cuentaOrigen] - cantidad;
            System.out.printf("%10.2f € de cuenta %d para cuenta %d", cantidad, cuentaOrigen, cuentaDestino);
            cuentas[cuentaDestino] = cuentas[cuentaDestino] + cantidad;
        } else {
            System.out.println("Herror, nº de cuenta inexistente.");
        }
        //DEVUELVE EL SALDO TOTAS DE TODAS LAS CUENTAS, DESPUES DE CADA TRANSFERENCIA.
        System.out.printf(" --------------- Saldo total en el banco: %10.2f\n", getSaldoTotal());
       
        notifyAll(); // notifica  a los hilos en espera la acción que acaba de realizar el último hilo.
    }

    public double getSaldoTotal() {
        double suma_cuentas = 0;
        for (double a : cuentas) {
            suma_cuentas += a;
        }
        return suma_cuentas;
    }

    // ------ MUESTRA EL SALDO DE LAS CUENTAS
    public void muestraDatos() {
        for (int i = 0; i < cuentas.length; i++) {
            System.out.println("Cuenta nº " + i + " --> " + cuentas[i] + " €.");

        }
    }

}

class EjecucionTransferencias implements Runnable {

    private Banco banco; // ---------------para poder utilizar todos los mt de la cl Banco.
    private int cuentaOrigen; // ----------almacena la cuenta de la que sale dinero.
    private double cantidadATransferir;// -cantidad máxima que se puede transferir

    public EjecucionTransferencias(Banco banco, int cuentaOrigen, double cantidadATransferir) {
        this.banco = banco;
        this.cuentaOrigen = cuentaOrigen;
        this.cantidadATransferir = cantidadATransferir;
    }

    @Override
    public void run() {

        while (true) {
            try {
                // la transferencia se realiza a un nº de cuenta aleatorio.
                // -- DATO: Math.random() devuelve un double entre 0 y 1. para pasar a entero hacemos casting.
                int paraLaCuenta = (int) (100 * Math.random());
                // la cantidad a transferir es aleatoria. y se almacena en la VL cantidad.
                double cantidad = cantidadATransferir * Math.random();
                //realizamos la transferencia.
                banco.transferencia(cuentaOrigen, paraLaCuenta, cantidad);

                //para que lo muestre en pantalla de forma lenta, dormimos el hilo.
                Thread.sleep((int) (Math.random() * 10));

            } catch (InterruptedException ex) {
                System.out.println("Error .....");
            }

        }

    }

}
