 package Graphisme;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import Jeu.Conneries;


public class PanGenView extends JPanel {

	JPanel apercu;
	Grille grilleCandy;
	JLabel scoreLabel;
	JLabel coupLabel;
	int taille;
	
	public static int scoreFinal = 100000;
	public static int coupFinal = 50;
	
	public PanGenView(int taille){
		this.taille = taille;
		this.setLayout(new BorderLayout());
		grilleCandy = new Grille(taille,taille,scoreFinal, coupFinal);
		
		apercu = new JPanel();
		scoreLabel = new JLabel();
		coupLabel = new JLabel();
		//scoreLabel.setText(""+grilleCandy.getJeuCandy().getScore());
		
		
		this.grilleCandy.ajouterUnObservateur(new Observateur(){
			public void miseAjour(Conneries c){
				scoreLabel.setText(""+c.getScore()+" /"+scoreFinal);
				coupLabel.setText(""+c.getNbCoup()+" /"+coupFinal);
			}
		});
		
		
		JPanel sousApercu = new JPanel();
		JPanel sousApercu2 = new JPanel();
		
		JLabel blabla = new JLabel();
		blabla.setText("score :");
		sousApercu.add(blabla);
		sousApercu.add(scoreLabel);
		
		JLabel blabla2 = new JLabel();
		blabla2.setText("nb coups :");
		sousApercu2.add(blabla2, BorderLayout.SOUTH);
		sousApercu2.add(coupLabel);
		
		apercu.add(sousApercu,BorderLayout.NORTH);
		apercu.add(sousApercu2, BorderLayout.SOUTH);
		
		this.add(apercu,BorderLayout.NORTH);
		this.add(grilleCandy, BorderLayout.CENTER);
	}

	public static int getScoreFinal() {
		return scoreFinal;
	}

	public static int getCoupFinal() {
		return coupFinal;
	}	
	
}
