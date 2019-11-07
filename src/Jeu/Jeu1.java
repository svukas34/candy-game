package Jeu;


public interface Jeu1 {
	
	//public boolean getExplosif(int l, int c);
	
	public Case getCase(int l, int c);
	
	public Conneries getC();
	
	public Plateau getPlateau();
	
	public int getLargeur();
	
	public int getHauteur();
	
	/*Retourne le score en cours*/
	public int getScore();
	
	/* Change la couleur d'un pion */
	public void modifier(int l, int c);
	
	/*Echange les deux pions*/
	public void echange(int l1, int c1, int l2, int c2);
	
	/*Verifie si les deux pions peuvent échanger de place, si oui appel la fonction echange pour le faire*/
	public boolean peutEchanger(int l1, int c1, int l2, int c2);
	
	/*Verifie que après le déplacement 3 pions sont alignés, sino les pions reviennent à leurs place*/
	public boolean deplacementPossible();
	
	/*Creer un bonbon qui explose*/
	public void creerExplosif(int l, int c);
	
	/*Supprime le bonbon à la ligne l et colonne c*/
	public void supprimer(int l, int c);
	
	/*Suprime les 3 pions qui sont alignés sur une meme ligne*/
	public void supprimerLigne(int l, int c);
	
	/*Supprime les 3 pions qui sont alignés sur une meme colonne*/
	public void supprimerColonne(int l, int c);
	
	/*Fait exploser les bonbons qui sont autours (2 fois)*/
	public void supprimerExplosif(int l, int c);
	
	/*Parcour tout le plateau et verifie si des combinaisons se sont formés*/
	public void parcourir();
	
	/*Melange tout les bonbons du plateau*/
	public void melanger();
	
	/*Lance le jeu*/
	public void jouer(int l1, int c1, int l2, int c2);
	
}
