package game;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Random;

import display.Board;
import pieces.*;
import utils.Save;

/**
 * La classe GameLogic contient les méthodes permettant de gérer la logique du jeu.
 */
public class GameLogic {
    
    public static int turn = 1;
    public static int scoreJoueur1 = 0;
    public static int scoreJoueur2 = 0;

    /**
     * Vérifie si la partie est terminée en fonction des scores et des nuages restants.
     *
     * @param scoreJoueur1 Score du joueur 1.
     * @param scoreJoueur2 Score du joueur 2.
     * @return true si la partie est terminée, sinon false.
     */
    public static boolean isGameOver(int scoreJoueur1, int scoreJoueur2) {

        int countClouds = 0;
        int[] pieceCounts = new int[2];
        pieceCounts[0] = 0;
        pieceCounts[1] = 0;

        for (Piece[] row : Board.board) {
            for (Piece piece : row) {
                if (piece != null) {
                    if (piece instanceof Nuage) {
                        countClouds++;
                        continue;
                    } else if (Board.getJoueur1().contains(piece)) {
                        pieceCounts[0]++;
                    } else if (Board.getJoueur2().contains(piece)) {
                        pieceCounts[1]++;
                    }
                }
            }
        }
        System.out.println("Nombre de nuages restants : " + countClouds);

        if (pieceCounts[0] == 0 || pieceCounts[1] == 0) {
            System.out.println("La partie est terminée : un des joueurs n'a plus de pièces.");
            if (pieceCounts[0] == 0) {
                System.out.println("Un des joueurs n'a plus de pièces ; le joueur 2 remporte la partie par forfait");
            } else {
                System.out.println("Un des joueurs n'a plus de pièces ; le joueur 1 remporte la partie par forfait");
            }
            return true;
        
        }
        
        if (scoreJoueur1 >= 16 || scoreJoueur2 >= 16) {
            System.out.println("La partie est terminée :");
            if (scoreJoueur1 > scoreJoueur2) {
                System.out.println("Le joueur 1 a remporté la partie avec " + scoreJoueur1 + " nuages capturés.");
            } else if (scoreJoueur1 < scoreJoueur2) {
                System.out.println("Le joueur 2 a remporté la partie avec " + scoreJoueur2 + " nuages capturés.");
            }
            return true;
        }

        if (countClouds == 0) {
            System.out.println("La partie est terminée : il n'y a plus de nuages sur le plateau.");
            if (scoreJoueur1 > scoreJoueur2) {
                System.out.println("Le joueur 1 remporte la partie avec " + scoreJoueur1 + " nuages capturés.");
            } else if (scoreJoueur1 < scoreJoueur2) {
                System.out.println("Le joueur 2 remporte la partie avec " + scoreJoueur2 + " nuages capturés.");
            } else if (((scoreJoueur1 == scoreJoueur2) && (scoreJoueur1 + scoreJoueur2 == 30))) {
                System.out.println("Match nul !");
            }
            return true;
        }
    
        return false;
    }

    /**
     * Vérifie si un déplacement est valide en fonction des règles du jeu.
     *
     * @param sourceX      Abscisse de la position source.
     * @param sourceY      Ordonnée de la position source.
     * @param destX        Abscisse de la position destination.
     * @param destY        Ordonnée de la position destination.
     * @param scoreJoueur1 Score du joueur 1.
     * @param scoreJoueur2 Score du joueur 2.
     * @return true si le déplacement est valide, sinon false.
     */
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
            if (isGlaceMove(piece, destination, sourceX, sourceY, destX, destY)) {
                return true;
            }
    
