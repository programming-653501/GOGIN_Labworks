#include <iostream>
#include <stdlib.h>
#include <time.h>
#include <stdio.h>
using namespace std;
bool Desk[10][10];
bool isEmpty(int X, int Y, int ang, int typeofShip)
{
    switch(ang)
    {
        case 0:
            if(Y+typeofShip-1<=9)
            {
                int startX = X-1;
                if(startX < 0) startX = X;
                int startY = Y-1;
                if(startY < 0) startY = Y;
                int endX = X+1;
                if(endX < 0) endX = X;
                int endY = Y + typeofShip;
                if(endY < 0) endX = Y + typeofShip-1;
                for(int i = startX; i <= endX; i++)
                    for(int j = startY; j <= endY; j++)
                        if(Desk[i][j] == 1) return false;
            }
            else return false;
            break;
        case 1:
            if( Y+typeofShip-1<=9)
            {
                int startX = X-1;
                if(startX < 0) startX = X;
                int startY = Y-1;
                if(startY < 0) startY = Y;
                int endX = X + typeofShip;
                if(endX < 0) endX = X + typeofShip - 1;
                int endY = Y + 1;
                if(endY < 0) endX = Y;
                for(int i = startX; i <= endX; i++)
                    for(int j = startY; j <= endY; j++)
                        if(Desk[i][j] == 1) return false;
            }
            else return false;
            break;
    }
    return true;
}
void fillShip(int type)
{
    int x, y, angle;
    srand( clock() );
    do
    {
        srand( clock() );
        x = rand() % 10;
        y = rand() % 10;
        angle = rand() % 2;
    }
    while (isEmpty(x, y, angle, 4) == false);
    if(angle == 0) for(int i = y; i < y + type; i++) Desk[x][i] = 1;
    else for(int i = x; i < x + type; i++) Desk[i][y] = 1;
}
int main() {
    srand( clock() );
    fillShip(4);
    fillShip(3);
    fillShip(3);
    fillShip(2);
    fillShip(2);
    fillShip(2);
    fillShip(1);
    fillShip(1);
    fillShip(1);
    fillShip(1);
    for (int i = 0; i < 10; ++i)
    {
        for (int j = 0; j < 10; ++j) {
            std::cout << Desk[i][j] << " ";
        }
        std::cout << std::endl;
    }
        return 0;
}