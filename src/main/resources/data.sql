DROP TABLE IF EXISTS LogZdarzen;
DROP TABLE IF EXISTS Wynik_Badania;
DROP TABLE IF EXISTS ZleceniePozycja;
DROP TABLE IF EXISTS Zlecenie_Badania;
DROP TABLE IF EXISTS Probka;
DROP TABLE IF EXISTS Badanie_Rodzaj;
DROP TABLE IF EXISTS SprzetLaboratoryjny;
DROP TABLE IF EXISTS Laboratorium;
DROP TABLE IF EXISTS Lekarz;
DROP TABLE IF EXISTS Pacjent;
DROP TABLE IF EXISTS Uzytkownik;
DROP TABLE IF EXISTS Rola;
DROP TABLE IF EXISTS Status_Zlecenia;
DROP TABLE IF EXISTS StatusPozycji;
DROP TABLE IF EXISTS TypZdarzenia;
DROP TABLE IF EXISTS Typ_Materialu;
DROP TABLE IF EXISTS Plec;


CREATE TABLE Rola (
    id_rola IDENTITY PRIMARY KEY,
    kod VARCHAR(20) NOT NULL UNIQUE,
    nazwa VARCHAR(100) NOT NULL
);

CREATE TABLE Plec (
    id_plec IDENTITY PRIMARY KEY,
    kod VARCHAR(10) NOT NULL UNIQUE,
    opis VARCHAR(50) NOT NULL
);

CREATE TABLE Status_Zlecenia (
    id_status_zlecenia IDENTITY PRIMARY KEY,
    kod VARCHAR(20) NOT NULL UNIQUE,
    opis VARCHAR(100) NOT NULL
);

CREATE TABLE StatusPozycji (
    id_status_pozycji IDENTITY PRIMARY KEY,
    kod VARCHAR(20) NOT NULL UNIQUE,
    opis VARCHAR(100) NOT NULL
);

CREATE TABLE TypZdarzenia (
    id_typ_zdarzenia IDENTITY PRIMARY KEY,
    kod VARCHAR(50) NOT NULL UNIQUE,
    opis VARCHAR(200) NOT NULL
);

CREATE TABLE Typ_Materialu (
    id_typ_materialu IDENTITY PRIMARY KEY,
    kod VARCHAR(50) NOT NULL UNIQUE,
    opis VARCHAR(200) NOT NULL
);


CREATE TABLE Uzytkownik (
    id_uzytkownika IDENTITY PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    haslo_hash VARCHAR(255) NOT NULL,
    id_rola BIGINT NOT NULL,
    data_utworzenia TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_uzytkownik_rola
        FOREIGN KEY (id_rola) REFERENCES Rola(id_rola)
);


CREATE TABLE Pacjent (
    id_pacjenta BIGINT PRIMARY KEY,
    imie VARCHAR(100) NOT NULL,
    nazwisko VARCHAR(100) NOT NULL,
    pesel VARCHAR(11) NOT NULL UNIQUE,
    telefon VARCHAR(20),
    data_urodzenia DATE,
    id_plec BIGINT NOT NULL,
    adres VARCHAR(255),
    numer_karty_pacjenta VARCHAR(50),
    CONSTRAINT fk_pacjent_uzytkownik
        FOREIGN KEY (id_pacjenta) REFERENCES Uzytkownik(id_uzytkownika),
    CONSTRAINT fk_pacjent_plec
        FOREIGN KEY (id_plec) REFERENCES Plec(id_plec)
);


CREATE TABLE Lekarz (
    id_lekarza BIGINT PRIMARY KEY,
    imie VARCHAR(100) NOT NULL,
    nazwisko VARCHAR(100) NOT NULL,
    pesel VARCHAR(11),
    telefon VARCHAR(20),
    data_urodzenia DATE,
    id_plec BIGINT,
    adres VARCHAR(255),
    specjalizacja VARCHAR(100),
    numer_prawa_wykonywania VARCHAR(50),
    oddzial VARCHAR(100),
    CONSTRAINT fk_lekarz_uzytkownik
        FOREIGN KEY (id_lekarza) REFERENCES Uzytkownik(id_uzytkownika),
    CONSTRAINT fk_lekarz_plec
        FOREIGN KEY (id_plec) REFERENCES Plec(id_plec)
);


