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
        int adjustedX;
        int adjustedY;
        boolean isYBorder = (((sourceY == 1) && (destY == 15)) || ((sourceY == 1) && (destY == 15)) || ((sourceY == 14) && (destY == 0)) || ((sourceY == 0) && (destY == 14)));
        boolean isXBorder = (((sourceX == 1) && (destX == 15)) || ((sourceX == 15) && (destX == 1)) || ((sourceX == 14) && (destX == 0)) || ((sourceX == 0) && (destX == 14)));

        System.out.println("TEST (deplacementAerien) : activé x2");
        if (isXBorder) {
            adjustedX = Math.abs(16 % (destX - sourceX));
            System.out.println("TEST (deplacementAerien) adjustedX : " + adjustedX);
            return (adjustedX == 2 && destY == sourceY);
        } else if (isYBorder) {
            adjustedY = Math.abs(16 % (destY - sourceY));
            System.out.println("TEST (deplacementAerien) adjustedY: " + adjustedY);
            return (adjustedY == 2 && (destX == sourceX));
        } else {
            return ((Math.abs(destX - sourceX) == 2) && (destY == sourceY)) ||   // Mouvement horizontal
                   ((Math.abs(destY - sourceY)) == 2 && (destX == sourceX)) ||   // Mouvement vertical
                   (((destX - sourceX) == 2) && ((destY - sourceY) == 2));      // Mouvement diagonal
        }
    }
    
    

    @Override
    public boolean deplacementTerrestre(int sourceX, int sourceY, int destX, int destY) {  
        int adjustedX;
        int adjustedY;
        boolean isYBorder = ((destY == 0) && (sourceY == Board.board.length - 1)) || 
                            ((sourceY == 0) && (destY == Board.board.length - 1));
        boolean isXBorder = ((destX == 0) && (sourceX == Board.board.length - 1)) || 
                            ((sourceX == 0) && (destX == Board.board.length - 1));
        
        if (isXBorder) {
            adjustedX = Math.abs(16 % (destX - sourceX));
            System.out.println("TEST (deplacementTerrestre) adjustedX : " + adjustedX);
            System.out.println("bordure horizontale");
            return ((adjustedX == 1) && (destY == sourceY));
        } else if (isYBorder) {
            adjustedY = Math.abs(16 % (destY - sourceY));
            System.out.println("TEST (deplacementTerrestre) adjustedY: " + adjustedY);
            System.out.println("bordure verticale");
            return ((adjustedY == 1) && (destX == sourceX));
        } else {
            return ((Math.abs(destX - sourceX) == 1) && (destY == sourceY)) ||   // Mouvement horizontal
                   ((Math.abs(destY - sourceY) == 1) && (destX == sourceX)) &&   // Mouvement vertical
                   ((destX == sourceX) || (destY == sourceY));                   // Empêcher les mouvements diagonaux
        }
    }
       
}


