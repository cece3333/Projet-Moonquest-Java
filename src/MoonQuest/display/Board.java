package display;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

import pieces.*;
import utils.Colors;


/**
 * Représente le plateau de jeu.
 */
public class Board {
    
    private static ArrayList<Piece> joueur1 = new ArrayList<Piece>();
    private static ArrayList<Piece> joueur2 = new ArrayList<Piece>();
    
    public static ArrayList<Piece> currentPlayer;
    public static Piece board[][] = new Piece[16][16]; 
    public static int BOARD_SIZE = board.length;

    
    /**
     * Récupère les pièces du joueur 1.
     *
     * @return La liste des pièces du joueur 1.
     */
    public static ArrayList<Piece> getJoueur1() {
        return joueur1;
    }

    /**
     * Récupère les pièces du joueur 2.
     *
     * @return La liste des pièces du joueur 2.
     */
    public static ArrayList<Piece> getJoueur2() {
        return joueur2;
    }

    /**
     * Récupère la pièce située à une position donnée sur le plateau.
     *
     * @param x Position en abscisse de la pièce.
     * @param y Position en ordonnée de la pièce.
     * @return La pièce située aux coordonnées spécifiées.
     */
    public static Piece getPiece(int x, int y) {
		return board[y][x];
	}

    /**
     * Place une pièce à une position donnée sur le plateau.
     *
     * @param x     Position en abscisse de la pièce.
     * @param y     Position en ordonnée de la pièce.
     * @param piece Pièce à placer sur le plateau.
     */
	public static void setPiece(int x, int y, Piece piece) {
		if (piece != null) {
			piece.setX(x);
			piece.setY(y);
		}
		board[y][x] = piece;
        
    }

