package pieces;
import display.Board;
import utils.Couleurs;

public class Glace extends Piece {
    public Glace(int x, int y, String icon, String type, Couleurs couleur) {
        super(x, y, icon, "None", couleur); // 'G' pour l'icône de la glace
    }

    public static boolean isGlaceMove(Piece piece, Piece destination, int sourceX, int sourceY, int destX, int destY) {
        if ((destination != null) && (!(Board.currentPlayer.contains(destination)))) {
            if (piece.deplacementTerrestre(sourceX, sourceY, destX, destY)) {
                System.out.println("La glace a écrasé la pièce " + destination.getIcon());
                Board.board[destY][destX] = null;
                } if (destination instanceof Vehicule) {
                    System.out.println("Véhicule détruit ; les nuages capturés sont perdus.");
                    if (Board.currentPlayer == Board.joueur1) {
                        Board.scoreJoueur2 -= ((Vehicule)destination).getNuagesCaptures();
                    } else {
                        Board.scoreJoueur1 -= ((Vehicule)destination).getNuagesCaptures();
                    }
                return true;
            }
        } if (Board.currentPlayer.contains(destination)) { 
            System.out.println("La glace ne peut pas écraser ses propres pièces.");
            return false;
        } else {
            return piece.deplacementTerrestre(sourceX, sourceY, destX, destY);
        }
    }
 
}
