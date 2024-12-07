#include <stdio.h>
#include <locale.h>

typedef struct carros{
	char marca[15];
	char nome[15];
	int ano;
	int preco;
}Carros;

int main(void){
	setlocale(LC_ALL,"Portuguese");
	Carros car[2];
	int i, valorMinimo, n;
	
	printf("Número de Carros Para Registrar: ");
	scanf("%d", &n);
	
	
		for(i = 0; i < n; i++){
			printf("Marca do %d° Carro: ", i + 1);
			scanf("%s", &car[i].marca);
			
			printf("Modelo do %d° Carro: ", i + 1);
			scanf("%s", &car[i].nome);
			
			printf("Ano do  %d° Carro: ", i + 1);
			scanf("%d", &car[i].ano);
			
			printf("Preço do Carro: ");
			scanf("%d", &car[i].preco);
			printf("\n");
		}
		printf("Carros Registrados:");
		
		for(i = 0; i < n; i++){
			printf("\nMarca: %s \nModelo: %s \nAno: %d \nPreço: R$ %d\n",car[i].marca, car[i].nome, car[i].ano, car[i].preco);
		}
		
		printf("\nValor Minimo do Carro: ");
		scanf("%d", &valorMinimo);
		
		for(i = 0; i < n; i++){
			if(car[i].preco < valorMinimo){
				printf("\nCarro com Melhor Preço\n");
				printf("Marca: %s \nModelo: %s \nAno: %d \nPreço: R$ %d\n",car[i].marca, car[i].nome, car[i].ano, car[i].preco);
			}	
		}
			
	return 0;
}
