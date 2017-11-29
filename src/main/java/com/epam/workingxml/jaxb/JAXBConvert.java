package com.epam.workingxml.jaxb;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class JAXBConvert {

    public static void main(String[] args){
        Book book = new Book();

        book.setTitle("Harry Potter");
        book.setAuthor("Joanne Rowling");
        book.setYear(1997);
        book.setPrice(250);
        book.setCategory("children");

//        JAXBConvert.convertToXML(book);
//        JAXBConvert.convertFromXML();
    }
}
