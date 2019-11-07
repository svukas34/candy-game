package Graphisme;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Fenetre extends JFrame implements ActionListener {

	public int taille = 7;
	private PanGenView panPrincipale;
	private JButton boutonReset;
	
	public Fenetre(){
		panPrincipale = new PanGenView(taille);
		boutonReset = new JButton("RESET");
		this.setTitle("Candy Crush JAVA Diderot");
		this.setSize(taille*40, taille*40 + 60);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		boutonReset.addActionListener(this);
		
		this.getContentPane().add(boutonReset, BorderLayout.SOUTH);
		this.getContentPane().add(panPrincipale, BorderLayout.CENTER);
		this.setVisible(true);	
	}
	
	public static void main(String[] args){
		new Fenetre();
	}
	
	public void actionPerformed(ActionEvent arg0) {
		this.getContentPane().removeAll();
		this.panPrincipale = new PanGenView(taille);
		this.getContentPane().add(panPrincipale, BorderLayout.CENTER);
		this.getContentPane().add(boutonReset, BorderLayout.SOUTH);
		this.getContentPane().revalidate();
		this.getContentPane().repaint();
	}
}