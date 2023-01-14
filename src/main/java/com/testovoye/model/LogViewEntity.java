package com.testovoye.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "log_view", schema = "public", catalog = "test")
public class LogViewEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "reg_time")
    private Timestamp regTime;
    @Basic
    @Column(name = "uid")
    private String uid;
    @Basic
    @Column(name = "fc_imp_chk")
    private Integer fcImpChk;
    @Basic
    @Column(name = "fc_time_chk")
    private Integer fcTimeChk;
    @Basic
    @Column(name = "utmtr")
    private Integer utmtr;
    @Basic
    @Column(name = "mm_dma")
    private Integer mmDma;
    @Basic
    @Column(name = "os_name")
    private String osName;
    @Basic
    @Column(name = "model")
    private String model;
    @Basic
    @Column(name = "hardware")
    private String hardware;
    @Basic
    @Column(name = "site_id")
    private String siteId;
    @Basic
    @Column(name = "tag")
    private String tag;

}
