package es.upm.euitt.seguridad.menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import es.upm.euitt.seguridad.enumerators.MainMenuOptEnum;

public class MainMenu extends BaseMenu {
    public void printMainMenu() {
        System.out.println("¿Qué tipo de criptografía desea utilizar?");
        System.out.println("1. Simétrica.");
        System.out.println("2. Asimétrica.");
        System.out.println("3. Salir.");
    }

    public MainMenuOptEnum requestMainOption() {
        try {
            int optInt = Integer.parseInt(this.getStrOpt());

            return MainMenuOptEnum.getOpt(optInt);
        } catch (NumberFormatException e) {

            return MainMenuOptEnum.ERRONEA;
        }
    }

    public MainMenuOptEnum mainMenu() {
        this.printMainMenu();

        return this.requestMainOption();
    }
}
