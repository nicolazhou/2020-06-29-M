package it.polito.tdp.imdb.model;

public class Adiacente implements Comparable<Adiacente>{

	private Director director;
	private Integer peso;
	
	
	public Adiacente(Director director, Integer peso) {
		super();
		this.director = director;
		this.peso = peso;
	}
	
	public Director getDirector() {
		return director;
	}
	
	
	public Integer getPeso() {
		return peso;
	}

	@Override
	public String toString() {
		return director + " - # attori condivisi: " + peso ;
	}

	@Override
	public int compareTo(Adiacente o) {
		// TODO Auto-generated method stub
		return -this.peso.compareTo(o.getPeso());
	}
	
	
	
	
}
