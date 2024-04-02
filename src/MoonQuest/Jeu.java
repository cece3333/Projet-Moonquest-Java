import java.util.Scanner;

public class Jeu {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Plateau2.startGame(); // Initialiser le jeu

        // Ajouter des nuages aléatoirement sur le Plateau2
        Plateau2.addClouds();

        // Commencer la partie
        playGame();
    }

    public static void playGame() {
        // Boucle principale du jeu
        while (true) {
            // Afficher le Plateau2
            Plateau2.printBoard();

            // Afficher le numéro de tour
            System.out.println("Tour " + Plateau2.turn);

            // Déterminer quel joueur doit jouer en fonction du numéro de tour
            if (Plateau2.turn % 2 == 1) { // Tour impair : Joueur 1
                Plateau2.currentPlayer = Plateau2.joueur1;
                System.out.println("Tour du joueur 1 (CYAN)");
                System.out.println("Nombre total de nuages capturés : " + Plateau2.scoreJoueur1);
            } else { // Tour pair : Joueur 2
                Plateau2.currentPlayer = Plateau2.joueur2;
                System.out.println("Tour du joueur 2 (PURPLE)");
                System.out.println("Nombre total de nuages capturés : " + Plateau2.scoreJoueur2);
            }

            // Récupérer les coordonnées de la pièce à déplacer
            System.out.print("Entrez les coordonnées de la pièce à déplacer: (ou q pour quitter) : ");
            String source = scanner.next().toUpperCase();

            // Vérifier si le joueur veut quitter la partie
            if (source.equalsIgnoreCase("q")) {
                // Demander au joueur s'il souhaite sauvegarder avant de quitter
                System.out.println("Voulez-vous sauvegarder avant de quitter ? (O/N)");
                String saveInput = scanner.next();
                if (saveInput.equalsIgnoreCase("O")) {
                    Plateau2.saveGame();
                }
                break; // Quitter le jeu
            }

            int sourceX = source.charAt(0) - 'A';
            int sourceY = Integer.parseInt(source.substring(1)) - 1;

            // Vérifier si la pièce sélectionnée appartient au joueur actuel
            Piece selectedPiece = Plateau2.board[sourceY][sourceX];
            if (selectedPiece == null || !Plateau2.currentPlayer.contains(selectedPiece)) {
                System.out.println("La pièce sélectionnée n'appartient pas au joueur actuel. Réessayez.");
                continue; // Revenir au début de la boucle pour redemander une pièce valide
            }

            // Récupérer les coordonnées de la destination
            System.out.print("Entrez les coordonnées de la destination (ex: B4): ");
            String destination = scanner.next().toUpperCase();
            int destX = destination.charAt(0) - 'A';
            int destY = Integer.parseInt(destination.substring(1)) - 1;

            if (Plateau2.isValidMove(sourceX, sourceY, destX, destY)) {
                // Effectuer le déplacement de la pièce
                Plateau2.movePiece(sourceX, sourceY, destX, destY);

                // Sauvegarder le coup joué dans le fichier sérialisable
                Plateau2.saveMoveToFile(source, destination, Plateau2.turn, Plateau2.scoreJoueur1, Plateau2.scoreJoueur2);

                // Incrémenter le numéro de tour
                Plateau2.turn++;
                 
                // Vérifier s'il y a un gagnant ou un match nul
                if (Plateau2.isGameOver(Plateau2.scoreJoueur1, Plateau2.scoreJoueur2)) {
                    // Afficher le résultat final
                    System.out.println("La partie est terminée. Résultat final : ...");
                    break;
                }
            } else {
                System.out.println("Déplacement invalide. Réessayez.");
            }
            Plateau2.moveClouds(); // Déplacer les nuages 
        } 
        scanner.close(); // Fermer le scanner après utilisation
    }
}
