package Jeu;

public class Plateau{

	private Case [][] plateau;
	private int largeur;
	private int hauteur;
	
	public Plateau(int largeur, int hauteur) {
		
		this.largeur = largeur;
		this.hauteur = hauteur;
		this.plateau = new Case[hauteur][largeur];
		
		for(int i=0; i<hauteur; i++)
			for(int j=0; j<largeur; j++){
				int nb = (int)(Math.random()*Jeton.values().length);
				this.plateau[i][j] = new Case(Jeton.values()[nb]);
					
			}			

	}
	
	public void affiche(){
		for(int i=0; i<hauteur; i++){
			for(int j=0; j<largeur; j++)
				System.out.print(plateau[i][j] +"\t ");
			System.out.println();
		}
		System.out.println();
	}

	
	
	public Case[][] getPlateau() {
		return plateau;
	}

	public Case getBonbon(int i, int j) {
		return plateau[i][j];
	}

	public void setBonbon(int i, int j, Jeton bonbon) {
		this.plateau[i][j].setBonbon(bonbon);
	}
	
	public void setExplosif(int i, int j, boolean explosif){
		this.plateau[i][j].setEstExplosable(explosif);
	}
	
}
