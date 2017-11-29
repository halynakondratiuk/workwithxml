package com.epam.workingxml;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.events.*;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.io.FileReader;
import java.util.Iterator;

public class ParseXML {

    public static void parseWithDOM(){
        try {

            File fXmlFile = new File("src/input/bookstore.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();

            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("book");

            System.out.println("<----------------->");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                System.out.println("\nCurrent Element :" + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    System.out.println(String.format("Book category : %s", eElement.getAttribute("category")));
                    System.out.println(String.format("Book title : %s", eElement.getElementsByTagName("title").item(0).getTextContent()));
                    System.out.println(String.format("Book author : %s", eElement.getElementsByTagName("author").item(0).getTextContent()));
                    System.out.println(String.format("Published in : %s", eElement.getElementsByTagName("year").item(0).getTextContent()));
                    System.out.println(String.format("Price : %s", eElement.getElementsByTagName("price").item(0).getTextContent()));

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void parseWithSAX(){
        try {
            File inputFile = new File("src/input/bookstore.xml");
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            DefaultHandler handler = new DefaultHandler() {

                boolean btitle = false;
                boolean bauthor = false;
                boolean byear = false;
                boolean bprice = false;

                public void startElement(String uri, String localName,String qName,
                                         Attributes attributes) throws SAXException {

                    System.out.println("Start Element :" + qName);

                    if (qName.equalsIgnoreCase("TITLE")) {
                        btitle = true;
                    }

                    if (qName.equalsIgnoreCase("AUTHOR")) {
                        bauthor = true;
                    }

                    if (qName.equalsIgnoreCase("YEAR")) {
                        byear = true;
                    }

                    if (qName.equalsIgnoreCase("PRICE")) {
                        bprice = true;
                    }

                }

                public void endElement(String uri, String localName,
                                       String qName) throws SAXException {

                    System.out.println("End Element :" + qName);

                }

                public void characters(char ch[], int start, int length) throws SAXException {

                    if (btitle) {
                        System.out.println(String.format("Book title : %s", new String(ch, start, length)));
                        btitle = false;
                    }

                    if (bauthor) {
                        System.out.println(String.format("Book author : %s", new String(ch, start, length)));
                        bauthor = false;
                    }

                    if (byear) {
                        System.out.println(String.format("Published in : %s", new String(ch, start, length)));
                        byear = false;
                    }

                    if (bprice) {
                        System.out.println(String.format("Price : %s", new String(ch, start, length)));
                        bprice = false;
                    }

                }

            };

            saxParser.parse(inputFile, handler);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void parseWithStAX(){
        boolean btitle = false;
        boolean bauthor = false;
        boolean byear = false;
        boolean bprice = false;

        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLEventReader eventReader =
                    factory.createXMLEventReader(new FileReader("src/input/bookstore.xml"));

            while(eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();

                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    String qName = startElement.getName().getLocalPart();

                    if (qName.equalsIgnoreCase("book")) {
                        System.out.println("Start Element : book");
                        Iterator<Attribute> attributes = startElement.getAttributes();
                        String category = attributes.next().getValue();
                        System.out.println(String.format("Category : %s", category));
                    } else if (qName.equalsIgnoreCase("TITLE")) {
                        btitle = true;
                    } else if (qName.equalsIgnoreCase("AUTHOR")) {
                        bauthor = true;
                    } else if (qName.equalsIgnoreCase("YEAR")) {
                        byear = true;
                    } else if (qName.equalsIgnoreCase("PRICE")) {
                        bprice = true;
                    }
                }

                if (event.isCharacters()) {
                    Characters characters = event.asCharacters();
                    if (btitle) {
                        System.out.println(String.format("Book title : %s", characters.getData()));
                        btitle = false;
                    }
                    if (bauthor) {
                        System.out.println(String.format("Book author : %s", characters.getData()));
                        bauthor = false;
                    }
                    if (byear) {
                        System.out.println(String.format("Published in: %s", characters.getData()));
                        byear = false;
                    }
                    if (bprice) {
                        System.out.println(String.format("Price : %s", characters.getData()));
                        bprice = false;
                    }
                }

                if (event.isEndElement()) {
                    EndElement endElement = event.asEndElement();

                    if (endElement.getName().getLocalPart().equalsIgnoreCase("book")) {
                        System.out.println("End Element : book");
                        System.out.println();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String argv[]) {
        System.out.println("Parse XML document using DOM parser:");
        long starDom = System.currentTimeMillis();
        ParseXML.parseWithDOM();
        System.out.println(String.format("Time for DOM: %d ms", (System.currentTimeMillis() - starDom)));

        System.out.println("***********************");

        System.out.println("Parse XML document using SAX parser:");
        System.out.println();
        long starSax = System.currentTimeMillis();
        ParseXML.parseWithSAX();
        System.out.println(String.format("Time for SAX: %d ms", (System.currentTimeMillis() - starSax)));

        System.out.println("***********************");

        System.out.println("Parse XML document using StAX parser:");
        System.out.println();
        long starStax = System.currentTimeMillis();
        ParseXML.parseWithStAX();
        System.out.println(String.format("Time for StAX: %d ms", (System.currentTimeMillis() - starStax)));

    }
}
