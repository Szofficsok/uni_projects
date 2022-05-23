#include "csapat.h"
#include "memtrace.h"

Jatekos::Jatekos(string nev, int ertek, int pont){
    this->nev = nev;
    this->ertek = ertek;
    this->pont = pont;
}

bool Jatekos::operator ==(Jatekos& masik) const{
    return (nev == masik.nev && ertek == masik.ertek && pont == masik.pont);
}

bool Jatekos::operator !=(Jatekos& masik) const{
    return !(nev == masik.nev && ertek == masik.ertek && pont == masik.pont);
}

Jatekos& Jatekos::operator =(Jatekos& masik){
    nev = masik.nev;
    ertek = masik.ertek;
    pont = masik.pont;
    return *this;
}

Csapat::Csapat(string csapatnev, string edzo, int letszam, Jatekos* jatekosok){
    this->csapatnev = csapatnev;
    this->edzo = edzo;
    this->letszam = letszam;
    this->jatekosok = jatekosok;

}

string Csapat::getCsapatnev() const{
    return csapatnev;
}

string Csapat::getEdzo() const{
    return edzo;
}

int Csapat::getLetszam() const{
    return letszam;
}

Jatekos* Csapat::getJatekosok() const{
    return jatekosok;
}

void Csapat::csere(Jatekos& egyik, Jatekos& masik){
    Jatekos ideig = egyik;
    egyik = masik;
    masik = ideig;
}

void Csapat::buborekRendezes(int mi_szerint)  {
    if(mi_szerint == 1){
        for (int i = 0; i < letszam-1; i++){
            for (int j = 0; j < letszam-i-1; j++){
                if (jatekosok[j].nev > jatekosok[j+1].nev){
                    csere(jatekosok[j], jatekosok[j+1]);
                }
            }
        }
    }
    else if(mi_szerint == 2){
        for (int i = 0; i < letszam-1; i++){
            for (int j = 0; j < letszam-i-1; j++){
                if (jatekosok[j].ertek > jatekosok[j+1].ertek){
                    csere(jatekosok[j], jatekosok[j+1]);
                }
            }
        }
    }
    else if(mi_szerint == 3){
        for (int i = 0; i < letszam-1; i++){
            for (int j = 0; j < letszam-i-1; j++){
                if (jatekosok[j].pont > jatekosok[j+1].pont){
                    csere(jatekosok[j], jatekosok[j+1]);
                }
            }
        }
    }
}

Csapat::~Csapat(){
    delete[] jatekosok;
}

