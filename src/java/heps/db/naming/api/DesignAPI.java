 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package heps.db.naming.api;

import heps.db.naming.entity.Accsystem;
import heps.db.naming.entity.Device;
import heps.db.naming.entity.DeviceSubsystemLocation;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author BaiYu
 */
public class DesignAPI {
    
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("heps-db-namingPU");
    EntityManager em = emf.createEntityManager();
    EntityTransaction et = em.getTransaction();
    
    public void init(){
        emf = Persistence.createEntityManagerFactory("heps-db-namingPU");
        em = emf.createEntityManager();
        et = em.getTransaction();
        et.begin();
    }
    
    public void destory(){
        em.close();
        emf.close();
    }
    
    //前端删除数据
    /**
     *
     * @param dslId 删除数据的id
     * @return 
     */
        public Integer deleteDeviceSubsystemLocation(Integer dslId){
        DeviceSubsystemLocation dsl = em.find(DeviceSubsystemLocation.class, dslId);
        em.remove(dsl);
        et.commit();
        return 1;
    }
    
    /**
     *
     * @return 查找到的所有系统代号
     */
    public String queryAllSystems(){
        String re;
        Query query = em.createQuery("SELECT DISTINCT a.systemName FROM Accsystem a ORDER BY a.systemName ASC");
        List l = query.getResultList();
        re = l.toString().substring(1, l.toString().length()-1);
        return re;
    }
    
    /**
     *
     * @return
     */
    public String getSystems() {
        Query query = em.createNamedQuery("Accsystem.findAll");
        List<Accsystem> re = query.getResultList();
        return re.toString();
    }
    
    /**
     *
     * @return 查找到的所有主体分区
     */
    public String queryAllSubsystems(){
        String re;
        String sql = "SELECT distinct naming.subsystem.subsystem_name FROM naming.subsystem order by field(naming.subsystem.subsystem_name,'LB','RB','BR','LA','BS','R') desc";
        Query query = em.createNativeQuery(sql);
//        Query query = em.createQuery("SELECT DISTINCT s.subsystemName FROM Subsystem s");
        List l = query.getResultList();
        re = l.toString().substring(1, l.toString().length()-1);
        return re;
    }
    
    /**
     *
     * @return 查找到的所有位置
     */
    public String queryAllLocations(){
        String re;
        Query query = em.createQuery("SELECT DISTINCT l.locationName FROM Location l");
        List l = query.getResultList();
        re = l.toString().substring(1, l.toString().length()-1);
        return re;
    }
    
    /**
     *
     * @return 查找到的所有设备代号
     */
    public String queryAllDesignNames(){
        String re;
        Query query = em.createQuery("SELECT DISTINCT d.designName FROM Device d ORDER BY d.designName ASC");
        List l = query.getResultList();
        re = l.toString().substring(1, l.toString().length()-1);
        return re;
    }
    
    /**
     *
     * @param sysName 系统代号
     * @return 此系统下，下一个设备序号
     */
    public String queryMaxSeqInDevice(String sysName){
        Integer re;
        Query q = em.createQuery("SELECT DISTINCT a.systemName FROM Accsystem a");
        List l = q.getResultList();
        String temp = l.toString();
        if (temp.contains(sysName)) {
            Query query = em.createQuery("SELECT MAX(d.seqNumber) FROM Device d WHERE d.systemId IN(SELECT a FROM Accsystem a WHERE a.systemName = :systemName)");
            query.setParameter("systemName", sysName);
            Query queryid = em.createQuery("SELECT DISTINCT d.systemId FROM Device d ");
            Query qsysid = em.createQuery("SELECT a.systemId FROM Accsystem a WHERE a.systemName = :systemName");
            qsysid.setParameter("systemName", sysName);
            if (!queryid.getResultList().toString().contains(qsysid.getSingleResult().toString())) {
                re = 1;
            }else{
                re = (Integer) query.getSingleResult();
                re += 1;
            }
            
        }else{
            re = 1;
        }
        return re.toString();
    }
    
    /**
     *
     * @param name 关键字
     * @return 依据关键字查找到的所有设备中文名
     */
    public String queryAllChineseName(String name){
        Query query = em.createQuery("SELECT d FROM Device d WHERE d.chinesename LIKE :param ORDER BY d.designName ASC");
//        Query query = em.createQuery("SELECT new Device(d.chinesename,d.englishname,d.designName) from Device d");
        query.setParameter("param", "%"+name+"%");
        List<Device> re = query.getResultList();
        return re.toString();
    }
    
    /**
     *
     * @param chineseName
     * @return 依据中文名查找到的英文名
     */
    public String queryEnglishNameByChinese(String chineseName) {
        Query query = em.createQuery("SELECT d FROM Device d WHERE d.chinesename = :chinesename");
        query.setParameter("chinesename", chineseName);
        List<Device> re = query.getResultList();
        return re.toString();
    }
    
