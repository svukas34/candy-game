package Jeu;

import java.util.Random;
import java.util.Scanner;

import org.omg.CORBA.SystemException;

public class Jeu extends RegleDuJeu implements Jeu1{ 
	
	int largeur;
	int hauteur;

	/**Chaque pion eliminé rapporte 20 points
	 * Si un pion spécial est créer, il rapporte 120 points
	 * quand il est éliminé, il ne rapporte rien**/
	
	
	Conneries conneries;
	
	public Jeu(int largeur, int hauteur) {
		super(largeur, hauteur);
		this.largeur=largeur;
		this.hauteur=hauteur;
		this.conneries = new Conneries();
		
		/*Fait en sorte qu'il n'y ais pas 3 pions alignés au début du jeu*/
	//	for(int i=0; i<hauteur; i++)
	//		for(int j=0; j<largeur; j++)
		
		/*Il n'y a pas de bonbons spéciaux au début du jeu*/
		parcourir();
		
		for(int i=0; i<hauteur; i++)
			for(int j=0; j<largeur; j++){
				this.plateau.getBonbon(i, j).setEstExplosable(false);
				this.plateau.getBonbon(i, j).setEstRayeH(false);
				this.plateau.getBonbon(i, j).setEstRayeV(false);
			}
				
	}

	public Conneries getC() {
		return conneries;
	}

	public void setC(Conneries c) {
		this.conneries = c;
	}

	public int getLargeur() {
		return largeur;
	}
	
	public int getHauteur() {
		return hauteur;
	}

	public int getScore() {
		return conneries.getScore();
	}
	
	/*Met un pion au hasard*/
	public void modifier(int l, int c){
		setPlateau(l,c,Jeton.values()[(int)(Math.random()*Jeton.values().length)]);
	}
	
	/*Permet d'échanger deux bonbons*/
	public void echange(int l1, int c1, int l2, int c2){
		
		/*Lorsque les deux pions peuvent etre echangé mais que l'un des deux est explosable */
		if(this.plateau.getBonbon(l1, c1).getEstExplosable()){
			this.plateau.getBonbon(l1, c1).setEstExplosable(false);
			this.plateau.getBonbon(l2, c2).setEstExplosable(true);
			
		}else if(this.plateau.getBonbon(l2, c2).getEstExplosable()){
			this.plateau.getBonbon(l2, c2).setEstExplosable(false);
			this.plateau.getBonbon(l1, c1).setEstExplosable(true);
			
		}else if(this.plateau.getBonbon(l1, c1).getEstRayeV()){
			this.plateau.getBonbon(l1, c1).setEstRayeV(false);
			this.plateau.getBonbon(l2, c2).setEstRayeV(true);
			
		}else if(this.plateau.getBonbon(l2, c2).getEstRayeV()){
			this.plateau.getBonbon(l2, c2).setEstRayeV(false);
			this.plateau.getBonbon(l1, c1).setEstRayeV(true);
		
		}else if(this.plateau.getBonbon(l1, c1).getEstRayeH()){
			this.plateau.getBonbon(l1, c1).setEstRayeH(false);
			this.plateau.getBonbon(l2, c2).setEstRayeH(true);
			
		}else if(this.plateau.getBonbon(l2, c2).getEstRayeH()){
			this.plateau.getBonbon(l2, c2).setEstRayeH(false);
			this.plateau.getBonbon(l1, c1).setEstRayeH(true);
		
		}else if(this.plateau.getBonbon(l1, c1).getEstMultiCouleur()){
			this.plateau.getBonbon(l1, c1).setEstMultiCouleur(false);
			this.plateau.getBonbon(l2, c2).setEstMultiCouleur(true);
		
		}else if(this.plateau.getBonbon(l2, c2).getEstMultiCouleur()){
			this.plateau.getBonbon(l2, c2).setEstMultiCouleur(false);
			this.plateau.getBonbon(l1, c1).setEstMultiCouleur(true);
		}
		
		Jeton b = getBonbon(l1,c1);
		setPlateau(l1,c1, getBonbon(l2,c2));
		setPlateau(l2,c2,b);
		
	}
	
