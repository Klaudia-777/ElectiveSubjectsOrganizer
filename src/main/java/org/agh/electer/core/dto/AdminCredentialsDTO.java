package org.agh.electer.core.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class AdminCredentialsDTO {
    private String login;
    private String password;
}