    /**
     *
     * @param englishName
     * @return 依据英文名查找到的所有设备代号
     */
    public String queryDeviceNameByEnglish(String englishName){
        Query query = em.createQuery("SELECT d FROM Device d WHERE d.englishname = :englishname");
        query.setParameter("englishname", englishName);
        List<Device> re = query.getResultList();
        return re.toString();
    }
    
    /**
     *
     * @param currentPage 当前页码
     * @param pageSize 每页显示数据条数
     * @return 所有数据
     */
    public String queryAll(int currentPage,int pageSize ){
        int firstResult = (currentPage - 1) * pageSize;
        int maxResults = pageSize;
        Query query = em.createQuery("SELECT d FROM DeviceSubsystemLocation d ORDER BY d.deviceId.systemId ASC, d.subsystemId.subsystemName ASC, d.locationId.locationName ASC, d.deviceId.designName ASC");
        List<DeviceSubsystemLocation> re = query.setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
        // System.out.println(re.toString());
        return re.toString();
    }
    
    /**
     *
     * @return 所有数据条数
     */
    public String queryAllCount() {
        Query query = em.createQuery("SELECT COUNT(d) FROM DeviceSubsystemLocation d");
        Long re = (Long) query.getSingleResult();
        // System.out.println(re.toString());
        return re.toString();
    }
    
    /**
     *
     * @param sysname 系统代号
     * @param currentPage 当前页码
     * @param pageSize 每页显示数据条数
     * @return 满足系统代号的查询结果
     */
    public String queryDeviceBySystemAcc(String sysname,int currentPage,int pageSize ){
        int firstResult = (currentPage - 1) * pageSize;
        int maxResults = pageSize;
        String strquery = " SELECT d FROM DeviceSubsystemLocation d WHERE d.deviceId.systemId IN(SELECT a FROM Accsystem a WHERE a.systemName IN) ORDER BY d.deviceId.systemId.systemName ASC, d.subsystemId.subsystemName ASC, d.locationId.locationName ASC, d.deviceId.designName ASC";
        ArrayList sysList = new ArrayList();
        String sysString[] = sysname.split(",");
        String newName = "";
        StringBuffer strBuf = new StringBuffer(strquery);
        int index = strquery.indexOf("e IN");
        for (int i = 0; i < sysString.length; i++) {
            sysList.add("?"+(i+1));    
        }
        newName = sysList.toString().replaceAll("\\[", "\\(").replaceAll("\\]", "\\)");
        StringBuffer newQuery = strBuf.insert((index+4), newName);
        Query query = em.createQuery(newQuery.toString());
        for (int j = 0; j < sysString.length; j++) {
            query.setParameter((j+1), sysString[j]);    
        }
        query.setFirstResult(firstResult).setMaxResults(maxResults);
        List<DeviceSubsystemLocation> re = query.getResultList();
        // System.out.println(re.toString());
        return re.toString();
    }

    /**
     *
     * @param sysname 系统代号
     * @return 此条件下数据的条数
     */
    public String queryDeviceBySystemAccCount(String sysname) {
        String strquery = " SELECT COUNT(d) FROM DeviceSubsystemLocation d WHERE d.deviceId.systemId IN(SELECT a FROM Accsystem a WHERE a.systemName IN)";
        ArrayList sysList = new ArrayList();
        String sysString[] = sysname.split(",");
        String newName = "";
        StringBuffer strBuf = new StringBuffer(strquery);
        int index = strquery.indexOf("e IN");
        for (int i = 0; i < sysString.length; i++) {
            sysList.add("?"+(i+1));    
        }
        newName = sysList.toString().replaceAll("\\[", "\\(").replaceAll("\\]", "\\)");
        StringBuffer newQuery = strBuf.insert((index+4), newName);
        Query query = em.createQuery(newQuery.toString());
        for (int j = 0; j < sysString.length; j++) {
            query.setParameter((j+1), sysString[j]);    
        } 
        Long re = (Long) query.getSingleResult();
        // System.out.println(re.toString());
        return re.toString();
    }
    
    /**
     *
     * @param subname 主体分区
     * @param currentPage 当前页码
     * @param pageSize 每页显示数据条数
     * @return 满足主体分区的查询结果
     */
    public String queryDeviceBySubsystem(String subname,int currentPage,int pageSize){
        int firstResult = (currentPage - 1) * pageSize;
        int maxResults = pageSize;
        Query query = em.createQuery(" SELECT d FROM DeviceSubsystemLocation d WHERE d.subsystemId IN(SELECT s FROM Subsystem s WHERE s.subsystemName = :subsystemName) ORDER BY d.deviceId.systemId.systemName ASC, d.locationId.locationName ASC, d.deviceId.designName ASC");
        query.setParameter("subsystemName", subname).setFirstResult(firstResult).setMaxResults(maxResults);
        List<DeviceSubsystemLocation> re = query.getResultList();
        // System.out.println(re.toString());
        return re.toString();
    }

