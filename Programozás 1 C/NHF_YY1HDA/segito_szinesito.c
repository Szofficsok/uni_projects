#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "segito_szinesito.h"
#include "debugmalloc.h"

//Bekéri a láncoltlista egy elemére mutató pointerét, majd a jóválasz segítségével egy irányított szavazógépet hoz létre. A statisztikát kiírja a képernyőre, ami segít a versenyzőnek.
void kozonsegsegit(Kerdesek *kerdes){
	int valaszok[5] = {0};
	for(int i=0;i<100;i++){
		valaszok[rand()%5]++;
	}
	int jovalasz = valaszok[0] + valaszok[1];
	if(kerdes->jovalasz == 'A'){
		printf("A: %d szavazat\n", jovalasz);
		printf("B: %d szavazat\n", valaszok[2]);
		printf("C: %d szavazat\n", valaszok[3]);
		printf("D: %d szavazat\n", valaszok[4]);
	}
	if(kerdes->jovalasz == 'B'){
		printf("A: %d szavazat\n", valaszok[2]);
		printf("B: %d szavazat\n", jovalasz);
		printf("C: %d szavazat\n", valaszok[3]);
		printf("D: %d szavazat\n", valaszok[4]);
	}
	if(kerdes->jovalasz == 'C'){
		printf("A: %d szavazat\n", valaszok[3]);
		printf("B: %d szavazat\n", valaszok[2]);
		printf("C: %d szavazat\n", jovalasz);
		printf("D: %d szavazat\n", valaszok[4]);
	}
	if(kerdes->jovalasz == 'D'){
		printf("A: %d szavazat\n", valaszok[4]);
		printf("B: %d szavazat\n", valaszok[2]);
		printf("C: %d szavazat\n", valaszok[3]);
		printf("D: %d szavazat\n", jovalasz);
	}
}
//Bekéri a láncoltlista egy elemére mutató pointerét, majd a jóválasz segítségével lefelezi a lehetőségek számát, úgy hogy a jó válasz benne marad a 2 lehetőségben és kiírja a képernyőre.
void felezessegit(Kerdesek *kerdes){
	char valaszok[3];
	valaszok[0]=kerdes->jovalasz;
	valaszok[2]='\0';
	int rnd = rand()%4+1;
	switch(rnd){
		case 1: valaszok[1] = 'A';
			break;
		case 2: valaszok[1] = 'B';
			break;
		case 3: valaszok[1] = 'C';
			break;
		case 4: valaszok[1] = 'D';
			break;
		default: valaszok[1] = 'A';
	}
	if(valaszok[1]==kerdes->jovalasz){
		felezessegit(kerdes);
		return;
	}
	int x = rand()%2;
	printf("A két megmaradt válasz: %c, %c\n", valaszok[x], valaszok[1-x]);
}
//Játék színesítő elem, amely bíztató jellegű sztringekkel tér vissza
char *dicsero(){
	int x = rand()%10+1;
	switch(x){
		case 1: return "Helyes válasz, csak így tovább!\n";
			break;
		case 2: return "Majdnem bedőlt, de szerencsére kötélidegei vannak!\n";
			break;
		case 3: return "Magabiztosan halad előre!\n";
			break;
		case 4: return "Már a sarkon az 50 millió!\n";
			break;
		case 5: return "Remélem már kigondolta mire lesz jó az a sok pénz ;)\n";
			break;
		case 6: return "Fú hát ezt még én sem tudtam volna ha nem súgják a fülembe....\n";
			break;
		case 7: return "Azt a kutya meg a fáját, maga aztán nem semmi, hogy ilyen könnyen veszi az akadályokat!\n";
			break;
		case 8: return "Még jó, hogy nem vacilál sokat, legalább hamar végig érünk a 15 kérdésen ;)\n";
			break;
		case 9: return "Na de most komolyan.... Sokat készülhetett! Bár az is lehet, hogy született zseni!\n";
			break;
		case 10: return "Huh, azt hittem elcsábul a másik válasz irányába, de szerencsére ellenállt a késztetésnek!\n";
			break;
		default: return "Helyes válasz, csak így tovább!\n";
	}
}
//Játék színesítő elem, amely a játékos elbizonytalanítására szolgáló sztringekkel tér vissza
char *elbizonytalanito(){
	int x = rand()%10+1;;
	switch(x){
		case 1: return "Remélem jól átgondolta, bár nekem más válasz jobban tetszene....Erősítsen rá szóval Ön mit gondol melyik a helyes?: ";
			break;
		case 2: return "Akkor ez a végleges döntése....kérem írja le megint, nehogy félre értsem: ";
			break;
		case 3: return "Hát igen ez a maga döntése....szóval akkor mi a válasz?: ";
			break;
		case 4: return "Rendbeeen....Azért inkább újra írja le.... Tehát a végleges válasz nem más, mint a(z): ";
			break;
		case 5: return "Ha biztos ebben, akkor írja le ide a végleges választ: ";
			break;
		case 6: return "Írja le ide a végleges választ, de tényleg csak ha jól végig gondolta, mert elég necces kérdés: ";
			break;
		case 7: return "Én bízok Önben! Kivéve ha Ön nem.... Szóval írja le ide a végleges választ, és bízzunk együtt Önben!: ";
			break;
		case 8: return "Van idő, gondolja át mégegyszer és írja le ide a tényleges döntését: ";
			break;
		case 9: return "Fogós kérdés, remélem nem hamarkodja el a döntést.... Szóval mi is akkor a válasza?: ";
			break;
		case 10: return "Csak a biztonság kedvéért, hogy félre ne értsem.... Mi a végleges döntése?: ";
			break;
		default: return "Ha biztos ebben, akkor írja le ide a végleges választ: ";
	}
}