CREATE TABLE Laboratorium (
    id_laboratorium IDENTITY PRIMARY KEY,
    nazwa VARCHAR(255) NOT NULL,
    adres VARCHAR(255),
    telefon VARCHAR(20)
);

CREATE TABLE SprzetLaboratoryjny (
    id_sprzetu IDENTITY PRIMARY KEY,
    nazwa VARCHAR(255) NOT NULL,
    typ VARCHAR(100),
    numer_seryjny VARCHAR(100),
    id_laboratorium BIGINT,
    CONSTRAINT fk_sprzet_laboratorium
        FOREIGN KEY (id_laboratorium) REFERENCES Laboratorium(id_laboratorium)
);


CREATE TABLE Badanie_Rodzaj (
    id_badania_rodzaj IDENTITY PRIMARY KEY,
    kod_badania VARCHAR(50) NOT NULL UNIQUE,
    nazwa VARCHAR(255) NOT NULL,
    opis VARCHAR(1000),
    jednostka VARCHAR(50),
    wartosc_min_referencyjna DECIMAL(10,2),
    wartosc_max_referencyjna DECIMAL(10,2),
    cena DECIMAL(10,2),
    id_typ_materialu BIGINT,
    CONSTRAINT fk_badanie_typ_materialu
        FOREIGN KEY (id_typ_materialu) REFERENCES Typ_Materialu(id_typ_materialu)
);

CREATE TABLE Probka (
    id_probki IDENTITY PRIMARY KEY,
    kod_probki VARCHAR(50) NOT NULL UNIQUE,
    id_typ_materialu BIGINT,
    data_pobrania TIMESTAMP,
    miejsce_pobrania VARCHAR(255),
    id_pacjenta BIGINT NOT NULL,
    pobrane_przez BIGINT,
    CONSTRAINT fk_probka_pacjent
        FOREIGN KEY (id_pacjenta) REFERENCES Pacjent(id_pacjenta),
    CONSTRAINT fk_probka_lekarz
        FOREIGN KEY (pobrane_przez) REFERENCES Lekarz(id_lekarza),
    CONSTRAINT fk_probka_typ_materialu
        FOREIGN KEY (id_typ_materialu) REFERENCES Typ_Materialu(id_typ_materialu)
);


CREATE TABLE Zlecenie_Badania (
    id_zlecenia IDENTITY PRIMARY KEY,
    id_pacjenta BIGINT NOT NULL,
    id_lekarza BIGINT NOT NULL,
    data_zlecenia TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    id_status_zlecenia BIGINT NOT NULL,
    uwagi_kliniczne VARCHAR(2000),
    CONSTRAINT fk_zlecenie_pacjent
        FOREIGN KEY (id_pacjenta) REFERENCES Pacjent(id_pacjenta),
    CONSTRAINT fk_zlecenie_lekarz
        FOREIGN KEY (id_lekarza) REFERENCES Lekarz(id_lekarza),
    CONSTRAINT fk_zlecenie_status
        FOREIGN KEY (id_status_zlecenia) REFERENCES Status_Zlecenia(id_status_zlecenia)
);

