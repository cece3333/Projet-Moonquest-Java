
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

import display.Board;
import game.AIgenerator;
import game.Game;
import pieces.Glace;
import pieces.Nuage;
import pieces.Piece;
import pieces.Vehicule;
import utils.Save;
import utils.Colors;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static int sourceX, sourceY, destX, destY;

    public static void main(String[] args) {

        System.out.println("Bienvenue dans MoonQuest !");
        System.out.println("Les informations concernant les règles du jeu sont disponibles dans le fichier README.md.\n");
        // Demander au joueur s'il veut commencer une nouvelle partie ou reprendre la partie précédente
        System.out.println("Voulez-vous commencer une nouvelle partie (N) ou reprendre la partie précédente (R) ?");
        String startChoice = scanner.next();
        boolean isSavedGame = startChoice.equalsIgnoreCase("R");

        System.out.println("Veuillez sélectionner le mode de jeu :");
        System.out.println("1. Joueur contre Joueur");
        System.out.println("2. Joueur contre IA");
        System.out.println("3. IA contre IA");

        int mode = scanner.nextInt();
        scanner.nextLine(); // Pour consommer la nouvelle ligne

        if (isSavedGame) {
            // Charger la partie précédente depuis le fichier game.ser
            Save.loadGame(Board.joueur1, Board.joueur2);
            Save.clearFile("utils/new_game_moves.txt");
            //modif:
            try {
                Files.copy(Paths.get("utils/saved_moves.txt"), Paths.get("utils/new_moves.txt"), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //Game.playGame(isSavedGame, mode, sourceX, sourceY, destX, destY);
        } else if (startChoice.equalsIgnoreCase("N")) {
            // Si le joueur choisit de commencer une nouvelle partie, démarrer le jeu et initialiser le plateau
            Board.initializeBoard();
            Board.addClouds(); // Ajouter des nuages aléatoirement sur le plateau
            Save.clearFile("utils/new_game_moves.txt"); // Vider le fichier new_game_moves.txt
        }

        while (true) {
                // Afficher le Board
                Board.printBoard();

                // Afficher le numéro de tour
                System.out.println("Tour " + Game.turn);

                // Déterminer quel joueur doit jouer en fonction du numéro de tour
                if (Game.turn % 2 == 1) { // Tour impair : Joueur 1
                    Board.currentPlayer = Board.joueur1;
                    System.out.println("Tour du joueur 1 (CYAN)");
                    System.out.println("Nombre total de nuages capturés : " + Game.scoreJoueur1);
                } else { // Tour pair : Joueur 2
                    Board.currentPlayer = Board.joueur2;
                    System.out.println("Tour du joueur 2 (PURPLE)");
                    System.out.println("Nombre total de nuages capturés : " + Game.scoreJoueur2);
                }

                // Récupérer les coordonnées de la pièce à déplacer
                String source = "";
                if (mode == 1 || (mode == 2 && Game.turn % 2 == 1)) {
                    System.out.print("Entrez les coordonnées de la pièce à déplacer (ou q pour quitter) : ");
                    source = scanner.next().toUpperCase();
                    if (source.equalsIgnoreCase("q")) {
                        if (isSavedGame) {
                            Save.readFiles("utils/new_moves.txt");
                        } else { // Sinon, c'est une nouvelle partie
                            Save.readFiles("utils/new_game_moves.txt"); //idem, effacer si les modifs fonctionnent
                        }
        
                        // Demander au joueur s'il souhaite sauvegarder avant de quitter
                        System.out.println("Voulez-vous sauvegarder avant de quitter ? (O/N)");
                        String saveInput = scanner.next();
                        if (saveInput.equalsIgnoreCase("O")&& (!isSavedGame)) {
                            Save.saveGame(Board.board, Board.joueur2, Board.joueur1, Game.scoreJoueur1, Game.scoreJoueur2, Game.turn);
                            //Si partie sauvegardée, copier le contenu de new_moves.txt dans saved_moves.txt
                            try {
                                Files.copy(Paths.get("utils/new_game_moves.txt"), Paths.get("utils/saved_moves.txt"), StandardCopyOption.REPLACE_EXISTING); //changer en saved_moves.txt
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else if ((saveInput.equalsIgnoreCase("O")) && (isSavedGame)) {
                            Save.saveGame(Board.board, Board.joueur2, Board.joueur1, Game.scoreJoueur1, Game.scoreJoueur2, Game.turn);
                            try {
                                Files.copy(Paths.get("utils/new_moves.txt"), Paths.get("utils/saved_moves.txt"), StandardCopyOption.REPLACE_EXISTING); //changer en saved_moves.txt
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        break; // Quitter le jeu
                    }
                    sourceX = source.charAt(0) - 'A';
                    sourceY = Integer.parseInt(source.substring(1)) - 1;
                } else if (mode == 2 || mode == 3) {
                    source = AIgenerator.generateAISource();
                    System.out.println("L'IA a choisi la source : " + source);
                    sourceX = source.charAt(0) - 'A';
                    sourceY = Integer.parseInt(source.substring(1)) - 1;
                }

                
                Piece selectedPiece = Board.board[sourceY][sourceX];
                Piece destinationPiece = Board.board[destY][destX];
                
                // Vérifier si la pièce sélectionnée appartient au joueur actuel
                if (selectedPiece == null || !Board.currentPlayer.contains(selectedPiece)) {
                    System.out.println("Sélection invalide. Réessayez.");
                    continue; // Revenir au début de la boucle pour redemander une pièce valide
                }
            
                // Récupérer les coordonnées de la destination
                String destination = "";
                if (mode == 1 || (mode == 2 && Game.turn % 2 == 1)) {
                    System.out.print("Entrez les coordonnées de la destination (ex: B4): ");
                    destination = scanner.next().toUpperCase();
                } else if (mode == 2 || mode == 3) {
                    destination = AIgenerator.generateLogicAIDest(sourceX, sourceY);
                    System.out.println("L'IA a choisi la destination : " + destination);
                }
                destX = destination.charAt(0) - 'A';
                destY = Integer.parseInt(destination.substring(1)) - 1;

                if (mode == 3) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // Déplacer la pièce
                if (Game.isValidMove(sourceX, sourceY, destX, destY, Game.scoreJoueur1, Game.scoreJoueur2)) {
                    if (isSavedGame) {
                        Save.saveMoveToFile(source, destination, Game.turn, Game.scoreJoueur1, Game.scoreJoueur2, "utils/new_moves.txt"); //saved_moves.txt
                    } else { // Sinon, c'est une nouvelle partie
                        Save.saveMoveToFile(source, destination, Game.turn, Game.scoreJoueur1, Game.scoreJoueur2, "utils/new_game_moves.txt");
                    }
                    //bouger la pièce si isValidMove: 
                    Board.movePiece(sourceX, sourceY, destX, destY);
                // Incrémenter le numéro de tour (déplacé dans Main.java)
                Game.turn++;
        
            } else {
                    System.out.println("Déplacement invalide. Réessayez.");
                }
            
            // Vérifier s'il y a un gagnant ou un match nul
            if (Game.isGameOver(Game.scoreJoueur1, Game.scoreJoueur2)) {
                // Afficher le résultat final
                Board.printBoard();
                System.out.println("La partie est terminée. Résultats finaux : \n");
                System.out.println("Score du joueur 1 : " + Game.scoreJoueur1 + "\nScore du joueur 2 : " + Game.scoreJoueur2 + "\n");
                if (isSavedGame) {
                    Save.readFiles("utils/new_moves.txt"); //used to be saved_moves.txt
                } else { // Sinon, c'est une nouvelle partie
                    Save.readFiles("utils/new_game_moves.txt"); 
                }
                break;
            }   
            Board.moveCloudsRandomly(); // Déplacer les nuages 
        }
        scanner.close(); // Fermer le scanner après utilisation
    }
} 
