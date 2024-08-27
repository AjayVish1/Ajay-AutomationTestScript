package org.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties
public class Root{
    public int code;
    public Object meta;
    public Data data;
}