CREATE TABLE ZleceniePozycja (
    id_pozycji IDENTITY PRIMARY KEY,
    id_zlecenia BIGINT NOT NULL,
    id_badania_rodzaj BIGINT NOT NULL,
    id_probki BIGINT,
    id_status_pozycji BIGINT NOT NULL,
    CONSTRAINT fk_pozycja_zlecenie
        FOREIGN KEY (id_zlecenia) REFERENCES Zlecenie_Badania(id_zlecenia),
    CONSTRAINT fk_pozycja_badanie
        FOREIGN KEY (id_badania_rodzaj) REFERENCES Badanie_Rodzaj(id_badania_rodzaj),
    CONSTRAINT fk_pozycja_probka
        FOREIGN KEY (id_probki) REFERENCES Probka(id_probki),
    CONSTRAINT fk_pozycja_status
        FOREIGN KEY (id_status_pozycji) REFERENCES StatusPozycji(id_status_pozycji)
);


CREATE TABLE Wynik_Badania (
    id_wyniku BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,

    id_pacjenta BIGINT NOT NULL,
    id_badania_rodzaj BIGINT NOT NULL,
    id_zlecenia BIGINT,

    wartosc_liczbowa DECIMAL(10,2),
    wartosc_opisowa VARCHAR(2000),
    jednostka VARCHAR(50),

    flaga_nieprawidlowy BOOLEAN NOT NULL DEFAULT FALSE,
    data_wykonania TIMESTAMP NOT NULL,
    wykonane_przez BIGINT,

    komentarz_diagnosty VARCHAR(2000),

    CONSTRAINT fk_wynik_pacjent
        FOREIGN KEY (id_pacjenta)
        REFERENCES Pacjent(id_pacjenta),

    CONSTRAINT fk_wynik_badanie
        FOREIGN KEY (id_badania_rodzaj)
        REFERENCES Badanie_Rodzaj(id_badania_rodzaj),

    CONSTRAINT fk_wynik_zlecenie
        FOREIGN KEY (id_zlecenia)
        REFERENCES Zlecenie_Badania(id_zlecenia),

    CONSTRAINT fk_wynik_uzytkownik
        FOREIGN KEY (wykonane_przez)
        REFERENCES Uzytkownik(id_uzytkownika)
);

CREATE TABLE LogZdarzen (
    id_logu IDENTITY PRIMARY KEY,
    id_uzytkownika BIGINT,
    data_zdarzenia TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    id_typ_zdarzenia BIGINT,
    opis VARCHAR(2000),
    CONSTRAINT fk_log_uzytkownik
        FOREIGN KEY (id_uzytkownika) REFERENCES Uzytkownik(id_uzytkownika),
    CONSTRAINT fk_log_typ
        FOREIGN KEY (id_typ_zdarzenia) REFERENCES TypZdarzenia(id_typ_zdarzenia)
);

INSERT INTO Rola (kod, nazwa) VALUES
 ('ADMIN', 'Administrator systemu'),
 ('LEKARZ', 'Pracownik laboratorium'),
 ('PACJENT', 'Pacjent');

INSERT INTO Plec (kod, opis) VALUES
 ('M', 'Mężczyzna'),
 ('F', 'Kobieta'),
 ('X', 'Inna / niepodana');

INSERT INTO Status_Zlecenia (kod, opis) VALUES
 ('NOWE', 'Nowe zlecenie'),
 ('W_TRAKCIE', 'Zlecenie w trakcie realizacji'),
 ('ZAKONCZONE', 'Zlecenie zakończone');

INSERT INTO StatusPozycji (kod, opis) VALUES
 ('OCZEKUJE', 'Oczekuje na realizację'),
 ('WYKONYWANE', 'W trakcie wykonywania'),
 ('ZAKONCZONE', 'Zakończone');

INSERT INTO TypZdarzenia (kod, opis) VALUES
 ('LOGOWANIE', 'Logowanie do systemu'),
 ('UTWORZENIE_ZLECENIA', 'Utworzenie zlecenia badania'),
 ('EDYCJA_WYNIKU', 'Edycja wyniku badania');

INSERT INTO Typ_Materialu (kod, opis) VALUES
 ('KREW', 'Krew żylna'),
 ('MOCZ', 'Mocz'),
 ('SERO', 'Surowica');


