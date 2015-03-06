package es.upm.euitt.seguridad.enumerators;

public enum AsymetricMenuOptEnum {
    VOLVER, GENERAR_CLAVE, CIFRAR, DESCIFRAR, FIRMAR, VERIFICAR, ERRONEA;

    public static AsymetricMenuOptEnum getOpt(int opt) {
        try {
            return AsymetricMenuOptEnum.values()[opt];
        } catch (ArrayIndexOutOfBoundsException e) {
            return AsymetricMenuOptEnum.ERRONEA;
        }
    }
}
