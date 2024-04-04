import java.util.Random;
import java.util.Scanner;

public class Game {
    private static final Scanner scanner = new Scanner(System.in);

    public static void playGame(boolean isSavedGame, int mode, int sourceX, int sourceY, int destX, int destY) {
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
            String source = "";
            if (mode == 1 || (mode == 2 && Plateau2.turn % 2 == 1)) {
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
                        Save.saveGame(Plateau2.board, Plateau2.joueur2, Plateau2.joueur1, Plateau2.scoreJoueur1, Plateau2.scoreJoueur2, Plateau2.turn);
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
                Piece selectedPiece = Plateau2.board[sourceY][sourceX];
                if (selectedPiece == null || !Plateau2.currentPlayer.contains(selectedPiece)) {
                    System.out.println("La pièce sélectionnée n'appartient pas au joueur actuel. Réessayez.");
                    continue; // Revenir au début de la boucle pour redemander une pièce valide
                }

            // Récupérer les coordonnées de la destination
            String destination = "";
            if (mode == 1 || (mode == 2 && Plateau2.turn % 2 == 1)) {
                System.out.print("Entrez les coordonnées de la destination (ex: B4): ");
                destination = scanner.next().toUpperCase();
            } else if (mode == 2 || mode == 3) {
                destination = generateAIDest(sourceX, sourceY);
                System.out.println("L'IA a choisi la destination : " + destination);
            }
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

        } else {
                System.out.println("Déplacement invalide. Réessayez.");
            }
        
        // Vérifier s'il y a un gagnant ou un match nul
        if (Plateau2.isGameOver(Plateau2.scoreJoueur1, Plateau2.scoreJoueur2)) {
            // Afficher le résultat final
            Plateau2.printBoard();
            System.out.println("La partie est terminée. Résultats finaux : \n");
            System.out.println("Score du joueur 1 : " + Plateau2.scoreJoueur1 + "\nScore du joueur 2 : " + Plateau2.scoreJoueur2 + "\n");
            if (isSavedGame) {
                Save.readMovesFile("saved_moves.txt");
            } else { // Sinon, c'est une nouvelle partie
                Save.readMovesFile("new_moves.txt");
            }
            break;
        } 
    
            Plateau2.moveClouds(); // Déplacer les nuages 
        }
        scanner.close(); // Fermer le scanner après utilisation
    }

    public static String generateAISource() {
        Random random = new Random();
        int sourceX, sourceY;
    
        // Générer des coordonnées de pièce source aléatoires
        sourceX = random.nextInt(Plateau2.BOARD_SIZE);
        sourceY = random.nextInt(Plateau2.BOARD_SIZE);
    
        Piece selectedPiece = Plateau2.board[sourceY][sourceX];
    
        // Vérifier que la pièce appartient bien au joueur
        while (!(selectedPiece != null && Plateau2.currentPlayer.contains(selectedPiece))) {
            sourceX = random.nextInt(Plateau2.BOARD_SIZE);
            sourceY = random.nextInt(Plateau2.BOARD_SIZE);
            selectedPiece = Plateau2.board[sourceY][sourceX];
        }
    
        // Transformation au format de coordonnées (ex: A1)
        return String.format("%c%d", sourceX + 'A', sourceY + 1);
    }
    
    public static String generateAIDest(int sourceX, int sourceY) {
        Random random = new Random();
        int destX, destY;
    
        // Générer des coordonnées de destination aléatoires
        destX = random.nextInt(Plateau2.BOARD_SIZE);
        destY = random.nextInt(Plateau2.BOARD_SIZE);
    
        Piece selectedPiece = Plateau2.board[sourceY][sourceX];
        while (!(selectedPiece.deplacementTerrestre(sourceX, sourceY, destX, destY) || 
                 (selectedPiece instanceof Vehicule && ((Vehicule) selectedPiece).getState() && 
                 selectedPiece.deplacementAerien(sourceX, sourceY, destX, destY)))) {
            destX = random.nextInt(Plateau2.BOARD_SIZE);
            destY = random.nextInt(Plateau2.BOARD_SIZE);
        }
    
        // Transformation au format de coordonnées (ex: A1)
        return String.format("%c%d", destX + 'A', destY + 1);
    }
}
