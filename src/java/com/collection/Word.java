/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.collection;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author nguyen
 */
@XmlRootElement
public class Word {

    private String name;
    private String definition;
    private String origin;
    private String examples;
    private String id;
    private String type;
    private String pronun;

    public String getPronun() {
        return pronun;
    }

    @XmlElement
    public void setPronun(String pronun) {
        this.pronun = pronun;
    }

    public Word() {
    }

    public String getType() {
        return type;
    }

    @XmlElement
    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    public String getDefinition() {
        return definition;
    }

    @XmlElement
    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getExamples() {
        return examples;
    }

    @XmlElement
    public void setExamples(String examples) {
        this.examples = examples;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
