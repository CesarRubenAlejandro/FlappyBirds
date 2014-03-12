/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examen2;

//Importar todas las librerias a utilizar
import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Toolkit;
import java.awt.Color;
import java.util.LinkedList;

/**
 *
 * @author Cesar Rodriguez A01036009, Angela Romo A01139764
 *
 */
public class Examen2 extends JFrame implements Runnable, KeyListener, MouseListener {

    // Declarar todas las variables
    private Color c;
    private long tiempoActual; //Variables de control de tiempo de la animacion
    private long tiempoInicial; //Variables de control de tiempo de la animacion
    private boolean pausa; // bandera para manejar la pausa
    //banderas para activar o no sonido y musica
    private boolean sonido;
    private boolean musica;
    private Image dbImage;// Imagen a proyectar	
    private Graphics dbg; // Objeto grafico
    private int numBloques; //numero de bloques de meth
    private Image fondo; //fondo del juego
    private int score; //puntaje
    private int vidas; //vidas del juego
    //sonidos
    private SoundClip tap;
    private SoundClip choca;
    private SoundClip musicaFondo;
    //imagenes pantallas
    private Image pantallaInstrucciones;
    private Image pantallaAjustes;
    private Image pantallaGameOver;
    private Image pantallaGoodJob;
    private Image pantallaCreditos;
    private Image pantallaPausa;
    private Image pantallaInicio;
    private LinkedList listUp; //lista para barreras
    private LinkedList listDown;
    //booleanos
    private boolean Menu;
    private boolean Creditos;
    private boolean Instrucciones;
    private boolean juegoInicia;
    private boolean gameOver;

    private boolean vuela;
    private Pikachu pika;
    private int tiempo;
    private int grav;
    private boolean pegaAbajo;
    private tubeDown tubo1;
    private tubeUp tubo2;
    private boolean auxScore;
    private int gap;
    private int numTubos;

