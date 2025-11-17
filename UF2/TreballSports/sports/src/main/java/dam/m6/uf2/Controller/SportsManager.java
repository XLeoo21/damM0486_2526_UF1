package dam.m6.uf2.Controller;

import java.sql.Statement;
import java.util.List;

import dam.m6.uf2.Model.ConnectionManager;
import dam.m6.uf2.Model.Esport;
import dam.m6.uf2.Model.EsportDAO;
import dam.m6.uf2.View.MainView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SportsManager {
	Connection conn;

	public static void main(String[] args) throws SQLException {
		System.out.println("Conectant-se a la base de dades..." + System.getProperty("user.dir"));
		SportsManager controller = new SportsManager();
		controller.init();
	}

	private void init() throws SQLException {
		this.conn = ConnectionManager.getConnection("database.xml");
		int option = 0;

		EsportDAO EsportDAO = new EsportDAO(conn);

		while (option != 5) {
			MainView mainView = new MainView();
			option = mainView.mainMenu();
			switch (option) {
				case 1:
					String esportName = MainView.esportForm();
					Esport newEsport = new Esport(0, esportName);
					EsportDAO.add(newEsport);
					// mainView.showUsers(DeportistaPgDAO.getAll());
					break;

				case 2:

					break;
				case 3:
					break;
				case 4:
					List<Esport> esports = new EsportDAO(conn).getAll();
					MainView.LlistaEsports(esports);
					break;
				case 5:
					MainView.showMessage("Tancant connexió, adeeeuuuu!");
					conn.close();
					break;
			}
		}

		// Afegir Usuari
		// Llistar usuaris

	}
}
