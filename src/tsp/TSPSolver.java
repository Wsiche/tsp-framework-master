package tsp;

import java.util.ArrayList;

/**
 * 
 * This class is the place where you should enter your code and from which you can create your own objects.
 * 
 * The method you must implement is solve(). This method is called by the programmer after loading the data.
 * 
 * The TSPSolver object is created by the Main class.
 * The other objects that are created in Main can be accessed through the following TSPSolver attributes: 
 * 	- #m_instance :  the Instance object which contains the problem data
 * 	- #m_solution : the Solution object to modify. This object will store the result of the program.
 * 	- #m_timeLimit : the maximum time limit (in seconds) given to the program.
 *  
 * @author Damien Prot, Fabien Lehuede, Axel Grimault
 * @version 2017
 * 
 */
public class TSPSolver {

	// -----------------------------
	// ----- ATTRIBUTS -------------
	// -----------------------------

	/**
	 * The Solution that will be returned by the program.
	 */
	private Solution m_solution;

	/** The Instance of the problem. */
	private Instance m_instance;

	/** Time given to solve the problem. */
	private long m_timeLimit;

	
	// -----------------------------
	// ----- CONSTRUCTOR -----------
	// -----------------------------

	/**
	 * Creates an object of the class Solution for the problem data loaded in Instance
	 * @param instance the instance of the problem
	 * @param timeLimit the time limit in seconds
	 */
	public TSPSolver(Instance instance, long timeLimit) {
		m_instance = instance;
		m_solution = new Solution(m_instance);
		m_timeLimit = timeLimit;
	}

	// -----------------------------
	// ----- METHODS ---------------
	// -----------------------------

	/**
	 * **TODO** Modify this method to solve the problem.
	 * 
	 * Do not print text on the standard output (eg. using `System.out.print()` or `System.out.println()`).
	 * This output is dedicated to the result analyzer that will be used to evaluate your code on multiple instances.
	 * 
	 * You can print using the error output (`System.err.print()` or `System.err.println()`).
	 * 
	 * When your algorithm terminates, make sure the attribute #m_solution in this class points to the solution you want to return.
	 * 
	 * You have to make sure that your algorithm does not take more time than the time limit #m_timeLimit.
	 * 
	 * @throws Exception may return some error, in particular if some vertices index are wrong.
	 */
	
	public long miniDistance(long[] m, ArrayList<Integer> B, int Vactuelle) {
		
		long a = m[B.get(0)];
		for (int i : B) {
			if ((m[i] < a)&&(i!=Vactuelle)) {
				a = m[i];
			}
		}
		return a;
	}
	
	public int miniVille(long[] m, ArrayList<Integer> B, int Vactuelle ) {
		int b = 0;
		long a = m[B.get(0)];
		for (int i : B) {
			if ((m[i] < a)&&(i!=Vactuelle)) {
				a = m[i];
				b = i;
			}
		}
		return b;
	}
	//retourne la matrice de pheromone
	public static double[][] MatricePheromone(int nbVilles){
		double[][] matricePheromone = new double[nbVilles][nbVilles];
		for(int ville=0;ville<nbVilles;ville++) {
			for(int v=0;v<nbVilles;v++) {
				matricePheromone[ville][v]=0.5;
			}
			
		}
		return matricePheromone;
			
		
}
	//methode qui retourne la liste ou l'element correpond au nombre de fourmis et l'index a la ville d'index index
	public static ArrayList<Integer> repartitionFourmis(int nbFourmis, int nbVilles) {
		ArrayList<Integer> listeDepart = new ArrayList<Integer>();
		int nbFourmisRestant = nbFourmis%nbVilles;
		int maxRepartition = nbFourmis/nbVilles;
		for(int ville=0;ville<nbVilles;ville++) {
			
			listeDepart.add(maxRepartition);
			
		}
		for(int fourmis=0;fourmis<nbFourmisRestant;fourmis++) {
			int villes = (int)(Math.random()*nbVilles);
			listeDepart.set(villes, listeDepart.get(villes)+fourmis);
		}
	
		return listeDepart;
		
		
	}
	
