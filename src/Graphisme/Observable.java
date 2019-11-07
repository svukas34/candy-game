package Graphisme;

//fortement inspiré du site du zero (open classroom)
public interface Observable {
	public void ajouterUnObservateur(Observateur obs);
	public void miseAjourObservateur();
	public void supprimerUnObservateur();
}
