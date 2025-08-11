package com.desergm.login.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Getter
@Setter
@Document(collection = "email-validation-token")
public class EmailValidation {

    @Id
    public String id;

    @Indexed
    public String email;

    public String token;

    @CreatedDate
    @Indexed(expireAfter = "5m")
    private Instant createdAt;

}
