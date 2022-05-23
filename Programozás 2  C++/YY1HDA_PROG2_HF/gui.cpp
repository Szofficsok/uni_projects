#include "gui.h"
#include "memtrace.h"

void menu(Sportegyesulet* fittsportegyesulet){
    int fut = 1;
    while(fut == 1){
        try{
            cout << "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" << endl;
            cout << "Udvozoljuk a Fitt Sportegyesulet adatbazisaban!" << endl;
            cout << "Rendelkezesere allo tevekenysegek:" << endl;
            cout << "\tUj csapat letrehozasa ~ 1" << endl;
            cout << "\tCsapat torlese ~ 2" << endl;
            cout << "\tJatekos keresese ~ 3" << endl;
            cout << "\tMinden csapat kilistazasa ~ 4" << endl;
            cout << "\tListazas sorrendben ~ 5" << endl;
            cout << "\tKilepes a programbol ~ 6" << endl;
            cout << "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" << endl;
            cout << "Kerem valasszon a megadott tevekenysegekbol, a megfelelo szam megadasaval: ";
            string tevstring;
            getline(cin, tevstring);
            int tev = 0;
            tev = stoi(tevstring);
            switch (tev) {

                case 1 :
                    bekerUjcsapat(fittsportegyesulet);
                    break;
                case 2 :
                    bekerTorolcsapat(fittsportegyesulet);
                    break;
                case 3 :
                    bekerJatekoskeres(fittsportegyesulet);
                    break;
                case 4 :
                    fittsportegyesulet->listazCsapatok();
                    break;
                case 5 :
                    bekerListazsorrendben(fittsportegyesulet);
                    break;
                case 6 :
                    fut = 0;
                    cout << endl << "|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||" << endl;
                    cout << " ~~~~~~~~~~~~~~~~ Viszontlatasra, legyen szep napja! :) ~~~~~~~~~~~~~~~~" << endl;
                    cout << "|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||" << endl;
                    break;
                default :
                    cout << "!---------------------------------------------------------------------!" << endl;
                    cout << "Ilyen szamnak megfelelo tevekenyseg nem letezik, kerem probalja ujra :)" << endl;
                    cout << "!---------------------------------------------------------------------!" << endl << endl;
            }
        }
        catch(invalid_argument& hiba){
            cout << "!------------------------------!" << endl;
            cout << "Nem megfelelo a megadott adat :(" << endl;
            cout << "!------------------------------!" << endl;
        }
        catch(out_of_range& hiba){
            cout << "!--------------------------!" << endl;
            cout << "Tul nagy szamot adott meg :(" << endl;
            cout << "!--------------------------!" << endl;
        }
        catch(exception& hiba){
            cout << hiba.what() << endl;
        }
    }
}

