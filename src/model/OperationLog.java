package model;

import util.OperationType;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

@Entity(name = "operationLog")
public class OperationLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String operation;
    private String authority;
    @Temporal(TemporalType.DATE)
    private Date date;
    private Time time;

    public OperationLog(OperationType operationType, String authority, Date date, Time time) {
        this.operation = operationType.getOperation();
        this.authority = authority;
        this.date = date;
        this.time = time;
    }

    public OperationLog() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}
