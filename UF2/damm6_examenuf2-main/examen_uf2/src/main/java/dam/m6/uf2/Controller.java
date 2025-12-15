package dam.m6.uf2;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class Controller {
    public static void main(String[] args) throws SQLException {
        Connection conn = ConnectionManager.getConnection();
        Session session = HibernateUtil.getSessionFactory().openSession();

        String title = "";
        String author = "";
        int option = -1;

        LlibrePgDAO LlibreDAO = new LlibrePgDAO(conn);

        do {
            option = MainView.mainMenu();

            switch (option) {
                case 1:
                    List<Llibre> llibres = new LlibrePgDAO(conn).getAll();
                    MainView.showMessage("--------------------------------");
                    MainView.LlistaLlibres(llibres);
                    MainView.showMessage("--------------------------------");
                    break;
                case 2:
                    title = MainView.askTitle();
                    author = MainView.askAuthor();
                    Llibre llibre = new Llibre(0, title, author);
                    LlibreDAO.add(llibre);
                    break;
                case 3:
                    LlibreH newLlibre = MainView.llibreForm();
                    saveEntity(session, newLlibre);
                    break;
                case 4:
                    System.out.println("Sortint del programa.....");
                    break;
                default:
                    System.out.println("Opció no valida.");
            }

        } while (option != 4);
        session.close();
    }

    private static void saveEntity(Session session, Object entity) {
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        transaction.commit();
    }
}
