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
import game.GameLogic;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Classe contenant des méthodes pour sauvegarder et charger l'état du jeu, ainsi que pour lire et écrire des fichiers.
 */
public class Save {

    /**
     * Sauvegarde l'état du jeu dans un fichier sérialisé.
     *
     * @param board        Le tableau de pièces représentant le plateau de jeu.
     * @param joueur2      La liste des pièces du joueur 2.
     * @param joueur1      La liste des pièces du joueur 1.
     * @param scoreJoueur1 Le score du joueur 1.
     * @param scoreJoueur2 Le score du joueur 2.
     * @param turn         Le tour actuel.
     */
    public static void saveGame(Piece[][] board, ArrayList<Piece> joueur2, ArrayList<Piece> joueur1, int scoreJoueur1, int scoreJoueur2, int turn) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("utils/game.ser"))) {
            outputStream.writeObject(board);
            outputStream.writeObject(joueur2);
            outputStream.writeObject(joueur1);
            outputStream.writeInt(scoreJoueur1);
            outputStream.writeInt(scoreJoueur2);
            outputStream.writeInt(turn);
            
            // Sérialiser les scores des véhicules pour le joueur 1
            for (Piece piece : joueur1) {
                if (piece instanceof Vehicule) {
                    int scoreVehicule = ((Vehicule) piece).getNuagesCaptured();
                    outputStream.writeInt(scoreVehicule);
                }
            }
            
            // Sérialiser les scores des véhicules pour le joueur 2
            for (Piece piece : joueur2) {
                if (piece instanceof Vehicule) {
                    int scoreVehicule = ((Vehicule) piece).getNuagesCaptured();
                    outputStream.writeInt(scoreVehicule);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Charge l'état du jeu à partir d'un fichier sérialisé.
     *
     * @param joueur1 La liste des pièces du joueur 1.
     * @param joueur2 La liste des pièces du joueur 2.
     */
    @SuppressWarnings("unchecked") //permet de supprimer les warnings de type "unchecked""
    public static void loadGame(ArrayList<Piece> joueur1, ArrayList<Piece> joueur2) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("utils/game.ser"))) {
            ArrayList<Piece> loadedJoueur1 = Board.getJoueur1();
            ArrayList<Piece> loadedJoueur2 = Board.getJoueur2();
            

            Board.board = (Piece[][]) inputStream.readObject();
            loadedJoueur1 = (ArrayList<Piece>) inputStream.readObject();
            loadedJoueur2 = (ArrayList<Piece>) inputStream.readObject();
            GameLogic.scoreJoueur1 = inputStream.readInt(); 
            GameLogic.scoreJoueur2 = inputStream.readInt(); 
            GameLogic.turn = inputStream.readInt(); 
    
            for (Piece piece : loadedJoueur1) {
                if (piece instanceof Vehicule) {
                    int scoreVehicule = inputStream.readInt();
                    ((Vehicule) piece).setNuagesCaptured(scoreVehicule);
                }
            }
            for (Piece piece : loadedJoueur2) {
                if (piece instanceof Vehicule) {
                    int scoreVehicule = inputStream.readInt();
                    ((Vehicule) piece).setNuagesCaptured(scoreVehicule);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Lit le contenu d'un fichier texte ligne par ligne et l'affiche dans la console.
     *
     * @param fichier_txt Le chemin du fichier texte à lire.
     */
    public static void readFiles(String fichier_txt) {
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

    /**
     * Sauvegarde un mouvement effectué dans un fichier texte.
     *
     * @param source       La position source du mouvement.
     * @param destination  La position destination du mouvement.
     * @param turn         Le tour actuel.
     * @param scoreJoueur1 Le score du joueur 1.
     * @param scoreJoueur2 Le score du joueur 2.
     * @param fichier_txt  Le chemin du fichier texte où sauvegarder le mouvement.
     */
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
    // Méthode pour vider le contenu d'un fichier
    public static void clearFile(String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(""); // Écrire une chaîne vide pour vider le fichier
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
