package Pipeline;

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
	        em.getTransaction().begin();
	        inverseOfProperty(o, em);
	        em.close();
	        emf.close();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void inverseOfProperty(OWLOntology o, EntityManager em) {
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
					
					
					String prefix = "ProtegeGenCode.CollegeProtege.impl.Default";
					TypedQuery<DefaultProfessor> retrieve = em.createQuery("SELECT o FROM ProtegeGenCode.CollegeProtege.impl.DefaultProfessor o", DefaultProfessor.class);
					List<DefaultProfessor> results = retrieve.getResultList();
			        for (DefaultProfessor p : results) {
			            System.out.println(p);
			        }
//					
//					TypedQuery<DefaultCourse> retreive = em.createQuery("select p FROM ProtegeGenCode.CollegeProtege.impl.DefaultProfessor c JOIN c.Teaches p", DefaultCourse.class);
//					List<DefaultCourse> df = retreive.getResultList();
//					System.out.println("1.-------------");
//					for(DefaultCourse prof: df) {
//						System.out.println("my"+prof.toString());
//					}
//					System.out.println("2.-------------");
					em.getTransaction().commit();
//					System.out.println("-------------");
					System.out.println(domain_iri);
					System.out.println(range_iri);
					System.out.println(source_property_iri);
					System.out.println(target_property_iri);
//					Query q1 = em.createQuery("");
//			        System.out.println("Total Points: " + q1.getSingleResult());
				}
			}
	}
	
	public static void transitivityProperty() {
		
	}
	
	public static void symmetricProperty() {
		
	}

}
