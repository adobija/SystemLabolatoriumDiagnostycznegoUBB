DROP TABLE IF EXISTS LogZdarzen;
DROP TABLE IF EXISTS WynikBadania;
DROP TABLE IF EXISTS ZleceniePozycja;
DROP TABLE IF EXISTS ZlecenieBadania;
DROP TABLE IF EXISTS Probka;
DROP TABLE IF EXISTS BadanieRodzaj;
DROP TABLE IF EXISTS Lekarz;
DROP TABLE IF EXISTS Pacjent;
DROP TABLE IF EXISTS Uzytkownik;
DROP TABLE IF EXISTS Laboratorium;
DROP TABLE IF EXISTS SprzetLaboratoryjny;

CREATE TABLE Uzytkownik (
    id_uzytkownika IDENTITY PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    haslo_hash VARCHAR(255) NOT NULL,
    imie VARCHAR(100) NOT NULL,
    nazwisko VARCHAR(100) NOT NULL,
    pesel VARCHAR(11),
    telefon VARCHAR(20),
    rola VARCHAR(20) NOT NULL, -- 'PACJENT','LEKARZ','ADMIN'
    data_utworzenia TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Pacjent (
    id_pacjenta BIGINT PRIMARY KEY,
    data_urodzenia DATE,
    plec VARCHAR(10),
    adres VARCHAR(255),
    numer_karty_pacjenta VARCHAR(50),
    CONSTRAINT fk_pacjent_uzytkownik
        FOREIGN KEY (id_pacjenta) REFERENCES Uzytkownik(id_uzytkownika)
);

CREATE TABLE Lekarz (
    id_lekarza BIGINT PRIMARY KEY,
    specjalizacja VARCHAR(100),
    numer_prawa_wykonywania VARCHAR(50),
    oddzial VARCHAR(100),
    CONSTRAINT fk_lekarz_uzytkownik
        FOREIGN KEY (id_lekarza) REFERENCES Uzytkownik(id_uzytkownika)
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

CREATE TABLE BadanieRodzaj (
    id_badania_rodzaj IDENTITY PRIMARY KEY,
    kod_badania VARCHAR(50) NOT NULL UNIQUE,
    nazwa VARCHAR(255) NOT NULL,
    opis VARCHAR(1000),
    jednostka VARCHAR(50),
    wartosc_min_referencyjna DECIMAL(10,2),
    wartosc_max_referencyjna DECIMAL(10,2),
    cena DECIMAL(10,2),
    typ_materialu VARCHAR(50)
);

CREATE TABLE Probka (
    id_probki IDENTITY PRIMARY KEY,
    kod_probki VARCHAR(50) NOT NULL UNIQUE,
    typ_materialu VARCHAR(50),
    data_pobrania TIMESTAMP,
    miejsce_pobrania VARCHAR(255),
    id_pacjenta BIGINT NOT NULL,
    pobrane_przez BIGINT,
    CONSTRAINT fk_probka_pacjent
        FOREIGN KEY (id_pacjenta) REFERENCES Pacjent(id_pacjenta),
    CONSTRAINT fk_probka_lekarz
        FOREIGN KEY (pobrane_przez) REFERENCES Lekarz(id_lekarza)
);

CREATE TABLE ZlecenieBadania (
    id_zlecenia IDENTITY PRIMARY KEY,
    id_pacjenta BIGINT NOT NULL,
    id_lekarza BIGINT NOT NULL,
    data_zlecenia TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) NOT NULL,
    uwagi_kliniczne VARCHAR(2000),
    CONSTRAINT fk_zlecenie_pacjent
        FOREIGN KEY (id_pacjenta) REFERENCES Pacjent(id_pacjenta),
    CONSTRAINT fk_zlecenie_lekarz
        FOREIGN KEY (id_lekarza) REFERENCES Lekarz(id_lekarza)
);

CREATE TABLE ZleceniePozycja (
    id_pozycji IDENTITY PRIMARY KEY,
    id_zlecenia BIGINT NOT NULL,
    id_badania_rodzaj BIGINT NOT NULL,
    id_probki BIGINT,
    status VARCHAR(20) NOT NULL,
    CONSTRAINT fk_pozycja_zlecenie
        FOREIGN KEY (id_zlecenia) REFERENCES ZlecenieBadania(id_zlecenia),
    CONSTRAINT fk_pozycja_badanie
        FOREIGN KEY (id_badania_rodzaj) REFERENCES BadanieRodzaj(id_badania_rodzaj),
    CONSTRAINT fk_pozycja_probka
        FOREIGN KEY (id_probki) REFERENCES Probka(id_probki)
);

CREATE TABLE WynikBadania (
    id_wyniku IDENTITY PRIMARY KEY,
    id_pozycji BIGINT NOT NULL UNIQUE,
    wartosc_liczbowa DECIMAL(10,2),
    wartosc_opisowa VARCHAR(2000),
    jednostka VARCHAR(50),
    flaga_nieprawidlowy BOOLEAN,
    data_wykonania TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    wykonane_przez BIGINT,
    CONSTRAINT fk_wynik_pozycja
        FOREIGN KEY (id_pozycji) REFERENCES ZleceniePozycja(id_pozycji),
    CONSTRAINT fk_wynik_uzytkownik
        FOREIGN KEY (wykonane_przez) REFERENCES Uzytkownik(id_uzytkownika)
);

CREATE TABLE LogZdarzen (
    id_logu IDENTITY PRIMARY KEY,
    id_uzytkownika BIGINT,
    data_zdarzenia TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    typ_zdarzenia VARCHAR(50),
    opis VARCHAR(2000),
    CONSTRAINT fk_log_uzytkownik
        FOREIGN KEY (id_uzytkownika) REFERENCES Uzytkownik(id_uzytkownika)
);


INSERT INTO Laboratorium (nazwa, adres, telefon) VALUES
  ('Laboratorium Główne', 'ul. Szpitalna 1, Warszawa', '222222222');

INSERT INTO SprzetLaboratoryjny (nazwa, typ, numer_seryjny, id_laboratorium) VALUES
  ('Analizator hematologiczny', 'HEMATOLOGIA', 'HEM-001', 1),
  ('Analizator biochemiczny', 'BIOCHEMIA', 'BIO-001', 1);

INSERT INTO Uzytkownik (email, haslo_hash, imie, nazwisko, pesel, telefon, rola) VALUES
  ('jan.kowalski@example.com', 'hash1', 'Jan', 'Kowalski', '90010112345', '600111111', 'PACJENT'),
  ('anna.nowak@example.com', 'hash2', 'Anna', 'Nowak', NULL, '600222222', 'LEKARZ'),
  ('diag1@example.com', 'hash3', 'Piotr', 'Diagnosta', NULL, '600333333', 'ADMIN');

INSERT INTO Pacjent (id_pacjenta, data_urodzenia, plec, adres, numer_karty_pacjenta) VALUES
  (1, DATE '1990-01-01', 'M', 'ul. Pacjenta 1, Warszawa', 'KARTA-001');

INSERT INTO Lekarz (id_lekarza, specjalizacja, numer_prawa_wykonywania, oddzial) VALUES
  (2, 'Internista', 'PWZ-123456', 'Oddział Chorób Wewnętrznych');

INSERT INTO BadanieRodzaj (kod_badania, nazwa, opis, jednostka,
                           wartosc_min_referencyjna, wartosc_max_referencyjna,
                           cena, typ_materialu)
VALUES
  ('MORF', 'Morfologia krwi', 'Podstawowa morfologia krwi obwodowej',
   '10^9/L', 4.00, 10.00, 15.00, 'krew'),
  ('GLU', 'Glikemia na czczo', 'Poziom glukozy we krwi na czczo',
   'mg/dL', 70.00, 99.00, 10.00, 'krew'),
  ('CRP', 'CRP', 'Białko C-reaktywne', 'mg/L', 0.00, 5.00, 20.00, 'krew');

INSERT INTO Probka (kod_probki, typ_materialu, data_pobrania, miejsce_pobrania, id_pacjenta, pobrane_przez)
VALUES
  ('PRB-0001', 'krew', CURRENT_TIMESTAMP, 'Punkt pobrań 1', 1, 2);

INSERT INTO ZlecenieBadania (id_pacjenta, id_lekarza, status, uwagi_kliniczne)
VALUES
  (1, 2, 'NOWE', 'Badania kontrolne po infekcji');

INSERT INTO ZleceniePozycja (id_zlecenia, id_badania_rodzaj, id_probki, status) VALUES
  (1, 1, 1, 'GOTOWE'),   -- MORF
  (1, 2, 1, 'GOTOWE'),   -- GLU
  (1, 3, 1, 'W_ANALIZIE'); -- CRP


INSERT INTO WynikBadania (id_pozycji, wartosc_liczbowa, wartosc_opisowa,
                          jednostka, flaga_nieprawidlowy, wykonane_przez)
VALUES
  (1, 6.50, NULL, '10^9/L', FALSE, 3),
  (2, 95.00, NULL, 'mg/dL', FALSE, 3);

INSERT INTO LogZdarzen (id_uzytkownika, typ_zdarzenia, opis) VALUES
  (2, 'UTWORZENIE_ZLECENIA', 'Lekarz Anna Nowak utworzyła zlecenie badań dla pacjenta Jan Kowalski'),
  (3, 'DODANIE_WYNIKU', 'Diagnosta Piotr dodał wyniki badań dla zlecenia 1');