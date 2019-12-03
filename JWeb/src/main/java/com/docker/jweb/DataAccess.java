/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.docker.jweb;

//import com.orientechnologies.orient.core.sql.OCommandSQL;
//import com.tinkerpop.blueprints.Vertex;
//import com.tinkerpop.blueprints.impls.orient.OrientGraph;
//import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import com.orientechnologies.orient.core.db.ODatabasePool;
import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.db.OrientDB;
import com.orientechnologies.orient.core.db.OrientDBConfig;
import com.orientechnologies.orient.core.sql.executor.OResultSet;

/**
 *
 * @author Wishwanath
 */
public class DataAccess {

    String conUrl = "remote:localhost/aoe";
    String userName = "root";
    String password = "admin";
    String db = "abc";

//    protected OrientGraphFactory db;
    OrientDB orientDB;

    public DataAccess() {

        Properties prop = new Properties();
        try {

            String path = System.getenv("DOCKER_HOME");

            InputStream fis = new FileInputStream(path + "db.properties");
            prop.load(fis);

            conUrl = prop.getProperty("url");
            userName = prop.getProperty("user");
            password = prop.getProperty("pass");

//            db = new OrientGraphFactory(conUrl, userName, password).setupPool(1, 10);
//            
            orientDB = new OrientDB(conUrl, userName, password, OrientDBConfig.defaultConfig());

        } catch (Exception e) {
            System.out.println("Error" + e);
            e.printStackTrace();
            return;
        }

    }

    public ArrayList list() {

        ArrayList<String> list = new ArrayList<>();

        String sql = "select from MyApp";
        try {

            ODatabaseSession dbx = orientDB.open(db, userName, password);

            try (OResultSet rs = dbx.execute("SQL", sql)) {

                while (rs.hasNext()) {
                                        
                    String name = rs.next().getProperty("name").toString();

                    list.add(name);

                }
            }
            
            dbx.close();

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }

        return list;
    }

}
