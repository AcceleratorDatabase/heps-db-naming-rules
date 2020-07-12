/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package heps.db.naming.api;

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
public class NoPaginationAPI {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("heps-db-namingPU");
    EntityManager em = emf.createEntityManager();
    EntityTransaction et = em.getTransaction();

    public void init(){
        emf = Persistence.createEntityManagerFactory("heps-db-namingPU");
        em = emf.createEntityManager();
        et = em.getTransaction();
        et.begin();
    }
//    public static EntityManager em = EmProvider.getInstance().getEntityManagerFactory().createEntityManager();
    public void destory(){
        em.close();
        emf.close();
    }
    
    /**
     *
     * @return 所有数据
     */
    public String queryAll(){
        Query query = em.createQuery("SELECT d FROM DeviceSubsystemLocation d ORDER BY d.deviceId.systemId.systemName ASC, d.subsystemId.subsystemName ASC, d.locationId.locationName ASC, d.deviceId.designName ASC");
        List<DeviceSubsystemLocation> re = query.getResultList();
        // System.out.println(re.toString());
        return re.toString();
    }

    /**
     *
     * @param sysname 系统代号
     * @return 此条件下查询结果
     */
    public String queryDeviceBySystemAcc(String sysname){
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
        List<DeviceSubsystemLocation> re = query.getResultList();
        // System.out.println(re.toString());
        return re.toString();
    }
    
    /**
     *
     * @param subname 主体分区
     * @return 此条件下查询结果
     */
    public String queryDeviceBySubsystem(String subname){
        Query query = em.createQuery(" SELECT d FROM DeviceSubsystemLocation d WHERE d.subsystemId IN(SELECT s FROM Subsystem s WHERE s.subsystemName = :subsystemName) ORDER BY d.deviceId.systemId.systemName ASC, d.locationId.locationName ASC, d.deviceId.designName ASC");
        query.setParameter("subsystemName", subname);
        List<DeviceSubsystemLocation> re = query.getResultList();
        // System.out.println(re.toString());
        return re.toString();
    }
   
    /**
     *
     * @param devname 设备代号
     * @return 此条件下查询结果
     */
    public String queryDeviceByDevname(String devname){
        Query query = em.createQuery("SELECT d FROM DeviceSubsystemLocation d WHERE d.deviceId.designName = :designName ORDER BY d.deviceId.systemId.systemName ASC, d.subsystemId.subsystemName ASC, d.locationId.locationName ASC");
        query.setParameter("designName", devname);
        List<DeviceSubsystemLocation> re = query.getResultList();
        return re.toString();
    }
    
    /**
     *
     * @param locname 位置
     * @return 此条件下查询结果
     */
    public String queryDeviceByLocation(String locname){
        Query query =em.createQuery("SELECT d FROM DeviceSubsystemLocation d WHERE d.locationId IN (SELECT l FROM Location l WHERE l.locationName = :locationName) ORDER BY d.deviceId.systemId.systemName ASC, d.subsystemId.subsystemName ASC, d.deviceId.designName ASC");
        query.setParameter("locationName", locname);
        List<DeviceSubsystemLocation> re = query.getResultList();
        return re.toString();
    }
    
    /**
     *
     * @param sysname 系统代号
     * @param subname 主体分区
     * @return 此条件下查询结果
     */
    public String queryDeviceBySystemAccSubsystem(String sysname, String subname){
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
        query.setParameter(1000, subname);
        List<DeviceSubsystemLocation> re = query.getResultList();
        return re.toString();
    }   
    
    /**
     *
     * @param sysname 系统代号
     * @param devname 设备代号
     * @return 此条件下查询结果
     */
    public String queryDeviceBySystemAccDevname(String sysname, String devname){
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
        query.setParameter(1000, devname);
        List<DeviceSubsystemLocation> re = query.getResultList();
        return re.toString();
    }
    
    /**
     *
     * @param sysname 系统代号
     * @param locname 位置信息
     * @return 此条件下查询结果
     */
    public String queryDeviceBySystemAccLocation(String sysname, String locname){
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
        query.setParameter(1000, locname);
        List<DeviceSubsystemLocation> re = query.getResultList();
        return re.toString();
    } 
    
