import java.awt.geom.*;

import javax.swing.*;
import javax.swing.BorderFactory;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
/**
 * THREADS,  programación concurrente, pueden realizar varias tareas simultaneamente. 
 * Hasta ahora hemos programado monotareas, esto es. SOLO SE EJECUTABA UN THREAD,  UN HILO.
 * 
 *  *  ======================================== PASOS PARA PODER EJECUTAR VARIOS HILOS =====================
 * 1º Crear cl que implemente interface Runnable. 
 * 2º Escribir el codigo de la tarea dentro del mt run():
 * 3º Instanciar la cl creada y almacenar la instancia en variable de tipo Runnable.
 * 4º Crear instancia de la cl Thread pasando como parametro al constructor de Thread el objeto Runnable anterior.
 * 5º Poner en marcha el hilo de ejecución con el mt start() de la cl Thread.
 *
 * @author franciscoJavier
 */
public class UsoThreads {

    public static void main(String[] args) {

        MarcoRebotes marco = new MarcoRebotes();
        marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

class Pelota {//***********************************************************************************************CLASE Pelota

    //tamano de la pelota

    private static final int TAMX = 15;
    private static final int TAMY = 15;
    //coordenadas de la pelota.
    private double x = 0;
    private double y = 0;
    private double dx = 1;//para aumentar las coordenadas.
    private double dy = 1;

    /**
     * @param limites será la lámina donde aparecca la pelota, al ser de tipo
     * Rectangle podemos obtener las dimensiones
     */
    public void mueve_pelota(Rectangle2D limites) {
        //incrementa las coordenadas 'x e y' para que la pelota se valla moviendo.
        x += dx;
        y += dy;
        // serie de mt de la cl Rectangle2D para detectar los límites de la lámina y invertir las coordenadas.
        if (x < limites.getMinX()) {
            x = limites.getMinX();
            dx = -dx;
        }

        if (x + TAMX >= limites.getMaxX()) {
            x = limites.getMaxX() - TAMX;
            dx = -dx;
        }

        if (y < limites.getMinY()) {
            y = limites.getMinY();
            dy = -dy;
        }

        if (y + TAMY >= limites.getMaxY()) {
            y = limites.getMaxY() - TAMY;
            dy = -dy;
        }
    }

    public Ellipse2D getShape() {
        return new Ellipse2D.Double(x, y, TAMX, TAMY);
    }
}


class LaminaPelota extends JPanel {///*****************************************************CLASE LaminaPelota extends JPanel

    private ArrayList<Pelota> pelotas = new ArrayList<>();

    //pone un borde a la lamina.
    public LaminaPelota() {
        
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE)));
    }

    //anade una pelota al ArrayList

    public void add(Pelota b) {
        pelotas.add(b);
    }

    //pinta la pelota en la lamina.
    public void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        for (Pelota pelota : pelotas) {
            g2.setPaint(Color.RED);
            g2.fill(pelota.getShape());
        }
    }
}


class MarcoRebotes extends JFrame {//************************************************************CLASE MarcoRebotes extends JFrame 

    private LaminaPelota lamina;
    private JPanel laminaBotones;

    //creamos hilos y botones para ejecutarles independientemente.
    private Thread t1, t2, t3;
    private JButton arranca1, arranca2, arranca3, deten1, deten2, deten3;

