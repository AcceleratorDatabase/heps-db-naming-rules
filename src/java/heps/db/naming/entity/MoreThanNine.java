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
@Table(name = "more_than_nine")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MoreThanNine.findAll", query = "SELECT m FROM MoreThanNine m")
    , @NamedQuery(name = "MoreThanNine.findByJudgeId", query = "SELECT m FROM MoreThanNine m WHERE m.judgeId = :judgeId")
    , @NamedQuery(name = "MoreThanNine.findByYesOrNo", query = "SELECT m FROM MoreThanNine m WHERE m.yesOrNo = :yesOrNo")
    , @NamedQuery(name = "MoreThanNine.findByAnotherName", query = "SELECT m FROM MoreThanNine m WHERE m.anotherName = :anotherName")})
public class MoreThanNine implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "judge_id")
    private Integer judgeId;
    @Size(max = 40)
    @Column(name = "yes_or_no")
    private String yesOrNo;
    @Size(max = 45)
    @Column(name = "another_name")
    private String anotherName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "judgeId")
    private List<Location> locationList;

    public MoreThanNine() {
    }

    public MoreThanNine(Integer judgeId) {
        this.judgeId = judgeId;
    }

    public Integer getJudgeId() {
        return judgeId;
    }

    public void setJudgeId(Integer judgeId) {
        this.judgeId = judgeId;
    }

    public String getYesOrNo() {
        return yesOrNo;
    }

    public void setYesOrNo(String yesOrNo) {
        this.yesOrNo = yesOrNo;
    }

    public String getAnotherName() {
        return anotherName;
    }

    public void setAnotherName(String anotherName) {
        this.anotherName = anotherName;
    }

    @XmlTransient
    public List<Location> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<Location> locationList) {
        this.locationList = locationList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (judgeId != null ? judgeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MoreThanNine)) {
            return false;
        }
        MoreThanNine other = (MoreThanNine) object;
        if ((this.judgeId == null && other.judgeId != null) || (this.judgeId != null && !this.judgeId.equals(other.judgeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "heps.db.naming.entity.MoreThanNine[ judgeId=" + judgeId + " ]";
    }
    
}
