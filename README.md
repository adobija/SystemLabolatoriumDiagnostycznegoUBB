_____  ______ _  _______ _   _    ____  _____    _______ __ __ _     _    _ 
 |  __ \|  ____| |/ /_   _| \ | |  / __ \|  __ \  |__   __|\_ /  | |   | |  | |
 | |__) | |__  | ' /  | | |  \| | | |  | | |  | |    | |   | ||  | |   | |  | |
 |  _  /|  __| |  <   | | | . ` | | |  | | |  | |    | |   | ||  | |   | |  | |
 | | \ \| |____| . \ _| |_| |\  | | |__| | |__| |    | |   | ||  | |___| |__| |
 |_|  \_\______|_|\_\_____|_| \_|  \____/|_____/     |_|   |_||__|______\____/ 
                                                                               
                                                                               
         System Laboratorium Diagnostycznego UBB

===================================================================================================================================================================

1. System Laboratorium Diagnostycznego 

Aplikacja webowa wspierająca obsługę laboratorium diagnostycznego. System umożliwia pacjentom dostęp do historii badań, laborantom/lekarzom dodawanie wyników badań, a administratorom zarządzanie rolami użytkowników.

-------------------------------------------------------------------------------------------------------------------------------------------------------------------

2. Opis projektu

Projekt został zrealizowany w technologii Spring Boot (backend) z wykorzystaniem Thymeleaf (warstwa widoku). System wprowadza trzy role użytkowników z odrębnymi panelami i zakresem uprawnień: pacjent, lekarz/laborant oraz administrator.

-------------------------------------------------------------------------------------------------------------------------------------------------------------------

3. Funkcjonalności

System realizuje pełen cykl operacyjny zarządzania wynikami badań, od momentu autoryzacji personelu po dystrybucję danych do pacjenta.

3.1 Logowanie i rejestracja
    - Rejestracja nowego konta użytkownika
    - Logowanie do systemu z podziałem na role
    - Zarządzanie sesją użytkownika

3.2 Panel pacjenta
    - Przeglądanie historii badań
    - Generowanie wyników badań do pliku PDF
    
3.3 Panel lekarza
    - Dodawanie nowych badań do systemu (wprowadzanie wyników dla pacjenta)

3.4 Panel administratora
    - Modyfikacja ról przypisanych użytkownikom
    

-------------------------------------------------------------------------------------------------------------------------------------------------------------------

4. Wymagania funkcjonalne

    - System umożliwia rejestrację nowego użytkownika (pacjenta)
    - System umożliwia logowanie użytkownika na podstawie danych uwierzytelniających
    - System rozróżnia uprawnienia w zależności od roli użytkownika (pacjent, lekarz, administrator)
    - Pacjent ma dostęp do panelu z historią swoich badań
    - Pacjent może wygenerować wynik badania w formacie PDF
    - Lekarz ma dostęp do panelu umożliwiającego dodanie nowego badania pacjentowi
    - Administrator ma dostęp do panelu zarządzania użytkownikami
    - Administrator może zmienić rolę przypisaną do konta użytkownika
    - System ogranicza dostęp do poszczególnych paneli wyłącznie do uprawnionej roli

-------------------------------------------------------------------------------------------------------------------------------------------------------------------

5. Wymagania niefunkcjonalne

    - Bezpieczeństwo – hasła użytkowników są przechowywane w postaci zahaszowanej
    - Bezpieczeństwo – dostęp do paneli jest kontrolowany na podstawie roli (autoryzacja)
    - interfejs oparty na szablonach Thymeleaf, prosty i zrozumiały dla użytkownika
    - aplikacja działa w oparciu o standardowy serwer aplikacyjny wspierany przez Spring Boot

-------------------------------------------------------------------------------------------------------------------------------------------------------------------

6. Technologie

    - Java
    - Spring Boot
    - Thymeleaf
    - HTML / CSS

-------------------------------------------------------------------------------------------------------------------------------------------------------------------

7. Role użytkowników
    - pacjent – Przeglądanie historii badań, generowanie PDF
    - Lekarz - Dodawanie badań pacjentom
    - Administrator - Zarządzanie rolami użytkowników
 






  
