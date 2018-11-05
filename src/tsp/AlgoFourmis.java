package tsp;

import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class AlgoFourmis.
 */
public class AlgoFourmis {
	
	/** Coefficient portant sur l'importance des pheromones. */
	private int alpha=4;
	/** Coefficient portant sur l'importance de la visibilite. */
	private int beta=4;
	/** Coefficient qui definit la vitesse d'evaporation des pheromone. */
	private double rho=0.5;
	
	/** nombre de fourmis effectuant les chemins. */
	private int nb_fourmis= 80;
	
	/**  nombre de tours effectués par les fourmis. */
	private int nb_tours_fourmis=2;
	
	/**  solution lue par le main. */
	public Solution m_solution;
	
	/** The secretion max. */
	//* borne superieur fixée pour la quantité de phéromones*/
	private double secretion_max=100000;
	
	/** The secretion min. */
	//*borne inferieur fixée pour la quantité de phéromones
	private double secretion_min=0.1;

	/** The Instance of the problem. */
	public Instance m_instance;

	/** Time given to solve the problem. */
	private long m_timeLimit;
	
	/**
	 * Instantiates a new algo fourmis.
	 */
	public AlgoFourmis() {
		super();
	}
	
	/**
	 * Instantiates a new algo fourmis.
	 *
	 * @param instance : instance du probleme
	 * @param solution : solution a retourner
	 * @param Tlimit : temps limite
	 */
	public AlgoFourmis(Instance instance, Solution solution, long Tlimit){
		this.m_instance=instance;
		this.m_solution=solution;
		this.m_timeLimit=Tlimit;
	}	
	
	/**
	 * Initialise la matrice des pheromones.
	 *
	 * @param nbVilles nombre de ville dans le probleme
	 * @return La matrice de pheromone initialise a 0.5
	 */
	public static double[][] MatricePheromone(int nbVilles){
		double[][] matricePheromone = new double[nbVilles][nbVilles];
		for(int ville=0;ville<nbVilles;ville++) {
			for(int v=0;v<nbVilles;v++) {
				matricePheromone[ville][v]=0.5;
			}
			
		}
		return matricePheromone;
			
		
}
	
