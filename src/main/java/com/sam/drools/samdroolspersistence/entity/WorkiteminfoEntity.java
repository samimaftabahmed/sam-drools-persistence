package com.sam.drools.samdroolspersistence.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Arrays;

@Entity
@Table(name = "workiteminfo", schema = "drool_demo")
public class WorkiteminfoEntity {
    private long workItemId;
    private Timestamp creationDate;
    private String name;
    private long processInstanceId;
    private long state;
    private Integer optlock;
    private byte[] workItemByteArray;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "workItemId")
    public long getWorkItemId() {
        return workItemId;
    }

    public void setWorkItemId(long workItemId) {
        this.workItemId = workItemId;
    }

    @Basic
    @Column(name = "creationDate")
    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "processInstanceId")
    public long getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(long processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    @Basic
    @Column(name = "state")
    public long getState() {
        return state;
    }

    public void setState(long state) {
        this.state = state;
    }

    @Basic
    @Column(name = "OPTLOCK")
    public Integer getOptlock() {
        return optlock;
    }

    public void setOptlock(Integer optlock) {
        this.optlock = optlock;
    }

    @Basic
    @Column(name = "workItemByteArray")
    public byte[] getWorkItemByteArray() {
        return workItemByteArray;
    }

    public void setWorkItemByteArray(byte[] workItemByteArray) {
        this.workItemByteArray = workItemByteArray;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WorkiteminfoEntity that = (WorkiteminfoEntity) o;

        if (workItemId != that.workItemId) return false;
        if (processInstanceId != that.processInstanceId) return false;
        if (state != that.state) return false;
        if (creationDate != null ? !creationDate.equals(that.creationDate) : that.creationDate != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (optlock != null ? !optlock.equals(that.optlock) : that.optlock != null) return false;
        if (!Arrays.equals(workItemByteArray, that.workItemByteArray)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (workItemId ^ (workItemId >>> 32));
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (int) (processInstanceId ^ (processInstanceId >>> 32));
        result = 31 * result + (int) (state ^ (state >>> 32));
        result = 31 * result + (optlock != null ? optlock.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(workItemByteArray);
        return result;
    }
}
