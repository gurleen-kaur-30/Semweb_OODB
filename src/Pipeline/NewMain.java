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
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;

import com.google.common.collect.Iterables;

import ProtegeGenCode.CollegeProtege.CollegeProtege;
import ProtegeGenCode.CollegeProtege.*;
import ProtegeGenCode.CollegeProtege.impl.*;

import ProtegeGenCode.Root2.Root2;
import ProtegeGenCode.Root2.*;
import ProtegeGenCode.Root2.impl.*;



public class NewMain {
	public static void main(String[] argv) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		try {
			String db_path = "jars/db/people.odb";
			String owl_file_path = "src/OWL_files/people.owl";
			String prefix = "ProtegeGenCode.Root2.impl.Default";

			
//			String db_path = "jars/db/college1.odb";
//			String owl_file_path = "src/OWL_files/college.owl";
//			String prefix = "ProtegeGenCode.CollegeProtege.impl.Default";
			
			
			File file = new File(owl_file_path);  
			OWLOntologyManager om = OWLManager.createOWLOntologyManager();
			OWLOntology o = om.loadOntologyFromOntologyDocument(file);
			Collection<OWLObjectProperty> owl_obj_props = o.getObjectPropertiesInSignature();
			
			EntityManagerFactory emf = Persistence.createEntityManagerFactory(db_path);
			EntityManager em = emf.createEntityManager();
			
			persistData(o, db_path, em);
//			inverseProperty(o, owl_obj_props, prefix, em);
			transitiveProperty(o, owl_obj_props, prefix, em);
			symmetricProperty(o, owl_obj_props, prefix, em);
			em.close();
			emf.close();
			
		} catch(Exception e) {
			System.out.println("Exception from main: "+e.getMessage());
		}
	}


	public static void persistData(OWLOntology o, String db_path, EntityManager em) {
		try {
			Root2 root = new Root2(o);
			Class root_class = Root2.class;
			
//			CollegeProtege root = new CollegeProtege(o);
//			Class root_class = CollegeProtege.class;
			
			em.getTransaction().begin();
	        Method[] methods = root_class.getMethods();
	        
	        for (int i = 0; i < methods.length; i++) {
	            String methodName = methods[i].getName();
	            if (methodName.length()>=6 && "getAll".equals(methodName.substring(0,6))) {
	                Object returnVal = methods[i].invoke(root);
	                Collection<Object> returnCol = (Collection<Object>) returnVal;
	                if (returnVal instanceof Collection<?>) {
	                	for(Object obj: returnCol) { em.persist(obj); }
	                }
	            }
	        }
	        em.getTransaction().commit();
	        System.out.println("----------------------Persisting complete----------------------");

		}
		catch(Exception e) {
			System.out.println("Exception from presist data: "+e.getMessage());
		}
	}
	
	public static void inverseProperty(OWLOntology o, Collection<OWLObjectProperty> owl_obj_props, String prefix, EntityManager em) {
		try {
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
					Class domain_class = Class.forName(prefix+domain);
					Class range_class = Class.forName(prefix+range);
			
					
					System.out.println("jgdasgdjasj");
					List<Object> retrieve = em.createQuery("SELECT o FROM "+prefix+domain+" o", domain_class).getResultList();
					System.out.println(retrieve);
					
					for(Object p: retrieve) {
					    Object p1 = em.find(domain_class, domain_class.getDeclaredMethod("getName").invoke(p));
	
						Collection<? extends Object> t = DefaultProfessor.class.cast(p).getTeaches();
	//				    Collection<? extends Object> t = DefaultPeople.class.cast(p).getHasFather();
				    	for(Object cour: t) {
				    		Object c1 = em.find(range_class, range_class.getDeclaredMethod("getName").invoke(cour));
				    		System.out.println("Modifying person: "+c1+" using details from "+p1);
				    		Class[] cArg = new Class[1];
							cArg[0] = Object.class;
							range_class.getDeclaredMethod("setTaughtBy", cArg).invoke(c1, p1);
	//						range_class.getDeclaredMethod("setHasChild", cArg).invoke(c1, p1);
	
				    	}
					}
					em.getTransaction().commit();	
			}
		}
		System.out.println("---------Inverse updates complete-------------------------------------");
		}
		catch(Exception e) {
			System.out.println("Exception from inverse: "+e.getMessage());
		}
	}
	
	public static void transitiveProperty(OWLOntology o, Collection<OWLObjectProperty> owl_obj_props, String prefix, EntityManager em) {
		try {
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
					domain = domain.substring(0, 1).toUpperCase() + domain.substring(1);
					
					em.getTransaction().begin();
					
					Class domain_class = Class.forName(prefix+domain);
					List<Object> retrieve = em.createQuery("SELECT o FROM "+prefix+domain+" o", domain_class).getResultList();	
					Map<Object, HashSet<Object>> relationshipMap = new HashMap<>();
					for(Object retObject : retrieve) {
						HashSet<Object> ancestors = (HashSet<Object>) ((DefaultPeople) retObject).getHasAncestor();
						relationshipMap.put(retObject, ancestors);
						for(Object ancestor : ancestors) {
							System.out.println(((DefaultPeople) retObject).getName() + " yoooo " + ((DefaultPeople) ancestor).getName());
						}

					}
					
					
					for (Map.Entry<Object,HashSet<Object>> relation : relationshipMap.entrySet()) {
						Object object1 = relation.getKey();
						HashSet<Object> objects = relation.getValue();
						for(Object obj : objects) {
							System.out.println(((DefaultPeople) obj).getName());
							if(relationshipMap.containsKey(obj)) {
							for(Object obj2 : relationshipMap.get(obj)) {
								if(obj2 != null) {
									objects.add(obj2);
								}
							}
							}
						}
					}
					em.getTransaction().commit();
					em.getTransaction().begin();
			    	int deletedCount = em.createQuery("DELETE FROM DefaultPeople").executeUpdate();
			    	System.out.println("deletes "+deletedCount);
//			    	em.remove(em.contains(p1) ? p1 : em.merge(p1));
			    	em.getTransaction().commit();
			    	em.close();
			    	emf.close();
			    	
			    	EntityManagerFactory emf_new = Persistence.createEntityManagerFactory(db_path);
					EntityManager em_new = emf_new.createEntityManager();
					em_new.getTransaction().begin();
					for(Map.Entry<Object,HashSet<Object>> relation : relationshipMap.entrySet()) {
						System.out.println("yo");
						for(Object obj : relation.getValue()) {
							System.out.println(((DefaultPeople) relation.getKey()).getName() + "yes man" +  ((DefaultPeople) obj).getName());
							((DefaultPeople) relation.getKey()).setHasAncestor(obj);
						}
						em_new.persist(relation.getKey());
					}
					
					System.out.println(domain_iri);
					System.out.println(domain);
					System.out.println(property_iri);
					System.out.println(property);
					System.out.println("-----------------");
					em_new.getTransaction().commit();
					em_new.close();
					emf_new.close();
				}
				
			}
			
			System.out.println("---------Transitive updates complete-------------------------------------");
		}
		catch(Exception e) {
			System.out.println("Exception: "+e.getMessage());
		}
	}
	
	public static void symmetricProperty(OWLOntology o, Collection<OWLObjectProperty> owl_obj_props, String prefix, EntityManager em) {
		try {
			for(OWLObjectProperty op: owl_obj_props) {
				Set<OWLSymmetricObjectPropertyAxiom> set_symm = o.getSymmetricObjectPropertyAxioms(op);
				Set<OWLObjectPropertyDomainAxiom> set_symm_dom = o.getObjectPropertyDomainAxioms(op);
					
				if(!set_symm.isEmpty() && !set_symm_dom.isEmpty()) {
					System.out.println("-----------------");
					System.out.println(set_symm);
					System.out.println(set_symm_dom);
					
					String domain_iri = Iterables.getOnlyElement(set_symm_dom).getDomain().toString();
					String property_iri = Iterables.getOnlyElement(set_symm_dom).getProperty().toString();
					System.out.println(domain_iri);
					System.out.println(property_iri);
					
					String domain = domain_iri.substring(domain_iri.indexOf("#")+"#".length(), domain_iri.length()-1);
					String property = property_iri.substring(property_iri.indexOf("#")+"#".length(),property_iri.length()-1);
					property = property.substring(0, 1).toUpperCase() + property.substring(1);
					domain = domain.substring(0, 1).toUpperCase() + domain.substring(1);
//						
					em.getTransaction().begin();
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
					System.out.println("-----------------");
					
					em.getTransaction().commit();	
				}
			}
		}
		catch(Exception e) {
			System.out.println("Exception from symmetric: "+e.getMessage());
		}
	}
}



