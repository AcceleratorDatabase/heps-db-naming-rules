/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package heps.db.naming.common.tools;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author BaiYu
 */
public class EmProvider {
    
    private EntityManagerFactory emf;
    private static final String DB_PU = "heps-db-namingPU";
    private static final EmProvider singleton = new EmProvider();
    
    public EmProvider(){
        
    }
    
    public static EmProvider getInstance(){
        return singleton;
    }
    
    public EntityManagerFactory getEntityManagerFactory(){
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory(DB_PU);
        }
        return emf;
    }
    
    public void closeEmf(){
        if (emf.isOpen() || emf != null) {
            emf.close();
        }
        emf.close();
    }
    
}
