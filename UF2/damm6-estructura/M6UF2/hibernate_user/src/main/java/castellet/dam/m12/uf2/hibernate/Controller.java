package castellet.dam.m12.uf2.hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;
import java.util.Scanner;

public class Controller {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MainView mainView = new MainView(scanner); // Crea una instancia de MainView

        // Initialize Hibernate session factory
        Session session = HibernateUtil.getSessionFactory().openSession();

        int option = -1;

        do {
            try {
                option = mainView.showMenu(); // Usa el método de MainView

                switch (option) {
                    case 1: {
                        User newUser = mainView.userForm(); // Usa el método de MainView
                        //saveEntity(session, newUser);
                        break;
                    }
                    case 2: {
                        List<User> users = new java.util.ArrayList<User>();
                        //List<User> users = getAllUser(session);
                        if (users.isEmpty()) {
                            System.out.println("No hi ha users.");
                        } else {
                            System.out.println("Llista d'usuaris:");
                            for (User u : users) {
                                System.out.println(u);
                            }
                        }   
                        break;
                    }
                    case 3: {
                        String name = mainView.askForUserName(); // Usa el método de MainView
                        //List<User> users = findUserByName(session, name);
                        List<User> users = new java.util.ArrayList<User>();
                        mainView.userList(users); // Usa el método de MainView
                        break;
                    }

                    case 0: {
                        System.out.println("Saliendo...");
                        break;
                    }
                    default:
                        System.out.println("Opción no válida.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (option != 0);

        session.close();
    }

    // Métodos auxiliares para interactuar con la base de datos a través de
    // Hibernate
    @SuppressWarnings("deprecation") // PUEDES COMENTAR SI NO TE FUNCIONA
    // private static void saveEntity(Session session, Object entity) {
    //     Transaction transaction = session.beginTransaction();
    //     session.save(entity);
    //     transaction.commit();
    // }

    // private static List<User> getAllUser(Session session) {
    //     session.clear();
    //     // Query the User entity (use entity name, not table name)
    //     Query<User> query = session.createQuery("FROM User", User.class);
    //     List<User> users = query.getResultList();
    //     if (users.isEmpty()) {
    //         System.out.println("No hi ha users.");
    //     } 
        
    //     return users;
    // }

    private static List<User> findUserByName(Session session, String name) {
        session.clear();
        Query<User> query = session.createQuery("FROM User WHERE name LIKE :name", User.class);
        query.setParameter("name", "%" + name + "%");
        return query.getResultList();
    }

}
