package com.epam.workingxml;

import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

public class XmlValidation {

    public static void main(String[] args) {
        boolean responseOne = XmlValidation.validateXMLSchema("src/input/bs.xsd", "src/input/bookstore.xml");
        System.out.println(String.format("bookstore.xml validates against bs.xsd? - %s", responseOne));

        boolean responceTwo = XmlValidation.validateXMLSchema("src/input/bs.xsd", "src/input/employee.xml");
        System.out.println(String.format("employee.xml validates against bs.xsd? - %s", responceTwo));
    }

    public static boolean validateXMLSchema(String xsdPath, String xmlPath){

        try {
            SchemaFactory factory =
                    SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(xsdPath));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(xmlPath)));
        } catch (SAXException se) {
            System.out.println("Is NOT valid reason: "+se.getMessage());
            return false;
        }catch (IOException e) {
            System.out.println("Exception: "+e.getMessage());
            return false;
        }
        return true;
    }
}
