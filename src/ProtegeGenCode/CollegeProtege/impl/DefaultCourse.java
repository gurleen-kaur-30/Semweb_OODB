package ProtegeGenCode.CollegeProtege.impl;

import ProtegeGenCode.CollegeProtege.*;

import java.io.Serializable;
import java.net.URI;
import java.util.Collection;

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
 * Source Class: DefaultCourse <br>
 * @version generated on Sat Apr 03 10:21:32 IST 2021 by prateksha
 */

 @Entity
 public class DefaultCourse extends WrappedIndividualImpl implements Course , Serializable {
	 private static final long serialVersionUID = 1L;
	 @Id @GeneratedValue
	 private long id;
	 private String name;
	

    public DefaultCourse(CodeGenerationInference inference, IRI iri) {
        super(inference, iri);
		 name = iri.toString()
	
    }





}
