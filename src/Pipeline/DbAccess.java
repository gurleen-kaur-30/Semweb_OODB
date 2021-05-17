package Pipeline;

import java.io.File;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import ProtegeGenCode.CollegeProtege.impl.DefaultCourse;
import ProtegeGenCode.CollegeProtege.impl.DefaultProfessor;
import ProtegeGenCode.CollegeProtege.impl.DefaultStudent;

public class DbAccess {
	public static void main(String[] argv) {
		try {
		String db_path = "jars/db/college_db.odb";
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(db_path);
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		DefaultProfessor p = em.find(DefaultProfessor.class, 1);
		DefaultCourse c = em.find(DefaultCourse.class, 1);
		c.setTaughtBy(p);
		em.getTransaction().commit();
		System.out.println("yo1");
		 em.close();
		 emf.close();
		} catch(Exception e) {
			System.out.println("yo");
			System.out.println(e.getMessage());
		}
	}
}
