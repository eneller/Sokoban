package de.uulm.sp.pvs.util;

import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SokobanLevel {
    private final String[] authors;
    private String lvlName;
    private String difficulty;
    private char[][] playingField;
    private final Logger logger = Logger.getGlobal();

    public SokobanLevel(String path) throws ParserConfigurationException, IOException, SAXException {
        ArrayList<String> list = new ArrayList<String>();
        var builder = DocumentBuilderFactory.newDefaultInstance().newDocumentBuilder();
        var doc = builder.parse(path);
        var root = doc.getDocumentElement();
        var children = root.getChildNodes();

        for(int i =0;i<children.getLength();i++){
            if(children.item(i) instanceof Element){
                String tag = ((Element) children.item(i)).getTagName();
                logger.log(Level.INFO, tag);
                switch(tag){
                    case "Author":
                        list.add(children.item(i).getTextContent());
                        break;
                    case "LevelName":
                        this.lvlName = children.item(i).getTextContent();
                        break;
                    case "Difficulty":
                        this.difficulty=children.item(i).getTextContent();
                        break;
                    case "LevelData":
                        //split at line breaks
                        String[] a = children.item(i).getTextContent().split("\n");
                        logger.log(Level.INFO, Arrays.toString(a));
                        //remove leading whitespaces
                        //get dimension attributes of LevelData element
                        int height = Integer.parseInt(((Element) children.item(i)).getAttribute("height"));
                        int width = Integer.parseInt(((Element) children.item(i)).getAttribute("width"));
                        logger.log(Level.INFO, "width: "+ width);
                        logger.log(Level.INFO, "height: "+ height);

                        if(a.length!= Integer.parseInt(((Element) children.item(i)).getAttribute("height"))){
                            throw new ArrayIndexOutOfBoundsException("Specified height differs from actual height");
                        }
                        this.playingField = new char[a.length][];
                        logger.log(Level.INFO, "array length: "+ a.length);
                        for(int j=0; j< playingField.length;j++){
                            logger.log(Level.INFO, j+" String length: " + a[j].strip().length());
                            this.playingField[j]= a[j].strip().toCharArray();
                            if(this.playingField[j].length!=width){
                                throw new ArrayIndexOutOfBoundsException("Specified width differs from actual width");
                            }
                        }


                        break;
                    default: throw new SAXException("Found unsupported Element");
                }

            }

        }
        this.authors= list.toArray(new String[0]);
        logger.log(Level.INFO, Arrays.toString(authors));
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

    public int[] getDimensions(){
        return new int[]{this.playingField.length, this.playingField[0].length};
    }


}
