



public enum Couleurs {
    // Constantes pour les couleurs du texte
    RESET("\u001B[0m"),
    BLACK("\u001B[30m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    PURPLE("\u001B[35m"),
    CYAN("\u001B[36m"),
    WHITE("\u001B[37m"),
    MAGENTA("\u001B[35m"); // Attention, la couleur MAGENTA semble identique à PURPLE, vous pouvez choisir de la modifier si nécessaire

    // Attribut pour le code ANSI de la couleur
    private final String code;

    // Constructeur privé pour initialiser le code ANSI de la couleur
    private Couleurs(String code) {
        this.code = code;
    }

    // Méthode pour obtenir le code ANSI de la couleur
    public String getCode() {
        return code;
    }
}
