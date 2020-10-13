package com.sam.drools.samdroolspersistence.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Arrays;

@Entity
@Table(name = "sessioninfo", schema = "drool_demo")
public class SessioninfoEntity {
    private long id;
    private Timestamp lastModificationDate;
    private byte[] rulesByteArray;
    private Timestamp startDate;
    private Integer optlock;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "lastModificationDate")
    public Timestamp getLastModificationDate() {
        return lastModificationDate;
    }

    public void setLastModificationDate(Timestamp lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }

    @Basic
    @Column(name = "rulesByteArray")
    public byte[] getRulesByteArray() {
        return rulesByteArray;
    }

    public void setRulesByteArray(byte[] rulesByteArray) {
        this.rulesByteArray = rulesByteArray;
    }

    @Basic
    @Column(name = "startDate")
    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    @Basic
    @Column(name = "OPTLOCK")
    public Integer getOptlock() {
        return optlock;
    }

    public void setOptlock(Integer optlock) {
        this.optlock = optlock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SessioninfoEntity that = (SessioninfoEntity) o;

        if (id != that.id) return false;
        if (lastModificationDate != null ? !lastModificationDate.equals(that.lastModificationDate) : that.lastModificationDate != null)
            return false;
        if (!Arrays.equals(rulesByteArray, that.rulesByteArray)) return false;
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;
        if (optlock != null ? !optlock.equals(that.optlock) : that.optlock != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (lastModificationDate != null ? lastModificationDate.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(rulesByteArray);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (optlock != null ? optlock.hashCode() : 0);
        return result;
    }
}
