//Filip Rutka
#include <iostream>
#include <string>


class Polynomial{
public:
    Polynomial();
    Polynomial(unsigned int degres,const Z3 *a);
//    ~Polynomial();
    Polynomial& operator=(const Polynomial&);
    unsigned int operator[](unsigned int) const;
    Polynomial& operator+=(const Polynomial&);
    Polynomial& operator-=(const Polynomial&);
    Polynomial& operator*=(const Polynomial&);
    Polynomial& operator*=(Z3);
    Polynomial& operator/=(Z3);
    unsigned int degree() const;
    string toString(const std::string&) const;
    unsigned int degres;
    Z3* t;
};
//
void shorten(const Polynomial&);
Polynomial operator+(const Polynomial& a,const Polynomial& b);
Polynomial operator-(const Polynomial& a,const Polynomial& b);
Polynomial operator*(const Polynomial& a,const Polynomial& b);
Polynomial operator*(Z3 a,const Polynomial& b);
Polynomial operator/(const Polynomial& a,Z3 b);
ostream& operator<<(ostream & os,const Polynomial & b);
istream& operator>>(istream & in,Polynomial & b);
void mod(const Polynomial,const Polynomial,Polynomial&,Polynomial&);
