package Pipeline;

import java.lang.reflect.Method;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import ProtegeGenCode.CollegeProtege.impl.*;

public class QueryClass {
	public static void main(String[] args) {
        // Open a database connection
        // (create a new database if it doesn't exist yet):
		try {
		String db_path = "jars/db/college_db.odb";
		Method[] m = EntityManager.class.getDeclaredMethods();
		for(Method i: m) {
			System.out.println(i);
		}
		
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(db_path);
        EntityManager em = emf.createEntityManager();
        CriteriaQuery<?> criteriaQuery= em.getCriteriaBuilder().createQuery(Class.forName("ProtegeGenCode.CollegeProtege.impl.DefaultProfessor"));
//        Method m = em.getClass().getDeclaredMethod("createQuery", criteriaQuery);

//        List l = em.createQuery("SELECT e FROM DefaultProfessor d JOIN d.Teaches e ").getResultList();
//        System.out.println(l);
//        
//        TypedQuery<DefaultProfessor> q1 = em.createQuery("SELECT s.Teaches FROM  ProtegeGenCode.CollegeProtege.impl.DefaultProfessor s", DefaultProfessor.class);
//		System.out.println("Retrieved: "+ q1.getResultList());
//		
//		em.close();
//		emf.close();
		}
		catch(Exception e) {
			System.out.println("Caught exception: "+e.getMessage());
		}
	}
}
