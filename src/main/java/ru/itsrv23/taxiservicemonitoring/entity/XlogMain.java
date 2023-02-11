package ru.itsrv23.taxiservicemonitoring.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Data
@Table(name = "xlog_main")
@Entity
public class XlogMain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx", nullable = false)
    private Integer idx;

    @Column(name = "mainid")
    private Integer mainId;

    @Column(name = "dispid")
    private Integer dispId;

    @Column(name = "descr")
    private String descr;

    @Column(name = "subject")
    private String subject;

    @Column(name = "editdate")
    private OffsetDateTime editDate;

    @Column(name = "autoid")
    private Integer auto;

    @Column(name = "vehicleid")
    private Integer vehicle;

    @Column(name = "driverid")
    private Integer driver;
}
