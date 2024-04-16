package pieces;

import display.Board;
import utils.Colors;
import game.Game;

public class Glace extends Piece {
    public Glace(int x, int y, String icon, String type, Colors Color) {
        super(x, y, icon, "None", Color); // 'G' pour l'icône de la glace
    }

    public static boolean isGlaceMove(Piece piece, Piece destination, int sourceX, int sourceY, int destX, int destY) {
        if ((destination != null) && (!(Board.currentPlayer.contains(destination)))) {
            if (piece.deplacementTerrestre(sourceX, sourceY, destX, destY)) {
                System.out.println("La glace a écrasé la pièce " + destination.getIcon());
                Board.board[destY][destX] = null;
                } if (destination instanceof Vehicule) {
                    System.out.println("Véhicule détruit ; les nuages capturés sont perdus.");
                    if (Board.currentPlayer == Board.joueur1) {
                        Game.scoreJoueur2 -= ((Vehicule)destination).getNuagesCaptures();
                    } else {
                        Game.scoreJoueur1 -= ((Vehicule)destination).getNuagesCaptures();
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
