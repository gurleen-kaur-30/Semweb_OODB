package Pipeline;

import java.io.File;
import java.lang.reflect.*;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import ProtegeGenCode.CollegeProtege.*;
import ProtegeGenCode.CollegeProtege.impl.*;

public class Main {
	public static void main(String[] argv) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		persist_data("$objectdb/db/college_db.odb", "src/OWL_files/college.owl");
		
	}
	public static void persist_data(String db_path, String owl_file_path)  {
		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory(db_path);
			EntityManager em = emf.createEntityManager();
			File file = new File(owl_file_path);  
			OWLOntologyManager om = OWLManager.createOWLOntologyManager();
			OWLOntology o = om.loadOntologyFromOntologyDocument(file);
			CollegeProtege root = new CollegeProtege(o);
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
	                	for(Object obj: returnCol) {
	                		em.persist(obj);
//	                		System.out.println("MY"+obj.getClass().getConstructor());
//	                		System.out.println(obj.inference);
	                	}
	                	
	                }
	            }
	        }
	        em.getTransaction().commit();
			
//			Collection<? extends College>  list_colleges = root.getAllCollegeInstances();
//			
//			
//			for (College c : list_colleges) {
//				em.persist(c);
//				System.out.println(c);
//			}
//        	
//			Collection<? extends IMTech> list_students = root.getAllIMTechInstances();
//			for (Student s : list_students) {
//				em.persist(s);
//				System.out.println(s.getHasDebt());
//			}
//			em.getTransaction().commit();
//
//			MTech m1 = root.getMTech("http://www.semanticweb.org/prateksha/ontologies/2021/1/college/#Rachna");
//			System.out.println(m1);
			em.close();
			emf.close();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
