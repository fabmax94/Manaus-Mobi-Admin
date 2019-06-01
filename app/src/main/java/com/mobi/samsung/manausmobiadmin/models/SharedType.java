package com.mobi.samsung.manausmobiadmin.models;

/**
 * Created by christian.s on 11/20/2017.
 */

public enum SharedType {
    Traffic("Trânsito"), Warning("Perigo");

    private String description;

    private SharedType(String sigla) {
        this.description = sigla;
    }

    public String getDescription() {
        return description;
    }
}
