/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package heps.db.naming.excel;


import heps.db.naming.api.InsertAPI;
import heps.db.naming.ejb.AccSystemAPI;
import heps.db.naming.ejb.DeviceAPI;
import heps.db.naming.ejb.DeviceSubsystemLocationAPI;
import heps.db.naming.ejb.LocationAPI;
import heps.db.naming.ejb.MoreThanNineAPI;
import heps.db.naming.ejb.SubsystemAPI;
import heps.db.naming.entity.Accsystem;
import heps.db.naming.entity.Device;
import heps.db.naming.entity.DeviceSubsystemLocation;
import heps.db.naming.entity.Location;
import heps.db.naming.entity.MoreThanNine;
import heps.db.naming.entity.Subsystem;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *将数据插入到数据库中
 * @author BaiYu
 */
public class DataInsertDB {
    ArrayList dataList;
    File file;
    
    public DataInsertDB(ArrayList dataList, File file) {
        this.dataList = dataList;
        this.file = file;
    }

    public DataInsertDB(File file) {
        this.dataList = null;
        this.file = file;
    }

    public DataInsertDB() {
        this.dataList = null;
        this.file = new File("");
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public ArrayList getDataList() {
        return dataList;
    }

    public void setDataList(ArrayList dataList) {
        this.dataList = dataList;
    }
    
    /**
     *插入所有系统代号
     */
    public void systemAccDataInsertDB() {
        int col = new ListTool(this.getDataList()).getColNum("系统代号");
        ArrayList systemData = new ArrayList();
        Iterator it = this.getDataList().iterator();
        while (it.hasNext()) {
            ArrayList a = (ArrayList) it.next();
            systemData.add(a.get(col));
        }
        Iterator it1 = systemData.iterator();
        while (it1.hasNext()) {
            String s = (String) it1.next();
            if (s != null && "" != s) {
                if (!(s.toLowerCase().contains("系统代号") || s.equals("系统代号"))) {
                    AccSystemAPI sf = new AccSystemAPI();
                    if (sf.getSystem(s) == null) {
                        sf.setSystem(s);
                    }
                }
            }
        }
    }
    
    /**
     *插入所有主体分区
     */
    public void subsystemDataInsertDB(){
        int col = new ListTool(this.getDataList()).getColNum("所在主体分区");
        ArrayList subsystemData = new ArrayList();
        Iterator it = this.getDataList().iterator();
        while (it.hasNext()) {
            ArrayList a = (ArrayList) it.next();
            subsystemData.add(a.get(col));
        }
        Iterator it1 = subsystemData.iterator();
        while (it1.hasNext()) {
            String s = (String) it1.next();
            if (s != null && "" != s) {
                if (!(s.toLowerCase().contains("所在主体分区") || s.equals("所在主体分区"))) {
                    SubsystemAPI sf = new SubsystemAPI();
                    if (sf.getSubsystem(s) == null) {
                        sf.setSubsystem(s);
                    }
        
                }
            }
        }
    }
    
    /**
     *插入所有Yes/No及别名
     */
    public void moreThanNineDataInsertDB(){
        int yonCol = new ListTool(this.getDataList()).getColNum("一个周期单元内的最大台套数>9");
        int anotherCol = new ListTool(this.getDataList()).getColNum("别名");
        ArrayList<MoreThanNine> moreThanNineData = new ArrayList();
        Iterator it = this.getDataList().iterator();
        while (it.hasNext()) {
            ArrayList a = (ArrayList) it.next();
            String yon = a.get(yonCol).toString();
            String another = a.get(anotherCol).toString();
            if (yon != null && "" != yon) {
                if (!(yon.toLowerCase().contains("一个周期单元内的最大台套数>9") || yon.equals("一个周期单元内的最大台套数>9"))) {
                    MoreThanNineAPI mf = new MoreThanNineAPI();
                    if (mf.queryByYesOrNo(yon,another) == null) {
                        mf.setMoreThanNine(yon, another);
                    }
        
                }
            }
        }
    }
    
    /**
     *插入所有位置信息
     */
    public void locationDataInsertDB(){
        int locationCol = new ListTool(this.getDataList()).getColNum("位置");
        int yonCol = new ListTool(this.getDataList()).getColNum("一个周期单元内的最大台套数>9");
        int anotherCol = new ListTool(this.getDataList()).getColNum("别名");
        ArrayList<Location> locationData = new ArrayList();
        LocationAPI la = new LocationAPI();
        Iterator it = this.getDataList().iterator();
        while (it.hasNext()) {
            String location = null;
            String yon = null;
            String another = null;
            ArrayList a = (ArrayList) it.next();
            location = a.get(locationCol).toString();
            if (!(location.toLowerCase().contains("位置") || location.equals("位置"))) {
                if (!"".equals(a.get(yonCol))) yon = a.get(yonCol).toString();
                another = a.get(anotherCol).toString();
                if (la.getLocation(yon, another, location) == null) {
                    MoreThanNine mtn = new MoreThanNineAPI().queryByYesOrNo(yon, another);
                    la.setLocation(mtn, location);
                }
            }
        }
    }
    
    /**
     *插入所有设备信息
     */
    public void deviceDataInsertDB(){
        int sysCol = new ListTool(this.getDataList()).getColNum("系统代号");
//        int seqCol = new ListTool(this.getDataList()).getColNum("序号");
        int chineseCol = new ListTool(this.getDataList()).getColNum("设备/部件中文名称"); 
        int englishCol = new ListTool(this.getDataList()).getColNum("设备/部件英文全称");
        int deviceCol = new ListTool(this.getDataList()).getColNum("设备/部件名称代号");
        int remarkCol = new ListTool(this.getDataList()).getColNum("备注");
        String sys = null;
//        String seq = null;
        String chinese = null;
        String english = null;
        String device = null;
        String remark = null;
        ArrayList<Device> deviceData = new ArrayList();
        DeviceAPI df = new DeviceAPI();
        Iterator it = this.getDataList().iterator();
        while (it.hasNext()) {
            ArrayList a = (ArrayList) it.next();
//            seq = a.get(seqCol).toString();
            chinese = a.get(chineseCol).toString();
            english = a.get(englishCol).toString();
            device = a.get(deviceCol).toString();
            remark = a.get(remarkCol).toString();
//            if (!(seq.toLowerCase().contains("序号") || seq.equals("序号"))) {
                if (!(chinese.toLowerCase().contains("设备/部件中文名称") || chinese.equals("设备/部件中文名称"))) {
                    if (!(english.toLowerCase().contains("设备/部件英文全称") || english.equals("设备/部件英文全称"))) {
                        if (!(device.toLowerCase().contains("设备/部件名称代号") || device.equals("设备/部件名称代号"))) {
                            if (!(remark.toLowerCase().contains("备注") || remark.equals("备注"))) {
                                sys = a.get(sysCol).toString();
                                Accsystem sysData = new AccSystemAPI().getSystem(sys);
                                if (df.getDeviceBy(sys, chinese, english, device, remark) == null) {
                                    df.setDevice(sysData, chinese, english, device, remark);
                                }
                            }
                        }
                    }
                }
//            }
        }
    }
    
    /**
     * 插入多对多中间关联表
     */
    public void deviceSubLocDataInsertDB(){
        int sysCol = new ListTool(this.getDataList()).getColNum("系统代号");
//        int seqCol = new ListTool(this.getDataList()).getColNum("序号");
        int chineseCol = new ListTool(this.getDataList()).getColNum("设备/部件中文名称"); 
        int englishCol = new ListTool(this.getDataList()).getColNum("设备/部件英文全称");
        int deviceCol = new ListTool(this.getDataList()).getColNum("设备/部件名称代号");
        int subCol = new ListTool(this.getDataList()).getColNum("所在主体分区");
        int locationCol = new ListTool(this.getDataList()).getColNum("位置");
        int yonCol = new ListTool(this.getDataList()).getColNum("一个周期单元内的最大台套数>9");
        int anotherCol = new ListTool(this.getDataList()).getColNum("别名");
        int remarkCol = new ListTool(this.getDataList()).getColNum("备注");
        String sys = null;
//        String seq = null;
        String chinese = null;
        String english = null;
        String device = null;
        String sub = null;
        String location = null;
        String yon = null;
        String another = null;
        String remark = null;
        
        ArrayList<DeviceSubsystemLocation> dslData = new ArrayList();
        DeviceSubsystemLocationAPI dslapi = new DeviceSubsystemLocationAPI();
        Iterator it = this.getDataList().iterator();
        while (it.hasNext()) {
            ArrayList a = (ArrayList) it.next();
            sys = a.get(sysCol).toString();
//            seq = a.get(seqCol).toString();
            chinese = a.get(chineseCol).toString();
            english = a.get(englishCol).toString();
            device = a.get(deviceCol).toString();
            remark = a.get(remarkCol).toString();
            sub = a.get(subCol).toString();
            location = a.get(locationCol).toString();
            yon = a.get(yonCol).toString();
            another = a.get(anotherCol).toString();
            if (!(sys.toLowerCase().contains("系统代号") || sys.equals("系统代号"))) {
//                if (!(seq.toLowerCase().contains("序号") || seq.equals("序号"))) {
                    if (!(chinese.toLowerCase().contains("设备/部件中文名称") || chinese.equals("设备/部件中文名称"))) {
                        if (!(english.toLowerCase().contains("设备/部件英文全称") || english.equals("设备/部件英文全称"))) {
                            if (!(device.toLowerCase().contains("设备/部件名称代号") || device.equals("设备/部件名称代号"))) {
                                if (!(remark.toLowerCase().contains("备注") || remark.equals("备注"))) {
                                    if (!(sub.toLowerCase().contains("所在主体分区") || sub.equals("所在主体分区"))) {
                                        if (!(location.toLowerCase().contains("位置") || location.equals("位置"))) {
                                            if (!(yon.toLowerCase().contains("一个周期单元内的最大台套数>9") || yon.equals("一个周期单元内的最大台套数>9"))) {
                                                if (!(another.toLowerCase().contains("别名") || another.equals("别名"))) {
                                                    Subsystem subData = new SubsystemAPI().getSubsystem(sub);
                                                    Location locationData = new LocationAPI().getLocation(yon, another, location);
                                                    Device deviceData = new DeviceAPI().getDeviceBy(sys, chinese, english, device, remark);

                                                    dslapi.setDeviceSubsystemLocation(deviceData, subData, locationData);  
                                                }
                                            }
                                        }
                                    }
                                }

                            }
                        }
                    }
//                }
            }
                            
        }
    }
    
    /**
     *按序插入所有数据
     */
    public void insertfull(){
        
        int sysCol = new ListTool(this.getDataList()).getColNum("系统代号");
//        int seqCol = new ListTool(this.getDataList()).getColNum("序号");
        int chineseCol = new ListTool(this.getDataList()).getColNum("设备/部件中文名称"); 
        int englishCol = new ListTool(this.getDataList()).getColNum("设备/部件英文全称");
        int deviceCol = new ListTool(this.getDataList()).getColNum("设备/部件名称代号");
        int subCol = new ListTool(this.getDataList()).getColNum("所在主体分区");
        int locationCol = new ListTool(this.getDataList()).getColNum("位置");
        int yonCol = new ListTool(this.getDataList()).getColNum("一个周期单元内的最大台套数>9");
        int anotherCol = new ListTool(this.getDataList()).getColNum("别名");
        int remarkCol = new ListTool(this.getDataList()).getColNum("备注");
        ArrayList fullInfo = (ArrayList) this.getDataList().remove(0);
        Iterator it = fullInfo.iterator();
        while (it.hasNext()) {
            ArrayList oneRow = (ArrayList) it.next();
            String sys = oneRow.get(sysCol).toString();
//            String seq = oneRow.get(seqCol).toString();
            String chinese = oneRow.get(chineseCol).toString();
            String english = oneRow.get(englishCol).toString();
            String design = oneRow.get(deviceCol).toString();
            String remark = oneRow.get(remarkCol).toString();
            String sub = oneRow.get(subCol).toString();
            String location = oneRow.get(locationCol).toString();
            String yon = oneRow.get(yonCol).toString();
            String another = oneRow.get(anotherCol).toString();
            Accsystem sysData;
            Subsystem subData;
            MoreThanNine mtnData;
            Location locationData;
            Device designData;
            DeviceSubsystemLocation dslData;
            InsertAPI in = new InsertAPI();
            if (in.getSystem(sys) == null) {
                in.setSystem(sys);
            }
            if (in.getSubsystem(sub) == null) {
                in.setSubsystem(sub);
            }
            if (in.queryByYesOrNo(yon, another) == null) {
                in.setMoreThanNine(yon, another);
            }
            if (in.getLocation(yon, another, location) == null) {
                mtnData = in.queryByYesOrNo(yon, another);
                in.setLocation(mtnData, location);
            }
            if (in.getDeviceBy(sys, chinese, english, design, remark) == null) {
                sysData = in.getSystem(sys);
                in.setDevice(sysData, chinese, english, design, remark);
            }
            
            subData = in.getSubsystem(sub);
            locationData = in.getLocation(yon, another, location);
            designData = in.getDeviceBy(sys, chinese, english, design, remark);
            if (in.getDeviceSubsystemLocationBy(sys, chinese, english, design, remark, sub, location, yon, another) == null) {
                in.setDeviceSubsystemLocation(designData, subData, locationData);
            }
        }
    }
    
    /**
     *
     * @return 全部执行后返回1
     */
    public Integer allDataInsertDB() {
        this.systemAccDataInsertDB();
        this.subsystemDataInsertDB();
        this.moreThanNineDataInsertDB();
        this.locationDataInsertDB();
        this.deviceDataInsertDB();
//        this.deviceSubLocDataInsertDB();
//        InsertAPI a = new InsertAPI();
//        a.init();
//        this.insertfull();
//        a.destory();
        return 1;
    }
}
