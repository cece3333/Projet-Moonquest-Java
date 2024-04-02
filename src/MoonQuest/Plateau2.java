
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
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
        System.out.println();
        System.out.println("      A    B    C    D    E    F    G    H    I    J    K    L    M    N    O    P");
        System.out.println("   ----------------------------------------------------------------------------------");
        
        int count = 1;
        for (int i = 0; i < 16; i++) {
            if (i < 9) {
                System.out.print(" " + count + " ");
            }
            else {
                System.out.print(count + " "); //rajouté des espaces 
            }
            System.out.print("|  ");
            
            for (int j = 0; j < 16; j++) {
                if (board[i][j] == null) {
                    System.out.print("   | "); //3 (2 gauche/1 droite)
                } else {
                    String pieceIcon = board[i][j].getIcon();
                    Couleurs pieceColor = board[i][j].getCouleur();
                    
                    // Récupérer le code ANSI de la couleur de la pièce
                    String colorCode = pieceColor.getCode();
                    
                    // Afficher la pièce avec la couleur appropriée
                    System.out.print(colorCode + pieceIcon + Couleurs.RESET.getCode() + " | "); //1 de chaque
                }
            }
            
            System.out.print(count);
            count++;
            System.out.println();
            System.out.println("   ----------------------------------------------------------------------------------");
        }
        System.out.println("      A    B    C    D    E    F    G    H    I    J    K    L    M    N    O    P");
        System.out.println(); 
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
        String[] vehicleTypes = {"Methane", "Eau", "Methane", "Eau", "Methane", "Eau", "Methane"};
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
    
    // methodes des nuages : 
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
                    setPiece(x, y, new Nuage(x, y, "NM", "Methane", Couleurs.YELLOW));
                } else { // Sinon, ajouter un nuage d'eau
                    setPiece(x, y, new Nuage(x, y, "NE", "Eau", Couleurs.BLUE));
                }
                cloudsAdded++;
            }
        }
    }
    
    //mouvements des nuages :
    static void moveClouds() {
        Random random = new Random();
    
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[y].length; x++) {
                // Vérifier si la case contient un nuage
                if (board[y][x] instanceof Nuage) {
                    // Générer un nombre aléatoire entre 0 et 4 inclusivement
                    int randomNumber = random.nextInt(5);
                    // Vérifier si le nuage doit se déplacer (1 chance sur 5)
                    if (randomNumber == 0) {
                        // Choisir une direction de déplacement aléatoire (0: haut, 1: bas, 2: gauche, 3: droite)
                        int direction = random.nextInt(4);
                        int destX = x, destY = y;
    
                        // Déplacer le nuage dans la direction choisie si la case adjacente est libre et respecte les règles du déplacement aérien
                        switch (direction) {
                            case 0: // Haut
                                destY -= 2;
                                break;
                            case 1: // Bas
                                destY += 2;
                                break;
                            case 2: // Gauche
                                destX -= 2;
                                break;
                            case 3: // Droite
                                destX += 2;
                                break;
                        }
    
                        // Vérifier si la destination est valide et si la case est libre
                        if (isValidDestination(destX, destY) && board[destY][destX] == null) { //à changer car les nuages peuvent écraser les véhicules
                            // Vérifier s'il n'y a pas de glace entre la position actuelle et la destination
                            if (!isGlaceBetween(x, y, destX, destY)) {
                                // Effectuer le déplacement du nuage
                                board[destY][destX] = board[y][x];
                                board[y][x] = null;
                            }
                        }
                    }
                }
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
            
            if (turn == 1) {
                System.out.println("Voulez-vous commencer une nouvelle partie (N) ou reprendre la partie précédente (R) ?");
                String startChoice = scanner.next();
                if (startChoice.equalsIgnoreCase("R")) {
                    // Charger la partie précédente depuis le fichier game.ser
                    loadGame();
                }
            }

         // Déterminer quel joueur doit jouer en fonction du numéro de tour
            if (turn % 2 == 1) { // Tour impair : Joueur 1
                currentPlayer = joueur1;
                System.out.println("Tour du joueur 1 (CYAN)");
                System.out.println("Nombre total de nuages capturés : " + scoreJoueur1);
            } else { // Tour pair : Joueur 2
                currentPlayer = joueur2;
                System.out.println("Tour du joueur 2 (PURPLE)");
                System.out.println("Nombre total de nuages capturés : " + scoreJoueur2);
            }

            // Récupérer les coordonnées de la pièce à déplacer
            System.out.print("Entrez les coordonnées de la pièce à déplacer: (ou q pour quitter) : ");
            String source = scanner.next();

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

            int sourceX = source.charAt(0) - 'A';
            int sourceY = Integer.parseInt(source.substring(1)) - 1;

            // Vérifier si la pièce sélectionnée appartient au joueur actuel
            Piece selectedPiece = board[sourceY][sourceX];
            if (selectedPiece == null || !currentPlayer.contains(selectedPiece)) {
                System.out.println("La pièce sélectionnée n'appartient pas au joueur actuel. Réessayez.");
                continue; // Revenir au début de la boucle pour redemander une pièce valide
            }

            // Récupérer les coordonnées de la destination
            System.out.print("Entrez les coordonnées de la destination (ex: B4): ");
            String destination = scanner.next();
            int destX = destination.charAt(0) - 'A';
            int destY = Integer.parseInt(destination.substring(1)) - 1;
            
            
            if (isValidMove(sourceX, sourceY, destX, destY)) {
                // Effectuer le déplacement de la pièce
                movePiece(sourceX, sourceY, destX, destY);
                

                // Sauvegarder le coup joué dans le fichier sérializable
                saveMoveToFile(source, destination, turn, scoreJoueur1, scoreJoueur2);

                // Incrémenter le numéro de tour
                turn++;

                // Vérifier s'il y a un gagnant ou un match nul
                // (implémentez cette logique selon vos règles spécifiques)
                if (isGameOver(scoreJoueur1, scoreJoueur2)) {
                    // Afficher le résultat final
                    System.out.println("La partie est terminée. Résultat final : ...");
                    break;
                }
            } else {
                System.out.println("Déplacement invalide. Réessayez.");
            }
            moveClouds(); //à voir si je la laisse la où dans le main (on verra après avoir scindé Plateau.java)
        }
        scanner.close();
    }

    @SuppressWarnings("unchecked")
    public static void loadGame() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("game.ser"))) {
            // Désérialiser les objets
            board = (Piece[][]) inputStream.readObject();
            joueur2 = (ArrayList<Piece>) inputStream.readObject();
            joueur1 = (ArrayList<Piece>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //méthodes à ajouter plus tard dans Game.java
    private static boolean isGameOver(int scoreJoueur1, int scoreJoueur2) {

        //Vérifier s'il n'y a plus de nuages à capturer
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
        // Vérifier s'il y a un gagnant (soit par score > 15, soit car plus de nuages à capturer)
        if ((scoreJoueur1 >= 16 || scoreJoueur2 >= 16) || (noMoreClouds)) {
            System.out.println("La partie est terminée.");
            if (scoreJoueur1 > scoreJoueur2) {
                System.out.println("Le joueur 1 (CYAN) a remporté la partie avec " + scoreJoueur1 + " nuages capturés.");
            } else if (scoreJoueur1 < scoreJoueur2) {
                System.out.println("Le joueur 2 (PURPLE) a remporté la partie avec " + scoreJoueur2 + " nuages capturés.");
            } else if (((scoreJoueur1 == scoreJoueur2) && (scoreJoueur1 + scoreJoueur2 == 30)) || ((scoreJoueur1 == scoreJoueur2) && (noMoreClouds))){
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
                if (destination instanceof Vehicule) { //si c'est un autre véhicule
                    System.out.println("La destination est occupée par un autre véhicule.");
                    return false;
                
                //cas où la case est un nuage ou de la glace : 
                } else if ((!(destination instanceof Vehicule)) && (destination != null)) {
                    System.out.println("La destination est occupée par un nuage ou de la glace, collision en cours...");
                    //si le véhicule est activé, il peut supprimer n'importe quel nuage
                    if ((((Vehicule) piece).getState()) && !(destination instanceof Glace)) {
                        System.out.println("Un nuage a été supprimé par le véhicule!");
                        return piece.deplacementAerien(sourceX, sourceY, destX, destY);
                    
                    //si la destination est un nuage de même type que le véhicule :
                    } else if (destination instanceof Nuage && ((Vehicule) piece).getType().equals(((Nuage) destination).getType())) {
                        if (piece.deplacementTerrestre(sourceX, sourceY, destX, destY)) {
                            ((Vehicule) piece).captureNuage();
                            //mise à jour des scores:
                            if (currentPlayer == joueur1) {
                                scoreJoueur1++;
                            } else {
                                scoreJoueur2++;
                            }
                            System.out.println("Déplacement terrestre.");
                            System.out.println("Le véhicule a capturé un nuage de type " + ((Nuage) destination).getType());
                            System.out.println("Nombre de nuages capturés pour ce véhicule : " + ((Vehicule) piece).getNuagesCaptures());
                            
                            return true;
                    
                    //Collision avec un nuage (de type différent) ou de la glace :
                    } else { 
                        System.out.println("Le véhicule a été détruit dans la collision !");
                        // Supprimer le véhicule du plateau
                        board[sourceY][sourceX] = null;
                        return true; //pour éviter que le joueur joue deux fois
                    }
                }
                
                //Si la destination est vide, on peut déplacer le véhicule (en fonction de son état)
                } if (destination == null) {
                        if (!((Vehicule) piece).getState()) {
                            System.out.println("Déplacement terrestre.");
                            return piece.deplacementTerrestre(sourceX, sourceY, destX, destY);
                        } else if ((((Vehicule) piece).getState()) && !(isGlaceBetween(sourceX, sourceY, destX, destY))) {
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
                System.out.println("La destination est en dehors du plateau."); //bon le problème c'est que le message s'affiche lors des mouvements des nuages
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

    //methodes de sauvegardes des mouvements :
    static void saveMoveToFile(String source, String destination, int turn, int scoreJoueur1, int scoreJoueur2) {
        try (FileWriter writer = new FileWriter("moves.txt", true)) {
            String symbole = "x";
            Piece destinationPiece = getPiece(destination.charAt(0) - 'A', Integer.parseInt(destination.substring(1)) - 1);
            if (destinationPiece != null) {
                symbole = ".";
            }
            writer.write(turn + ". " + source + "-" + destination + " " + symbole + " " + scoreJoueur1 + "-" + scoreJoueur2 + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveGame() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("game.ser"))) {
            outputStream.writeObject(board);
            outputStream.writeObject(joueur2);
            outputStream.writeObject(joueur1);
            outputStream.writeInt(scoreJoueur1);
            outputStream.writeInt(scoreJoueur2);
            
            //Sérialiser les scores des véhicules pour le joueur 1
            for (Piece piece : joueur1) {
                if (piece instanceof Vehicule) {
                    int scoreVehicule = ((Vehicule) piece).getNuagesCaptures();
                    outputStream.writeInt(scoreVehicule);
                }
            }
            
            //Sérialiser les scores des véhicules pour le joueur 2
            for (Piece piece : joueur2) {
                if (piece instanceof Vehicule) {
                    int scoreVehicule = ((Vehicule) piece).getNuagesCaptures();
                    outputStream.writeInt(scoreVehicule);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //methodes des pièces du plateau : 
    static void movePiece(int sourceX, int sourceY, int destX, int destY) {
        // Récupérer la pièce à déplacer
        Piece piece = board[sourceY][sourceX];
        // Déplacer la pièce vers la destination
        board[destY][destX] = piece;
        // Vider la case source
        board[sourceY][sourceX] = null;
    }

    	// set piece to provided coordinates
	public static void setPiece(int x, int y, Piece piece) {
		if (piece != null) {
			piece.setX(x);
			piece.setY(y);
		}
		board[y][x] = piece;
        //suppression de l'ajout de la pièce dans l'arraylist du joueur
    }

	// check spot on board
	public static Piece getPiece(int x, int y) {
		return board[y][x];
	}
}