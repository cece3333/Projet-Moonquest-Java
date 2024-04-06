package game;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Random;
import java.util.Scanner;

import display.Board;
import pieces.Piece;
import pieces.Vehicule;
import utils.Save;

public class Game {
    private static final Scanner scanner = new Scanner(System.in);

    /* 
    public static void playGame(boolean isSavedGame, int mode, int sourceX, int sourceY, int destX, int destY) {
        // Sauvegarder le coup joué dans le fichier .txt (a mettre avant le mouvement de la pièce)
        if (Board.isValidMove(sourceX, sourceY, destX, destY, destX, destY)) {
            if (isSavedGame) {
                Save.saveMoveToFile(source, destination, Board.turn, Board.scoreJoueur1, Board.scoreJoueur2, "saved_moves.txt");
            } else { // Sinon, c'est une nouvelle partie
                Save.saveMoveToFile(source, destination, Board.turn, Board.scoreJoueur1, Board.scoreJoueur2, "new_moves.txt");
            }
            //bouger la pièce si isValidMove: 
            Board.movePiece(sourceX, sourceY, destX, destY);
        // Incrémenter le numéro de tour (déplacé dans Main.java)
        Board.turn++;

    } else {
            System.out.println("Déplacement invalide. Réessayez.");
        }
    
    // Vérifier s'il y a un gagnant ou un match nul
    if (Board.isGameOver(Board.scoreJoueur1, Board.scoreJoueur2)) {
        // Afficher le résultat final
        Board.printBoard();
        System.out.println("La partie est terminée. Résultats finaux : \n");
        System.out.println("Score du joueur 1 : " + Board.scoreJoueur1 + "\nScore du joueur 2 : " + Board.scoreJoueur2 + "\n");
        if (isSavedGame) {
            Save.readMovesFile("saved_moves.txt");
        } else { // Sinon, c'est une nouvelle partie
            Save.readMovesFile("new_moves.txt");
        }
    }
    }
*/
    public static int selectGameMode() {
        System.out.println("Veuillez sélectionner le mode de jeu :");
        System.out.println("1. Joueur contre Joueur");
        System.out.println("2. Joueur contre IA");
        System.out.println("3. IA contre IA");

        int mode = scanner.nextInt();
        scanner.nextLine(); // Pour consommer la nouvelle ligne

        return mode;
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
