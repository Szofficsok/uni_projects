#ifndef GUI_H_INCLUDED
#define GUI_H_INCLUDED

#include "csapat.h"
#include "sportegyesulet.h"
#include "csapattipusok.h"
#include "memtrace.h"

void menu(Sportegyesulet*);
Jatekos* csapatJatekosai(int);
void bekerUjcsapat(Sportegyesulet*);
void bekerTorolcsapat(Sportegyesulet*);
void bekerJatekoskeres(Sportegyesulet*);
void bekerListazsorrendben(Sportegyesulet*);

#endif // GUI_H_INCLUDED
