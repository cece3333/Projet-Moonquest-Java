import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

import display.Board;
import game.Game;
import utils.Save;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static int sourceX, sourceY, destX, destY;

    public static void main(String[] args) {
        // Demander au joueur s'il veut commencer une nouvelle partie ou reprendre la partie précédente
        System.out.println("Voulez-vous commencer une nouvelle partie (N) ou reprendre la partie précédente (R) ?");
        String startChoice = scanner.next();
        boolean isSavedGame = startChoice.equalsIgnoreCase("R");

        int mode = selectGameMode();

        while (true) {
            System.out.print("Tape pour commencer, q pour quitter : ");
            String quit = scanner.next();
            if (quit.equalsIgnoreCase("q")) {
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
                    if (startChoice.equalsIgnoreCase("N")) {
                        Save.clearFile("saved_moves.txt");
                    }
                    try {
                        Files.copy(Paths.get("new_moves.txt"), Paths.get("saved_moves.txt"), StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break; // Quitter le jeu
            } else {
                if (isSavedGame) {
                    // Charger la partie précédente depuis le fichier game.ser
                    Save.loadGame(Board.joueur1, Board.joueur2);
                    Game.playGame(isSavedGame, mode, sourceX, sourceY, destX, destY);
                } else if (startChoice.equalsIgnoreCase("N")) {
                    // Si le joueur choisit de commencer une nouvelle partie, démarrer le jeu et initialiser le plateau
                    Board.initializeBoard();
                    Board.addClouds(); // Ajouter des nuages aléatoirement sur le plateau
                    Save.clearFile("new_moves.txt"); // Vider le fichier new_moves.txt
                    Game.playGame(isSavedGame, mode, sourceX, sourceY, destX, destY);
                }
            }
        }

        // Fermer le scanner après avoir fini d'utiliser les entrées de l'utilisateur
        scanner.close();
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
}
