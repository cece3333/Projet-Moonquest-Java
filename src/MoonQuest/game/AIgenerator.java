package game;

import java.util.Random;

import display.Board;
import pieces.Glace;
import pieces.Nuage;
import pieces.Piece;
import pieces.Vehicule;

public class AIgenerator {
    
    public static int[][] terrianDirections = {{0, 1}, {0, -1}, {-1, 0}, {1, 0}};


    public static String generateAISource() {
        Random random = new Random();
        int sourceX, sourceY;
    
        // Parcourez le plateau pour trouver les pièces appropriées
        Piece selectedPiece = null;
        do {
            sourceX = random.nextInt(Board.BOARD_SIZE);
            sourceY = random.nextInt(Board.BOARD_SIZE);
            selectedPiece = Board.board[sourceY][sourceX];
            // Vérifiez si la pièce est un véhicule avec un nuage de même type à côté
            if (selectedPiece instanceof Vehicule && logicAdjacentChoice(sourceX, sourceY)) {
                return String.format("%c%d", sourceX + 'A', sourceY + 1);
            } else if (selectedPiece instanceof Glace && logicAdjacentChoice(sourceX, sourceY)) {
                return String.format("%c%d", sourceX + 'A', sourceY + 1);
            }
        } while (!(selectedPiece != null && Board.currentPlayer.contains(selectedPiece)));
    
        // Transformation au format de coordonnées (ex: A1)
        return String.format("%c%d", sourceX + 'A', sourceY + 1);
    }
    
    
    // Méthode pour vérifier si un nuage de même type est adjacent à une pièce
    private static boolean logicAdjacentChoice(int sourceX, int sourceY) {
        Piece piece = Board.board[sourceY][sourceX];
        // Parcourez les directions terriennes autour de la pièce
        for (int[] direction : terrianDirections) {
            int nextX = sourceX + direction[0];
            int nextY = sourceY + direction[1];
            // Vérifiez si les coordonnées sont valides et si la case contient un nuage de même type
            if (nextX >= 0 && nextX < Board.BOARD_SIZE && nextY >= 0 && nextY < Board.BOARD_SIZE) {
                Piece adjacentPiece = Board.board[nextY][nextX];
                if (piece instanceof Vehicule && ((Vehicule) piece).canCapture(adjacentPiece)) {
                    System.out.println("TEST Un nuage de même type est adjacent à la pièce.");
                    return true;
                } else if (piece instanceof Glace && !(Board.currentPlayer.contains(adjacentPiece))) {
                    System.out.println("TEST Une pièce ennemie est adjacent à la pièce.");
                    return true;
                }
            }
        }
        return false;
    }
    
    //mettre dans board ?
    public static String generateAIDest(int sourceX, int sourceY) {
        Random random = new Random();
        int destX, destY;

        // Générer des coordonnées de destination aléatoires
        destX = random.nextInt(Board.BOARD_SIZE);
        destY = random.nextInt(Board.BOARD_SIZE);
    
        Piece selectedPiece = Board.board[sourceY][sourceX];
        while (!(selectedPiece.deplacementTerrestre(sourceX, sourceY, destX, destY) || 
                 (selectedPiece instanceof Vehicule && ((Vehicule) selectedPiece).isActive() && 
                 selectedPiece.deplacementAerien(sourceX, sourceY, destX, destY)))) {
            destX = random.nextInt(Board.BOARD_SIZE);
            destY = random.nextInt(Board.BOARD_SIZE);
        }
    
        // Transformation au format de coordonnées (ex: A1)
        return String.format("%c%d", destX + 'A', destY + 1);
    }
    
    
     public static String generateLogicAIDest(int sourceX, int sourceY) {
        Random random = new Random();
    
        Piece selectedPiece = Board.board[sourceY][sourceX];

        // Parcourir les directions possibles
        for (int[] direction : terrianDirections) {
            int nextX = sourceX + direction[0];
            int nextY = sourceY + direction[1];
    
            // Vérifier si la destination est valide
            if (nextX >= 0 && nextX < Board.BOARD_SIZE && nextY >= 0 && nextY < Board.BOARD_SIZE) {
                Piece obstacle = Board.board[nextY][nextX];
    
                // Vérifier si la case est vide ou si le mouvement est valide
                if (selectedPiece.deplacementTerrestre(sourceX, sourceY, nextX, nextY) || Game.isValidMove(sourceX, sourceY, nextX, nextY, Board.scoreJoueur1, Board.scoreJoueur2)) {
                    // Si la pièce est une Glace ou un véhicule non activé
                    if (selectedPiece instanceof Vehicule && !((Vehicule) selectedPiece).isActive()) {
                        //Si on peut capturer un nuage
                        if (obstacle != null && ((Vehicule) selectedPiece).canCapture(obstacle)) {
                            System.out.println("TEST Le véhicule peut capturer un nuage)");
                            return String.format("%c%d", nextX + 'A', nextY + 1);
                        } else if (obstacle == null) {
                            return String.format("%c%d", nextX + 'A', nextY + 1);
                        }
                    } else { 
                        if (selectedPiece instanceof Glace && obstacle != null && !(Board.currentPlayer.contains(obstacle))) {
                            System.out.println("TEST La glace va écrasé la pièce " + obstacle.getIcon());
                            return String.format("%c%d", nextX + 'A', nextY + 1);
                        }
                    }
                }
            }
        }
    
        // Aucun mouvement valide trouvé, retourner random
        return generateAIDest(sourceX, sourceY);
    }

}