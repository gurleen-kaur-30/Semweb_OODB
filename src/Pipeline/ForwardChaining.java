package Pipeline;

import java.io.File;
import java.util.Collection;

import org.protege.editor.owl.ProtegeOWL;
import org.protege.editor.owl.model.OWLModelManager;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.parameters.Imports;
import org.semanticweb.owlapi.reasoner.Node;
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
//		System.out.println(o.getIndividualsInSignature());
		OWLReasoner reasoner = reasonerFactory.createReasoner(o);
//		System.out.println(reasoner.getRootOntology().getAxioms());
//		System.out.println(o.getDataPropertiesInSignature());
//		System.out.println(o.getObjectPropertiesInSignature());
		for(OWLObjectProperty op: o.getObjectPropertiesInSignature() ) {
			System.out.println(o.getInverseObjectPropertyAxioms(op));
//			Node<OWLObjectPropertyExpression> iop = reasoner.getInverseObjectProperties(op);
		}
		// reasoner.getRootOntology().getInverseFunctionalObjectPropertyAxioms(null);
//		 for(OWLClass oc: owl_classes) {
//            NodeSet<OWLNamedIndividual> instances = reasoner.getInstances(oc, true);
//            System.out.println("The Individuals of my class : " + oc.getIRI());
//        
//            for (OWLNamedIndividual i : instances.getFlattened()) {
//            	System.out.println(i.getDataPropertiesInSignature());
//            	o.getObjectPropertiesInSignature();
//            }
//		 }
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