	/*Regarde si les deux bonbons ont le droit d'etre échangé (à coté et permet d'en aligner 3) si oui les échange*/
	public boolean peutEchanger(int l1, int c1, int l2, int c2){
		if (aCote(l1,c1,l2,c2)){
			echange(l1,c1,l2,c2);
			if (!troisAlignes(l1,c1) && !troisAlignes(l2,c2)){
				echange(l1,c1,l2,c2);
				return false;
			}
			
		}
		return true;
	}
	
	/*Renvoi TRUE si il y a un deplacement possible*/
	/*Change un pion avec son voisin et regarde si il a en a trois alignes*/
	public boolean deplacementPossible(){
		
		for(int i=0; i<hauteur; i++)
			for(int j=0; j<largeur; j++){
				/*Echange le pion (i,j) avec (i,j+1) si possible, il y a encore des déplacements*/
				if(peutEchanger(i,j,i,j+1) && j!=largeur-1){
					/*On remet les pions comme c'etait*/
					echange(i,j,i,j+1);
					return true;
				}else if(peutEchanger(i,j,i,j-1) && j!=0){
					echange(i,j,i,j-1);
					return true;
				}else if(peutEchanger(i,j,i+1,j) && i!=hauteur-1){
					echange(i,j,i+1,j);
					return true;
				}else if(peutEchanger(i,j,i-1,j) && i!=0){
					echange(i,j,i-1,j);
					return true;
				}
			}
		return false;
	}
	
	/*Creer un bonbon qui peut exploser*/
	public void creerExplosif(int l, int c){
		
			/*On dis que maintenant ce bonbon est explosable*/
			this.getC().setScore(this.conneries.getScore()+120);
		
			/*On supprime la ligne ou se trouve le bonbon sauf lui*/
			int j;
			if (troisAlignesLigneD(l,c)){
				supprimer(l,c-1);
				supprimer(l,c-2);
			}else if (troisAlignesLigneC(l,c)){
				supprimer(l,c-1);
				supprimer(l,c+1);
			}else if(troisAlignesLigneG(l,c)){ 
				supprimer(l,c+1);
				supprimer(l,c+2);
			}
				
			/*On supprime la colonne ou se trouve le bonbon sauf lui*/
			if(troisAlignesColonneH(l,c)){
				this.plateau.getBonbon(l+2, c).setEstExplosable(true);
				this.plateau.getBonbon(l+2, c).setBonbon(this.plateau.getBonbon(l+2, c).getBonbon());
				supprimer(l,c);
				supprimer(l+1,c);
				
			}else if(troisAlignesColonneC(l,c)){
				this.plateau.getBonbon(l+1, c).setEstExplosable(true);
				this.plateau.getBonbon(l+1, c).setBonbon(this.plateau.getBonbon(l+1, c).getBonbon());
				supprimer(l-1,c);
				supprimer(l,c);
				
			}else if(troisAlignesColonneB(l,c)){ 
				this.plateau.getBonbon(l, c).setEstExplosable(true);
				this.plateau.getBonbon(l, c).setBonbon(this.plateau.getBonbon(l, c).getBonbon());
				supprimer(l-2,c);
				supprimer(l-1,c);
				
				}	
	}
	
	/*Creer un bonbon multicouleur et supprime ceux autour*/
	public void creerMultiCouleur(int l, int c){
		this.getC().setScore(this.conneries.getScore()+120);
		
		this.plateau.getBonbon(l, c).setEstMultiCouleur(true);
		this.plateau.getBonbon(l, c).setBonbon(this.plateau.getBonbon(l, c).getBonbon());
		if(troisAlignesLigne(l, c)){
			supprimer(l, c-2);
			supprimer(l, c-1);
			supprimer(l, c+2);
			supprimer(l, c+1);
		}else{
			supprimer(l-2, c);
			supprimer(l-1, c);
			supprimer(l+1, c);
			supprimer(l+2, c);
		}
	}
	
	/*Supprime le bonbon à la position (l,c) et fait descendre toute la colonne*/
	public void supprimer(int l, int c){
		
		if(!this.plateau.getBonbon(l, c).getEstExplosable() && !this.plateau.getBonbon(l, c).getEstRayeV() && !this.plateau.getBonbon(l, c).getEstRayeH()){
			this.conneries.setScore(this.conneries.getScore()+20);
		}
		
		if(this.plateau.getBonbon(l, c).getEstRayeV())
			supprimerRayeV(l,c);
		else if(this.plateau.getBonbon(l, c).getEstRayeH())
			supprimerRayeH(l,c);
		else if(this.plateau.getBonbon(l, c).getEstExplosable())
			supprimerExplosif(l,c);
	
		for(int i=l; i>0; i--){

			echange(i,c,i-1,c);
		}

		
		modifier(0,c);
		

	}
	
