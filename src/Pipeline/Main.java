package Pipeline;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
			String db_path = "jars/db/college.odb";
			String owl_file_path = "src/OWL_files/college.owl";
			
			String prefix = "ProtegeGenCode.CollegeProtege.impl.Default";
//			String prefix = "ProtegeGenCode.Root2.impl.Default";
			
			File file = new File(owl_file_path);  
			OWLOntologyManager om = OWLManager.createOWLOntologyManager();
			OWLOntology o = om.loadOntologyFromOntologyDocument(file);
			
			EntityManagerFactory emf = Persistence.createEntityManagerFactory(db_path);
			EntityManager em = emf.createEntityManager();
			
			em.getTransaction().begin();
			
			CollegeProtege root = new CollegeProtege(o);
			Class root_class = CollegeProtege.class;
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
	        
	        System.out.println("----------------------Persisting complete----------------------");
//	        
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
					
					domain = domain.substring(0, 1).toUpperCase() + domain.substring(1);
					range = range.substring(0, 1).toUpperCase() + range.substring(1);
					target_property = target_property.substring(0, 1).toUpperCase() + target_property.substring(1);
					
					em.getTransaction().begin();
//					System.out.println("Query: "+ "SELECT o FROM "+prefix+domain+" o");
					Class domain_class = Class.forName(prefix+domain);
					Class range_class = Class.forName(prefix+range);
					
					
					List<Object> retrieve = em.createQuery("SELECT o FROM "+prefix+domain+" o", domain_class).getResultList();
//					
					for(Object p: retrieve) {
						System.out.println(p.getClass());
//						System.out.println(DefaultProfessor.class);
					    Object p1 = em.find(domain_class, domain_class.getDeclaredMethod("getName").invoke(p));
//					    System.out.println("Object: "+p1);

						Collection<? extends Object> t = DefaultProfessor.class.cast(p).getTeaches();
//					    Collection<? extends Object> t = DefaultPeople.class.cast(p).getHasFather();
				    	for(Object cour: t) {
				    		Object c1 = em.find(range_class, range_class.getDeclaredMethod("getName").invoke(cour));
//				    		System.out.println("Objectt: "+c1);
				    		Class[] cArg = new Class[1];
							cArg[0] = Object.class;
							range_class.getDeclaredMethod("setTaughtBy", cArg).invoke(c1, p1);
//							range_class.getDeclaredMethod("setHasChild", cArg).invoke(c1, p1);
				    	}
					}
					em.getTransaction().commit();
					
				}
			}
//			System.out.println("---------Inverse updates complete-------------------------------------");
//			for(OWLObjectProperty op: owl_obj_props) {
//				Set<OWLTransitiveObjectPropertyAxiom> set_trans = o.getTransitiveObjectPropertyAxioms(op);
//				Set<OWLObjectPropertyDomainAxiom> set_trans_dom = o.getObjectPropertyDomainAxioms(op);
//
//					
//				if(!set_trans.isEmpty() && !set_trans_dom.isEmpty()) {
//					System.out.println("-----------------");
//					System.out.println(set_trans);
//					System.out.println(set_trans_dom);
//					
//					String domain_iri = Iterables.getOnlyElement(set_trans_dom).getDomain().toString();
//					String property_iri = Iterables.getOnlyElement(set_trans_dom).getProperty().toString();
//					
//					String domain = domain_iri.substring(domain_iri.indexOf("#")+"#".length(), domain_iri.length()-1);
//					String property = property_iri.substring(property_iri.indexOf("#")+"#".length(),property_iri.length()-1);
//					property = property.substring(0, 1).toUpperCase() + property.substring(1);
//					domain = domain.substring(0, 1).toUpperCase() + domain.substring(1);
//					
////					String prefix = "ProtegeGenCode.CollegeProtege.impl.Default";
//
//					em.getTransaction().begin();
//					
//					Class domain_class = Class.forName(prefix+domain);
//					List<Object> retrieve = em.createQuery("SELECT o FROM "+prefix+domain+" o", domain_class).getResultList();	
//					Map<Object, HashSet<Object>> relationshipMap = new HashMap<>();
//					for(Object retObject : retrieve) {
//						HashSet<Object> ancestors = (HashSet<Object>) ((DefaultPeople) retObject).getHasAncestor();
//						for(Object ancestor : ancestors) {
//							relationshipMap.put(retObject, ancestors);
//							System.out.println(((DefaultPeople) retObject).getName() + " yoooo " + ((DefaultPeople) ancestor).getName());
//						}
//
//					}
//					
//					for (Map.Entry<Object,HashSet<Object>> relation : relationshipMap.entrySet()) {
//						Object object1 = relation.getKey();
//						HashSet<Object> objects = relation.getValue();
//						for(Object obj : objects) {
//							System.out.println(((DefaultPeople) obj).getName());
//							if(relationshipMap.containsKey(obj)) {
//							for(Object obj2 : relationshipMap.get(obj)) {
//								if(obj2 != null) {
//									objects.add(obj2);
//								}
//							}
//							}
//						}
//					}
//					
//					for(Map.Entry<Object,HashSet<Object>> relation : relationshipMap.entrySet()) {
//						for(Object obj : relation.getValue()) {
//							System.out.println(((DefaultPeople) relation.getKey()).getName() + "yes man" +  ((DefaultPeople) obj).getName());
//							((DefaultPeople) relation.getKey()).setHasAncestor(obj);
//						}
//					}
//					
//					System.out.println(domain_iri);
//					System.out.println(domain);
//					System.out.println(property_iri);
//					System.out.println(property);
//					System.out.println("-----------------");
//					
//					em.getTransaction().commit();
//						
//				}
//			}
			
			System.out.println("---------Transitive updates complete-------------------------------------");

					    
			em.close();
			emf.close();
			
		} catch(Exception e) {
			System.out.println("Exception: "+e.getMessage());
		}
	}

}
