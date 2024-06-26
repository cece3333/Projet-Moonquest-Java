# MoonQuest

### Informations sur le projet
Auteur : Céline Hosteins  
Enseignant : Slim Karkar  

Ce projet a été réalisé dans le cadre du Master 1 de Bioinformatique à l'Université de Bordeaux.

### Contenu du Projet :

#### Exécutables (.jar) :
- Le dossier *bin* contient l'exécutable de l'application MoonQuestApp_CH.jar
- Pour exécuter le jeu, vous pouvez utiliser la commande "java -jar MoonQuestApp_CH.jar" dans votre terminal.

##### Source du Code (.java) :
- Le dossier *src* contient le code source de l'application, écrit en langage Java.
- Vous pouvez explorer ce dossier pour voir les fichiers source individuels qui composent le jeu MoonQuest.

#### Documentation (Javadoc) :
- Le dossier *docs* contient la documentation Java (Javadoc) générée pour le projet fournissant une référence complète pour les classes, les méthodes et les packages utilisés dans le projet.
- Vous pouvez accéder à la documentation en ouvrant le fichier *index.html*.


## Bienvenue dans MoonQuest !
### Une aventure spatiale épique où vous devrez conquérir les lunes de Jupiter pour asseoir votre suprématie dans l'univers !

Vous aurez pour mission de capturer un maximum de nuages avec vos véhicules afin de conquérir le territoire. Mais gare à l'adversaire et aux obstacles qui menaceront votre conquête !

---

## Comment jouer :

1. **Sélectionner une nouvelle partie (N) ou charger une partie sauvegardée (R)**
2. **Choisir le mode de jeu :**
   - **Joueur contre Joueur (PvP) :** chaque joueur entre les coordonnées de la pièce à déplacer puis de la case d'arrivée (ex: taper "A1", entrée, puis taper "B1")
   - **Joueur contre IA :** le joueur 1 entre les coordonnées au clavier comme décrit précédemment puis les mouvements de l'IA sont sélectionnés automatiquement (joueur 2)
   - **IA contre IA :** les mouvements des deux joueurs sont sélectionnés automatiquement et le déroulement de la partie se fait sans intervention humaine.

   NB: Pour les modes 1 et 2, vous pouvez quitter la partie lorsque c'est à votre tour de jouer en tapant "q" puis la sauvegarder en sélectionnant "O" lorsque le message de sauvegarde s'affiche.

---

## Éléments du jeu :
- Chaque joueur possède 8 Véhicules (V), 4 de chaque type ("Méthane" ou "Eau") ainsi que 24 blocs de glace (G)
- Le score d'un joueur correspond au nombre total de Nuages (N) capturés par ses véhicules

### Véhicules (V) :
- Un Véhicule peut être déplacé d'une seule case selon un déplacement terrestre, soit verticalement ou horizontalement (hors diagonale)
- Les Véhicules ne peuvent capturer que les nuages de leur type (même couleur) ; lorsqu'il entre en contact avec un nuage de type différent, il est détruit et ses nuages
- Lorsqu'un véhicule capture 3 nuages, il est activé (devient blanc), et peut alors se déplacer de deux cases à la fois (dans n'importe quelle direction) selon le déplacement aérien
- Un véhicule activé peut détruire n'importe quel nuage. Il peut survoler les nuages et les autres véhicules, mais ne peut pas survoler les blocs de glace.

### Blocs de glace (G) :
- Les blocs de glaces peuvent être déplacés d'une seule case selon un déplacement terrestre
- Ils détruisent toutes les pièces adverses ainsi que tous les nuages sur leur passage
- Ils ne peuvent être détruits que par les nuages ou les blocs de glace adverses

### Nuages (NM, NE) :
- Les Nuages sont de deux types différents : Méthane (vert, "NM") et Eau (bleu, "NE")
- Les Nuages ne sont pas contrôlés par les joueurs ; ils ont 1 chance sur 5 de se déplacer aléatoirement sur le plateau selon un déplacement aérien.
- Comme les blocs de Glace, un Nuage détruit toutes les pièces situées sur sa case d'arrivée
- Ils ne peuvent être capturés que par les Véhicules de même types.

---

## Fin de la partie :
1. La partie se termine lorsque tous les nuages ont été capturés/détruits par les joueurs
2. Le joueur ayant capturé le plus de nuages remporte la partie

NB: si un joueur capture 16 nuages ou plus, il remporte automatiquement la partie. De même, s'il n'a plus aucune pièce sur le plateau, il perd automatiquement la partie.

