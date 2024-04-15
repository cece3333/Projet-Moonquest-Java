package game;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Random;
import java.util.Scanner;

import display.Board;
import pieces.*;
import utils.Save;

public class Game {
    private static final Scanner scanner = new Scanner(System.in);

    public static boolean isGameOver(int scoreJoueur1, int scoreJoueur2) {

        // Initialiser le compteur de nuages restants
        int countClouds = 0;
        int[] pieceCounts = new int[2]; // Index 0 pour joueur 1, index 1 pour joueur 2
        pieceCounts[0] = 0;
        pieceCounts[1] = 0;

        for (Piece[] row : Board.board) {
            for (Piece piece : row) {
                if (piece != null) {
                    if (piece instanceof Nuage) {
                        countClouds++;
                        continue;
                    } else if (Board.joueur1.contains(piece)) {
                        pieceCounts[0]++;
                    } else if (Board.joueur2.contains(piece)) {
                        pieceCounts[1]++;
                    }
                }
            }
        }
        System.out.println("Nombre de nuages restants : " + countClouds);
        System.out.println("Pièces du joueur 1 restantes : " + pieceCounts[0]);
        System.out.println("Pièces du joueur 2 restantes : " + pieceCounts[1]);

            // Vérifier si l'un des joueurs n'a plus de pièces
        if (pieceCounts[0] == 0 || pieceCounts[1] == 0) {
            System.out.println("Un des joueurs n'a plus de pièces.");
            if (pieceCounts[0] == 0) {
                System.out.println("Un des joueurs n'a plus de pièces ; le joueur 2 remporte la partie par forfait");
            } else {
                System.out.println("Un des joueurs n'a plus de pièces ; le joueur 1 remporte la partie par forfait");
            }
            return true;
        
        }
        
        if (scoreJoueur1 >= 16 || scoreJoueur2 >= 16 || countClouds == 0) {
            System.out.println("La partie est terminée !");
            if (scoreJoueur1 > scoreJoueur2) {
                System.out.println("Le joueur 1 (CYAN) a remporté la partie avec " + scoreJoueur1 + " nuages capturés.");
            } else if (scoreJoueur1 < scoreJoueur2) {
                System.out.println("Le joueur 2 (PURPLE) a remporté la partie avec " + scoreJoueur2 + " nuages capturés.");
            } else if (((scoreJoueur1 == scoreJoueur2) && (scoreJoueur1 + scoreJoueur2 == 30)) || countClouds == 0) {
                System.out.println("Match nul !");
            }
            return true;
        }

        System.out.println("La partie continue.");
    
        return false;
    }

    
    public static boolean isValidMove(int sourceX, int sourceY, int destX, int destY, int scoreJoueur1, int scoreJoueur2) {

        if (destX < 0 || destY < 0 || destY >= Board.board[0].length) {
            System.out.println("La destination est en dehors du plateau.");
            return false;
        }
    
        //On récupère les pièces aux coordonnées source et destination : 
        Piece piece = Board.board[sourceY][sourceX];
        Piece destination = Board.board[destY][destX];
    
        //Vérification qu'on a bien une pièce sur la case source:
        if (piece == null) {
            System.out.println("La case source est vide.");
            return false;
        }
    
        //Si la pièce est une glace (déplacement où elle veut sauf sur les propres pièces du joueur) :
        if ((piece instanceof Glace)) {
            if (Glace.isGlaceMove(piece, destination, sourceX, sourceY, destX, destY)) {
                return true;
            }
    
        //Vérification lorsqu'on déplace un véhicule :
        } else if (piece instanceof Vehicule) {
            if (Vehicule.isVehiculeMove(piece, destination, sourceX, sourceY, destX, destY)) {
                return true;
            }
        } else if (piece instanceof Nuage && destination instanceof Vehicule) {
            if (((Vehicule) destination).getType().equals(((Nuage) piece).getType())) {
                System.out.println("Véhicule détruit ; les nuages capturés sont perdus.");
                if (Board.currentPlayer == Board.joueur1) {
                    Board.scoreJoueur2 -= ((Vehicule) destination).getNuagesCaptures();
                } else {
                    Board.scoreJoueur1 -= ((Vehicule) destination).getNuagesCaptures();
                }
            }
            return false;

    
        } else { //condition reacheable?
            System.out.println("Mouvement invalide.");
            return false;
        }
        return false;
    }   
}