	//algorithme fourmis principal
	// methode qui retourne la matrice des trajets de chaque fourmis
	public static int[][] AlgoFourmisVoyCommerce(int nbFourmis, int nbVilles, double[][] pheromone, double[][] distance, int alpha, int beta) {
		int[][] trajet = new int[nbFourmis][nbVilles+1]; //Matrice des trajets de chaque fourmis 
		ArrayList<Integer> repartition = repartitionFourmis(nbFourmis,nbVilles);
		int index =0; // represente le numero de fourmis
		
		for(int villeActuel=0;villeActuel<nbVilles;villeActuel++) {
			int nb = repartition.get(villeActuel); // nombre de fourmis pour la ville actuelle
			for(int fourmis=0;fourmis<nb;fourmis++) {
				trajet[index][0]=villeActuel;
				//System.out.println(trajet[index][0]);
				index++;
			}
			
			
				
			}
		
		for(int fourmis=0; fourmis<nbFourmis;fourmis++) {
			trajet[fourmis][nbVilles]=trajet[fourmis][0];
		}
		int temp=0; // Ville actuelle temporaire
		
		
		
		for(int fourmis=0;fourmis<nbFourmis;fourmis++) {
			ArrayList<Integer> villeNonVisite = new ArrayList<Integer>();

			for(int v=0;v<nbVilles; v++) {// initialisation des villes non visites
				villeNonVisite.add(v);
			}
			System.out.println("passage");
			System.out.println(villeNonVisite);

			while(villeNonVisite.size()!=0 && temp<nbVilles-1) {
				//System.out.println(temp);
				Integer villeActuel = trajet[fourmis][temp];
				System.out.println(fourmis);
				villeNonVisite.remove(villeActuel);
				double sommeProba=0;
				ArrayList<Double> proba= new ArrayList<Double>(); //Liste de probabilite dont l'index represente la ville 

				for(int j : villeNonVisite) {
					sommeProba = sommeProba+Math.pow(pheromone[villeActuel][j], alpha)*Math.pow((1/(distance[villeActuel][j])), beta);
				}
				
				for(int j : villeNonVisite) { //pour chaque ville non visitee
					proba.add((Math.pow(pheromone[villeActuel][j], alpha)*Math.pow((1/(distance[villeActuel][j])), beta))/sommeProba);
					
				}
				//System.out.println(villeNonVisite);
				boolean res = false;
				double alea = Math.random();
				int ville = 0;
				double p = proba.get(0);
				while(!res && p<=1 && ville<proba.size()) {
					if(alea<p) {
						res=true;
					}else {
						ville++;
						p+=proba.get(ville);
						
						//System.out.println(ville);
						
					}
					
				}
				//System.out.println(villeNonVisite);

				//System.out.println(ville);
				temp++;
				trajet[fourmis][temp]=villeNonVisite.get(ville);
					
				
				 // deplacement de la fourmis fourmis a la ville suivante temp
				
				
				
			}
			temp=0;
			
				
			}
		
		
		return trajet;
		
		}
	
	
	public void solve() throws Exception{
		/*
		int n = m_instance.getNbCities();
		Solution S = new Solution(m_instance);
		long[][] A = m_instance.getDistances();
		System.out.println(A);
		long DistanceFinale =  0;
		ArrayList<Integer> B = new ArrayList<Integer>();
		ArrayList<Integer> M = new ArrayList<Integer>();
		for (int i = 1; i < n; i++) {
			B.add(i);
		}
		long[] ListeActive = A[0];
		int Vactuelle = 0;
		int k = 1;
		S.setCityPosition(0, 0);
		S.setCityPosition(0, n);
		while (!B.isEmpty()) {
			long miniD = miniDistance(ListeActive, B, Vactuelle);
			int miniV = miniVille(ListeActive, B, Vactuelle);
			System.out.println(miniV);
			System.out.println(ListeActive);
			M.add(miniV);
			Vactuelle = miniV;
			S.setCityPosition(miniV, k);
			DistanceFinale = DistanceFinale + miniD;
			Integer city = miniV;
			B.remove(city);
			System.out.println(B.size());
			ListeActive = A[miniV];
			k = k+1;
			
		}
		S.setObjectiveValue(DistanceFinale);
		*/
		
		
		//algo final nearest neighbour
		/*
		int n = m_instance.getNbCities();
		Solution Sol = new Solution(m_instance);
		long[][] m_distances = m_instance.getDistances();
		ArrayList<Integer> v_visités = new ArrayList<Integer>(); //villes visités
		ArrayList<Integer> v_avisiter = new ArrayList<Integer>(); //villes a visiter
		int villeactuelle=0;
		v_visités.add(villeactuelle);
		int k=0;
		Sol.setCityPosition(villeactuelle, k);
		Sol.setCityPosition(villeactuelle, n);
		for (int i = 1; i < n; i++) {
			v_avisiter.add(i);
		}
		
		
		while (v_avisiter.size()>0) {
			int ville=v_avisiter.get(0); // recherche de la nouvelle ville a visiter
			long distance=m_distances[villeactuelle][v_avisiter.get(0)];
			for ( int u : v_avisiter) {//recherche parmis toutes les villes non visitées
				if (distance>m_distances[villeactuelle][u]) {
					distance = m_distances[villeactuelle][u];
					ville=u;
				}
			}
			v_visités.add(ville);
			Integer city = ville;
			v_avisiter.remove(city);
			k+=1;
			Sol.setCityPosition(ville, k);
			villeactuelle=ville;
		}

		Sol.evaluate();
		this.m_solution=Sol;
		
	}
	*/
		
	}
	
	
	public void solve2() throws Exception
	{
		m_solution.print(System.err);
		
		
		// Example of a time loop
		long startTime = System.currentTimeMillis();
		long spentTime = 0;
		do
		{
			// TODO
			// Code a loop base on time here
			int dummyVariable = 0;
			spentTime = System.currentTimeMillis() - startTime;
		}while(spentTime < (m_timeLimit * 1000 - 100) );
		
	}

	// -----------------------------
	// ----- GETTERS / SETTERS -----
	// -----------------------------

	/** @return the problem Solution */
	public Solution getSolution() {
		return m_solution;
	}

	/** @return problem data */
	public Instance getInstance() {
		return m_instance;
	}

	/** @return Time given to solve the problem */
	public long getTimeLimit() {
		return m_timeLimit;
	}

	/**
	 * Initializes the problem solution with a new Solution object (the old one will be deleted).
	 * @param solution : new solution
	 */
	public void setSolution(Solution solution) {
		this.m_solution = solution;
	}

	/**
	 * Sets the problem data
	 * @param instance the Instance object which contains the data.
	 */
	public void setInstance(Instance instance) {
		this.m_instance = instance;
	}

	/**
	 * Sets the time limit (in seconds).
	 * @param time time given to solve the problem
	 */
	public void setTimeLimit(long time) {
		this.m_timeLimit = time;
	}

}
