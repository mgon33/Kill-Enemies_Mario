package org.cuatrovientos.dam.psp.V1;

public class Enemy implements Character{
    public boolean isEnemy() {
        return true;
    }

    public void kill() {
        System.out.println("Ahhhggg, me mataste, bastardo!");
    }
}
