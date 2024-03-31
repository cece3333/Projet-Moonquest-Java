public class Glace extends Piece {
    public Glace(int x, int y, String icon, String type, Couleurs couleur) {
        super(x, y, icon, "None", couleur); // 'G' pour l'icône de la glace
    }

    @Override
    public Couleurs getCouleur() {
        return this.couleur;
    }

    @Override
    public String getIcon() {
        return this.icon;
    }

    @Override
    public boolean deplacementTerrestre(int sourceX, int sourceY, int destX, int destY) {
        // Vérifier si la destination est adjacente à la source (verticalement ou horizontalement) ; return true si oui
        return (Math.abs(destX - sourceX) == 1 && destY == sourceY) ||   // Mouvement horizontal
               (Math.abs(destY - sourceY) == 1 && destX == sourceX) &&   // Mouvement vertical
               (destX == sourceX || destY == sourceY);                   // Empêcher les mouvements diagonaux
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