	/*Si 3 pions sont sur meme ligne*/
	public void supprimerLigne(int l, int c){
		if(troisAlignesLigneC(l, c)){
			supprimer(l,c-1);
			supprimer(l,c);
			supprimer(l,c+1);
		
		}else if(troisAlignesLigneD(l, c)){
			supprimer(l,c-2);
			supprimer(l,c-1);
			supprimer(l,c);
			
		}else if(troisAlignesLigneG(l, c)){
			supprimer(l,c);
			supprimer(l,c+1);
			supprimer(l,c+2);
		}
	}
	
	
	/*Supprime toute une colonne*/
	void supprimerRayeV(int l, int c){
		this.plateau.getBonbon(l, c).setEstRayeV(false);
		this.plateau.getBonbon(l, c).setBonbon(this.plateau.getBonbon(l, c).getBonbon());
		
		for(int k=hauteur-1; k>=0; k--){
			
			if(this.plateau.getBonbon(k, c).getEstRayeH())
				supprimerRayeH(k,c);
			
			else if(this.plateau.getBonbon(k, c).getEstExplosable())
				supprimerExplosif(k,c);
			
			else if(this.plateau.getBonbon(k, c).getEstRayeV())
				supprimerRayeV(k, c);
			
			else
			supprimer(k, c);
		}
	}
	
	/*Supprime toute un ligne*/
	void supprimerRayeH(int l, int c){
		this.plateau.getBonbon(l, c).setEstRayeH(false);
		this.plateau.getBonbon(l, c).setBonbon(this.plateau.getBonbon(l, c).getBonbon());
		
		for(int k=0; k<largeur; k++){
			
			if(this.plateau.getBonbon(l, k).getEstRayeV())
				supprimerRayeV(l,k);
			
			else if(this.plateau.getBonbon(l, k).getEstExplosable())
				supprimerExplosif(l,k);
			
			else if(this.plateau.getBonbon(l, k).getEstRayeH())
				supprimerRayeH(l, k);
			
			else
			supprimer(l,k);
		}
	}
	
	/*Est utilliser quand on parcours le plateau*/
	public void supprimerMultiCouleur(int l, int c){
		this.plateau.getBonbon(l, c).setEstMultiCouleur(false);
		
		int b = (int)(Math.random()*Jeton.values().length);
		System.out.println("Couleur supprimer "+b);
		
		for(int i=0; i<hauteur; i++)
			for(int j=0; j<largeur; j++){
				if(b == this.plateau.getBonbon(i, j).getBonbon().ordinal()){
					System.out.println(i+", "+j);
					supprimer(i,j);
				}
			}
	}
	
	/* (l1,c1) pion multiCouleur, (l2,c2) le pion à supprimer*/
	public void supprimerMultiCouleur(int l1, int c1, int l2, int c2){
		this.plateau.getBonbon(l1, c1).setEstMultiCouleur(false);
		
		supprimer(l1, c1);
		int b = this.plateau.getBonbon(l2,c2).getBonbon().ordinal();
		System.out.println("Couleur supprimer "+b);
		
		for(int i=0; i<hauteur; i++)
			for(int j=0; j<largeur; j++){
				if(b == this.plateau.getBonbon(i, j).getBonbon().ordinal()){
					System.out.println(i+", "+j);
					supprimer(i,j);
				}
			}
	}
	
	/*Si 3 pions alignés dans la colonne, les supprime et d'autres arrivent*/
	public void supprimerColonne(int l, int c){
		int i=l;
		
		if(troisAlignesColonneC(l,c))
			i=l-1;
		else if(troisAlignesColonneB(l,c))
			i=l-2;
		
		for(int j=i; j<i+3; j++){
			if(!this.plateau.getBonbon(j, c).getEstExplosable() && !this.plateau.getBonbon(j, c).getEstRayeV() && !this.plateau.getBonbon(j, c).getEstRayeH())
				supprimer(j,c);

			/*Si le bonbon est rayé verticalement, detruit toute la colonne*/
			else if(this.plateau.getBonbon(j, c).getEstRayeV()){
				supprimerRayeV(j,c);
			}
			
			/*Si le bonbon est rayé horizontalement, détruit toute la ligne*/
			else if(this.plateau.getBonbon(j, c).getEstRayeH()){
				supprimerRayeV(j,c);
							
			}
			
			else if(this.plateau.getBonbon(j, c).getEstExplosable())
				supprimerExplosif(j,c);
		}
	}
	
