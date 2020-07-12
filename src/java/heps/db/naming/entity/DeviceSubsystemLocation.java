/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heps.db.naming.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author root
 */
@Entity
@Table(name = "device_subsystem_location")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DeviceSubsystemLocation.findAll", query = "SELECT d FROM DeviceSubsystemLocation d")
    , @NamedQuery(name = "DeviceSubsystemLocation.findById", query = "SELECT d FROM DeviceSubsystemLocation d WHERE d.id = :id")})
public class DeviceSubsystemLocation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "device_id", referencedColumnName = "device_id")
    @ManyToOne(optional = false)
    private Device deviceId;
    @JoinColumn(name = "location_id", referencedColumnName = "location_id")
    @ManyToOne(optional = false)
    private Location locationId;
    @JoinColumn(name = "subsystem_id", referencedColumnName = "subsystem_id")
    @ManyToOne(optional = false)
    private Subsystem subsystemId;

    public DeviceSubsystemLocation() {
    }

    public DeviceSubsystemLocation(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Device getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Device deviceId) {
        this.deviceId = deviceId;
    }

    public Location getLocationId() {
        return locationId;
    }

    public void setLocationId(Location locationId) {
        this.locationId = locationId;
    }

    public Subsystem getSubsystemId() {
        return subsystemId;
    }

    public void setSubsystemId(Subsystem subsystemId) {
        this.subsystemId = subsystemId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DeviceSubsystemLocation)) {
            return false;
        }
        DeviceSubsystemLocation other = (DeviceSubsystemLocation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "{\"dslId\":\"" + id + "\"," + "\"deviceId\":\"" + deviceId.getDeviceId() + "\"," + "\"systemId\":\"" + deviceId.getSystemId().getSystemId() + "\"," + "\"system\":\"" + deviceId.getSystemId().getSystemName() + "\"," 
                + "\"Chinesename\":\"" + deviceId.getChinesename() + "\"," + "\"Englishname\":\"" + deviceId.getEnglishname() + "\"," 
                + "\"devicename\":\"" + deviceId.getDesignName() + "\"," + "\"subsystemId\":\"" + subsystemId.getSubsystemId() + "\"," + "\"subsystem\":\"" + subsystemId.getSubsystemName()+ "\"," 
                + "\"locationId\":\"" + locationId.getLocationId() + "\"," + "\"location\":\"" + locationId.getLocationName() + "\"," 
                + "\"judgeId\":\"" + locationId.getJudgeId().getJudgeId() + "\"," + "\"judge\":\"" + locationId.getJudgeId().getYesOrNo() + "\"," 
                + "\"another\":\"" + locationId.getJudgeId().getAnotherName() + "\"," + "\"remark\":\"" + deviceId.getRemark() + "\"}";	
    }
    
}
