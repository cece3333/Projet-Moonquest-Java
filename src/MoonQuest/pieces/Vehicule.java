package pieces;

import display.Board;
import utils.Couleurs;

public class Vehicule extends Piece {
    private boolean estActif;
    private int nuagesCaptures;

    public Vehicule(int x, int y, String icon, String type, Couleurs couleur, boolean estActif) {
        super(x, y, icon, type, couleur);
        this.estActif = estActif;
        estActif = false;
        this.nuagesCaptures = 0;

        // Assignation de la couleur en fonction du type de nuage
        if (type.equals("Methane")) {
            setCouleur(Couleurs.YELLOW); // Utilisation de la constante Couleurs.YELLOW
        } else if (type.equals("Eau")) {
            setCouleur(Couleurs.BLUE);; // Utilisation de la constante Couleurs.BLUE
        }

        if (estActif) {
            setCouleur(Couleurs.WHITE);
        }
    }

    @Override
    public Couleurs getCouleur() {
        return this.couleur;
    }

    @Override
    public String getIcon() {
        return this.icon;
    }

    public boolean getState() {
        return estActif;
    }

    public int getNuagesCaptures() {
        return nuagesCaptures;
    }
    
    public void setNuagesCaptures(int nuagesCaptures) {
        this.nuagesCaptures = nuagesCaptures;
    }

    //si le score de nuages capturés est égal à 3, le véhicule est activé (estActif = true) :
        // Méthode pour capturer un nuage
        public void captureNuage() {
            if (nuagesCaptures < 3) { // Vérifier si le véhicule peut encore capturer des nuages
                nuagesCaptures++;
                if (nuagesCaptures == 3) { // Activer le véhicule si le nombre de nuages capturés atteint 3
                    estActif = true;
                    System.out.println("Véhicule activé");
                    setCouleur(Couleurs.WHITE); // Mettre à jour la couleur du véhicule
                }
            }
        }
        
    @Override
    public boolean deplacementAerien(int sourceX, int sourceY, int destX, int destY) {
        // Ajuster les coordonnées de destination pour les mouvements de grille infinie
        int adjustedX;
        int adjustedY;
        boolean isYBorder = (((sourceY == 1) && (destY == 15)) || 
                             ((sourceY == 1) && (destY == 15)) || 
                             ((sourceY == 14) && (destY == 0)) || 
                             ((sourceY == 0) && (destY == 14)));
        boolean isXBorder = (((sourceX == 1) && (destX == 15)) || 
                             ((sourceX == 15) && (destX == 1)) || 
                             ((sourceX == 14) && (destX == 0)) || 
                             ((sourceX == 0) && (destX == 14)));

        if (isXBorder) {
            adjustedX = Math.abs(16 % (destX - sourceX));
            return (adjustedX == 2 && destY == sourceY);
        } else if (isYBorder) {
            adjustedY = Math.abs(16 % (destY - sourceY));
            return (adjustedY == 2 && (destX == sourceX));
        } else {
            return ((Math.abs(destX - sourceX) == 2) && (destY == sourceY)) ||   // Mouvement horizontal
                   ((Math.abs(destY - sourceY)) == 2 && (destX == sourceX)) ||   // Mouvement vertical
                   (((Math.abs(destX - sourceX)) == 2) && ((Math.abs(destY - sourceY) == 2)));      // Mouvement diagonal
        }
    }  

    @Override
    public boolean deplacementTerrestre(int sourceX, int sourceY, int destX, int destY) {
        // Vérifier si la destination est adjacente à la source (verticalement ou horizontalement) ; return true si oui
        
        // Ajuster les coordonnées de destination pour les mouvements de grille infinie
        int adjustedX;
        int adjustedY;
        boolean isYBorder = ((destY == 0) && (sourceY == Board.board.length - 1)) || 
                            ((sourceY == 0) && (destY == Board.board.length - 1));
        boolean isXBorder = ((destX == 0) && (sourceX == Board.board.length - 1)) || 
                            ((sourceX == 0) && (destX == Board.board.length - 1));
        
        if (isXBorder) {
            adjustedX = Math.abs(16 % (destX - sourceX));
            return ((adjustedX == 1) && (destY == sourceY));
        } else if (isYBorder) {
            adjustedY = Math.abs(16 % (destY - sourceY));
            return ((adjustedY == 1) && (destX == sourceX));
        } else {
            return ((Math.abs(destX - sourceX) == 1) && (destY == sourceY)) ||   // Mouvement horizontal
                   ((Math.abs(destY - sourceY) == 1) && (destX == sourceX)) &&   // Mouvement vertical
                   ((destX == sourceX) || (destY == sourceY));                   // Empêcher les mouvements diagonaux
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
            if ((((Vehicule) piece).getState()) && !(destination instanceof Glace)) {
                System.out.println("Un nuage a été supprimé par le véhicule!");
                return piece.deplacementAerien(sourceX, sourceY, destX, destY);
        
        //si la destination est un nuage de même type que le véhicule :
            } else if (destination instanceof Nuage && ((Vehicule) piece).getType().equals(((Nuage) destination).getType())) {
                if (piece.deplacementTerrestre(sourceX, sourceY, destX, destY)) {
                    ((Vehicule) piece).captureNuage();
                    // Mise à jour des scores:
                    if (Board.currentPlayer == Board.joueur1) {
                        Board.scoreJoueur1++; //important de laisser pour bien actualiser les scores de Board
                    } else {
                        Board.scoreJoueur2++;
                    }
                    System.out.println("Le véhicule a capturé un nuage de type " + ((Nuage) destination).getType() +"\n" + "Nuages capturés : " + ((Vehicule) piece).getNuagesCaptures());       
                    return true;           
        }
        } else { //Collision avec un nuage (de type différent) ou de la glace :
            System.out.println("Le véhicule a été détruit dans la collision ! Les nuages capturés sont perdus.");
            if (Board.currentPlayer == Board.joueur1) {
                Board.scoreJoueur1 -= ((Vehicule)piece).getNuagesCaptures();
            } else {
                Board.scoreJoueur2 -= ((Vehicule)piece).getNuagesCaptures();
            }
            // Supprimer le véhicule du plateau
            Board.board[sourceY][sourceX] = null;
            Board.turn++;
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
        return false; 
    }
    
}
