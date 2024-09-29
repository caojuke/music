package com.juke.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewUser implements Serializable {
    private String username;
    private String password;
    private String birthdate;
    private String[] hobby;
    private int gender;
}
