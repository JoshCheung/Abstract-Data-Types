//===========================================================================
//Joshua Cheung
//Jotcheun
//PA5
//Graph.c
//===========================================================================


#include <stdio.h>
#include <stdlib.h>
#include "List.h"
#include "Graph.h"
#include <string.h>

#define Wh 1
#define Gr 2
#define Bl 3

struct GraphObj
{
	int size;
	int order;
	int time;
	int *color;
	int *parent;
	int *discover;
	int *finish;
	List *adj_list;
}GraphObj;

/*Constructor and Destructors*/
Graph newGraph(int n)
{
	Graph G = malloc(sizeof(struct GraphObj));
	G->size = 0;
	G->order = n;
	G->time = UNDEF;
	G->color = calloc(n + 1, sizeof(int));
	G->parent = calloc(n + 1, sizeof(int));;
	G->discover = calloc(n + 1, sizeof(int));
	G->finish = calloc(n + 1, sizeof(int));
	G->adj_list = calloc(n + 1, sizeof(List));
	for (int i = 1; i <= n; i++)
	{
		G->discover[i] = UNDEF;
		G->finish[i] = UNDEF; 
		G->color[i] = UNDEF;
		G->parent[i] = NIL;
		G->adj_list[i] = newList();
	}
	return G;
}

void freeGraph(Graph* pG)
{
	Graph G = *pG;
	free(G->color);
	free(G->parent);
	free(G->discover);
	free(G->finish);
	for(int i = 1; i <= G->order; i++)
	{
		clear(G->adj_list[i]);
	}
	free(G->adj_list);
	free(G);
	*pG = NULL;
}

void sortedInsert(List L, int x)
{
	if (length(L) == 0)
	{
		append(L, x);
	}
	else
	{
		moveFront(L);

		for (int i = 0; i <= length(L); i++)
		{
			if (get(L) > x)
			{
				insertBefore(L, x);
				break;
			}
			else if (get(L) == back(L))
			{
				insertAfter(L, x);
				break;
			}
			else
			{
				moveNext(L);
			}
		}
	}
}

/*Access Functions*/
int getOrder(Graph G)
{
	if (G != NULL)
	{
		return (G->order);
	}
	else
	{
		printf("getOrder: Graph is NULL, cannot process.");
		exit(0);
	}
}

int getSize(Graph G)
{
	if (G != NULL)
	{
		return (G->size);
	}
	else
	{
		printf("getSize: Graph is NULL, cannot process.");
		exit(0);
	}
}

int getParent(Graph G, int u)
{
	if (u >= 1 && u <= getOrder(G))
	{
		return (G->parent[u]);
	}
	else
	{
		printf("getParent: preconditions not met.");
		exit(0);
	}
}

int getDiscover(Graph G, int u)
{
	if(G == NULL) 
	{
		printf("Graph Error: calling getDiscover() on NULL Graph reference\n");
		exit(1);
	}
	if(u<1 || u>getOrder(G)) 
	{
		printf("Graph Error: calling getDiscover() with out of bounds value\n");
		exit(1);
	}
	return (G->discover[u]);
}

int getFinish(Graph G, int u)
{
	if(G == NULL) 
	{
		printf("Graph Error: calling getFinish() on NULL Graph reference\n");
		exit(1);
	}

	if(u<1 || u>getOrder(G)) 
	{
		printf("Graph Error: calling getFinish() with out of bounds value\n");
		exit(1);
	}
	return (G->finish[u]);
}

/*Manipulation Procedures*/
void addEdge(Graph G, int u, int v)
{
	if (u < 1 || u > getOrder(G) || v < 1 || v > getOrder(G))
	{
    	printf("addEdge: The preconditions were not met.\n");
    	return;
  	}
	sortedInsert(G->adj_list[u], v);//when added new numbers to the list will be sorted.
	G->size++;
	sortedInsert(G->adj_list[v], u);
}

void addArc(Graph G, int u, int v)
{
	if (u < 1 || u > getOrder(G) || v < 1 || v > getOrder(G))
	{
    	printf("addArc: The preconditions were not met.\n");
    	return;
  	}
  	G->size++;
	sortedInsert(G->adj_list[u], v);
}

void Visit(Graph G, List S, int u, int time){
	G->color[u] = Gr;
	G->time++;
	G->discover[u] = G->time;
	List l = G->adj_list[u];
	if(length(l) != 0)
	{
		for (moveFront(l); index(l) != -1; moveNext(l))
		{
			if(G->color[get(l)] == Wh)
			{
				G->parent[get(l)] = u;
				Visit(G, S, get(l),G->time);
			}
		}
	}

	G->color[u] = Bl;
	G->time++;
	G->finish[u] = G->time;
	prepend(S,u);
}

void DFS(Graph G, List S)
{
	for(int i = 1; i <= G->order; i++)
	{
		G->color[i] = Wh;
		G->parent[i] = NIL;
	}
	G->time = 0;

	List other_S = copyList(S);
	clear(S);

	for(moveFront(other_S); index(other_S) != -1; moveNext(other_S))
	{
		if(G->color[get(other_S)] == Wh)
		{
			Visit(G, S, get(other_S), G->time);
		} 
	}
}


/*Other Functions*/
Graph transpose(Graph G)
{
	Graph T_G = newGraph(G->order);
	for (int i = 1; i <= G->order; i++)
	{
		List l = G->adj_list[i];
		if (length(l) != 0)
		{
			for (moveFront(l);index(l) != -1;moveNext(l))
			{
				addArc(T_G, get(l), i);
			}
		}
	}
	return T_G;
}

Graph copyGraph(Graph G)
{
	Graph new_G = newGraph(G->order);
	for (int i = 1; i <= G->order;i++)
	{
		new_G->adj_list[i] = copyList(G->adj_list[i]);
	}
	return new_G;
}

void printGraph(FILE *out, Graph G)
{
	for (int i = 1; i <= G->order; i++)
	{
		if (length(G->adj_list[i]) == 0)
		{
			fprintf(out, "%d: \n",i);
		}
		else
		{
			fprintf(out, "%d: ",i);
			printList(out, G->adj_list[i]);
			fprintf(out, "\n");
		}
	}
}






