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
 */
public class EjercicioConThreads_BancoSinSincronizar {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Banco banco = new Banco();
        banco.transferencia(1, 0, 500);
        //banco.muestraDatos();
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

    public Banco() {
        cuentas = new double[100];

        for (int i = 0; i < cuentas.length; i++) {
            cuentas[i] = 2000;
        }
    }

    // REALIZA TRANSFERENCIAS ENTRE DOS CUENTAS.
    public void transferencia(int cuentaOrigen, int cuentaDestino, double cantidad) {
        //1º comprobamos si la cuentaOrigen tien saldo suficiente.
        if (cuentas[cuentaOrigen] < cantidad) {
            System.out.println("Herror, saldo insuficiente en la cuena nº. " +cuentaOrigen);
        }
        else{
            //2º muestra en pantalla el Thread que va ha hacer la transferencia.
            System.out.println("Hilo.- " + Thread.currentThread());
            //3º comprueba si los nº de cuenta existen y hace la transferencia.
            if (cuentaOrigen >= 0 && cuentaOrigen < 100 && cuentaDestino >= 0 && cuentaDestino < 100) {
                System.out.printf("%10.2f € de cuenta %d para cuenta %d",cantidad, cuentaOrigen, cuentaDestino);
                cuentas[cuentaOrigen] = cuentas[cuentaOrigen] - cantidad;
                cuentas[cuentaDestino] = cuentas[cuentaDestino] + cantidad;
            } else {
                System.out.println("Herror, nº de cuenta inexistente.");
            }   
            //DEVUEVE EL SALDO TOTAS DE TODAS LAS CUENTAS.
            System.out.printf("\n\nSaldo total en el banco: %10.2f%n", getSaldoTotal(), " €.");
        }
    }

    public double getSaldoTotal(){
        double suma_cuentas = 0;
        for(double a: cuentas){
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
