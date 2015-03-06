package es.upm.euitt.seguridad.enumerators;

public enum MainMenuOptEnum {
    MAIN, SIMETRICA, ASIMETRICA, SALIR, ERRONEA;

    public static MainMenuOptEnum getOpt(int opt) {
        try {
            return MainMenuOptEnum.values()[opt];
        } catch (ArrayIndexOutOfBoundsException e) {
            return MainMenuOptEnum.ERRONEA;
        }
    }
}
