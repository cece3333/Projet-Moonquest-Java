package pieces;

import display.Board;
import utils.Colors;

/**
 * La classe Vehicule permet de créer des instances de Vehicule aux attributs communs.
 * Elle hérite de la classe abstraite Piece.
 */
public class Vehicule extends Piece {
    private boolean isActivated;
    private int nuagesCaptured;

    /**
     * Constructeur de la classe Vehicule.
     *
     * @param x           La coordonnée x du véhicule.
     * @param y           La coordonnée y du véhicule.
     * @param icon        L'icône représentant le véhicule.
     * @param type        Le type de nuage que le véhicule peut capturer.
     * @param color       La couleur du véhicule.
     * @param isActivated L'état d'activation du véhicule.
     */
    public Vehicule(int x, int y, String icon, String type, Colors color, boolean isActivated) {
        super(x, y, icon, type, color);
        this.isActivated = isActivated;
        isActivated = false;
        this.nuagesCaptured = 0;

        // Assignation de la color en fonction du type de nuage
        if (type.equals("Methane")) {
            setColor(Colors.YELLOW); // Utilisation de la constante Colors.YELLOW
        } else if (type.equals("Eau")) {
            setColor(Colors.BLUE);; // Utilisation de la constante Colors.BLUE
        }

        if (isActivated) {
            setColor(Colors.WHITE);
        }
    }

    /**
     * Getter pour l'état d'activation du véhicule.
     *
     * @return L'état d'activation du véhicule.
     */
    public boolean isActive() {
        return isActivated;
    }

    /**
     * Renvoie le nombre de nuages capturés par le véhicule.
     *
     * @return Le nombre de nuages capturés.
     */
    public int getNuagesCaptured() {
        return nuagesCaptured;
    }

    /**
     * Modifie le nombre de nuages capturés par le véhicule.
     *
     * @param nuagesCaptured Le nouveau nombre de nuages capturés.
     */
    public void setNuagesCaptured(int nuagesCaptured) {
        this.nuagesCaptured = nuagesCaptured;
    }

    /**
     * Vérifie si le véhicule peut capturer un nuage.
     *
     * @param piece La pièce à vérifier.
     * @return true si le véhicule peut capturer le nuage, false sinon.
     */
    public boolean canCapture(Piece piece) {
        if ((piece instanceof Nuage) && (isActivated == false)) {
            if (((Nuage) piece).getType().equals(this.getType())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Capture un nuage avec le véhicule.
     * Si le nombre de nuages capturés atteint 3, le véhicule est activé.
     */
    public void captureNuage() {
        if (nuagesCaptured < 3) { 
            nuagesCaptured++;
            if (nuagesCaptured == 3) { 
                isActivated = true;
                System.out.println("Véhicule activé");
                setColor(Colors.WHITE);
            }
        }
    }
    
}
