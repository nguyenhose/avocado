/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.collection;

import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author nguyen
 */
@XmlRootElement
public class Album {
   
   private List<Word> word;
   private String userId;
   private String name;

    public List<Word> getWord() {
        return word;
    }
    @XmlElement 
    public void setWord(List<Word> word) {
        this.word = word;
    }
    
    public String getUserId() {
        return userId;
    }
    @XmlAttribute
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }
    @XmlAttribute
    public void setName(String name) {
        this.name = name;
    }
   
}
