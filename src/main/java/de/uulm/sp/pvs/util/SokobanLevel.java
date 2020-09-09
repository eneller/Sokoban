package de.uulm.sp.pvs.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import static de.uulm.sp.pvs.App.getResourceAsFile;


public class SokobanLevel {
    private static final String XML_SCHEMA = "/sokoban.xsd";
    private final String[] authors;
    private final Logger logger = Logger.getGlobal();
    private String lvlName;
    private String difficulty;
    private char[][] playingField;

    public SokobanLevel(String path) throws ParserConfigurationException, IOException, SAXException {
        // Load the schema
        Schema schema;


        try {
            URL u = new URL("file:"+XML_SCHEMA);
            URI uri = new URI(u.toExternalForm());
            schema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
                    .newSchema(new File(SokobanLevel.class.getResource(XML_SCHEMA).toURI()));
        } catch (SAXException | URISyntaxException saxe) {
            throw new IOException("Error reading schema!", saxe);
        }




/*
        try {
            URL u = new URL("file:"+XML_SCHEMA);
            URI uri = new URI(u.toExternalForm());
            //File f = FileUtils.toFile // use commons io or plexus utils
            File f = getResourceAsFile(XML_SCHEMA);
            schema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
                    .newSchema(f); //use getResourceAsStream to ensure compatibility with maven and jar file navigation
        } catch (SAXException | URISyntaxException e) {
            throw new IOException("Error reading schema!", e);
        }
*/


        // Create the document builder
        DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
        fact.setSchema(schema);
        fact.setNamespaceAware(true);

        DocumentBuilder builder;
        try {
            builder = fact.newDocumentBuilder();
        } catch (ParserConfigurationException pce) {
            throw new SAXException("Error in parser configuration", pce);
        }
        builder.setErrorHandler(new ErrorHandler() {
            @Override
            public void warning(SAXParseException e) throws SAXException {
                throw new SAXException("Warning");
            }

            @Override
            public void error(SAXParseException e) throws SAXException {
                throw new SAXException(e);
            }

            @Override
            public void fatalError(SAXParseException e) throws SAXException {
                throw new SAXException("fatal");
            }
        });

        // Read the document
        Document sokobanDocument;
        try {
            sokobanDocument = builder.parse(path);
        } catch (SAXException saxe) {
            throw new SAXException("Error while parsing file: " + saxe.getMessage(), saxe);
        } catch (IOException ioe) {
            throw new IOException("Error while reading file!", ioe);
        }




        Document doc;
        try{
            doc= builder.parse(path);
        }
        catch (IOException e){
            throw new IOException("Error reading file",e);
        }
        catch(SAXException e){
            throw new SAXException("Error parsing file",e);
        }


        var root = doc.getDocumentElement();
        var children = root.getChildNodes();

        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < children.getLength(); i++) {
            if (children.item(i) instanceof Element) {
                String tag = ((Element) children.item(i)).getTagName();
                //logger.log(Level.INFO, tag);
                switch (tag) {
                    case "Author":
                        list.add(children.item(i).getTextContent());
                        break;
                    case "LevelName":
                        this.lvlName = children.item(i).getTextContent();
                        break;
                    case "Difficulty":
                        this.difficulty = children.item(i).getTextContent();
                        break;
                    case "LevelData":
                        //split at line breaks
                        String[] a = children.item(i).getTextContent().split("\n");
                        //logger.log(Level.INFO, Arrays.toString(a));
                        //remove leading whitespaces
                        //get dimension attributes of LevelData element
                        int height = Integer.parseInt(((Element) children.item(i)).getAttribute("height"));
                        int width = Integer.parseInt(((Element) children.item(i)).getAttribute("width"));
                        logger.log(Level.INFO, "width: " + width);
                        logger.log(Level.INFO, "height: " + height);

                        if (a.length != Integer.parseInt(((Element) children.item(i)).getAttribute("height"))) {
                            throw new ArrayIndexOutOfBoundsException("Specified height differs from actual height");
                        }
                        this.playingField = new char[a.length][];
                        //logger.log(Level.INFO, "array length: " + a.length);
                        for (int j = 0; j < playingField.length; j++) {
                            //logger.log(Level.INFO, j + " String length: " + a[j].strip().length());
                            this.playingField[j] = a[j].strip().toCharArray();
                            if (this.playingField[j].length != width) {
                                throw new ArrayIndexOutOfBoundsException("Specified width differs from actual width");
                            }
                        }


                        break;
                    default:
                        throw new SAXException("Found unsupported Element");
                }

            }

        }
        this.authors = list.toArray(new String[0]);
        logger.log(Level.INFO, "Author(s): "+Arrays.toString(authors));

    }

    public String[] getAuthors() {
        return authors;
    }

    public String getLvlName() {
        return lvlName;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public char[][] getPlayingField() {
        return playingField;
    }

    public int[] getDimensions() {
        return new int[]{this.playingField.length, this.playingField[0].length};
    }


}
