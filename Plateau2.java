
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Plateau2 {
    public static ArrayList<Piece> joueur2 = new ArrayList<Piece>();
    public static ArrayList<Piece> joueur1 = new ArrayList<Piece>();
    public static ArrayList<Piece> currentPlayer;

    public static Piece board[][] = new Piece[16][16];

    static Scanner scanner = new Scanner(System.in);

    //on déclare turn comme une variable de cette classe :
    static int turn = 1;
    static int scoreJoueur1 = 0;
    static int scoreJoueur2 = 0;

    public static void main(String[] args) {
        // Démarrer le jeu et initialiser le plateau
        startGame();

        // Ajouter des nuages aléatoirement sur le plateau
        addClouds();

        // Commencer la partie
        playGame();
    }

    static void printBoard() {
        System.out.println("      A    B    C    D    E    F    G    H    I    J    K    L    M    N    O    P");
        System.out.println("  ---------------------------------------------------------------------------------");
        
        int count = 1;
        for (int i = 0; i < 16; i++) {
            System.out.print(count + " ");
            System.out.print("|  ");
            
            for (int j = 0; j < 16; j++) {
                if (board[i][j] == null) {
                    System.out.print("  |  ");
                } else {
                    String pieceIcon = board[i][j].getIcon();
                    Couleurs pieceColor = board[i][j].getCouleur();
                    
                    // Récupérer le code ANSI de la couleur de la pièce
                    String colorCode = pieceColor.getCode();
                    
                    // Afficher la pièce avec la couleur appropriée
                    System.out.print(colorCode + pieceIcon + Couleurs.RESET.getCode() + " | ");
                }
            }
            
            System.out.print(count);
            count++;
            System.out.println();
            System.out.println("  ---------------------------------------------------------------------------------");
        }
        System.out.println("      A    B    C    D    E    F    G    H    I    J    K    L    M    N    O    P");
    }
    
    
    // glace (row 1)
    static void startGame() {
        System.out.println("Ajout des pièces sur le plateau :");
        
        // Initialisation des pièces du joueur 1
        
        // Boucle pour initialiser les pièces de glace sur la première ligne
        for (int i = 0; i < 8; i++) {
            Glace glace = new Glace(2 * i, 0, "G1", "None", Couleurs.CYAN);
            setPiece(2 * i, 0, glace);
            joueur1.add(glace); // Ajoute la pièce à l'arraylist du joueur 1
        }
        
        // Initialisation des pièces de véhicule sur la deuxième ligne
        String[] vehicleTypes = {"Methane", "Water", "Methane", "Water", "Methane", "Water", "Methane"};
        for (int i = 0; i < 7; i++) {
            Vehicule vehicule = new Vehicule(2 * i + 1, 1, "V1", vehicleTypes[i], (vehicleTypes[i].equals("Methane")) ? Couleurs.YELLOW : Couleurs.BLUE, false);
            setPiece(2 * i + 1, 1, vehicule);
            joueur1.add(vehicule); // Ajoute la pièce à l'arraylist du joueur 1
        }
    
        // Boucle pour initialiser les pièces de glace sur la deuxième ligne
        for (int i = 0; i < 8; i++) {
            Glace glace = new Glace(2 * i, 2, "G1", "None", Couleurs.CYAN);
            setPiece(2 * i, 2, glace);
            joueur1.add(glace); // Ajoute la pièce à l'arraylist du joueur 1
        }
    
        // Boucle pour initialiser les pièces de glace sur la troisième ligne
        for (int i = 0; i < 8; i++) {
            Glace glace = new Glace(2 * i + 1, 3, "G1", "None", Couleurs.CYAN);
            setPiece(2 * i + 1, 3, glace);
            joueur1.add(glace); // Ajoute la pièce à l'arraylist du joueur 1
        }
    
        // Initialisation des pièces du joueur 2
        
        // Boucle pour initialiser les pièces de glace sur la quatorzième ligne
        for (int i = 0; i < 8; i++) {
            Glace glace = new Glace(2 * i, 13, "G2", "None", Couleurs.PURPLE);
            setPiece(2 * i, 13, glace);
            joueur2.add(glace); // Ajoute la pièce à l'arraylist du joueur 2
        }
    
        // Initialisation des pièces de véhicule sur la quinzième ligne
        for (int i = 0; i < 7; i++) {
            Vehicule vehicule = new Vehicule(2 * i + 1, 14, "V2", vehicleTypes[i], (vehicleTypes[i].equals("Methane")) ? Couleurs.YELLOW : Couleurs.BLUE, false);
            setPiece(2 * i + 1, 14, vehicule);
            joueur2.add(vehicule); // Ajoute la pièce à l'arraylist du joueur 2
        }
    
        // Boucle pour initialiser les pièces de glace sur la quinzième ligne
        for (int i = 0; i < 8; i++) {
            Glace glace = new Glace(2 * i, 15, "G2", "None", Couleurs.PURPLE);
            setPiece(2 * i, 15, glace);
            joueur2.add(glace); // Ajoute la pièce à l'arraylist du joueur 2
        }
    
        // Boucle pour initialiser les pièces de glace sur la quatorzième ligne
        for (int i = 0; i < 8; i++) {
            Glace glace = new Glace(2 * i + 1, 12, "G2", "None", Couleurs.PURPLE);
            setPiece(2 * i + 1, 12, glace);
            joueur2.add(glace); // Ajoute la pièce à l'arraylist du joueur 2
        }
    }
    
    

    public static void addClouds() {
        Random random = new Random();
        int cloudsAdded = 0;
    
        while (cloudsAdded < 30) {
            // Générer une coordonnée x aléatoire entre 0 et 15
            int x = random.nextInt(16);
            
            // Générer une coordonnée y aléatoire entre 5 et 12 inclusivement (correspond rééllement aux lignes 4 à 11 sur le tableau)
            int y = random.nextInt(8) + 4;
    
            // Vérifier si les coordonnées générées correspondent à une case vide sur le plateau et dans la plage spécifiée
            if (board[y][x] == null) {
                // Ajouter un nuage de méthane si le nombre de nuages ajoutés est inférieur à 15
                if (cloudsAdded < 15) {
                    setPiece(x, y, new Nuage(x, y, "N", "Methane", Couleurs.YELLOW));
                } else { // Sinon, ajouter un nuage d'eau
                    setPiece(x, y, new Nuage(x, y, "N", "Eau", Couleurs.BLUE));
                }
                cloudsAdded++;
            }
        }
    }
    

    static void playGame() {
        // Variable pour garder une trace du tour actuel

        // Boucle principale du jeu
        while (true) {
            // Afficher le plateau
            printBoard();

            // Afficher le numéro de tour
            System.out.println("Tour " + turn);


         // Déterminer quel joueur doit jouer en fonction du numéro de tour
            if (turn % 2 == 1) { // Tour impair : Joueur 1
                currentPlayer = joueur1;
                System.out.println("Tour du joueur 1 (CYAN)");
            } else { // Tour pair : Joueur 2
                currentPlayer = joueur2;
                System.out.println("Tour du joueur 2 (PURPLE)");
            }

            // Récupérer les coordonnées de la pièce à déplacer
            System.out.print("Entrez les coordonnées de la pièce à déplacer: (ou q pour quitter) : ");
            String source = scanner.next();
            int sourceX = source.charAt(0) - 'A';
            int sourceY = Integer.parseInt(source.substring(1)) - 1;

            // Vérifier si la pièce sélectionnée appartient au joueur actuel
            Piece selectedPiece = board[sourceY][sourceX];
            if (selectedPiece == null || !currentPlayer.contains(selectedPiece)) {
                System.out.println("La pièce sélectionnée n'appartient pas au joueur actuel. Réessayez.");
                continue; // Revenir au début de la boucle pour redemander une pièce valide
            }

            // Vérifier si le joueur veut quitter la partie
            if (source.equalsIgnoreCase("q")) {
                // Demander au joueur s'il souhaite sauvegarder avant de quitter
                System.out.println("Voulez-vous sauvegarder avant de quitter ? (O/N)");
                String saveInput = scanner.next();
                if (saveInput.equalsIgnoreCase("O")) {
                    saveGame();
                }
                break; // Quitter le jeu
            }

            // Récupérer les coordonnées de la destination
            System.out.print("Entrez les coordonnées de la destination (ex: B4): ");
            String destination = scanner.next();
            int destX = destination.charAt(0) - 'A';
            int destY = Integer.parseInt(destination.substring(1)) - 1;

            //1) vérifier si la case est vide ou contient un nuage 
            //2) Permettre le mouvement (reprendre le type déterminé après le 1er input) : terrestre,
            //Vérifier que la pièce appartient au joueur actuel: 
            
            // Vérifier si les coordonnées sont valides et 
            if (isValidMove(sourceX, sourceY, destX, destY)) {
                // Effectuer le déplacement de la pièce
                movePiece(sourceX, sourceY, destX, destY);
                

                // Sauvegarder le coup joué dans le fichier sérializable
                saveMoveToFile(source, destination, turn);

                // Incrémenter le numéro de tour
                turn++;

                // Vérifier s'il y a un gagnant ou un match nul
                // (implémentez cette logique selon vos règles spécifiques)
                if (isGameOver()) {
                    // Afficher le résultat final
                    System.out.println("La partie est terminée. Résultat final : ...");
                    break;
                }
            } else {
                System.out.println("Déplacement invalide. Réessayez.");
            }
        }
        scanner.close();
    }


    //méthodes à ajouter plus tard dans Game.java
    private static boolean isGameOver() {
        int countCyanClouds = 0;
        int countPurpleClouds = 0;
    
        // Parcourir le plateau pour compter les nuages capturés par chaque joueur
        // il faut que ce soit le count des véhicules et pas des pièces CYAN et PURPLE
        for (Piece[] row : board) {
            for (Piece piece : row) {
                if (piece instanceof Nuage) {
                    if (piece.getCouleur() == Couleurs.CYAN) {
                        countCyanClouds++;
                    } else if (piece.getCouleur() == Couleurs.PURPLE) {
                        countPurpleClouds++;
                    }
                }
            }
        }
    
        // Vérifier s'il y a un gagnant
        if (countCyanClouds >= 16 || countPurpleClouds >= 16) {
            System.out.println("La partie est terminée.");
            if (countCyanClouds > countPurpleClouds) {
                System.out.println("Le joueur 1 (CYAN) a remporté la partie avec " + countCyanClouds + " nuages capturés.");
            } else if (countCyanClouds < countPurpleClouds) {
                System.out.println("Le joueur 2 (PURPLE) a remporté la partie avec " + countPurpleClouds + " nuages capturés.");
            } else {
                System.out.println("Match nul !");
            }
            return true;
        }
    
        // Vérifier s'il n'y a plus de nuages à capturer
        boolean noMoreClouds = true;
        for (Piece[] row : board) {
            for (Piece piece : row) {
                if (piece instanceof Nuage) {
                    noMoreClouds = false;
                    break;
                }
            }
            if (!noMoreClouds) {
                break;
            }
        }
        if (noMoreClouds) {
            System.out.println("La partie est terminée.");
            if (countCyanClouds > countPurpleClouds) {
                System.out.println("Le joueur 1 a remporté la partie avec " + countCyanClouds + " nuages capturés.");
            } else if (countCyanClouds < countPurpleClouds) {
                System.out.println("Le joueur 2 a remporté la partie avec " + countPurpleClouds + " nuages capturés.");
            } else {
                System.out.println("Match nul !");
            }
            return true;
        }
    
        return false;
    }
    
        public static boolean isValidMove(int sourceX, int sourceY, int destX, int destY) {
            if (!isValidDestination(destX, destY)) {
                return false;
            }
            //On récupère les pièces aux coordonnées source et destination : 
            Piece piece = board[sourceY][sourceX];
            Piece destination = board[destY][destX];
        
            //Vérification qu'on a bien une pièce sur la case source:
            if (piece == null) {
                System.out.println("La case source est vide.");
                return false;
            }
            
            //Si la pièce est une glace (déplacement où elle veut sauf sur une autre glace)
            if (piece instanceof Glace && !(destination instanceof Glace)) {
                return piece.deplacementTerrestre(sourceX, sourceY, destX, destY);
            
            //Vérification lorsqu'on déplace un véhicule :
            } else if (piece instanceof Vehicule) {
                //Cas où la destination est occupée par une autre pièce :
                if (destination != null) {
                    if (destination instanceof Vehicule) { //si c'est un autre véhicule
                        System.out.println("La destination est occupée par un autre véhicule.");
                        return false;
                    //cas où la case est un nuage ou de la glace : 
                    } else if (destination instanceof Nuage && !((Vehicule) piece).getState() || destination instanceof Glace && !((Vehicule) piece).getState()) {
                        System.out.println("La destination est occupée par un nuage ou de la glace, collision en cours...");
                        
                        //Collision avec un nuage (du même type) :
                        if (destination instanceof Nuage && ((Vehicule) piece).getType().equals(((Nuage) destination).getType())) {
                            ((Vehicule) piece).captureNuage();
                            System.out.println("Déplacement terrestre.");
                            System.out.println("Le véhicule a capturé un nuage de type " + ((Nuage) destination).getType());
                            System.out.println("Nombre de nuages capturés pour ce véhicule : " + ((Vehicule) piece).getNuagesCaptures());
                            return piece.deplacementTerrestre(sourceX, sourceY, destX, destY);
                        
                        //Collision avec un nuage (de type différent) ou de la glace :
                        } else { 
                            System.out.println("Le véhicule a été détruit dans la collision !");
                            // Supprimer le véhicule du plateau
                            board[sourceY][sourceX] = null;
                            return false;
                        }
                    }
                }
                if (destination == null) {
                    if (!((Vehicule) piece).getState()) {
                        System.out.println("Déplacement terrestre.");
                        return piece.deplacementTerrestre(sourceX, sourceY, destX, destY);
                    } if ((((Vehicule) piece).getState()) && !(isGlaceBetween(sourceX, sourceY, destX, destY))) {
                        System.out.println("Déplacement aérien.");
                        return piece.deplacementAerien(sourceX, sourceY, destX, destY);
                    } else {
                        System.out.println("Déplacement aérien impossible : il y a une glace entre la source et la destination.");
                        return false;
                    }
                }
            //à implémenter !!! comment bouger les nuages ?
            } else if (piece instanceof Nuage) {
                return false;
            }
        
            return false;
        }
    
        // Méthode pour vérifier si la destination est valide
        private static boolean isValidDestination(int destX, int destY) {
            if (destX < 0 || destX >= board.length || destY < 0 || destY >= board[0].length) {
                System.out.println("La destination est en dehors du plateau.");
                return false;
            }
            return true;
        }
    


    //mettre cette méthode plutot dans la classe Vehicule.java et nuage.java
    static boolean isGlaceBetween(int sourceX, int sourceY, int destX, int destY) {
        // Vérifier s'il y a une glace entre la source et la destination
        int deltaX = destX - sourceX;
        int deltaY = destY - sourceY;
        int stepX = (deltaX == 0) ? 0 : deltaX / Math.abs(deltaX); // Direction horizontale (+1 pour droite, -1 pour gauche)
        int stepY = (deltaY == 0) ? 0 : deltaY / Math.abs(deltaY); // Direction verticale (+1 pour bas, -1 pour haut)
        int currentX = sourceX + stepX;
        int currentY = sourceY + stepY;
    
        while (currentX != destX || currentY != destY) {
            if (board[currentY][currentX] instanceof Glace) {
                return true; // Il y a une glace entre la source et la destination
            }
            currentX += stepX;
            currentY += stepY;
        }
    
        return false; // Aucune glace sur le chemin
    }
    

    static void movePiece(int sourceX, int sourceY, int destX, int destY) {
        // Récupérer la pièce à déplacer
        Piece piece = board[sourceY][sourceX];
        // Déplacer la pièce vers la destination
        board[destY][destX] = piece;
        // Vider la case source
        board[sourceY][sourceX] = null;
    }

    static void saveMoveToFile(String source, String destination, int turn) {
        try (FileWriter writer = new FileWriter("moves.ser", true)) {
            writer.write(turn + ". " + source + "-" + destination + ".\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void saveGame() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("game.ser"))) {
            outputStream.writeObject(board);
            outputStream.writeObject(joueur2);
            outputStream.writeObject(joueur1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    	// set piece to provided coordinates
	public static void setPiece(int x, int y, Piece piece) {
		if (piece != null) {
			piece.setX(x);
			piece.setY(y);
		}
		board[y][x] = piece;

        // Association de la pièce au joueur correspondant
        if (piece != null && (piece.getIcon().equals("G1") || piece.getIcon().equals("V1"))) {
            joueur1.add(piece); // Ajouter la pièce à joueur1
        } else {
            joueur2.add(piece); // Ajouter la pièce à joueur2
        }
    }

	// check spot on board
	public static Piece getPiece(int x, int y) {
		return board[y][x];
	}
}