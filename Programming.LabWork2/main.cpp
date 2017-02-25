#include <iostream>
#define DELIVERY 15000
using namespace std;
unsigned int potatoMass;
unsigned int carrotMass;
unsigned int beetMass;
double valueOfDiscount();
void Feedback();
void Basket();
void price();
void setPotatoOrder();
void setCarrotOrder();
void setBeetOrder();
int main(){
    int ind;
    cout <<  "1. Заказ картофеля (кг).\n"
         <<  "2. Заказ моркови (кг).\n"
         <<  "3. Заказ свеклы (кг).\n"
         <<  "4. Корзина (вывод сведений о количестве заказанных овощей и\n"
         <<  "их стоимости).\n"
         <<  "5. Расчет стоимости заказа (сведения о стоимости каждого вида\n"
         <<  "овощей, затратах на доставку, скидке на данный заказ, общей\n"
         <<  "стоимости заказа).\n"
         <<  "6. Обратная связь (название магазина, номер лицензии,\n"
         <<  "контактная информация).\n"
         <<  "0. Выход из программы.\n";
    do
    {
        cin >> ind;
        switch (ind)
        {
            case 1:
                setPotatoOrder();
                break;
            case 2:
                setCarrotOrder();
                break;
            case 3:
                setBeetOrder();
                break;
            case 4:
                Basket();
                break;
            case 5:
                price();
                break;
            case 6:
                Feedback();
                break;
            case 0:
                ind = 0;
                exit(0);
            default:
                cout << "Something goes wrong!";
                ind = 0;
                break;
        }
    }
    while (ind != 0);
    return 0;
}
void setPotatoOrder()
{
    cout << "Enter potato mass!!!" << endl;
    unsigned int kilos;
    cin >> kilos;
    if (kilos < 0)
    {
        cout << "mass is incorrect";
        exit(1);
    }
    potatoMass = kilos;
}
void setCarrotOrder()
{
    cout << "Enter potato mass!!!" << endl;
    unsigned int kilos;
    cin >> kilos;
    if (kilos < 0)
    {
        cout << "mass is incorrect";
        exit(1);
    }
    carrotMass = kilos;
}
void setBeetOrder()
{
    cout << "Enter potato mass!!!" << endl;
    unsigned int kilos;
    cin >> kilos;
    if (kilos < 0)
    {
        cout << "mass is incorrect";
        exit(1);
    }
    beetMass = kilos;
}
double valueOfDiscount(){
    int value;
    if(potatoMass+carrotMass+beetMass > 10)
    {
        return 0.1;
    }
    else if(potatoMass+carrotMass+beetMass > 25)
    {
        return 0.2;
    }
    else if(potatoMass+carrotMass+beetMass > 50)
    {
        return 0.3;
    }
    else return 0;
}
double getPotatoPrice()
{
    return (1.0-valueOfDiscount())*500*potatoMass;
}
double getCarrotPrice()
{
    return (1.0-valueOfDiscount())*1000*carrotMass;
}
double getBeetPrice()
{
    return (1.0-valueOfDiscount())*700*beetMass;
}
void price()
{
    double Cost = getPotatoPrice() + getCarrotPrice() + getBeetPrice() + DELIVERY;
    cout << "Общая цена: " << Cost << endl;
    cout << "Морковь: " << carrotMass << "kilos " << getCarrotPrice() << "BYR" << endl;
    cout << "Картошка: " << potatoMass << "kilos " << getPotatoPrice() << "BYR" << endl;
    cout << "Свекла: " << beetMass << "kilos " << getBeetPrice() << "BYR" << endl;
    cout << "Доставка: " << DELIVERY << endl;
    cout << "Скидка " << valueOfDiscount() << endl;
}
void Basket()
{
    cout << "Морковь: " << carrotMass << "kilos " << getCarrotPrice() << "BYR" << endl;
    cout << "Картошка: " << potatoMass << "kilos " << getPotatoPrice() << "BYR" << endl;
    cout << "Свекла: " << beetMass << "kilos " << getBeetPrice() << "BYR" << endl;
}
void Feedback()
{
    cout << "eVegetables Inc.\n "
         << "mail@evg.com \n "
         << "licence: none \n "
         << "Republic of Belarus, Vitebsk region, 210035\n" << endl;
}