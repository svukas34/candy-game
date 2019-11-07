package Graphisme;
import Jeu.Case;

public class compareTab {

	
	private Case[][] ancienPlateau;
	
	boolean[][] casesARafraichire;
	boolean[][] casesEnMouvement;
	
	public compareTab(Case[][]tab){
		this.ancienPlateau = tab;
	}
	
	public void miseAjour(Case[][]nouvoTab){
		casesARafraichire = nouvellesCases(nouvoTab);
		casesEnMouvement = vaTomber(nouvoTab);
	}
	
	public boolean[][] nouvellesCases(Case[][]nouvoTab){
		int maxI = ancienPlateau.length;
		int maxJ = ancienPlateau[0].length;
		boolean returnNouvellesCases[][] = new boolean[maxI][maxJ];
		for (int i=0; i< maxI; i++){
			for (int j=0; j< maxJ; j++){
				if((this.ancienPlateau[i][j]) != (nouvoTab[i][j]))
					returnNouvellesCases[i][j] = true;
			}
		}
		return returnNouvellesCases;
	}
	
	//renvoi true au cordonées des bonbon qui "tombent"
	public boolean[][] ligneVaTomber(Case[][]nouvoTab){
		int maxI = ancienPlateau.length;
		int maxJ = ancienPlateau[0].length;
		boolean returnVaTomber[][] = new boolean[maxI][maxJ];
		boolean nouvellesCases[][] = nouvellesCases(nouvoTab);
		int memoryX=-1, memoryY=-1;
		for (int j=0; j< maxJ; j++){
			for (int i=maxI-1; i> -1; i--){
				if(nouvellesCases[i][j] == false){
					if(i>0){
						if(nouvellesCases[i-1][j] == true){
							i--;
							memoryX = i;
							memoryY = j;
						}
					}
				} else {
					if(nouvoTab[memoryX][memoryY] == this.ancienPlateau[i][j]){
						returnVaTomber[i][j] = true;
						//memoryX++;
						//memoryY++;
					}
				}
			}
		}
		return returnVaTomber;
	}
		
	public boolean[][] vaTomber(Case[][]nouvoTab){
		boolean[][] ligne = ligneVaTomber(nouvoTab);
		int maxI = ancienPlateau.length;
		int maxJ = ancienPlateau[0].length;
		boolean returnVaTomber[][] = new boolean[maxI][maxJ];
		boolean tampon;
		for (int j=0; j< maxJ; j++){
			tampon = false;
			for (int i=maxI-1; i> -1; i--){
				if (ligne[i][j] == true)
					tampon = true;
				returnVaTomber[i][j] = tampon;
			}
		}
		return returnVaTomber;
	}

}