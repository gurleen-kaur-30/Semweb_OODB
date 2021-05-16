package ProtegeGenCode.Root2;

import java.net.URI;
import java.util.Collection;
import javax.xml.datatype.XMLGregorianCalendar;

import org.protege.owl.codegeneration.WrappedIndividual;

import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * 
 * <p>
 * Generated by Protege (http://protege.stanford.edu). <br>
 * Source Class: Old <br>
 * @version generated on Sun May 16 23:25:38 IST 2021 by gurleen
 */

public interface Old extends People {

    /* ***************************************************
     * Property http://www.semanticweb.org/gurleen/ontologies/people#hasAncestor
     */
     
    /**
     * Gets all property values for the hasAncestor property.<p>
     * 
     * @returns a collection of values for the hasAncestor property.
     */
    Collection<? extends People> getHasAncestor();

    /**
     * Checks if the class has a hasAncestor property value.<p>
     * 
     * @return true if there is a hasAncestor property value.
     */
    boolean hasHasAncestor();

    /**
     * Adds a hasAncestor property value.<p>
     * 
     * @param newHasAncestor the hasAncestor property value to be added
     */
    void addHasAncestor(People newHasAncestor);

    /**
     * Removes a hasAncestor property value.<p>
     * 
     * @param oldHasAncestor the hasAncestor property value to be removed.
     */
    void removeHasAncestor(People oldHasAncestor);


    /* ***************************************************
     * Property http://www.semanticweb.org/gurleen/ontologies/people#hasChild
     */
     
    /**
     * Gets all property values for the hasChild property.<p>
     * 
     * @returns a collection of values for the hasChild property.
     */
    Collection<? extends People> getHasChild();

    /**
     * Checks if the class has a hasChild property value.<p>
     * 
     * @return true if there is a hasChild property value.
     */
    boolean hasHasChild();

    /**
     * Adds a hasChild property value.<p>
     * 
     * @param newHasChild the hasChild property value to be added
     */
    void addHasChild(People newHasChild);

    /**
     * Removes a hasChild property value.<p>
     * 
     * @param oldHasChild the hasChild property value to be removed.
     */
    void removeHasChild(People oldHasChild);


    /* ***************************************************
     * Property http://www.semanticweb.org/gurleen/ontologies/people#hasFather
     */
     
    /**
     * Gets all property values for the hasFather property.<p>
     * 
     * @returns a collection of values for the hasFather property.
     */
    Collection<? extends People> getHasFather();

    /**
     * Checks if the class has a hasFather property value.<p>
     * 
     * @return true if there is a hasFather property value.
     */
    boolean hasHasFather();

    /**
     * Adds a hasFather property value.<p>
     * 
     * @param newHasFather the hasFather property value to be added
     */
    void addHasFather(People newHasFather);

    /**
     * Removes a hasFather property value.<p>
     * 
     * @param oldHasFather the hasFather property value to be removed.
     */
    void removeHasFather(People oldHasFather);


    /* ***************************************************
     * Property http://www.semanticweb.org/gurleen/ontologies/people#hasFriend
     */
     
    /**
     * Gets all property values for the hasFriend property.<p>
     * 
     * @returns a collection of values for the hasFriend property.
     */
    Collection<? extends People> getHasFriend();

    /**
     * Checks if the class has a hasFriend property value.<p>
     * 
     * @return true if there is a hasFriend property value.
     */
    boolean hasHasFriend();

    /**
     * Adds a hasFriend property value.<p>
     * 
     * @param newHasFriend the hasFriend property value to be added
     */
    void addHasFriend(People newHasFriend);

    /**
     * Removes a hasFriend property value.<p>
     * 
     * @param oldHasFriend the hasFriend property value to be removed.
     */
    void removeHasFriend(People oldHasFriend);


    /* ***************************************************
     * Property http://www.semanticweb.org/gurleen/ontologies/people#hasMother
     */
     
    /**
     * Gets all property values for the hasMother property.<p>
     * 
     * @returns a collection of values for the hasMother property.
     */
    Collection<? extends People> getHasMother();

    /**
     * Checks if the class has a hasMother property value.<p>
     * 
     * @return true if there is a hasMother property value.
     */
    boolean hasHasMother();

    /**
     * Adds a hasMother property value.<p>
     * 
     * @param newHasMother the hasMother property value to be added
     */
    void addHasMother(People newHasMother);

    /**
     * Removes a hasMother property value.<p>
     * 
     * @param oldHasMother the hasMother property value to be removed.
     */
    void removeHasMother(People oldHasMother);


    /* ***************************************************
     * Common interfaces
     */

    OWLNamedIndividual getOwlIndividual();

    OWLOntology getOwlOntology();

    void delete();

}
