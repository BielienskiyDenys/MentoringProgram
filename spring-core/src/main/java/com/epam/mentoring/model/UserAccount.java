package com.epam.mentoring.model;

import lombok.*;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
public class UserAccount {
    @Id
    private Long id;

    private User user;

    private Double balance = 0.0;

    public UserAccount(User user) {
        this.id = user.getId();
        this.user = user;
    }

}
