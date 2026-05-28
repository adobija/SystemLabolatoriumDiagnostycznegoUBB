package ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ubb.izi.skibs.IIb.SystemLabolatoriumDiagnostycznegoUBB.repository.UzytkownikRepo;

@SpringBootApplication
public class Application {




    public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Autowired
	private final UzytkownikRepo uzytkownikRepo;

	public Application(UzytkownikRepo uzytkownikRepo) {
		this.uzytkownikRepo = uzytkownikRepo;
	}

}
