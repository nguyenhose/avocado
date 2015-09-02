/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

import com.collection.Album;
import com.collection.Albums;
import com.collection.Word;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 *
 * @author nguyen
 */
public class ParseHelper {

    public Document getHtmlFromUrl(String url) throws IOException {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return doc;
    }

    public Word getMeaning(Document doc, String name) throws IOException {
        Word word = new Word();
        //  word.setDefinition(doc.);

        Element keyWord = doc.getElementsByClass("pageTitle").get(0);
        Element meaning = doc.getElementsByClass("sense").get(0);
        Element definition = meaning.getElementsByClass("definition").get(0);
        Element type = doc.getElementsByClass("partOfSpeech").get(0);
        Element original = doc.getElementsByClass("etymology").get(0);
        Element pronun = doc.getElementsByClass("headpron").get(0);
        Element exam = null;
        try {
            exam = doc.getElementsByClass("example").get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        word.setName(keyWord.text());

        if (!original.text().isEmpty()) {
            word.setOrigin(original.text());
        } else {
            word.setOrigin("N/A");
        }
        if (exam != null) {
            word.setExamples(exam.text());
        } else {
            word.setExamples("N/A");
        }

        word.setPronun(pronun.text().split(":")[1]);
        word.setDefinition(definition.text().replace(":", "."));
        word.setType(type.text());
        word.setName(name);
        return word;
    }

    public boolean initalFile(Word word, String name, String userId, File file) {
        Albums albums = new Albums();
        Album myAlbum = new Album();
        List<Word> listWords = new ArrayList<>();
        List<Album> listAlbums = new ArrayList<>();
        listWords.add(word);
        myAlbum.setName(name);
        myAlbum.setUserId(userId);
        myAlbum.setPub("false");
        myAlbum.setWord(listWords);
        listAlbums.add(myAlbum);
        albums.setAlbum(listAlbums);
        //create the xml file

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Albums.class
            );
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
                    true);
            jaxbMarshaller.marshal(albums, file);

            System.out.print(
                    "marshal start");
            jaxbMarshaller.marshal(albums, System.out);
            
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void XSLConverter(String xslPath, String xmlPath, String output, String path) throws TransformerException, FileNotFoundException {
        try {
            StreamSource xstlFile = new StreamSource(xslPath);
            StreamSource xmlFile = new StreamSource(xmlPath);
            StreamResult htmlFile = new StreamResult(new FileOutputStream(output));

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer trans = tf.newTransformer(xstlFile);
            trans.transform(xmlFile, htmlFile);

        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
    }
}
