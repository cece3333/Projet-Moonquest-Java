import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

import display.Board;
import game.Game;
import pieces.Piece;
import pieces.Vehicule;
import pieces.Glace;
import pieces.Nuage;
import utils.Save;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static int sourceX, sourceY, destX, destY;

    public static void main(String[] args) {
        // Demander au joueur s'il veut commencer une nouvelle partie ou reprendre la partie précédente
        System.out.println("Voulez-vous commencer une nouvelle partie (N) ou reprendre la partie précédente (R) ?");
        String startChoice = scanner.next();
        boolean isSavedGame = startChoice.equalsIgnoreCase("R");

        int mode = Game.selectGameMode();

        if (isSavedGame) {
            // Charger la partie précédente depuis le fichier game.ser
            Save.loadGame(Board.joueur1, Board.joueur2);
            //Game.playGame(isSavedGame, mode, sourceX, sourceY, destX, destY);
        } else if (startChoice.equalsIgnoreCase("N")) {
            // Si le joueur choisit de commencer une nouvelle partie, démarrer le jeu et initialiser le plateau
            Board.initializeBoard();
            Board.addClouds(); // Ajouter des nuages aléatoirement sur le plateau
            Save.clearFile("new_moves.txt"); // Vider le fichier new_moves.txt
        }

        while (true) {
                // Afficher le Board
                Board.printBoard();
                //System.out.println("TEST : taille du board : " + Board.board.length);

                // Afficher le numéro de tour
                System.out.println("Tour " + Board.turn);

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
                        if (saveInput.equalsIgnoreCase("O") && !isSavedGame) {
                            Save.saveGame(Board.board, Board.joueur2, Board.joueur1, Board.scoreJoueur1, Board.scoreJoueur2, Board.turn);
                            //Si partie sauvegardée, copier le contenu de new_moves.txt dans saved_moves.txt
                            try {
                                Files.copy(Paths.get("new_moves.txt"), Paths.get("saved_moves.txt"), StandardCopyOption.REPLACE_EXISTING);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else if (saveInput.equalsIgnoreCase("O") && isSavedGame) {
                            Save.saveGame(Board.board, Board.joueur2, Board.joueur1, Board.scoreJoueur1, Board.scoreJoueur2, Board.turn);
                        }
                        break; // Quitter le jeu
                    }
                    sourceX = source.charAt(0) - 'A';
                    sourceY = Integer.parseInt(source.substring(1)) - 1;
                } else if (mode == 2 || mode == 3) {
                    source = Game.generateAISource();
                    System.out.println("L'IA a choisi la source : " + source);
                    sourceX = source.charAt(0) - 'A';
                    sourceY = Integer.parseInt(source.substring(1)) - 1;
                }

                // Vérifier si la pièce sélectionnée appartient au joueur actuel
                Piece selectedPiece = Board.board[sourceY][sourceX];
                if (selectedPiece == null || !Board.currentPlayer.contains(selectedPiece)) {
                    System.out.println("Sélection invalide. Réessayez.");
                    continue; // Revenir au début de la boucle pour redemander une pièce valide
                }
            
                // Récupérer les coordonnées de la destination
                String destination = "";
                if (mode == 1 || (mode == 2 && Board.turn % 2 == 1)) {
                    System.out.print("Entrez les coordonnées de la destination (ex: B4): ");
                    destination = scanner.next().toUpperCase();
                } else if (mode == 2 || mode == 3) {
                    destination = Game.generateAIDest(sourceX, sourceY);
                    System.out.println("L'IA a choisi la destination : " + destination);
                }
                destX = destination.charAt(0) - 'A';
                destY = Integer.parseInt(destination.substring(1)) - 1;

                // Déplacer la pièce
                if (Board.isValidMove(sourceX, sourceY, destX, destY, Board.scoreJoueur1, Board.scoreJoueur2)) {
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
            if (Game.isGameOver(Board.scoreJoueur1, Board.scoreJoueur2)) {
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
}
