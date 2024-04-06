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
    public boolean deplacementAerien(int sourceX, int sourceY, int destX, int destY) {
        // Calculer la distance entre la source et la destination
        int deltaX = Math.abs(destX - sourceX);
        int deltaY = Math.abs(destY - sourceY);
    
        // Vérifier si la destination est à une distance valide pour un déplacement aérien
        return (deltaX == 2 && deltaY == 0) ||  // Déplacement horizontal
               (deltaX == 0 && deltaY == 2) ||  // Déplacement vertical
               (deltaX == 2 && deltaY == 2);    // Déplacement diagonal
    }

    @Override
    public boolean deplacementTerrestre(int sourceX, int sourceY, int destX, int destY) {
        // Vérifier si la destination est adjacente à la source (verticalement ou horizontalement) ; return true si oui
        
        // Ajuster les coordonnées de destination pour les mouvements de grille infinie
        int adjustedX;
        int adjustedY;
        
        if (Board.isXBorder(sourceX)) {
            adjustedX = Math.abs(16 % (destX - sourceX));
            System.out.println("TEST (deplacementTerrestre) adjustedX : " + adjustedX);
            return (adjustedX == 1 && destY == sourceY);
        } else if (Board.isYBorder(sourceY)) {
            adjustedY = Math.abs(16 % (destY - sourceY));
            System.out.println("TEST (deplacementTerrestre) adjustedY: " + adjustedY);
            return (adjustedY == 1 && destX == sourceX);
        } else {
            return (Math.abs(destX - sourceX) == 1 && destY == sourceY) ||   // Mouvement horizontal
                   (Math.abs(destY - sourceY) == 1 && destX == sourceX) &&   // Mouvement vertical
                   (destX == sourceX || destY == sourceY);                   // Empêcher les mouvements diagonaux
        }
    }
       
}