INSERT INTO Uzytkownik (email, haslo_hash, id_rola) VALUES
 ('admin@lab.pl', '$2a$10$zUGzFNJFxxUbwHs3VNXf5eRuw7WeolDpFmshe49lZGghM9QJvmQ1m', 1),
 ('lekarz1@lab.pl', '$2a$10$zUGzFNJFxxUbwHs3VNXf5eRuw7WeolDpFmshe49lZGghM9QJvmQ1m', 2),
 ('pacjent4@lab.pl', '$2a$10$zUGzFNJFxxUbwHs3VNXf5eRuw7WeolDpFmshe49lZGghM9QJvmQ1m', 3);


INSERT INTO Uzytkownik (email, haslo_hash, id_rola) VALUES
 ('pacjent1@lab.pl', '$2a$10$zUGzFNJFxxUbwHs3VNXf5eRuw7WeolDpFmshe49lZGghM9QJvmQ1m', 3), --adminadmin
 ('pacjent2@lab.pl', '$2a$10$zUGzFNJFxxUbwHs3VNXf5eRuw7WeolDpFmshe49lZGghM9QJvmQ1m', 3), --adminadmin
 ('pacjent3@lab.pl', '$2a$10$zUGzFNJFxxUbwHs3VNXf5eRuw7WeolDpFmshe49lZGghM9QJvmQ1m', 3); --adminadmin


INSERT INTO Pacjent (id_pacjenta, imie, nazwisko, pesel, telefon, data_urodzenia, id_plec, adres, numer_karty_pacjenta) VALUES
 (4, 'Jan', 'Kowalski', '80010112345', '500100100', DATE '1980-01-01', 1, 'Warszawa, ul. Prosta 1', 'KARTA001'),
 (5, 'Anna', 'Nowak', '90020254321', '500200200', DATE '1990-02-02', 2, 'Warszawa, ul. Długa 2', 'KARTA002'),
 (6, 'Piotr', 'Zieliński', '85030367890', '500300300', DATE '1985-03-03', 1, 'Warszawa, ul. Krótka 3', 'KARTA003');


INSERT INTO Uzytkownik (email, haslo_hash, id_rola) VALUES
 ('lekarz2@lab.pl', '$2a$10$zUGzFNJFxxUbwHs3VNXf5eRuw7WeolDpFmshe49lZGghM9QJvmQ1m', 2),
 ('lekarz3@lab.pl', '$2a$10$zUGzFNJFxxUbwHs3VNXf5eRuw7WeolDpFmshe49lZGghM9QJvmQ1m', 2);


INSERT INTO Lekarz (id_lekarza, imie, nazwisko, pesel, telefon, data_urodzenia, id_plec, adres, specjalizacja, numer_prawa_wykonywania, oddzial) VALUES
 (3, 'Marek', 'Wiśniewski', '70010111111', '501111111', DATE '1970-01-01', 1, 'Warszawa, ul. Lekarska 1', 'Internista', 'PWZ12345', 'Interna'),
 (7, 'Katarzyna', 'Lewandowska', '75020222222', '502222222', DATE '1975-02-02', 2, 'Warszawa, ul. Szpitalna 2', 'Pediatra', 'PWZ54321', 'Pediatria'),
 (8, 'Tomasz', 'Kaczmarek', '72030333333', '503333333', DATE '1972-03-03', 1, 'Warszawa, ul. Zdrowa 3', 'Chirurg', 'PWZ67890', 'Chirurgia');


INSERT INTO Laboratorium (nazwa, adres, telefon) VALUES
 ('Laboratorium Centralne', 'Warszawa, ul. Laboratorna 1', '222100100'),
 ('Laboratorium Szpitalne A', 'Warszawa, ul. Szpitalna 10', '222200200'),
 ('Laboratorium Szpitalne B', 'Warszawa, ul. Szpitalna 20', '222300300');