    /**
     * Affiche graphiquement le plateau de jeu dans la console.
     */
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
                    System.out.print("   | ");
                } else {
                    String pieceIcon = board[i][j].getIcon();
                    Colors pieceColor = board[i][j].getColor();
                    
                    String colorCode = pieceColor.getCode();
                    
                    System.out.print(colorCode + pieceIcon + Colors.RESET.getCode() + " | ");
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

     /**
     * Initialise le plateau avec les pièces des deux joueurs.
     */
    public static void initializeBoard() {
        System.out.println("Ajout des pièces sur le plateau :");
        
        // Initialisation des pièces du joueur 1
        
        
        for (int i = 0; i < 8; i++) {
            Glace glace = new Glace(2 * i, 0, "G1", "None", Colors.CYAN);
            setPiece(2 * i, 0, glace);
            joueur1.add(glace); 
        }
        
        String[] vehicleTypes = {"Methane", "Eau", "Methane", "Eau", "Methane", "Eau", "Methane"};
        for (int i = 0; i < 7; i++) {
            Vehicule vehicule = new Vehicule(2 * i + 1, 1, "V1", vehicleTypes[i], (vehicleTypes[i].equals("Methane")) ? Colors.YELLOW : Colors.BLUE, false);
            setPiece(2 * i + 1, 1, vehicule);
            joueur1.add(vehicule); 
        }
    
        for (int i = 0; i < 8; i++) {
            Glace glace = new Glace(2 * i, 2, "G1", "None", Colors.CYAN);
            setPiece(2 * i, 2, glace);
            joueur1.add(glace); 
        }
    
        for (int i = 0; i < 8; i++) {
            Glace glace = new Glace(2 * i + 1, 3, "G1", "None", Colors.CYAN);
            setPiece(2 * i + 1, 3, glace);
            joueur1.add(glace); 
        }
    
        // Initialisation des pièces du joueur 2
        
        for (int i = 0; i < 8; i++) {
            Glace glace = new Glace(2 * i, 13, "G2", "None", Colors.PURPLE);
            setPiece(2 * i, 13, glace);
            joueur2.add(glace); 
        }
    
        for (int i = 0; i < 7; i++) {
            Vehicule vehicule = new Vehicule(2 * i + 1, 14, "V2", vehicleTypes[i], (vehicleTypes[i].equals("Methane")) ? Colors.YELLOW : Colors.BLUE, false);
            setPiece(2 * i + 1, 14, vehicule);
            joueur2.add(vehicule); 
        }
    
        for (int i = 0; i < 8; i++) {
            Glace glace = new Glace(2 * i, 15, "G2", "None", Colors.PURPLE);
            setPiece(2 * i, 15, glace);
            joueur2.add(glace); 
        }
    
        for (int i = 0; i < 8; i++) {
            Glace glace = new Glace(2 * i + 1, 12, "G2", "None", Colors.PURPLE);
            setPiece(2 * i + 1, 12, glace);
            joueur2.add(glace); 
        }
    }

    /**
     * Ajoute des nuages de manière aléatoire sur le plateau.
     */
    public static void addNuages() {
        Random random = new Random();
        int cloudsAdded = 0;
    
        while (cloudsAdded < 30) {
            int x = random.nextInt(16); 
            int y = random.nextInt(8) + 4;
    
            if (board[y][x] == null) {
                if (cloudsAdded < 15) {
                    setPiece(x, y, new Nuage(x, y, "NM", "Methane", Colors.YELLOW));
                } else { // Sinon, ajouter un nuage d'eau
                    setPiece(x, y, new Nuage(x, y, "NE", "Eau", Colors.BLUE));
                }
                cloudsAdded++;
            }
        }
    }

     /**
     * Déplace une pièce d'une position source à une position destination sur le plateau.
     *
     * @param sourceX Abscisse de la position source.
     * @param sourceY Ordonnée de la position source.
     * @param destX   Abscisse de la position destination.
     * @param destY   Ordonnée de la position destination.
     */
    public static void movePiece(int sourceX, int sourceY, int destX, int destY) {
        Piece piece = board[sourceY][sourceX];
        board[destY][destX] = piece;
        board[sourceY][sourceX] = null;
    }

     /**
     * Déplace de manière aléatoire les nuages sur le plateau.
     */
    public static void moveNuagesRandomly() {
        Random random = new Random();
    
        for (int y = 0; y < BOARD_SIZE; y++)
            for (int x = 0; x < Board.board[y].length; x++)
                if (Board.board[y][x] instanceof Nuage && random.nextInt(5) == 0) {
                    int direction = random.nextInt(4);
                    int destX = x + (direction == 2 ? -2 : (direction == 3 ? 2 : 0));
                    int destY = y + (direction == 0 ? -2 : (direction == 1 ? 2 : 0));
    
                    destX = (destX + Board.board[0].length) % Board.board[0].length;
                    destY = (destY + BOARD_SIZE) % BOARD_SIZE;
    
                    if (!(Board.IsGlaceBetween(x, y, destX, destY) || Board.board[destY][destX] instanceof Nuage)) {
                        Board.board[destY][destX] = Board.board[y][x];
                        Board.board[y][x] = null;
                    }
                }
        
        }

    /**
     * Vérifie s'il y a une glace entre deux positions sur le plateau.
     *
     * @param sourceX Abscisse de la position source.
     * @param sourceY Ordonnée de la position source.
     * @param destX   Abscisse de la position destination.
     * @param destY   Ordonnée de la position destination.
     * @return true s'il y a une glace entre les deux positions, sinon false.
     */
    public static boolean IsGlaceBetween(int sourceX, int sourceY, int destX, int destY) {
        int adjustedDestX = destX % Board.board[0].length;
        int adjustedDestY = destY % BOARD_SIZE;
    
        int deltaX = adjustedDestX - sourceX;
        int deltaY = adjustedDestY - sourceY;
        int stepX = (deltaX == 0) ? 0 : deltaX / Math.abs(deltaX);
        int stepY = (deltaY == 0) ? 0 : deltaY / Math.abs(deltaY);
        int currentX = sourceX + stepX;
        int currentY = sourceY + stepY;
    
        while (currentX != adjustedDestX || currentY != adjustedDestY) {
            if (Board.board[currentY][currentX] instanceof Glace) {
                return true;
            }
            currentX += stepX;
            currentY += stepY;
        }
    
        return false;
    }
}
