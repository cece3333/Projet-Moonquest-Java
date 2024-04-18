package pieces;

import java.io.Serializable;

import utils.Colors;

/**
 * La classe abstraite Piece permet de créer des instances de Pieces aux attributs communs.
 */
public abstract class Piece implements Serializable{
    protected int x; 
    protected int y; 
    protected String icon; 
    protected String type; 
    protected Colors color;
    
    protected static final int boardSize = 16; //taille d'un plateau (redéfinie ici pour éviter les réferences circulaires avec Board.java)
    
    /**
     * Constructeur de la classe Piece.
     *
     * @param x     La coordonnée x de la pièce.
     * @param y     La coordonnée y de la pièce.
     * @param icon  L'icône représentant la pièce.
     * @param type  Le type de la pièce.
     * @param color La couleur de la pièce.
     */
    public Piece(int x, int y, String icon, String type, Colors color) {
        this.x = x;
        this.y = y;
        this.icon = icon;
        this.type = type;
        this.color = color;
    }
 
        /**
         * Getter pour la coordonnée x de la pièce.
         *
         * @return La coordonnée x de la pièce.
         */
        public int getX() {
            return x;
        }

        /**
         * Setter pour la coordonnée x de la pièce.
         *
         * @param x La nouvelle coordonnée x de la pièce.
         */
        public void setX(int x) {
            this.x = x;
        }

        /**
         * Getter pour la coordonnée y de la pièce.
         *
         * @return La coordonnée y de la pièce.
         */
        public int getY() {
            return y;
        }

        /**
         * Setter pour la coordonnée y de la pièce.
         *
         * @param y La nouvelle coordonnée y de la pièce.
         */
        public void setY(int y) {
            this.y = y;
        }

        /**
         * Getter pour le type de la pièce.
         *
         * @return Le type de la pièce.
         */
        public String getType() {
            return type;
        }

        /**
         * Setter pour le type de la pièce.
         *
         * @param type Le nouveau type de la pièce.
         */
        public void setType(String type) {
            this.type = type;
        }

        /**
         * Getter pour l'icône de la pièce.
         *
         * @return L'icône de la pièce.
         */
        public String getIcon() {
            return icon;
        }

        /**
         * Setter pour l'icône de la pièce.
         *
         * @param icon La nouvelle icône de la pièce.
         */
        public void setIcon(String icon) {
            this.icon = icon;
        }

        /**
         * Getter pour la couleur de la pièce.
         *
         * @return La couleur de la pièce.
         */
        public Colors getColor() {
            return color;
        }

        /**
         * Setter pour la couleur de la pièce.
         *
         * @param color La nouvelle couleur de la pièce.
         */
        public void setColor(Colors color) {
            this.color = color;
        }

        /**
         * Méthode vérifiant la validité d'un déplacement aérien 
         * (déplacement de 2 cases dans toutes les directions).
         *
         * @param sourceX La coordonnée x de la position de départ.
         * @param sourceY La coordonnée y de la position de départ.
         * @param destX   La coordonnée x de la position d'arrivée.
         * @param destY   La coordonnée y de la position d'arrivée.
         * @return true si le déplacement est valide, false sinon.
         */
        public boolean aerialMove(int sourceX, int sourceY, int destX, int destY) {
            int adjustedX;
            int adjustedY;
            boolean isYBorder = (((sourceY == 1) && (destY == boardSize - 1)) ||
                                ((sourceY == 1) && (destY == boardSize - 1)) || 
                                ((sourceY == boardSize - 2) && (destY == 0)) || 
                                ((sourceY == 0) && (destY == boardSize - 2)));
            boolean isXBorder = (((sourceX == 1) && (destX == boardSize - 1)) || 
                                ((sourceX == boardSize - 1) && (destX == 1)) || 
                                ((sourceX == boardSize - 2) && (destX == 0)) || 
                                ((sourceX == 0) && (destX == boardSize - 2)));

            if (isXBorder) {
                adjustedX = Math.abs(boardSize % (destX - sourceX));
                return (adjustedX == 2 && destY == sourceY);
            } else if (isYBorder) {
                adjustedY = Math.abs(boardSize % (destY - sourceY));
                return (adjustedY == 2 && (destX == sourceX));
            } else {
                return ((Math.abs(destX - sourceX) == 2) && (destY == sourceY)) ||
                    ((Math.abs(destY - sourceY)) == 2 && (destX == sourceX)) ||   
                    (((Math.abs(destX - sourceX)) == 2) && ((Math.abs(destY - sourceY) == 2)));     
            }
        }  

        /**
         * Méthode vérifiant la validité d'un déplacement terrestre 
         * (déplacement d'une case dans toutes les directions).
         *
         * @param sourceX La coordonnée x de la position de départ.
         * @param sourceY La coordonnée y de la position de départ.
         * @param destX   La coordonnée x de la position d'arrivée.
         * @param destY   La coordonnée y de la position d'arrivée.
         * @return true si le déplacement est valide, false sinon.
         */
        public boolean terrestrialMove(int sourceX, int sourceY, int destX, int destY) {
            int adjustedX;
            int adjustedY;
            boolean isYBorder = ((destY == 0) && (sourceY == boardSize - 1)) || 
                                ((sourceY == 0) && (destY == boardSize - 1));
            boolean isXBorder = ((destX == 0) && (sourceX == boardSize - 1)) || 
                                ((sourceX == 0) && (destX == boardSize - 1));
            
            if (isXBorder) {
                adjustedX = Math.abs(16 % (destX - sourceX));
                return ((adjustedX == 1) && (destY == sourceY));
            } else if (isYBorder) {
                adjustedY = Math.abs(16 % (destY - sourceY));
                return ((adjustedY == 1) && (destX == sourceX));
            } else {
                return ((Math.abs(destX - sourceX) == 1) && (destY == sourceY)) ||
                    ((Math.abs(destY - sourceY) == 1) && (destX == sourceX)) &&   
                    ((destX == sourceX) || (destY == sourceY));                   
            }
        }
    }
    