INSERT INTO SprzetLaboratoryjny (nazwa, typ, numer_seryjny, id_laboratorium) VALUES
 ('Analizator hematologiczny', 'Hematologia', 'HEMA-001', 1),
 ('Analizator biochemiczny', 'Biochemia', 'BIO-001', 1),
 ('Centrifuga', 'Ogólny', 'CENT-001', 2);


INSERT INTO Badanie_Rodzaj
(kod_badania, nazwa, opis, jednostka,
 wartosc_min_referencyjna, wartosc_max_referencyjna,
 cena, id_typ_materialu)
VALUES

('HGB',
 'Hemoglobina',
 'Oznaczenie stężenia hemoglobiny we krwi.',
 'g/dL',
 12.00, 17.00,
 12.00, 1),

('RBC',
 'Erytrocyty',
 'Liczba krwinek czerwonych w próbce krwi.',
 'mln/µL',
 4.00, 5.80,
 12.00, 1),

('WBC',
 'Leukocyty',
 'Liczba krwinek białych w próbce krwi.',
 'tys./µL',
 4.00, 10.00,
 12.00, 1),

('PLT',
 'Płytki krwi',
 'Liczba płytek krwi uczestniczących w procesie krzepnięcia.',
 'tys./µL',
 150.00, 400.00,
 12.00, 1),

('HCT',
 'Hematokryt',
 'Procentowy udział elementów morfotycznych we krwi.',
 '%',
 36.00, 50.00,
 12.00, 1),

('GLU',
 'Glukoza',
 'Oznaczenie stężenia glukozy we krwi.',
 'mg/dL',
 70.00, 99.00,
 15.00, 1),

('CRP',
 'Białko C-reaktywne',
 'Wskaźnik wykorzystywany w ocenie procesu zapalnego.',
 'mg/L',
 0.00, 5.00,
 25.00, 1),

('OB',
 'Odczyn Biernackiego',
 'Nieswoisty wskaźnik procesu zapalnego.',
 'mm/h',
 0.00, 20.00,
 15.00, 1),

('CREA',
 'Kreatynina',
 'Oznaczenie pomocne w ocenie czynności nerek.',
 'mg/dL',
 0.60, 1.30,
 18.00, 3),

('UREA',
 'Mocznik',
 'Oznaczenie produktu przemiany białek.',
 'mg/dL',
 15.00, 45.00,
 18.00, 3),

('NA',
 'Sód',
 'Oznaczenie stężenia sodu.',
 'mmol/L',
 135.00, 145.00,
 16.00, 3),

('K',
 'Potas',
 'Oznaczenie stężenia potasu.',
 'mmol/L',
 3.50, 5.10,
 16.00, 3),

('CA',
 'Wapń całkowity',
 'Oznaczenie stężenia wapnia całkowitego.',
 'mg/dL',
 8.50, 10.50,
 18.00, 3),

('ALT',
 'Aminotransferaza alaninowa',
 'Enzym wykorzystywany w diagnostyce funkcji wątroby.',
 'U/L',
 0.00, 45.00,
 20.00, 3),

('AST',
 'Aminotransferaza asparaginianowa',
 'Enzym obecny między innymi w wątrobie, sercu i mięśniach.',
 'U/L',
 0.00, 40.00,
 20.00, 3),

('BIL',
 'Bilirubina całkowita',
 'Oznaczenie bilirubiny całkowitej.',
 'mg/dL',
 0.20, 1.20,
 20.00, 3),

('CHOL',
 'Cholesterol całkowity',
 'Podstawowy parametr profilu lipidowego.',
 'mg/dL',
 0.00, 190.00,
 20.00, 3),

('HDL',
 'Cholesterol HDL',
 'Oznaczenie frakcji HDL cholesterolu.',
 'mg/dL',
 40.00, 100.00,
 20.00, 3),

