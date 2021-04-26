package ProtegeGenCode.CollegeProtege.impl;

import ProtegeGenCode.CollegeProtege.*;

import java.io.Serializable;
import java.net.URI;
import java.util.Collection;

import javax.jdo.annotations.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.datatype.XMLGregorianCalendar;

import org.protege.owl.codegeneration.WrappedIndividual;
import org.protege.owl.codegeneration.impl.WrappedIndividualImpl;

import org.protege.owl.codegeneration.inference.CodeGenerationInference;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;


/**
 * Generated by Protege (http://protege.stanford.edu).<br>
 * Source Class: DefaultProfessor <br>
 * @version generated on Sat Apr 03 10:21:32 IST 2021 by prateksha
 */

 @Entity
 public class DefaultProfessor extends WrappedIndividualImpl implements Professor , Serializable {
	 private static final long serialVersionUID = 1L;
	 @GeneratedValue
	 private long id;
	 @Id
	 private String name;
	
@Embedded private Collection<? extends Course> Teaches;

    public DefaultProfessor(CodeGenerationInference inference, IRI iri) {
        super(inference, iri);
		 name = iri.toString();
	Teaches=getTeaches();
	System.out.println("Constructor called for Professor");
    }


    public String getName() {
    	return name;
    }


    /* ***************************************************
     * Object Property http://www.semanticweb.org/prateksha/ontologies/2021/1/college#teaches
     */
     
    public Collection<? extends Course> getTeaches() {
        return getDelegate().getPropertyValues(getOwlIndividual(),
                                               Vocabulary.OBJECT_PROPERTY_TEACHES,
                                               DefaultCourse.class);
    }

    public boolean hasTeaches() {
	   return !getTeaches().isEmpty();
    }

    public void addTeaches(Course newTeaches) {
        getDelegate().addPropertyValue(getOwlIndividual(),
                                       Vocabulary.OBJECT_PROPERTY_TEACHES,
                                       newTeaches);
    }

    public void removeTeaches(Course oldTeaches) {
        getDelegate().removePropertyValue(getOwlIndividual(),
                                          Vocabulary.OBJECT_PROPERTY_TEACHES,
                                          oldTeaches);
    }


}