    /**
     *
     * @param subname 主体分区
     * @return 此条件下的数据条数
     */
    public String queryDeviceBySubsystemCount(String subname) {
        Query query = em.createQuery("SELECT COUNT(d) FROM DeviceSubsystemLocation d WHERE d.subsystemId IN(SELECT s FROM Subsystem s WHERE s.subsystemName = :subsystemName)");
        Long re = (Long) query.setParameter("subsystemName", subname).getSingleResult();
        // System.out.println(re.toString());
        return re.toString();
    }
    
    /**
     *
     * @param devname 设备代号
     * @param currentPage 当前页码
     * @param pageSize 每页显示数据条数
     * @return 满足设备代号的查询结果
     */
    public String queryDeviceByDevname(String devname,int currentPage,int pageSize){
        int firstResult = (currentPage - 1) * pageSize;
        int maxResults = pageSize;
        Query query = em.createQuery("SELECT d FROM DeviceSubsystemLocation d WHERE d.deviceId.designName = :designName ORDER BY d.deviceId.systemId.systemName ASC, d.subsystemId.subsystemName ASC, d.locationId.locationName ASC");
        query.setParameter("designName", devname).setFirstResult(firstResult).setMaxResults(maxResults);
        List<DeviceSubsystemLocation> re = query.getResultList();
        return re.toString();
    }

    /**
     *
     * @param devname 设备代号
     * @return 此条件下的数据条数
     */
    public String queryDeviceByDevnameCount(String devname) {
        Query query = em.createQuery("SELECT COUNT(d) FROM DeviceSubsystemLocation d WHERE d.deviceId.designName = :designName");
        Long re = (Long) query.setParameter("designName", devname).getSingleResult();
        // System.out.println(re.toString());
        return re.toString();
    }
   
    /**
     *
     * @param locname 位置
     * @param currentPage 当前页码
     * @param pageSize 每页显示数据条数
     * @return 满足位置信息的查询结果
     */
    public String queryDeviceByLocation(String locname,int currentPage,int pageSize){
        int firstResult = (currentPage - 1) * pageSize;
        int maxResults = pageSize;
        Query query =em.createQuery("SELECT d FROM DeviceSubsystemLocation d WHERE d.locationId IN (SELECT l FROM Location l WHERE l.locationName = :locationName) ORDER BY d.deviceId.systemId.systemName ASC, d.subsystemId.subsystemName ASC, d.deviceId.designName ASC");
        query.setParameter("locationName", locname).setFirstResult(firstResult).setMaxResults(maxResults);
        List<DeviceSubsystemLocation> re = query.getResultList();
        return re.toString();
    }

    /**
     *
     * @param locname 位置
     * @return 此条件下的数据条数
     */
    public String queryDeviceByLocationCount(String locname) {
        Query query = em.createQuery("SELECT COUNT(d) FROM DeviceSubsystemLocation d WHERE d.locationId IN (SELECT l FROM Location l WHERE l.locationName = :locationName)");
        Long re = (Long) query.setParameter("locationName", locname).getSingleResult();
        // System.out.println(re.toString());
        return re.toString();
    }
    
    /**
     *
     * @param sysname 系统代号
     * @param subname 主体分区
     * @param currentPage 当前页码
     * @param pageSize 每页显示数据条数
     * @return 查询条件为：sysname subname 的结果
     */
    public String queryDeviceBySystemAccSubsystem(String sysname, String subname,int currentPage,int pageSize){
        int firstResult = (currentPage - 1) * pageSize;
       int maxResults = pageSize;
       String strquery = "SELECT d FROM DeviceSubsystemLocation d WHERE d.deviceId.systemId.systemName IN AND d.subsystemId.subsystemName = ?1000 ORDER BY d.deviceId.systemId.systemName ASC, d.locationId.locationName ASC, d.deviceId.designName ASC";
       ArrayList sysList = new ArrayList();
       String sysString[] = sysname.split(",");
       String newName = "";
       StringBuffer strBuf = new StringBuffer(strquery);
       int index = strquery.indexOf("e IN");
       for (int i = 0; i < sysString.length; i++) {
           sysList.add("?"+(i+1));    
       }
       newName = sysList.toString().replaceAll("\\[", "\\(").replaceAll("\\]", "\\)");
       StringBuffer newQuery = strBuf.insert((index+4), newName);
       Query query = em.createQuery(newQuery.toString());
       for (int j = 0; j < sysString.length; j++) {
           query.setParameter((j+1), sysString[j]);    
       }
       query.setParameter(1000, subname).setFirstResult(firstResult).setMaxResults(maxResults);
       List<DeviceSubsystemLocation> re = query.getResultList();
       return re.toString();
    }   

