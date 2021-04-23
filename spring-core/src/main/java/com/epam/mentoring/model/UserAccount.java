package com.epam.mentoring.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "accounts")
public class UserAccount {
    @Id
    private Long id;

    @OneToOne
    @MapsId
    private User user;

    private Double balance = 0.0;

    public UserAccount(User user) {
        this.id = user.getId();
        this.user = user;
    }

}
