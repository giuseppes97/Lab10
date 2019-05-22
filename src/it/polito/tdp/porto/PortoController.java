package it.polito.tdp.porto;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.porto.db.PortoDAO;
import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.Model;
import it.polito.tdp.porto.model.Paper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class PortoController {
  Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Author> boxPrimo;

    @FXML
    private ComboBox<Author> boxSecondo;

    @FXML
    private TextArea txtResult;

    @FXML
    void handleCoautori(ActionEvent event) {
    
    	model.creaGrafo();
       Author a=boxPrimo.getValue();
       List<Author> aut=new ArrayList<Author>();
       aut=model.geticoautoriselezionato(a);
       for(Author k:aut) {
    	   txtResult.appendText(""+k.getId()+" "+k.getLastname()+" "+k.getFirstname()+"\n");
       }
       boxSecondo.getItems().addAll(model.getautorinoncollegati(a));
       
    }

    @FXML
    void handleSequenza(ActionEvent event) {
    List<Paper> collegamenti=new ArrayList<Paper>();
    collegamenti=model.trovaCamminoMinimo(boxPrimo.getValue(), boxSecondo.getValue());
    txtResult.appendText("\n\nIl cammino e': \n");
    for(Paper p:collegamenti) {
    	txtResult.appendText(""+p+"\n");
    }
    }

    @FXML
    void initialize() {
        assert boxPrimo != null : "fx:id=\"boxPrimo\" was not injected: check your FXML file 'Porto.fxml'.";
        assert boxSecondo != null : "fx:id=\"boxSecondo\" was not injected: check your FXML file 'Porto.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Porto.fxml'.";

    }

	public void setModel(Model m) {
		this.model=m;
		boxPrimo.getItems().addAll(model.gettuttiautori());
		//boxSecondo.getItems().addAll(model.gettuttiautori());
		
	}
}
