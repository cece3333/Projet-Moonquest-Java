package game;

import display.Board;
import pieces.Glace;
import pieces.Piece;
import pieces.Vehicule;

import java.util.Random;

/**
 * Classe contenant des méthodes pour générer les mouvements de l'intelligence artificielle (IA) dans les modes 2 et 3 du jeu.
 */
public class AIgenerator {

    /** Tableau des directions terrestres pour le déplacement des pièces. */
    private static final int[][] TERRESTRIAL_DIRECTIONS = {{0, 1}, {0, -1}, {-1, 0}, {1, 0}};

    /**
     * Génère des coordonnées source logiques (ou aléatoires) pour le mouvement de l'IA.
     *
     * @return Les coordonnées source générées sous forme de chaîne.
     */
    public static String generateLogicAISource() {
        Random random = new Random();
        int sourceX, sourceY;

        Piece selectedPiece;
        do {
            sourceX = random.nextInt(Board.BOARD_SIZE);
            sourceY = random.nextInt(Board.BOARD_SIZE);
            selectedPiece = Board.board[sourceY][sourceX];
        } while (!(selectedPiece != null && Board.currentPlayer.contains(selectedPiece) && logicAdjacentChoice(selectedPiece, sourceX, sourceY)));

        return String.format("%c%d", sourceX + 'A', sourceY + 1);
    }

    /**
     * Vérifie si le choix de mouvement adjacent à la pièce est logique.
     *
     * @param piece    La pièce pour laquelle vérifier le choix de mouvement.
     * @param sourceX  L'abscisse de la position source.
     * @param sourceY  L'ordonnée de la position source.
     * @return true si le choix de mouvement adjacent est logique, sinon false.
     */
    private static boolean logicAdjacentChoice(Piece piece, int sourceX, int sourceY) {
        for (int[] direction : TERRESTRIAL_DIRECTIONS) {
            int nextX = sourceX + direction[0];
            int nextY = sourceY + direction[1];
            if (nextX >= 0 && nextX < Board.BOARD_SIZE && nextY >= 0 && nextY < Board.BOARD_SIZE) {
                Piece adjacentPiece = Board.board[nextY][nextX];
                if ((piece instanceof Vehicule && ((Vehicule) piece).canCapture(adjacentPiece)) ||
                        (piece instanceof Glace && !(Board.currentPlayer.contains(adjacentPiece)))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Génère les coordonnées destination aléatoires pour le mouvement de l'IA.
     *
     * @param sourceX L'abscisse de la position source.
     * @param sourceY L'ordonnée de la position source.
     * @return Les coordonnées destination générées sous forme de chaîne.
     */
    public static String generateAIDest(int sourceX, int sourceY) {
        Random random = new Random();
        int destX, destY;

        Piece selectedPiece = Board.board[sourceY][sourceX];
        do {
            destX = random.nextInt(Board.BOARD_SIZE);
            destY = random.nextInt(Board.BOARD_SIZE);
        } while (!(selectedPiece.terrestrialMove(sourceX, sourceY, destX, destY) ||
                (selectedPiece instanceof Vehicule && ((Vehicule) selectedPiece).isActive() && selectedPiece.aerialMove(sourceX, sourceY, destX, destY))));

        return String.format("%c%d", destX + 'A', destY + 1);
    }

    /**
     * Génère des coordonnées destination logiques pour le mouvement de l'IA.
     *
     * @param sourceX L'abscisse de la position source.
     * @param sourceY L'ordonnée de la position source.
     * @return Les coordonnées destination logiques générées sous forme de chaîne.
     */
    public static String generateLogicAIDest(int sourceX, int sourceY) {
        Piece selectedPiece = Board.board[sourceY][sourceX];

        for (int[] direction : TERRESTRIAL_DIRECTIONS) {
            int nextX = sourceX + direction[0];
            int nextY = sourceY + direction[1];
            if (nextX >= 0 && nextX < Board.BOARD_SIZE && nextY >= 0 && nextY < Board.BOARD_SIZE) {
                Piece obstacle = Board.board[nextY][nextX];
                if ((selectedPiece.terrestrialMove(sourceX, sourceY, nextX, nextY) ||
                        GameLogic.isValidMove(sourceX, sourceY, nextX, nextY, GameLogic.scoreJoueur1, GameLogic.scoreJoueur2)) &&
                        ((selectedPiece instanceof Vehicule && !((Vehicule) selectedPiece).isActive() && ((obstacle != null && ((Vehicule) selectedPiece).canCapture(obstacle)) || obstacle == null)) ||
                        (selectedPiece instanceof Glace && obstacle != null && !(Board.currentPlayer.contains(obstacle))))) {
                    return String.format("%c%d", nextX + 'A', nextY + 1);
                }
            }
        }
        return generateAIDest(sourceX, sourceY);
    }
}
