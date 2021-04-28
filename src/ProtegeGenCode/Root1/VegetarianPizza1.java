package Root1;

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
 * Source Class: VegetarianPizza1 <br>
 * @version generated on Wed Apr 28 11:02:02 IST 2021 by prateksha
 */

public interface VegetarianPizza1 extends Pizza {

    /* ***************************************************
     * Property http://www.co-ode.org/ontologies/pizza/pizza.owl#hasBase
     */
     
    /**
     * Gets all property values for the hasBase property.<p>
     * 
     * @returns a collection of values for the hasBase property.
     */
    Collection<? extends PizzaBase> getHasBase();

    /**
     * Checks if the class has a hasBase property value.<p>
     * 
     * @return true if there is a hasBase property value.
     */
    boolean hasHasBase();

    /**
     * Adds a hasBase property value.<p>
     * 
     * @param newHasBase the hasBase property value to be added
     */
    void addHasBase(PizzaBase newHasBase);

    /**
     * Removes a hasBase property value.<p>
     * 
     * @param oldHasBase the hasBase property value to be removed.
     */
    void removeHasBase(PizzaBase oldHasBase);


    /* ***************************************************
     * Property http://www.co-ode.org/ontologies/pizza/pizza.owl#hasIngredient
     */
     
    /**
     * Gets all property values for the hasIngredient property.<p>
     * 
     * @returns a collection of values for the hasIngredient property.
     */
    Collection<? extends Food> getHasIngredient();

    /**
     * Checks if the class has a hasIngredient property value.<p>
     * 
     * @return true if there is a hasIngredient property value.
     */
    boolean hasHasIngredient();

    /**
     * Adds a hasIngredient property value.<p>
     * 
     * @param newHasIngredient the hasIngredient property value to be added
     */
    void addHasIngredient(Food newHasIngredient);

    /**
     * Removes a hasIngredient property value.<p>
     * 
     * @param oldHasIngredient the hasIngredient property value to be removed.
     */
    void removeHasIngredient(Food oldHasIngredient);


    /* ***************************************************
     * Property http://www.co-ode.org/ontologies/pizza/pizza.owl#hasTopping
     */
     
    /**
     * Gets all property values for the hasTopping property.<p>
     * 
     * @returns a collection of values for the hasTopping property.
     */
    Collection<? extends PizzaTopping> getHasTopping();

    /**
     * Checks if the class has a hasTopping property value.<p>
     * 
     * @return true if there is a hasTopping property value.
     */
    boolean hasHasTopping();

    /**
     * Adds a hasTopping property value.<p>
     * 
     * @param newHasTopping the hasTopping property value to be added
     */
    void addHasTopping(PizzaTopping newHasTopping);

    /**
     * Removes a hasTopping property value.<p>
     * 
     * @param oldHasTopping the hasTopping property value to be removed.
     */
    void removeHasTopping(PizzaTopping oldHasTopping);


    /* ***************************************************
     * Common interfaces
     */

    OWLNamedIndividual getOwlIndividual();

    OWLOntology getOwlOntology();

    void delete();

}
