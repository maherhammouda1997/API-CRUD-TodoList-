package fr2.m2i.todoliste;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

    public class TodoList implements AutoCloseable {
        private static Connection connection;
        private static TodoList instance = null;

        // Constructeur privé pour empêcher l'instanciation directe
        private TodoList() {
            try {
                // Connexion à la base de données
                String BASE = "jdbc:mysql://localhost:3306/maher";
                String USER = "root";
                String PASSWORD = "79911997mM.";
                connection = DriverManager.getConnection(BASE, USER, PASSWORD);
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        // Méthode pour récupérer l'instance unique de la classe TodoList
        public static TodoList getInstance() {
            if (instance == null) {
                instance = new TodoList();
            }
            return instance;
        }

        // Méthode pour ajouter un todo à la base de données
        public void ajouterTodo(Todo todo) {
            String QUERY_AJOUTER = "INSERT INTO Todo (urgence, titre, description) VALUES (?, ?, ?)";
            try {
                PreparedStatement statement = connection.prepareStatement(QUERY_AJOUTER);
                statement.setString(1, String.valueOf(todo.geturgence()));
                statement.setString(2, todo.gettitre());
                statement.setString(3, todo.getDescription());
                //statement.executeUpdate();
                statement.execute();


                // ajout de la ligne de commit
                connection.commit();
                System.out.println("Successfully commited changes to the database !");
            } catch (SQLException e) {
                try {
                    //
                    connection.rollback();
                    System.out.println("Successfully rolled back changes from the database !");
                } catch (SQLException e1) {
                    System.out.println("Could not rollback updates " + e1.getMessage());
                }
                System.out.println(e.getMessage());
            }
        }

        // Méthode pour AFFICHER tous les todos depuis la base de données
        public List<Todo> voirTodos() {
            List<Todo> todos = new ArrayList<>();
            String QUERY_AFFICHAGE = "SELECT * FROM Todo";
            try {
                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery(QUERY_AFFICHAGE);
                while (result.next()) {
                    Todo todo = new Todo(
                            result.getString("urgence"),
                            result.getString("titre"),
                            result.getString("description")
                    );
                    todo.setId(result.getInt("id"));
                    todos.add(todo);
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return todos;
        }

        // Méthode pour retirer et afficher un todo par index depuis la base de données
        public Todo supprimerIndex(int id) {
            String QUERY_SUPPRIMER_ID = "DELETE FROM Todo WHERE id = ?";
            Todo todo = null;
            try {
                PreparedStatement statement = connection.prepareStatement(QUERY_SUPPRIMER_ID);
                statement.setInt(1, id);
                int rowsDeleted = statement.executeUpdate();
                if (rowsDeleted > 0) {
                    todo = recupererId(id);
                }

                // ajout de la ligne de commit
                connection.commit();
                System.out.println("Successfully commited changes to the database !");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return todo;
        }

        // Méthode pour récupérer un todo par son id depuis la base de données
        private Todo recupererId(int id) {
            String QUERY_RECUPERER = "SELECT * FROM Todo WHERE id = ?";
            Todo todo = null;
            try {
                PreparedStatement statement = connection.prepareStatement(QUERY_RECUPERER);
                statement.setInt(1, id);
                ResultSet result = statement.executeQuery();
                if (result.next()) {
                    todo = new Todo(
                            result.getString("urgence"),
                            result.getString("titre"),
                            result.getString("description")
                    );
                    todo.setId(result.getInt("id"));
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return todo;

        }

        /*// Méthode pour retirer et afficher le dernier todo depuis la base de données
        public Todo supprimerDernierTodo() {
            String sql = "SELECT * FROM Todo ORDER BY id DESC LIMIT 1";
            Todo todo = null;
            try {
                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery(sql);
                if (result.next()) {
                    todo = new Todo(
                            result.getString("urgence"),
                            result.getString("titre"),
                            result.getString("description")
                    );
                    todo.setId(result.getInt("id"));
                    supprimerDernierTodo();
                }
                connection.commit();
                System.out.println("Successfully commited changes to the database !");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return todo;
        }*/

        // Méthode pour supprimer le dernier todo de la base de données
        // Méthode pour supprimer le dernier todo de la base de données
        public Todo supprimerDernierTodo() {
            String QUERY_SUPPRIMER_DERNIER = "DELETE FROM Todo WHERE id = (SELECT MAX(id) FROM (SELECT id FROM Todo) as Todo)";
            try {
                Statement statement = connection.createStatement();
                statement.executeUpdate(QUERY_SUPPRIMER_DERNIER);

                // ajout de la ligne de commit
                connection.commit();
                System.out.println("Successfully committed changes to the database !");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return null;
        }



        @Override
        public void close() throws Exception {
            this.connection.close();
        }
    }
