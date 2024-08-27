package org.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties
public class Data{
    public int id;
    public String name;
    public String email;
    public String gender;
    public String status;
}