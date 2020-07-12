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
@Table(name = "location")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Location.findAll", query = "SELECT l FROM Location l")
    , @NamedQuery(name = "Location.findByLocationId", query = "SELECT l FROM Location l WHERE l.locationId = :locationId")
    , @NamedQuery(name = "Location.findByLocationName", query = "SELECT l FROM Location l WHERE l.locationName = :locationName")})
public class Location implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "location_id")
    private Integer locationId;
    @Size(max = 45)
    @Column(name = "location_name")
    private String locationName;
    @JoinColumn(name = "judge_id", referencedColumnName = "judge_id")
    @ManyToOne(optional = false)
    private MoreThanNine judgeId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "locationId")
    private List<DeviceSubsystemLocation> deviceSubsystemLocationList;

    public Location() {
    }

    public Location(Integer locationId) {
        this.locationId = locationId;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public MoreThanNine getJudgeId() {
        return judgeId;
    }

    public void setJudgeId(MoreThanNine judgeId) {
        this.judgeId = judgeId;
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
        hash += (locationId != null ? locationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Location)) {
            return false;
        }
        Location other = (Location) object;
        if ((this.locationId == null && other.locationId != null) || (this.locationId != null && !this.locationId.equals(other.locationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "{\"locationId\":\"" + locationId + "\"," + "\"judgeId\":\"" + judgeId.getJudgeId() + "\"," + "\"judge\":\"" + judgeId.getYesOrNo() + "\"," + "\"another\":\"" + judgeId.getAnotherName() + "\","
                + "\"location\":\"" + locationName + "\"}";
    }
    
}
