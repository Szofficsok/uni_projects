#ifndef SPORTEGYESULET_H_INCLUDED
#define SPORTEGYESULET_H_INCLUDED

#include <fstream>
#include <iostream>
#include <string>
#include "csapat.h"
#include "memtrace.h"

using namespace std;

class Sportegyesulet{
    Csapat** adatbazis;
    int hossz;
public:
    Sportegyesulet(Csapat** adatbazis = nullptr, int hossz = 0);
    int getHossz() const;
    Csapat** getAdatbazis() const;
    void listazCsapatok();
    void listazSorrendben(int);
    void jatekosKeres(string);
    void ujCsapat(Csapat*);
    void torolCsapat(string);
    void beolvasFajlbol();
    void kiirFajlba();
    ~Sportegyesulet();
};

#endif // SPORTEGYESULET_H_INCLUDED