    /**
     *
     * @param sysname 系统代号
     * @param subname 主体分区
     * @return 此查询条件下的数据条数
     */
    public String queryDeviceBySystemAccSubsystemCount(String sysname, String subname) {
        String strquery = "SELECT COUNT(d) FROM DeviceSubsystemLocation d WHERE d.deviceId.systemId.systemName IN AND d.subsystemId.subsystemName = ?1000";
       ArrayList sysList = new ArrayList();
       String sysString[] = sysname.split(",");
       String newName = "";
       StringBuffer strBuf = new StringBuffer(strquery);
       int index = strquery.indexOf("e IN");
       for (int i = 0; i < sysString.length; i++) {
           sysList.add("?"+(i+1));    
       }
       newName = sysList.toString().replaceAll("\\[", "\\(").replaceAll("\\]", "\\)");
       StringBuffer newQuery = strBuf.insert((index+4), newName);
       Query query = em.createQuery(newQuery.toString());
       for (int j = 0; j < sysString.length; j++) {
           query.setParameter((j+1), sysString[j]);    
       } 
       Long re = (Long) query.setParameter(1000, subname).getSingleResult();
       // System.out.println(re.toString());
       return re.toString();
    }
    
    /**
     *
     * @param sysname 系统代号
     * @param devname 设备代号
     * @param currentPage 当前页码
     * @param pageSize 每页显示数据条数
     * @return 查询条件为：sysname devname 的结果
     */
    public String queryDeviceBySystemAccDevname(String sysname, String devname,int currentPage,int pageSize){
       int firstResult = (currentPage - 1) * pageSize;
       int maxResults = pageSize;
       String strquery = "SELECT d FROM DeviceSubsystemLocation d WHERE d.deviceId.systemId.systemName IN AND d.deviceId.designName = ?1000 ORDER BY d.deviceId.systemId.systemName ASC, d.subsystemId.subsystemName ASC, d.locationId.locationName ASC";
       ArrayList sysList = new ArrayList();
       String sysString[] = sysname.split(",");
       String newName = "";
       StringBuffer strBuf = new StringBuffer(strquery);
       int index = strquery.indexOf("e IN");
       for (int i = 0; i < sysString.length; i++) {
           sysList.add("?"+(i+1));    
       }
       newName = sysList.toString().replaceAll("\\[", "\\(").replaceAll("\\]", "\\)");
       StringBuffer newQuery = strBuf.insert((index+4), newName);
       Query query = em.createQuery(newQuery.toString());
       for (int j = 0; j < sysString.length; j++) {
           query.setParameter((j+1), sysString[j]);    
       }
       query.setParameter(1000, devname).setFirstResult(firstResult).setMaxResults(maxResults);
       List<DeviceSubsystemLocation> re = query.getResultList();
       return re.toString();
   }

    /**
     *
     * @param sysname 系统代号
     * @param devname 设备代号
     * @return 此查询条件下的数据条数
     */
    public String queryDeviceBySystemAccDevnameCount(String sysname, String devname) {
       String strquery = "SELECT COUNT(d) FROM DeviceSubsystemLocation d WHERE d.deviceId.systemId.systemName IN AND d.deviceId.designName = ?1000";
       ArrayList sysList = new ArrayList();
       String sysString[] = sysname.split(",");
       String newName = "";
       StringBuffer strBuf = new StringBuffer(strquery);
       int index = strquery.indexOf("e IN");
       for (int i = 0; i < sysString.length; i++) {
           sysList.add("?"+(i+1));    
       }
       newName = sysList.toString().replaceAll("\\[", "\\(").replaceAll("\\]", "\\)");
       StringBuffer newQuery = strBuf.insert((index+4), newName);
       Query query = em.createQuery(newQuery.toString());
       for (int j = 0; j < sysString.length; j++) {
           query.setParameter((j+1), sysString[j]);    
       } 
        Long re = (Long) query.setParameter(1000, devname).getSingleResult();
       // System.out.println(re.toString());
       return re.toString();
   }
   
    /**
     *
     * @param sysname 系统代号
     * @param locname 位置
     * @param currentPage 当前页码
     * @param pageSize 每页显示数据条数
     * @return 查询条件为：sysname locname 的结果
     */
    public String queryDeviceBySystemAccLocation(String sysname, String locname,int currentPage,int pageSize){
       int firstResult = (currentPage - 1) * pageSize;
       int maxResults = pageSize;
       String strquery = "SELECT d FROM DeviceSubsystemLocation d WHERE d.deviceId.systemId.systemName IN AND d.locationId.locationName = ?1000 ORDER BY d.subsystemId.subsystemName ASC, d.deviceId.designName ASC";
       ArrayList sysList = new ArrayList();
       String sysString[] = sysname.split(",");
       String newName = "";
       StringBuffer strBuf = new StringBuffer(strquery);
       int index = strquery.indexOf("e IN");
       for (int i = 0; i < sysString.length; i++) {
           sysList.add("?"+(i+1));    
       }
       newName = sysList.toString().replaceAll("\\[", "\\(").replaceAll("\\]", "\\)");
       StringBuffer newQuery = strBuf.insert((index+4), newName);
       Query query = em.createQuery(newQuery.toString());
       for (int j = 0; j < sysString.length; j++) {
           query.setParameter((j+1), sysString[j]);    
       }
       query.setParameter(1000, locname).setFirstResult(firstResult).setMaxResults(maxResults);
       List<DeviceSubsystemLocation> re = query.getResultList();
       return re.toString();
   } 

