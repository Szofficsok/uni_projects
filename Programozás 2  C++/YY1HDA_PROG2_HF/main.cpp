#include <iostream>
#include "sportegyesulet.h"
#include "csapattipusok.h"
#include "csapat.h"
#include "nagyhf_tesztek.hpp"
#include "gui.h"
#include "memtrace.h"

using namespace std;

int main(){
    Sportegyesulet *fittsportegyesulet = new Sportegyesulet;
    fittsportegyesulet->beolvasFajlbol();
    menu(fittsportegyesulet);
    fittsportegyesulet->kiirFajlba();
    teszt_sportegyesulet();
    teszt_csapat();
    teszt_csapattipusok();
    delete fittsportegyesulet;
    return 0;
}
