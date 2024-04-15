package pieces;
import display.Board;
import utils.Couleurs;

public class Nuage extends Piece {

    public Nuage(int x, int y, String icon, String type, Couleurs couleur) {
        super(x, y, icon, type, couleur); // 'N' pour l'icône du nuage
        this.couleur = couleur; // Initialisation de la couleur dans le constructeur de Nuage

        // Assignation de la couleur en fonction du type de nuage
        if (type.equals("Methane")) {
            setCouleur(Couleurs.YELLOW); // Utilisation de la constante Couleurs.YELLOW
        } else if (type.equals("Eau")) {
            setCouleur(Couleurs.BLUE);; // Utilisation de la constante Couleurs.BLUE
        }
    }
}