    /**
     *
     * @param sysname 系统代号
     * @param locname 位置
     * @return 此查询条件下的数据条数
     */
    public String queryDeviceBySystemAccLocationCount(String sysname, String locname) {
       String strquery = "SELECT COUNT(d) FROM DeviceSubsystemLocation d WHERE d.deviceId.systemId.systemName IN AND d.locationId.locationName = ?1000";
       ArrayList sysList = new ArrayList();
       String sysString[] = sysname.split(",");
       String newName = "";
       StringBuffer strBuf = new StringBuffer(strquery);
       int index = strquery.indexOf("e IN");
       for (int i = 0; i < sysString.length; i++) {
           sysList.add("?"+(i+1));    
       }
       newName = sysList.toString().replaceAll("\\[", "\\(").replaceAll("\\]", "\\)");
       StringBuffer newQuery = strBuf.insert((index+4), newName);
       Query query = em.createQuery(newQuery.toString());
       for (int j = 0; j < sysString.length; j++) {
           query.setParameter((j+1), sysString[j]);    
       }
       Long re = (Long) query.setParameter(1000, locname).getSingleResult();
       // System.out.println(re.toString());
       return re.toString();
   }
   
    /**
     *
     * @param subname 主体分区
     * @param devname 设备代号
     * @param currentPage 当前页码
     * @param pageSize 每页显示数据条数
     * @return 查询条件为：subname devname 的结果
     */
    public String queryDeviceBySubsystemDevname(String subname, String devname,int currentPage,int pageSize){
       int firstResult = (currentPage - 1) * pageSize;
       int maxResults = pageSize;
       Query query = em.createQuery("SELECT d FROM DeviceSubsystemLocation d WHERE d.subsystemId.subsystemName = :subsystemName AND d.deviceId.designName = :designName ORDER BY d.deviceId.systemId.systemName ASC, d.locationId.locationName ASC");
       query.setParameter("subsystemName", subname).setParameter("designName", devname).setFirstResult(firstResult).setMaxResults(maxResults);
       List<DeviceSubsystemLocation> re = query.getResultList();
       return re.toString();
   }

    /**
     *
     * @param subname 主体分区
     * @param devname 设备代号
     * @return 此查询条件下的数据条数
     */
    public String queryDeviceBySubsystemDevnameCount(String subname, String devname) {
       Query query = em.createQuery("SELECT COUNT(d) FROM DeviceSubsystemLocation d WHERE d.subsystemId.subsystemName = :subsystemName AND d.deviceId.designName = :designName");
       Long re = (Long) query.setParameter("subsystemName", subname).setParameter("designName", devname).getSingleResult();
       // System.out.println(re.toString());
       return re.toString();
   }
   
    /**
     *
     * @param subname 主体分区
     * @param locname 位置
     * @param currentPage 当前页码
     * @param pageSize 每页显示条数
     * @return 查询条件为：subname locname 的结果
     */
    public String queryDeviceBySubsystemLocation(String subname, String locname,int currentPage,int pageSize){
       int firstResult = (currentPage - 1) * pageSize;
       int maxResults = pageSize;
       Query query = em.createQuery("SELECT d FROM DeviceSubsystemLocation d WHERE d.subsystemId.subsystemName = :subsystemName AND d.locationId.locationName = :locationName ORDER BY d.deviceId.systemId.systemName ASC, d.deviceId.designName ASC");
       query.setParameter("subsystemName", subname).setParameter("locationName", locname).setFirstResult(firstResult).setMaxResults(maxResults);
       List<DeviceSubsystemLocation> re = query.getResultList();
       return re.toString();
   }

    /**
     *
     * @param subname 主体分区
     * @param locname 位置
     * @return 此查询条件下的数据条数
     */
    public String queryDeviceBySubsystemLocationCount(String subname, String locname) {
       Query query = em.createQuery("SELECT COUNT(d) FROM DeviceSubsystemLocation d WHERE d.subsystemId.subsystemName = :subsystemName AND d.locationId.locationName = :locationName");
       Long re = (Long) query.setParameter("subsystemName", subname).setParameter("locationName", locname).getSingleResult();
       // System.out.println(re.toString());
       return re.toString();
   }
   
    /**
     *
     * @param devname 设备代号
     * @param locname 位置
     * @param currentPage 当前页码
     * @param pageSize 每页显示条数
     * @return 查询条件为：devname locname 的结果
     */
    public String queryDeviceByDevnameLocation(String devname, String locname,int currentPage,int pageSize){
       int firstResult = (currentPage - 1) * pageSize;
       int maxResults = pageSize;
       Query query = em.createQuery("SELECT d FROM DeviceSubsystemLocation d WHERE d.deviceId.designName = :designName AND d.locationId.locationName = :locationName ORDER BY d.deviceId.systemId.systemName ASC, d.subsystemId.subsystemName ASC");
       query.setParameter("designName", devname).setParameter("locationName", locname).setFirstResult(firstResult).setMaxResults(maxResults);
       List<DeviceSubsystemLocation> re = query.getResultList();
       return re.toString();
   }

