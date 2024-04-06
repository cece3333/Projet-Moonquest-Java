package display;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import pieces.Glace;
import pieces.Nuage;
import pieces.Piece;
import pieces.Vehicule;
import utils.Couleurs;

public class Board {
    public static ArrayList<Piece> joueur2 = new ArrayList<Piece>();
    public static ArrayList<Piece> joueur1 = new ArrayList<Piece>();
    public static ArrayList<Piece> currentPlayer;

    public static Piece board[][] = new Piece[16][16];
    public static int BOARD_SIZE = Board.board.length;

    public static Scanner scanner = new Scanner(System.in);

    public static int turn = 1;
    public static int scoreJoueur1 = 0;
    public static int scoreJoueur2 = 0;

    public static void printBoard() {
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
    public static void initializeBoard() {
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
    
    public static void moveClouds() {
        Random random = new Random();
    
        for (int y = 0; y < Board.board.length; y++) {
            for (int x = 0; x < Board.board[y].length; x++) {
                if (Board.board[y][x] instanceof Nuage) {
                    int randomNumber = random.nextInt(5);
                    if (randomNumber == 0) {
                        int direction = random.nextInt(4);
                        int destX = x, destY = y;
    
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
    
                        // Ajuster les coordonnées de destination pour qu'elles restent dans les limites de la grille
                        destX = (destX + Board.board[0].length) % Board.board[0].length;
                        destY = (destY + Board.board.length) % Board.board.length;
    
                        if (!isGlaceBetween(x, y, destX, destY)) {
                            Board.board[destY][destX] = Board.board[y][x];
                            Board.board[y][x] = null;
                        }
                    }
                }
            }
        }
    }

    public static boolean isValidMove(int sourceX, int sourceY, int destX, int destY, int scoreJoueur1, int scoreJoueur2) {
        if (!isValidDestination(destX, destY)) {
            System.out.println("TEST : La destination est invalide.");
            return false;
        }
        // Mettre à jour les coordonnées de destination avec les ajustements de la grille infinie
        //destY = (destY + Board.board[0].length) % Board.board[0].length;
        //destX = (destX + Board.board.length) % Board.board.length;
        System.out.println("TEST : Source : " + sourceX + ", " + sourceY);
        System.out.println("TEST : Destination : " + destX + ", " + destY);
        
        //On récupère les pièces aux coordonnées source et destination : 
        Piece piece = board[sourceY][sourceX];
        Piece destination = board[destY][destX];

        //Vérification qu'on a bien une pièce sur la case source:
        if (piece == null) {
            System.out.println("La case source est vide.");
            return false;
        }
        
        //Si la pièce est une glace (déplacement où elle veut sauf sur les propres pièces du joueur) :
        if ((piece instanceof Glace)) {
            if ((destination != null) && (!(currentPlayer.contains(destination)))) {
                System.out.println("La glace a écrasé la pièce " + destination.getIcon());
                Board.board[destY][destX] = null;
            } if (currentPlayer.contains(destination)) { 
                System.out.println("La glace ne peut pas écraser ses propres pièces.");
                return false;
            } else {
                boolean result = piece.deplacementTerrestre(sourceX, sourceY, destX, destY);
                if (result) {
                    System.out.println("Déplacement de la glace effectué.");
                    return true;
                } else {
                    System.out.println("Déplacement de la glace impossible.");
                    return false;
                }
            }
        
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
                        // Mise à jour des scores:
                        if (currentPlayer == joueur1) {
                            Board.scoreJoueur1++; //important de laisser pour bien actualiser les scores de Board
                        } else {
                            Board.scoreJoueur2++;
                        }
                        System.out.println("Le véhicule a capturé un nuage de type " + ((Nuage) destination).getType() +"\n" + "Nuages capturés : " + ((Vehicule) piece).getNuagesCaptures());       
                        return true;           
                }
            } else { //Collision avec un nuage (de type différent) ou de la glace :
                System.out.println("Le véhicule a été détruit dans la collision !");
                // Supprimer le véhicule du plateau
                board[sourceY][sourceX] = null;
                turn++;
                return false; //pour éviter que le joueur joue deux fois
            }
            
            //Si la destination est vide, on peut déplacer le véhicule (en fonction de son état)
            } if (destination == null) {
                    if (!((Vehicule) piece).getState()) {
                        System.out.println("Déplacement terrestre.");
                        return piece.deplacementTerrestre(sourceX, sourceY, destX, destY);
                    } else if ((((Vehicule) piece).getState()) && !(Board.isGlaceBetween(sourceX, sourceY, destX, destY))) {
                        System.out.println("Déplacement aérien.");
                        return piece.deplacementAerien(sourceX, sourceY, destX, destY);
                    } else {
                        System.out.println("Déplacement aérien impossible : il y a une glace entre la source et la destination.");
                        return false;
                    }
                }
        } else {
            System.out.println("Mouvement invalide.");
            return false;
        }
        return false; //vite changer en false
    }

    public static boolean isXBorder(int x) {
        boolean res = x == 0 || x == Board.board[0].length - 1;
        if (res) {
            System.out.println("La destination est en bordure horizontale.");
            return true;
        } else {
            System.out.println("La destination n'est pas en bordure horizontale.");
            return false;
        }
    }

    public static boolean isYBorder(int y) {
        boolean res = y == 0 || y == Board.board.length - 1;
        if (res) {
            System.out.println("La destination est en bordure verticale.");
            return true;
        } else {
            System.out.println("La destination n'est pas en bordure verticale.");
            return false;
        }
    }


    public static boolean isValidDestination(int destX, int destY) {
        // Vérifier si la destination est en dehors du plateau
        if (destX < 0 || destY < 0 || destY >= Board.board[0].length) {
            System.out.println("La destination est en dehors du plateau.");
            return false;
        }
    
        // Si la destination est en dehors de la grille horizontalement, ajuster la coordonnée X
        if (destX >= Board.board.length) {
            destX = destX % Board.board.length;
        } else if (destX < 0) {
            destX = Board.board.length - 1 - (-destX - 1) % Board.board.length;
        }
    
        // Si la destination est en dehors de la grille verticalement, ajuster la coordonnée Y
        if (destY >= Board.board[0].length) {
            destY = destY % Board.board[0].length;
        } else if (destY < 0) {
            destY = Board.board[0].length - 1 - (-destY - 1) % Board.board[0].length;
        }
    
        return true;
    }
    

    public static boolean isGlaceBetween(int sourceX, int sourceY, int destX, int destY) {
        int adjustedDestX = destX % Board.board[0].length;
        int adjustedDestY = destY % Board.board.length;
    
        // Vérifier s'il y a une glace entre la source et la destination
        int deltaX = adjustedDestX - sourceX;
        int deltaY = adjustedDestY - sourceY;
        int stepX = (deltaX == 0) ? 0 : deltaX / Math.abs(deltaX); // Direction horizontale (+1 pour droite, -1 pour gauche)
        int stepY = (deltaY == 0) ? 0 : deltaY / Math.abs(deltaY); // Direction verticale (+1 pour bas, -1 pour haut)
        int currentX = sourceX + stepX;
        int currentY = sourceY + stepY;
    
        while (currentX != adjustedDestX || currentY != adjustedDestY) {
            if (Board.board[currentY][currentX] instanceof Glace) {
                return true; // Il y a une glace entre la source et la destination
            }
            currentX += stepX;
            currentY += stepY;
        }
    
        return false; // Aucune glace sur le chemin
    }

    //methodes des pièces du plateau : 
    public static void movePiece(int sourceX, int sourceY, int destX, int destY) {
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