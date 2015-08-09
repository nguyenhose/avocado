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

    public static void getConnection() throws UnknownHostException {
        MongoClient mongo = new MongoClient("localhost", 27017);
        DB db = mongo.getDB("avocado");
        DBCollection col = db.getCollection("users");
        Users user = new Users();
        DBObject doc = createDBObject(user);
        
        WriteResult result = col.insert(doc);
        System.out.println(result.getUpsertedId());
        System.out.println(result.getN());
        System.out.println(result.isUpdateOfExisting());
        System.out.println(result.getLastConcern());
    }

    private static DBObject createDBObject(Users user) {
        BasicDBObjectBuilder docBuilder = BasicDBObjectBuilder.start();

        docBuilder.append("_id", user.getId());
        docBuilder.append("name", user.getName());
        return docBuilder.get();
    }

    private static Users createUser() {
        Users u = new Users();
        u.setId(2);
        u.setName("Nguyen");
        return u;
    }
}
