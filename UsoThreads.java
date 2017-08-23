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

        JFrame marco=new MarcoRebote();
        marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        marco.setVisible(true);
    }
}

//Movimiento de la pelota-----------------------------------------------------------------------------------------
class Pelota{
    //coordenadas donde aparece la bola.
    private double x=0;
    private double y=0;
    private double dx=1;
    private double dy=1;

    
    private static final int TAMX=15;
    private static final int TAMY=15;
    /**
     * @param limites será la lámina donde aparecca la pelota, al ser de tipo
     * Rectangle podemos obtener las dimensiones
     */
    public void mueve_pelota(Rectangle2D limites){
        //incrementa las coordenadas 'x e y' para que la pelota se valla moviendo.
        x+=dx;
        y+=dy;
        // serie de mt de la cl Rectangle2D para detectar los límites de la lámina y invertir las coordenadas.
        if(x<limites.getMinX()){
            x=limites.getMinX();
            dx=-dx;
        }
        
        if(x + TAMX>=limites.getMaxX()){
            x=limites.getMaxX() - TAMX;
            dx=-dx;
        }

        if(y<limites.getMinY()){
            y=limites.getMinY();
            dy=-dy;
        }

        if(y + TAMY>=limites.getMaxY()){
            y=limites.getMaxY()-TAMY;
            dy=-dy;
        }
    }

    //Forma de la pelota en su posición inicial
    public Ellipse2D getShape(){

        return new Ellipse2D.Double(x,y,TAMX,TAMY);

    }   
}

// Lámina que dibuja las pelotas----------------------------------------------------------------------
class LaminaPelota extends JPanel{

    private ArrayList<Pelota> pelotas=new ArrayList<Pelota>();
    //Añadimos pelota a la lámina
    public void add(Pelota b){
        pelotas.add(b);
    }

    public void paintComponent(Graphics g){

        super.paintComponent(g);
        Graphics2D g2=(Graphics2D)g;
        for(Pelota b: pelotas){
            g2.fill(b.getShape());
        }
    }
}

//Marco con lámina y botones------------------------------------------------------------------------------
class MarcoRebote extends JFrame{

    private LaminaPelota lamina;

    public MarcoRebote(){
        setBounds(600,300,400,350);
        setTitle ("Rebotes");

        lamina=new LaminaPelota();
        add(lamina, BorderLayout.CENTER);

        JPanel laminaBotones=new JPanel();
        add(laminaBotones, BorderLayout.SOUTH);

        ponerBoton(laminaBotones, "Dale!", new ActionListener(){
                public void actionPerformed(ActionEvent evento){
                    comienza_el_juego();
                }
            });

        ponerBoton(laminaBotones, "Salir", new ActionListener(){
                public void actionPerformed(ActionEvent evento){
                    System.exit(0);
                }
            });

    }

    //Ponemos botones
    public void ponerBoton(Container c, String titulo, ActionListener oyente){
        JButton boton=new JButton(titulo);
        c.add(boton);
        boton.addActionListener(oyente);
    }
    
    /**
     * 1º instancia la cl Pelota y crea una pelota.
     * 2º agrega a la lamina la pelota.
     * 3º pasa la cl 'Hilo' que implementa la interface Runnable la pelota y la lamina y almacena todo esto en una VL de tipo Runnable.
     * 4º crea un tarea con ese Runnable
     * 5º le dice que comience la tarea
     * LA VENTAJA DE ESTO ES QUE CADA VEZ QUE INVOQUEMOS ESTE MT DESDE EL BOTÓN VA A CREAR UNA PELOTA EN LA LAMINA.
     */
    public void comienza_el_juego (){
        Pelota pelota=new Pelota();
        lamina.add(pelota);
        
        // ------ PARA EJECUTAT VARIOS THEADS PASAMOS ESTE CÓDIGO A LA CL, CON LA INTERFARCE
        /* for (int i=1; i<=3000; i++){
            pelota.mueve_pelota(lamina.getBounds());
            lamina.paint(lamina.getGraphics());
            //para hacer una pausa en la ejecucion de un hilo. Todos los programas que hemos hecho hasta ahora
                // solo tienen un hilo
            try{
                Thread.sleep(4);
            }catch(InterruptedException e){
            }
        }*/
        
       /* *  *  ======================================== PASOS PARA PODER EJECUTAR VARIOS HILOS =====================
 
        * 3º Instanciar la cl creada y almacenar la instancia en variable de tipo Runnable.
        * 4º Crear instancia de la cl Thread pasando como parametro al constructor de Thread el objeto Runnable anterior.
        * 5º Poner en marcha el hilo de ejecución con el mt start() de la cl Thread.*/

        Runnable r = new Hilo(pelota, lamina);
        Thread t = new Thread(r);
        t.start();
    }
    
    /**
     * *  ======================================== PASOS PARA PODER EJECUTAR VARIOS HILOS =====================
     * 1º Crear cl que implemente interface Runnable. 
     */
    private class Hilo implements Runnable {

        private Pelota pelota;
        private Component componente;

        public Hilo(Pelota pelota, Component componente) {
            this.pelota = pelota;
            this.componente = componente;
        }

        /**
         *  ======================================== PASOS PARA PODER EJECUTAR VARIOS HILOS =====================
        * 2º Escribir el codigo de la tarea dentro del mt run():
         */
        @Override
        public void run() {
            //bucle que llama 3000 veces al mt, mueve_pelota, el cual mueve la pelota una posición cada vez.
            for (int i = 1; i <= 3000; i++) {
                pelota.mueve_pelota(componente.getBounds());
                componente.paint(componente.getGraphics());
                if (i == 3000) {

                }
                //para hacer una pausa en la ejecucion de un hilo. Todos los programas que hemos hecho hasta ahora
                // solo tienen un hilo. este tendrá varios hilos
                try {
                    Thread.sleep(0, 4);
                } catch (InterruptedException e) {
                    System.out.println("Herro. ¡¡¡¡");
                }
            }
        }
    }
}