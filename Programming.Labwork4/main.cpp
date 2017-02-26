#include <iostream>
#include <stdlib.h>
#include <stdio.h>
void abcAlgorithm(int lSize, FILE* fpr, FILE* fpw)
{
    char * buffer = (char*) malloc(sizeof(char) * lSize);
    if (buffer == NULL)
    {
        fputs("Ошибка памяти", stderr);
        exit(2);
    }
    while (!feof(fpr))
    {
        fgets(buffer, lSize, fpr);
        int i = 0, j = 0, start = 0;
        char ch = '@';
        std::cout << ch << std::endl;
        char * word = new char[lSize];
        char * buff_word = new char[lSize];
        while(buffer[i] != '\0')
        {
            if((toupper(buffer[i]) > ch) && (toupper(buffer[i]) <= 'Z'))
            {
                ch = toupper(buffer[i]);
                word[j] = buffer[i];
                j++;
            }
            if(buffer[i] == ' ' || i == strlen(buffer)-1)
            {
                ch = '@';
                if (strcmp(buff_word, word) == 0)
                {
                    fputs(word, fpw);
                    fputs("\n", fpw);
                }
                start = 0;
                j=0;
                memset(word, 0, lSize);
                memset(buff_word, 0, lSize);
            }
            if(buffer[i] != ' ')
            {
                buff_word[start] = buffer[i];
                start++;
            }
            i++;
        }
    }
}
int main() {
    FILE *fpread = fopen("/Users/yuragogin/ClionProjects/Programming.Labwork4/input.txt", "r");
    FILE *fpwrite = fopen("/Users/yuragogin/ClionProjects/Programming.Labwork4/output.txt", "a+");
    abcAlgorithm(256, fpread, fpwrite);
    fclose(fpread);
    fclose(fpwrite);
    return 0;
}