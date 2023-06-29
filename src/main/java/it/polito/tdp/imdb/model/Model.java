package it.polito.tdp.imdb.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.imdb.db.ImdbDAO;

public class Model {
	
	private ImdbDAO dao;
	
	//private List<Integer> lista;
	
	private Graph<Director, DefaultWeightedEdge> grafo;
	
	private List<Director> vertici;
	private Map<Integer, Director> directorIdMap;
	
	
	// Ricorsione
	private List<Director> soluzione;
	
	
	public Model() {
		
		this.dao = new ImdbDAO();
		
		//this.lista = new ArrayList<>();
		
	}
	
	
	public void creaGrafo(int anno) {
		
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

		
		this.vertici = new ArrayList<>(this.dao.getVertici(anno));
		
		this.directorIdMap = new HashMap<>();
		
		for(Director d : this.vertici) {
			
			this.directorIdMap.put(d.getId(), d);
			
		}
		
		// Vertici:
		Graphs.addAllVertices(this.grafo, this.vertici);

		
		
		// Archi
		for(Arco arco : this.dao.getArchi(anno, directorIdMap)) {
			
			
			Graphs.addEdgeWithVertices(this.grafo, arco.getDirector1(), arco.getDirector2(), arco.getPeso());
			
			
		}
		
		
	}
	
	
	public int getNNodes() {
		return this.grafo.vertexSet().size();
	}
	
	public int getNArchi() {
		return this.grafo.edgeSet().size();
	}
	
	
	public boolean isGrafoLoaded() {
		
		if(this.grafo == null)
			return false;
		
		return true;
	}


	public List<Director> getVertici() {
		return vertici;
	}
	
	
	public List<Adiacente> getAdiacenti(Director director) {
		
		List<Adiacente> result = new ArrayList<>();
		
		for(Director d : Graphs.neighborListOf(this.grafo, director)) {
			
			DefaultWeightedEdge e = this.grafo.getEdge(director, d);
			
			int peso = (int) this.grafo.getEdgeWeight(e);
			
			Adiacente ad = new Adiacente(d, peso);
			
			result.add(ad);
			
		}
		
		Collections.sort(result);
		
		return result;
	}
	
	
	
	public List<Director> trovaCammino(Director partenza, Integer c) {
		
		// Inizializzazione
		List<Director> parziale = new ArrayList<>();
		this.soluzione = new ArrayList<>();
		
		
		parziale.add(partenza);
		
		cerca(parziale, c);
		
		
		System.out.println("Soluzione: " + soluzione);
		
		return this.soluzione;
	}


	private void cerca(List<Director> parziale, Integer c) {
		// TODO Auto-generated method stub
		
		// Condizione di terminazione
		if(parziale.size() > this.soluzione.size()) {
			
			this.soluzione = new ArrayList<>(parziale);
			
		}
		
		
		Director ultimo = parziale.get(parziale.size()-1);
		List<Director> vicini = Graphs.neighborListOf(this.grafo, ultimo);
		
		for(Director d : vicini) {
			
			if(!parziale.contains(d)) {
				
				DefaultWeightedEdge e = this.grafo.getEdge(ultimo, d);
				
				int peso = (int) this.grafo.getEdgeWeight(e);
				
				parziale.add(d);
				
				
				if(sommaPesi(parziale) + peso <= c) {
					
					cerca(parziale, c);
					
				}
				
				
				parziale.remove(parziale.size()-1);
				
			}
			
		}
		
		
	}


	private int sommaPesi(List<Director> parziale) {
		// TODO Auto-generated method stub
		
		int somma = 0;
		
		for(int i = 0; i < parziale.size()-1; i++) {
			
			DefaultWeightedEdge e = this.grafo.getEdge(parziale.get(i), parziale.get(i+1));
			
			int peso = (int) this.grafo.getEdgeWeight(e);
			
			somma += peso;
			
		}
		
		
		return somma;
	}
	
	
	
	
}
