package org.cuatrovientos.dam.psp.V2;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {
    private static final String FILE_NAME = "savegame.dat";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Hero hero = null;
        ArrayList<Character> characters = null;

        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
                hero = (Hero) ois.readObject();
                // Suprimo el warning porque el segundo objeto guardado es el ArrayList
                @SuppressWarnings("unchecked")
                ArrayList<Character> loadedCharacters = (ArrayList<Character>) ois.readObject();
                characters = loadedCharacters;
                System.out.println("Partida cargada con éxito.");
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error al cargar: " + e.getMessage());
            }
        }

        if (hero == null || characters == null) {
            System.out.println("No se ha encontrado partida. Iniciando nueva partida...");
            System.out.print("Introduce el nombre de tu héroe: ");
            String heroName = scanner.nextLine();

            hero = new Hero(heroName);
            characters = new ArrayList<>();

            for (int i = 0; i < 5; i++) characters.add(new Friend());
            for (int i = 0; i < 5; i++) characters.add(new Enemy());

            Collections.shuffle(characters);
        }

        playGame(scanner, hero, characters);
    }

    private static void playGame(Scanner scanner, Hero hero, ArrayList<Character> characters) {
        boolean playing = true;

        while (playing && !characters.isEmpty()) {
            System.out.println("\n--- TURNO ---");
            hero.displayStats();
            countCharacters(characters);
            showCharacters(characters);

            System.out.println("\n¿Qué deseas hacer?");
            System.out.println("1. Atacar");
            System.out.println("2. Defender");
            System.out.println("3. Guardar y salir");
            System.out.print("Opción: ");

            String choice = scanner.nextLine();

            if (choice.equals("3")) {
                saveGame(hero, characters);
                playing = false;
                continue;
            }

            System.out.print("Introduce el índice del personaje sobre el que actuar: ");
            int index;
            try {
                index = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, introduce un número válido.");
                continue;
            }

            if (index < 0 || index >= characters.size()) {
                System.out.println("Índice fuera de rango.");
                continue;
            }

            Character target = characters.get(index);

            if (choice.equals("1")) { // Intentar atacar
                if (target.isEnemy()) {
                    hero.attack((Enemy) target);
                } else {
                    System.out.println("Has intentado matar a un amigo. Ha sido eliminado de la lista.");
                }
                characters.remove(index);

            } else if (choice.equals("2")) { // Intentar defender
                if (!target.isEnemy()) {
                    hero.defend((Friend) target);
                } else {
                    System.out.println("Has intentado defender a un enemigo. ¡Se ha duplicado!");
                    characters.add(new Enemy());
                }
            }
        }

        if (characters.isEmpty()) {
            System.out.println("\n¡Juego terminado! No quedan personajes.");
            hero.displayStats();
            new File(FILE_NAME).delete();
        }
    }

    private static void countCharacters(ArrayList<Character> characters) {
        int friends = 0, enemies = 0;
        for (Character c : characters) {
            if (c.isEnemy()) enemies++;
            else friends++;
        }
        System.out.println("Contador actual -> Amigos: " + friends + " | Enemigos: " + enemies);
    }

    private static void showCharacters(ArrayList<Character> characters) {
        for (int i = 0; i < characters.size(); i++) {
            System.out.println("[" + i + "] - " + characters.get(i).toString());
        }
    }

    private static void saveGame(Hero hero, ArrayList<Character> characters) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(hero);
            oos.writeObject(characters);
            System.out.println("Partida guardada correctamente en " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }
    }
}