	/*Fait exploser un bonbon et ces alentours*/
	public void supprimerExplosif(int l, int c){
		this.plateau.getBonbon(l, c).setEstExplosable(false);
		this.plateau.getBonbon(l, c).setBonbon(this.plateau.getBonbon(l, c).getBonbon());
		
		for(int i=1; i<=2; i++){
			if(c==0){
				if(l==0){
					supprimer(l,c+1);
					supprimer(l+1,c);
					supprimer(l+1,c+1);
					
				}else if(l==hauteur-1){
					supprimer(l-1,c);
					supprimer(l-1,c+1);
					supprimer(l,c+1);
					
				}else{
					supprimer(l-1,c);
					supprimer(l-1,c+1);
					supprimer(l,c+1);
					supprimer(l+1,c);
					supprimer(l+1,c+1);
				}
			}else if(c==largeur-1){
				if(l==0){
					supprimer(l,c-1);
					supprimer(l+1,c);
					supprimer(l+1,c-1);
				}else if(l==hauteur-1){
					supprimer(l-1,c);
					supprimer(l-1,c-1);
					supprimer(l,c-1);
				}else{
					supprimer(l-1,c);
					supprimer(l-1,c-1);
					supprimer(l,c-1);
					supprimer(l+1,c);
					supprimer(l+1,c-1);
				}
			}else{
				if(l==0){
					supprimer(l,c-1);
					supprimer(l,c+1);
					supprimer(l+1,c-1);
					supprimer(l+1,c);
					supprimer(l+1,c+1);
					
				}else if(l==hauteur-1){
					supprimer(l-1,c-1);
					supprimer(l-1,c+1);
					supprimer(l-1,c);
					supprimer(l,c-1);
					supprimer(l,c+1);
				}else{
					supprimer(l-1,c);
					supprimer(l-1,c-1);
					supprimer(l-1,c+1);
					supprimer(l,c+1);
					supprimer(l,c-1);
					supprimer(l+1,c-1);
					supprimer(l+1,c);
					supprimer(l+1,c+1);
				}
			}	
			if(l!=hauteur-1)
				l++;
		}
		supprimer(l,c);
	} 
	
	/*Permet de creer un bonbon rayé Verticalement*/
	/*ATTENTION, il faut d'abord verifier si cela est possible*/
	public void creerRayeV(int l, int c){
			System.out.println("creer RayeV");
			/*lorsque un bonbon rayé est crée, on gagne 120 points*/
			this.getC().setScore(this.conneries.getScore()+120);
			
			if(troisAlignesLigneD(l,c)){
				System.out.println("creer RayeV ligneD");
				supprimer(l,c-2);
				supprimer(l,c-1);
				supprimer(l,c+1);
				this.plateau.getBonbon(l,c).setEstRayeV(true);
				this.plateau.getBonbon(l, c).setBonbon(this.plateau.getBonbon(l, c).getBonbon());
		
			}else if(troisAlignesLigneG(l,c)){
				System.out.println("creer RayeV ligneG");
				supprimer(l,c-1);
				supprimer(l,c+1);
				supprimer(l,c+2);
				this.plateau.getBonbon(l,c).setEstRayeV(true);
				this.plateau.getBonbon(l, c).setBonbon(this.plateau.getBonbon(l, c).getBonbon());
			}
		
	}

