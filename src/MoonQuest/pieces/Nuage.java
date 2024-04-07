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

    @Override
    public Couleurs getCouleur() {
        return this.couleur;
    }

    @Override
    public String getIcon() {
        return this.icon;
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public boolean deplacementTerrestre(int sourceX, int sourceY, int destX, int destY) {
        // Ne rien faire dans cette méthode, car les nuages ne se déplacent pas en l'air
        return false; // Ou true, selon votre logique
    }


    @Override
    public boolean deplacementAerien(int sourceX, int sourceY, int destX, int destY) {
        // Ajuster les coordonnées de destination pour les mouvements de grille infinie
        int adjustedX;
        int adjustedY;
        boolean isYBorder = (((sourceY == 1) && (destY == 15)) || 
                             ((sourceY == 1) && (destY == 15)) || 
                             ((sourceY == 14) && (destY == 0)) || 
                             ((sourceY == 0) && (destY == 14)));
        boolean isXBorder = (((sourceX == 1) && (destX == 15)) || 
                             ((sourceX == 15) && (destX == 1)) || 
                             ((sourceX == 14) && (destX == 0)) || 
                             ((sourceX == 0) && (destX == 14)));

        if (isXBorder) {
            adjustedX = Math.abs(16 % (destX - sourceX));
            return (adjustedX == 2 && destY == sourceY);
        } else if (isYBorder) {
            adjustedY = Math.abs(16 % (destY - sourceY));
            return (adjustedY == 2 && (destX == sourceX));
        } else {
            return ((Math.abs(destX - sourceX) == 2) && (destY == sourceY)) ||   // Mouvement horizontal
                   ((Math.abs(destY - sourceY)) == 2 && (destX == sourceX)) ||   // Mouvement vertical
                   (((Math.abs(destX - sourceX)) == 2) && ((Math.abs(destY - sourceY) == 2)));      // Mouvement diagonal
        }
    }
       
}


