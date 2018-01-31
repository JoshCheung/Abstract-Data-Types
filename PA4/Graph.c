//===========================================================================
//Joshua Cheung
//Jotcheun
//PA4
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


struct GraphObj{
	int size;
	int order;
	int source;
	int *color;
	int *parent;
	int *distance;
	List *adj_list;
}GraphObj;

//Constructors and Deconstructors
Graph newGraph(int n)
{
	Graph G = malloc(sizeof(struct GraphObj));
	G->size = 0;
	G->order = n;
	G->source = NIL;
	G->color = calloc(n + 1, sizeof(int));
	G->parent = calloc(n + 1, sizeof(int));;
	G->distance = calloc(n + 1, sizeof(int));
	G->adj_list = calloc(n + 1, sizeof(List));

	for(int i = 1; i <= n ; i++)
	{
		G->color[i] = Wh;
		G->distance[i] = INF;
		G->parent[i] = NIL;
		G->adj_list[i] = newList();
	}
	return G;
}

void freeGraph(Graph* pG)
{
	Graph G = *pG;

	for (int i = 1; i <= getOrder(G); i++)
	{
		freeList(&(G->adj_list[i]));
	}
	free(G->color);
	free(G->parent);
	free(G->distance);
	free(G->adj_list);
	free(*pG);
	*pG = NULL;
}

//Additional functions
void sortedInsert(List L, int x)
{
	if (length(L) == 0)
	{
		append(L, x);
	}
	else
	{
		moveFront(L);

		for (int i = 0;i <= length(L); i++)
		{
			if (get(L) > x)
			{
				insertBefore(L, x);
				break;
			}
			else if (get(L) == back(L))
			{
				append(L, x);
				break;
			}
			else
			{
				moveNext(L);
			}
		}
	}
}

int deQueue(List L)
{
	int x = -1;
	if (length(L) == 0)
	{
		printf("deQueue: The list is empty, there is nothing to pop.\n");
		exit(0);
	}
	else
	{
		x = front(L);
		deleteFront(L);
		return x;
	}
}

//Access Functions
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

int getSource(Graph G)
{
	if (G != NULL)
	{
		return (G->source);
	}
	else
	{
		printf("getSource: Graph is NULL, cannot process.");
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

int getDist(Graph G, int u)
{
	if (u >= 1 && u <= getOrder(G))
	{
		return (G->distance[u]);
	}
	else
	{
		printf("getDist: preconditions not met.");
		exit(0);
	}
}

void getPath(List L, Graph G, int u)
{
	int source = getSource(G);
   	int p = G->parent[u];

  	if (!(1 <= u && u <= getOrder(G)))
  	{
      	printf("Graph Error: getPath() called on an invalid vertex number\n");
      	exit(1);
   	}
   	if (source == NIL)
   	{
      	printf("Graph Error: getPath() called on a Graph with an invalid source\n");
      	exit(1);
   	}
   	if (u == source)
   	{
      	append(L, source);
   	}
   	else if (p == NIL)
   	{
      	append(L, NIL);
   	}
   	else if (p != NIL)
   	{
      	getPath(L, G, p);
      	append(L, u);
    }
}


//Manipulation Procedures
void makeNull(Graph G)
{
	for (int i = 1;i <= getOrder(G) ;i++)
	{
		clear(G->adj_list[i]);
	}
	G->size = 0;
	G->source = NIL;
}

void addEdge(Graph G, int u, int v)
{
	if (u < 1 || u > getOrder(G) || v < 1 || v > getOrder(G))
	{
    	printf("addEdge: The preconditions were not met.\n");
    	return;
  	}
	addArc(G,u,v);
    addArc(G,v,u);
    G->size--;
}

void addArc(Graph G, int u, int v)
{
   	if((u < 1 || u > getOrder(G)) || (v < 1 || v > getOrder(G))) 
   	{  
    	printf("Graph Error: calling addArc() with vertex out of bounds\n");
      	exit(1);
   	} 

  	List temp = G->adj_list[u];
   	moveFront(temp);

   	while (index(temp) != -1 && get(temp) < v)
   	{
   	    moveNext(temp);
   	}

   	if (index(temp) == -1)
   	{
        append(temp,v);
   	}
   	else 
   	{
        insertBefore(temp,v);
   	}
   	G->size++;
}


void BFS(Graph G, int s){
	for(int i = 1; i <= getOrder(G);i++)
	{
		G->color[i] = Wh;
		G->distance[i] = INF;
		G->parent[i] = NIL;
	}
	G->color[s] = Gr;
	G->distance[s] = 0;
	G->parent[s] = NIL;
	G->source = s;
	List Queue = newList();
	append(Queue,s);

	while (length(Queue) != 0) 
	{
		int curr_s = front(Queue);
		deleteFront(Queue);
		List l = G->adj_list[curr_s];

		if (length(l) == 0)
		{
			break;
		}
		for (moveFront(l); index(l) != -1; moveNext(l)) 
		{
			int val = get(l);
			if (G->color[val] == Wh) 
			{
				G->color[val] = Gr;
				G->distance[val] = (G->distance[curr_s] + 1);
				G->parent[val] = curr_s;
				append(Queue, val);
			}
		}
		G->color[curr_s] = Bl;
	}
	freeList(&Queue);
}

//Other Operations
void printGraph(FILE *out, Graph G)
{
	for (int i = 1; i <= getOrder(G); i++)
    {
        List list = G->adj_list[i];
        fprintf(out, "%d:", i);
        for (moveFront(list); index(list) != -1; moveNext(list))
        {
            fprintf(out, " %d", get(list));
        }
        fprintf(out, "\n");
   }
}
