package dam.m6.uf2.Controller;

import java.util.List;
import java.util.Scanner;

import org.hibernate.*;

import dam.m6.uf2.Tables.deportes;
import dam.m6.uf2.Tables.deportistas;
import dam.m6.uf2.View.MainView;

public class Controller {
    public static void main(String[] args) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Scanner scanner = new Scanner(System.in);

        int option = -1;
        MainView view = new MainView(scanner);
        do {
            option = view.mainMenu();
            switch (option) {
                case 1:
                    deportes newSport = view.sportForm();
                    saveEntity(session, newSport);
                    break;
                case 2:
                    System.out.print("Nom de l'atleta: ");
                    String name = scanner.nextLine();

                    deportes sport = selectSport(session, scanner);
                    if (sport == null)
                        break;

                    deportistas athlete = new deportistas();
                    athlete.setNombre(name);
                    athlete.setSport(sport);

                    saveEntity(session, athlete);
                    break;
                case 3:
                    String searchName = view.askAthleteNameToSearch();

                    List<deportistas> athletes = getAthletesByName(session, searchName);

                    view.athleteList(athletes);
                    break;
                case 4:
                    deportes selectedSport = selectSport(session, scanner);
                    if (selectedSport == null)
                        break;

                    List<deportistas> athletesBySport = getAthletesBySport(session, selectedSport);

                    view.athleteList(athletesBySport);
                    break;
                case 5:
                    System.out.println("Sortint...");
                    break;
                default:
                    System.out.println("Opció no disponible.");
                    break;
            }
        } while (option != 5);
        session.close();
    }

    @SuppressWarnings("deprecation") // PUEDES COMENTAR SI NO TE FUNCIONA
    private static void saveEntity(Session session, Object entity) {
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        transaction.commit();
    }

    private static deportes selectSport(Session session, Scanner scanner) {
        List<deportes> sports = session.createQuery("from deportes", deportes.class).list();

        if (sports.isEmpty()) {
            System.out.println("No hi ha esports. Cal crear-ne un primer.");
            return null;
        }

        System.out.println("Esports disponibles:");
        for (deportes d : sports) {
            System.out.println(d.getCod() + " - " + d.getNombre());
        }

        System.out.print("Introdueix el codi de l'esport: ");
        int cod = scanner.nextInt();
        scanner.nextLine();

        return session.get(deportes.class, cod);
    }

    private static List<deportistas> getAthletesByName(Session session, String name) {
        return session.createQuery(
                "from deportistas d where lower(d.nombre) like :name",
                deportistas.class)
                .setParameter("name", "%" + name.toLowerCase() + "%")
                .list();
    }

    private static List<deportistas> getAthletesBySport(Session session, deportes sport) {
        return session.createQuery(
                "from deportistas d where d.sport = :sport",
                deportistas.class)
                .setParameter("sport", sport)
                .list();
    }

}
