#include "sportegyesulet.h"
#include "csapat.h"
#include "csapattipusok.h"
#include "memtrace.h"

Sportegyesulet::Sportegyesulet(Csapat** adatbazis, int hossz) {
    this->hossz = hossz;
    this->adatbazis = adatbazis;
}

int Sportegyesulet::getHossz() const{
    return hossz;
}

Csapat** Sportegyesulet::getAdatbazis() const{
    return adatbazis;
}

void Sportegyesulet::listazCsapatok(){
    if(hossz == 0){
        cout << "~~~~~~ Az adatbazis ures ~~~~~~" << endl;
    }
    for(int i = 0; i < hossz; i++){
        cout << "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" << endl;
        adatbazis[i]->csapatKiir();
    }
}

void Sportegyesulet::listazSorrendben(int mi_szerint){
    if(hossz == 0){
        cout << "~~~~~~ Az adatbazis ures ~~~~~~" << endl;
    }
    for(int i = 0; i < hossz; i++){
        adatbazis[i]->buborekRendezes(mi_szerint);
        cout << "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" << endl;
        adatbazis[i]->csapatKiir();
    }
}

void Sportegyesulet::ujCsapat(Csapat* ujcsapat){
    Csapat** ideig = new Csapat*[hossz+1];
    for (int i = 0; i < hossz; i++) {
        ideig[i] = adatbazis[i];
    }
    ideig[hossz] = ujcsapat;
    delete [] adatbazis;
    adatbazis = ideig;
    hossz++;
}

void Sportegyesulet::jatekosKeres(string nev){
    int volt_e = 0;
    for(int i = 0; i < hossz; i++){
        int letszam = adatbazis[i]->getLetszam();
        for(int j = 0; j < letszam; j++){
            string adatbazisnev = adatbazis[i]->getJatekosok()[j].nev;
            if((nev == adatbazisnev) || (adatbazisnev.find(nev) != adatbazisnev.npos)){
                volt_e = 1;
                cout << "A keresett jatekos csapata:" << endl;
                cout << "~~~~~~~~~~~~~~~~~~~~~~~~~~~~" << endl;
                adatbazis[i]->csapatKiir();
            }
        }
    }
    if(volt_e == 0){
        cout << "!------------------!" << endl;
        cout << "Nincs ilyen jatekos" <<endl;
        cout << "!------------------!" << endl;
    }
}

void Sportegyesulet::torolCsapat(string torlendocsapat){
    Csapat** ideig = new Csapat*[hossz-1];
    int j = 0;
    int torlendo;
    for (int i = 0; i < hossz; i++) {
        if(torlendocsapat != adatbazis[i]->getCsapatnev()){
            ideig[j] = adatbazis[i];
            j++;
        }
        else{
            torlendo = i;
        }
    }
    hossz--;
    delete adatbazis[torlendo];
    delete [] adatbazis;
    adatbazis = ideig;

    cout << "~~~~~~~~ A csapat torolve az adatbazisbol! ~~~~~~~~" << endl;
}

void Sportegyesulet::beolvasFajlbol(){
    ifstream fajl_be;
    string hosszstring, tipus, csapatnev, edzo, letszamstring, edzo2;
    fajl_be.open("adatbazis.txt");
    getline(fajl_be, hosszstring);
    if(hosszstring == ""){
        hossz = 0;
        return;
    }
    hossz = stoi(hosszstring);
    if(hossz == 0){
        return;
    }
    adatbazis = new Csapat* [hossz];
    int letszam, pompomszam, tamogatas;
    for(int i = 0; i < hossz; i++){
        getline(fajl_be, tipus, '\t');
        getline(fajl_be, csapatnev, '\t');
        getline(fajl_be, edzo, '\t');
        getline(fajl_be, letszamstring, '\t');
        letszam = stoi(letszamstring);
        if(tipus == "1"){
            getline(fajl_be, edzo2);
        }
        else if(tipus == "2"){
            string pompomszamstring;
            getline(fajl_be, pompomszamstring);
            pompomszam = stoi(pompomszamstring);
        }
        else if(tipus == "3"){
            string tamogatasstring;
            getline(fajl_be, tamogatasstring);
            tamogatas = stoi(tamogatasstring);
        }
        Jatekos* jatekosok = new Jatekos [letszam];
        for(int j = 0; j < letszam; j++){
            string nev, ertekstring, pontstring;
            int ertek, pont;
            getline(fajl_be, nev, '\t');
            getline(fajl_be, ertekstring, '\t');
            ertek = stoi(ertekstring);
            getline(fajl_be, pontstring);
            pont = stoi(pontstring);
            Jatekos uj(nev, ertek, pont);
            jatekosok[j] = uj;
        }
        if(tipus == "1"){
            Foci* focicsapat = new Foci(csapatnev, edzo, letszam, jatekosok, edzo2);
            adatbazis[i] = focicsapat;
        }
        else if(tipus == "2"){
            Kosar* kosarcsapat = new Kosar(csapatnev, edzo, letszam, jatekosok, pompomszam);
            adatbazis[i] = kosarcsapat;
        }
        else if(tipus == "3"){
            Kezi* kezicsapat = new Kezi(csapatnev, edzo, letszam, jatekosok, tamogatas);
            adatbazis[i] = kezicsapat;
        }
    }
    fajl_be.close();
}

void Sportegyesulet::kiirFajlba(){
    ofstream fajl_ki;
    fajl_ki.open("adatbazis.txt");
    if(fajl_ki.fail()){
        throw("Nem sikerult megnyitni a fajlt!\n");
    }
    fajl_ki << hossz << endl;
    if(hossz == 0){
        return;
    }
    for(int i = 0; i < hossz; i++){
        adatbazis[i]->kiirFajlba(fajl_ki);
    }
    fajl_ki.close();
}

Sportegyesulet::~Sportegyesulet(){
    for(int i = 0; i < hossz; i++){
        delete adatbazis[i];
    }
    delete[] adatbazis;
}
