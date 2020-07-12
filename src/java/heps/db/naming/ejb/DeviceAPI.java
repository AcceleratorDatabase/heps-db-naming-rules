/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package heps.db.naming.ejb;

import heps.db.naming.common.tools.EmProvider;
import heps.db.naming.entity.Device;
import heps.db.naming.entity.Location;
import heps.db.naming.entity.MoreThanNine;
import heps.db.naming.entity.Subsystem;
import heps.db.naming.entity.Accsystem;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author BaiYu
 */

public class DeviceAPI {

    /**
     *创建EntityManager
     */
    public static EntityManager em = EmProvider.getInstance().getEntityManagerFactory().createEntityManager();
    
    /**
     *
     * @return 全部device结果
     */
    public List<Device> getAllDevice(){
        Query query = em.createNamedQuery("Device.findAll");
        return query.getResultList();
    }
    
    /**
     *
     * @param system 持久化实体，往数据表中插入的数据
     * @param ChineseName 同上
     * @param EnglishName 同上
     * @param designName 同上
     * @param remarks 同上
     */
    public void setDevice(Accsystem system, String ChineseName, String EnglishName, String designName, String remarks){
        Device d = new Device();
        d.setSystemId(system);
//        d.setSeqNumber(Integer.parseInt(seqNumber));
        d.setEnglishname(EnglishName);
        d.setChinesename(ChineseName);
        d.setDesignName(designName);
        d.setRemark(remarks);
        
        em.getTransaction().begin();
        em.persist(d);
        em.getTransaction().commit();
    }
    
    /**
     *
     * @param device 持久化实体，往数据表中插入的数据
     */
    public void setDevice(Device device){
        em.getTransaction().begin();
        em.persist(device);
        em.getTransaction().commit();
    }
    
    /**
     *
     * @param newDevice 往数据表中更新的数据
     */
    public void updateDevice(Device newDevice){
        em.getTransaction().begin();
        em.persist(newDevice);
        em.getTransaction().commit();
    }
    
    /**
     *
     * @param deviceId 删除数据的id
     * @return
     */
    public Integer deleteDeviceById(Integer deviceId){
        Device d = em.find(Device.class, deviceId);
        em.remove(d);
        em.getTransaction().commit();
        return 1;
    }
    
    /**
     *
     * @param deviceId 
     * @return 由id查找的结果
     */
    public Device findById(Integer deviceId){
        Query query = em.createNamedQuery("Device.findByDeviceId");
        query.setParameter("deviceId", deviceId);
        List<Device> result = query.getResultList();
        if (result.isEmpty() || result == null) {
            return null;
        }else{
            Iterator it = result.iterator();
            while(it.hasNext()){
                Device device = (Device) it.next();
                return device;
            }
        }
        return null;
    }
    
    /**
     *
     * @param system
     * @param ChineseName
     * @param EnglishName
     * @param designName
     * @param remarks
     * @return 此条件下的查询结果
     */
    public Device getDeviceBy(String system, String ChineseName, String EnglishName, String designName, String remarks) {
        Query query = em.createQuery("SELECT d FROM Device d WHERE d.systemId.systemName = :systemName AND d.chinesename = :chinesename AND d.englishname = :englishname "
                + "AND d.designName = :designName AND d.remark = :remark");
        query.setParameter("systemName", system).setParameter("chinesename", ChineseName).setParameter("englishname", EnglishName).setParameter("designName", designName).setParameter("remark", remarks);
        List<Device> re = query.getResultList();
        if (re.isEmpty()) {
            return null;
        } else {
            return re.get(0);
        }
    }
}
