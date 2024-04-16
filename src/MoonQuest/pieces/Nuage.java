package pieces;

import display.Board;
import utils.Colors;

public class Nuage extends Piece {

    public Nuage(int x, int y, String icon, String type, Colors color) {
        super(x, y, icon, type, color); // 'N' pour l'icône du nuage
        this.color = color; // Initialisation de la color dans le constructeur de Nuage

        // Assignation de la color en fonction du type de nuage
        if (type.equals("Methane")) {
            setColor(Colors.YELLOW); // Utilisation de la constante Colors.YELLOW
        } else if (type.equals("Eau")) {
            setColor(Colors.BLUE);; // Utilisation de la constante Colors.BLUE
        }
    }
}