	/*Permet de creer un bonbon rayé Horizontalement*/
	/*ATTENTION, il faut d'abord verifier si cela est possible*/
	public void creerRayeH(int l, int c){
			System.out.println("creer RayeH");		
			/*lorsque un bonbon rayé est crée, on gagne 120 points*/
			this.getC().setScore(this.conneries.getScore()+120);
			
			if(troisAlignesColonneB(l,c)){
				supprimer(l-2,c);
				supprimer(l-1,c);
				supprimer(l,c);
				this.plateau.getBonbon(l+1, c).setEstRayeH(true);
				this.plateau.getBonbon(l+1, c).setBonbon(this.plateau.getBonbon(l+1, c).getBonbon());
			}
			else if(troisAlignesColonneH(l,c)){
				supprimer(l-1,c);
				supprimer(l,c);
				supprimer(l+1,c);
				this.plateau.getBonbon(l+2, c).setEstRayeH(true);
				this.plateau.getBonbon(l+2, c).setBonbon(this.plateau.getBonbon(l+2, c).getBonbon());
			}
			
			
	}
	
	/*Permet d'éliminer trois pions qui se sont retrouvés à coté après un mouvement*/
		/*Ne le fait pas toujours, si on ne commence pas de zéros*/
	public void parcourir(){
		
		for(int i=0; i<hauteur; i++)
			for(int j=0; j<largeur; j++){
				
			if(peutMultiCouleur(i, j)){
				System.out.println("creer MultiCouleur parcourir "+i+" , "+j);
				creerMultiCouleur(i, j);
				parcourir();
				
				/*Regarde si il peut creer un bonbon qui explose*/
			}else if(peutExplosif(i, j) && this.getC().getNbCoup()!=0){
					System.out.println("creer Explosif parcourir, "+i+", "+j);
					creerExplosif(i,j);
					parcourir();
					
				/*Regarde si il peut creer un bonbon rayé horizontalement*/					
				}else if(peutRayeH(i, j)){
					System.out.println("creerRayeH 2 parcourir, "+i+", "+j);
					creerRayeH(i, j);
					parcourir();
					
				/*Regarde si il peut creer un bonbon rayé verticalement*/				
				}else if(peutRayeV(i, j)){
					System.out.println("creerRayeV 2 parcourir, "+i+", "+j);
					creerRayeV(i, j);
					parcourir();
					
				/*Regarde si il y a trois bonbons à cotés sur la meme ligne*/	
				}  else if(troisAlignesLigne(i,j)){
						System.out.println("supp ligne parcourir, "+i+", "+j);
						supprimerLigne(i,j);
						// affiche();
						parcourir();
				
				/*Regarde si il y a trois bonbons à cotés sur la meme colonne*/
				}else if(troisAlignesColonne(i,j)){
					System.out.println("supp colonne parcourir, "+i+", "+j);
					supprimerColonne(i,j);
					// affiche();
					parcourir();
				}
			}
	}
	
	/*Change de place tout les pions sur le plateau*/
	/*Est utilisé quand il n'y a plus de déplacements possible*/
	public void melanger(){
		int [] nb = new int[4*Jeton.values().length];
		
		/*On compte les sortes de bonbons présents dans le jeu*/
		for(int i=0; i<hauteur; i++)
			for(int j=0; j<largeur; j++){
				
				/*Si le bonbon peut exploser, on le rajoute dans la deuxième partie du tableau*/
				if(this.plateau.getBonbon(i, j).getEstExplosable())
					nb[getBonbon(i,j).ordinal()+Jeton.values().length]++;
				
				/*Si le bonbon est rayé horizontalement, on le rajoute dans la troisième partie du tableau*/
				else if(this.plateau.getBonbon(i, j).getEstRayeH())
					nb[getBonbon(i,j).ordinal()+2*Jeton.values().length]++;
				
				/*Si le bonbon est rayé verticalement, on le rajoute dans la troisième partie du tableau*/	
				else if(this.plateau.getBonbon(i, j).getEstRayeV())
					nb[getBonbon(i,j).ordinal()+3*Jeton.values().length]++;
				
				/*Si c'est un bonbon normale, on le met dans la première partie du tableau*/
				else
					nb[getBonbon(i,j).ordinal()]++;
			}
					
		for (int i=0; i<nb.length; i++)
			System.out.print(nb[i]+" | ");
		System.out.println();
				
		/*On remet les pions dans le plateau au hasard*/
		for(int i=0; i<hauteur; i++)
			for(int j=0; j<largeur; j++){
				Random r = new Random();
				int k = r.nextInt(Jeton.values().length*4);
				

				while(nb[k]==0){
					r = new Random();
					k = r.nextInt(Jeton.values().length*4);
				}
				System.out.println("Valeur remise "+k);
				Jeton l = Jeton.values()[k];
				setPlateau(i,j,l);
				
				if(k>=Jeton.values().length && k<2*Jeton.values().length){
					this.plateau.getBonbon(i, j).setEstExplosable(true);
					this.plateau.getBonbon(i, j).setEstRayeH(false);
					this.plateau.getBonbon(i, j).setEstRayeV(false);
					
				}else if(k>=2*Jeton.values().length && k<3*Jeton.values().length){
					this.plateau.getBonbon(i, j).setEstExplosable(false);
					this.plateau.getBonbon(i, j).setEstRayeH(true);
					this.plateau.getBonbon(i, j).setEstRayeV(false);
					
				}else if(k>=3*Jeton.values().length){
					this.plateau.getBonbon(i, j).setEstExplosable(false);
					this.plateau.getBonbon(i, j).setEstRayeH(false);
					this.plateau.getBonbon(i, j).setEstRayeV(true);
					
				}else{
					this.plateau.getBonbon(i, j).setEstExplosable(false);
					this.plateau.getBonbon(i, j).setEstRayeH(false);
					this.plateau.getBonbon(i, j).setEstRayeV(false);
				}
				
				nb[k]--;
			}
		parcourir();
	}
	
