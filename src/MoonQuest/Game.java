import java.util.Random;
import java.util.Scanner;

public class Game {
    private static final Scanner scanner = new Scanner(System.in);
    private static int sourceX;
    private static int sourceY;
    private static int destX;
    private static int destY;

    public static void playGame(boolean isSavedGame) {
        // Boucle principale du jeu
        while (true) {
            // Afficher le Plateau2
            Plateau2.printBoard();

            // Afficher le numéro de tour
            System.out.println("Tour " + Plateau2.turn);

            //*** plutot dans le Main ?
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
            System.out.print("Entrez les coordonnées de la pièce à déplacer (ou q pour quitter) : \n");
            String source = scanner.next().toUpperCase();

            // Vérifier si le joueur veut quitter la partie
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
                    Save.saveGame(Plateau2.board, Plateau2.joueur2, Plateau2.joueur1, Plateau2.scoreJoueur1, Plateau2.scoreJoueur2, Plateau2.turn);
                }
                break; // Quitter le jeu
            }
            //**

            sourceX = source.charAt(0) - 'A';
            sourceY = Integer.parseInt(source.substring(1)) - 1;

            // Vérifier si la pièce sélectionnée appartient au joueur actuel
            Piece selectedPiece = Plateau2.board[sourceY][sourceX];
            if (selectedPiece == null || !Plateau2.currentPlayer.contains(selectedPiece)) {
                System.out.println("La pièce sélectionnée n'appartient pas au joueur actuel. Réessayez.");
                continue; // Revenir au début de la boucle pour redemander une pièce valide
            }

            // Récupérer les coordonnées de la destination
            System.out.print("Entrez les coordonnées de la destination (ex: B4): ");
            String destination = scanner.next().toUpperCase();
            destX = destination.charAt(0) - 'A';
            destY = Integer.parseInt(destination.substring(1)) - 1;

            // Sauvegarder le coup joué dans le fichier .txt (a mettre avant le mouvement de la pièce)
            if (isSavedGame) {
                Save.saveMoveToFile(source, destination, Plateau2.turn, Plateau2.scoreJoueur1, Plateau2.scoreJoueur2, "saved_moves.txt");
            } else { // Sinon, c'est une nouvelle partie
                Save.saveMoveToFile(source, destination, Plateau2.turn, Plateau2.scoreJoueur1, Plateau2.scoreJoueur2, "new_moves.txt");
            }
        
            if (Plateau2.isValidMove(sourceX, sourceY, destX, destY, Plateau2.scoreJoueur1, Plateau2.scoreJoueur2)) {
                // Effectuer le déplacement de la pièce
                Plateau2.movePiece(sourceX, sourceY, destX, destY);
        
            // Incrémenter le numéro de tour (déplacé dans Main.java)
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

    public static int selectGameMode() {
        System.out.println("Veuillez sélectionner le mode de jeu :");
        System.out.println("1. Joueur contre Joueur");
        System.out.println("2. Joueur contre IA");
        System.out.println("3. IA contre IA");

        int mode = scanner.nextInt();
        scanner.nextLine(); // Pour consommer la nouvelle ligne

        return mode;
    }

    public static void generateAIMove() {
        Random random = new Random();
        int sourceX, sourceY, destX, destY;

        // Générer des coordonnées de pièce source et de destination aléatoires
        sourceX = random.nextInt(Plateau2.BOARD_SIZE);
        sourceY = random.nextInt(Plateau2.BOARD_SIZE);
        destX = random.nextInt(Plateau2.BOARD_SIZE);
        destY = random.nextInt(Plateau2.BOARD_SIZE);

        // Vérifier si le mouvement généré est valide
        while (!Plateau2.isValidMove(sourceX, sourceY, destX, destY, Plateau2.scoreJoueur1, Plateau2.scoreJoueur2)) {
            sourceX = random.nextInt(Plateau2.BOARD_SIZE);
            sourceY = random.nextInt(Plateau2.BOARD_SIZE);
            destX = random.nextInt(Plateau2.BOARD_SIZE);
            destY = random.nextInt(Plateau2.BOARD_SIZE);
        }

        // Effectuer le mouvement sur le plateau
        Plateau2.movePiece(sourceX, sourceY, destX, destY);
    }
}