	/**
	 *  place les fourmis de facon équirepartie sur les villes, en mettant aléatoirement les fourmis restantes (reste de la divison euclidienne de nbville par nbfourmis).
	 *
	 * @param nbFourmis nombre de fourmis
	 * @param nbVilles nombre de villes
	 * @return retourne la liste correpondant au nombre de fourmis initial sur chacune des villes
	 */
	public static ArrayList<Integer> repartitionFourmis(int nbFourmis, int nbVilles) {
		ArrayList<Integer> listeDepart = new ArrayList<Integer>();
		int nbFourmisRestant = nbFourmis%nbVilles;
		int maxRepartition = nbFourmis/nbVilles;
		for(int ville=0;ville<nbVilles;ville++) {
			
			listeDepart.add(maxRepartition);
			
		}
		for(int fourmis=0;fourmis<nbFourmisRestant;fourmis++) {
			int villes = (int)(Math.random()*nbVilles);
			listeDepart.set(villes, listeDepart.get(villes)+1);
		}
	
		return listeDepart;
		
		
	}
	
	
	/**
	 * methode qui calcule le trajet effectué par chaque fourmis pour effectuer chacune leur parcours de toute les villes.
	 *
	 * @param nbFourmis nombre de fourmis
	 * @param nbVilles nombre de villes
	 * @param pheromone tableau qui repertorie la quantité de phéromones sur le trajet entre la ville i et j a l'nedroit phéromone[i][j]
	 * @param distance la matrice distance, copie de instance.getDistances()
	 * @param alpha coefficient portant sur l'importance des phéromones
	 * @param beta coefficient portant sur l'importance des trajets
	 * @return le tableau repertoriant les trajets de fourmis
	 */
	public static int[][] AlgoFourmisVoyCommerce(int nbFourmis, int nbVilles, double[][] pheromone, long[][] distance, int alpha, int beta) {
		int[][] trajet = new int[nbFourmis][nbVilles+1]; //Matrice des trajets de chaque fourmis 
		ArrayList<Integer> repartition = repartitionFourmis(nbFourmis,nbVilles);
		int index =0; // represente le numero de fourmis
		
		for(int villeActuel=0;villeActuel<nbVilles;villeActuel++) {
			int nb = repartition.get(villeActuel); // nombre de fourmis pour la ville actuelle
			for(int fourmis=0;fourmis<nb;fourmis++) {
				trajet[index][0]=villeActuel;
				System.out.println(trajet[index][0]);
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

			while(villeNonVisite.size()!=0 && temp<nbVilles-1) {
				Integer villeActuel = trajet[fourmis][temp];
				villeNonVisite.remove(villeActuel);
				double sommeProba=0;
				ArrayList<Double> proba= new ArrayList<Double>(); //Liste de probabilite dont l'index represente la ville 

				for(int j : villeNonVisite) {
					sommeProba = sommeProba+Math.pow(pheromone[villeActuel][j], alpha)*Math.pow((1/(distance[villeActuel][j])), beta);
				}
				
				for(int j : villeNonVisite) { //pour chaque ville non visitee
					proba.add((Math.pow(pheromone[villeActuel][j], alpha)*Math.pow((1/(distance[villeActuel][j])), beta))/sommeProba);
					
				}
				System.out.println(villeNonVisite);
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
					}
					
				}
				temp++;
				trajet[fourmis][temp]=villeNonVisite.get(ville);
					
				
				 // deplacement de la fourmis fourmis a la ville suivante temp
				
				
				
			}
			temp=0;
			
				
			}
		
		
		return trajet;
		
		}
	
	/**
	 * algorithme final qui fait evoluer la matrice phéromone a travers les differents passages des fourmis pour aider les fourmis suivante a trouver un meilleur chemin et ainsi obtenir le chemin optimisé.
	 *
	 * @param instance instance du probleme
	 * @param solution the solution
	 * @param tlimit temps limite
	 * @return the int[]
	 */
	public int[] full_algo_fourmis (Instance instance, Solution solution, long tlimit) {
		int k=nb_fourmis;
		int n=instance.getNbCities();
		double[][] secretion=MatricePheromone(n);
		int [][] trajets_f= new int[k][n+1];
		long[][] distance = instance.getDistances();
		int [] meilleur_trajet=new int[n+1];
		long meilleure_distance=1000000000;
		for (int t=0; t<nb_tours_fourmis;t++) {
			trajets_f=AlgoFourmisVoyCommerce(this.nb_fourmis, n,secretion, distance, alpha, beta);//actualisation du tableau représentant les trajet des fourmis
			long[]distance_fourmis= new long[nb_fourmis];
			for (int i=0;i<k;i++) {
				long distance1=0;//calcul de la distance parcouru par la fourmis
				for (int j=0;j<n;j++){
					distance1+=distance[trajets_f[i][j]][trajets_f[i][j+1]];
				}
				distance_fourmis[i]=distance1;
				if (distance1<meilleure_distance) {
					meilleure_distance=distance1;
					meilleur_trajet=trajets_f[i];
				}
			}
			for (int i=0;i<n;i++) {
				for (int j=0;j<n;j++) {
					secretion[i][j]=secretion[i][j]*(1-rho);
				}
			}
			
			for (int i=0;i<nb_fourmis;i++) {//pour chaque fourmis
				for (int j=0;j<n-1;j++) {//pour chaque trajet effectué ( c a d passage de la ville j a j+1)
					secretion[trajets_f[i][j]][trajets_f[i][j+1]]+=100000/distance_fourmis[i];//actualisation des secretion
				}
			}
			
			for (int i=0;i<n;i++) {
				for (int j=0;j<n;j++) {// limiter la quantité de phéromones presents sur chaque trajet pour eviter de prioriser un trajet 
					if (secretion[i][j]>=this.secretion_max) {
						secretion[i][j]=this.secretion_max;
					}
					if (secretion[i][j]<=this.secretion_min) {
						secretion[i][j]=this.secretion_min;
					}
				}
				}
			}
		return meilleur_trajet;
		
		
		
	}
	
	
	
}