    /**
     *
     * @param subname 主体分区
     * @param devname 设备代号
     * @return 此条件下查询结果
     */
    public String queryDeviceBySubsystemDevname(String subname, String devname){
        Query query = em.createQuery("SELECT d FROM DeviceSubsystemLocation d WHERE d.subsystemId.subsystemName = :subsystemName AND d.deviceId.designName = :designName ORDER BY d.deviceId.systemId.systemName ASC, d.locationId.locationName ASC");
        query.setParameter("subsystemName", subname).setParameter("designName", devname);
        List<DeviceSubsystemLocation> re = query.getResultList();
        return re.toString();
    }
   
    /**
     *
     * @param subname 主体分区
     * @param locname 位置
     * @return 此条件下查询结果
     */
    public String queryDeviceBySubsystemLocation(String subname, String locname){
        Query query = em.createQuery("SELECT d FROM DeviceSubsystemLocation d WHERE d.subsystemId.subsystemName = :subsystemName AND d.locationId.locationName = :locationName ORDER BY d.deviceId.systemId.systemName ASC, d.deviceId.designName ASC");
        query.setParameter("subsystemName", subname).setParameter("locationName", locname);
        List<DeviceSubsystemLocation> re = query.getResultList();
        return re.toString();
    }
    
    /**
     *
     * @param devname 设备代号
     * @param locname 位置
     * @return 此条件下查询结果
     */
    public String queryDeviceByDevnameLocation(String devname, String locname){
        Query query = em.createQuery("SELECT d FROM DeviceSubsystemLocation d WHERE d.deviceId.designName = :designName AND d.locationId.locationName = :locationName ORDER BY d.deviceId.systemId.systemName ASC, d.subsystemId.subsystemName ASC");
        query.setParameter("designName", devname).setParameter("locationName", locname);
        List<DeviceSubsystemLocation> re = query.getResultList();
        return re.toString();
    }
    
    /**
     *
     * @param sysname 系统代号
     * @param subname 主体分区
     * @param devname 设备代号
     * @return 此条件下查询结果
     */
    public String querySystemAccSubsystemDevname(String sysname, String subname, String devname){
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
        query.setParameter(1000, subname).setParameter(1001, devname);
        List<DeviceSubsystemLocation> re = query.getResultList();
        return re.toString();
    }
    
    /**
     *
     * @param sysname 系统代号
     * @param subname 主体分区
     * @param locname 位置
     * @return 此条件下查询结果
     */
    public String querySystemAccSubsystemLocation(String sysname, String subname, String locname){
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
        query.setParameter(1000, subname).setParameter(1001, locname);
        List<DeviceSubsystemLocation> re = query.getResultList();
        return re.toString();
    }
    
    /**
     *
     * @param subname 主体分区
     * @param devname 设备代号
     * @param locname 位置
     * @return 此条件下查询结果
     */
    public String querySubsystemDevnameLocation(String subname, String devname, String locname){
        Query query = em.createQuery("SELECT d FROM DeviceSubsystemLocation d WHERE d.subsystemId.subsystemName = :subsystemName AND d.deviceId.designName = :designName AND d.locationId.locationName = :locationName ORDER BY d.deviceId.systemId.systemName ASC");
        query.setParameter("subsystemName", subname).setParameter("designName", devname).setParameter("locationName", locname);
        List<DeviceSubsystemLocation> re = query.getResultList();
        return re.toString();
    }
    
    /**
     *
     * @param sysname 系统代号
     * @param devname 设备代号
     * @param locname 位置
     * @return 此条件下查询结果
     */
    public  String querySystemAccDevnameLocation(String sysname, String devname, String locname){
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
        query.setParameter(1000, devname).setParameter(1001, locname);
        List<DeviceSubsystemLocation> re = query.getResultList();
        return re.toString();
    }
    
    /**
     *
     * @param sysname 系统代号
     * @param subname 主体分区
     * @param devname 设备代号
     * @param locname 位置
     * @return 此条件下查询结果
     */
    public String querySystemAccSubsystemDevnameLocation(String sysname, String subname, String devname, String locname){
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
        query.setParameter(1000, subname).setParameter(1001, devname).setParameter(1002, locname);
        List<DeviceSubsystemLocation> re = query.getResultList();
        return re.toString();
    }
    
}
