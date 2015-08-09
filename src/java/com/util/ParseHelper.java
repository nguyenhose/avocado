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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
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
        word.setName(keyWord.text());
        word.setOrigin(original.text());
        word.setDefinition(definition.text());
        word.setType(type.text());
        word.setName(name);
        return word;
    }

    public boolean initalFile(Word word, File file) {
        Albums albums = new Albums();
        Album myAlbum = new Album();
        List<Word> listWords = new ArrayList<>();
        List<Album> listAlbums = new ArrayList<>();
        listWords.add(word);
        listWords.add(word);
        myAlbum.setName("fistOne");
        myAlbum.setUserId("1");
        myAlbum.setWord(listWords);
        listAlbums.add(myAlbum);
        listAlbums.add(myAlbum);
        albums.setAlbum(listAlbums);
        //create the xml file
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Albums.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(albums, file);
            System.out.print("marshal start");
            jaxbMarshaller.marshal(albums, System.out);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
