package com.quinbay.advertiz.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="userid")
    Integer userid;

    @Column(name="username")
    String username;

    @JsonIgnore
    @Column(name="password")
    String password;

    @Column(name="email")
    String email;

    @Column(name = "phone")
    String phone;
}
