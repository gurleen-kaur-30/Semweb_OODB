package Pipeline;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;

public class QueryParser {

    private static String[] matchPattern(String str1, Pattern pattern) 
    {
        Matcher m = pattern.matcher(str1);
        ArrayList<String> res = new ArrayList<String>();
        int count = 0;
        
        while(m.find()) {
            res.add(m.group(1));
            // System.out.println(m.group(1));
        }
        
        String delims = "[ .,!]+";
        String[] list = res.get(0).split(delims);

        // for(String t : list)
        //     System.out.println(t);

        return list;
    }

    public static String convertQuery(String s) {
        String[] select;
        String[] where; 
        
        String jpqlQuery = "";
        String jpqlSelect = "";
        String jpqlFrom = "ProtegeGenCode.CollegeProtege.impl.Default";
        String jpqlWhere = "";

        int flag = 0;

        Pattern pattern = Pattern.compile("#(.*?)>");
        jpqlFrom += matchPattern(s, pattern)[0];
        // System.out.println(jpqlFrom);
    
        pattern = Pattern.compile("\\{([^}]+)\\}");
        where = matchPattern(s, pattern);
        where = Arrays.copyOfRange(where, 1, where.length);

        for(String t : where)
            System.out.println(t);
        
        System.out.println("");
        
        pattern = Pattern.compile("[?]");

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

            // System.out.println(t);

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

    public static void main(String[] argv) {
		String s = convertQuery("PREFIX foo: <http://www.semanticweb.org/prateksha/ontologies/2021/1/college#iMTech> SELECT ?subject ?object WHERE { ?subject foo:Id ?object .}");
		
        System.out.println(s);

        s = convertQuery("PREFIX foo: <http://www.semanticweb.org/prateksha/ontologies/2021/1/college#iMTech> SELECT ?subject WHERE { ?subject foo:Id \"42\" .}");
		
        System.out.println(s);

        s = convertQuery("PREFIX foo: <http://www.semanticweb.org/prateksha/ontologies/2021/1/college#iMTech> SELECT ?Id ?age WHERE { ?subject foo:Id ?Id . ?subject foo:age ?age .}");
		
        System.out.println(s);

        s = convertQuery("PREFIX foo: <http://www.semanticweb.org/prateksha/ontologies/2021/1/college#iMTech> SELECT ?Id ?age WHERE { ?subject foo:Id ?Id ; foo:age ?age .}");
		
        System.out.println(s);

	}

}
