package Root1.impl;

import Root1.*;


import java.net.URI;
import java.util.Collection;
import javax.xml.datatype.XMLGregorianCalendar;

import org.protege.owl.codegeneration.WrappedIndividual;
import org.protege.owl.codegeneration.impl.WrappedIndividualImpl;

import org.protege.owl.codegeneration.inference.CodeGenerationInference;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;


/**
 * Generated by Protege (http://protege.stanford.edu).<br>
 * Source Class: DefaultCapricciosa <br>
 * @version generated on Wed Apr 28 11:02:02 IST 2021 by prateksha
 */
public class DefaultCapricciosa extends WrappedIndividualImpl implements Capricciosa {

    public DefaultCapricciosa(CodeGenerationInference inference, IRI iri) {
        super(inference, iri);
    }





    /* ***************************************************
     * Object Property http://www.co-ode.org/ontologies/pizza/pizza.owl#hasBase
     */
     
    public Collection<? extends PizzaBase> getHasBase() {
        return getDelegate().getPropertyValues(getOwlIndividual(),
                                               Vocabulary.OBJECT_PROPERTY_HASBASE,
                                               DefaultPizzaBase.class);
    }

    public boolean hasHasBase() {
	   return !getHasBase().isEmpty();
    }

    public void addHasBase(PizzaBase newHasBase) {
        getDelegate().addPropertyValue(getOwlIndividual(),
                                       Vocabulary.OBJECT_PROPERTY_HASBASE,
                                       newHasBase);
    }

    public void removeHasBase(PizzaBase oldHasBase) {
        getDelegate().removePropertyValue(getOwlIndividual(),
                                          Vocabulary.OBJECT_PROPERTY_HASBASE,
                                          oldHasBase);
    }


    /* ***************************************************
     * Object Property http://www.co-ode.org/ontologies/pizza/pizza.owl#hasIngredient
     */
     
    public Collection<? extends Food> getHasIngredient() {
        return getDelegate().getPropertyValues(getOwlIndividual(),
                                               Vocabulary.OBJECT_PROPERTY_HASINGREDIENT,
                                               DefaultFood.class);
    }

    public boolean hasHasIngredient() {
	   return !getHasIngredient().isEmpty();
    }

    public void addHasIngredient(Food newHasIngredient) {
        getDelegate().addPropertyValue(getOwlIndividual(),
                                       Vocabulary.OBJECT_PROPERTY_HASINGREDIENT,
                                       newHasIngredient);
    }

    public void removeHasIngredient(Food oldHasIngredient) {
        getDelegate().removePropertyValue(getOwlIndividual(),
                                          Vocabulary.OBJECT_PROPERTY_HASINGREDIENT,
                                          oldHasIngredient);
    }


    /* ***************************************************
     * Object Property http://www.co-ode.org/ontologies/pizza/pizza.owl#hasTopping
     */
     
    public Collection<? extends PizzaTopping> getHasTopping() {
        return getDelegate().getPropertyValues(getOwlIndividual(),
                                               Vocabulary.OBJECT_PROPERTY_HASTOPPING,
                                               DefaultPizzaTopping.class);
    }

    public boolean hasHasTopping() {
	   return !getHasTopping().isEmpty();
    }

    public void addHasTopping(PizzaTopping newHasTopping) {
        getDelegate().addPropertyValue(getOwlIndividual(),
                                       Vocabulary.OBJECT_PROPERTY_HASTOPPING,
                                       newHasTopping);
    }

    public void removeHasTopping(PizzaTopping oldHasTopping) {
        getDelegate().removePropertyValue(getOwlIndividual(),
                                          Vocabulary.OBJECT_PROPERTY_HASTOPPING,
                                          oldHasTopping);
    }


}
