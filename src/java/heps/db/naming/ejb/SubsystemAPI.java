/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package heps.db.naming.ejb;

import java.util.Iterator;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import heps.db.naming.common.tools.EmProvider;
import heps.db.naming.entity.Location;
import heps.db.naming.entity.Subsystem;

/**
 *
 * @author BaiYu
 */

public class SubsystemAPI{
    
    /**
     *创建EntityManager
     */
    public static EntityManager em = EmProvider.getInstance().getEntityManagerFactory().createEntityManager();
    
    /**
     *
     * @param name 持久化实体，往数据表中插入的数据
     */
    public void setSubsystem(String name){
        Subsystem sub = new Subsystem();
        sub.setSubsystemName(name);
        em.getTransaction().begin();
        em.persist(sub);
        em.getTransaction().commit();
    }
    
    /**
     *
     * @param name
     * @return 此条件下查询结果
     */
    public Subsystem getSubsystem(String name){
        Query query = em.createNamedQuery("Subsystem.findBySubsystemName");
        query.setParameter("subsystemName", name);
        List<Subsystem> result = query.getResultList();
        if (result.isEmpty()) {
            return null;
        }else{
            return result.get(0);
        }
    }

    /**
     *
     * @return 所有Subsystem数据
     */
    public List<Subsystem> getAllSubsystem(){
        Query query = em.createNamedQuery("Subsystem.findAll");
        return query.getResultList();
    }
    
    /**
     *
     * @param subsysId 删除数据的id
     * @return
     */
    public Integer deleteSubsystemById(Integer subsysId){
        Subsystem sub = em.find(Subsystem.class, subsysId);
        em.remove(sub);
        em.getTransaction().commit();
        return 1;
    }
    
}
