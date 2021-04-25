package Pipeline;

import java.io.File;
import java.util.Collection;

import org.protege.editor.owl.ProtegeOWL;
import org.protege.editor.owl.model.OWLModelManager;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;

public class ForwardChaining {
	 static OWLReasonerFactory reasonerFactory = new StructuralReasonerFactory();
	public static void main(String[] argv) {
		try {
		File file = new File("src/OWL_files/college.owl");  
		OWLOntologyManager om = OWLManager.createOWLOntologyManager();
		OWLOntology o = om.loadOntologyFromOntologyDocument(file);
		Collection<OWLClass> owl_classes = o.getClassesInSignature();
		System.out.println(o.getIndividualsInSignature());
		for(OWLClass oc: owl_classes) {
			OWLReasoner reasoner = reasonerFactory.createReasoner(o);
//            NodeSet<OWLNamedIndividual> instances = reasoner.getInstances(oc, true);
//            System.out.println("The Individuals of my class : " + oc.getIRI());
//        
//            for (OWLNamedIndividual i : instances.getFlattened()) {
//            	System.out.println(i.getDataPropertiesInSignature());
//            	o.getObjectPropertiesInSignature();
//            }
		}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void inverseOfProperty() {
		
	}
	
	public static void transitivityProperty() {
		
	}
	
	public static void symmetricProperty() {
		
	}

}
