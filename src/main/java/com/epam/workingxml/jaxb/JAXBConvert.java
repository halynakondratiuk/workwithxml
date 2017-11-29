package com.epam.workingxml.jaxb;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class JAXBConvert {

    public static void convertToXML(Book book){
        try {
            File file = new File("src/input/book.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(Book.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(book, file);
            jaxbMarshaller.marshal(book, System.out);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public static void convertFromXML(){
        try {

            File file = new File("src/input/book.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(Book.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Book book = (Book) jaxbUnmarshaller.unmarshal(file);
            System.out.println(String.format("Convert from XML: %s, %s, %d", book.getTitle(), book.getAuthor(), book. getYear()));

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

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
