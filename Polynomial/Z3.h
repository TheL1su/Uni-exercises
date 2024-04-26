//Filip Rutka
#include <iostream>
#include <sstream>

using namespace std;

class Z3{
public:
    Z3();
    Z3(short int);
    Z3(int);
    Z3& operator=(int);
    Z3& operator=(const Z3&);
    Z3& operator+=(const Z3&);
    Z3& operator-=(const Z3&);
    Z3& operator*=(const Z3&);
    Z3& operator/=(const Z3&);
    explicit operator short int() const;
    int value;
};

Z3 operator+(Z3,const Z3&);

Z3 operator-(Z3,const Z3&);

Z3 operator*(Z3,const Z3&);

Z3 operator/(Z3,const Z3&);

ostream& operator<<(ostream &,const Z3 &);

