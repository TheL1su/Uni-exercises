//Filip Rutka
#include <iostream>
#include "Z3.h"
using namespace std;

Z3::Z3() : value(0){
}

Z3::Z3(short int a) {
    if(a < 0){
        if(abs(a)%3 == 2) value = (abs((int)a)-1)%3;
        if(abs(a)%3 == 1) value = (abs((int)a)+1)%3;
        if(abs(a)%3 == 0) value = (abs((int)a))%3;
    }
    else value = (int)a%3;
}
Z3::Z3(int a){
    if(a < 0){
        if(abs(a)%3 == 2) value = (abs((int)a)-1)%3;
        if(abs(a)%3 == 1) value = (abs((int)a)+1)%3;
        if(abs(a)%3 == 0) value = (abs((int)a))%3;
    }
    else value = (int)a%3;
}

Z3& Z3::operator=(int a){
    Z3 c(a);
    this->value = c.value;
    return *this;
}
Z3& Z3::operator=(const Z3 & a){
    this->value = a.value;
    return *this;
}


Z3& Z3::operator+=(const Z3 & b) {
    this->value = (this->value + b.value)%3;
    return *this;
}

Z3& Z3::operator-=(const Z3 & b) {
    if(this->value-b.value>=0) this->value = this->value-b.value;
    else this->value = this->value - b.value + 3;
    return *this;
}

Z3& Z3::operator*=(const Z3 & b) {
    this->value = (b.value * this->value)%3;
    return *this;
}

Z3& Z3::operator/=(const Z3 & b) {
    if(b.value != 0){
        if(this->value == 0) this->value = 0;
        else if(this->value == 1){
            if(b.value == 2) this->value = 2;
            else this->value = 1;
        }
        else if(this->value == 2){
            if(b.value == 2) this->value=1;
            else this->value = 2;
        }
    }
    else cout << "Dzielenie przez zero\n";
    return *this;
}


Z3::operator short int() const {
    return (short int)this->value;
}

ostream &operator<<(ostream & os,const Z3 & a) {
    os << a.value;
    return os;
}

Z3 operator+(Z3 a,const Z3 & b){
    Z3 c(a.value + b.value);
    return c;
}
Z3 operator-(Z3 a,const Z3 & b){
    Z3 c(a.value - b.value);
    return c;
}
Z3 operator*(Z3 a,const Z3 & b){
    Z3 c(a.value * b.value);
    return c;
}

Z3 operator/(Z3 a,const Z3 & b){
    short int c=-1;
    if(b.value != 0){
        if(a.value == 0) c = 0;
        else if(a.value == 1){
            if(b.value == 2) c = 2;
            else c = 1;
        }
        else if(a.value == 2){
            if(b.value == 2) c=1;
            else c = 2;
        }
    }
    else {
        cout << "Dzielenie przez zero \n";
        return a;
    }
    Z3 d(c);
    return d;
}


