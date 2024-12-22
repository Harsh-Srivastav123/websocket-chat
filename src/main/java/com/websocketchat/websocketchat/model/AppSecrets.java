package com.websocketchat.websocketchat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AppSecrets {
    private String dbUsername;
    private String dbPassword;
    private String dbUrl;
    private String dbHost;
    private String dbPort;
    private String dbName;
}
