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
		//*
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
			int ville=v_avisiter.get(0); // recherche de la nouvelle ville a visiter
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
	/*
	 * 
	 */
	
		
	
	
	
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
