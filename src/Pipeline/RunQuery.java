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

public class RunQuery {
	public static void main(String[] args) {
		try {
		String db_path = "jars/db/college_db.odb";
        String query = "SELECT i.name, s  FROM  ProtegeGenCode.CollegeProtege.impl.DefaultIMTech i JOIN i.HasDebt s";
        int queryType = 3;
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(db_path);
        EntityManager em = emf.createEntityManager();
        
        
        if(queryType == 1) {
        	System.out.println(em.createQuery(query).getResultList());
        }

        else if(queryType == 2 || queryType == 3 || queryType == 4) {
            List<Object[]> queryResults = em.createQuery(query).getResultList();
            for (Object[] ele: queryResults) {
            	System.out.println("--------------------------------------------------------------------------------");
           	    System.out.println(ele[0]);
           	    System.out.println(ele[1]);
           	}
        }

		em.close();
		emf.close();
		}
		catch(Exception e) {
			System.out.println("Caught exception: "+e.getMessage());
		}
	}
}
