#include <stdio.h>
#include <stdbool.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include "jatek.h"
#include "segito_szinesito.h"
#include "debugmalloc.h"

//Megkapja hanyadik kérdésnél tartunk, és visszaadja, hogy kiszállás esetén mennyi pénzt nyer a játékos
int kilepopenz(int hanyadik){
	switch(hanyadik){
		case 1: return 10000;
			break;
		case 2: return 20000;
			break;
		case 3: return 50000;
			break;
		case 4: return 100000;
			break;
		case 5: return 250000;
			break;
		case 6: return 500000;
			break;
		case 7: return 750000;
			break;
		case 8: return 1000000;
			break;
		case 9: return 1500000;
			break;
		case 10: return 2000000;
			break;
		case 11: return 5000000;
			break;
		case 12: return 10000000;
			break;
		case 13: return 15000000;
			break;
		case 14: return 25000000;
			break;
		default: return 40000000;
	}
	return 0;
}
//Megkapja hanyadik kérdésnél tartunk, és visszaadja, hogy rossz válasz esetén mennyi pénzt nyer a játékos
int kiesopenz(int hanyadik){
    if(hanyadik<5){
		return 0;
	}
	if(hanyadik<10){
		return 250000;
	}
	if(hanyadik<15){
		return 2000000;
	}
	else{
		return 50000000;
	}
}
//Bekéri a láncolt lista első elemére mutató pointert, majd az ehhez láncolt listát felszabadítja. Nincs visszatérési értéke.
void felszabadit_listat(Kerdesek *eleje){
	Kerdesek *mozgo = eleje;
	while(mozgo != NULL){
		Kerdesek *kovi = mozgo->kov;
		free(mozgo);
		mozgo = kovi;
	}
}
//Bekér egy láncolt lista első  elemére mutató pointert és egy beszúandó elemet, majd a láncolt lista végére helyezi az elemet. Nincs visszatérési értéke.
void beszur_hatulra(Kerdesek *eleje, Kerdesek ujkerdes){
	Kerdesek *mozgo = eleje;
	Kerdesek *uj = (Kerdesek*) malloc(sizeof(Kerdesek));
	if(uj==NULL){
		printf("Nincs memória\n");
		return;
	}
	uj->nehezseg = ujkerdes.nehezseg;
	strcpy(uj->kerdes, ujkerdes.kerdes);
	strcpy(uj->a, ujkerdes.a);
	strcpy(uj->b, ujkerdes.b);
	strcpy(uj->c, ujkerdes.c);
	strcpy(uj->d, ujkerdes.d);
	uj->jovalasz = ujkerdes.jovalasz;
	strcpy(uj->tema, ujkerdes.tema);
	while(mozgo->kov != NULL){
		mozgo = mozgo->kov;
	}
	mozgo->kov = uj;
	uj->kov = NULL;
}
//Fel kell szabadítani ezt a listát! Megnyit egy fájlt és miután az első sort beolvassa egy felesleg sztringbe, utána beolvassa az adatokat egy láncolt listába aminek az első elemére mutató pointerével visszatér.
Kerdesek *beolvas(){
	Kerdesek ideiglenes;
	Kerdesek *eleje = (Kerdesek*) malloc (sizeof(Kerdesek));
	char felesleg[401];
	FILE *f = fopen ("loim.csv", "r");
	if (f != NULL) {
		fscanf(f, "%[^\n]\n", felesleg);
		fscanf(f,"%d\t%[^\t]\t%[^\t]\t%[^\t]\t%[^\t]\t%[^\t]\t%c\t%[^\n]\n", &eleje->nehezseg, eleje->kerdes, eleje->a, eleje->b, eleje->c, eleje->d, &eleje->jovalasz, eleje->tema);
		eleje->kov = NULL;
		while(fscanf(f,"%d\t%[^\t]\t%[^\t]\t%[^\t]\t%[^\t]\t%[^\t]\t%c\t%[^\n]\n", &ideiglenes.nehezseg, ideiglenes.kerdes, ideiglenes.a, ideiglenes.b, ideiglenes.c, ideiglenes.d, &ideiglenes.jovalasz, ideiglenes.tema) == 8){
			beszur_hatulra(eleje,ideiglenes);
		}
		fclose(f);
    }
    else {
        perror("Nem sikerült megnyitni a fájlt");
        return 0;
    }
	return eleje;
}
// Bekéri a láncolt lista első elemére mutató pointert, majd új listába sorrendbe állítja, a régi listát felszabadítja és egy bekért tömbbe pedig megszámolja hány kérdés van az adott nehézségekhez. Visszatér a sorrendben levő láncolt lista elejére mutató pointerrel.
Kerdesek *sorrend(Kerdesek *eleje, int nehez[]){
	Kerdesek *mozgo;
	Kerdesek *sorrendben = NULL;
	for(int j=1;j<=15;j++){
		for(mozgo = eleje; mozgo != NULL; mozgo = mozgo->kov){
			if(mozgo->nehezseg == j){
				if(sorrendben == NULL){
					sorrendben = (Kerdesek*) malloc(sizeof(Kerdesek));
					sorrendben->nehezseg = mozgo->nehezseg;
					strcpy(sorrendben->kerdes, mozgo->kerdes);
					strcpy(sorrendben->a, mozgo->a);
					strcpy(sorrendben->b, mozgo->b);
					strcpy(sorrendben->c, mozgo->c);
					strcpy(sorrendben->d, mozgo->d);
					sorrendben->jovalasz = mozgo->jovalasz;
					strcpy(sorrendben->tema, mozgo->tema);
					sorrendben->kov = NULL;
				}
				else{
					Kerdesek ideiglenes;
					ideiglenes.nehezseg = mozgo->nehezseg;
					strcpy(ideiglenes.kerdes, mozgo->kerdes);
					strcpy(ideiglenes.a, mozgo->a);
					strcpy(ideiglenes.b, mozgo->b);
					strcpy(ideiglenes.c, mozgo->c);
					strcpy(ideiglenes.d, mozgo->d);
					ideiglenes.jovalasz = mozgo->jovalasz;
					strcpy(ideiglenes.tema, mozgo->tema);
					beszur_hatulra(sorrendben, ideiglenes);
				}
				nehez[j-1]++;
			}
		}
	}
	felszabadit_listat(eleje);
	return sorrendben;
}
//Ez maga a játék. Addig tesz fel kérdéseket amíg vagy elrontja, vagy kilép, vagy eléri a 15ik kérdés végét. Használható 2 féle segítség a játék során, mindegyiket egyszer lehet csak. Kis és nagybetű is megfelel a kérdésre való válaszoláskor.
int jatek(char ujnev[]){
	bool kozonseg = true;
	bool felezo = true;
	int nehez[15]= {0};
	Kerdesek *eleje = beolvas();
	Kerdesek *kerdesek = sorrend(eleje, nehez);
	int hanyadik;
	char segitseg;
	char valasztaselir[21];
	for(hanyadik=0;hanyadik<15;	hanyadik++){
		int osszeg=0;
		for (int i = 0; i < hanyadik; i++){
			osszeg = osszeg+nehez[i];
		}
		int x = rand()%nehez[hanyadik]+osszeg;
		Kerdesek *mozgo = kerdesek;
		for(int i=0;i<x;i++){
			mozgo = mozgo->kov;
		}
		if(hanyadik==14){
			printf("\nKoncentráljon, elérkezett az 50 milliós kérdéshez!!");
		}
		printf("\n%d. %s\nA válaszok:\nA: %s\nB: %s\nC: %s\nD: %s\n",mozgo->nehezseg,mozgo->kerdes,mozgo->a,mozgo->b,mozgo->c,mozgo->d);
		if(kozonseg && felezo){
			printf("Még két féle segítsége van, szeretné használni a közönség segítségét (K)\nvagy felezzük a válaszokat (F)\nDe dönthet úgy is, hogy magától oldja meg (N)\n");
			scanf("\n%[^\n]", valasztaselir);
		}
		else if(kozonseg){
			printf("Még a közönség segítsége lehetőség maradt, ha használni szeretné (K)\nha nincs rá szüksége (N)\n");
			scanf("\n%[^\n]", valasztaselir);
		}
		else if(felezo){
			printf("Még a felezés lehetőség maradt, ha használni szeretné (F)\nha nincs rá szüksége (N)\n");
			scanf("\n%[^\n]", valasztaselir);
		}
		segitseg = valasztaselir[0];
		segitseg = toupper(segitseg);

		if(segitseg == 'K'){
			kozonseg = false;
			kozonsegsegit(mozgo);
		}
		else if(segitseg == 'F'){
            felezo = false;
			felezessegit(mozgo);
		}
		segitseg = 'N';
		char valasz;
		printf("Ön szerint mi a válasz? A B C vagy esetleg a D....: ");
		char valaszelir[21];
		scanf("\n%[^\n]", valaszelir);
		printf("%s", elbizonytalanito());
		scanf("\n%[^\n]", valaszelir);
		valasz = valaszelir[0];
		valasz = toupper(valasz);
		int nyertpenz = kiesopenz(hanyadik+1);
		if(valasz != mozgo->jovalasz){
			printf("Sajnos a helyes válasz a(z) %c, Ön számára itt a játék véget ért. :(\n", mozgo->jovalasz);
			if (nyertpenz==0){
				printf("Sajnos üres kézzel távozik, de reméljük legközelebb már nem így lesz!");
				felszabadit_listat(kerdesek);
				return nyertpenz;
			}

			else{
				printf("Az összeg amit hazavihet: %d Ft", nyertpenz);
				felszabadit_listat(kerdesek);
				return nyertpenz;
			}
			break;
		}
		else if(nyertpenz==50000000){
				printf("Gratulálunk, Ön milliomos! <3\nAz Ön által megnyert összeg 50, azaz 50 millió forint!!!!!!!!!");
				felszabadit_listat(kerdesek);
				return nyertpenz;
		}
		else{
			nyertpenz = kilepopenz(hanyadik+1);
			char dontes[21];
			printf("%s", dicsero());
			printf("Folytatni szeretné a játékot(1), vagy kiszállni a jelenlegi összeggel: %d Ft(2)?: ",nyertpenz);
			scanf("\n%[^\n]", dontes);
			if(dontes[0]=='2'){
				printf("Gratulálunk, az ön nyereménye %d Ft, reméljük élvezte a játékot, várjuk vissza máskor is!", nyertpenz);
				felszabadit_listat(kerdesek);
				return nyertpenz;
			}
		}
	}
	felszabadit_listat(kerdesek);
	return 0;
}
