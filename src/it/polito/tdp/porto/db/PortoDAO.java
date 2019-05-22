package it.polito.tdp.porto.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.Paper;

public class PortoDAO {

	/*
	 * Dato l'id ottengo l'autore.
	 */
	public Author getAutore(int id) {

		final String sql = "SELECT * FROM author where id=?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, id);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {

				Author autore = new Author(rs.getInt("id"), rs.getString("lastname"), rs.getString("firstname"));
				return autore;
			}

			conn.close();
			return null;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	/*
	 * Dato l'id ottengo l'articolo.
	 */
	public Paper getArticolo(int eprintid) {

		final String sql = "SELECT * FROM paper where eprintid=?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, eprintid);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				Paper paper = new Paper(rs.getInt("eprintid"), rs.getString("title"), rs.getString("issn"),
						rs.getString("publication"), rs.getString("type"), rs.getString("types"));
				return paper;
			}

			return null;

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}
	
	public List<Author> gettuttiautori() {

		final String sql = "SELECT * FROM author";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			List<Author> listaautori=new ArrayList<Author>();

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Author autore = new Author(rs.getInt("id"), rs.getString("lastname"), rs.getString("firstname"));
				listaautori.add(autore);
				
			}

			conn.close();
			return listaautori;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}



	public boolean sonocoautori(Author author, Author author2) {
		
		final String sql = "SELECT * " + 
				"FROM creator AS C, creator AS D " + 
				"WHERE C.eprintid=D.eprintid AND " + 
				"C.authorid<>D.authorid AND C.authorid=? AND D.authorid=?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, author.getId());
			st.setInt(2, author2.getId());
			

			ResultSet rs = st.executeQuery();

			if (rs.next()) {
               conn.close();
				return true;
				
			}

			else {conn.close(); return false;}

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
		
	
	
	}

	public Paper getPaper(Author author, Author author2) {
		final String sql = "SELECT p.eprintid,p.title,p.issn,p.publication,p.type,p.types " + 
				"FROM paper AS p, creator AS c1, creator AS c2 " + 
				"WHERE p.eprintid=c1.eprintid AND c1.eprintid=c2.eprintid AND c1.authorid=?  AND c2.authorid=?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, author.getId());
			st.setInt(2, author2.getId());
			

			ResultSet rs = st.executeQuery();
           Paper p;
			if (rs.next()) {
				p=new Paper(rs.getInt("p.eprintid"),rs.getString("p.title"),rs.getString("p.issn"),rs.getString("p.publication"),rs.getString("p.type"),rs.getString("p.types"));
               conn.close();
				return p;
				
			}

			

		} catch (SQLException e) {
			
			throw new RuntimeException("Errore Db");
		}
		return null;
	}

}