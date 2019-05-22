package it.polito.tdp.porto.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.event.ConnectedComponentTraversalEvent;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.event.VertexTraversalEvent;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.porto.db.PortoDAO;

public class Model {
	private class EdgeTraversedGraphListener implements TraversalListener<Author, DefaultEdge> {

		@Override
		public void connectedComponentFinished(ConnectedComponentTraversalEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void connectedComponentStarted(ConnectedComponentTraversalEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void edgeTraversed(EdgeTraversalEvent<DefaultEdge> arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void vertexFinished(VertexTraversalEvent<Author> arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void vertexTraversed(VertexTraversalEvent<Author> arg0) {
			// TODO Auto-generated method stub
			
		}

	
		
	}
	
PortoDAO dao;
List<Author> tuttiautori;
List<Author> cammino;
Graph<Author,DefaultEdge> grafo;

	
public Model() {
	
	this.dao = new PortoDAO();
	this.tuttiautori = new ArrayList<Author>();
	this.grafo = new SimpleGraph<>(DefaultEdge.class);
	cammino=new ArrayList<Author>();
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
public List<Paper> trovaCamminoMinimo(Author partenza, Author arrivo) {
	DijkstraShortestPath<Author, DefaultEdge> dijstra = new DijkstraShortestPath<>(this.grafo) ;
	GraphPath<Author, DefaultEdge> path = dijstra.getPath(partenza, arrivo) ;
	cammino=path.getVertexList();
	List<Paper> listaart=new ArrayList<Paper>();
	for(int i=0;i<cammino.size();i++)
	{ if((i+1)==cammino.size()) {}
	else {
		Paper p=dao.getPaper(cammino.get(i),cammino.get(i+1));
		listaart.add(p);
	}
		
	}
	
	
	
	return listaart ;
}

}
