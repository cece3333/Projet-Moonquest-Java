
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Plateau {
    public static ArrayList<Piece> joueur2 = new ArrayList<Piece>();
    public static ArrayList<Piece> joueur1 = new ArrayList<Piece>();

    public static Piece board[][] = new Piece[16][16];

    static Scanner scanner = new Scanner(System.in);

    //préciser que le joueur 2 possède les pièces  aux icones G2 et V2

    //implémenter une fonction qui permet de switcher de player à chaque tour (tour % 2 etc.)

    //on déclare turn comme une variable de cette classe :
    static int turn = 1;

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
        System.out.println("How to play:");
    
        // Initialisation des pièces du joueur 1

        // glace (row 1)
        setPiece(0, 0, new Glace(0, 0, "G1", "None", Couleurs.CYAN));
        setPiece(2, 0, new Glace(2, 0, "G1", "None", Couleurs.CYAN));
        setPiece(4, 0, new Glace(4, 0, "G1", "None", Couleurs.CYAN));
        setPiece(6, 0, new Glace(6, 0, "G1", "None", Couleurs.CYAN));
        setPiece(8, 0, new Glace(8, 0, "G1", "None", Couleurs.CYAN));
        setPiece(10, 0, new Glace(10, 0, "G1", "None", Couleurs.CYAN));
        setPiece(12, 0, new Glace(12, 0, "G1", "None", Couleurs.CYAN));
        setPiece(14, 0, new Glace(14, 0, "G1", "None", Couleurs.CYAN));
    
        // véhicules (alternance entre types méthanes et type eau)
        setPiece(1, 1, new Vehicule(1, 1, "V1", "Methane", Couleurs.YELLOW, false));
        setPiece(5, 1, new Vehicule(5, 1, "V1", "Water", Couleurs.CYAN, false));
        setPiece(7, 1, new Vehicule(7, 1, "V1", "Methane", Couleurs.YELLOW, false));
        setPiece(9, 1, new Vehicule(9, 1, "V1", "Water", Couleurs.CYAN, false));
        setPiece(11, 1, new Vehicule(11, 1, "V1", "Methane", Couleurs.YELLOW, false));
        setPiece(13, 1, new Vehicule(13, 1, "V1", "Water", Couleurs.CYAN, false));
        setPiece(15, 1, new Vehicule(15, 1, "V1", "Methane", Couleurs.YELLOW, false));

        // glace (row 2)
        setPiece(1, 2, new Glace(1, 2, "G1", "None", Couleurs.CYAN));
        setPiece(3, 2, new Glace(3, 2, "G1", "None", Couleurs.CYAN));
        setPiece(5, 2, new Glace(5, 2, "G1", "None", Couleurs.CYAN));
        setPiece(7, 2, new Glace(7, 2, "G1", "None", Couleurs.CYAN));
        setPiece(9, 2, new Glace(9, 2, "G1", "None", Couleurs.CYAN));
        setPiece(11, 2, new Glace(11, 2, "G1", "None", Couleurs.CYAN));
        setPiece(13, 2, new Glace(13, 2, "G1", "None", Couleurs.CYAN));
        setPiece(15, 2, new Glace(15, 2, "G1", "None", Couleurs.CYAN));

        // glace (row 3)
        setPiece(0, 3, new Glace(1, 2, "G1", "None", Couleurs.CYAN));
        setPiece(2, 3, new Glace(3, 2, "G1", "None", Couleurs.CYAN));
        setPiece(4, 3, new Glace(5, 2, "G1", "None", Couleurs.CYAN));
        setPiece(6, 3, new Glace(7, 2, "G1", "None", Couleurs.CYAN));
        setPiece(8, 3, new Glace(9, 2, "G1", "None", Couleurs.CYAN));
        setPiece(10, 3, new Glace(11, 2, "G1", "None", Couleurs.CYAN));
        setPiece(12, 3, new Glace(13, 2, "G1", "None", Couleurs.CYAN));
        setPiece(14, 3, new Glace(15, 2, "G1", "None", Couleurs.CYAN));
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////
        
        // Initialisation des pièces du joueur 2
        // glace (row 1)
        setPiece(1, 15, new Glace(1, 15, "G2", "None", Couleurs.PURPLE));
        setPiece(3, 15, new Glace(3, 15, "G2", "None", Couleurs.PURPLE));
        setPiece(5, 15, new Glace(5, 15, "G2", "None", Couleurs.PURPLE));
        setPiece(7, 15, new Glace(7, 15, "G2", "None", Couleurs.PURPLE));
        setPiece(9, 15, new Glace(9, 15, "G2", "None", Couleurs.PURPLE));
        setPiece(11, 15, new Glace(11, 15, "G2", "None", Couleurs.PURPLE));
        setPiece(13, 15, new Glace(13, 15, "G2", "None", Couleurs.PURPLE));
        setPiece(15, 15, new Glace(15, 15, "G2", "None", Couleurs.PURPLE));

        // véhicules (alternance entre types méthanes et type eau)
        setPiece(1, 14, new Vehicule(1, 14, "V2", "Methane", Couleurs.YELLOW, false));
        setPiece(5, 14, new Vehicule(5, 14, "V2", "Water", Couleurs.CYAN, false));
        setPiece(7, 14, new Vehicule(7, 14, "V2", "Methane", Couleurs.YELLOW, false));
        setPiece(9, 14, new Vehicule(9, 14, "V2", "Water", Couleurs.CYAN, false));
        setPiece(11, 14, new Vehicule(11, 14, "V2", "Methane", Couleurs.YELLOW, false));
        setPiece(13, 14, new Vehicule(13, 14, "V2", "Water", Couleurs.CYAN, false));
        setPiece(15, 14, new Vehicule(15, 14, "V2", "Methane", Couleurs.YELLOW, false));
        
        // glace (row 2)
        setPiece(0, 13, new Glace(0, 13, "G2", "None", Couleurs.PURPLE));
        setPiece(2, 13, new Glace(2, 13, "G2", "None", Couleurs.PURPLE));
        setPiece(4, 13, new Glace(4, 13, "G2", "None", Couleurs.PURPLE));
        setPiece(6, 13, new Glace(6, 13, "G2", "None", Couleurs.PURPLE));
        setPiece(8, 13, new Glace(8, 13, "G2", "None", Couleurs.PURPLE));
        setPiece(10, 13, new Glace(10, 13, "G2", "None", Couleurs.PURPLE));
        setPiece(12, 13, new Glace(12, 13, "G2", "None", Couleurs.PURPLE));
        setPiece(14, 13, new Glace(14, 13, "G2", "None", Couleurs.PURPLE));


        // glace (row 3)
        setPiece(1, 12, new Glace(1, 12, "G2", "None", Couleurs.PURPLE));
        setPiece(3, 12, new Glace(3, 12, "G2", "None", Couleurs.PURPLE));
        setPiece(5, 12, new Glace(5, 12, "G2", "None", Couleurs.PURPLE));
        setPiece(7, 12, new Glace(7, 12, "G2", "None", Couleurs.PURPLE));
        setPiece(9, 12, new Glace(9, 12, "G2", "None", Couleurs.PURPLE));
        setPiece(11, 12, new Glace(11, 12, "G2", "None", Couleurs.PURPLE));
        setPiece(13, 12, new Glace(13, 12, "G2", "None", Couleurs.PURPLE));
        setPiece(15, 12, new Glace(15, 12, "G2", "None", Couleurs.PURPLE));
    }
    public static void addClouds() {
        Random random = new Random();
        int cloudsAdded = 0;

        while (cloudsAdded < 30) {
            int x = random.nextInt(16); // Générer une coordonnée x aléatoire entre 0 et 15
            int y = random.nextInt(16); // Générer une coordonnée y aléatoire entre 0 et 15

            // Vérifier si les coordonnées générées correspondent à une case vide sur le plateau
            if (board[y][x] == null) {
                // Ajouter un nuage de méthane si le nombre de nuages de méthane ajoutés est inférieur à 15
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

            // Récupérer les coordonnées de la pièce à déplacer
            System.out.print("Entrez les coordonnées de la pièce à déplacer: ");
            String source = scanner.next();
            int sourceX = source.charAt(0) - 'A';
            int sourceY = Integer.parseInt(source.substring(1)) - 1;

            //Vérification de la pièce, vérif que c'est bien : 1) pièce du joueur acutel, 2) pièce non nulle 3) pièce non nuage 4)type de pièce


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
            // Vérifier si les coordonnées sont valides et que la pièce appartient au joueur actuel
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
        // Vérifier si les coordonnées de la destination sont valides
        if (destX < 0 || destX >= board.length || destY < 0 || destY >= board[0].length) {
            System.out.println("La destination est en dehors du plateau.");
            return false; // La destination est en dehors du plateau
        }
    
        // Récupérer la pièce à déplacer
        Piece piece = board[sourceY][sourceX];
        System.out.println("Pièce à déplacer : " + piece.getIcon());

        /*    
        // Vérifier si la pièce appartient au joueur actuel (en fonction de sa couleur)
        ArrayList<Piece> currentPlayerPieces = (turn % 2 == 1) ? joueur1 : joueur2;
        if (!currentPlayerPieces.contains(piece)) {
            System.out.println("Vous ne pouvez déplacer que vos propres pièces.");
            return false; // La pièce n'appartient pas au joueur actuel
        }
        */

    
        // Vérifier si la destination est un nuage ou une case vide
        if (board[destY][destX] instanceof Nuage || board[destY][destX] == null) {
            // Si la destination est un nuage
            if (board[destY][destX] instanceof Nuage) {
                Nuage destinationNuage = (Nuage) board[destY][destX];
                Vehicule vehicule = (Vehicule) piece;
                vehicule.checkCollision(destinationNuage);
                return true; // Le mouvement est valide après la collision
            } else {
                return piece.deplacementAerien(sourceX, sourceY, destX, destY);
            }
        } else {
            // Vérifier si la destination est une case vide
            if (board[destY][destX] != null) {
                System.out.println("La destination est occupée par une autre pièce.");
                return false; // La destination est occupée par une autre pièce
            }
        }

        // Vérifier le type de déplacement en fonction de la pièce
        if (piece instanceof Glace || (piece instanceof Vehicule && !((Vehicule) piece).getState())) {
            // Déplacement terrestre
            System.out.println("Déplacement terrestre.");
            return piece.deplacementTerrestre(sourceX, sourceY, destX, destY);
        } else if (piece instanceof Nuage || (piece instanceof Vehicule && ((Vehicule) piece).getState())) {
            // Déplacement aérien
            // Vérifier s'il y a une glace sur le chemin
            if (isGlaceBetween(sourceX, sourceY, destX, destY)) {
                System.out.println("Il y a une glace sur le chemin, le mouvement n'est pas permis.");
                return false; // Il y a une glace sur le chemin, le mouvement n'est pas permis
            }
            System.out.println("Déplacement aérien.");
            return true;
        }
    
        // Si aucun des cas ci-dessus n'est vérifié, le mouvement est invalide
        System.out.println("Aucun mouvement valide n'a été trouvé.");
        return false;
    }
    
    //a appeler dans les fonctions de vérif 
    
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



//pas de mouvement des glaces ; on ne peut uniquement bouger les véhicules du jOUEUR 1 même au 2ème tour 
// 

// Faire plusieurs fonctions de validation de mouvements pour chaque type de pièce à incorporer dans
// "start game" et "play game" pour vérifier si le mouvement est valide
//empêcher le mouvement des nuages 