	/* Permet de jouer au jeu*/
	public void jouer(int l1, int c1, int l2, int c2){
		
		System.out.println(l1+", "+c1+"\t"+l2+", "+c2);
		
		/*Si un des deux bonbon est un MultiCouleur */
		if(aCote(l1, c1, l2, c2) && (c1!=c2 || l1!=l2)){
			if(this.plateau.getBonbon(l1, c1).getEstMultiCouleur()){
				this.conneries.setNbCoup(this.conneries.getNbCoup()+1);
				supprimerMultiCouleur(l1, c1, l2, c2);
			}if(this.plateau.getBonbon(l2, c2).getEstMultiCouleur()){
				this.conneries.setNbCoup(this.conneries.getNbCoup()+1);
				supprimerMultiCouleur(l2, c2, l1, c1);
			}
			
			parcourir();
		}
			
		
		if(peutEchanger(l1,c1,l2,c2) && (c1!=c2 || l1!=l2)){
			System.out.println("Echange");
			this.conneries.setNbCoup(this.conneries.getNbCoup()+1);
				
			if(peutMultiCouleur(l1, c1)){
				System.out.println("MultiCouleur 1");
				creerMultiCouleur(l1,c1);
			}
			
			if(peutMultiCouleur(l2, c2)){
				System.out.println("MultiCouleur 2");
				creerMultiCouleur(l2,c2);
			}
			
			if(peutExplosif(l1,c1)){
				System.out.println("Explosif 1");
				creerExplosif(l1,c1);
				// affiche();
			}
			if(peutExplosif(l2,c2)){
				System.out.println("Explosif 2");
				creerExplosif(l2,c2);
				// affiche();
			}
			if(peutRayeV(l1,c1)){
				System.out.println("RayeV 1");
				creerRayeV(l1,c1);
				
			}if(peutRayeV(l2,c2)){
				System.out.println("RayeV 2");
				creerRayeV(l2,c2);
				
			}if (peutRayeH(l1,c1)){
				System.out.println("rayeH 1");
				creerRayeH(l1,c1);
			}
			if (peutRayeH(l2,c2)){
				System.out.println("RayeH 2");
				creerRayeH(l2,c2);
				} 
			
			if(troisAlignesLigne(l1,c1)){
				System.out.println("Ligne 1");
				supprimerLigne(l1,c1);
				// affiche();
			}
			if(troisAlignesLigne(l2,c2)){
				System.out.println("Ligne 2");
				supprimerLigne(l2,c2);
				// affiche();
			}
			if(troisAlignesColonne(l1,c1)){
				System.out.println("Colonne 1");
				supprimerColonne(l1,c1);
				// affiche();
			}
			if(troisAlignesColonne(l2,c2)){
				System.out.println("Colonne 2");
				supprimerColonne(l2,c2);
				// affiche();				
			}
			parcourir();
		}else
			System.out.println("Le déplacement n'est pas autorisé");
	
		/* Si il n'y a plus de déplacements possible*/
		while(!deplacementPossible()){
			System.out.println("plus de déplacements");
			affiche();
			melanger();
			affiche();
		
	}
		
	}
	
}