    /**
     *
     * @param devname 设备代号
     * @param locname 位置
     * @return 此查询条件下的数据条数
     */
    public String queryDeviceByDevnameLocationCount(String devname, String locname) {
       Query query = em.createQuery("SELECT COUNT(d) FROM DeviceSubsystemLocation d WHERE d.deviceId.designName = :designName AND d.locationId.locationName = :locationName");
       Long re = (Long) query.setParameter("designName", devname).setParameter("locationName", locname).getSingleResult();
       // System.out.println(re.toString());
       return re.toString();
   }
   
    /**
     *
     * @param sysname 系统代号
     * @param subname 主体分区
     * @param devname 设备代号
     * @param currentPage 当前页码
     * @param pageSize 每页显示条数
     * @return 查询条件为：sysname subname devname 的结果
     */
    public String querySystemAccSubsystemDevname(String sysname, String subname, String devname,int currentPage,int pageSize){
       int firstResult = (currentPage - 1) * pageSize;
       int maxResults = pageSize;
       String strquery = "SELECT d FROM DeviceSubsystemLocation d WHERE d.deviceId.systemId.systemName IN AND d.subsystemId.subsystemName = ?1000 AND d.deviceId.designName = ?1001 ORDER BY d.deviceId.systemId.systemName ASC, d.locationId.locationName ASC";
       ArrayList sysList = new ArrayList();
       String sysString[] = sysname.split(",");
       String newName = "";
       StringBuffer strBuf = new StringBuffer(strquery);
       int index = strquery.indexOf("e IN");
       for (int i = 0; i < sysString.length; i++) {
           sysList.add("?"+(i+1));    
       }
       newName = sysList.toString().replaceAll("\\[", "\\(").replaceAll("\\]", "\\)");
       StringBuffer newQuery = strBuf.insert((index+4), newName);
       Query query = em.createQuery(newQuery.toString());
       for (int j = 0; j < sysString.length; j++) {
           query.setParameter((j+1), sysString[j]);    
       }
       query.setParameter(1000, subname).setParameter(1001, devname).setFirstResult(firstResult).setMaxResults(maxResults);
       List<DeviceSubsystemLocation> re = query.getResultList();
       return re.toString();
   }

    /**
     *
     * @param sysname 系统代号
     * @param subname 主体分区
     * @param devname 设备代号
     * @return 此查询条件下的数据条数
     */
    public String querySystemAccSubsystemDevnameCount(String sysname, String subname, String devname) {
       String strquery = "SELECT COUNT(d) FROM DeviceSubsystemLocation d WHERE d.deviceId.systemId.systemName IN AND d.subsystemId.subsystemName = ?1000 AND d.deviceId.designName = ?1001";
       ArrayList sysList = new ArrayList();
       String sysString[] = sysname.split(",");
       String newName = "";
       StringBuffer strBuf = new StringBuffer(strquery);
       int index = strquery.indexOf("e IN");
       for (int i = 0; i < sysString.length; i++) {
           sysList.add("?"+(i+1));    
       }
       newName = sysList.toString().replaceAll("\\[", "\\(").replaceAll("\\]", "\\)");
       StringBuffer newQuery = strBuf.insert((index+4), newName);
       Query query = em.createQuery(newQuery.toString());
       for (int j = 0; j < sysString.length; j++) {
           query.setParameter((j+1), sysString[j]);    
       } 
       Long re = (Long) query.setParameter(1000, subname).setParameter(1001, devname).getSingleResult();
       // System.out.println(re.toString());
       return re.toString();
   }
   
    /**
     *
     * @param sysname 系统代号
     * @param subname 主体分区
     * @param locname 位置
     * @param currentPage 当前页码
     * @param pageSize 每页显示条数
     * @return 查询条件为：sysname subname locname 的结果
     */
    public String querySystemAccSubsystemLocation(String sysname, String subname, String locname,int currentPage,int pageSize){
       int firstResult = (currentPage - 1) * pageSize;
       int maxResults = pageSize;
       String strquery = "SELECT d FROM DeviceSubsystemLocation d WHERE d.deviceId.systemId.systemName IN AND d.subsystemId.subsystemName = ?1000 AND d.locationId.locationName = ?1001 ORDER BY d.deviceId.systemId.systemName ASC, d.deviceId.designName ASC";
       ArrayList sysList = new ArrayList();
       String sysString[] = sysname.split(",");
       String newName = "";
       StringBuffer strBuf = new StringBuffer(strquery);
       int index = strquery.indexOf("e IN");
       for (int i = 0; i < sysString.length; i++) {
           sysList.add("?"+(i+1));    
       }
       newName = sysList.toString().replaceAll("\\[", "\\(").replaceAll("\\]", "\\)");
       StringBuffer newQuery = strBuf.insert((index+4), newName);
       Query query = em.createQuery(newQuery.toString());
       for (int j = 0; j < sysString.length; j++) {
           query.setParameter((j+1), sysString[j]);    
       }
       query.setParameter(1000, subname).setParameter(1001, locname).setFirstResult(firstResult).setMaxResults(maxResults);
       List<DeviceSubsystemLocation> re = query.getResultList();
       return re.toString();
   }

