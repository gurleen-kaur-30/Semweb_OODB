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
        
        String delims = "[ .,?!]+";
        String[] list = res.get(0).split(delims);

        // for(String t : list)
        //     System.out.println(t);

        return list;
    }

    public static String convertQuery(String s) {
        String jpqlQuery = "";
        String[] select;
        String[] where; 
        String jpqlFrom = "ProtegeGenCode.CollegeProtege.impl.Default";

        Pattern pattern = Pattern.compile("#(.*?)>");
        jpqlFrom += matchPattern(s, pattern)[0];
        // System.out.println(jpqlFrom);
    
        pattern = Pattern.compile("\\{([^}]+)\\}");
        where = matchPattern(s, pattern);

        for(String t : where)
            System.out.println(t);
        
        System.out.println("");

        pattern = Pattern.compile("SELECT(.+?)WHERE");
        select = matchPattern(s, pattern);
        select = Arrays.copyOfRange(select, 1, select.length);

        String jpqlSelect = "";

        for(String t : select) {

            // System.out.println(t);

            if(t.equals("*"))
                jpqlSelect += t;
            else if(t.equals(where[1]))
                jpqlSelect += "s.name" + " ";
            else if(t.equals(where[3])){
                String[] temp = where[2].split("[:]");
                jpqlSelect += "s." + temp[1]; 
            } 
            else if(Pattern.compile("[?]").matcher(t).find()){
                jpqlSelect += "s." + t.substring(1) + " ";
            }
            else
                jpqlSelect += "s." + t + " ";
            
        }

        // System.out.println(jpqlSelect);

        if(jpqlSelect.equals("*"))
            jpqlQuery = "select " + jpqlSelect + " from " + jpqlFrom;
        else
            jpqlQuery = "select " + jpqlSelect + " from " + jpqlFrom + " s";
        
        return jpqlQuery;
    }

    public static void main(String[] argv) {
		String s = convertQuery("PREFIX foo: <http://www.semanticweb.org/prateksha/ontologies/2021/1/college#iMTech> SELECT ?subject ?object WHERE { ?subject foo:Id ?object }");
		
        System.out.println(s);
	}

}