('LDL',
 'Cholesterol LDL',
 'Oznaczenie frakcji LDL cholesterolu.',
 'mg/dL',
 0.00, 115.00,
 20.00, 3),

('TSH',
 'Hormon tyreotropowy',
 'Podstawowe oznaczenie stosowane w ocenie czynności tarczycy.',
 'µIU/mL',
 0.27, 4.20,
 30.00, 3);


INSERT INTO Probka (kod_probki, id_typ_materialu, data_pobrania, miejsce_pobrania, id_pacjenta, pobrane_przez) VALUES
 ('PRB-001', 1, CURRENT_TIMESTAMP, 'Gabinet zabiegowy 1', 4, 3),
 ('PRB-002', 1, CURRENT_TIMESTAMP, 'Gabinet zabiegowy 2', 5, 7),
 ('PRB-003', 2, CURRENT_TIMESTAMP, 'Punkt pobrań', 6, 8);


INSERT INTO Zlecenie_Badania (id_pacjenta, id_lekarza, data_zlecenia, id_status_zlecenia, uwagi_kliniczne) VALUES
 (4, 3, CURRENT_TIMESTAMP, 1, 'Kontrola po infekcji'),
 (5, 7, CURRENT_TIMESTAMP, 2, 'Podejrzenie cukrzycy'),
 (6, 8, CURRENT_TIMESTAMP, 1, 'Badania profilaktyczne');


INSERT INTO ZleceniePozycja (id_zlecenia, id_badania_rodzaj, id_probki, id_status_pozycji) VALUES
 (1, 1, 1, 2),  -- morfologia dla pacjenta 4
 (2, 2, 2, 1),  -- glukoza dla pacjenta 5
 (3, 3, 3, 1);  -- CRP dla pacjenta 6


