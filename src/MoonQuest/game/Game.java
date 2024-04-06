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

    public static void playGame(boolean isSavedGame, int mode, int sourceX, int sourceY, int destX, int destY) {
        // Boucle principale du jeu
        while (true) {
            // Afficher le Board
            Board.printBoard();

            // Afficher le numéro de tour
            System.out.println("Tour " + Board.turn);

            //*** plutot dans le Main ?
            // Déterminer quel joueur doit jouer en fonction du numéro de tour
            if (Board.turn % 2 == 1) { // Tour impair : Joueur 1
                Board.currentPlayer = Board.joueur1;
                System.out.println("Tour du joueur 1 (CYAN)");
                System.out.println("Nombre total de nuages capturés : " + Board.scoreJoueur1);
            } else { // Tour pair : Joueur 2
                Board.currentPlayer = Board.joueur2;
                System.out.println("Tour du joueur 2 (PURPLE)");
                System.out.println("Nombre total de nuages capturés : " + Board.scoreJoueur2);
            }

            // Récupérer les coordonnées de la pièce à déplacer
            String source = "";
            if (mode == 1 || (mode == 2 && Board.turn % 2 == 1)) {
                System.out.print("Entrez les coordonnées de la pièce à déplacer (ou q pour quitter) : ");
                source = scanner.next().toUpperCase();
                if (source.equalsIgnoreCase("q")) {
                    if (isSavedGame) {
                        Save.readMovesFile("saved_moves.txt");
                    } else { // Sinon, c'est une nouvelle partie
                        Save.readMovesFile("new_moves.txt");
                    }
                    // Demander au joueur s'il souhaite sauvegarder avant de quitter
                    System.out.println("Voulez-vous sauvegarder avant de quitter ? (O/N)");
                    String saveInput = scanner.next();
                    if (saveInput.equalsIgnoreCase("O")) {
                        Save.saveGame(Board.board, Board.joueur2, Board.joueur1, Board.scoreJoueur1, Board.scoreJoueur2, Board.turn);
                        //Save.clearFile("saved_moves.txt");
                        try {
                                Files.copy(Paths.get("new_moves.txt"), Paths.get("saved_moves.txt"), StandardCopyOption.REPLACE_EXISTING);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                    } 
                    break; // Quitter le jeu
                } 
                sourceX = source.charAt(0) - 'A';
                sourceY = Integer.parseInt(source.substring(1)) - 1;
            } else if (mode == 2 || mode == 3) {
                source = generateAISource();
                System.out.println("L'IA a choisi la source : " + source);
                sourceX = source.charAt(0) - 'A';
                sourceY = Integer.parseInt(source.substring(1)) - 1;
            }

                //redondance avec le mouvement source AI (à régler)
                // Vérifier si la pièce sélectionnée appartient au joueur actuel
                Piece selectedPiece = Board.board[sourceY][sourceX];
                if (selectedPiece == null || !Board.currentPlayer.contains(selectedPiece)) {
                    System.out.println("La pièce sélectionnée n'appartient pas au joueur actuel. Réessayez.");
                    continue; // Revenir au début de la boucle pour redemander une pièce valide
                }

            // Récupérer les coordonnées de la destination
            String destination = "";
            if (mode == 1 || (mode == 2 && Board.turn % 2 == 1)) {
                System.out.print("Entrez les coordonnées de la destination (ex: B4): ");
                destination = scanner.next().toUpperCase();
            } else if (mode == 2 || mode == 3) {
                destination = generateAIDest(sourceX, sourceY);
                System.out.println("L'IA a choisi la destination : " + destination);
            }
            destX = destination.charAt(0) - 'A';
            destY = Integer.parseInt(destination.substring(1)) - 1;

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
            break;
        } 
    
            Board.moveClouds(); // Déplacer les nuages 
        }
        scanner.close(); // Fermer le scanner après utilisation
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
