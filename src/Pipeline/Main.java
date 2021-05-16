package Pipeline;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;

import com.google.common.collect.Iterables;

import ProtegeGenCode.CollegeProtege.CollegeProtege;
import ProtegeGenCode.CollegeProtege.*;
import ProtegeGenCode.CollegeProtege.impl.*;

import ProtegeGenCode.Root2.Root2;
import ProtegeGenCode.Root2.*;
import ProtegeGenCode.Root2.impl.*;


public class Main {
	public static void main(String[] argv) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		try {
			String db_path = "jars/db/people.odb";
			String owl_file_path = "src/OWL_files/people.owl";
			
			File file = new File(owl_file_path);  
			OWLOntologyManager om = OWLManager.createOWLOntologyManager();
			OWLOntology o = om.loadOntologyFromOntologyDocument(file);
			
			EntityManagerFactory emf = Persistence.createEntityManagerFactory(db_path);
			EntityManager em = emf.createEntityManager();
			
			em.getTransaction().begin();
			
			Root2 root = new Root2(o);
			Class root_class = Root2.class;
	        Method[] methods = root_class.getMethods();
	        
	        for (int i = 0; i < methods.length; i++) {
	            String methodName = methods[i].getName();
	            if (methodName.length()>=6 && "getAll".equals(methodName.substring(0,6))) {
//	            	System.out.println("Printing for "+methodName);
	                Object returnVal = methods[i].invoke(root);
	                Collection<Object> returnCol = (Collection<Object>) returnVal;
	                if (returnVal instanceof Collection<?>) {
	                	for(Object obj: returnCol) { em.persist(obj); }
	                }
	            }
	        }
	        
	        em.getTransaction().commit();
	        
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
					
					String prefix = "ProtegeGenCode.CollegeProtege.impl.Default";


					em.getTransaction().begin();
					
					Class domain_class = Class.forName("ProtegeGenCode.CollegeProtege.impl.Default"+domain);
					Class range_class = Class.forName("ProtegeGenCode.CollegeProtege.impl.Default"+range);
					
					List<Object> retrieve = em.createQuery("SELECT o FROM "+prefix+domain+" o", domain_class).getResultList();
//					
					for(Object p: retrieve) {
//						System.out.println(p.getClass());
//						System.out.println(DefaultProfessor.class);
					    Object p1 = em.find(domain_class, domain_class.getDeclaredMethod("getName").invoke(p));
//					    System.out.println("Object: "+p1);

						Collection<? extends Object> t = DefaultProfessor.class.cast(p).getTeaches();
				    	for(Object cour: t) {
				    		Object c1 = em.find(range_class, range_class.getDeclaredMethod("getName").invoke(cour));
//				    		System.out.println("Objectt: "+c1);
				    		Class[] cArg = new Class[1];
							cArg[0] = Object.class;
							range_class.getDeclaredMethod("setTaughtBy", cArg).invoke(c1, p1);
				    	}
					}
					em.getTransaction().commit();
					
				}
			}
			for(OWLObjectProperty op: owl_obj_props) {
				Set<OWLTransitiveObjectPropertyAxiom> set_trans = o.getTransitiveObjectPropertyAxioms(op);
				Set<OWLObjectPropertyDomainAxiom> set_trans_dom = o.getObjectPropertyDomainAxioms(op);

					
				if(!set_trans.isEmpty() && !set_trans_dom.isEmpty()) {
					System.out.println("-----------------");
					System.out.println(set_trans);
					System.out.println(set_trans_dom);
					
					String domain_iri = Iterables.getOnlyElement(set_trans_dom).getDomain().toString();
					String property_iri = Iterables.getOnlyElement(set_trans_dom).getProperty().toString();
					
					String domain = domain_iri.substring(domain_iri.indexOf("#")+"#".length(), domain_iri.length()-1);
					String property = property_iri.substring(property_iri.indexOf("#")+"#".length(),property_iri.length()-1);
					property = property.substring(0, 1).toUpperCase() + property.substring(1);
					
					String prefix = "ProtegeGenCode.CollegeProtege.impl.Default";

					em.getTransaction().begin();
					
					Class domain_class = Class.forName("ProtegeGenCode.CollegeProtege.impl.Default"+domain);
					
					List<Object> retrieve = em.createQuery("SELECT o FROM "+prefix+domain+" o", domain_class).getResultList();					
					
					System.out.println(domain_iri);
					System.out.println(domain);
					System.out.println(property_iri);
					System.out.println(property);
					System.out.println("-----------------");
					
					em.getTransaction().commit();
						
				}
			}
					    
			em.close();
			emf.close();
			
		} catch(Exception e) {
			System.out.println("Exception: "+e.getMessage());
		}
	}

}
