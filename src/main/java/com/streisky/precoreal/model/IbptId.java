package com.streisky.precoreal.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class IbptId implements Serializable {
    private String ncm;
    //private String ex;
    private String uf;
}
