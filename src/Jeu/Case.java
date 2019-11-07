package Jeu;


import javax.swing.ImageIcon;

import Graphisme.Position;

public class Case{
	private Jeton bonbon;
	
	private ImageIcon img;
	private Position pos;
	
	public Position getPos() {
		return pos;
	}

	public void setPos(Position pos) {
		this.pos = pos;
	}

	private boolean chocolat;
	private boolean gelatine;
	private boolean jouable;
	private boolean vide;
	
	private boolean estRayeH;
	private boolean estRayeV;
	private boolean estExplosable;
	private boolean estMultiCouleur;
	
	public Case(Jeton bonbon){
		this.img = new ImageIcon(""+bonbon+".jpg");
		this.chocolat = false;
		this.gelatine = false;
		this.jouable = true;
		this.estExplosable = false;
		this.estRayeH = false;
		this.estRayeV = false;
		this.bonbon = bonbon;
		this.vide = false;
		this.estMultiCouleur=false;
	}
	
	public Case(Jeton bonbon, boolean chocolat, boolean gelatine, boolean jouable, boolean estRayeH, boolean estRayeV, boolean estExplosable, boolean vide, boolean estMultiCouleur){
		
		this.bonbon = bonbon;
		this.chocolat = chocolat;
		this.gelatine = gelatine;
		this.jouable = jouable;	
		this.estExplosable = estExplosable;
		this.estRayeH = estRayeH;
		this.estRayeV = estRayeV;
		this.vide=vide;
		this.estMultiCouleur=estMultiCouleur;
		
	}

	public boolean getEstMultiCouleur() {
		return estMultiCouleur;
	}

	public void setEstMultiCouleur(boolean estMultiCouleur) {
		this.estMultiCouleur = estMultiCouleur;
	}

	public ImageIcon getImgIc() {
		return img;
	}

	public void setImgIc(ImageIcon img) {
		this.img = img;
	}

	public boolean getVide(){
		return vide;
	}
	
	public void setVide(boolean vide){
		this.vide=vide;
	}
	
	public Jeton getBonbon() {
		return bonbon;
	}

	//quand on change un bonbon on doit mettre à jour l'image !!!
	public void setBonbon(Jeton bonbon) {
		this.bonbon = bonbon;
		
		if(getEstMultiCouleur())
			setImgIc(new ImageIcon("noir.jpg"));
		
		else if(getEstRayeV())
			setImgIc(new ImageIcon(""+bonbon+"RV.jpg"));
		
		else if(getEstRayeH())
			setImgIc(new ImageIcon(""+bonbon+"RH.jpg"));
		
		else if(getEstExplosable())
			setImgIc(new ImageIcon(""+bonbon+"E.jpg"));
		
		else
			setImgIc(new ImageIcon(""+bonbon+".jpg"));
		
	}

	public boolean getChocolat() {
		return chocolat;
	}

	public void setChocolat(boolean chocolat) {
		this.chocolat = chocolat;
	}

	public boolean getGelatine() {
		return gelatine;
	}

	public void setGelatine(boolean gelatine) {
		this.gelatine = gelatine;
	}

	public boolean getJouable() {
		return jouable;
	}

	public void setJouable(boolean jouable) {
		this.jouable = jouable;
	}
	
	public boolean getEstRayeH() {
		return estRayeH;
	}

	public void setEstRayeH(boolean estRayeH) {
		this.estRayeH = estRayeH;
	}

	public boolean getEstRayeV() {
		return estRayeV;
	}

	public void setEstRayeV(boolean estRayeV) {
		this.estRayeV = estRayeV;
	}

	public boolean getEstExplosable() {
		return estExplosable;
	}

	public void setEstExplosable(boolean estExplosable) {
		this.estExplosable = estExplosable;
			
	}

	/*pour l'affichage dans le terminal*/
	public String toString() {
		if(this.bonbon.ordinal()==0) //Vert
			return "E";
		if(this.bonbon.ordinal()==1) //Rouge
			return "R";
		else if(this.bonbon.ordinal()==2) //Bleu
			return "B";
		else if(this.bonbon.ordinal()==3) //Jaune
			return "J";
		else if(this.bonbon.ordinal()==4) //Orange
			return "O";
		else							 // Violet
			return "V"; 
	}	
}
