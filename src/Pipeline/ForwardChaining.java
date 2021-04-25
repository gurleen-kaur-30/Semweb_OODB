package Pipeline;

import java.io.File;
import java.util.Collection;

import org.protege.editor.owl.ProtegeOWL;
import org.protege.editor.owl.model.OWLModelManager;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
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
			Collection<OWLObjectProperty> owl_obj_props = o.getObjectPropertiesInSignature();
			OWLReasoner reasoner = reasonerFactory.createReasoner(o);
		
			for(OWLObjectProperty op: owl_obj_props) {
				System.out.println("-------------");
				System.out.println(o.getInverseObjectPropertyAxioms(op));
				System.out.println(o.getObjectPropertyDomainAxioms(op));
				System.out.println(o.getObjectPropertyRangeAxioms(op));
//			Node<OWLObjectPropertyExpression> iop = reasoner.getInverseObjectProperties(op);
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
