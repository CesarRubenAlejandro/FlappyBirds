/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package examen2;

/**
 *
 * @author Aaron
 */
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

public class tubeUp extends Base {

    public tubeUp(int posX, int posY) {
        super(posX, posY);

        URL bURL = this.getClass().getResource("Obstaculos/tubeUp.png");
        Image pic0 = Toolkit.getDefaultToolkit().getImage(bURL);

        anima = new Animacion();
        anima.sumaCuadro(pic0, 200);
    }

}
