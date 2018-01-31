//===========================================================================
//Joshua Cheung
//Jotcheun
//PA5
//List.c
//===========================================================================

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>
#include "List.h"

#define TRUE 1
#define FALSE 0

//-------------------------------------------------
//Private type
//-------------------------------------------------

typedef struct NodeObj
{
  	int data;
  	struct NodeObj* prev;
  	struct NodeObj* next;
} NodeObj;

typedef unsigned short bool;


typedef NodeObj* Node;

typedef struct ListObj
{
	Node front;
	Node back;
	Node cursor; 
	int index;
	int numitems;
} ListObj;



Node newNode(int node_Data) 
{
  	Node N = malloc(sizeof(NodeObj));
  	assert(N!=NULL);
  	N->data = node_Data;
  	N->prev = NULL;
  	N->next = NULL;
  	return(N);
}

void freeNode(Node* pN) 
{
	if (pN != NULL && *pN != NULL) 
	{
		free(*pN);
		*pN = NULL;
	}
}

bool isEmpty(List L)
{
	return L->numitems == 0;
}

//-------------------------------------------------
// Constructors-Destructors
//-------------------------------------------------

List newList(void)
{
  	List L = malloc(sizeof(ListObj));
  	assert(L!=NULL);
  	L->front = L->back = L->cursor = NULL;
  	L->numitems = 0;
  	L->index = -1;
  	return L;
}

void clear(List L)
{
	if (L != NULL) 
  	{
  		while (length(L) > 0) 
  		{
			deleteBack(L);
		}
	}
	L->index = -1;

}


void freeList (List* pL)
{
	clear(*pL);
	free(*pL);
	*pL = NULL;
}

// Access functions -----------------------------------------------------------

int length(List L) 
{
	if (L == NULL) 
	{
		printf("List Error: calling length() on NULL List reference\n");
		exit(1);
	}
  	return (L->numitems);
}

int index(List L) 
{  
	if (L->cursor != NULL)
	{
		return (L->index); 
	}
	return -1;
}

int front(List L)
{
	if (isEmpty(L))
	{
		fprintf(stderr, "List Error: Front() called on empty List\n");
		exit(EXIT_FAILURE);
	}
	return L->front->data;
}

int back(List L)
{
	if (isEmpty(L))
	{
		fprintf(stderr, "List Error: back() called on empty List\n");
		exit(EXIT_FAILURE);
	}
	return L->back->data;
}

int get(List L)
{
	if (isEmpty(L) || index(L) < 0)
	{
		fprintf(stderr, "List Error: get() called on empty List\n");
		exit(EXIT_FAILURE);
	}
	return L->cursor->data;
}

int equals(List A, List B)
{
	int equal = 0; 
	Node n, m;
	n = A->front;
	m = B->front;
	equal = (length(A) == length(B));

	while (equal && n!= NULL)
	{
		equal = (n->data == m->data);
		m = m->next;
		n = n->next;
	}
	return equal;
}

// Manipulation procedures ----------------------------------------------------

void moveFront(List L)
{
	if (L == NULL)
  	{
    	fprintf(stderr, "List Error: moveFront() called on empty List\n");
    	exit(EXIT_FAILURE);
  	}
  	else 
	{
		L->cursor = L->front;
		L->index = 0;
	}

}

void moveBack(List L)
{
	if (L == NULL) 
	{
		printf("List Error: calling moveBack() on NULL List reference\n");
		exit(1);
	}
  	else 
	{
		L->cursor = L->back;
		L->index = length(L) - 1;
	}
}

void movePrev(List L)
{
	if (L == NULL) 
	{
		printf("List Error: calling movePrev() on NULL List reference\n");
		exit(1);
	}
	if (L->cursor == L->front)
	{
		L->index = -1;
		L->cursor = NULL;
	}
	else if (L->cursor != NULL)
	{
		L->cursor = L->cursor->prev;
		L->index--;
	}
}

void moveNext(List L)
{
	if (L == NULL) 
	{
		printf("List Error: calling moveNext() on NULL List reference\n");
		exit(1);
	}
	if (L->cursor == L->back)
	{
		L->index = -1;
		L->cursor = NULL;
	}
	else 
	{
		L->cursor = L->cursor->next;
		L->index++;
	}
}

void prepend(List L, int data)
{
	Node n = newNode(data);

	if (L == NULL) 
	{
		printf("List Error: calling prepend() on NULL List reference\n");
		exit(1);
	}
	if (isEmpty(L))
	{
		L->front = L->back = n;
	}
	else
	{
		n->next = L->front;
		L->front->prev = n;
		L->front = n;
	}
	L->index++;
	L->numitems++;
}

