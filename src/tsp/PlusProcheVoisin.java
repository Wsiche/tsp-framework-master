package tsp;

import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class PlusProcheVoisin.
 */
public class PlusProcheVoisin {

	
	/** The m instance. */
	private Instance m_instance;
	
	/** The m time limit. */
	private long m_timeLimit;
	
	/** The m solution. */
	private Solution m_solution;
	
	/**
	 * Instantiates a new plus proche voisin.
	 *
	 * @param m_instance the m instance
	 * @param m_timeLimit the m time limit
	 * @param m_solution the m solution
	 */
	public PlusProcheVoisin(Instance m_instance, long m_timeLimit, Solution m_solution) {
		this.m_instance = m_instance;
		this.m_solution = m_solution;
		this.m_timeLimit = m_timeLimit;
	}
	
	
	/**
	 * Calul avec la methode du plus proche voisin.
	 *
	 * @return Le chemin du point initial 0 a
	 * la position finale 0, c'est a dire la liste de l'ensemble des villes parcouru
	 * @throws Exception the exception
	 */
	public ArrayList<Integer> PlusProcheVoisinCalcul() throws Exception {
		int n = m_instance.getNbCities();
		Solution Sol = new Solution(m_instance);
		ArrayList<Integer> SolutionListe = new ArrayList<Integer>();
		long[][] m_distances = m_instance.getDistances();
		ArrayList<Integer> v_visites = new ArrayList<Integer>(); //villes visites
		ArrayList<Integer> v_avisiter = new ArrayList<Integer>(); //villes a visiter
		int villeInitiale = 0;
		int villeactuelle=0;
		v_visites.add(villeactuelle);
		int k=0;
		Sol.setCityPosition(villeactuelle, k);
		Sol.setCityPosition(villeactuelle, n);
		SolutionListe.add(villeactuelle);
		for (int i = 1; i < n; i++) {
			v_avisiter.add(i);
		}
	
	
		while (v_avisiter.size()>0) {
			int ville=v_avisiter.get(0); // recherche de la nouvelle ville à visiter
			long distance=m_distances[villeactuelle][v_avisiter.get(0)];
			for ( int u : v_avisiter) {//recherche parmis toutes les villes non visitees
				if (distance>m_distances[villeactuelle][u]) {
					distance = m_distances[villeactuelle][u];
					ville=u;
				}
			}
				v_visites.add(ville);
				Integer city = ville;
				v_avisiter.remove(city);
				k+=1;
				Sol.setCityPosition(ville, k);
				SolutionListe.add(ville);
				villeactuelle=ville;
		}

		//Sol.evaluate();
		//this.m_solution=Sol;
		SolutionListe.add(villeInitiale);
		return SolutionListe;
	}

	
	/**
	 * Secondopt.
	 *
	 * @param SolutionListe the solution liste
	 * @return the array list
	 */

	public ArrayList<Integer> Secondopt(ArrayList<Integer> SolutionListe){
		int n = SolutionListe.size();
		
		for ( int i = 0; i < n-1; i++ ) {
			for ( int j = i+1; j < n; j++ ) {
				long nb = calculDistance(SolutionListe);
				Integer a = SolutionListe.get(i);
				SolutionListe.set(i, SolutionListe.get(j));
				SolutionListe.set(j, a);
				if (nb < calculDistance(SolutionListe)) {
					Integer b = SolutionListe.get(i);
					SolutionListe.set(i, SolutionListe.get(j));
					SolutionListe.set(j, b);
				}
			}
		}
		int temp = SolutionListe.get(0);
		//SolutionListe.remove(0);
		//SolutionListe.add(temp);
		return SolutionListe;
	}
	
	/**
	 * Calcul distance.
	 *
	 * @param chemin the chemin
	 * @return the long
	 */
	public long calculDistance (ArrayList<Integer> chemin) {
		int n = m_instance.getNbCities();
		long[][] distance = m_instance.getDistances();
		long distance1=0;//calcul de la distance parcouru par la fourmis
		for (int j=0;j<n;j++){
			distance1+=distance[chemin.get(j)][chemin.get(j+1)];
		}
		return distance1;
	
	}
}