    public MarcoRebotes() {

        laminaBotones = new JPanel();
        lamina = new LaminaPelota();

        add(lamina, BorderLayout.CENTER);
        add(laminaBotones, BorderLayout.SOUTH);

        arranca1 = new JButton("Arranca1");
        arranca2 = new JButton("Arranca2");
        arranca3 = new JButton("Arranca3");
        deten1 = new JButton("Deten1");
        deten2 = new JButton("Deten2");
        deten3 = new JButton("Deten3");
        laminaBotones.add(arranca1);
        laminaBotones.add(arranca2);
        laminaBotones.add(arranca3);
        laminaBotones.add(deten1);
        laminaBotones.add(deten2);
        laminaBotones.add(deten3);

        //---------------------------------------------- SERIE DE BOTONES PARA MOSTRAR UNA PELOTA.
        arranca1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                comienza_el_juego(e);
            }
        });
        arranca2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                comienza_el_juego(e);
            }
        });
        arranca3.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                comienza_el_juego(e);
            }
        });

        //---------------------------------------------- SERIE DE BOTONES PARA DETENER UNA PELOTA.
        deten1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                detener(e);
            }
        });
        deten2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                detener(e);
            }
        });
        deten3.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                detener(e);
            }
        });
        //coordenadas y tamano del marco.
        setBounds(450, 150, 600, 400);
        setTitle("Para therad");
        setVisible(true);
        //para mostrar miIcono personal.
        Toolkit icono = Toolkit.getDefaultToolkit();
        Image imagen = icono.getImage("../iconos/a.gif");
        setIconImage(imagen);
    }

    /**
     * 1º instancia la cl Pelota y crea una pelota. 2º agrega a la lamina la
     * pelota. 3º pasa la cl que implementa la interface Runnable la pelota y la
     * lamina y almacena todo esto en una VL de tipo Runnable. 4º crea un tarea
     * con ese Runnable. 5º le dice que comience la tarea LA VENTAJA DE ESTO ES
     * QUE CADA VEZ QUE INVOQUEMOS ESTE MT DESDE EL BOTÓN VA A CREAR UNA PELOTA
     * EN LA LAMINA.
     */
    public void comienza_el_juego(ActionEvent e) {
        Pelota pelota = new Pelota();// 1º instancia la cl Pelota y crea una pelota. 
        lamina.add(pelota);// 2º agrega a la lamina la pelota.
        Runnable r = new Hilo(pelota, lamina);// 3º pasa la cl que implementa la interface Runnable la pelota y lalamina y almacena todo esto en una VL de tipo Runnable.

        if (e.getSource().equals(arranca1)) {
            t1 = new Thread(r);// 4º crea un tarea con ese Runnable.
            t1.start();// 5º le dice que comience la tarea LA VENTAJA DE ESTO ES QUE CADA VEZ QUE INVOQUEMOS ESTE MT DESDE EL BOTÓN VA A CREAR UNA PELOTA EN LA LAMINA.
        } else if (e.getSource().equals(arranca2)) {
            t2 = new Thread(r);
            t2.start();
        } else if (e.getSource().equals(arranca3)) {
            t3 = new Thread(r);
            t3.start();
        }
    }

    /**
     * mt invocado desde los botónes 'Detener1, 2 y 3' para detener la ejecución
     * de un hilo. 1º
     */
    public void detener(ActionEvent e) {
        //t.stop();// ----este mt, está en desuso, no es recomendable su utilización.
        if (e.getSource().equals(deten1)) {
            t1.interrupt();
        } else if (e.getSource().equals(deten2)) {
            t2.interrupt();
        } else if (e.getSource().equals(deten3)) {
            t3.interrupt();
        }
    }

    private class Hilo implements Runnable {//-------------------------------------------------------CLASE Hilo implements Runnable

        private Pelota pelota;
        private Component componente;

        public Hilo(Pelota pelota, Component componente) {
            this.pelota = pelota;
            this.componente = componente;
        }

        @Override
        public void run() {
            //bucle que se ejecuta infinitamente hasta que no se cumpla la condición.
            System.out.println("Esta interrumpido el hilo? " + Thread.currentThread().isInterrupted());

            //while (!Thread.interrupted()) {
            while (!Thread.currentThread().isInterrupted()) {
                pelota.mueve_pelota(componente.getBounds());
                componente.paint(componente.getGraphics());

                //para hacer una pausa en la ejecucion de un hilo.
                try {
                    Thread.sleep(0, 4);
                } catch (InterruptedException e) {
                    //System.out.println("Herror. ¡¡¡¡");
                    //---------------------TRUCO PARA DETENER EL HILO CUANDO LANZA LA EXCEPCION PRODUCIDA POR EL MT, sleep()
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println("Esta interrumpido el hilo? " + Thread.currentThread().isInterrupted());
        }
    }
}
/////////////////////////////////////////******************************************************************************************************









