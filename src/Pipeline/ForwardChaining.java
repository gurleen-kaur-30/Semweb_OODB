package Pipeline;

import org.protege.editor.owl.ProtegeOWL;
import org.protege.editor.owl.model.OWLModelManager;

public class ForwardChaining {
	
	public static void main(String[] argv) {
		String ontologyURI = "http://www.semanticweb.org/prateksha/ontologies/2021/1/college";
		OWLModelManager owlModel = ProtegeOWL.createJenaOWLModelFromURI(ontologyURI);
	}
	
	public static void inverseOfProperty() {
		
	}
	
	public static void transitivityProperty() {
		
	}
	
	public static void symmetricProperty() {
		
	}

}
