/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package heps.db.naming.ejb;

import heps.db.naming.common.tools.EmProvider;
import heps.db.naming.entity.Location;
import heps.db.naming.entity.MoreThanNine;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author BaiYu
 */
public class LocationAPI{
    
    /**
     *创建EntityManager
     */
    public static EntityManager em = EmProvider.getInstance().getEntityManagerFactory().createEntityManager();
    
    /**
     *
     * @param mtn 持久化实体，往数据表中插入的数据
     * @param name
     */
    public void setLocation(MoreThanNine mtn, String name){
        Location l = new Location();
        l.setJudgeId(mtn);
        l.setLocationName(name);
        em.getTransaction().begin();
        em.persist(l);
        em.getTransaction().commit();
    }
    
    /**
     *
     * @param mtn
     * @param another
     * @param name
     * @return 此条件下查询结果
     */
    public Location getLocation(String mtn, String another, String name){
        Query query = em.createQuery("SELECT l FROM Location l WHERE l.locationName = :locationName AND l.judgeId.yesOrNo = :yesOrNo AND l.judgeId.anotherName = :anotherName");
        query.setParameter("locationName", name).setParameter("yesOrNo", mtn).setParameter("anotherName", another);
        List<Location> result = query.getResultList();
        if (result.isEmpty()) {
            return null;
        }else{
            return result.get(0);
        }
    }
    
    /**
     *
     * @return 所有location结果
     */
    public List<Location> getAllLocation(){
        Query query = em.createNamedQuery("Location.findAll");
        return query.getResultList();
    }
    
    
    
}
