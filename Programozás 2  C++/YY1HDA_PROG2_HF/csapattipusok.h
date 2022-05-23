#ifndef CSAPATTIPUSOK_H_INCLUDED
#define CSAPATTIPUSOK_H_INCLUDED

#include "csapat.h"
#include "memtrace.h"

using namespace std;

class Foci : public Csapat{
    string edzo2;
public:
    Foci(string csapatnev = "", string edzo = "", int letszam = 0, Jatekos* jatekosok = nullptr, string edzo2 = "");
    string getEdzo2() const;
    void csapatKiir();
    void kiirFajlba(ofstream&);
};

class Kosar : public Csapat{
    int pompomszam;
public:
    Kosar(string csapatnev = "", string edzo = "", int letszam = 0, Jatekos* jatekosok = nullptr, int pompomszam = 0);
    int getPompomszam() const;
    void csapatKiir();
    void kiirFajlba(ofstream&);
};

class Kezi : public Csapat{
    int tamogatas;
public:
    Kezi(string csapatnev = "", string edzo = "", int letszam = 0, Jatekos* jatekosok = nullptr, int tamogatas = 0);
    int getTamogatas() const;
    void csapatKiir();
    void kiirFajlba(ofstream&);
};

#endif // CSAPATTIPUSOK_H_INCLUDED
