package game;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Random;
import java.util.Scanner;

import display.Board;
import pieces.Nuage;
import pieces.Piece;
import pieces.Vehicule;
import utils.Save;

public class Game {
    private static final Scanner scanner = new Scanner(System.in);

    public static void moveClouds() {
        Random random = new Random();
    
        for (int y = 0; y < Board.board.length; y++) {
            for (int x = 0; x < Board.board[y].length; x++) {
                if (Board.board[y][x] instanceof Nuage) {
                    int randomNumber = random.nextInt(5);
                    if (randomNumber == 0) {
                        int direction = random.nextInt(4);
                        int destX = x, destY = y;
    
                        switch (direction) {
                            case 0: // Haut
                                destY -= 2;
                                break;
                            case 1: // Bas
                                destY += 2;
                                break;
                            case 2: // Gauche
                                destX -= 2;
                                break;
                            case 3: // Droite
                                destX += 2;
                                break;
                        }
    
                        // Ajuster les coordonnées de destination pour qu'elles restent dans les limites de la grille
                        destX = (destX + Board.board[0].length) % Board.board[0].length;
                        destY = (destY + Board.board.length) % Board.board.length;
    
                        if ((!Board.isGlaceBetween(x, y, destX, destY) && Board.board[destY][destX] == null)) {
                            Board.board[destY][destX] = Board.board[y][x];
                            Board.board[y][x] = null;
                        }
                    }
                }
            }
        }
    }
    public static int selectGameMode() {
        System.out.println("Veuillez sélectionner le mode de jeu :");
        System.out.println("1. Joueur contre Joueur");
        System.out.println("2. Joueur contre IA");
        System.out.println("3. IA contre IA");

        int mode = scanner.nextInt();
        scanner.nextLine(); // Pour consommer la nouvelle ligne

        return mode;
    }
    
    public static boolean isGameOver(int scoreJoueur1, int scoreJoueur2) {

        // Initialiser le compteur de nuages restants
        int countClouds = 0;
    
        // Vérifier s'il n'y a plus de nuages à capturer
        for (Piece[] row : Board.board) {
            for (Piece piece : row) {
                if (piece instanceof Nuage) {
                    countClouds++;
                }
            }
        }
        System.out.println("Nombre de nuages restants : " + countClouds);
        if (countClouds < 0) {
            System.out.println("Il n'y a plus de nuages à capturer.");
            return true;
        }
    
        // Vérifier s'il y a un gagnant (soit par score > 15, soit car plus de nuages à capturer)
        if (scoreJoueur1 >= 16 || scoreJoueur2 >= 16 || countClouds == 0) {
            System.out.println("La partie est terminée.");
            if (scoreJoueur1 > scoreJoueur2) {
                System.out.println("Le joueur 1 (CYAN) a remporté la partie avec " + scoreJoueur1 + " nuages capturés.");
            } else if (scoreJoueur1 < scoreJoueur2) {
                System.out.println("Le joueur 2 (PURPLE) a remporté la partie avec " + scoreJoueur2 + " nuages capturés.");
            } else if (((scoreJoueur1 == scoreJoueur2) && (scoreJoueur1 + scoreJoueur2 == 30)) || countClouds == 0) {
                System.out.println("Match nul !");
            }
            return true;
        }
        System.out.println("La partie continue.");
    
        return false;
    }

    public static String generateAISource() {
        Random random = new Random();
        int sourceX, sourceY;
    
        // Générer des coordonnées de pièce source aléatoires
        sourceX = random.nextInt(Board.BOARD_SIZE);
        sourceY = random.nextInt(Board.BOARD_SIZE);
    
        Piece selectedPiece = Board.board[sourceY][sourceX];
    
        // Vérifier que la pièce appartient bien au joueur
        while (!(selectedPiece != null && Board.currentPlayer.contains(selectedPiece))) {
            sourceX = random.nextInt(Board.BOARD_SIZE);
            sourceY = random.nextInt(Board.BOARD_SIZE);
            selectedPiece = Board.board[sourceY][sourceX];
        }
    
        // Transformation au format de coordonnées (ex: A1)
        return String.format("%c%d", sourceX + 'A', sourceY + 1);
    }
    


    public static String generateAIDest(int sourceX, int sourceY) {
        Random random = new Random();
        int destX, destY;
    
        // Générer des coordonnées de destination aléatoires
        destX = random.nextInt(Board.BOARD_SIZE);
        destY = random.nextInt(Board.BOARD_SIZE);
    
        Piece selectedPiece = Board.board[sourceY][sourceX];
        while (!(selectedPiece.deplacementTerrestre(sourceX, sourceY, destX, destY) || 
                 (selectedPiece instanceof Vehicule && ((Vehicule) selectedPiece).getState() && 
                 selectedPiece.deplacementAerien(sourceX, sourceY, destX, destY)))) {
            destX = random.nextInt(Board.BOARD_SIZE);
            destY = random.nextInt(Board.BOARD_SIZE);
        }
    
        // Transformation au format de coordonnées (ex: A1)
        return String.format("%c%d", destX + 'A', destY + 1);
    }
}
