package tsp;

public class PlusProcheVoisin {

	
	private Instance m_instance;
	private long m_timeLimit;
	private Solution m_solution;
	
	public PlusProcheVoisin(Instance m_instance, long m_timeLimit, Solution m_solution) {
		this.m_instance = m_instance;
		this.m_solution = m_solution;
		this.m_timeLimit = m_timeLimit;
	}
	
	public 
	int n = m_instance.getNbCities();
	Solution Sol = new Solution(m_instance);
	long[][] m_distances = m_instance.getDistances();
	ArrayList<Integer> v_visites = new ArrayList<Integer>(); //villes visites
	ArrayList<Integer> v_avisiter = new ArrayList<Integer>(); //villes a visiter
	int villeactuelle=0;
	v_visites.add(villeactuelle);
	int k=0;
	Sol.setCityPosition(villeactuelle, k);
	Sol.setCityPosition(villeactuelle, n);
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
		villeactuelle=ville;
	}

	Sol.evaluate();
	this.m_solution=Sol;
}
