package com.computacionJava;

import java.util.HashMap;

import com.computacionJava.evidencia.Medico;

public class Medicos{
    Integer id;
    Medico medico;


    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Medico getMedico() {
        return this.medico;
    }

    public void setMedico(Medico doc) {
        this.medico = doc;
    }

    public Medicos() {
    }

    public Medicos(Integer id, Medico medico) {
        this.id = id;
        this.medico = medico;
    }
}
