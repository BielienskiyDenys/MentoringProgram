package com.epam.module06boottask2.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "Title should be present")
    @Size(min = 3, max = 45, message = "Title should be between 3 and 45 characters")
    @Column(name = "username")
    private String name;

    @NotNull(message = "Title should be present")
    @Size(min = 3, max = 60, message = "Title should be between 3 and 60 characters")
    private String password;
}
