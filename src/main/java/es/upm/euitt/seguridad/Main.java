package es.upm.euitt.seguridad;

import es.upm.euitt.seguridad.menu.MainMenu;
import es.upm.euitt.seguridad.menu.SymetricMenu;
import es.upm.euitt.seguridad.menu.AsymmetricMenu;
import es.upm.euitt.seguridad.enumerators.MainMenuOptEnum;

public class Main {
    public static SymetricMenu sm;
    public static MainMenu mm;
    public static AsymmetricMenu am;

    public static MainMenuOptEnum chooseMenu(MainMenuOptEnum me) {

        switch (me) {
            case MAIN:
                me = mm.mainMenu();
                break;
            case SIMETRICA:
                me = sm.symetricMenu();
                break;
            case ASIMETRICA:
                me = am.asymetricMenu();
                break;
            default:
                me = MainMenuOptEnum.SALIR;
                break;
        }

        return me;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        Main.sm = new SymetricMenu();
        Main.mm = new MainMenu();
        Main.am = new AsymmetricMenu();
        MainMenuOptEnum me = MainMenuOptEnum.MAIN;

        while (me != MainMenuOptEnum.SALIR) {
            me = chooseMenu(me);
        }

        System.out.println("¡Adios!");
    }
}
