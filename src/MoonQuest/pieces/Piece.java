package pieces;

import java.io.Serializable;

import display.Board;
import utils.Couleurs;

public abstract class Piece implements Serializable{
    protected int x; // Position x sur le Plateau
    protected int y; // Position y sur le Plateau
    protected String icon; // Icône de la pièce
    protected String type; // Type de l'objet (Méthane, Eau, None pour la glace et cases vides)
    protected Couleurs couleur; // Couleur de la pièce

    public Piece(int x, int y, String icon, String type, Couleurs couleur) {
        this.x = x;
        this.y = y;
        this.icon = icon;
        this.type = type;
        this.couleur = couleur;
    }
 
        // Méthodes getters et setters pour les variables privées
        public int getX() {
            return x;
        }
    
        public void setX(int x) {
            this.x = x;
        }
    
        public int getY() {
            return y;
        }
    
        public void setY(int y) {
            this.y = y;
        }
    
        public String getType() {
            return type;
        }
    
        public void setType(String type) {
            this.type = type;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public Couleurs getCouleur() {
            return couleur;
        }

        public void setCouleur(Couleurs couleur) {
            this.couleur = couleur;
        }

        public String getPlayer() {
        // Extrait le numéro du joueur à partir de l'icône de la pièce
        // Les icônes des pièces des joueurs 1 commencent par "G1" ou "V1"
        // Les icônes des pièces des joueurs 2 commencent par "G2" ou "V2"
        // Donc, pour déterminer le joueur, nous pouvons utiliser le premier caractère après la première lettre
        char playerChar = icon.charAt(1);
        
        // Convertit le caractère du joueur en chaîne et le retourne
        return Character.toString(playerChar);
    }

        public boolean deplacementAerien(int sourceX, int sourceY, int destX, int destY) {
            //Mouvements de grille infinie
            int adjustedX;
            int adjustedY;
            boolean isYBorder = (((sourceY == 1) && (destY == Board.BOARD_SIZE - 1)) || //BOARDSIZE
                                ((sourceY == 1) && (destY == Board.BOARD_SIZE - 1)) || 
                                ((sourceY == Board.BOARD_SIZE - 2) && (destY == 0)) || 
                                ((sourceY == 0) && (destY == Board.BOARD_SIZE - 2)));
            boolean isXBorder = (((sourceX == 1) && (destX == Board.BOARD_SIZE - 1)) || 
                                ((sourceX == Board.BOARD_SIZE - 1) && (destX == 1)) || 
                                ((sourceX == Board.BOARD_SIZE - 2) && (destX == 0)) || 
                                ((sourceX == 0) && (destX == Board.BOARD_SIZE - 2)));

            if (isXBorder) {
                adjustedX = Math.abs(Board.BOARD_SIZE % (destX - sourceX));
                return (adjustedX == 2 && destY == sourceY);
            } else if (isYBorder) {
                adjustedY = Math.abs(Board.BOARD_SIZE % (destY - sourceY));
                return (adjustedY == 2 && (destX == sourceX));
            } else {
                return ((Math.abs(destX - sourceX) == 2) && (destY == sourceY)) ||   // Mouvement horizontal
                    ((Math.abs(destY - sourceY)) == 2 && (destX == sourceX)) ||   // Mouvement vertical
                    (((Math.abs(destX - sourceX)) == 2) && ((Math.abs(destY - sourceY) == 2)));      // Mouvement diagonal
            }
        }  

        public boolean deplacementTerrestre(int sourceX, int sourceY, int destX, int destY) {
            // Vérifier si la destination est adjacente à la source (verticalement ou horizontalement) ; return true si oui
            
            //Mouvements de grille infinie
            int adjustedX;
            int adjustedY;
            boolean isYBorder = ((destY == 0) && (sourceY == Board.BOARD_SIZE - 1)) || 
                                ((sourceY == 0) && (destY == Board.BOARD_SIZE - 1));
            boolean isXBorder = ((destX == 0) && (sourceX == Board.BOARD_SIZE - 1)) || 
                                ((sourceX == 0) && (destX == Board.BOARD_SIZE - 1));
            
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
    }
    