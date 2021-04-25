package Pipeline;

import java.io.File;
import java.lang.reflect.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import com.google.common.collect.Iterables;

import ProtegeGenCode.CollegeProtege.*;
import ProtegeGenCode.CollegeProtege.impl.*;

public class PersistData {
	public static void main(String[] argv) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		try {
			String db_path = "jars/db/college_db.odb";
			String owl_file_path = "src/OWL_files/college.owl";
			
			File file = new File(owl_file_path);  
			OWLOntologyManager om = OWLManager.createOWLOntologyManager();
			OWLOntology o = om.loadOntologyFromOntologyDocument(file);
			
			EntityManagerFactory emf = Persistence.createEntityManagerFactory(db_path);
			EntityManager em = emf.createEntityManager();
			persist_data(o, em);
			inverseOfProperty(o, em);
			em.close();
			emf.close();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}		
	}
	public static void persist_data(OWLOntology o , EntityManager em)  {
		try {

			CollegeProtege root = new CollegeProtege(o);
			System.out.println(o.getOntologyID().getOntologyIRI());
			Class c = CollegeProtege.class;
	        Method[] methods = c.getMethods();
	        
	        em.getTransaction().begin();
	        for (int i = 0; i < methods.length; i++) {
	            String methodName = methods[i].getName();
	            if (methodName.length()>=6 && "getAll".equals(methodName.substring(0,6))) {
	            	System.out.println("Printing for "+methodName);
	                Object returnVal = methods[i].invoke(root);
	                Collection<Object> returnCol = (Collection<Object>) returnVal;
	                if (returnVal instanceof Collection<?>) {
	                	for(Object obj: returnCol) { em.persist(obj); }
	                }
	            }
	        }
	        em.getTransaction().commit();
		}
		catch(Exception e) {
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
				
				String prefix = "ProtegeGenCode.CollegeProtege.impl.Default";
				Class[] cArg = new Class[2];
				cArg[0] = String.class;
//				cArg[1] = Class.forName("ProtegeGenCode.CollegeProtege.impl.Default"+domain).getClass();
				cArg[1] = Class.forName("ProtegeGenCode.CollegeProtege.impl.Default"+domain);
				System.out.println(DefaultProfessor.class);
				System.out.println(Class.forName("ProtegeGenCode.CollegeProtege.impl.Default"+domain));
				Method m = em.getClass().getDeclaredMethod("createQuery", cArg);
				System.out.println("HERE1");
				String res = prefix.concat(domain);
				Class c = Class.forName(res);
//				Class c = Class.forName("ProtegeGenCode.CollegeProtege.impl.Default"+domain);
				Object ret = m.invoke(em, "SELECT o FROM "+res+" o", c);
				System.out.println("HERE");
				List<Object> rets = (List<Object>) ret;
                if (rets instanceof List<?>) {
                	for(Object obj: rets) { System.out.println(obj); }
                }
//				System.out.println(Class.forName("Default"+domain));
//				Class c = Class.forName("ProtegeGenCode.CollegeProtege.impl.Default"+domain);
//				TypedQuery<DefaultProfessor> retrieve = em.createQuery("SELECT o FROM "+prefix+domain+" o", c);
//				List<DefaultProfessor> results = retrieve.getResultList();
//			    for (DefaultProfessor p : results) {
//			    	System.out.println(p);
//			    }

//					System.out.println(domain_iri+domain);
//					System.out.println(range_iri);
//					System.out.println(source_property_iri);
//					System.out.println(target_property_iri);
//					Query q1 = em.createQuery("");
//			        System.out.println("Total Points: " + q1.getSingleResult());
				} 
			}
		}
		catch(Exception e) {
			System.out.println("Caught Exception: "+e.getMessage());
		}
		
	}

}
