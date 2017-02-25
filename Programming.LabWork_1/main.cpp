#include <iostream>
using namespace std;
int main() {
    int N = 0;
    cout << "Enter N number:" << endl;
    cin >> N;
    for(int i = 1; i < 9; i++)
    {
        int result = i;
        for(int j = i+1; j <= 9; j++)
        {
            result = result * 10 + j;
            if (result > N) break;
            cout << result << endl;
        }
    }
    for(int i = 9; i > 0; i--)
    {
        int result = i;
        for(int j = i - 1; j >= 0; j--)
        {
            result = result * 10 + j;
            if (result <= N)
            cout << result << endl;
            else break;
        }
    }
    return 0;
}
