package it.polito.tdp.imdb.model;

public class Arco {
	
	
	private Director director1;
	private Director director2;
	
	private int peso;

	public Arco(Director director1, Director director2, int peso) {
		super();
		this.director1 = director1;
		this.director2 = director2;
		this.peso = peso;
	}

	public Director getDirector1() {
		return director1;
	}

	public void setDirector1(Director director1) {
		this.director1 = director1;
	}

	public Director getDirector2() {
		return director2;
	}

	public void setDirector2(Director director2) {
		this.director2 = director2;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}
	
	

}
