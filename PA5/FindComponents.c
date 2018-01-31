//===========================================================================
//Joshua Cheung
//Jotcheun
//PA5
//FindComponents.c
//===========================================================================

#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include"List.h"
#include"Graph.h"

#define MAX_LEN 100


const char DELIM[2] = " \n";

int getNum(char* str){//converts char* to int value.
    int value = 0;
    for (int i = 0;i < strlen(str);i++)
    {
        if (str[i] == '\n')
        {
            break;
        }
        else
        {
            value = value * 10 + (str[i] - '0');
        }
    }
    return value;
} 

void setupList(Graph G, List S)
{
    for (int i = 1; i < getOrder(G) + 1;i++){
        append(S,i);
    }
}

int main(int argc, char* argv[]) 
{
    FILE *input, *output;

    if (argc != 3) 
    {
        printf("Please input into correctly: %s <input file> <output file>", argv[0]);
        exit(1);
    }

    input = fopen(argv[1], "r");    
    output = fopen(argv[2], "w");

    if (input == NULL) 
    {
        printf("There is no input file to be read."); 
        exit(1);
    }

    if(output == NULL) 
    {
        printf("There is no output file to be written to.");
        exit(1);
    }

    char c_num_verticies[MAX_LEN];
    fgets(c_num_verticies, MAX_LEN, input);
    int i_num_verticies = getNum(c_num_verticies);
    Graph G = newGraph(i_num_verticies);

    int u = 0;
    int v = 0;
    char *token;
    char c_input[MAX_LEN];
    while (strncmp(fgets(c_input, MAX_LEN, input), "0 0", 3) != 0)
    {
        token = strtok(c_input, DELIM);
        u = getNum(token);
        token = strtok(NULL, DELIM);
        v = getNum(token);
        addArc(G, u, v);
    }
    List stackList = newList();
    setupList(G, stackList);
    DFS(G, stackList);
    Graph T_G = transpose(G);
    DFS(T_G, stackList);
    fprintf(output, "Adjacency list representation of G:\n");
    printGraph(output, G);

    int num_nil = 0;
    for (int i = 1;i < (getOrder(G) + 1);i++)
    {
        if(getParent(T_G, i) == (int)NIL)
        {
            num_nil++;
        }
    }

    fprintf(output, "\nG contains %i strongly connected components:", num_nil);
    int num_C = 1; 
    moveBack(stackList);
    List temp_list = newList();

    for (int i = 1;i < (getOrder(G) + 1);i++)
    {
        if (getParent(T_G, get(stackList)) != (int)NIL)
        {
        prepend(temp_list, get(stackList));
        movePrev(stackList);
        }
        else if (getParent(T_G, get(stackList)) == (int)NIL)
        {
            fprintf(output, "\nComponent %i: ", num_C);
            num_C++;
            prepend(temp_list, get(stackList));
            if (index(stackList) == 0)
            {
                printList(output, temp_list);
	            clear(temp_list);
	            continue;
            }
            else
            {
	            movePrev(stackList);
            }
            printList(output, temp_list);
            clear(temp_list);
        }
    }

    fclose(input);
    fclose(output);
}


