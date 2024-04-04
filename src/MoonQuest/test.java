public class test {
    

    //Dans playGame
        //On récupère les pièces aux coordonnées source et destination : 
        
        int sourceX;
        int sourceY;
        
        Piece pieceSource = Plateau2.board[sourceY][sourceX];
        Piece pieceDestination = Plateau2.board[destY][destX];

        //Vérification qu'on a bien une pièce sur la case source:
        if (pieceSource == null) {
            System.out.println("La case source est vide.");
            return false;
        } 
        
        if (!isValidDestination(destX, destY)) {
            return false;
        }
    //Dans Plateau2:

    //Dans Plateau2:

    //si l'instance est une glace : 
    public static boolean iceMove(Piece pieceSource, Piece pieceDestination, int sourceX, int sourceY, int destX, int destY) {
        //Si la pièce est une glace (déplacement où elle veut sauf sur une autre glace) ==> dans playGame
        if (pieceDestination != null) {
            Plateau2.board[destY][destX] = null;
        }
        
        return pieceSource.deplacementTerrestre(sourceX, sourceY, destX, destY);
    }

    //si l'instance est un véhicule et case vide :
    public static boolean vehiculeMove(Piece pieceSource, Piece pieceDestination, int sourceX, int sourceY, int destX, int destY) {
        if (!((Vehicule) pieceSource).getState()) {
            System.out.println("Déplacement terrestre.");
            return pieceSource.deplacementTerrestre(sourceX, sourceY, destX, destY);
        } else if ((((Vehicule) pieceSource).getState()) && !(Plateau2.isGlaceBetween(sourceX, sourceY, destX, destY))) {
            System.out.println("Déplacement aérien.");
            return pieceSource.deplacementAerien(sourceX, sourceY, destX, destY);
        } else {
            System.out.println("Déplacement aérien impossible : il y a une glace entre la source et la destination.");
            return false;
        }
    }

        //if destination is not null
        //if destination is a vehicule
        //if destination is a nuage
        //if destination is a glace
    
    //en faire une méthode uniquement propre à véhicule si possible /si case pas vide
    public static boolean checkVehiculeCollision(Piece pieceSource, Piece pieceDestination, int sourceX, int sourceY, int destX, int destY, int scoreJoueur1, int scoreJoueur2) {
        if (pieceDestination instanceof Vehicule) {
            // La destination est occupée par un autre véhicule
            System.out.println("La destination est occupée par un autre véhicule.");
            return false;
        } else if (pieceDestination instanceof Nuage) {
            // La destination est un nuage
            Nuage nuageDestination = (Nuage) pieceDestination;
            if (((Vehicule) pieceSource).getType().equals(nuageDestination.getType())) {
                // Types de nuages identiques
                if (pieceSource.deplacementTerrestre(sourceX, sourceY, destX, destY)) {
                    ((Vehicule) pieceSource).captureNuage();
                    // Mise à jour des scores
                    if (currentPlayer == joueur1) {
                        Plateau2.scoreJoueur1++;
                    } else {
                        Plateau2.scoreJoueur2++;
                    }
                    System.out.println("Le véhicule a capturé un nuage de type " + nuageDestination.getType());
                    System.out.println("Nombre de nuages capturés pour ce véhicule : " + ((Vehicule) pieceSource).getNuagesCaptures());
                    return true;
                }
            } else {
                // Types de nuages différents
                System.out.println("Le véhicule a été détruit dans la collision !");
                // Supprimer le véhicule du plateau
                board[sourceY][sourceX] = null;
                turn++; //pour éviter que le joueur joue deux fois
                return false; 
            }
        } else {
            // La destination n'est ni un véhicule ni un nuage
            System.out.println("La destination n'est ni un véhicule ni un nuage. Réessayez.");
            return false;
        }
        return false;
    }
    //dans playGame, else si l'instance est un nuage (return false)
       
       
       
       
       
       
       /* 
        //Cas où la destination est occupée par une autre pièce :
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
                    if (currentPlayer == joueur1) {
                        Plateau2.scoreJoueur1++; //important de laisser pour bien actualiser les scores de Plateau2
                    } else {
                        Plateau2.scoreJoueur2++;
                    }
                    System.out.println("Déplacement terrestre.");
                    System.out.println("Le véhicule a capturé un nuage de type " + ((Nuage) destination).getType());
                    System.out.println("Nombre de nuages capturés pour ce véhicule : " + ((Vehicule) piece).getNuagesCaptures());
                    
                    return true;
            //???????
            //Collision avec un nuage (de type différent) ou de la glace :
            }
        } else { 
            System.out.println("Le véhicule a été détruit dans la collision !");
            // Supprimer le véhicule du plateau
            board[sourceY][sourceX] = null;
            turn++;
            return false; //*/
    }


    public static boolean isValidMove(int sourceX, int sourceY, int destX, int destY, int scoreJoueur1, int scoreJoueur2) {



        

        
        //Vérification lorsqu'on déplace un véhicule :
        } else if (piece instanceof Vehicule) {
            
            //Cas où la destination est occupée par une autre pièce :
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
                        if (currentPlayer == joueur1) {
                            Plateau2.scoreJoueur1++; //important de laisser pour bien actualiser les scores de Plateau2
                        } else {
                            Plateau2.scoreJoueur2++;
                        }
                        System.out.println("Déplacement terrestre.");
                        System.out.println("Le véhicule a capturé un nuage de type " + ((Nuage) destination).getType());
                        System.out.println("Nombre de nuages capturés pour ce véhicule : " + ((Vehicule) piece).getNuagesCaptures());
                        
                        return true;
                //???????
                //Collision avec un nuage (de type différent) ou de la glace :
                }
            } else { 
                System.out.println("Le véhicule a été détruit dans la collision !");
                // Supprimer le véhicule du plateau
                board[sourceY][sourceX] = null;
                turn++;
                return false; //pour éviter que le joueur joue deux fois
            }
            
            //Si la destination est vide, on peut déplacer le véhicule (en fonction de son état)

        //à implémenter !!! comment bouger les nuages ?
        } else if (piece instanceof Nuage) {
            return false;
        }

        return false;
    }
}
