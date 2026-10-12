package org.cuatrovientos.dam.psp.V2;

public class Hero implements Character {
    private static final long serialVersionUID = 1L;
    private String name;
    private int enemiesKilled;
    private int friendsDefended;

    public Hero(String name) {
        this.name = name;
        this.enemiesKilled = 0;
        this.friendsDefended = 0;
    }

    @Override
    public boolean isEnemy() {
        return false;
    }

    public void attack(Enemy enemy) {
        System.out.println("¡He atacado a un enemigo!");
        enemy.kill();
        enemiesKilled++;
    }

    public void defend(Friend friend) {
        System.out.println("¡He defendido a un amigo!");
        friend.heal();
        friendsDefended++;
    }

    public void displayStats() {
        System.out.println("Héroe: " + name + " | Enemigos matados: " + enemiesKilled + " | Amigos defendidos: " + friendsDefended);
    }
}
