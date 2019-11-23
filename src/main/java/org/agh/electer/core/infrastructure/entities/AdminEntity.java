package org.agh.electer.core.infrastructure.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminEntity {
    @Id
    @Column(name = "LOGIN")
    private String login;

    @Column(name = "PASSWORD")
    private String password;
}
