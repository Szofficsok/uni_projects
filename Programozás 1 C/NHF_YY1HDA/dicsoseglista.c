#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "dicsoseglista.h"
#include "debugmalloc.h"

//Bekéri a láncolt dicsőséglista első elemére mutató pointert, majd az ehhez láncolt listát felszabadítja. Nincs visszatérési értéke.
void felszabadit_dicsoseglistat(Dicsoseglista *eleje){
	Dicsoseglista *mozgo = eleje;
	while(mozgo != NULL){
		Dicsoseglista *kovi = mozgo->kov;
		free(mozgo);
		mozgo = kovi;
	}
}
//Bekér a láncolt dicsőséglista első  elemére mutató pointert és egy beszúrandó elemet, majd a láncolt lista végére helyezi az elemet. Nincs visszatérési értéke.
void beszur_hatulra_dicsoseglista(Dicsoseglista *eleje, Dicsoseglista ujkerdes){
	Dicsoseglista *mozgo = eleje;
	Dicsoseglista *uj = (Dicsoseglista*) malloc(sizeof(Dicsoseglista));
	if(uj==NULL){
		printf("Nincs memória\n");
		return;
	}
	strcpy(uj->nev, ujkerdes.nev);
	uj->penz = ujkerdes.penz;
	uj->ido = ujkerdes.ido;
	while(mozgo->kov != NULL){
		mozgo = mozgo->kov;
	}
	mozgo->kov = uj;
	uj->kov = NULL;
}
//Bekéri a lista két egymás mellett lévő elemeire mutató pointereit és közészúrja listaelemként a bekért adatokat. Nincs visszatérési értéke.
void beszur_dicsoseglista(Dicsoseglista *elozo, Dicsoseglista *kov, char *nev, int penz, double ido){
	Dicsoseglista *uj = (Dicsoseglista*) malloc(sizeof(Dicsoseglista));
	strcpy(uj->nev, nev);
	uj->penz = penz;
	uj->ido = ido;
	elozo->kov = uj;
	uj->kov = kov;
}
//Bekéri a láncoltlista első elemére mutató pointert és az elejére szúrja listaelemként a bekért adatokat és az új láncoltlistával tér vissza.
Dicsoseglista *beszur_elore_dicsoseglista(Dicsoseglista *eleje, char *nev, int penz, double ido){
	Dicsoseglista *uj = (Dicsoseglista*) malloc(sizeof(Dicsoseglista));
	strcpy(uj->nev, nev);
	uj->penz = penz;
	uj->ido = ido;
	uj->kov = eleje;
	return uj;
}
//Bekéri a láncoltlista első elemére mutató pointert, majd új listába sorrendbe állítja pénz alapján, a régi listát felszabadítja. Visszatér a sorrendben levő láncolt lista elejére mutató pointerrel.
Dicsoseglista *dicsoseglista_sorrendbe(Dicsoseglista *eleje){
	Dicsoseglista *sorrendbe = (Dicsoseglista*) malloc(sizeof(Dicsoseglista));
	strcpy(sorrendbe->nev, eleje->nev);
	sorrendbe->penz = eleje->penz;
	sorrendbe->ido = eleje->ido;
	sorrendbe->kov = NULL;
	Dicsoseglista *mozgo1;

	for(mozgo1 = eleje->kov; mozgo1 != NULL; mozgo1 = mozgo1->kov){
		Dicsoseglista *lemarado = NULL;
		Dicsoseglista *mozgo2 = sorrendbe;
		while(mozgo2 != NULL && mozgo2->penz > mozgo1->penz){
			lemarado = mozgo2;
			mozgo2 = mozgo2->kov;
		}
		if(lemarado == NULL){
			sorrendbe = beszur_elore_dicsoseglista(mozgo2, mozgo1->nev, mozgo1->penz, mozgo1->ido);
		}
		else if(mozgo2 == NULL){
			Dicsoseglista uj;
			strcpy(uj.nev, mozgo1->nev);
			uj.penz = mozgo1->penz;
			uj.ido = mozgo1->ido;
			beszur_hatulra_dicsoseglista(lemarado, uj);
		}
		else{
			beszur_dicsoseglista(lemarado, mozgo2, mozgo1->nev, mozgo1->penz, mozgo1->ido);
		}
	}
	felszabadit_dicsoseglistat(eleje);
	return sorrendbe;
}
//Bekéri az előbb játékban levő játékos nevét, nyert pénzét és idejét, majd megnyitja a fájlt a végénél és hozzáfűzi az új adatokat. Nincs visszatérési értéke.
void dicsoseglista_keszit(char ujnev[], int penz, double ido){
	FILE *f = fopen("dicsoseglista.txt", "a");
	if (f != NULL) {
		fprintf(f,"%s\t%d Ft\t%1.1f P\n",ujnev,penz,ido);
	fclose(f);
    }
    else {
        perror("Nem sikerült megnyitni a fájlt");
    }
}
//A fájlból egy láncolt listába teszi az adatokat, majd azt a dicsoseglista_sorrendbe függvénnyel sorrendbe helyezi pénz alapján, és kiírja a képernyőre.
void dicsoseglista_kiir(){
	Dicsoseglista ideiglenes;
	Dicsoseglista *eleje = (Dicsoseglista*) malloc (sizeof(Dicsoseglista));
	FILE *f = fopen ("dicsoseglista.txt", "r");
	if (f != NULL) {
		fscanf(f,"%[^\t]\t%d Ft\t%lf P\n", eleje->nev, &eleje->penz, &eleje->ido);
		eleje->kov = NULL;
		while(fscanf(f,"%[^\t]\t%d Ft\t%lf P\n", ideiglenes.nev, &ideiglenes.penz, &ideiglenes.ido) == 3){
			beszur_hatulra_dicsoseglista(eleje,ideiglenes);
		}
		fclose(f);
    }
    else {
        perror("Nem sikerült megnyitni a fájlt");
        return;
    }
    eleje = dicsoseglista_sorrendbe(eleje);
    Dicsoseglista *mozgo = eleje;
    while(mozgo != NULL){
		printf("%s\t%d Ft\t%1.1lf Perc\n", mozgo->nev, mozgo->penz, mozgo->ido);
		mozgo = mozgo->kov;
	}
    felszabadit_dicsoseglistat(eleje);
}