Jatekos* csapatJatekosai(int letszam){
    Jatekos* jatekosok = new Jatekos[letszam];
    try{
        if(letszam == 0){
            return jatekosok;
        }
        int akar_e;
        string akar_estring;
        cout << "Szeretne jatekost hozzaadni?" << endl;
        cout << "Igen ~ 1" << endl;
        cout << "Nem ~ 2" << endl;
        cout << "++++++++++++++++++++++++++++++" << endl;
        cout << "Adja meg a megfelelo szamot: ";
        getline(cin, akar_estring);
        akar_e = stoi(akar_estring);
        cout << "++++++++++++++++++++++++++++++" << endl;
        while(akar_e < 1 || akar_e > 2){
            cout << "!-------------------------------------------!" << endl;
            cout << "Nincs ilyen lehetoseg, kerem valasszon ujra: ";
            getline(cin, akar_estring);
            akar_e = stoi(akar_estring);
            cout << "!-------------------------------------------!" << endl;
        }
        Jatekos ures;
        for (int i = 0; i < letszam; i++) {
            jatekosok[i] = ures;
        }
        int db = 0;
        while(akar_e == 1){
            string nev, ertekstring, pontstring;
            int ertek, pont;
            cout << "Kerem adja meg az uj jatekos nevet: " << endl;
            getline(cin, nev);
            cout << "Kerem adja meg az uj jatekos erteket: " << endl;
            getline(cin, ertekstring);
            ertek = stoi(ertekstring);
            cout << "Kerem adja meg az uj jatekos pontjat: " << endl;
            getline(cin, pontstring);
            pont = stoi(pontstring);
            Jatekos ujjatekos(nev, ertek, pont);
            for (int i = 0; i < letszam; i++) {
                if(jatekosok[i] == ures) {
                    jatekosok[i] = ujjatekos;
                    break;
                }
            }
            db++;
            if(db == letszam){
                cout << "~~~~~~ Minden jatekos megadva ~~~~~~" << endl;
                return jatekosok;
            }
            cout << "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" << endl;
            cout << "Szeretne meg hozzaadni jatekost?" << endl;
            cout << "Igen ~ 1" << endl;
            cout << "Nem ~ 2" << endl;
            cout << "+++++++++++++++++++++++++++++" << endl;
            cout << "Adja meg a megfelelo szamot: ";
            getline(cin, akar_estring);
            akar_e = stoi(akar_estring);
            cout << "++++++++++++++++++++++++++++++" << endl;
            while(akar_e < 1 || akar_e > 2){
                cout << "!-------------------------------------------!" << endl;
                cout << "Nincs ilyen lehetoseg, kerem valasszon ujra: " << endl;
                getline(cin, akar_estring);
                akar_e = stoi(akar_estring);
                cout << "!-------------------------------------------!" << endl;
            }
        }
        return jatekosok;
    }
    catch(invalid_argument& hiba){
        Jatekos ures;
        for(int i = 0; i < letszam; i++){
            jatekosok[i] = ures;
        }
        cout << "!----------------------------------------------------!" << endl;
        cout << "Nem megfelelo a megadott adat, a jatekosok nincsenek elmentve" <<endl;
        cout << "!----------------------------------------------------!" << endl;
        return jatekosok;
    }
}

void bekerUjcsapat(Sportegyesulet* fittsportegyesulet){
    int tipus, letszam;
    string csapatnev, edzo, tipusstring, letszamstring, pompomstring, tamogatasstring;
    cout << "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" << endl;
    cout << "Kerem valassza ki az uj csapat tipusat: " << endl;
    cout << "foci csapat ~ 1" << endl;
    cout << "kosar csapat ~ 2" << endl;
    cout << "kezi csapat ~ 3" << endl;
    cout << "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" << endl;
    getline(cin, tipusstring);
    tipus = stoi(tipusstring);
    while(tipus < 1 || tipus > 3){
        cout << "!--------------------------------------------------------------!" << endl;
        cout << "Ilyen szamnak megfelelo tipus nem letezik, kerem probalja ujra: ";
        getline(cin, tipusstring);
        tipus = stoi(tipusstring);
        cout << "!--------------------------------------------------------------!" << endl;
    }
    cout << "###################################" << endl;
    cout << "Kerem adja meg a csapatnevet: " << endl;
    getline(cin, csapatnev);
    int jo_e = 1;
    do{
        for(int i = 0; i < fittsportegyesulet->getHossz(); i++){
            if(csapatnev == fittsportegyesulet->getAdatbazis()[i]->getCsapatnev()){
                jo_e = 0;
                break;
            }
            if(i == fittsportegyesulet->getHossz()-1){
                jo_e = 1;
            }
        }
        if(jo_e == 0){
            cout << "Ilyen csapatnev mar letezik kerem adjon meg ujat: " << endl;
            getline(cin, csapatnev);
        }
    }
    while(jo_e == 0);
    cout << "Kerem adja meg a edzo nevet: " << endl;
    getline(cin, edzo);
    cout << "Kerem adja meg a csapat letszamat: " << endl;
    getline(cin, letszamstring);
    letszam = stoi(letszamstring);
    while(letszam < 0){
        cout << "!------------------------------------------------------!" << endl;
        cout << "A letszam erteke nem lehet 0-nal kisebb, kerem probalja ujra!" << endl;
        cout << "!------------------------------------------------------!" << endl;
        cout << "Kerem adja meg a csapat letszamat: " << endl;
        getline(cin, letszamstring);
        letszam = stoi(letszamstring);
    }
    if(tipus == 1) {
        string edzo2;
        cout << "Kerem adja meg a masodedzo nevet: " << endl;
        getline(cin, edzo2);
        cout << "###################################" << endl;
        Foci* focicsapat = new Foci(csapatnev, edzo, letszam, csapatJatekosai(letszam), edzo2);
        fittsportegyesulet->ujCsapat(focicsapat);
    }
    else if(tipus == 2) {
        int pompomszam;
        cout << "Kerem adja meg a pompom csapat letszamat: " << endl;
        getline(cin, pompomstring);
        pompomszam = stoi(pompomstring);
        while(pompomszam < 0){
            cout << "!------------------------------------------------------------------------!" << endl;
            cout << "A pompom csapat letszamanak erteke nem lehet 0-nal kisebb, kerem probalja ujra!" << endl;
            cout << "!------------------------------------------------------------------------!" << endl;
            cout << "Kerem adja meg a pompom csapat letszamat: " << endl;
            getline(cin, pompomstring);
            pompomszam = stoi(pompomstring);
        }
        cout << "###################################" << endl;
        Kosar* kosarcsapat = new Kosar(csapatnev, edzo, letszam, csapatJatekosai(letszam), pompomszam);
        fittsportegyesulet->ujCsapat(kosarcsapat);
    }
    else if(tipus == 3) {
        int tamogatas;
        cout << "Kerem adja meg a tamogatas osszeget: " << endl;
        getline(cin, tamogatasstring);
        tamogatas = stoi(tamogatasstring);
        while(tamogatas < 0){
            cout << "!--------------------------------------------------------!" << endl;
            cout << "A tamogatas erteke nem lehet 0-nal kisebb, kerem probalja ujra!" << endl;
            cout << "!--------------------------------------------------------!" << endl;
            cout << "Kerem adja meg a tamogatas osszeget: " << endl;
            getline(cin, tamogatasstring);
            tamogatas = stoi(tamogatasstring);
        }
        cout << "###################################" << endl;
        Kezi* kezicsapat = new Kezi(csapatnev, edzo, letszam, csapatJatekosai(letszam), tamogatas);
        fittsportegyesulet->ujCsapat(kezicsapat);
    }
}