    public Examen2() {
        setTitle("JFrame Fly A Chu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(200, 200);
        this.setSize(600, 800); //tamaño del jframe
        score = 0; //el score inicia en 0
        vidas = 1; //solo hay 1 vida en el juego
        //booleanos de audio
        // sonido = true;
        // musica = true;
        addMouseListener(this);
        addKeyListener(this);
        c = new Color(255, 255, 255); //para el string de vidas 
        //crea lista para barreras
        listUp = new LinkedList();
        listDown = new LinkedList();
        //Imagenes
        fondo = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Pantallas/Slide1.jpg"));
        // pantallaInstrucciones = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Pantallas/pantallaInstrucciones.jpg"));
        //   pantallaPausa = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Pantallas/pantallaPausa.jpg"));
        //  pantallaCreditos = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Pantallas/pantallaCreditos.jpg"));
        //pantallaAjustes = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Pantallas/pantallaAjustes.jpg"));
        // pantallaGameOver = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Pantallas/pantallaGameOver.jpg"));
        //  pantallaInicio = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Pantallas/pantallaInicio.jpg"));
        //booleanos para control de pantallas
        //inicializar variables booleanas 
        // Menu = true;
        Creditos = false;
        Instrucciones = false;
        gameOver = false;
        juegoInicia = false;

        vuela = false;
        pika = new Pikachu(getWidth() / 4, getHeight() / 4);
        tiempo = 0;
        grav = 2;
        pegaAbajo = false;
        gap = getHeight() / 3;
        numTubos = 3;

        int auxPosX = 0;
        for (int i = 0; i < numTubos; i++) {
            int auxPosY = (int) (Math.random() * (600 - 400) + 400);
            tubo2 = new tubeUp(getWidth() + 20 + auxPosX, auxPosY);
            tubo1 = new tubeDown(getWidth() + 20 + auxPosX, -100);
            tubo1.setPosY(auxPosY - gap - tubo1.getAlto());

            listUp.add(tubo2);
            listDown.add(tubo1);
            auxPosX += getWidth() / 2;
        }

        /* int auxPosY = (int) (Math.random() * (600 - 400) + 400);
         tubo2 = new tubeUp (getWidth()+20, auxPosY); 
         tubo1 = new tubeDown (getWidth()+20, -100);
         tubo1.setPosY(auxPosY-gap - tubo1.getAlto());*/
        auxScore = true;

        //HILO
        Thread th = new Thread(this);
        // Empieza el hilo
        th.start();
    }

    public void reset() {
        vidas = 1;
        tiempo = 0;
        score = 0;
        vuela = false;
        pika.setPosY(getHeight() / 4);
        pika.setPosX(getWidth() / 4);
        /* tubo1.setPosX(getWidth() + 50);
         tubo2.setPosX(getWidth() + 50);*/
        int auxPosX = 0;
        for (int i = 0; i < numTubos; i++) {
            int auxPosY = (int) (Math.random() * (600 - 400) + 400);
            ((tubeDown) listDown.get(i)).setPosX(getWidth() + 20 + auxPosX);
            ((tubeUp) listUp.get(i)).setPosX(getWidth() + 20 + auxPosX);
            ((tubeUp) listUp.get(i)).setPosY(auxPosY);
            ((tubeDown) listDown.get(i)).setPosY(auxPosY - gap - ((tubeUp) listUp.get(i)).getAlto());

            auxPosX += getWidth() / 2;
        }
    }

    /**
     * Metodo <I>run</I> sobrescrito de la clase <code>Thread</code>.<P>
     * En este metodo se ejecuta el hilo, es un ciclo indefinido donde se
     * incrementa la posicion en x o y dependiendo de la direccion, finalmente
     * se repinta el <code>JFrame</code> y luego manda a dormir el hilo.
     *
     */
    public void run() {

        //Guarda el tiempo actual del sistema
        tiempoActual = System.currentTimeMillis();

        //Ciclo principal del JFrame. Actualiza y despliega en pantalla hasta que se acaben las vidas
        while (vidas > 0) {

            //si esta pausado no actualizas ni checas colision 
            if (!pausa) {
                actualiza();
                checaColision();
            }
            repaint(); // Se actualiza el <code>JFrame</code> repintando el contenido.
            try {
                // El thread se duerme.
                Thread.sleep(80);
            } catch (InterruptedException ex) {
                System.out.println("Error en " + ex.toString());
            }

        }
    }

    /**
     * Metodo <I>actualiza</I>
     * Es usado para actualizar la posicion de los personajes y los valores de
     * las variables.
     */
    public void actualiza() {

        if (pegaAbajo) {
            reset();
            pegaAbajo = false;
        }
        //Determina el tiempo que ha transcurrido desde que el Applet inicio su ejecuciÃ³n
        long tiempoTranscurrido = System.currentTimeMillis() - tiempoActual;
        //Guarda el tiempo actual
        tiempoActual += tiempoTranscurrido;

        // Aumenta el tiempo para la formula fisica
        tiempo++;
        //Actualiza los gifs
        pika.actualiza(tiempoActual);
        // Si presionas SPACEBAR reinicias el tiempo y le das una velocidad inicial al pikachu
        if (vuela) {
            tiempo = 0;
            pika.setVelocidad(8);
        }

        // Movimiento de caida libre
        int aux = (pika.getVelocidad() * tiempo) - (grav * tiempo * tiempo) / 2;
        pika.setPosY(pika.getPosY() - aux);

        /*if ((pika.getPosX() > tubo1.getPosX() + tubo1.getAncho()) && (auxScore)) {
         score++;
         auxScore = false;
         }*/
        // Movimiento de las tuberias
        for (int i = 0; i < numTubos; i++) {
            ((tubeUp) listUp.get(i)).setPosX(((tubeUp) listUp.get(i)).getPosX() - 10);
            ((tubeDown) listDown.get(i)).setPosX(((tubeDown) listDown.get(i)).getPosX() - 10);
            if ((pika.getPosX() > ((tubeUp) listUp.get(i)).getPosX() + ((tubeUp) listUp.get(i)).getAncho()) && (auxScore)) {
                score++;
                auxScore = false;
            }
        }
        /*tubo1.setPosX(tubo1.getPosX() - 10);
         tubo2.setPosX(tubo2.getPosX() - 10);*/

        //reinicia bools
        vuela = false;
    }

    /**
     * Metodo <I>checaColision</I>
     * Metodo usado para checar las colisiones de los objetos barquito y rayito
     * entre sí y con las orillas del <code>JFrame</code>.
     */
    public void checaColision() {

        //Si el pikachu golpea abajo del jframe
        if (pika.getPosY() > getWidth()) {
            pegaAbajo = true;
        }

        //Checa colision de los tubos con la pared izquierda
        // Cuando llegan a la pared, reiniciarlos y reiniciar el auxScore
        for (int i = 0; i < numTubos; i++) {
            //Si el pikachu golpea con cualquier tubo
            if ((pika.intersecta(((tubeDown) listDown.get(i)))) || (pika.intersecta(((tubeUp) listUp.get(i))))) {
                pegaAbajo = true;
            }
            int auxX = getWidth()/2;
            tubeDown tub = (tubeDown) listDown.get(i);
            if (tub.getPosX() + tub.getAncho() < 0) {
                if (i==0){
                    ((tubeDown) listDown.get(i)).setPosX(getWidth()  + auxX);
                    ((tubeUp) listUp.get(i)).setPosX(getWidth() + auxX);
                    int auxPosY = (int) (Math.random() * (600 - 400) + 400);
                    ((tubeUp) listUp.get(i)).setPosY(auxPosY);
                    ((tubeDown) listDown.get(i)).setPosY(auxPosY - gap - ((tubeUp) listUp.get(i)).getAlto());

                    auxX += getWidth()/2;
                    auxScore = true;
                }
                else{
                    ((tubeDown) listDown.get(i)).setPosX(getWidth() + 20 + auxX);
                    ((tubeUp) listUp.get(i)).setPosX(getWidth() + 20 + auxX);
                    int auxPosY = (int) (Math.random() * (600 - 400) + 400);
                    ((tubeUp) listUp.get(i)).setPosY(auxPosY);
                    ((tubeDown) listDown.get(i)).setPosY(auxPosY - gap - ((tubeUp) listUp.get(i)).getAlto());

                    auxX += getWidth()/2;
                    auxScore = true;
                }
            }
        }
    }

    /**
     * Metodo <I>paint</I>
     * En este metodo lo que hace es actualizar el contenedor (Update)
     *
     * @param g es el <code>objeto grafico</code> usado para dibujar.
     */
    public void paint(Graphics g) {
        // Inicializan el DoubleBuffer
        if (dbImage == null) {
            dbImage = createImage(this.getSize().width, this.getSize().height);
            dbg = dbImage.getGraphics();
        }

        // Actualiza la imagen de fondo.
        dbg.setColor(getBackground());
        dbg.fillRect(0, 0, this.getSize().width, this.getSize().height);

        // Actualiza el Foreground.
        dbg.setColor(getForeground());
        paint1(dbg);

        // Dibuja la imagen actualizada
        g.drawImage(dbImage, 0, 0, this);

    }

    /**
     * Metodo <I>paint1</I>
     * En este metodo se dibuja la imagen con la posicion actualizada, ademas
     * que cuando la imagen es cargada te despliega una advertencia. (Paint)
     *
     * @param g es el <code>objeto grafico</code> usado para dibujar.
     */
    public void paint1(Graphics g) {
        g.drawImage(fondo, 0, 0, this);
        g.drawImage(pika.getImagenI(), pika.getPosX(), pika.getPosY(), this);
        for (int i = 0; i < numTubos; i++) {
            g.drawImage(((tubeDown) listDown.get(i)).getImagenI(), ((tubeDown) listDown.get(i)).getPosX(), ((tubeDown) listDown.get(i)).getPosY(), this);
            g.drawImage(((tubeUp) listUp.get(i)).getImagenI(), ((tubeUp) listUp.get(i)).getPosX(), ((tubeUp) listUp.get(i)).getPosY(), this);
        }
        // g.drawImage(tubo1.getImagenI(), tubo1.getPosX(), tubo1.getPosY(), this);
        // g.drawImage(tubo2.getImagenI(), tubo2.getPosX(), tubo2.getPosY(), this);
        g.drawString("Score: " + score, getWidth() / 2 - 30, 40);
    }

    /**
     * Metodo <I>keyTyped</I> sobrescrito de la interface
     * <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al presionar una tecla que
     * no es de accion.
     *
     * @param e es el <code>evento</code> que se genera en al presionar las
     * teclas.
     */
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Metodo <I>keyPressed</I> sobrescrito de la interface
     * <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al presionar cualquier la
     * tecla.
     *
     * @param e es el <code>evento</code> generado al presionar las teclas.
     */
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_P) {    //Presiono letra P
            pausa = !pausa; //cambio valor de pausa
        }

