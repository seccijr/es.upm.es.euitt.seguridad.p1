package es.upm.euitt.seguridad;

public enum MainMenuOptEnum {
    SIMETRICA, ASIMETRICA, SALIR, ERRONEA;

    public static MainMenuOptEnum getOpt(int opt) {
        try {

            return MainMenuOptEnum.values()[opt - 1];
        } catch (ArrayIndexOutOfBoundsException e) {

            return MainMenuOptEnum.ERRONEA;
        }
    }
}
