import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

//petite classe, voir si je la fusionne pas avec Game.java


public class Save {

    public static void saveGame(Piece[][] board, ArrayList<Piece> joueur2, ArrayList<Piece> joueur1, int scoreJoueur1, int scoreJoueur2, int turn) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("game.ser"))) {
            outputStream.writeObject(board);
            outputStream.writeObject(joueur2);
            outputStream.writeObject(joueur1);
            outputStream.writeInt(scoreJoueur1);
            outputStream.writeInt(scoreJoueur2);
            outputStream.writeInt(turn);
            
            // Sérialiser les scores des véhicules pour le joueur 1
            for (Piece piece : joueur1) {
                if (piece instanceof Vehicule) {
                    int scoreVehicule = ((Vehicule) piece).getNuagesCaptures();
                    outputStream.writeInt(scoreVehicule);
                }
            }
            
            // Sérialiser les scores des véhicules pour le joueur 2
            for (Piece piece : joueur2) {
                if (piece instanceof Vehicule) {
                    int scoreVehicule = ((Vehicule) piece).getNuagesCaptures();
                    outputStream.writeInt(scoreVehicule);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings("unchecked") //supprime l'averstissement de type non vérifié
    public static void loadGame(int turn, int scoreJoueur1, int scoreJoueur2, ArrayList<Piece> joueur1, ArrayList<Piece> joueur2) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("game.ser"))) {
            // Désérialiser les objets
            Plateau2.board = (Piece[][]) inputStream.readObject();
            Plateau2.joueur2 = (ArrayList<Piece>) inputStream.readObject();
            Plateau2.joueur1 = (ArrayList<Piece>) inputStream.readObject();
            scoreJoueur1 = inputStream.readInt(); // Charger le score du joueur 1
            scoreJoueur2 = inputStream.readInt(); // Charger le score du joueur 2
            turn = inputStream.readInt(); // Charger le tour actuel
    
            // Charger les scores des véhicules pour le joueur 1
            for (Piece piece : joueur1) {
                if (piece instanceof Vehicule) {
                    int scoreVehicule = inputStream.readInt();
                    ((Vehicule) piece).setNuagesCaptures(scoreVehicule);
                }
            }
    
            // Charger les scores des véhicules pour le joueur 2
            for (Piece piece : joueur2) {
                if (piece instanceof Vehicule) {
                    int scoreVehicule = inputStream.readInt();
                    ((Vehicule) piece).setNuagesCaptures(scoreVehicule);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //methodes de sauvegardes des mouvements :
    public static void saveMoveToFile(String source, String destination, int turn, int scoreJoueur1, int scoreJoueur2) {
        try (FileWriter writer = new FileWriter("moves.txt", true)) {
            String symbole = "x";
            Piece destinationPiece = Plateau2.getPiece(destination.charAt(0) - 'A', Integer.parseInt(destination.substring(1)) - 1);
            if (destinationPiece != null) {
                symbole = ".";
            }
            writer.write(turn + ". " + source + "-" + destination + " " + symbole + " " + scoreJoueur1 + "-" + scoreJoueur2 + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
