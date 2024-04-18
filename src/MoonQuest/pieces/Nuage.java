package pieces;

import display.Board;
import utils.Colors;

/**
 * Représente un nuage sur le plateau de jeu.
 * Hérite de la classe abstraite Piece.
 */
public class Nuage extends Piece {

        /**
     * Constructeur de la classe Nuage.
     *
     * @param x     Position en abscisse du nuage sur le plateau.
     * @param y     Position en ordonnée du nuage sur le plateau.
     * @param icon  Icône représentant le nuage.
     * @param type  Type de nuage ("Methane" ou "Eau").
     * @param color Couleur du nuage.
     */
    public Nuage(int x, int y, String icon, String type, Colors color) {
        super(x, y, icon, type, color);
        this.color = color;

        if (type.equals("Methane")) {
            setColor(Colors.YELLOW); 
        } else if (type.equals("Eau")) {
            setColor(Colors.BLUE);;
        }
    }
}


