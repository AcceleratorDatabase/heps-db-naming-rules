/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heps.db.naming.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author root
 */
@Entity
@Table(name = "device")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Device.findAll", query = "SELECT d FROM Device d")
    , @NamedQuery(name = "Device.findByDeviceId", query = "SELECT d FROM Device d WHERE d.deviceId = :deviceId")
    , @NamedQuery(name = "Device.findByEnglishname", query = "SELECT d FROM Device d WHERE d.englishname = :englishname")
    , @NamedQuery(name = "Device.findByChinesename", query = "SELECT d FROM Device d WHERE d.chinesename = :chinesename")
    , @NamedQuery(name = "Device.findByDesignName", query = "SELECT d FROM Device d WHERE d.designName = :designName")
    , @NamedQuery(name = "Device.findByRemark", query = "SELECT d FROM Device d WHERE d.remark = :remark")})
public class Device implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "device_id")
    private Integer deviceId;
    @Size(max = 100)
    @Column(name = "English_name")
    private String englishname;
    @Size(max = 100)
    @Column(name = "Chinese_name")
    private String chinesename;
    @Size(max = 45)
    @Column(name = "design_name")
    private String designName;
    @Size(max = 500)
    @Column(name = "remark")
    private String remark;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "deviceId")
    private List<DeviceSubsystemLocation> deviceSubsystemLocationList;
    @JoinColumn(name = "system_id", referencedColumnName = "system_id")
    @ManyToOne(optional = false)
    private Accsystem systemId;

    public Device() {
    }

    public Device(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public String getEnglishname() {
        return englishname;
    }

    public void setEnglishname(String englishname) {
        this.englishname = englishname;
    }

    public String getChinesename() {
        return chinesename;
    }

    public void setChinesename(String chinesename) {
        this.chinesename = chinesename;
    }

    public String getDesignName() {
        return designName;
    }

    public void setDesignName(String designName) {
        this.designName = designName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @XmlTransient
    public List<DeviceSubsystemLocation> getDeviceSubsystemLocationList() {
        return deviceSubsystemLocationList;
    }

    public void setDeviceSubsystemLocationList(List<DeviceSubsystemLocation> deviceSubsystemLocationList) {
        this.deviceSubsystemLocationList = deviceSubsystemLocationList;
    }

    public Accsystem getSystemId() {
        return systemId;
    }

    public void setSystemId(Accsystem systemId) {
        this.systemId = systemId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (deviceId != null ? deviceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Device)) {
            return false;
        }
        Device other = (Device) object;
        if ((this.deviceId == null && other.deviceId != null) || (this.deviceId != null && !this.deviceId.equals(other.deviceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "{\"deviceId\":\"" + deviceId + "\"," + "\"systemId\":\"" + systemId.getSystemId() + "\"," + "\"system\":\"" + systemId.getSystemName() + "\","+ "\"Chinesename\":\"" + chinesename + "\","
    + "\"Englishname\":\"" + englishname + "\"," + "\"devicename\":\"" + designName + "\"," + "\"remark\":\"" + remark + "\"}";
    }
    
}
