package pieces;
import display.Board;
import utils.Couleurs;

public class Glace extends Piece {
    public Glace(int x, int y, String icon, String type, Couleurs couleur) {
        super(x, y, icon, "None", couleur); // 'G' pour l'icône de la glace
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
    public boolean deplacementTerrestre(int sourceX, int sourceY, int destX, int destY) {
        // Vérifier si la destination est adjacente à la source (verticalement ou horizontalement) ; return true si oui
        
        // Ajuster les coordonnées de destination pour les mouvements de grille infinie
        int adjustedX;
        int adjustedY;
        boolean isYBorder = ((destY == 0) && (sourceY == Board.board.length - 1)) || 
                            ((sourceY == 0) && (destY == Board.board.length - 1));
        boolean isXBorder = ((destX == 0) && (sourceX == Board.board.length - 1)) || 
                            ((sourceX == 0) && (destX == Board.board.length - 1));
        
        if (isXBorder) {
            adjustedX = Math.abs(16 % (destX - sourceX));
            return ((adjustedX == 1) && (destY == sourceY));
        } else if (isYBorder) {
            adjustedY = Math.abs(16 % (destY - sourceY));
            return ((adjustedY == 1) && (destX == sourceX));
        } else {
            return ((Math.abs(destX - sourceX) == 1) && (destY == sourceY)) ||   // Mouvement horizontal
                   ((Math.abs(destY - sourceY) == 1) && (destX == sourceX)) &&   // Mouvement vertical
                   ((destX == sourceX) || (destY == sourceY));                   // Empêcher les mouvements diagonaux
        }
    }

    @Override
    public boolean deplacementAerien(int sourceX, int sourceY, int destX, int destY) {
        // Ne rien faire dans cette méthode, car les glaces ne se déplacent pas en l'air
        return false; // Ou true, selon votre logique
    }

    public static boolean isIceMove(Piece piece, Piece destination, int sourceX, int sourceY, int destX, int destY) {
        if ((destination != null) && (!(Board.currentPlayer.contains(destination)))) {
            if (piece.deplacementTerrestre(sourceX, sourceY, destX, destY)) {
                System.out.println("La glace a écrasé la pièce " + destination.getIcon());
                Board.board[destY][destX] = null;
            }
        } if (Board.currentPlayer.contains(destination)) { 
            System.out.println("La glace ne peut pas écraser ses propres pièces.");
            return false;
        } if (destination instanceof Vehicule) {
            System.out.println("Véhicule détruit ; les nuages capturés sont perdus.");
            if (Board.currentPlayer == Board.joueur1) {
                Board.scoreJoueur2 -= ((Vehicule)destination).getNuagesCaptures();
            } else {
                Board.scoreJoueur1 -= ((Vehicule)destination).getNuagesCaptures();
            }
        } else {
            return piece.deplacementTerrestre(sourceX, sourceY, destX, destY);
        }
        return false;
    }
 
}
