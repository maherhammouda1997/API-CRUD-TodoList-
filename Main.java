package fr2.m2i.todoliste;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final TodoList todoList = TodoList.getInstance();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            afficherMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); 
            switch (choice) {
                case 1:
                    ajouterTodo();
                    break;
                case 2:
                    voirTodos();
                    break;
                case 3:
                    supprimerIndex();
                    break;
                case 4:
                    supprimerDernierTodo();
                    break;
                case 5:
                    System.out.println("À bientôt !");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Choix invalide.");
            }
        }
    }

    private static void afficherMenu() {
        System.out.println("------------------------");
        System.out.println("1. Ajouter un nouveau Todo");
        System.out.println("2. Voir les todos");
        System.out.println("3. Supprimer un todo par index");
        System.out.println("4. Supprimer le dernier Todo");
        System.out.println("5. Quitter");
        System.out.println("------------------------");
        System.out.print("Entrez votre choix : ");
    }

    private static void ajouterTodo() {
        System.out.print("Entrez la priorité (HAUTE, NORMALE, ou FAIBLE) : ");
        String input = scanner.nextLine().toUpperCase();
        try {
            Urgence urgence = Urgence.valueOf(input);
            System.out.print("Entrez le titre : ");
            String titre = scanner.nextLine();
            System.out.print("Entrez la description : ");
            String description = scanner.nextLine();
            Todo todo = new Todo(urgence, titre, description);
            todoList.ajouterTodo(todo);
            System.out.println("Todo ajouté !");
        } catch (IllegalArgumentException e) {
            System.out.println("Priorité invalide. Veuillez réessayer.");
        }
    }

    private static void voirTodos() {
        List<Todo> todos = todoList.voirTodos();
        if (todos.isEmpty()) {
            System.out.println("La liste de todo est vide.");
        } else {
            for (Todo todo : todos) {
                System.out.println(todo.getId() + " - " + todo.geturgence() + " - " + todo.gettitre() + " - "
                        + todo.getDescription());
            }
        }
    }

    private static void supprimerIndex() {
        System.out.print("Entrez l'index du todo à retirer : ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Todo todo = todoList.supprimerIndex(id);
        if (todo != null) {
            System.out.println("Todo retiré : " + todo.geturgence() + " - " + todo.gettitre());
        } /*else {
            System.out.println("Index invalide. Veuillez réessayer. ");
        }*/
    }

    private static void supprimerDernierTodo() {
        Todo todo = todoList.supprimerDernierTodo();
        if (todo != null) {
            System.out.println("Dernier todo retiré : " + todo.geturgence() + " - " + todo.gettitre());
        } else {
            System.out.println("La liste de todo est vide.");
        }
    }
}