INSERT INTO Wynik_Badania (
    id_pacjenta,
    id_badania_rodzaj,
    id_zlecenia,
    wartosc_liczbowa,
    wartosc_opisowa,
    jednostka,
    flaga_nieprawidlowy,
    data_wykonania,
    wykonane_przez,
    komentarz_diagnosty
)
VALUES
    (4, 1, NULL, 6.50, 'W normie', '10^9/L', FALSE, TIMESTAMP '2026-06-17 22:17:04.507027', 2, 'Wynik prawidłowy.'),
    (4, 1, NULL, 14.00, CONCAT('Materiał oceniono jako odpowiedni do wykonania oznaczenia.', CHAR(10), CHAR(10), 'Pomiar wykonano zgodnie z obowiązującą procedurą.', CHAR(10), CHAR(10), 'Uwagi do wyniku:'), 'g/dL', FALSE, TIMESTAMP '2026-06-17 22:18:18.511854', 2, CONCAT('Materiał został oceniony jako odpowiedni do wykonania oznaczeń.', CHAR(10), CHAR(10), 'Badania wykonano zgodnie z obowiązującymi procedurami laboratoryjnymi.', CHAR(10), CHAR(10), 'Uzyskane wyniki należy interpretować z uwzględnieniem obrazu klinicznego pacjenta.', CHAR(10), CHAR(10), 'Wyniki oznaczone jako nieprawidłowe wymagają oceny przez lekarza prowadzącego.', CHAR(10), CHAR(10), 'Uwagi dodatkowe:')),
    (4, 2, NULL, 24.00, CONCAT('Materiał oceniono jako odpowiedni do wykonania oznaczenia.', CHAR(10), CHAR(10), 'Pomiar wykonano zgodnie z obowiązującą procedurą.', CHAR(10), CHAR(10), 'Uwagi do wyniku:'), 'mln/µL', TRUE, TIMESTAMP '2026-06-17 22:18:18.537137', 2, CONCAT('Materiał został oceniony jako odpowiedni do wykonania oznaczeń.', CHAR(10), CHAR(10), 'Badania wykonano zgodnie z obowiązującymi procedurami laboratoryjnymi.', CHAR(10), CHAR(10), 'Uzyskane wyniki należy interpretować z uwzględnieniem obrazu klinicznego pacjenta.', CHAR(10), CHAR(10), 'Wyniki oznaczone jako nieprawidłowe wymagają oceny przez lekarza prowadzącego.', CHAR(10), CHAR(10), 'Uwagi dodatkowe:')),
    (4, 3, NULL, 12.00, CONCAT('Materiał oceniono jako odpowiedni do wykonania oznaczenia.', CHAR(10), CHAR(10), 'Pomiar wykonano zgodnie z obowiązującą procedurą.', CHAR(10), CHAR(10), 'Uwagi do wyniku:'), 'tys./µL', TRUE, TIMESTAMP '2026-06-17 22:18:18.540138', 2, CONCAT('Materiał został oceniony jako odpowiedni do wykonania oznaczeń.', CHAR(10), CHAR(10), 'Badania wykonano zgodnie z obowiązującymi procedurami laboratoryjnymi.', CHAR(10), CHAR(10), 'Uzyskane wyniki należy interpretować z uwzględnieniem obrazu klinicznego pacjenta.', CHAR(10), CHAR(10), 'Wyniki oznaczone jako nieprawidłowe wymagają oceny przez lekarza prowadzącego.', CHAR(10), CHAR(10), 'Uwagi dodatkowe:')),
    (4, 4, NULL, 13.00, CONCAT('Materiał oceniono jako odpowiedni do wykonania oznaczenia.', CHAR(10), CHAR(10), 'Pomiar wykonano zgodnie z obowiązującą procedurą.', CHAR(10), CHAR(10), 'Uwagi do wyniku:'), 'tys./µL', TRUE, TIMESTAMP '2026-06-17 22:18:18.542654', 2, CONCAT('Materiał został oceniony jako odpowiedni do wykonania oznaczeń.', CHAR(10), CHAR(10), 'Badania wykonano zgodnie z obowiązującymi procedurami laboratoryjnymi.', CHAR(10), CHAR(10), 'Uzyskane wyniki należy interpretować z uwzględnieniem obrazu klinicznego pacjenta.', CHAR(10), CHAR(10), 'Wyniki oznaczone jako nieprawidłowe wymagają oceny przez lekarza prowadzącego.', CHAR(10), CHAR(10), 'Uwagi dodatkowe:')),
    (4, 5, NULL, 1222.00, CONCAT('Materiał oceniono jako odpowiedni do wykonania oznaczenia.', CHAR(10), CHAR(10), 'Pomiar wykonano zgodnie z obowiązującą procedurą.', CHAR(10), CHAR(10), 'Uwagi do wyniku:'), '%', TRUE, TIMESTAMP '2026-06-17 22:18:18.546166', 2, CONCAT('Materiał został oceniony jako odpowiedni do wykonania oznaczeń.', CHAR(10), CHAR(10), 'Badania wykonano zgodnie z obowiązującymi procedurami laboratoryjnymi.', CHAR(10), CHAR(10), 'Uzyskane wyniki należy interpretować z uwzględnieniem obrazu klinicznego pacjenta.', CHAR(10), CHAR(10), 'Wyniki oznaczone jako nieprawidłowe wymagają oceny przez lekarza prowadzącego.', CHAR(10), CHAR(10), 'Uwagi dodatkowe:')),
    (4, 6, NULL, 99.00, CONCAT('Materiał oceniono jako odpowiedni do wykonania oznaczenia.', CHAR(10), CHAR(10), 'Pomiar wykonano zgodnie z obowiązującą procedurą.', CHAR(10), CHAR(10), 'Uwagi do wyniku:'), 'mg/dL', FALSE, TIMESTAMP '2026-06-17 22:18:18.548165', 2, CONCAT('Materiał został oceniony jako odpowiedni do wykonania oznaczeń.', CHAR(10), CHAR(10), 'Badania wykonano zgodnie z obowiązującymi procedurami laboratoryjnymi.', CHAR(10), CHAR(10), 'Uzyskane wyniki należy interpretować z uwzględnieniem obrazu klinicznego pacjenta.', CHAR(10), CHAR(10), 'Wyniki oznaczone jako nieprawidłowe wymagają oceny przez lekarza prowadzącego.', CHAR(10), CHAR(10), 'Uwagi dodatkowe:')),
    (4, 7, NULL, 2.00, CONCAT('Materiał oceniono jako odpowiedni do wykonania oznaczenia.', CHAR(10), CHAR(10), 'Pomiar wykonano zgodnie z obowiązującą procedurą.', CHAR(10), CHAR(10), 'Uwagi do wyniku:'), 'mg/L', FALSE, TIMESTAMP '2026-06-17 22:18:18.550168', 2, CONCAT('Materiał został oceniony jako odpowiedni do wykonania oznaczeń.', CHAR(10), CHAR(10), 'Badania wykonano zgodnie z obowiązującymi procedurami laboratoryjnymi.', CHAR(10), CHAR(10), 'Uzyskane wyniki należy interpretować z uwzględnieniem obrazu klinicznego pacjenta.', CHAR(10), CHAR(10), 'Wyniki oznaczone jako nieprawidłowe wymagają oceny przez lekarza prowadzącego.', CHAR(10), CHAR(10), 'Uwagi dodatkowe:')),
    (4, 8, NULL, 21.00, CONCAT('Materiał oceniono jako odpowiedni do wykonania oznaczenia.', CHAR(10), CHAR(10), 'Pomiar wykonano zgodnie z obowiązującą procedurą.', CHAR(10), CHAR(10), 'Uwagi do wyniku:'), 'mm/h', TRUE, TIMESTAMP '2026-06-17 22:18:18.552679', 2, CONCAT('Materiał został oceniony jako odpowiedni do wykonania oznaczeń.', CHAR(10), CHAR(10), 'Badania wykonano zgodnie z obowiązującymi procedurami laboratoryjnymi.', CHAR(10), CHAR(10), 'Uzyskane wyniki należy interpretować z uwzględnieniem obrazu klinicznego pacjenta.', CHAR(10), CHAR(10), 'Wyniki oznaczone jako nieprawidłowe wymagają oceny przez lekarza prowadzącego.', CHAR(10), CHAR(10), 'Uwagi dodatkowe:')),
    (4, 9, NULL, 1.00, CONCAT('Materiał oceniono jako odpowiedni do wykonania oznaczenia.', CHAR(10), CHAR(10), 'Pomiar wykonano zgodnie z obowiązującą procedurą.', CHAR(10), CHAR(10), 'Uwagi do wyniku:'), 'mg/dL', FALSE, TIMESTAMP '2026-06-17 22:18:18.556188', 2, CONCAT('Materiał został oceniony jako odpowiedni do wykonania oznaczeń.', CHAR(10), CHAR(10), 'Badania wykonano zgodnie z obowiązującymi procedurami laboratoryjnymi.', CHAR(10), CHAR(10), 'Uzyskane wyniki należy interpretować z uwzględnieniem obrazu klinicznego pacjenta.', CHAR(10), CHAR(10), 'Wyniki oznaczone jako nieprawidłowe wymagają oceny przez lekarza prowadzącego.', CHAR(10), CHAR(10), 'Uwagi dodatkowe:'));


INSERT INTO LogZdarzen (id_uzytkownika, data_zdarzenia, id_typ_zdarzenia, opis) VALUES
 (1, CURRENT_TIMESTAMP, 1, 'Użytkownik admin zalogował się do systemu'),
 (3, CURRENT_TIMESTAMP, 2, 'Lekarz wystawił zlecenie badania dla pacjenta 4'),
 (2, CURRENT_TIMESTAMP, 3, 'Laborant edytował wynik badania morfologii');
