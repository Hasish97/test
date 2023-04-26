package com.Inova.ClickupDashboard.entity;

import com.Inova.ClickupDashboard.controllers.App_constants;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
public class AttendanceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int attendanceRecordID;

    @ManyToOne
    private Member member;

    @Temporal(TemporalType.DATE)
    @Column(name="date")
    private Date date;

    @Column(name = "attendanceStatus")
    @Enumerated(EnumType.STRING)
    private App_constants.YesNo attendanceStatus;

    public AttendanceRecord(){}
    public int getAttendanceRecordID() {
        return attendanceRecordID;
    }

    public void setAttendanceRecordID(int attendanceRecordID) {
        this.attendanceRecordID = attendanceRecordID;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public App_constants.YesNo getAttendanceStatus() {
        return attendanceStatus;
    }

    public void setAttendanceStatus(App_constants.YesNo attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
    }
}
