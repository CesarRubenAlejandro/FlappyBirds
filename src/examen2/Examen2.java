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
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;


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
    private Image dbImage;// Imagen a proyectar	
    private Graphics dbg; // Objeto grafico
    private Image fondo; //fondo del juego
    private int score; //puntaje
    private int vidas; //vidas del juego
    //sonidos
    private SoundClip tap;
    private SoundClip punto;
    private SoundClip choca;
    private SoundClip musicaFondo;
    //imagenes pantallas
    private Image pantallaInstrucciones;
    private Image pantallaGameOver;
    private Image pantallaCreditos;
    private Image pantallaInicio;
    private botonCreditos botonCre;
    private Image botonSco;
    private botonBack botonBack;
    //listas encadenadas para barreras
    private LinkedList listUp;
    private LinkedList listDown;
    //booleanos
    private boolean Menu;
    private boolean Creditos;
    private boolean Instrucciones;
    private boolean juegoInicia;
    private boolean gameOver;
    private boolean back;
    //clicks
    private int clickX;
    private int clickY;
    //pikachu y juego
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
    private int contador;
    private boolean auxDif1;
    private boolean auxDif2;
    private int nivel2;
    //best score
    private String nombreArchivo;    //Nombre del archivo.
    private int scoreMayor;
    // Variables para el rango en y de los tubos
    private int rangoMayor;
    private int rangoMenor;
    // Archivo de puntajes altos
    private boolean puntajes;

    public Examen2() {
        setTitle("JFrame Fly A Chu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(200, 200);
        this.setSize(600, 800); //tamaño del jframe
        score = 0; //el score inicia en 0
        vidas = 1; //solo hay 1 vida en el juego
        //booleanos de audio
        sonido = true;
        addMouseListener(this);
        addKeyListener(this);
        contador = 0;
        c = new Color(255, 255, 255); //para el string de vidas 
        //crea lista para barreras
        listUp = new LinkedList();
        listDown = new LinkedList();
        //Imagenes
        fondo = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Pantallas/Fondo.jpg"));
        pantallaInstrucciones = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Pantallas/Instrucciones.jpg"));
        pantallaCreditos = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Pantallas/Creditos.jpg"));
        pantallaGameOver = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Pantallas/GameOver.jpg"));
        pantallaInicio = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Pantallas/Menu.jpg"));
        botonSco = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Iconos/Scores.png"));
        botonCre = new botonCreditos(getWidth() / 2, 450);
        botonCre.setPosX(getWidth() / 2 - botonCre.getAncho() / 2); //reposicionar
        botonBack = new botonBack(getWidth() / 2, 650);
        botonBack.setPosX(getWidth() / 2 - botonBack.getAncho() / 2); //reposicionar
        //Sonidos
        tap = new SoundClip("Sounds/vida.wav");
        punto = new SoundClip("Sounds/desk_bell.wav");
        choca = new SoundClip("Sounds/dead.wav");
        musicaFondo = new SoundClip("Sounds/shorter-swbl.wav");
        //musica
        //Activa la repetición del clip
        musicaFondo.setLooping(true);
        //Reproduce el clip
        musicaFondo.play();
        //booleanos para control de pantallas
        Menu = true;
        Creditos = false;
        Instrucciones = false;
        gameOver = false;
        juegoInicia = false;
        back = false;
        gameOver = false;
        //clicks
        clickX = 0;
        clickY = 0;
        //variables para control del juego
        vuela = false;
        pika = new Pikachu(getWidth() / 2, getHeight() / 2);
        tiempo = 0;
        grav = 2;
        pegaAbajo = false;
        gap = getHeight() / 4;
        numTubos = 3;
        //crear lista de obstaculos
        int auxPosX = 0;
        rangoMayor = 200;
        rangoMenor = 100;
        for (int i = 0; i < numTubos; i++) {
            //int auxPosY = (int) (Math.random() * (600 - 400) + 400);
            int auxPosY = (int) (Math.random() * (rangoMayor - rangoMenor) + rangoMenor);
            tubo2 = new tubeUp(getWidth() + 20 + auxPosX, auxPosY);
            tubo1 = new tubeDown(getWidth() + 20 + auxPosX, -100);
            tubo1.setPosY(auxPosY - gap - tubo1.getAlto());

            listUp.add(tubo2);
            listDown.add(tubo1);
            auxPosX += getWidth() / 2;
        }
        auxScore = true;
        auxDif1 = true;
        auxDif2 = true;
        nivel2 = 0;
        nombreArchivo = "Datos.txt";
        puntajes = false;
        //HILO
        Thread th = new Thread(this);
        // Empieza el hilo
        th.start();
    }

    /**
     * Metodo para resetear los valores Cuando se pide volver a jugar
     */
    public void reset() {
        juegoInicia = true;
        //booleanos para control de pantallas
        Menu = false;
        Creditos = false;
        Instrucciones = false;
        gameOver = false;
        back = false;
        gameOver = false;
        pausa = false;
        //variables
        vidas = 1;
        tiempo = 0;
        score = 0;
        vuela = false;
        pegaAbajo = false;
        gap = getHeight() / 4;
        pika.setPosY(getHeight() / 2);
        pika.setPosX(getWidth() / 2);
        int auxPosX = 0;
        rangoMayor = 500;
        rangoMenor = 480;
        for (int i = 0; i < numTubos; i++) {

            int auxPosY = (int) (Math.random() * (rangoMayor - rangoMenor) + rangoMenor);
            ((tubeDown) listDown.get(i)).setPosX(getWidth() + 20 + auxPosX);
            ((tubeUp) listUp.get(i)).setPosX(getWidth() + 20 + auxPosX);
            ((tubeUp) listUp.get(i)).setPosY(auxPosY);
            ((tubeDown) listDown.get(i)).setPosY(auxPosY - gap - ((tubeUp) listUp.get(i)).getAlto());

            auxPosX += getWidth() / 2;
        }

        nivel2 = 0;
        auxDif2 = true;
        auxScore = true;
        puntajes = false;
        //Iniciar thread nuevo si es el segundo juego o despues
        if (contador > 1) {
            Thread th = new Thread(this);
            // Empieza el hilo
            th.start();
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
            if (!pausa && juegoInicia) {
                try {
                    actualiza();
                } catch (IOException e) {
                    System.out.println("Error en " + e.toString());
                }
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
    public void actualiza() throws IOException {
        //Musica de fondo si se desea
        if (!sonido) {
            musicaFondo.stop();
        }
        //Niveles de Dificultad
        if (score % 10 == 0 && auxDif2) { // Se hace residuo con 10 para que la dificultad aumente a partir del tubo #10
            // if (nivel2<getWidth()/2 && rangoMayor < 600 && rangoMenor > 400)
            nivel2 += 65;
            rangoMayor += 20;
            rangoMenor -= 20;
        }
        auxDif2 = false;

        if (pegaAbajo) {
            vidas--;
            if (sonido) {
                choca.play();
            }
            juegoInicia = false;
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
            if (sonido) {
                tap.play();
            }
        }

        // Movimiento de caida libre
        int aux = (pika.getVelocidad() * tiempo) - (grav * tiempo * tiempo) / 2;
        pika.setPosY(pika.getPosY() - aux);

        // Movimiento de las tuberias
        for (int i = 0; i < numTubos; i++) {
            ((tubeUp) listUp.get(i)).setPosX(((tubeUp) listUp.get(i)).getPosX() - 10);
            ((tubeDown) listDown.get(i)).setPosX(((tubeDown) listDown.get(i)).getPosX() - 10);
            if ((pika.getPosX() > ((tubeUp) listUp.get(i)).getPosX() + ((tubeUp) listUp.get(i)).getAncho()) && (auxScore)) {
                score++;
                auxDif2 = true; // Permite que se aumente el nivel solo una vez 
                //Sonido si se desea
                if (sonido) {
                    punto.play();
                }
                auxScore = false;
            }
        }
        //reinicia bools
        vuela = false;

        //checa vidas
        if (vidas == 0) {
            //guarda mejor score al finalizar el juego
           // URL urlToDictionary = this.getClass().getResource("/" + "Datos.txt");
            BufferedReader fileIn = new BufferedReader(new FileReader(nombreArchivo));
            int compara = Integer.parseInt(fileIn.readLine());
            fileIn.close();
            if (compara < score) {
                PrintWriter fileOut = new PrintWriter(new FileWriter(nombreArchivo));
                fileOut.println(score);
                fileOut.close();
                scoreMayor = score;
            } else {
                scoreMayor = compara;
            }

            gameOver = true; //prende bool para PANTALLA de gameover
        }
    }

    /**
     * Metodo <I>checaColision</I>
     * Metodo usado para checar las colisiones de los objetos barquito y rayito
     * entre sí y con las orillas del <code>JFrame</code>.
     */
    public void checaColision() {

        // Si el pikachu llega hasta el limite superior del jframe
        if (pika.getPosY() < 0) {
            pegaAbajo = true;
        }

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
            int auxX = getWidth() / 2 - nivel2;
            tubeDown tub = (tubeDown) listDown.get(i);
            if (tub.getPosX() + tub.getAncho() < 0) {

                ((tubeDown) listDown.get(i)).setPosX(getWidth() + 20 + auxX);
                ((tubeUp) listUp.get(i)).setPosX(getWidth() + 20 + auxX);
                int auxPosY = (int) (Math.random() * (rangoMayor - rangoMenor) + rangoMenor);
                ((tubeUp) listUp.get(i)).setPosY(auxPosY);
                ((tubeDown) listDown.get(i)).setPosY(auxPosY - gap - ((tubeUp) listUp.get(i)).getAlto());

                auxX += getWidth() / 2;
                auxScore = true;

            }
        }
    }
    /**
     * Despliega la tabla de high scores, llamado por el paint1
     * @param g para pintar a pantalla
     * @throws IOException 
     */
    public void despliega(Graphics g) throws IOException { // Despliega score
        int y = getHeight() / 4;
        BufferedReader fileIn = new BufferedReader(new FileReader("puntaje.txt"));
        String linea = fileIn.readLine();
        g.setColor(c);
        while (linea != null) {
            g.drawString(linea, 100, y);
            linea = fileIn.readLine();
            y += 50;
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

        if (Menu) {
            //Pintar menu
            g.drawImage(pantallaInicio, 0, 0, this);
        } else if (Creditos) {
            //Pantalla de creditos y boton back
            g.drawImage(pantallaCreditos, 0, 0, this);
            g.drawImage(botonBack.getImagenI(), botonBack.getPosX(), botonBack.getPosY(), this);
        } else if (Instrucciones) {
            //Pintar instrucciones
            g.drawImage(pantallaInstrucciones, 0, 0, this);
        } else if (gameOver) {
            //Pintar game over, score y boton creditos
            g.drawImage(pantallaGameOver, 0, 0, this);
            g.drawImage(botonSco, botonCre.getPosX(), 300, this);
            g.drawString("" + score, botonCre.getPosX() + 200, 330);
            g.drawString("" + scoreMayor, botonCre.getPosX() + 200, 380);
            g.drawImage(botonCre.getImagenI(), botonCre.getPosX(), botonCre.getPosY(), this);
        } else if (juegoInicia) {
            //Pintar juego
            g.drawImage(fondo, 0, 0, this);
            g.drawImage(pika.getImagenI(), pika.getPosX(), pika.getPosY(), this);
            for (int i = 0; i < numTubos; i++) {
                g.drawImage(((tubeDown) listDown.get(i)).getImagenI(), ((tubeDown) listDown.get(i)).getPosX(), ((tubeDown) listDown.get(i)).getPosY(), this);
                g.drawImage(((tubeUp) listUp.get(i)).getImagenI(), ((tubeUp) listUp.get(i)).getPosX(), ((tubeUp) listUp.get(i)).getPosY(), this);
            }
            g.drawString("Score: " + score, getWidth() - 100, 40);
            if (pausa) {
                //Indicar pausa
                g.drawString("PAUSA", pika.getPosX() + 10, pika.getPosY() - 5);
            }
        }
        if (puntajes) {
            g.drawImage(fondo, 0, 0, this);
            g.setColor(c);
            g.drawString("HIGH SCORES", getWidth() / 2 - 100, getHeight() / 4 - 100);
            try {
                despliega(g);
            } catch (IOException e) {
                System.out.println("Error en " + e.toString());

            }

        }
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
            if (sonido) {
                //Reproduce el clip
                musicaFondo.play();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_I) { //Presiono tecla I
            if (Menu) {
                Menu = false;
                Instrucciones = !Instrucciones;
            } else if (Instrucciones) {
                Instrucciones = !Instrucciones;
                Menu = true;
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_N && (Menu || gameOver)) { //tecleo N
            juegoInicia = true;
            Menu = false;
            gameOver = false;
            contador++;
            reset();
        }

        if (e.getKeyCode() == KeyEvent.VK_SPACE) { //Presiono tecla espacio
            if (juegoInicia) {
                vuela = true;
            }
            if (gameOver || Menu) {
                juegoInicia = true;
                Menu = false;
                gameOver = false;
                contador++;
                reset();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_F) {
            puntajes = !puntajes;
            pausa = true;
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

    }

    /**
     * Metodo mouseClicked sobrescrito de la interface MouseListener. En este
     * metodo maneja el evento que se genera al hacer click con el mouse sobre
     * algun componente. e es el evento generado al hacer click con el mouse.
     */
    public void mouseClicked(MouseEvent e) {
        clickX = e.getX();
        clickY = e.getY();
        if (botonCre.clickEnPersonaje(clickX, clickY)) {
            Creditos = true;
            gameOver = false;
        }
        if (botonBack.clickEnPersonaje(clickX, clickY)) {
            Creditos = false;
            gameOver = true;
        }

        if (juegoInicia) {
            vuela = true;
        }
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
