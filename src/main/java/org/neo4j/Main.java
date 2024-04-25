package org.neo4j;

import org.neo4j.driver.Driver;
import org.neo4j.entities.Player;
import org.neo4j.entities.Team;
import org.neo4j.service.Service;
import org.neo4j.utils.Neo4jConnector;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Neo4jConnector connector = Neo4jConnector.getInstance();
        Driver driver = connector.getDriver();
        Service service = new Service(driver);
        Scanner input = new Scanner(System.in);

        System.out.println("Вас приветствует консольный клиент для работы с базой данных Redis!");
        printMainMenu();
        int choice;

        do {
            choice = input.nextInt();
            switch (choice) {
                case 1 -> {
                    System.out.println("Введите название команды: ");
                    String name = input.next();

                    System.out.println("Введите количество участников: ");
                    try {
                        int count = Integer.parseInt(input.next());
                        service.createTeam(new Team(name, count));
                        System.out.println("Создана команда с названием " + name);
                    } catch (NumberFormatException e) {
                        System.out.println("Не удалось создать команду, количество участников должно быть числом");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Ошибка: " + e.getMessage());
                    }
                }
                case 2 -> {
                    System.out.println("Введите имя игрока: ");
                    String firstName = input.next();

                    System.out.println("Введите фамилию игрока: ");
                    String lastName = input.next();

                    try {
                        service.createPlayer(new Player(firstName, lastName));
                        System.out.println("Игрок создан");
                    } catch (Exception e) {
                        System.out.println("Ошибка: " + e.getMessage());
                    }
                }
                case 3 -> {
                    System.out.println("Введите идентификатор команды: ");
                    String id = input.next();

                    System.out.println("Введите название команды: ");
                    String name = input.next();

                    System.out.println("Введите количество участников: ");
                    try {
                        int count = Integer.parseInt(input.next());
                        service.updateTeam(new Team(id, name, count));
                        System.out.println("Команда отредактирована");
                    } catch (NumberFormatException e) {
                        System.out.println("Не удалось создать команду, количество участников должно быть числом");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Ошибка при добавлении записи в коллекцию: " + e.getMessage());
                    }

                }
                case 4 -> {
                    System.out.println("Введите название метки: ");
                    String label = input.next();

                    System.out.println("Введите идентификатор: ");
                    String id = input.next();

                    try {
                        service.deleteNode(label, id);
                        System.out.println("Удаление успешно");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Ошибка при удалении: " + e.getMessage());
                    }
                }
                case 5 -> {
                    System.out.println("Введите метку: ");
                    String label = input.next();
                    service.getNodesByMark(label);
                }
                case 6 -> {
                    System.out.println("Введите название команды: ");
                    String name = input.next();

                    service.getTeamsPlayers(name);
                }
                case 7 -> {
                    System.out.println("Введите идентификатор команды: ");
                    String teamId = input.next();

                    System.out.println("Введите идентификатор игрока: ");
                    String playerId = input.next();

                    try {
                        service.addPlayerToTeam(teamId, playerId);
                        System.out.println("Связь успешно создана");
                    } catch (Exception e) {
                        System.out.println("Ошибка: " + e.getMessage());
                    }
                }
                default -> {
                    continue;
                }
            }
            printMainMenu();
        } while (choice != 0);

        connector.close();
        System.exit(0);
    }

    private static void printMainMenu() {
        System.out.println("Меню: ");
        System.out.println("1 - Создать команду");
        System.out.println("2 - Создать игрока");
        System.out.println("3 - Редактировать команду");
        System.out.println("4 - Удалить запись");
        System.out.println("5 - Получить все узлы по метке");
        System.out.println("6 - Получить список игроков команды");
        System.out.println("7 - Добавить игрока в команду");
        System.out.println("0 - Выход");
    }
}