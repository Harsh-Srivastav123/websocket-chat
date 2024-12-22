package com.websocketchat.websocketchat.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AppSecrets {
    @JsonProperty("dbUsername")
    private String dbUsername;
    
    @JsonProperty("dbPassword")
    private String dbPassword;
    
    @JsonProperty("dbUrl")
    private String dbUrl;
}