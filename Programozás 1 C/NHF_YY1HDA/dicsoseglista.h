#ifndef DICSOSEGLISTA_H_INCLUDED
#define DICSOSEGLISTA_H_INCLUDED

//ez a struktúra ad típust a dicsőséglista elkészítéséhez
typedef struct Dicsoseglista{
	char nev[31];
	int penz;
	double ido;
	struct Dicsoseglista *kov;
}Dicsoseglista;

void dicsoseglista_keszit(char ujnev[], int penz, double ido);
void dicsoseglista_kiir();

#endif // DICSOSEGLISTA_H_INCLUDED
