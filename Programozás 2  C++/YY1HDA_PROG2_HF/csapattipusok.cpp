#include "sportegyesulet.h"
#include "csapattipusok.h"
#include "memtrace.h"

Foci::Foci(string csapatnev, string edzo, int letszam, Jatekos* jatekosok, string edzo2): Csapat(csapatnev, edzo, letszam, jatekosok), edzo2(edzo2) {}

string Foci::getEdzo2() const{
    return edzo2;
}

void Foci::csapatKiir(){
    cout << "Focicsapat ~ " << getCsapatnev() << endl;
    cout << "Edzok(vezeto, masod) ~ " << getEdzo() << ", " << edzo2 << endl;
    cout << "Csapat letszama ~ " << getLetszam() << endl;
    if(getLetszam() != 0){
        cout << "Csapat jatekosai(Neve, Mez erteke, Pontszama):" << endl;
        int i = 0;
        Jatekos ures;
        Jatekos* ideig = getJatekosok();
        int ideigletszam = getLetszam();
        while(i < ideigletszam && ideig[i] != ures){
            cout << ideig[i].nev << ", " << ideig[i].ertek << ", " << ideig[i].pont << endl;
            i++;
        }
    }
    else{
        cout << "~ Nincsenek jatekosok ~" << endl;
    }
}

void Foci::kiirFajlba(ofstream& fajl_ki){
    fajl_ki << "1\t" << getCsapatnev() << "\t" << getEdzo() << "\t" << getLetszam() << "\t" << edzo2 << endl;
    for(int i = 0; i < getLetszam(); i++){
        fajl_ki << getJatekosok()[i].nev << "\t" << getJatekosok()[i].ertek << "\t" << getJatekosok()[i].pont << endl;
    }
}

Kosar::Kosar(string csapatnev, string edzo, int letszam, Jatekos* jatekosok, int pompomszam): Csapat(csapatnev, edzo, letszam, jatekosok), pompomszam(pompomszam) {}

int Kosar::getPompomszam() const{
    return pompomszam;
}

void Kosar::csapatKiir(){
    cout << "Kosarcsapat ~ " << getCsapatnev() << endl;
    cout << "Edzo ~ " << getEdzo() << endl;
    cout << "Csapat letszama ~ " << getLetszam() << "\tPompomlany csapat letszama ~ " << pompomszam << endl;
    if(getLetszam() != 0){
        cout << "Csapat jatekosai(Neve, Mez erteke, Pontszama):" << endl;
        int i = 0;
        Jatekos ures;
        Jatekos* ideig = getJatekosok();
        int ideigletszam = getLetszam();
        while(i < ideigletszam && ideig[i] != ures){
            cout << ideig[i].nev << ", " << ideig[i].ertek << ", " << ideig[i].pont << endl;
            i++;
        }
    }
    else{
        cout << "~ Nincsenek jatekosok ~" << endl;
    }
}

void Kosar::kiirFajlba(ofstream& fajl_ki){
    fajl_ki << "2\t" << getCsapatnev() << "\t" << getEdzo() << "\t" << getLetszam() << "\t" << pompomszam << endl;
    for(int i = 0; i < getLetszam(); i++){
        fajl_ki << getJatekosok()[i].nev << "\t" << getJatekosok()[i].ertek << "\t" << getJatekosok()[i].pont << endl;
    }
}

Kezi::Kezi(string csapatnev, string edzo, int letszam, Jatekos* jatekosok, int tamogatas): Csapat(csapatnev, edzo, letszam, jatekosok), tamogatas(tamogatas) {}

int Kezi::getTamogatas() const{
    return tamogatas;
}

void Kezi::csapatKiir(){
    cout << "Kezicsapat ~ " << getCsapatnev() << endl;
    cout << "Edzo ~ " << getEdzo() << endl;
    cout << "Csapat letszama ~ " << getLetszam() << "\tCsapat evi tamogatasa ~ " << tamogatas << endl;
    if(getLetszam() != 0){
        cout << "Csapat jatekosai(Neve, Mez erteke, Pontszama):" << endl;
        int i = 0;
        Jatekos ures;
        Jatekos* ideig = getJatekosok();
        int ideigletszam = getLetszam();
        while(i < ideigletszam && ideig[i] != ures){
            cout << ideig[i].nev << ", " << ideig[i].ertek << ", " << ideig[i].pont << endl;
            i++;
        }
    }
    else{
        cout << "~ Nincsenek jatekosok ~" << endl;
    }
}

void Kezi::kiirFajlba(ofstream& fajl_ki){
    fajl_ki << "3\t" << getCsapatnev() << "\t" << getEdzo() << "\t" << getLetszam() << "\t" << tamogatas << endl;
    for(int i = 0; i < getLetszam(); i++){
        fajl_ki << getJatekosok()[i].nev << "\t" << getJatekosok()[i].ertek << "\t" << getJatekosok()[i].pont << endl;
    }
}
