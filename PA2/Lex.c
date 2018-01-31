//===========================================================================
//Joshua Cheung
//Jotcheun
//PA2
//Lex.c
//===========================================================================

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "List.h"

#define MAX_LEN 160

List sort(char** word, int count)
{
    List indices = newList();
    append(indices, 0);
    char* fr;
    char* ba;
    char* current;

    for (int i =1; i < count; i++)
    {
        current = *(word+i);
        fr = *(word + front(indices));
        ba = *(word + back(indices));
        moveFront(indices);

        if (current != NULL)
        {
            if (strcmp(current, ba) > 0)
            {
                append(indices, i);
                continue;
            }
            else if (strcmp(current, fr) < 0)
            {
                prepend(indices, i);
                continue;
            }   
            moveFront(indices);

            while (index(indices) != -1)
            {
                if (strcmp(current, *(word + get(indices))) < 0)
                {
                    insertBefore(indices, i);
                    break;
                }
                else{
                    moveNext(indices);
                }
            }
        }    
    }
    return indices;
}

int LineCounter(FILE* filePointer) 
{
    int curr_ch = 0;
    int numLines = 0;
    numLines++;

    while (!feof(filePointer)) 
    { 
        curr_ch = fgetc(filePointer);

        if (curr_ch == '\n') 
        { 
            numLines++; 
        }
    }
    fclose(filePointer);
    return numLines;
}

char** readLines(char* fileName)                                    //reads in a file and puts it into a char** array.
{                                       
    FILE *input;
    input = fopen(fileName, "r");
    int numLines = LineCounter(input);
    input = fopen(fileName, "r");                                   //reopen file after closing it in numLines. 
    char** all_lines = (char **)calloc(numLines,sizeof(char*));     //sets aside space for the number of lines                                                                                        
    char line[MAX_LEN];
    int counter = 0;

    while(fgets(line, MAX_LEN, input) != NULL) 
    {
        char *ind_line = malloc(strlen(line) + 1);                  //printf("ind_line: %d\n" , (int)strlen(line) + 1);
        strcpy(ind_line,line);

        if (strlen(ind_line) >= 2 && ind_line[strlen(ind_line)-1] == '\n')
        { 
            ind_line[strlen(ind_line) - 1] = '\0';
        }
        if (strlen(ind_line) == 1 && ind_line[0] == '\n') 
        {
            ind_line[0] = '\0';
        }
        all_lines[counter] = ind_line;

        if (feof(input))                                            
        {       
            break;
        }
        counter++;
    }
    fclose(input);  
    return all_lines;
}

int main(int argc, char* argv[])
{
    if (argc != 3) 
    {
        printf("Usage: Simulation input_file output_file\n");
        exit(1);
    }

    FILE* file_in = fopen(argv[1], "r");
    FILE* file_out = fopen(argv[2], "w");

    if (file_in == NULL)
    {
        printf("Unable to open file %s\n", argv[1]);
        exit(1);
    }
    if (file_out == NULL)
    {
        printf("Unable to open file %s\n", argv[2]);
        exit(1);
    }

    int line = LineCounter(file_in);
    file_in = fopen(argv[1], "r");
    char** read = readLines(argv[1]);
    List sorted = sort(read, line);

    for (moveFront(sorted); index(sorted) >= 0; moveNext(sorted))
    {
        fprintf(file_out, "%s\n", (*(read + get(sorted))));
    }
    
    for(int i = 0; i < line; i++)
    {
        free(*(read + i));
    }

    free(read);
    freeList(&sorted);
    fclose(file_in);
    fclose(file_out);
    return(0);
}
