package com.exuberant.code2create4.models;

import java.util.List;

public class AgendaModel {

    List<Agenda> agendasList;

    public AgendaModel(List<Agenda> agendasList) {
        this.agendasList = agendasList;
    }

    public AgendaModel() {
    }

    public List<Agenda> getAgendasList() {
        return agendasList;
    }

    public void setAgendasList(List<Agenda> agendasList) {
        this.agendasList = agendasList;
    }
}
