package com.ifmg.formiga.ecommerce.model;

import com.ifmg.formiga.ecommerce.enums.EnumChannel;
import com.ifmg.formiga.ecommerce.enums.EnumService;

import java.io.Serializable;
import java.util.ArrayList;

public class Comunication implements Serializable {

    @Override
    public String toString() {
        return "Comunication [channel=" + channel + ", service=" + service + ", content=" + content + "]";
    }

    private static final long serialVersionUID = -585749592930578838L;
    public EnumChannel channel;
    public EnumService service;
    public ArrayList<Object> content;

    public Comunication() {
        content = new ArrayList<Object>();
    }

    public Comunication(EnumChannel channel, EnumService service, ArrayList<Object> content) {
        this.channel = channel;
        this.service = service;
        this.content = content;
    }

}
