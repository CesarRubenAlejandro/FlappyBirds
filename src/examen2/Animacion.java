/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examen2;

/**
 *
 * @author Angela Romo , Cesar Rodriguez
 */
import java.awt.Image;
import java.util.ArrayList;

public class Animacion {

    private ArrayList cuadros;
    private int indiceCuadroActual;
    private long tiempoDeAnimacion;
    private long duracionTotal;

    /**
     * Crea una nueva Animacion vacía
     */
    public Animacion() {
        cuadros = new ArrayList();
        duracionTotal = 0;
        iniciar();
    }

    /**
     * Añade una cuadro a la animación con la duración indicada (tiempo que se
     * muestra la imagen).
     */
    public synchronized void sumaCuadro(Image imagen, long duracion) {
        duracionTotal += duracion;
        cuadros.add(new cuadroDeAnimacion(imagen, duracionTotal));
    }

    // Inicializa la animación desde el principio. 
    public synchronized void iniciar() {
        tiempoDeAnimacion = 0;
        indiceCuadroActual = 0;
    }

    /**
     * Actualiza la imagen (cuadro) actual de la animación, si es necesario.
     */
    public synchronized void actualiza(long tiempoTranscurrido) {
        if (cuadros.size() > 1) {
            tiempoDeAnimacion += tiempoTranscurrido;

            if (tiempoDeAnimacion >= duracionTotal) {
                tiempoDeAnimacion = tiempoDeAnimacion % duracionTotal;
                indiceCuadroActual = 0;
            }

            while (tiempoDeAnimacion > getCuadro(indiceCuadroActual).tiempoFinal) {
                indiceCuadroActual++;
            }
        }
    }

    /**
     * Captura la imagen actual de la animación. Regeresa null si la animación
     * no tiene imágenes.
     */
    public synchronized Image getImagen() {
        if (cuadros.size() == 0) {
            return null;
        } else {
            return getCuadro(indiceCuadroActual).imagen;
        }
    }

    /**
     * Cuadro De Animacion
     *
     * @param i
     * @return cuadro
     */
    private cuadroDeAnimacion getCuadro(int i) {
        return (cuadroDeAnimacion) cuadros.get(i);
    }

    /**
     * Cuadro de animacion
     */
    public class cuadroDeAnimacion {

        Image imagen;
        long tiempoFinal;

        /**
         * Cuadro de animacion
         */
        public cuadroDeAnimacion() {
            this.imagen = null;
            this.tiempoFinal = 0;
        }

        /**
         * Cuadro de animacion
         */
        public cuadroDeAnimacion(Image imagen, long tiempoFinal) {
            this.imagen = imagen;
            this.tiempoFinal = tiempoFinal;
        }

        /**
         * get Imagen regresa la imagen del objeto
         */
        public Image getImagen() {
            return imagen;
        }

        /**
         * regresa tiempo final
         */
        public long getTiempoFinal() {
            return tiempoFinal;
        }

        /**
         * colocar imagen
         */
        public void setImagen(Image imagen) {
            this.imagen = imagen;
        }

        /**
         * colocar tiempo final
         */
        public void setTiempoFinal(long tiempoFinal) {
            this.tiempoFinal = tiempoFinal;
        }
    }
}
