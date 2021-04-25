package Pipeline;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.protege.editor.owl.ProtegeOWL;
import org.protege.editor.owl.model.OWLModelManager;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLPropertyDomainAxiom;
import org.semanticweb.owlapi.model.parameters.Imports;

import com.google.common.collect.Iterables;

public class ForwardChaining {
	public static void main(String[] argv) {
		try {
			File file = new File("src/OWL_files/college.owl");  
			OWLOntologyManager om = OWLManager.createOWLOntologyManager();
			OWLOntology o = om.loadOntologyFromOntologyDocument(file);
			inverseOfProperty(o);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void inverseOfProperty(OWLOntology o) {
			Collection<OWLObjectProperty> owl_obj_props = o.getObjectPropertiesInSignature();
			for(OWLObjectProperty op: owl_obj_props) {
				Set<OWLInverseObjectPropertiesAxiom> set_inv = o.getInverseObjectPropertyAxioms(op);
				Set<OWLObjectPropertyDomainAxiom> set_inv_dom = o.getObjectPropertyDomainAxioms(op);
				Set<OWLObjectPropertyRangeAxiom> set_inv_range = o.getObjectPropertyRangeAxioms(op);
				
//				Iterator<OWLInverseObjectPropertiesAxiom> iterator = set_inv.iterator();
				if(!set_inv.isEmpty() && !set_inv_dom.isEmpty()) {
					String domain_iri = Iterables.getOnlyElement(set_inv_dom).getDomain().toString();
					String range_iri = Iterables.getOnlyElement(set_inv_range).getRange().toString();
					String source_property_iri = Iterables.getOnlyElement(set_inv_dom).getProperty().toString();
					String target_property_iri = (source_property_iri.equals(Iterables.getOnlyElement(set_inv).getFirstProperty().toString()) ? 
							Iterables.getOnlyElement(set_inv).getSecondProperty().toString() :
								Iterables.getOnlyElement(set_inv).getFirstProperty().toString()	);
					System.out.println("-------------");
					System.out.println(domain_iri);
					System.out.println(range_iri);
					System.out.println(source_property_iri);
					System.out.println(target_property_iri);
				}
			}
	}
	
	public static void transitivityProperty() {
		
	}
	
	public static void symmetricProperty() {
		
	}

}
