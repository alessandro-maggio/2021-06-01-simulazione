package it.polito.tdp.genes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.genes.db.Coppia;
import it.polito.tdp.genes.db.GenesDao;

public class Model {
	
	private Map<String, Genes> idMap;
	private GenesDao dao;
	private Graph<Genes, DefaultWeightedEdge> grafo;
	private List<Coppia> coppie;
	
	
	public Model() {
		this.dao= new GenesDao();
		idMap= dao.getAllGenes();
	}
	
	public String creaGrafo() {
		
		this.grafo= new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(this.grafo, getEssenziali());
		
		coppie= dao.getArchi(idMap);
		
		for(Coppia c: coppie) {
			
			Graphs.addEdge(this.grafo, c.getG1(), c.getG2(), c.getPeso());
		}
		
		String string="Grafo creato con "+this.grafo.vertexSet().size()+" vertici e "+this.grafo.edgeSet().size()+" archi!\n";
		
		
	
		//System.out.println(Graphs.neighborListOf(this.grafo, idMap.get("G234122")));
		
		return string;
	}
	
	
	public List<Genes> getEssenziali(){
		return dao.getEssenziali(idMap);
	}
	
	
	public List<Coppia> getAdiacenti(Genes gene){
		
		List<Genes> adiacenti= Graphs.neighborListOf(this.grafo, gene);
		List<Coppia> vicini= new ArrayList<>();
		
		for(Genes v: adiacenti) {
			for(Coppia c: coppie) {
				if(c.getG1().equals(gene) || c.getG2().equals(gene)) {
					if(c.getG1().equals(v) || c.getG2().equals(v)) {
						vicini.add(c);
					}
				}
			}
		}
		
		Collections.sort(vicini);
		
		return vicini;
				
	}
	
	
	public String simula(int n, Genes gene) {
		
		Simulazione sim= new Simulazione();
		sim.init(n, gene, grafo, coppie);
		PriorityQueue<Evento> queue= sim.run();
		List<Genes> result= new ArrayList<>();
		String ritorno="Lista geni in corso di studio:\n";
		
		for(Evento e: queue) {
			if(!result.contains(e.getGenes())) {
				result.add(e.getGenes());
				int count=0;
				for(Evento e1: queue) {
					if(e.getGenes().equals(e1.getGenes()))
						count++;
				}
				ritorno+= e.getGenes().getGeneId()+" studiato da "+count+" ingegneri\n";
			}
		}
		
		return ritorno;
		
		
	}
	
	
	
	
}
