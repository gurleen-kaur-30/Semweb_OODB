package Pipeline;

import Pipeline.RunQuery;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.*;

public class QueryParser {

    private static String[] matchPattern(String str1, Pattern pattern) 
    {
        Matcher m = pattern.matcher(str1);
        ArrayList<String> res = new ArrayList<String>();
        int count = 0;
        
        while(m.find()) {
            res.add(m.group(1));
        }
        
        String delims = "[ .,!]+";
        String[] list = res.get(0).split(delims);

        return list;
    }

    private static Map<String,String> createDict(String str1, Pattern pattern)
    {
        Matcher m = pattern.matcher(str1);
        ArrayList<String> res = new ArrayList<String>();
        Map<String, String> params = new HashMap<String, String>();
        int count = 0;
        
        while(m.find()) {
            res.add(m.group(1));
        }
        
        for(int i=0;i<res.size();i++)
        {
            String temp = res.get(i).split("<")[1];
            Pattern pattern1 = Pattern.compile("\\#$");

            if(pattern1.matcher(temp).find())
            {  continue;  }
            else{
                params.put(res.get(i).split("<")[0].replaceAll("\\s", ""),temp.split("#")[1]);
            }
            
        }
        return params;
    }

    public static String convertQuery(String s) {
        String[] select;
        String[] where; 
        
        String jpqlQuery = "";
        String jpqlSelect = "";
        String jpqlFrom = "Default";
        String jpqlWhere = "";

        int flag = 0;

        Pattern pattern = Pattern.compile("#(.*?)>");

        String temp1 = matchPattern(s, pattern)[0];
        temp1 = temp1.substring(0, 1).toUpperCase() + temp1.substring(1);
        jpqlFrom += temp1;
    
        pattern = Pattern.compile("\\{([^}]+)\\}");
        where = matchPattern(s, pattern);
        where = Arrays.copyOfRange(where, 1, where.length);
        pattern = Pattern.compile("[:?]");

        if(!pattern.matcher(where[2]).find()) {
            pattern = Pattern.compile("[:]");
            if(pattern.matcher(where[1]).find()) {
                jpqlWhere = " where s." + where[1].split(":")[1] + " = " + where[2];
            }
        }

        pattern = Pattern.compile("SELECT(.+?)WHERE");
        select = matchPattern(s, pattern);
        select = Arrays.copyOfRange(select, 1, select.length);

        int i = 0;

        for(String t : select) {
            if(t.equals("*"))
                jpqlSelect += t;
            else if(t.equals(where[0]))
                jpqlSelect += "s.name";
            else if(t.equals(where[i])){
                String[] temp = where[1].split("[:]");
                jpqlSelect += "s." + temp[1]; 
            } 
            else if(Pattern.compile("[?]").matcher(t).find()){
                jpqlSelect += "s." + t.substring(1) + " ";
            }
            else
                jpqlSelect += "s." + t + " ";
            jpqlSelect += " ";
            i += 2;
            
        }

        if(jpqlSelect.equals("*"))
            jpqlQuery = "select " + jpqlSelect + "from " + jpqlFrom;
        else
            jpqlQuery = "select " + jpqlSelect + "from " + jpqlFrom + " s" + jpqlWhere;
        
        return jpqlQuery;
    }

    public static String convertQuery2(String s) {
        String[] select;
        String[] where; 
        String[] prefixfirst;
        String[] prefixsecond;
        
        String jpqlQuery = "";
        String jpqlSelect = "";
        String jpqlFrom = "Default";
        String jpqlWhere = "";
        String afterJoin = "";

        int flag = 0;

        Map<String,String> prefixs = new HashMap<String, String>();

        Pattern pattern = Pattern.compile("PREFIX(.*?)>");

        prefixs = createDict(s, pattern);
        pattern = Pattern.compile("\\{([^}]+)\\}");
        where = matchPattern(s, pattern);
        where = Arrays.copyOfRange(where, 1, where.length);
        
        for(int i=0;i<where.length;i++)
        {            
            if(where[i].equals("rdf:type"))
            {
                String end = prefixs.get(where[i+1]);
                jpqlFrom += end.substring(0,1).toUpperCase() + end.substring(1);
            }
        }
        
        if(where.length > 6)
        {
            jpqlWhere += " Where s ";
            jpqlWhere += where[5];
            jpqlWhere += " ";
            jpqlWhere += where[6];
            jpqlWhere =  jpqlWhere.substring(0,jpqlWhere.length()-1);
        }

        
        pattern = Pattern.compile("[:?]");

        if(!pattern.matcher(where[2]).find()) {
            pattern = Pattern.compile("[:]");
            if(pattern.matcher(where[1]).find()) {
                jpqlWhere = " where s." + where[1].split(":")[1] + " = " + where[2];
            }
        }

        pattern = Pattern.compile("SELECT(.+?)WHERE");
        select = matchPattern(s, pattern);
        select = Arrays.copyOfRange(select, 1, select.length);

        int i = 0;

        for(String t : select) {
            if(t.equals("*"))
                jpqlSelect += t;
            else if(t.equals(where[0]))
                jpqlSelect += "i.name,";
            else if(t.equals(where[i])){
                String[] temp = where[1].split("[:]");
                afterJoin += "i." + temp[1].substring(0,1).toUpperCase() + temp[1].substring(1); 
            } 
            else if(Pattern.compile("[?]").matcher(t).find()){
                jpqlSelect += "s." + t.substring(1) + " ";
            }
            else
                jpqlSelect += "s." + t + " ";

            jpqlSelect += " ";
            i += 2;
            
        }

        if(jpqlSelect.equals("*"))
            jpqlQuery = "select " + jpqlSelect + "from " + jpqlFrom;
        else
            jpqlQuery = "select " + jpqlSelect + "s from " + jpqlFrom + " i JOIN " + afterJoin + " s" + jpqlWhere;
        
        return jpqlQuery;
    }



