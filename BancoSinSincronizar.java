/**
 * El siguiente ejercicio representa a un banco con cien cuentas corrientes. Cada cuenta tiene un saldo inicial de 2000€,
 * por lo que el saldo total de todas las cuentas es de 200000 €.

 *--La cuenta nº 1 por ej, hace una transferencia de 500€ a la cuenta nº 4, por lo que una cuenta se queda en 1500€ y 
 *otra cuenta aumenta hasta 2500€, el saldo total del banco sigue siendo de 200000 €.
 *Esta operarción la realizará un Thread.

 *--Lo mismo ocurre con la cuenta nº 2 y la cuenta nº 6, pero con una cantidad de 725 €.
Esta operarción la realizará otro Thread.

 *---Eso es lo que hará el programa. Crea un banco con 100 cuentas, y dentro de un bucle infinito realizará 
 *transferencias entre cuentas.

 * 1º creamos la cl Baco, para crear 100 Cuentas Corrientes, y cargar en cada
 * una de ellas 2000€. 2º creamos un mt que realice las transferencias entre
 * cuentas.
 *
 * 2º Creamos la cl que implemente el mt run(), para que los Threads realicen
 * las transferencias de forma infinta. Este programa genera transferencias
 * ifinitas.
 * 
 * 3º ejecutamos el mt main. a) instanciar la cl que implementa la interface Runnable.
 *                           b) instanciar un objeto Thread pasandole un objeto que implenta la interface Runnable.
 *                           c) invocar el mt start() sobre el objeto Thread.
 *
 * 4º Al ser programación concurrente ocurre que se están ejecutando varios
 * Threads a la vez, con lo que uno de ellos puede estar sacando dinero de una
 * cuenta y antes de haberlo ingresado en otra cuenta, otro de los hilos haya
 * imprimido el saldo total de todas las cuenta, con lo que ocurrirá que falte
 * dinero.
 *
 * Para solucionar este problema, utilizamos la cl, 'ReentrantLock' la cual
 * implementa dos interfaces, --la interface 'Lock' implementa varios mt, el mt
 * ‘lock()’  el cual se encarga de bloquear un trozo de código hasta que el
 * hilo que lo esté ejecutando termine de recorrer ese código. El mt ‘unLock()’
 *  que desbloquea el código antes bloqueado.
 *
 */
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BancoSinSincronizar {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Banco banco = new Banco();
        banco.muestraDatos();
        for(int i = 0; i < 100; i ++){
            EjecucionTransferencias r = new EjecucionTransferencias(banco, i, 2000);
            Thread t = new Thread(r);
            t.start();

        }
    }

}

/**
 * creamos la cl Baco, para crear 100 Cuentas Corrientes, y cargar en cada una
 * de ellas 2000€.
 *
 * @author Usuario
 */
class Banco {

    private final double[] cuentas;
    //para facilitar el que se ejecute un solo Thread cada vez en un trozo de código.
    private Lock cierreBanco = new ReentrantLock();

    public Banco() {
        cuentas = new double[100];

        for (int i = 0; i < cuentas.length; i++) {
            cuentas[i] = 2000;
        }
    }

    // REALIZA TRANSFERENCIAS ENTRE DOS CUENTAS.
    public void transferencia(int cuentaOrigen, int cuentaDestino, double cantidad) {

        try {

            cierreBanco.lock();// lock()--> solo permite la ejecución de un Thread cada vez.

            //1º comprobamos si la cuentaOrigen tien saldo suficiente.
            if (cuentas[cuentaOrigen] < cantidad) {
                System.out.println("Herror, saldo insuficiente en la cuena nº. " + cuentaOrigen);
            } else {
                //2º muestra en pantalla el Thread que va ha hacer la transferencia.
                System.out.println("Hilo.- " + Thread.currentThread());
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
                System.out.printf("\n\nSaldo total en el banco: %10.2f\n", getSaldoTotal());
            }           
        } 
        finally {// finally --> que tanto si se produce una Excepción como si no, al final del código libera la Excepcion.
            cierreBanco.unlock();
        }
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
