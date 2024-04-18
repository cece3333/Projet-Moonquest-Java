package pieces;

import utils.Colors;

/**
 * Représente une case de glace sur le plateau de jeu.
 * Hérite de la classe abstraite Piece.
 */
public class Glace extends Piece {

    /**
     * Constructeur de la classe Glace.
     *
     * @param x     Position en abscisse de la glace sur le plateau.
     * @param y     Position en ordonnée de la glace sur le plateau.
     * @param icon  Icône représentant la glace.
     * @param type  Type de la glace (fixé à "None").
     * @param color Couleur de la glace.
     */
    public Glace(int x, int y, String icon, String type, Colors color) {
        super(x, y, icon, "None", color);
    }
}