void bekerTorolcsapat(Sportegyesulet* fittsportegyesulet){
    string csapatnev;
    cout << "----------------------------------------" << endl;
    cout << "Kerem adja meg a torlendo csapat nevet: " << endl;
    getline(cin, csapatnev);
    cout << "----------------------------------------" << endl;
    int volt_e = 0;
    int hossz = fittsportegyesulet->getHossz();
    for(int i = 0; i < hossz; i++){
        string ideignev = fittsportegyesulet->getAdatbazis()[i]->getCsapatnev();
        if(csapatnev == ideignev){
            volt_e = 1;
            fittsportegyesulet->torolCsapat(csapatnev);
            break;
        }
    }
    if(volt_e == 0){
        cout << "!---------------------------------!" << endl;
        cout << "Nincs az adatbazisban ilyen csapat" <<endl;
        cout << "!---------------------------------!" << endl;
    }
}

void bekerJatekoskeres(Sportegyesulet* fittsportegyesulet){
    string nev;
    cout << "++++++++++++++++++++++++++++++++++++++++++" << endl;
    cout << "Kerem adja meg a keresett jatekos nevet: " << endl;
    getline(cin, nev);
    cout << "++++++++++++++++++++++++++++++++++++++++++" << endl;
    fittsportegyesulet->jatekosKeres(nev);
}

void bekerListazsorrendben(Sportegyesulet* fittsportegyesulet){
    int mi_szerint;
    string mi_szerintstring;
    cout << "Mi alapjan legyenek sorrendben a jatekosok?" << endl;
    cout << "\tNev ~ 1" << endl;
    cout << "\tMezszam ~ 2" << endl;
    cout << "\tPontszam ~ 3" << endl;
    getline(cin, mi_szerintstring);
    mi_szerint = stoi(mi_szerintstring);
    fittsportegyesulet->listazSorrendben(mi_szerint);
}
