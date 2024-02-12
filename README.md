Tema ovog projekta je sistem koji je simulacija za pravljenje video kluba.
Projekat se sastoji od tri podsistema koji komuniciraju sa MySql bazom,
centralnog servera i klijentske aplikacije. Podsistemi su ti koji upisuju
u bazu koristeci JPA, dok centralni server samo prima REST zahteve i 
salje ih podsistemima preko JMS-a.

Da bi projekat radio potrebno je napraviti JMS resurse na serveru, server
koji sam ja koristio je GlassFish. Treba napraviti jedan Queue koji ima
naziv "RedZaZahteve". Treba napraviti tri Topic-a koji imaju nazive
"PodsistemXTopic" gde x predstavlja brojeve 1, 2, 3 koji su i brojevi
podsistema.

Potrebno je promeniti i u persistence.xml fajlovima na mestu gde treba
da stoji lozinka, lozinka od MySql servera, i verovatno bi bilo pozeljno da
prvi put kad se pokrece kod da se u persistence.xml stavi da Table Generation
Strategy bude Create, da bi se formirale baze.
