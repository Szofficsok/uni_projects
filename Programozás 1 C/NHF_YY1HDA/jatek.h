#ifndef JATEK_H_INCLUDED
#define JATEK_H_INCLUDED

//ez a struktúra alkalmas a txt fájlba levő adatok lemodellezésére láncolt listával
typedef struct Kerdesek{
	int nehezseg;
	char kerdes[201], a[51], b[51], c[51], d[51], tema[21];
	char jovalasz;
	struct Kerdesek *kov;
}Kerdesek;

int jatek(char ujnev[]);

#endif // JATEK_H_INCLUDED
