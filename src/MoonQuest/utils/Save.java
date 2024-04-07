package utils;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import display.Board;
import pieces.Piece;
import pieces.Vehicule;

import java.io.BufferedReader;
import java.io.FileReader;

//petite classe, voir si je la fusionne pas avec Game.java


public class Save {

    public static void saveGame(Piece[][] board, ArrayList<Piece> joueur2, ArrayList<Piece> joueur1, int scoreJoueur1, int scoreJoueur2, int turn) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("game.ser"))) { //game.ser
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
    
    @SuppressWarnings("unchecked")
    public static void loadGame(ArrayList<Piece> joueur1, ArrayList<Piece> joueur2) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("game.ser"))) {
            // Désérialiser les objets
            Board.board = (Piece[][]) inputStream.readObject();
            Board.joueur2 = (ArrayList<Piece>) inputStream.readObject();
            Board.joueur1 = (ArrayList<Piece>) inputStream.readObject();
            Board.scoreJoueur1 = inputStream.readInt(); // Charger le score du joueur 1
            Board.scoreJoueur2 = inputStream.readInt(); // Charger le score du joueur 2
            Board.turn = inputStream.readInt(); // Charger le tour actuel
    
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
    
    public static void readMovesFile(String fichier_txt) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fichier_txt))) {
            String line;
            System.out.println("Mouvements effectués lors de la partie :");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Méthode pour vider le contenu d'un fichier
    public static void clearFile(String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(""); // Écrire une chaîne vide pour vider le fichier
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
            //methodes de sauvegardes des mouvements :
    public static void saveMoveToFile(String source, String destination, int turn, int scoreJoueur1, int scoreJoueur2, String fichier_txt) {
        try (FileWriter writer = new FileWriter(fichier_txt, true)) {
            Piece destinationPiece = Board.getPiece(destination.charAt(0) - 'A', Integer.parseInt(destination.substring(1)) - 1);
            String symbole = "x";
            if (destinationPiece != null) {
                symbole = ".";
            }
            writer.write(turn + ". " + source + "-" + destination + " " + symbole + " " + scoreJoueur1 + "-" + scoreJoueur2 + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
