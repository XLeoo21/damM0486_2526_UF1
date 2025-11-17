package dam.m6.uf2.Model;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;

public class ReadConfigXML {
	private String host;
	private String port;
	private String user;
	private String password;
	private String database;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}

	public String getDatabase() {
		return database;
	}

	public ReadConfigXML(String config_file) {
		File file = new File(config_file);

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);

			host = doc.getElementsByTagName("host").item(0).getTextContent();
			port = doc.getElementsByTagName("port").item(0).getTextContent();
			user = doc.getElementsByTagName("user").item(0).getTextContent();
			password = doc.getElementsByTagName("password").item(0).getTextContent();
			database = doc.getElementsByTagName("database").item(0).getTextContent();
System.out.println("host");
		} catch (Exception e) {
			System.out.println("Error" + e.getMessage());
		}
	}
}
