package com.ctts.member.entity;

import jakarta.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true, length = 100, nullable = false)
    private String username;
    @Column(length = 100, nullable = false)
    private String password;
}
