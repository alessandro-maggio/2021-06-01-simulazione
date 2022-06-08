package it.polito.tdp.genes.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.genes.db.Coppia;
import javafx.event.EventTarget;

public class Simulazione {
	
	
	
	
	//coda degli eventi
	private PriorityQueue<Evento> queue;
	
	//dati input
	private Genes gene;
	private double probabilità;
	
	//dati output
	private List<Genes> geni_analizzati;
	private int nIgnegneri_associati;
	
	//stato del mondo
	private Map<Integer, PriorityQueue<Evento>> treAnni;
	private Graph<Genes, DefaultWeightedEdge> grafo;
	private List<Coppia> archi;
	
	
	
	
	public void init(int soglia, Genes gene, Graph<Genes, DefaultWeightedEdge> grafo, List<Coppia> archi) {
		
		this.queue= new PriorityQueue<>();
		this.treAnni= new HashMap<Integer, PriorityQueue<Evento>>();
		this.gene= gene;
		this.grafo= grafo;
		this.archi= archi;
		
		
		for(int i=1; i<=soglia; i++) {   //per ogni mese avrò n ingegneri che lavorano su un certo gene
			Double p= Math.random();
			Evento evento= new Evento(gene, archi, grafo);
			queue.add(evento);
		}
		
		
	}
	
	
	
	public PriorityQueue<Evento> run() {
		
		for(int i=0; i<36; i++) { //ogni mese scorro gli eventi
			for(Evento e: queue) {
				e.setProbabilità();
				if(e.getProbabilità()>0.3) {
					double pb= Math.random();
					for(Genes g: e.getVicini()) {
						if(pb<= g.getProbabilita()) {
							e.setGenes(g);
							break;
						}
					}
				}
			}
		}
		
		return queue;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
/*	//output
	private List<Genes> geni_in_corso_di_studio;
	private int nIngegneri;
	
	//coda degli eventi
	PriorityQueue<Evento> queue;
	
	Map<Integer, PriorityQueue<Evento>> mapEventi;
	private Graph<Genes, DefaultWeightedEdge> grafo;
	
	
	public void init(int n, Genes gene) {
		
		this.queue= new PriorityQueue<>();
		this.mapEventi= new HashMap<>();
		
		for(int i=0; i<=36; i++) {
			
			List<Evento> eventi= new ArrayList<>();
			for(int j=0; j<=n; j++) {
				Double caso= Math.random();
				Evento e= new Evento(caso, gene);
				queue.add(e);
			}
			
			mapEventi.put(i, queue);
		}
		
	}
	
	public void run(Genes gene, Graph grafo) {
		
		this.grafo= grafo;
		
		for(PriorityQueue<Evento> m: mapEventi.values()) {   //scorro i 36 mesi
			for(Evento e: mapEventi.get(m)) {  //per ogni mese scorro gli eventi
				if(e.getProbabilità()>0.3) {
					
				}
			}
		}
		
		
	}
	
	
	public void getPesi(Genes gene){
		
		double pesoTot=0;
		
		for(Genes g: Graphs.neighborListOf(this.grafo, gene)) {
			pesoTot+=this.grafo.getEdgeWeight(this.grafo.getEdge(gene, g));
		}
		
		for(Genes g: Graphs.neighborListOf(this.grafo, gene)) {
			
		}
		
		
		
	}*/
	

}
