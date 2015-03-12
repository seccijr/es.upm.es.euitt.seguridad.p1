package es.upm.euitt.seguridad;

import es.upm.euitt.seguridad.menu.MainMenu;
import es.upm.euitt.seguridad.menu.SymetricMenu;
import es.upm.euitt.seguridad.menu.AsymmetricMenu;
import es.upm.euitt.seguridad.enumerators.MainMenuOptEnum;

public class Main {
    public static MainMenuOptEnum chooseMenu(MainMenuOptEnum me) {

        switch (me) {
            case MAIN:
                MainMenu mm = new MainMenu();
                me = mm.mainMenu();
                break;
            case SIMETRICA:
                SymetricMenu sm = new SymetricMenu();
                me = sm.symetricMenu();
                break;
            case ASIMETRICA:
                AsymmetricMenu am = new AsymmetricMenu();
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
        MainMenuOptEnum me = MainMenuOptEnum.MAIN;

        while (me != MainMenuOptEnum.SALIR) {
            me = chooseMenu(me);
        }

        System.out.println("¡Adios!");
    }
}
