import java.io.IOException;
import java.io.RandomAccessFile;

public class EscriptorRandomAccessFile {

	public static void main(String[] args) {
		Pais[] paisos = new Pais[5];

		paisos[0] = new Pais("Albània", "ALB", "Tirana");
		paisos[1] = new Pais("Bòsnia i Hercegovina", "BIH", "Sarajevo");
		paisos[2] = new Pais("Croàcia", "HRV", "Zagreb");
		paisos[3] = new Pais("Montenegro", "MNE", "Podgorica");
		paisos[4] = new Pais("Sèrbia", "SRB", "Belgrad");
		paisos[0].setPoblacio(3582205);
		paisos[1].setPoblacio(4498976);
		paisos[2].setPoblacio(4800000);
		paisos[3].setPoblacio(630548);
		paisos[4].setPoblacio(8196411);

		StringBuilder b = null;
		try (RandomAccessFile fitxer = new RandomAccessFile("paisos.dat", "rw");) {
			for (int i=0; i<paisos.length; i++) {
				b = new StringBuilder(paisos[i].getNom());
				b.setLength(40); //Asigna mida de 40 caracters al contingut de StringBuilder
				fitxer.writeInt(i+1);						//id  ------> int  (4 bytes)
				fitxer.writeChars(b.toString());			//nom ------> char (2 bytes) * 40 caràcters
				fitxer.writeChars(paisos[i].getCodiISO());	//Codi ISO -> char (2 bytes) * 3 caràcters
				b = new StringBuilder(paisos[i].getCapital());
				b.setLength(40);
				fitxer.writeChars(b.toString());			//Capital --> char (2 bytes) * 40 caràcters
				fitxer.writeInt(paisos[i].getPoblacio());	//població -> int  (4 bytes)
												//total per país: 174 bytes
			} // Total: 174 bytes * 5 països = 870 bytes
		} catch (IOException e) {
			System.err.println(e);
		}
	}
}

