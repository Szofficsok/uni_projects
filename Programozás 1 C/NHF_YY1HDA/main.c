#include <stdio.h>
#include <stdbool.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include "debugmalloc.h"
#include "jatek.h"
#include "dicsoseglista.h"
#include "segito_szinesito.h"
//Az InfoC-ről átemelt ékezetes karaktereket megjelenítő programrész
#ifdef _WIN32
    #include <windows.h>
#endif

//Ez jelenik meg a játék indításakor és ki lehet választani, hogy játszani szeretne vagy dicsőséglistát nyitni az illető. Nincs visszatérési értéke.
void kezdolap(){
	char ujnev[31];
	printf("Mit szeretne csinálni? (név, ékezetmentesen)Játszani (dicsoseglista)Dicsőségtáblát megtekinteni:\n");
	scanf("%[^\n]", ujnev);
	if(strcmp(ujnev, "dicsoseglista")==0){
		dicsoseglista_kiir();
	}
	else{
		int penz;
		time_t now,now2;
		time(&now);
		penz = jatek(ujnev);
		time(&now2);
		double ido = difftime(now2,now)/60;
		dicsoseglista_keszit(ujnev,penz,ido);
	}
}

int main(void){
	#ifdef _WIN32
    SetConsoleCP(65001);
    SetConsoleOutputCP(65001);
	#endif
	srand(time(NULL));
	kezdolap();
	return 0;
}
