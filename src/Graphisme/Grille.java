package Graphisme;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import Jeu.Case;
import Jeu.Jeu;
import Jeu.Jeu1;

public class Grille extends JPanel implements Observable, MouseListener, MouseMotionListener{
	
	private ArrayList<Observateur> listObservateur = new ArrayList<Observateur>();
	
	private static int distance40 = 40;

	
	private Jeu1 jeuCandy; // travailler avec une interface Jeu1 normalement !!!! (ici ne fonctionne pas car la methode getPlateau est dans regle du jeu mais n'est pas dans Jeu1 ...)
	
	private Case caseSave;
	
	public static int scoreFinal;
	public static int coupFinal;
	
	private static Position tabPos[][];
	
	compareTab compTab;
	
	boolean agitation;
	boolean premierTourDagitation;
	
	boolean toDrag;
		
	public Grille(int largeur, int hauteur, int scoreF, int coupF) {
		
		this.scoreFinal = scoreF;
		this.coupFinal = coupF;
			
		jeuCandy = new Jeu(largeur,hauteur);
		
		compTab = new compareTab(jeuCandy.getPlateau().getPlateau());
		
		tabPos = new Position[largeur][hauteur];
		initPositions();
		
		repaint();
	
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		
		this.miseAjourObservateur();
		
	}

	int xDown, yDown, yUp, xUp, deltaX, deltaY;


