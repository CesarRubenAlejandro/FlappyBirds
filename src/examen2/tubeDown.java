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

public class tubeDown extends Base {

    //constructor
    public tubeDown(int posX, int posY) {
        super(posX, posY);

        URL bURL = this.getClass().getResource("Obstaculos/tubeDown.png");
        Image pic0 = Toolkit.getDefaultToolkit().getImage(bURL);

        anima = new Animacion();
        anima.sumaCuadro(pic0, 200);

    }

}
