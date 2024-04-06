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
    public boolean deplacementTerrestre(int sourceX, int sourceY, int destX, int destY) {
        // Vérifier si la destination est adjacente à la source (verticalement ou horizontalement) ; return true si oui
        
        // Ajuster les coordonnées de destination pour les mouvements de grille infinie
        int adjustedX;
        int adjustedY;
        
        if (Board.isXBorder(sourceX)) {
            adjustedX = Math.abs(16 % (destX - sourceX));
            System.out.println("TEST (deplacementTerrestre) adjustedX : " + adjustedX);
            return (adjustedX == 1 && destY == sourceY);
        } else if (Board.isYBorder(sourceY)) {
            adjustedY = Math.abs(16 % (destY - sourceY));
            System.out.println("TEST (deplacementTerrestre) adjustedY: " + adjustedY);
            return (adjustedY == 1 && destX == sourceX);
        } else {
            return (Math.abs(destX - sourceX) == 1 && destY == sourceY) ||   // Mouvement horizontal
                   (Math.abs(destY - sourceY) == 1 && destX == sourceX) &&   // Mouvement vertical
                   (destX == sourceX || destY == sourceY);                   // Empêcher les mouvements diagonaux
        }
    }

    @Override
    public boolean deplacementAerien(int sourceX, int sourceY, int destX, int destY) {
        // Calculer la distance entre la source et la destination
        int deltaX = Math.abs(destX - sourceX);
        int deltaY = Math.abs(destY - sourceY);
    
        // Vérifier si la destination est à une distance valide pour un déplacement aérien
        return (deltaX == 2 && deltaY == 0) ||  // Déplacement horizontal
               (deltaX == 0 && deltaY == 2) ||  // Déplacement vertical
               (deltaX == 2 && deltaY == 2);    // Déplacement diagonal
    }
    
}
