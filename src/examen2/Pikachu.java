/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examen2;

/**
 *
 * @author Angela Romo, Cesar Rodriguez
 */
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

public class Pikachu extends Base {

    private int velocidad; //variable velocidad

    /**
     * Constructor
     *
     * @param posX posicion en X
     * @param posY posicion en Y
     */
    public Pikachu(int posX, int posY) {
        super(posX, posY);
        //imagenes a animar
        URL bURL = this.getClass().getResource("ImagenesPikachu/frame_000.gif");
        Image pic0 = Toolkit.getDefaultToolkit().getImage(bURL);

        URL b1URL = this.getClass().getResource("ImagenesPikachu/frame_001.gif");
        Image pic1 = Toolkit.getDefaultToolkit().getImage(b1URL);

        URL b2URL = this.getClass().getResource("ImagenesPikachu/frame_002.gif");
        Image pic2 = Toolkit.getDefaultToolkit().getImage(b2URL);

        URL b3URL = this.getClass().getResource("ImagenesPikachu/frame_003.gif");
        Image pic3 = Toolkit.getDefaultToolkit().getImage(b3URL);

        URL b4URL = this.getClass().getResource("ImagenesPikachu/frame_004.gif");
        Image pic4 = Toolkit.getDefaultToolkit().getImage(b4URL);

        URL b5URL = this.getClass().getResource("ImagenesPikachu/frame_005.gif");
        Image pic5 = Toolkit.getDefaultToolkit().getImage(b5URL);

        URL b6URL = this.getClass().getResource("ImagenesPikachu/frame_006.gif");
        Image pic6 = Toolkit.getDefaultToolkit().getImage(b6URL);

        URL b7URL = this.getClass().getResource("ImagenesPikachu/frame_007.gif");
        Image pic7 = Toolkit.getDefaultToolkit().getImage(b7URL);

        anima = new Animacion();
        anima.sumaCuadro(pic0, 200);
        anima.sumaCuadro(pic1, 200);
        anima.sumaCuadro(pic2, 200);
        anima.sumaCuadro(pic3, 200);
        anima.sumaCuadro(pic4, posY);
        anima.sumaCuadro(pic5, posY);
        anima.sumaCuadro(pic6, posY);
        anima.sumaCuadro(pic7, posY);

        velocidad = 0;
    }

    /**
     * Establecer variable velocidad (integer)
     *
     * @param x velocidad
     */
    public void setVelocidad(int x) {
        velocidad = x;
    }

    /**
     * Regresa valor de velocidad
     *
     * @return velocidad
     */
    public int getVelocidad() {
        return velocidad;
    }
}
