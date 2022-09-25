# FamilyApp
Zadanie ewaluacyjne na młodszego programistę JAVA

Aplikacja składa się z trzech komponentów: FamilyApp, FamilyMemberApp oraz komponentu bazy danych zawierającej schematy FamilyDB oraz FamilyMemberDB.
Podczas tworzenia nowej rodziny, FamilyApp sprawdza, czy liczba członków rodziny jest poprawna, a następnie tworzy rodzinę i przekazuje dane o jej członkach do komponentu FamilyMemberApp, gdzie są przekazywane dalej do bazy danych.

W celu uruchomienia, należy pobrać plik "docker-compose.yml" i następnie użyć polecenia "docker compose up".

http://localhost:8080/families/ - Zwroca nam wszystkie dostepne rodziny (bez czlonków).

http://localhost:8080/families/getFamily/{id} - Zwraca rodzine o podanym id oraz jej członków.

http://localhost:8081/familyMembers/createFamily - Tworzy rodzine wraz z podanymi członkami rodziny.
