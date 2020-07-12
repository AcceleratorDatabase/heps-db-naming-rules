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
import heps.db.naming.entity.Device;
import heps.db.naming.entity.MoreThanNine;

/**
 *
 * @author BaiYu
 */
public class MoreThanNineAPI{
    
    /**
     *创建EntityManager
     */
    public static EntityManager em = EmProvider.getInstance().getEntityManagerFactory().createEntityManager();
    
    /**
     *
     * @param judge 持久化实体，往数据表中插入的数据
     * @param anotherName
     */
    public void setMoreThanNine(String judge,String anotherName){
        MoreThanNine mtn = new MoreThanNine();
        mtn.setYesOrNo(judge);
        mtn.setAnotherName(anotherName);
        em.getTransaction().begin();
        em.persist(mtn);
        em.getTransaction().commit();
    }
    
    /**
     *
     * @param judge
     * @return 此条件下查询结果
     */
    public MoreThanNine getMoreThanNineByYON(String judge){
        if(judge==null||("".equals(judge))) return null;
        Query query = em.createNamedQuery("MoreThanNine.findByYesOrNo");
        query.setParameter("yesOrNo", judge);
        List<MoreThanNine> result = query.getResultList();
        if (result.isEmpty()) {
            return null;
        }else{
            return result.get(0);
        }
    }
    
    /**
     *
     * @return 所有MoreThanNine结果
     */
    public List<MoreThanNine> getAllMoreThanNine(){
        Query query = em.createNamedQuery("MoreThanNine.findAll");
        return query.getResultList();
    }
    
    /**
     *
     * @param judge
     * @param another
     * @return 此条件下查询结果
     */
    public MoreThanNine queryByYesOrNo(String judge,String another){
        if(judge==null||("".equals(judge))) return null;
        Query query = em.createQuery("SELECT m FROM MoreThanNine m WHERE m.yesOrNo = :yesOrNo AND m.anotherName = :anotherName");
        query.setParameter("yesOrNo", judge).setParameter("anotherName", another);
        List<MoreThanNine> result = query.getResultList();
        if (result.isEmpty()) {
            return null;
        }else{
            return result.get(0);
        }
    }
    
    /**
     *
     * @param judgeId 删除数据的id
     * @return
     */
    public Integer deleteMoreThanNineById(Integer judgeId){
        MoreThanNine mtn = em.find(MoreThanNine.class, judgeId);
        em.remove(mtn);
        em.getTransaction().commit();
        return 1;
    }
    
}
