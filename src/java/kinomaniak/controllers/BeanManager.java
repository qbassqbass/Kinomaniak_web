/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kinomaniak.controllers;

import javax.inject.Named;
import javax.enterprise.context.Dependent;
import kinomaniak.database.DBConnector;

/**
 *
 * @author Jakub
 */
@Named(value = "beanManager")
@Dependent
public class BeanManager {
    DBConnector db;
    /**
     * Creates a new instance of BeanManager
     */
    public BeanManager() {
        db = new DBConnector();
        db.connect();
    }
    
}
