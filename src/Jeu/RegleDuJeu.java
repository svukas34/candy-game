package Jeu;

public class RegleDuJeu {

		public int largeur;
		public int hauteur;
		public Plateau plateau;
		
		public RegleDuJeu(int largeur, int hauteur){
			this.largeur=largeur;
			this.hauteur=hauteur;
			setPlateau(new Plateau(largeur, hauteur));
		}
		
		public void affiche(){
			getPlateau().affiche();
		}
		
		/*Retourne le Jeton à la case l,c*/
		public Jeton getBonbon(int l, int c) {
			return this.plateau.getBonbon(l, c).getBonbon();
		}
		
		public Case getCase(int l, int c) {
			return this.plateau.getBonbon(l, c);
		}

		/*Permet de changer à la case l et c la valeur du jeton*/
		public void setPlateau(int l, int c, Jeton bonbon) {
			this.plateau.setBonbon(l,c,bonbon);
		}

		/*Retourne vrai si les pios 1 et 2 sont de la meme couleur*/
		public boolean memeCouleur(int l1, int c1, int l2, int c2){
			return this.plateau.getBonbon(l1,c1).getBonbon().ordinal()==this.getPlateau().getBonbon(l2,c2).getBonbon().ordinal();
	}
		
		/*Retourne vrai si deux pions sont à cotés par rapport à la ligne*/
		public boolean aCoteMemeLigne(int c1, int c2){
			if(c2<largeur && c2>=0 && c1<largeur && c1>=0 ){
				if(c1==0)
					return (c1+1==c2);
				if(c2==0)
					return (c2+1==c1);
				if(c1==largeur-1)
					return (c1-1==c2);
				if(c2==largeur-1)
					return (c2-1==c1);
				return (c2+1==c1 || c1+1==c2);
			}return false;
		}

		/*Retoure vrai si deux pions sont à cotés par rapport à la colonne*/
		public boolean aCoteMemeColonne(int l1, int l2){
			if(l2<hauteur && l2>=0 && l1<hauteur && l1>=0 ){
				if(l1==0)
					return (l1+1==l2);
				if(l2==0)
					return (l2+1==l1);
				if(l1==largeur-1)
					return (l1-1==l2);
				if(l2==largeur-1)
					return (l2-1==l1);
				return (l2+1==l1 || l1+1==l2);
			}return false;
		}
		
		/*Retourne vrai si les pions 1 et 2 sont à cotés (utilise aCoteMemeLigne et aCoteMemeColonne)*/
		public boolean aCote(int l1, int c1, int l2, int c2){
			/*Evite true si les cases sont en diagonales*/
			if(aCoteMemeColonne(l1,l2) && aCoteMemeLigne(c1,c2))
				return false;
			if(aCoteMemeColonne(l1,l2))
				return true;
			if (aCoteMemeLigne(c1,c2))
				return true;
			return false;
		}

		/*Retourne vrai si trois pions sont à coté sur la meme ligne et le pion l1, c1 est au centre*/
		public boolean troisAlignesLigneC(int l1, int c1){
			if(c1>=1 && c1<=largeur-2)
				return (memeCouleur(l1,c1, l1, c1-1) && memeCouleur(l1,c1, l1, c1+1));
			else
				return false;
		}
		
		/*Retourne vrai si trois pions sont à coté sur la meme ligne et le pion l1, c1 est à droite*/
		public boolean troisAlignesLigneD(int l1, int c1){
			if(c1>1)
				return (memeCouleur(l1,c1, l1, c1-1) && memeCouleur(l1,c1, l1, c1-2));
			else 
				return false;
		}

		/*Retourne vrai si trois pions sont à coté sur la meme ligne et le pion l1, c1 est à gauche*/
		public boolean troisAlignesLigneG(int l1, int c1){
			if(c1<largeur-2)
				return (memeCouleur(l1,c1, l1, c1+1) && memeCouleur(l1,c1, l1, c1+2));
			else
				return false;
		}
		
		public boolean troisAlignesLigne(int l, int c){
			return (troisAlignesLigneD(l,c) || troisAlignesLigneC(l,c) || troisAlignesLigneG(l,c));
		}
		
		/*Retourne vrai si trois pions sont à coté sur la meme ligne et le pion l1, c1 est au centre*/
		public boolean troisAlignesColonneC(int l1, int c1){
			if(l1!=hauteur-1 && l1!=0)
				return (memeCouleur(l1,c1, l1-1, c1) && memeCouleur(l1,c1, l1+1, c1));
			else
				return false;
		}
		
		/*Retourne vrai si trois pions sont à coté sur la meme Colonne et le pion l1, c1 est en haute*/
		public boolean troisAlignesColonneH(int l1, int c1){
			if(l1<hauteur-2)
				return (memeCouleur(l1,c1,l1+1,c1) && memeCouleur(l1,c1,l1+2,c1));
			else
				return false;
				
		}

		/*Retourne vrai si trois pions sont à coté sur la meme colonne et le pion l1, c1 est en bas*/
		public boolean troisAlignesColonneB(int l1, int c1){
			if(l1>=2)
				return (memeCouleur(l1,c1, l1-1, c1) && memeCouleur(l1,c1, l1-2, c1));
			else 
				return false;
		}
		
		public boolean troisAlignesColonne(int l, int c){
			return (troisAlignesColonneH(l,c) || troisAlignesColonneC(l,c) || troisAlignesColonneB(l,c));
		}
		
		/*retourne vrai si trois pions sont alignés soit sur une colonne soit sur une ligne*/
		public boolean troisAlignes(int l, int c){
			return (troisAlignesColonne(l,c) || troisAlignesLigne(l,c));
		}

		public Plateau getPlateau() {
			return plateau;
		}
		
		public boolean peutRayeV(int l, int c){
		
			if(this.plateau.getBonbon(l, c).getEstExplosable() || !troisAlignesLigne(l,c) || this.plateau.getBonbon(l, c).getEstRayeH())
				return false;
			
			if(c+1<largeur)
				if(troisAlignesLigneD(l,c) && memeCouleur(l,c,l,c+1))
					return true;
			
			if(c-1>=0)
				if(troisAlignesLigneG(l,c) && memeCouleur(l,c,l,c-1))
					return true;
				
			
			return false;
		}
		
		/*Renvoie VRAI si un bonbon rayé horizontalement peut etre creer*/
		public boolean peutRayeH(int l, int c){
			if(this.plateau.getBonbon(l, c).getEstExplosable() || this.plateau.getBonbon(l, c).getEstRayeV() || !troisAlignesColonne(l,c))
				return false;
			
			if(l+1<hauteur)
				if(troisAlignesColonneB(l,c) && memeCouleur(l,c,l+1,c))
					return true;
			
			if(l-1>=0)
				if(troisAlignesColonneH(l,c) && memeCouleur(l,c,l-1,c))
					return true;
			
			return false; 
		}
		
		/*Retourne true si un bonbon qui Explose peut etre creer*/
		public boolean peutExplosif(int l, int c){
			return (troisAlignesColonne(l, c) && troisAlignesLigne(l,c));
		}
		
		/*Renvoie vrai si il y a 5 bonbons sur la meme rangée*/
		public boolean peutMultiCouleur(int l, int c){
			if(troisAlignesLigneD(l, c) && troisAlignesLigneG(l, c))
				return true;
			
			if(troisAlignesColonneB(l, c) && troisAlignesColonneH(l, c))
				return true;
			
			return false;
		}
		
		public void setPlateau(Plateau plateau) {
			this.plateau = plateau;
		}
		
}