void append(List L, int data)
{
	if (L == NULL) 
	{
		printf("List Error: calling append() on NULL List reference\n");
		exit(1);
	}
	if (L->front == NULL)
	{
		L->front = L->back = newNode(data);
	}
	else
	{
		Node n = newNode(data);
		L->back->next = n;
		n->prev = L->back;
		L->back = n;
	}
	L->numitems++;
}

void insertBefore(List L, int data)
{
	Node n = newNode(data);

	if (L == NULL) 
	{
		printf("List Error: calling insertBefore() on NULL List reference\n");
		exit(1);
	}
	if (isEmpty(L) || index(L) < 0)
	{
		L->front = L->back = n;
		L->index++;
		L->numitems++;
		n = NULL;
	} 
	else 
	{
		if (L->cursor->prev == NULL)
		{
			free(n);
			prepend(L, data);
		} 
		else 
		{
			Node temp = L->cursor->prev;
			temp->next = n;
			n->next = L->cursor;
			L->cursor->prev = n;
			n->prev = temp;
			L->numitems++;
			L->index++;
		}
	}
}

void insertAfter(List L, int data)
{
	Node n = newNode(data);
	
	if (L == NULL) 
	{
		printf("List Error: calling insertAfter() on NULL List reference\n");
		exit(1);
	}
	if (isEmpty(L) || index(L) < 0)
	{
		L->front = L->back = n;
		L->numitems++;
	}
	else
	{
		if (L->cursor->next == NULL)
		{
			free(n);
			append(L, data);
		}
		else
		{
			Node temp = L->cursor->next;
			L->cursor->next = n;
			temp->prev = n;
			n->next = temp;
			n->prev = L->cursor;
			L->numitems++;
		}
	}	
}

void deleteFront(List L)
{
	if (L == NULL) 
	{
		printf("List Error: calling deleteFront() on NULL List reference\n");
		exit(1);
	}
	else if (L->front == L->back)
	{
		L->cursor = L->front = L->back = NULL;
	}
	else
	{
		if (L->cursor == L->front)
		{
			L->cursor = NULL;
		}
		L->front = L->front->next;
		freeNode(&L->front->prev);
		L->front->prev = NULL;
	}
	L->numitems--;
	L->index--;
	
}

void deleteBack(List L)
{
	if (L == NULL || length(L) == 0) 
	{
		printf("List Error: calling deleteBack() on NULL List reference\n");
		exit(1);
	}
	else if (L->front == L->back)
	{
		freeNode(&L->front);
		L->cursor = L->front = L->back = NULL;
	} 
	else 
	{
		if (L->cursor == L->back)
		{
			L->cursor = NULL;
		}
		L->back = L->back->prev;
		freeNode(&L->back->next);
		L->back->next = NULL;

	}
	L->numitems--;
}

void delete(List L)
{
	if (L == NULL) 
	{
		printf("List Error: calling delete() on NULL List reference\n");
		exit(1);
	}
	else if (L->cursor == L->front && L->cursor == L->back)
	{
		L->cursor = L->front = L->back = NULL;
		L->numitems--;
	} 
	else if (L->cursor == L->front)
	{
		deleteFront(L);
	} 
	else if (L->cursor == L->back)
	{
		deleteBack(L);		
	} 
	else if (L->cursor->prev == NULL && L->cursor->next == NULL) 
	{
		L->front = L->back = NULL;
		L->index--;
		L->numitems--;
	} 
	else
	{
		L->cursor->prev->next = L->cursor->next;
		L->cursor->next->prev = L->cursor->prev;
		freeNode(&L->cursor);
		L->cursor = NULL;
		L->index--;
		L->numitems--;
	}
}

// Other operations -----------------------------------------------------------
void printList(FILE* out, List L)
{
  	Node N;
  	
  	if (L == NULL)
  	{
      	printf("List Error: calling printList() on NULL List reference\n");
      	exit(1);
   	}
   	for (N = L->front; N != NULL; N = N->next)
   	{
        fprintf(out, "%d ", N->data);
   	}
}

List copyList(List L)
{
	List b = newList();

	if (L == NULL) 
	{
		printf("List Error: calling copyList() on NULL List reference\n");
		exit(1);
	}
	if (L != NULL)
	{
		for (Node n = L->front; n!=NULL; n = n->next)
		{
			append(b, n->data);
		}
	}
	return b;
}



































