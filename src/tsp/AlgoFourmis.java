package tsp;

import java.util.ArrayList;

public class AlgoFourmis {
	private int alpha=1;
	private int beta=5;
	private double rho=0.5;
	private int nb_fourmis= 1000;
	private int nb_tours_fourmis=100;
	private Solution m_solution;

	/** The Instance of the problem. */
	private Instance m_instance;

	/** Time given to solve the problem. */
	private long m_timeLimit;
	
	public AlgoFourmis(Instance instance, Solution solution, long Tlimit){
		this.m_instance=instance;
		this.m_solution=solution;
		this.m_timeLimit=Tlimit;
	}
	
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
	public static int[][] AlgoFourmisVoyCommerce(int nbFourmis, int nbVilles, double[][] pheromone, long[][] distance, int alpha, int beta) {
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

	public void full_algo_fourmis (Instance instance, Solution solution, long tlimit) {
		int k=nb_fourmis;
		int n=instance.getNbCities();
		double[][] secretion=MatricePheromone(n);
		int [][] trajets_f= new int[k][n];
		long[][] distance = instance.getDistances();
		for (int t=0; t<nb_tours_fourmis;t++) {
			AlgoFourmisVoyCommerce(this.nb_fourmis, n,secretion, distance, alpha, beta);
			int[]distance_fourmis= new int[nb_fourmis];
			for (int i=0;i<n;i++) {
				for (int j=0;j<n;j++) {
					secretion[i][j]=secretion[i][j]*(1-rho);
				}
			}
			for (int i=0;i<nb_fourmis;i++) {
				for (int j=0;j<n-1;j++) {
					secretion[trajets_f[i][j]][trajets_f[i][j+1]]+=1/distance_fourmis[i];
				} , 
			}
			for (int i=0;i<n;i++) {
	}
}
