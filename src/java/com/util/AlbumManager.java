/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

import com.collection.Album;
import com.collection.Word;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;

/**
 *
 * @author nguyen
 */
public class AlbumManager {

    Document document = null;
    Element root = null;

    public XPathExpression<Element> selectElement(String query, File file)
            throws FileNotFoundException, JDOMException, IOException {
        //read file 
        FileInputStream fis = new FileInputStream(file);
        SAXBuilder sb = new SAXBuilder();
        document = sb.build(fis);
        root = document.getRootElement();
        fis.close();
        //get album
        XPathExpression<Element> xpath
                = XPathFactory.instance().compile(query, Filters.element());
        return xpath;
    }

    public boolean addWord(Word word, File file, String id)
            throws JDOMException, IOException {
        String query = "//*[@userId= '2']";
        XPathExpression<Element> xpath = selectElement(query, file);
        Element targetAlbum = xpath.evaluateFirst(document);
        //add word
        Element myWord = new Element("word");
        myWord.addContent(new Element("definition").setText(word.getDefinition()));
        myWord.addContent(new Element("name").setText(word.getName()));
        myWord.addContent(new Element("type").setText(word.getType()));
   
        targetAlbum.addContent(myWord);
        document.setContent(root);
        //send back to xml file
        try {
            FileWriter writer = new FileWriter("/home/nguyen/Desktop/file.xml");
            XMLOutputter outputter = new XMLOutputter();
            outputter.setFormat(Format.getPrettyFormat());
            outputter.output(document, writer);
            outputter.output(document, System.out);
            writer.close(); // close writer
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Word> selectAlbum(File file, String id)
            throws JDOMException, IOException {
        String query = "//album[@userId= '2']/*";
        XPathExpression<Element> xpath = selectElement(query, file);
        List<Element> listNode = xpath.evaluate(document);
        List<Word> listWord = new ArrayList<>();
        for (Element element : listNode) {
            Word current = new Word();
            current.setName(element.getChildTextTrim("name"));
            current.setType(element.getChildTextTrim("type"));
            current.setDefinition(element.getChildTextTrim("definition"));
            listWord.add(current);
        }
        return listWord;
    }

    public List<Album> selectAlbums(File file, String id)
            throws JDOMException, IOException {
        String query = "//album[@userId= '2']";
        XPathExpression<Element> xpath = selectElement(query, file);
        List<Element> listNode = xpath.evaluate(document);
        List<Album> listAlbum = new ArrayList<>();
        for (Element element : listNode) {
            Album current = new Album();
            current.setUserId(element.getAttributeValue("userId"));
            current.setName(element.getAttributeValue("name"));
            listAlbum.add(current);
        }
        return listAlbum;
    }
}
