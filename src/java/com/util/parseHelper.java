/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

import com.collection.Words;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author nguyen
 */
public class parseHelper {
    
    public Document getHtmlFromUrl(String url) throws IOException{
    Document doc = Jsoup.connect(url).get();
    return doc;
    }
    public Words getMeaning(Document doc, String name) throws IOException{
    Words word = new Words();
  //  word.setDefinition(doc.);
    Element meaning = doc.getElementsByClass("sense").get(0);
    Element definition = meaning.getElementsByClass("definition").get(0);
    Element type = doc.getElementsByClass("partOfSpeech").get(0);
    Element original = doc.getElementsByClass("etymology").get(0);
    System.out.print("type"+type.text());
    System.out.print("origincal"+original.text());
    System.out.print("meaning"+definition.text());
    word.setOrigin(original.text());
    word.setDefinition(definition.text());
    word.setType(type.text());
    word.setName(name);
    return word;
    }
}
