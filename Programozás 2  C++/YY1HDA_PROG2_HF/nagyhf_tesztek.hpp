#ifndef NAGYHF_TESZTEK_HPP_INCLUDED
#define NAGYHF_TESZTEK_HPP_INCLUDED

#include <iostream>
#include <string>
#include "sportegyesulet.h"
#include "csapat.h"
#include "gtest_lite.h"
#include "memtrace.h"

using namespace std;

void teszt_sportegyesulet(){
    TEST(teszt1, sportegyesuletKonstruktor){
        Sportegyesulet fittse;
        EXPECT_EQ(0, fittse.getHossz()) << "Hiba: sportegyesulet konstruktor" << endl;
    }
    END

    TEST(teszt2, ujCsapat){
        Sportegyesulet fittse;
        Foci* csapat = new Foci;
        fittse.ujCsapat(csapat);
        EXPECT_EQ(1, fittse.getHossz()) << "Hiba: uj csapat hozzaadasa" << endl;
    }
    END

    TEST(teszt3, torolCsapat){
        Sportegyesulet fittse;
        Kosar* csapat = new Kosar("nev", "edzo", 3, nullptr, 12);
        fittse.ujCsapat(csapat);
        fittse.torolCsapat("nev");
        EXPECT_EQ(0, fittse.getHossz()) << "Hiba: csapat torlese" << endl;
    }
    END

    TEST(teszt4, beolvasFajlbol){
        Sportegyesulet fittse;
        fittse.beolvasFajlbol();
        EXPECT_NE(0, fittse.getHossz()) << "Hiba: nem adott meg adatbazist" << endl;
    }
    END

    TEST(teszt5, kiirFajlba){
        Sportegyesulet fittse;
        fittse.beolvasFajlbol();
        EXPECT_NO_THROW(fittse.kiirFajlba()) << "Hiba: nem nyilt meg az fajl" << endl;
    }
    END
}

void teszt_csapat(){
    TEST(teszt6, csapatKonstruktor){
        Csapat tesztcsapat;
        EXPECT_EQ(0, tesztcsapat.getLetszam()) << "Hiba: csapat konstruktor" << endl;
        EXPECT_EQ("", tesztcsapat.getCsapatnev()) << "Hiba: csapat konstruktor" << endl;
        EXPECT_EQ("", tesztcsapat.getEdzo()) << "Hiba: csapat konstruktor" << endl;
        Csapat tesztcsapat2("Vadmacskak", "Harry Styles", 10, nullptr);
        EXPECT_EQ(10, tesztcsapat2.getLetszam()) << "Hiba: csapat konstruktor" << endl;
        EXPECT_EQ("Vadmacskak", tesztcsapat2.getCsapatnev()) << "Hiba: csapat konstruktor" << endl;
        EXPECT_EQ("Harry Styles", tesztcsapat2.getEdzo()) << "Hiba: csapat konstruktor" << endl;
    }
    END
}

void teszt_csapattipusok(){
    TEST(teszt7, fociKonstruktor){
        Foci focicsapat;
        EXPECT_EQ(0, focicsapat.getLetszam()) << "Hiba: focicsapat konstruktor" << endl;
        EXPECT_EQ("", focicsapat.getCsapatnev()) << "Hiba: focicsapat konstruktor" << endl;
        EXPECT_EQ("", focicsapat.getEdzo()) << "Hiba: focicsapat konstruktor" << endl;
        EXPECT_EQ("", focicsapat.getEdzo2()) << "Hiba: focicsapat konstruktor" << endl;
        Foci focicsapat2("Bulldogok", "Szabo Erkel", 14, nullptr, "Kovacs Julia");
        EXPECT_EQ(14, focicsapat2.getLetszam()) << "Hiba: focicsapat konstruktor" << endl;
        EXPECT_EQ("Bulldogok", focicsapat2.getCsapatnev()) << "Hiba: focicsapat konstruktor" << endl;
        EXPECT_EQ("Szabo Erkel", focicsapat2.getEdzo()) << "Hiba: focicsapat konstruktor" << endl;
        EXPECT_EQ("Kovacs Julia", focicsapat2.getEdzo2()) << "Hiba: focicsapat konstruktor" << endl;
    }
    END

    TEST(teszt8, kosarKonstruktor){
        Kosar kosarcsapat;
        EXPECT_EQ(0, kosarcsapat.getLetszam()) << "Hiba: kosarcsapat konstruktor" << endl;
        EXPECT_EQ("", kosarcsapat.getCsapatnev()) << "Hiba: kosarcsapat konstruktor" << endl;
        EXPECT_EQ("", kosarcsapat.getEdzo()) << "Hiba: kosarcsapat konstruktor" << endl;
        EXPECT_EQ(0, kosarcsapat.getPompomszam()) << "Hiba: kosarcsapat konstruktor" << endl;
        Kosar kosarcsapat2("Sasok", "Antal Akos", 9, nullptr, 18);
        EXPECT_EQ(9, kosarcsapat2.getLetszam()) << "Hiba: kosarcsapat konstruktor" << endl;
        EXPECT_EQ("Sasok", kosarcsapat2.getCsapatnev()) << "Hiba: kosarcsapat konstruktor" << endl;
        EXPECT_EQ("Antal Akos", kosarcsapat2.getEdzo()) << "Hiba: kosarcsapat konstruktor" << endl;
        EXPECT_EQ(18, kosarcsapat2.getPompomszam()) << "Hiba: kosarcsapat konstruktor" << endl;
    }
    END

    TEST(teszt9, keziKonstruktor){
        Kezi kezicsapat;
        EXPECT_EQ(0, kezicsapat.getLetszam()) << "Hiba: kezicsapat konstruktor" << endl;
        EXPECT_EQ("", kezicsapat.getCsapatnev()) << "Hiba: kezicsapat konstruktor" << endl;
        EXPECT_EQ("", kezicsapat.getEdzo()) << "Hiba: kezicsapat konstruktor" << endl;
        EXPECT_EQ(0, kezicsapat.getTamogatas()) << "Hiba: kezicsapat konstruktor" << endl;
        Kezi kezicsapat2("Tigers", "Ernesto De la Cruz", 14, nullptr, 1000000);
        EXPECT_EQ(14, kezicsapat2.getLetszam()) << "Hiba: kezicsapat konstruktor" << endl;
        EXPECT_EQ("Tigers", kezicsapat2.getCsapatnev()) << "Hiba: kezicsapat konstruktor" << endl;
        EXPECT_EQ("Ernesto De la Cruz", kezicsapat2.getEdzo()) << "Hiba: kezicsapat konstruktor" << endl;
        EXPECT_EQ(1000000, kezicsapat2.getTamogatas()) << "Hiba: kezicsapat konstruktor" << endl;
    }
    END
}


#endif // NAGYHF_TESZTEK_HPP_INCLUDED
