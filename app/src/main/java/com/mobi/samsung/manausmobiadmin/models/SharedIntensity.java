package com.mobi.samsung.manausmobiadmin.models;

/**
 * Created by christian.s on 11/20/2017.
 */

public enum SharedIntensity {
    Moderate("Moderado"), Intense("Intenso"), Stopped("Parado"), InRoute("Em Rota"), Coasting("Acostamento"),Climate("Clima");

    private String description;

    private SharedIntensity(String sigla) {
        this.description = sigla;
    }

    public String getDescription() {
        return description;
    }

}
