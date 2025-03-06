#include <stdio.h>
int main(void){
	int horaInicial, minutoInicial, diaInicial, segundoInicial;
	
	int horaFinal, minutoFinal, diaFinal, segundoFinal;
	
	scanf("%d", &diaInicial);
	scanf("%d", &horaInicial);
	scanf("%d", &minutoInicial);
	scanf("%d", &segundoInicial);
 
	scanf("%d", &diaFinal);
	scanf("%d", &horaFinal);
	scanf("%d", &minutoFinal);
	scanf("%d", &segundoFinal);

	segundoFinal = segundoFinal - segundoInicial;
	minutoInicial = minutoFinal - minutoInicial;
	horaInicial = horaFinal - horaInicial;
	diaInicial = diaFinal - diaInicial;

	if(segundoInicial < 0){
		segundoInicial += 60;
		minutoInicial --;
	}

	if(minutoInicial < 0){
		minutoInicial += 60;
		horaInicial --;
	}

	if(horaInicial < 0){
		horaInicial += 24;
		diaInicial --;
	}

	printf("%d dia(s)\n", diaInicial);
	printf("%d hora(s)\n", horaInicial);
	printf("%d minuto(s)\n", minutoInicial);
	printf("%d segundo(s)\n", segundoInicial);


return 0;
}
