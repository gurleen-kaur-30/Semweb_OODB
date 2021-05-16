package Pipeline;

import java.io.File;
import java.util.Collection;
import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import com.google.common.collect.Iterables;

public class StaticMapping {
	public static void main(String[] argv) {
		try {
			File file = new File("src/OWL_files/people.owl");  
			String project_name = "Root2";
			OWLOntologyManager om = OWLManager.createOWLOntologyManager();
			OWLOntology o = om.loadOntologyFromOntologyDocument(file);
			
//			ProcessBuilder gen_pb = new ProcessBuilder("automation_script.sh", project_name);
//			gen_pb.start();
			
			Collection<OWLObjectProperty> owl_obj_props = o.getObjectPropertiesInSignature();
			for(OWLObjectProperty op: owl_obj_props) {
					Set<OWLInverseObjectPropertiesAxiom> set_inv = o.getInverseObjectPropertyAxioms(op);
					Set<OWLObjectPropertyDomainAxiom> set_inv_dom = o.getObjectPropertyDomainAxioms(op);
					Set<OWLObjectPropertyRangeAxiom> set_inv_range = o.getObjectPropertyRangeAxioms(op);
					
					if(!set_inv.isEmpty() && !set_inv_dom.isEmpty()) {
						String domain_iri = Iterables.getOnlyElement(set_inv_dom).getDomain().toString();
						String range_iri = Iterables.getOnlyElement(set_inv_range).getRange().toString();
						String source_property_iri = Iterables.getOnlyElement(set_inv_dom).getProperty().toString();
						String target_property_iri = (source_property_iri.equals(Iterables.getOnlyElement(set_inv).getFirstProperty().toString()) ? 
								Iterables.getOnlyElement(set_inv).getSecondProperty().toString() :
									Iterables.getOnlyElement(set_inv).getFirstProperty().toString()	);
						
						String domain = domain_iri.substring(domain_iri.indexOf("#")+"#".length(), domain_iri.length()-1);
						String range = range_iri.substring(range_iri.indexOf("#")+"#".length(), range_iri.length()-1);
						String target_property = target_property_iri.substring(target_property_iri.indexOf("#")+"#".length(),target_property_iri.length()-1);
						target_property = target_property.substring(0, 1).toUpperCase() + target_property.substring(1);
						
						ProcessBuilder inv_pb = new ProcessBuilder("inverse.sh", range, domain, target_property);
						inv_pb.start();
					}
				}
		}
		catch(Exception e) {
			
		}
	}
	
}
