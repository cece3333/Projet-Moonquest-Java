//importations
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    //créer ici le menu d'affichage du jeu

    public static void main(String[] args) {
        // Demander au joueur s'il veut commencer une nouvelle partie ou reprendre la partie précédente
        System.out.println("Voulez-vous commencer une nouvelle partie (N) ou reprendre la partie précédente (R) ?");
        String startChoice = scanner.next();
    
        // Si le joueur choisit de reprendre la partie précédente
        if (startChoice.equalsIgnoreCase("R")) {
            // Charger la partie précédente depuis le fichier game.ser
            Save.loadGame(Plateau2.turn, Plateau2.scoreJoueur1, Plateau2.scoreJoueur2, Plateau2.joueur1, Plateau2.joueur2);
        } else {
            // Si le joueur choisit de commencer une nouvelle partie, démarrer le jeu et initialiser le plateau
            Plateau2.initializeBoard();
            Plateau2.addClouds(); // Ajouter des nuages aléatoirement sur le plateau
        }
    
        // Commencer la partie
        Game.playGame();
    }
    
}
