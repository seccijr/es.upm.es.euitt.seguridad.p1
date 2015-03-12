package es.upm.euitt.seguridad.enumerators;

public enum AsymmetricMenuOptEnum {
    VOLVER, GENERAR_CLAVE, CIFRAR, DESCIFRAR, FIRMAR, VERIFICAR, ERRONEA;

    public static AsymmetricMenuOptEnum getOpt(int opt) {
        try {
            return AsymmetricMenuOptEnum.values()[opt];
        } catch (ArrayIndexOutOfBoundsException e) {
            return AsymmetricMenuOptEnum.ERRONEA;
        }
    }
}
