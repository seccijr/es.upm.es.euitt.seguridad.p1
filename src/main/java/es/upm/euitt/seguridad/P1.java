package es.upm.euitt.seguridad;

public class P1 {
    public static MenuEnum chooseMenu(MenuEnum me) {
        Menu m = new Menu();
        MenuEnum me2 = MenuEnum.MAIN;

        switch (me) {
            case MAIN:
                me2 = m.mainMenu();
                break;

            case SIMETRICO:
                me2 = m.symetricMenu();
                break;

            default:
                me2 = MenuEnum.SALIR;
                break;
        }

        return me2;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        MenuEnum me = MenuEnum.MAIN;

        while (me != MenuEnum.SALIR) {
            me = chooseMenu(me);
        }

        System.out.println("¡Adios!");
    }
}
