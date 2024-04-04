//importations
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    //créer ici le menu d'affichage du jeu

    public static void main(String[] args) {
        // Demander au joueur s'il veut commencer une nouvelle partie ou reprendre la partie précédente
        System.out.println("Voulez-vous commencer une nouvelle partie (N) ou reprendre la partie précédente (R) ?");
        String startChoice = scanner.next();
        Boolean isSavedGame = startChoice.equalsIgnoreCase("R"); 
    
        // Si le joueur choisit de reprendre la partie précédente
        if (isSavedGame) {
            // Charger la partie précédente depuis le fichier game.ser
            Save.loadGame(Plateau2.joueur1, Plateau2.joueur2);
        } else {
            // Si le joueur choisit de commencer une nouvelle partie, démarrer le jeu et initialiser le plateau
            Plateau2.initializeBoard();
            Plateau2.addClouds(); // Ajouter des nuages aléatoirement sur le plateau
            clearFile("new_moves.txt"); // Vider le fichier new_moves.txt
        }
    
        // Commencer la partie
        Game.playGame(isSavedGame);
        //ajouter dans isGameOver l'affichage des score du fichier txt (même logique que dans playGame)
    }

        // Méthode pour vider le contenu d'un fichier
    private static void clearFile(String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(""); // Écrire une chaîne vide pour vider le fichier
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
