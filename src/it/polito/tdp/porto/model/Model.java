package it.polito.tdp.porto.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.porto.db.PortoDAO;

public class Model {
PortoDAO dao;
List<Author> tuttiautori;
Graph<Author,DefaultEdge> grafo;

	
public Model() {
	
	this.dao = new PortoDAO();
	this.tuttiautori = new ArrayList<Author>();
	this.grafo = new SimpleGraph<>(DefaultEdge.class);
}


public List<Author> gettuttiautori() {
		tuttiautori=dao.gettuttiautori();
		return tuttiautori;
	}


public void creaGrafo() {
	Graphs.addAllVertices(grafo, tuttiautori);
	for(int a=0;a<tuttiautori.size();a++) {
		for(int k=a+1;k<tuttiautori.size();k++)
		{
			if(dao.sonocoautori(tuttiautori.get(a),tuttiautori.get(k))) {
				grafo.addEdge(tuttiautori.get(a), tuttiautori.get(k));
			}
		}
		
	}
	System.out.println(""+grafo.vertexSet().size()+" "+grafo.edgeSet().size());
	
}


public List<Author> geticoautoriselezionato(Author a) {
     List<Author> autoricollegati=new ArrayList<Author>();
	autoricollegati=Graphs.neighborListOf(grafo, a);
			return autoricollegati;
}


public List<Author> getautorinoncollegati(Author a) {
	List<Author> collegati=new ArrayList<Author>();
	collegati=Graphs.neighborListOf(grafo, a);
	List<Author> autoririmasti=new ArrayList<Author>(tuttiautori);
	for(Author aut:collegati) {
		if(autoririmasti.contains(aut)) {autoririmasti.remove(aut);}
	}
	autoririmasti.remove(a);
	return autoririmasti;
}

}
