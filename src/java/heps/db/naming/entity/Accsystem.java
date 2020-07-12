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
@Table(name = "accsystem")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Accsystem.findAll", query = "SELECT a FROM Accsystem a")
    , @NamedQuery(name = "Accsystem.findBySystemId", query = "SELECT a FROM Accsystem a WHERE a.systemId = :systemId")
    , @NamedQuery(name = "Accsystem.findBySystemName", query = "SELECT a FROM Accsystem a WHERE a.systemName = :systemName")})
public class Accsystem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "system_id")
    private Integer systemId;
    @Size(max = 45)
    @Column(name = "system_name")
    private String systemName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "systemId")
    private List<Device> deviceList;

    public Accsystem() {
    }

    public Accsystem(Integer systemId) {
        this.systemId = systemId;
    }

    public Integer getSystemId() {
        return systemId;
    }

    public void setSystemId(Integer systemId) {
        this.systemId = systemId;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    @XmlTransient
    public List<Device> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List<Device> deviceList) {
        this.deviceList = deviceList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (systemId != null ? systemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Accsystem)) {
            return false;
        }
        Accsystem other = (Accsystem) object;
        if ((this.systemId == null && other.systemId != null) || (this.systemId != null && !this.systemId.equals(other.systemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "{\"systemId\":\"" + systemId + "\"," + "\"system\":\"" + systemName + "\"}";
    }
    
}
