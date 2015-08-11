/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

import com.collection.Users;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import java.net.UnknownHostException;

/**
 *
 * @author nguyen
 */
public class MongoConnection {

    public static MongoClient getConnection() throws UnknownHostException {
        MongoClient mongo = new MongoClient("localhost", 27017);
        return mongo;
    }
}
