package Pipeline;

import ProtegeGenCode.CollegeProtege.*;
import ProtegeGenCode.CollegeProtege.impl.*;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

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
	        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jars/db/college_db.odb");
	        EntityManager em = emf.createEntityManager();
	        
	        inverseOfProperty(o, em);
	        
	        em.close();
	        emf.close();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void inverseOfProperty(OWLOntology o, EntityManager em) {
		try {
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
					
//					System.out.println(domain_iri);
//					System.out.println(range_iri);
//					System.out.println(source_property_iri);
//					System.out.println(target_property_iri);
					
					String prefix = "ProtegeGenCode.CollegeProtege.impl.Default";

					em.getTransaction().begin();
					Class c = Class.forName("ProtegeGenCode.CollegeProtege.impl.Default"+domain);
					TypedQuery<DefaultProfessor> retrieve = em.createQuery("SELECT o FROM "+prefix+domain+" o", c);
					for(DefaultProfessor p: retrieve.getResultList()) {
					    DefaultProfessor p1 = em.find(DefaultProfessor.class, p.getName());

						Collection<? extends Course> t = p.getTeaches();
				    	Collection<DefaultCourse> t_new = (Collection<DefaultCourse>) t;
				    	for(DefaultCourse cour: t_new) {
				    		DefaultCourse c1 = em.find(DefaultCourse.class, cour.getName());
							c1.setTaughtBy(p1);
				    	}
//						DefaultCourse c = em.find(DefaultCourse.class, )
					}
					em.getTransaction().commit();
				}
			}
		}
		catch(Exception e) {
			System.out.println("Exception: "+e.getMessage());
		}
	}
	
	public static void transitivityProperty() {
		
	}
	
	public static void symmetricProperty() {
		
	}

}
