package com.npci.shirotutorial.security.model.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "SECURITY_ROLE")
@SequenceGenerator(name = "SEQ_GENERATOR", sequenceName = "ROLE_SEQ")
@NamedQueries({
        @NamedQuery(name = Role.FIND_ALL, query = "select t from Role t"),
})
public class Role {

    public static final String FIND_ALL = "Role.findAll";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GENERATOR")
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    @NotNull
    @Column(name = "NAME", length = 50, nullable = false)
    private String name;

    @NotNull
    @Past
    @Column(name = "creation", nullable = false, updatable = false, columnDefinition = "DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creation;

    @Version
    @Column(name = "VERSION", nullable = false)
    private Timestamp version;
}
