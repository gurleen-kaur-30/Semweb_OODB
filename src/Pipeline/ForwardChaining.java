package Pipeline;

import org.protege.editor.owl.ProtegeOWL;
import org.protege.editor.owl.model.OWLModelManager;

public class ForwardChaining {
	
	public static void main(String[] argv) {
		String ontologyURI = "http://www.semanticweb.org/prateksha/ontologies/2021/1/college";
	}
	public ProtegeReasoner createPelletOWLAPIReasoner(OWLModel owlModel) {
		// Get the reasoner manager instance 
		ReasonerManager reasonerManager = ReasonerManager.getInstance(); 
		//Get an instance of the Protege Pellet reasoner 
		ProtegeReasoner reasoner = reasonerManager.createProtegeReasoner(owlModel, ProtegePelletOWLAPIReasoner.class); return reasoner; }
	}
	
	public static void inverseOfProperty() {
		
	}
	
	public static void transitivityProperty() {
		
	}
	
	public static void symmetricProperty() {
		
	}

}
