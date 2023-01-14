package com.testovoye.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "event_log", schema = "public", catalog = "test")
public class EventLogEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "uid")
    private String uid;
    @Basic
    @Column(name = "tag")
    private String tag;

}
