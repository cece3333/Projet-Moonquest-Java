package pieces;

import display.Board;
import utils.Colors;
import game.Game;

public class Vehicule extends Piece {
    private boolean isActivated;
    private int nuagesCaptures;

    public Vehicule(int x, int y, String icon, String type, Colors color, boolean isActivated) {
        super(x, y, icon, type, color);
        this.isActivated = isActivated;
        isActivated = false;
        this.nuagesCaptures = 0;

        // Assignation de la color en fonction du type de nuage
        if (type.equals("Methane")) {
            setColor(Colors.YELLOW); // Utilisation de la constante Colors.YELLOW
        } else if (type.equals("Eau")) {
            setColor(Colors.BLUE);; // Utilisation de la constante Colors.BLUE
        }

        if (isActivated) {
            setColor(Colors.WHITE);
        }
    }

    public boolean isActive() {
        return isActivated;
    }

    public int getNuagesCaptures() {
        return nuagesCaptures;
    }
    
    public void setNuagesCaptures(int nuagesCaptures) {
        this.nuagesCaptures = nuagesCaptures;
    }

    public boolean canCapture(Piece piece) {
        if ((piece instanceof Nuage) && (isActivated == false)) {
            if (((Nuage) piece).getType().equals(this.getType())) {
                return true;
            }
        }
        return false;
    }

    //si le score de nuages capturés est égal à 3, le véhicule est activé (isActivated = true) :
    // Méthode pour capturer un nuage
    public void captureNuage() {
        if (nuagesCaptures < 3) { // Vérifier si le véhicule peut encore capturer des nuages
            nuagesCaptures++;
            if (nuagesCaptures == 3) { // Activer le véhicule si le nombre de nuages capturés atteint 3
                isActivated = true;
                System.out.println("Véhicule activé");
                setColor(Colors.WHITE); // Mettre à jour la color du véhicule
            }
        }
    }
    
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
                return piece.deplacementAerien(sourceX, sourceY, destX, destY);
        
        //si la destination est un nuage de même type que le véhicule :
            } else if (((Vehicule) piece).canCapture(destination)) {
                if (piece.deplacementTerrestre(sourceX, sourceY, destX, destY)) {
                    ((Vehicule) piece).captureNuage();
                    // Mise à jour des scores:
                    if (Board.currentPlayer == Board.joueur1) {
                        Game.scoreJoueur1++; //important de laisser pour bien actualiser les scores de Board
                    } else {
                        Game.scoreJoueur2++;
                    }
                    System.out.println("Le véhicule a capturé un nuage de type " + ((Nuage) destination).getType() +"\n" + "Nuages capturés : " + ((Vehicule) piece).getNuagesCaptures());       
                    return true;           
        }
        } else { //Collision avec un nuage (de type différent) ou de la glace :
            System.out.println("Le véhicule a été détruit dans la collision ! Les nuages capturés sont perdus.");
            if (Board.currentPlayer == Board.joueur1) {
                Game.scoreJoueur1 -= ((Vehicule)piece).getNuagesCaptures();
            } else {
                Game.scoreJoueur2 -= ((Vehicule)piece).getNuagesCaptures();
            }
            // Supprimer le véhicule du plateau
            Board.board[sourceY][sourceX] = null;
            Game.turn++;
            return false; //pour éviter que le joueur joue deux fois
    }
    
    //Si la destination est vide, on peut déplacer le véhicule (en fonction de son état)
    } if (destination == null) {
            if (!((Vehicule) piece).isActive()) {
                System.out.println("Déplacement terrestre.");
                return piece.deplacementTerrestre(sourceX, sourceY, destX, destY);
            } else if ((((Vehicule) piece).isActive()) && !(Board.IsGlaceBetween(sourceX, sourceY, destX, destY))) {
                System.out.println("Déplacement aérien.");
                return piece.deplacementAerien(sourceX, sourceY, destX, destY);
            } else {
                System.out.println("Déplacement aérien impossible : il y a une glace entre la source et la destination.");
                return false;
            }
        }
        return false; 
    }
    
}
