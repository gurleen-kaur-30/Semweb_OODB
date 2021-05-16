package ProtegeGenCode.Root2.impl;

import ProtegeGenCode.Root2.*;

import java.util.HashSet;

import java.io.Serializable;
import javax.jdo.annotations.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;



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
 * Source Class: DefaultOld <br>
 * @version generated on Sun May 16 23:49:48 IST 2021 by prateksha
 */
@Entity
 public class DefaultOld extends WrappedIndividualImpl implements Old , Serializable {
	 private static final long serialVersionUID = 1L;
	 @GeneratedValue
	 private long id;
	 @Id private String name;
	
@Embedded private Collection<? extends People> HasAncestor;
@Embedded private Collection<? extends People> HasFather;
@Embedded private Collection<? extends People> HasFriend;
@Embedded private Collection<? extends People> HasMother;

    public DefaultOld(CodeGenerationInference inference, IRI iri) {
        super(inference, iri);
		 name = iri.toString();
	HasAncestor=getHasAncestor();
	HasFather=getHasFather();
	HasFriend=getHasFriend();
	HasMother=getHasMother();
	
    }





    /* ***************************************************
     * Object Property http://www.semanticweb.org/gurleen/ontologies/people#hasAncestor
     */
     
    public Collection<? extends People> getHasAncestor() {
        return getDelegate().getPropertyValues(getOwlIndividual(),
                                               Vocabulary.OBJECT_PROPERTY_HASANCESTOR,
                                               DefaultPeople.class);
    }

    public boolean hasHasAncestor() {
	   return !getHasAncestor().isEmpty();
    }

    public void addHasAncestor(People newHasAncestor) {
        getDelegate().addPropertyValue(getOwlIndividual(),
                                       Vocabulary.OBJECT_PROPERTY_HASANCESTOR,
                                       newHasAncestor);
    }

    public void removeHasAncestor(People oldHasAncestor) {
        getDelegate().removePropertyValue(getOwlIndividual(),
                                          Vocabulary.OBJECT_PROPERTY_HASANCESTOR,
                                          oldHasAncestor);
    }


    /* ***************************************************
     * Object Property http://www.semanticweb.org/gurleen/ontologies/people#hasFather
     */
     
    public Collection<? extends People> getHasFather() {
        return getDelegate().getPropertyValues(getOwlIndividual(),
                                               Vocabulary.OBJECT_PROPERTY_HASFATHER,
                                               DefaultPeople.class);
    }

    public boolean hasHasFather() {
	   return !getHasFather().isEmpty();
    }

    public void addHasFather(People newHasFather) {
        getDelegate().addPropertyValue(getOwlIndividual(),
                                       Vocabulary.OBJECT_PROPERTY_HASFATHER,
                                       newHasFather);
    }

    public void removeHasFather(People oldHasFather) {
        getDelegate().removePropertyValue(getOwlIndividual(),
                                          Vocabulary.OBJECT_PROPERTY_HASFATHER,
                                          oldHasFather);
    }


    /* ***************************************************
     * Object Property http://www.semanticweb.org/gurleen/ontologies/people#hasFriend
     */
     
    public Collection<? extends People> getHasFriend() {
        return getDelegate().getPropertyValues(getOwlIndividual(),
                                               Vocabulary.OBJECT_PROPERTY_HASFRIEND,
                                               DefaultPeople.class);
    }

    public boolean hasHasFriend() {
	   return !getHasFriend().isEmpty();
    }

    public void addHasFriend(People newHasFriend) {
        getDelegate().addPropertyValue(getOwlIndividual(),
                                       Vocabulary.OBJECT_PROPERTY_HASFRIEND,
                                       newHasFriend);
    }

    public void removeHasFriend(People oldHasFriend) {
        getDelegate().removePropertyValue(getOwlIndividual(),
                                          Vocabulary.OBJECT_PROPERTY_HASFRIEND,
                                          oldHasFriend);
    }


    /* ***************************************************
     * Object Property http://www.semanticweb.org/gurleen/ontologies/people#hasMother
     */
     
    public Collection<? extends People> getHasMother() {
        return getDelegate().getPropertyValues(getOwlIndividual(),
                                               Vocabulary.OBJECT_PROPERTY_HASMOTHER,
                                               DefaultPeople.class);
    }

    public boolean hasHasMother() {
	   return !getHasMother().isEmpty();
    }

    public void addHasMother(People newHasMother) {
        getDelegate().addPropertyValue(getOwlIndividual(),
                                       Vocabulary.OBJECT_PROPERTY_HASMOTHER,
                                       newHasMother);
    }

    public void removeHasMother(People oldHasMother) {
        getDelegate().removePropertyValue(getOwlIndividual(),
                                          Vocabulary.OBJECT_PROPERTY_HASMOTHER,
                                          oldHasMother);
    }


}