	public void mousePressed(MouseEvent e) {
		if(e.getX()<distance40*jeuCandy.getLargeur() && e.getY()<distance40*jeuCandy.getHauteur()){
			xDown = e.getX()/distance40;
			yDown = e.getY()/distance40;
			deltaX = e.getX() - tabPos[xDown][yDown].getX();
			deltaY = e.getY() - tabPos[xDown][yDown].getY();
			caseSave = jeuCandy.getPlateau().getBonbon(xDown,yDown);
			System.out.println(xDown+","+yDown+" :"+caseSave+" explose "+caseSave.getEstExplosable()+" rayé Verticale "+caseSave.getEstRayeV()+" rayé Horizontale "+caseSave.getEstRayeH()+" multiCouleur "+caseSave.getEstMultiCouleur());
			toDrag = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		resetPositions();
		toDrag = false;
		//si se situe sur le plateau
		if(e.getX()<distance40*jeuCandy.getLargeur() && e.getY()<distance40*jeuCandy.getHauteur()){
			xUp = e.getX()/distance40;
			yUp = e.getY()/distance40;

			if(jeuCandy.getC().getScore()<scoreFinal || jeuCandy.getC().getNbCoup()<coupFinal){
				jeuCandy.jouer(yDown, xDown, yUp, xUp);
				
				this.compTab.miseAjour(jeuCandy.getPlateau().getPlateau());
				
				System.out.println("LE SCORE EST DE:"+jeuCandy.getScore());
				//met a jour le score :
				Grille.this.miseAjourObservateur();
				repaint();
				try{
					Thread.sleep(100);
				} catch (InterruptedException e2){
					e2.printStackTrace();
				}
				
				// ne sen sert pas pour l'instant
				agitation = false;
				premierTourDagitation = false;
				
				
				
				//animation();
			}else{
				repaint();
				System.out.println("FIN DE JEU LE SCORE EST DE:"+jeuCandy.getScore());
			}
		}
	}
	//@Override
	public void mouseDragged(MouseEvent e) {
		tabPos[xDown][yDown].setX(e.getX() - deltaX);
		tabPos[xDown][yDown].setY(e.getY() - deltaY);
		System.out.println("yes" + e.getX());
		repaint(); 
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	public void mouseClicked(MouseEvent arg0) {
		//System.out.println("lol");
	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}



	
	public Jeu1 getJeuCandy() {
		return jeuCandy;
	}

	public void setJeuCandy(Jeu jeuCandy) {
		this.jeuCandy = jeuCandy;
	}

	public void paintComponent(Graphics g){
		
		/*System.out.println("2bis");
		
		if(agitation == true){
			System.out.println("2");
			if(premierTourDagitation){
				System.out.println("3");
				g.drawImage(new ImageIcon("fond.png").getImage(), 0, 0,800,800, this);
				for(int i=0; i<jeuCandy.getHauteur(); i++){	
					for(int j=0; j<jeuCandy.getLargeur(); j++){
						if(compTab.casesARafraichire[i][j] == false)
							g.drawImage(jeuCandy.getCase(i,j).getImgIc().getImage(),tabPos[j][i].getX(), tabPos[j][i].getY(), distance40, distance40, this);
						if(compTab.casesEnMouvement[i][j] == true)
							g.drawImage(jeuCandy.getCase(i,j).getImgIc().getImage(),tabPos[j][i].getX(), tabPos[j][i].getY(), distance40, distance40, this);
					}	
				}
			premierTourDagitation = false;
			} else {
				System.out.println("4");
				for(int i=0; i<jeuCandy.getHauteur(); i++){	
					for(int j=0; j<jeuCandy.getLargeur(); j++){
						if(compTab.casesEnMouvement[i][j] == true)
							g.drawImage(jeuCandy.getCase(i,j).getImgIc().getImage(),tabPos[j][i].getX(), tabPos[j][i].getY(), distance40, distance40, this);
					}	
				}
			}
		}
		else {*/

			System.out.println("5");
			g.drawImage(new ImageIcon("fond.png").getImage(), 0, 0,800,800, this);
			if(jeuCandy.getC().getScore()<scoreFinal || jeuCandy.getC().getNbCoup()<coupFinal){
				for(int i=0; i<jeuCandy.getHauteur(); i++){	
					for(int j=0; j<jeuCandy.getLargeur(); j++){
						if(toDrag == true){
							if (((i == xDown) && (j == yDown))){
								System.out.println("ntm");
							} else {
								g.drawImage(jeuCandy.getCase(i,j).getImgIc().getImage(),tabPos[j][i].getX(), tabPos[j][i].getY(), distance40, distance40, this);
							}
						}
						else {
							g.drawImage(jeuCandy.getCase(i,j).getImgIc().getImage(),tabPos[j][i].getX(), tabPos[j][i].getY(), distance40, distance40, this);
						}
					}
				}
				
			}else{
				for(int i=0; i<jeuCandy.getHauteur(); i++){	
					for(int j=0; j<jeuCandy.getLargeur(); j++){
						g.drawImage(jeuCandy.getCase(i,j).getImgIc().getImage(),tabPos[j][i].getX(), tabPos[j][i].getY(), distance40, distance40, this);
					}
				}
				g.drawImage(new ImageIcon("gameover.png").getImage(), 0, 0,400,400, this);
				Font texte = new Font("Century Schoolbook L Bold", 3, 20);
				g.setFont(texte);
				g.drawString("Votre score est de : "+jeuCandy.getScore(),100, 270);
				//g.drawString("vous avez joué : "+nbCoup+" coups ",90, 290);
			}
		//}
		//if(toDrag == true){
			g.drawImage(jeuCandy.getCase(xDown,yDown).getImgIc().getImage(),tabPos[yDown][xDown].getX(), tabPos[yDown][xDown].getY(), distance40, distance40, this);
		//}
	}
	
	
	//fortement inspiré du site du zero (open classroom) :
		
	//Ajoute un observateur à la liste
	public void ajouterUnObservateur(Observateur obs) {
		this.listObservateur.add(obs);
	}
	//Retire tous les observateurs de la liste
	public void supprimerUnObservateur() {
		this.listObservateur = new ArrayList<Observateur>();
	}
	//Avertit les observateurs que l'objet observable a changé
	//et invoque la méthode update() de chaque observateur
	public void miseAjourObservateur() {
		for(Observateur obs : this.listObservateur ){
			obs.miseAjour(this.jeuCandy.getC());
		}
		
	}
	
	public Position[][] getTabPos(){
		return this.tabPos;
	}

	public void initPositions(){
		Position tempPos;
		for(int i=0; i<jeuCandy.getHauteur(); i++){	
			for(int j=0; j<jeuCandy.getLargeur(); j++){
				tempPos = new Position(j*(distance40), i*distance40);
				this.getTabPos()[j][i] = tempPos;
			}
		}
	}
	
	public void resetPositions(){
		for(int i=0; i<jeuCandy.getHauteur(); i++){	
			for(int j=0; j<jeuCandy.getLargeur(); j++){
				this.getTabPos()[j][i].setX(j*(distance40));
				this.getTabPos()[j][i].setY(i*distance40);
			}
		}
	}
	
	// A REVOIR !!!!
	public void animation(){
		for(int k = 0; k <distance40; k++){
			for(int j = 0; j<jeuCandy.getLargeur(); j++){
				for(int i = 0; i<jeuCandy.getHauteur(); i++){
					if (compTab.casesEnMouvement[i][j] == true){
						tabPos[i][j].setY(tabPos[i][j].getY()-1);
					}
				}
			}
			try{
				Thread.sleep(100);
			} catch (InterruptedException e2){
				e2.printStackTrace();
			}
			repaint();
			System.out.println(agitation);
			System.out.println("1");
		}
		agitation = false;
	}
}
