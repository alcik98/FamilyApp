# FamilyApp
Zadanie ewaluacyjne na młodszego programistę JAVA

Aplikacja składa się z trzech komponentów: FamilyApp, FamilyMemberApp oraz komponentu bazy danych zawierającej schematy FamilyDB oraz FamilyMemberDB.
Podczas tworzenia nowej rodziny, FamilyApp sprawdza, czy liczba członków rodziny jest poprawna, a następnie tworzy rodzinę i przekazuje dane o jej członkach do komponentu FamilyMemberApp, gdzie są przekazywane dalej do bazy danych.

W celu uruchomienia, należy pobrać plik "docker-compose.yml" i następnie użyć polecenia "docker compose up".

http://localhost:8080/families/ - Zwroca nam wszystkie dostepne rodziny (bez czlonków).

http://localhost:8080/families/getFamily/{id} - Zwraca rodzine o podanym id oraz jej członków.

http://localhost:8081/familyMembers/createFamily - Tworzy rodzine wraz z podanymi członkami rodziny.

![image](https://user-images.githubusercontent.com/4568346/192171549-9db7f6c6-6061-4168-8d87-77a60bbcb383.png)
![image](https://user-images.githubusercontent.com/4568346/192172002-259e9bf1-9c97-4755-a0c3-307eef02d6b9.png)
![image](https://user-images.githubusercontent.com/4568346/192172064-e8cd00cf-b5e7-44fb-b844-0ab62481a0d2.png)
