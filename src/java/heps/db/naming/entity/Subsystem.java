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
@Table(name = "subsystem")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Subsystem.findAll", query = "SELECT s FROM Subsystem s")
    , @NamedQuery(name = "Subsystem.findBySubsystemId", query = "SELECT s FROM Subsystem s WHERE s.subsystemId = :subsystemId")
    , @NamedQuery(name = "Subsystem.findBySubsystemName", query = "SELECT s FROM Subsystem s WHERE s.subsystemName = :subsystemName")})
public class Subsystem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "subsystem_id")
    private Integer subsystemId;
    @Size(max = 45)
    @Column(name = "subsystem_name")
    private String subsystemName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subsystemId")
    private List<DeviceSubsystemLocation> deviceSubsystemLocationList;

    public Subsystem() {
    }

    public Subsystem(Integer subsystemId) {
        this.subsystemId = subsystemId;
    }

    public Integer getSubsystemId() {
        return subsystemId;
    }

    public void setSubsystemId(Integer subsystemId) {
        this.subsystemId = subsystemId;
    }

    public String getSubsystemName() {
        return subsystemName;
    }

    public void setSubsystemName(String subsystemName) {
        this.subsystemName = subsystemName;
    }

    @XmlTransient
    public List<DeviceSubsystemLocation> getDeviceSubsystemLocationList() {
        return deviceSubsystemLocationList;
    }

    public void setDeviceSubsystemLocationList(List<DeviceSubsystemLocation> deviceSubsystemLocationList) {
        this.deviceSubsystemLocationList = deviceSubsystemLocationList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (subsystemId != null ? subsystemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Subsystem)) {
            return false;
        }
        Subsystem other = (Subsystem) object;
        if ((this.subsystemId == null && other.subsystemId != null) || (this.subsystemId != null && !this.subsystemId.equals(other.subsystemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "heps.db.naming.entity.Subsystem[ subsystemId=" + subsystemId + " ]";
    }
    
}