    /**
     *
     * @param sysname 系统代号
     * @param subname 主体分区
     * @param locname 位置
     * @return 此查询条件下的数据条数
     */
    public String querySystemAccSubsystemLocationCount(String sysname, String subname, String locname) {
       String strquery = "SELECT COUNT(d) FROM DeviceSubsystemLocation d WHERE d.deviceId.systemId.systemName IN AND d.subsystemId.subsystemName = ?1000 AND d.locationId.locationName = ?1001";
       ArrayList sysList = new ArrayList();
       String sysString[] = sysname.split(",");
       String newName = "";
       StringBuffer strBuf = new StringBuffer(strquery);
       int index = strquery.indexOf("e IN");
       for (int i = 0; i < sysString.length; i++) {
           sysList.add("?"+(i+1));    
       }
       newName = sysList.toString().replaceAll("\\[", "\\(").replaceAll("\\]", "\\)");
       StringBuffer newQuery = strBuf.insert((index+4), newName);
       Query query = em.createQuery(newQuery.toString());
       for (int j = 0; j < sysString.length; j++) {
           query.setParameter((j+1), sysString[j]);    
       } 
       Long re = (Long) query.setParameter(1000, subname).setParameter(1001, locname).getSingleResult();
       // System.out.println(re.toString());
       return re.toString();
   }
   
    /**
     *
     * @param subname 主体分区
     * @param devname 设备代号
     * @param locname 位置
     * @param currentPage 当前页码
     * @param pageSize 每页显示数据条数 
     * @return 查询条件为：subname devname locname 的结果
     */
    public String querySubsystemDevnameLocation(String subname, String devname, String locname,int currentPage,int pageSize){
       int firstResult = (currentPage - 1) * pageSize;
       int maxResults = pageSize;
       Query query = em.createQuery("SELECT d FROM DeviceSubsystemLocation d WHERE d.subsystemId.subsystemName = :subsystemName AND d.deviceId.designName = :designName AND d.locationId.locationName = :locationName ORDER BY d.deviceId.systemId.systemName ASC");
       query.setParameter("subsystemName", subname).setParameter("designName", devname).setParameter("locationName", locname).setFirstResult(firstResult).setMaxResults(maxResults);
       List<DeviceSubsystemLocation> re = query.getResultList();
       return re.toString();
   }

    /**
     *
     * @param subname 主体分区
     * @param devname 设备代号
     * @param locname 位置
     * @return 此查询条件下的数据条数
     */
    public String querySubsystemDevnameLocationCount(String subname, String devname, String locname) {
       Query query = em.createQuery("SELECT COUNT(d) FROM DeviceSubsystemLocation d WHERE d.subsystemId.subsystemName = :subsystemName AND d.deviceId.designName = :designName AND d.locationId.locationName = :locationName");
       Long re = (Long) query.setParameter("subsystemName", subname).setParameter("designName", devname).setParameter("locationName", locname).getSingleResult();
       // System.out.println(re.toString());
       return re.toString();
   }
   
    /**
     *
     * @param sysname 系统代号
     * @param devname 设备代号
     * @param locname 位置
     * @param currentPage 当前页码
     * @param pageSize 每页显示条数
     * @return 查询条件为：sysname devname locname 的结果
     */
    public  String querySystemAccDevnameLocation(String sysname, String devname, String locname,int currentPage,int pageSize){
       int firstResult = (currentPage - 1) * pageSize;
       int maxResults = pageSize;
       String strquery = "SELECT d FROM DeviceSubsystemLocation d WHERE d.deviceId.systemId.systemName IN AND d.deviceId.designName = ?1000 AND d.locationId.locationName = ?1001 ORDER BY d.deviceId.systemId.systemName ASC, d.subsystemId.subsystemName ASC";
       ArrayList sysList = new ArrayList();
       String sysString[] = sysname.split(",");
       String newName = "";
       StringBuffer strBuf = new StringBuffer(strquery);
       int index = strquery.indexOf("e IN");
       for (int i = 0; i < sysString.length; i++) {
           sysList.add("?"+(i+1));    
       }
       newName = sysList.toString().replaceAll("\\[", "\\(").replaceAll("\\]", "\\)");
       StringBuffer newQuery = strBuf.insert((index+4), newName);
       Query query = em.createQuery(newQuery.toString());
       for (int j = 0; j < sysString.length; j++) {
           query.setParameter((j+1), sysString[j]);    
       }
       query.setParameter(1000, devname).setParameter(1001, locname).setFirstResult(firstResult).setMaxResults(maxResults);
       List<DeviceSubsystemLocation> re = query.getResultList();
       return re.toString();
   }

