#include <iostream>
#include <math.h>
using namespace std;
double taylorsFibonachi(double x)
{
    double Sum = 0.0;
    double p = x;
    int i=1;
    do
    {
        Sum += p;
        p *= (-1.0)*pow(x, 2)/(2*i*(2*i+1));
        i++;

    }
    while (fabs(Sum-sin(x)) > 0.000000000000000000001);
    cout << "Сходится при n >= " << i << endl;
    return Sum;
}
int main() {
    std::cout << "Hello, World!" << std::endl;
    double x;
    cin >> x;
    std::cout << taylorsFibonachi(x) << endl;
    return 0;
}