        //Vérification lorsqu'on déplace un véhicule :
        } else if (piece instanceof Vehicule) {
            if (isVehiculeMove(piece, destination, sourceX, sourceY, destX, destY)) {
                return true;
            }
        } else if (piece instanceof Nuage && destination instanceof Vehicule) {
            if (((Vehicule) destination).getType().equals(((Nuage) piece).getType())) {
                System.out.println("Véhicule détruit ; les nuages capturés sont perdus.");
                if (Board.currentPlayer == Board.getJoueur1()) {
                    GameLogic.scoreJoueur2 -= ((Vehicule) destination).getNuagesCaptured();
                } else {
                    GameLogic.scoreJoueur1 -= ((Vehicule) destination).getNuagesCaptured();
                }
            }
            return false;

    
        } else { //condition reacheable?
            System.out.println("Mouvement invalide.");
            return false;
        }
        return false;
    }

    /**
     * Vérifie si un déplacement de glace est valide en fonction des règles du jeu.
     *
     * @param piece   La pièce à déplacer.
     * @param destX   Abscisse de la position destination.
     * @param destY   Ordonnée de la position destination.
     * @param sourceX Abscisse de la position source.
     * @param sourceY Ordonnée de la position source.
     * @return true si le déplacement de glace est valide, sinon false.
     */
    public static boolean isGlaceMove(Piece piece, Piece destination, int sourceX, int sourceY, int destX, int destY) {
        if ((destination != null) && (!(Board.currentPlayer.contains(destination)))) {
            if (piece.terrestrialMove(sourceX, sourceY, destX, destY)) {
                System.out.println("La glace a écrasé la pièce " + destination.getIcon());
                Board.board[destY][destX] = null;
                } if (destination instanceof Vehicule) {
                    System.out.println("Véhicule détruit ; les nuages capturés sont perdus.");
                    if (Board.currentPlayer == Board.getJoueur1()) {
                        GameLogic.scoreJoueur2 -= ((Vehicule)destination).getNuagesCaptured();
                    } else {
                        GameLogic.scoreJoueur1 -= ((Vehicule)destination).getNuagesCaptured();
                    }
                return true;
            }
        } if (Board.currentPlayer.contains(destination)) { 
            System.out.println("La glace ne peut pas écraser ses propres pièces.");
            return false;
        } else {
            return piece.terrestrialMove(sourceX, sourceY, destX, destY);
        }
    }

    /**
     * Vérifie si un déplacement de véhicule est valide en fonction des règles du jeu.
     *
     * @param piece   La pièce à déplacer.
     * @param destX   Abscisse de la position destination.
     * @param destY   Ordonnée de la position destination.
     * @param sourceX Abscisse de la position source.
     * @param sourceY Ordonnée de la position source.
     * @return true si le déplacement de véhicule est valide, sinon false.
     */
    public static boolean isVehiculeMove(Piece piece, Piece destination, int sourceX, int sourceY, int destX, int destY) {

        //1. Cas où la destination est occupée par une autre pièce :
        if (destination instanceof Vehicule) { //si c'est un autre véhicule
        System.out.println("La destination est occupée par un autre véhicule.");
        return false;
    
    //cas où la case est un nuage ou de la glace : 
        } else if ((!(destination instanceof Vehicule)) && (destination != null)) {
            System.out.println("La destination est occupée par un nuage ou de la glace, collision en cours...");
            //si le véhicule est activé, il peut supprimer n'importe quel nuage
            if ((((Vehicule) piece).isActive()) && !(destination instanceof Glace)) {
                System.out.println("Un nuage a été supprimé par le véhicule!");
                return piece.aerialMove(sourceX, sourceY, destX, destY);
        
        //si la destination est un nuage de même type que le véhicule :
            } else if (((Vehicule) piece).canCapture(destination)) {
                if (piece.terrestrialMove(sourceX, sourceY, destX, destY)) {
                    ((Vehicule) piece).captureNuage();
                    // Mise à jour des scores:
                    if (Board.currentPlayer == Board.getJoueur1()) {
                        GameLogic.scoreJoueur1++; //important de laisser pour bien actualiser les scores de Board
                    } else {
                        GameLogic.scoreJoueur2++;
                    }
                    System.out.println("Le véhicule a capturé un nuage de type " + ((Nuage) destination).getType() +"\n" + "Nuages capturés : " + ((Vehicule) piece).getNuagesCaptured());       
                    return true;           
        }
        } else { //Collision avec un nuage (de type différent) ou de la glace :
            System.out.println("Le véhicule a été détruit dans la collision ! Les nuages capturés sont perdus.");
            if (Board.currentPlayer == Board.getJoueur1()) {
                GameLogic.scoreJoueur1 -= ((Vehicule)piece).getNuagesCaptured();
            } else {
                GameLogic.scoreJoueur2 -= ((Vehicule)piece).getNuagesCaptured();
            }
            // Supprimer le véhicule du plateau
            Board.board[sourceY][sourceX] = null;
            GameLogic.turn++;
            return false; //pour éviter que le joueur joue deux fois
    }
    
    //Si la destination est vide, on peut déplacer le véhicule (en fonction de son état)
    } if (destination == null) {
            if (!((Vehicule) piece).isActive()) {
                System.out.println("Déplacement terrestre.");
                return piece.terrestrialMove(sourceX, sourceY, destX, destY);
            } else if ((((Vehicule) piece).isActive()) && !(Board.IsGlaceBetween(sourceX, sourceY, destX, destY))) {
                System.out.println("Déplacement aérien.");
                return piece.aerialMove(sourceX, sourceY, destX, destY);
            } else {
                System.out.println("Déplacement aérien impossible : il y a une glace entre la source et la destination.");
                return false;
            }
        }
        return false; 
    }
}