    /**
     *
     * @param sysname 系统代号
     * @param devname 设备代号
     * @param locname 位置
     * @return 此查询条件下的数据条数
     */
    public String querySystemAccDevnameLocationCount(String sysname, String devname, String locname) {
       String strquery = "SELECT COUNT(d) FROM DeviceSubsystemLocation d WHERE d.deviceId.systemId.systemName IN AND d.deviceId.designName = ?1000 AND d.locationId.locationName = ?1001";
       ArrayList sysList = new ArrayList();
       String sysString[] = sysname.split(",");
       String newName = "";
       StringBuffer strBuf = new StringBuffer(strquery);
       int index = strquery.indexOf("e IN");
       for (int i = 0; i < sysString.length; i++) {
           sysList.add("?"+(i+1));    
       }
       newName = sysList.toString().replaceAll("\\[", "\\(").replaceAll("\\]", "\\)");
       StringBuffer newQuery = strBuf.insert((index+4), newName);
       Query query = em.createQuery(newQuery.toString());
       for (int j = 0; j < sysString.length; j++) {
           query.setParameter((j+1), sysString[j]);    
       } 
       Long re = (Long) query.setParameter(1000, devname).setParameter(1001, locname).getSingleResult();
       // System.out.println(re.toString());
       return re.toString();
   }
   
    /**
     *
     * @param sysname 系统代号
     * @param subname 主体分区 
     * @param devname 设备代号
     * @param locname 位置
     * @param currentPage 当前页码
     * @param pageSize 每页显示条数
     * @return 查询条件为：sysname subname devname locname 的结果
     */
    public String querySystemAccSubsystemDevnameLocation(String sysname, String subname, String devname, String locname,int currentPage,int pageSize){
       int firstResult = (currentPage - 1) * pageSize;
       int maxResults = pageSize;
       String strquery = "SELECT d FROM DeviceSubsystemLocation d WHERE d.deviceId.systemId.systemName IN AND d.subsystemId.subsystemName = ?1000 AND d.deviceId.designName = ?1001 AND d.locationId.locationName = ?1002 ORDER BY d.deviceId.systemId.systemName ASC";
       ArrayList sysList = new ArrayList();
       String sysString[] = sysname.split(",");
       String newName = "";
       StringBuffer strBuf = new StringBuffer(strquery);
       int index = strquery.indexOf("e IN");
       for (int i = 0; i < sysString.length; i++) {
           sysList.add("?"+(i+1));    
       }
       newName = sysList.toString().replaceAll("\\[", "\\(").replaceAll("\\]", "\\)");
       StringBuffer newQuery = strBuf.insert((index+4), newName);
       Query query = em.createQuery(newQuery.toString());
       for (int j = 0; j < sysString.length; j++) {
           query.setParameter((j+1), sysString[j]);    
       }
       query.setParameter(1000, subname).setParameter(1001, devname).setParameter(1002, locname).setFirstResult(firstResult).setMaxResults(maxResults);
       List<DeviceSubsystemLocation> re = query.getResultList();
       return re.toString();
   }

    /**
     *
     * @param sysname 系统代号
     * @param subname 主体分区
     * @param devname 设备代号
     * @param locname 位置
     * @return 此查询条件下的数据条数
     */
    public String querySystemAccSubsystemDevnameLocationCount(String sysname, String subname, String devname, String locname) {
       String strquery = "SELECT COUNT(d) FROM DeviceSubsystemLocation d WHERE d.deviceId.systemId.systemName IN AND d.subsystemId.subsystemName = ?1000 AND d.deviceId.designName = ?1001 AND d.locationId.locationName = ?1002";
       ArrayList sysList = new ArrayList();
       String sysString[] = sysname.split(",");
       String newName = "";
       StringBuffer strBuf = new StringBuffer(strquery);
       int index = strquery.indexOf("e IN");
       for (int i = 0; i < sysString.length; i++) {
           sysList.add("?"+(i+1));    
       }
       newName = sysList.toString().replaceAll("\\[", "\\(").replaceAll("\\]", "\\)");
       StringBuffer newQuery = strBuf.insert((index+4), newName);
       Query query = em.createQuery(newQuery.toString());
       for (int j = 0; j < sysString.length; j++) {
           query.setParameter((j+1), sysString[j]);    
       }
       Long re = (Long) query.setParameter(1000, subname).setParameter(1001, devname).setParameter(1002, locname).getSingleResult();
       // System.out.println(re.toString());
       return re.toString();
   }
   
    /**
     *
     * @param deviceId
     * @return
     */
    public Integer deleteDeviceById(Integer deviceId){
       Device device = em.find(Device.class, deviceId);
       em.remove(device);
       et.commit();
       return 1;
   }
    
}