        if (e.getKeyCode() == KeyEvent.VK_S) { //Presiono tecla S
            sonido = !sonido;
        }

        if (e.getKeyCode() == KeyEvent.VK_M) { //Presiono tecla M
            musica = !musica;
        }

        if (e.getKeyCode() == KeyEvent.VK_SPACE) { //Presiono tecla M
            vuela = true;
        }
    }

    /**
     * Metodo <I>keyReleased</I> sobrescrito de la interface
     * <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al soltar la tecla
     * presionada.
     *
     * @param e es el <code>evento</code> que se genera en al soltar las teclas.
     */
    public void keyReleased(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

    /**
     * Metodo mouseClicked sobrescrito de la interface MouseListener. En este
     * metodo maneja el evento que se genera al hacer click con el mouse sobre
     * algun componente. e es el evento generado al hacer click con el mouse.
     */
    public void mouseClicked(MouseEvent e) {
        /*  clickX = e.getX();
         clickY = e.getY();

         //checa clicks en botones
         if (botonInst.clickEnPersonaje(clickX, clickY)) {
         if (!juegoInicia) {
         Menu = false;
         Instrucciones = true;
         }
         }
         if (botonIni.clickEnPersonaje(clickX, clickY)) {
         pausa = false;
         Menu = false;
         juegoInicia = true;
         }
         if (botonAj.clickEnPersonaje(clickX, clickY)) {
         if (!juegoInicia) {
         Menu = false;
         Ajustes = true;
         }
         }
         if (botonCre.clickEnPersonaje(clickX, clickY)) {
         if (!juegoInicia) {
         Menu = false;
         Creditos = true;
         }
         }
         if (botonBack.clickEnPersonaje(clickX, clickY)) {
         if (vidas==0 || gano) 
         {
         vidas=1;
         gano = false;
         reset();
         }
         Menu = true;
         Instrucciones = false;
         Ajustes = false;
         Creditos = false;
         juegoInicia=false;
            
            
         }
         */
    }

    /**
     * Metodo mousePressed sobrescrito de la interface MouseListener. En este
     * metodo maneja el evento que se genera al presionar un botón del mouse
     * sobre algun componente. e es el evento generado al presionar un botón del
     * mouse sobre algun componente.
     */
    public void mousePressed(MouseEvent e) {
    }

    /**
     * Metodo mouseReleased sobrescrito de la interface MouseListener. En este
     * metodo maneja el evento que se genera al soltar un botón del mouse sobre
     * algun componente. e es el evento generado al soltar un botón del mouse
     * sobre algun componente.
     */
    public void mouseReleased(MouseEvent e) {
    }

    /**
     * Metodo mouseEntered sobrescrito de la interface MouseListener. En este
     * metodo maneja el evento que se genera cuando el mouse entra en algun
     * componente. e es el evento generado cuando el mouse entra en algun
     * componente.
     */
    public void mouseEntered(MouseEvent e) {
    }

    /**
     * Metodo mouseExited sobrescrito de la interface MouseListener. En este
     * metodo maneja el evento que se genera cuando el mouse sale de algun
     * componente. e es el evento generado cuando el mouse sale de algun
     * componente.
     */
    public void mouseExited(MouseEvent e) {
    }

}
