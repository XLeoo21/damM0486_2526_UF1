package dam.m6.uf2.Controller;

import java.sql.Statement;
import java.util.List;

import dam.m6.uf2.Model.Atleta;
import dam.m6.uf2.Model.AtletaDAO;
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
		int option = 0, codEsport = 0;
		String name = "", atletaName = "", esportName = "";

		EsportDAO EsportDAO = new EsportDAO(conn);
		AtletaDAO AtletaDAO = new AtletaDAO(conn);
		List<Esport> esports = new EsportDAO(conn).getAll();

		while (option != 5) {
			MainView mainView = new MainView();
			option = mainView.mainMenu();
			switch (option) {
				case 1:
					esportName = MainView.esportForm();
					Esport newEsport = new Esport(0, esportName);
					EsportDAO.add(newEsport);
					break;

				case 2:
					atletaName = MainView.atletaForm();
					MainView.showMessage("---------------------------------------------");
					MainView.LlistaEsports(esports);
					MainView.showMessage("---------------------------------------------");
					codEsport = MainView.askCodEsport();
					Atleta newAtleta = new Atleta(0, atletaName, codEsport);
					AtletaDAO.add(newAtleta);
					break;
				case 3:
					name = MainView.askName();
					List<Atleta> atleta = new AtletaDAO(conn).getOne(name);
					MainView.showMessage("---------------------------------------------");
					MainView.LlistaAtletas(atleta);
					MainView.showMessage("---------------------------------------------");
					break;
				case 4:
					MainView.showMessage("---------------------------------------------");
					MainView.LlistaEsports(esports);
					MainView.showMessage("---------------------------------------------");
					codEsport = MainView.askCodEsport();
					List<Atleta> atletas = new AtletaDAO(conn).getAll(codEsport);
					MainView.showMessage("---------------------------------------------");
					MainView.LlistaAtletas(atletas);
					MainView.showMessage("---------------------------------------------");
					break;
				case 5:
					MainView.showMessage("Tancant connexió, adeeeuuuu!");
					conn.close();
					break;
			}
		}

	}
}


