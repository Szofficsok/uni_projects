#ifndef CSAPAT_H_INCLUDED
#define CSAPAT_H_INCLUDED

#include <string>
#include "memtrace.h"

using namespace std;

struct Jatekos{
    string nev;
    int ertek, pont;
    Jatekos(string nev = "", int ertek = 0, int pont = 0);
    bool operator ==(Jatekos&) const;
    bool operator !=(Jatekos&) const;
    Jatekos& operator =(Jatekos&);
};

class Csapat{
    string csapatnev, edzo;
    int letszam;
    Jatekos* jatekosok;
public:
    Csapat(string csapatnev="", string edzo="", int letszam=0, Jatekos* jatekosok=nullptr);
    string getCsapatnev() const;
    string getEdzo() const;
    int getLetszam() const;
    Jatekos* getJatekosok() const;
    void csere(Jatekos&, Jatekos&);
    void buborekRendezes(int);
    virtual void csapatKiir(){}
    virtual void kiirFajlba(ofstream&){}
    virtual ~Csapat();
};

#endif // CSAPAT_H_INCLUDED
