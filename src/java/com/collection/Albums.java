/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.collection;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author nguyen
 */
@XmlRootElement
public class Albums {
   
    private List<Album> album;

    public List<Album> getAlbum() {
        return album;
    }
    @XmlElement
    public void setAlbum(List<Album> album) {
        this.album = album;
    }
  
}
