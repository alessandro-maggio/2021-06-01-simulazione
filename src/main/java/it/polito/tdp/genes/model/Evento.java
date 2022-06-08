package it.polito.tdp.genes.model;

import java.awt.im.InputMethodHighlight;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.genes.db.Coppia;

public class Evento implements Comparable<Evento>{
	
	public enum EventType {
		
		STESSO,
		AGGIORNO
		
	}
	
	private Double probabilità=0.0;
	private Genes gene;
	private Map<Genes, Double> mappaProbabilità;
	private EventType tipo; //non l'ho usato
	private List<Genes> vicini;
	
	
	public Evento(Genes gene, List<Coppia> archi,Graph<Genes, DefaultWeightedEdge> grafo) {
		super();
		this.gene = gene;
		
		this.vicini= Graphs.neighborListOf(grafo, gene);
		double pesoTot=0;
		
		for(Genes g: vicini) {  //calcolo il peso totale degli archi vicini
			for(Coppia c: archi) {
				if(c.getG1().equals(gene) || c.getG2().equals(gene)) {
					if(c.getG1().equals(g) || c.getG2().equals(g)) {
						pesoTot+= c.getPeso();
					}
				}
			}
		}
		
		double precedente=0;
		
		for(Genes g: vicini) {
			precedente+= grafo.getEdgeWeight(grafo.getEdge(gene, g))/pesoTot;
			g.setProbabilita(precedente);
		}
		
		Collections.sort(vicini);
	}
	
	
	
	

	public List<Genes> getVicini() {
		return vicini;
	}





	public Double getProbabilità() {
		return probabilità;
	}



	public void setProbabilità() {
		this.probabilità = Math.random();
	}



	public Genes getGenes() {
		return gene;
	}



	public void setGenes(Genes gene) {
		this.gene = gene;
	}
	
	



	public Map<Genes, Double> getMappaProbabilità() {
		return mappaProbabilità;
	}





	@Override
	public int compareTo(Evento o) {
		return this.probabilità.compareTo(o.probabilità);
	}
	
	

}
