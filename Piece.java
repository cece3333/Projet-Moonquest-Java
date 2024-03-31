public abstract class Piece {
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

        public abstract boolean deplacementTerrestre(int sourceX, int sourceY, int destX, int destY);

        public abstract boolean deplacementAerien(int sourceX, int sourceY, int destX, int destY);
    }
    