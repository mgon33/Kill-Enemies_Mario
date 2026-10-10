package org.cuatrovientos.dam.psp.V1;

import java.util.ArrayList;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        ArrayList<Character> characters = new ArrayList<>();

        for (int i = 0; i < 5; i++) characters.add(new Friend());
        for (int i = 0; i < 5; i++) characters.add(new Enemy());

        Collections.shuffle(characters);

        for (int i = 0; i < characters.size(); i++) {
            Character c = characters.get(i);

            if (c.isEnemy()) {
                System.out.println("El personaje " + i + " es un enemigo! ¡Mátalo!");
                Enemy enemy = (Enemy) c;
                enemy.kill();
            } else {
                System.out.println("El carácter " + i + " es un amigo!");
            }
        }
    }
}
