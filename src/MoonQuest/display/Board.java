package display;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import pieces.*;
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
                System.out.print(count + " "); 
            }
            System.out.print("|  ");
            
            for (int j = 0; j < 16; j++) {
                if (board[i][j] == null) {
                    System.out.print("   | "); //3 (2 gauche/1 droite)
                } else {
                    String pieceIcon = board[i][j].getIcon();
                    Couleurs pieceColor = board[i][j].getCouleur();
                    
                    String colorCode = pieceColor.getCode();
                    
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

    
	public static void setPiece(int x, int y, Piece piece) {
		if (piece != null) {
			piece.setX(x);
			piece.setY(y);
		}
		board[y][x] = piece;
        
    }

	public static Piece getPiece(int x, int y) {
		return board[y][x];
	}

    public static void movePiece(int sourceX, int sourceY, int destX, int destY) {
        // Récupérer la pièce à déplacer
        Piece piece = Board.board[sourceY][sourceX];
        // Déplacer la pièce vers la destination
        Board.board[destY][destX] = piece;
        // Vider la case source
        Board.board[sourceY][sourceX] = null;
    }
    
    public static void initializeBoard() {
        System.out.println("Ajout des pièces sur le plateau :");
        
        // Initialisation des pièces du joueur 1
        
        
        for (int i = 0; i < 8; i++) {
            Glace glace = new Glace(2 * i, 0, "G1", "None", Couleurs.CYAN);
            setPiece(2 * i, 0, glace);
            joueur1.add(glace); 
        }
        
        String[] vehicleTypes = {"Methane", "Eau", "Methane", "Eau", "Methane", "Eau", "Methane"};
        for (int i = 0; i < 7; i++) {
            Vehicule vehicule = new Vehicule(2 * i + 1, 1, "V1", vehicleTypes[i], (vehicleTypes[i].equals("Methane")) ? Couleurs.YELLOW : Couleurs.BLUE, false);
            setPiece(2 * i + 1, 1, vehicule);
            joueur1.add(vehicule); 
        }
    
        for (int i = 0; i < 8; i++) {
            Glace glace = new Glace(2 * i, 2, "G1", "None", Couleurs.CYAN);
            setPiece(2 * i, 2, glace);
            joueur1.add(glace); 
        }
    
        for (int i = 0; i < 8; i++) {
            Glace glace = new Glace(2 * i + 1, 3, "G1", "None", Couleurs.CYAN);
            setPiece(2 * i + 1, 3, glace);
            joueur1.add(glace); 
        }
    
        // Initialisation des pièces du joueur 2
        
        for (int i = 0; i < 8; i++) {
            Glace glace = new Glace(2 * i, 13, "G2", "None", Couleurs.PURPLE);
            setPiece(2 * i, 13, glace);
            joueur2.add(glace); 
        }
    
        for (int i = 0; i < 7; i++) {
            Vehicule vehicule = new Vehicule(2 * i + 1, 14, "V2", vehicleTypes[i], (vehicleTypes[i].equals("Methane")) ? Couleurs.YELLOW : Couleurs.BLUE, false);
            setPiece(2 * i + 1, 14, vehicule);
            joueur2.add(vehicule); 
        }
    
        for (int i = 0; i < 8; i++) {
            Glace glace = new Glace(2 * i, 15, "G2", "None", Couleurs.PURPLE);
            setPiece(2 * i, 15, glace);
            joueur2.add(glace); 
        }
    
        for (int i = 0; i < 8; i++) {
            Glace glace = new Glace(2 * i + 1, 12, "G2", "None", Couleurs.PURPLE);
            setPiece(2 * i + 1, 12, glace);
            joueur2.add(glace); 
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
                    setPiece(x, y, new Nuage(x, y, "NM", "Methane", Couleurs.YELLOW));
                } else { // Sinon, ajouter un nuage d'eau
                    setPiece(x, y, new Nuage(x, y, "NE", "Eau", Couleurs.BLUE));
                }
                cloudsAdded++;
            }
        }
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

}