    public static void main(String[] argv) {
    	String query = "PREFIX foo: <http://www.semanticweb.org/prateksha/ontologies/2021/1/college#> PREFIX foo1: <http://www.semanticweb.org/prateksha/ontologies/2021/1/college#iMTech> SELECT ?subject ?object WHERE{ ?subject foo:hasRollNumber ?object . ?subject rdf:type foo1: .}";
		int type = 2;
		
        String db_path = "jars/db/college.odb";
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(db_path);
        EntityManager em = emf.createEntityManager();
        RunQuery rq = new RunQuery();
        
    	if(type == 1) {
    		String s_gen = convertQuery(query);
    		System.out.println(s_gen);
    		rq.runQuery(type, s_gen, em);
    	}
    	else {
    		String s_gen = convertQuery2(query);
    		System.out.println(s_gen);
    		rq.runQuery(type, s_gen, em);
    	}
    	
    	em.close();
    	emf.close();
    	// String s = convertQuery("PREFIX foo: <http://www.semanticweb.org/prateksha/ontologies/2021/1/college#iMTech> SELECT ?subject ?object WHERE { ?subject foo:Id ?object .}");		
        // System.out.println(s);

        // s = convertQuery("PREFIX foo: <http://www.semanticweb.org/prateksha/ontologies/2021/1/college#iMTech> SELECT ?subject WHERE { ?subject foo:Id \"42\" .}");		
        // System.out.println(s);

        // s = convertQuery("PREFIX foo: <http://www.semanticweb.org/prateksha/ontologies/2021/1/college#iMTech> SELECT ?Id ?age WHERE { ?subject foo:Id ?Id . ?subject foo:age ?age .}");		
        // System.out.println(s);

        // s = convertQuery("PREFIX foo: <http://www.semanticweb.org/prateksha/ontologies/2021/1/college#iMTech> SELECT ?Id ?age WHERE { ?subject foo:Id ?Id ; foo:age ?age .}");
        // System.out.println(s);

//        String s = convertQuery("PREFIX foo: <http://www.semanticweb.org/prateksha/ontologies/2021/1/college#iMTech> SELECT ?subject WHERE { ?subject rdf:type foo:} ");
//        System.out.println(s);
//
//        s = convertQuery2("PREFIX foo: <http://www.semanticweb.org/prateksha/ontologies/2021/1/college#> PREFIX foo1: <http://www.semanticweb.org/prateksha/ontologies/2021/1/college#iMTech> SELECT ?subject ?object WHERE{ ?subject foo:hasRollNumber ?object . ?subject rdf:type foo1: .}");
//        System.out.println(s);
//
//        s = convertQuery2("PREFIX foo: <http://www.semanticweb.org/prateksha/ontologies/2021/1/college#> PREFIX foo1: <http://www.semanticweb.org/prateksha/ontologies/2021/1/college#Professor> SELECT ?subject ?object WHERE{ ?subject foo:teaches ?object . ?subject rdf:type foo1: .}");
//        System.out.println(s);
//
//        s = convertQuery2("PREFIX foo: <http://www.semanticweb.org/prateksha/ontologies/2021/1/college#> PREFIX foo1: <http://www.semanticweb.org/prateksha/ontologies/2021/1/college#iMTech> SELECT ?subject ?object WHERE{ ?subject foo:hasDebt ?object . FILTER (?object < 3000) ?subject rdf:type foo1: .}");
//        System.out.println(s);


	}

}
