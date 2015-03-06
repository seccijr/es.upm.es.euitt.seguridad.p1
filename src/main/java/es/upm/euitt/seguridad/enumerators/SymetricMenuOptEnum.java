package es.upm.euitt.seguridad.enumerators;

public enum SymetricMenuOptEnum {
    VOLVER, GENERAR_CLAVE, CIFRAR, DESCIFRAR, ERRONEA;

    public static SymetricMenuOptEnum getOpt(int opt) {
        try {
            return SymetricMenuOptEnum.values()[opt];
        } catch (ArrayIndexOutOfBoundsException e) {
            return SymetricMenuOptEnum.ERRONEA;
        }
    }
}
