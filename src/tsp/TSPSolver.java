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
public class TSPSolver extends AlgoFourmis {

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
	private long m_timeLimit=60;

	
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
		
		Solution sol = new Solution(m_instance);
		PlusProcheVoisin p = new PlusProcheVoisin(m_instance,m_timeLimit,sol);
		
		ArrayList<Integer> l = p.PlusProcheVoisinCalcul();
		//System.out.println(l);
		ArrayList<Integer> secondopt = p.Secondopt(l);
		
		
		ArrayList<Integer> decaler = new ArrayList<>();
		decaler.add(secondopt.get(secondopt.size()-1));
		for(int i=0;i<secondopt.size()-1;i++) {
			decaler.add(secondopt.get(i));
		}
		for(int i=0;i<secondopt.size();i++) {
			sol.setCityPosition(decaler.get(i), i);
			System.out.println(decaler.get(i));
		}
		sol.evaluate();
		this.m_solution = sol;
		
		
		/* A decommenter pour utiliser l'algorithme fourmi
		//Algorithme fourmi 
		m_solution.print(System.err);
		
		
		// Example of a time loop
		long startTime = System.currentTimeMillis();
		long spentTime = 0;
		Solution Sol = new Solution(m_instance);
		int[] result = new int[m_instance.getNbCities()+1];
		do
		{
			// TODO
			// Code a loop base on time here
			result = full_algo_fourmis(this.m_instance,this.m_solution,this.m_timeLimit);
		
			spentTime = System.currentTimeMillis() - startTime;
		}while(spentTime <0.006);
		
	
		int n = result.length;
		int j = 0;
		int[] temp = new int[result.length];
		
		for(int i=0;i<n;i++) {
			if(result[i]==0) {
				j=i;
			}
		}
		
		for(int i=0;i<n-1;i++) {
			if(i>=j) {
				temp[i-j]=result[i];
			}else {
				temp[n-i+j-2]=result[i];
				
			}
		}
		temp[n-1]=0;
		temp[n-2]=result[0];
		
		for(int i=0;i<temp.length;i++) {
			Sol.setCityPosition(temp[i], i);
			System.out.println(temp[i]);
		}
					
	
		
		Sol.evaluate();
		this.m_solution = Sol;
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
