package com.example.final_pr.enums;

public enum Heating {
    GAS,ELECTRIC,WITH_FIREWOOD;

    @Override
    public String toString() {
        return name()+"_HEATING";
    }
}
