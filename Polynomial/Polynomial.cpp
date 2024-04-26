//Filip Rutka
#include "Z3.h"
#include "Polynomial.h"
#include <string>

Polynomial::Polynomial(){
    degres = 0;
    t = new Z3[1];
    t[0] = Z3(0);
}

Polynomial::Polynomial(unsigned int degres,const Z3 *a) : degres(degres){
    this->t = new Z3[degres+1];
    unsigned int last_not_zero=0;
    for(unsigned int i=0;i<=degres;i++){
        this->t[i] = a[i];
        if(a[i].value != 0) last_not_zero = i;
    }
    if(last_not_zero != degres){
        delete[] this->t;
        this->t = new Z3[last_not_zero+1];
        this->degres=last_not_zero;
        for(unsigned int i=0;i<=last_not_zero;i++){
            this->t[i] = a[i];
        }
    }
}
Polynomial& Polynomial::operator=(const Polynomial & b) {
    if(this != &b){
        delete[] this->t;
        this->degres = b.degres;
        this->t = new Z3[b.degres+1];
        for(unsigned int i=0;i<=b.degres;i++){
            this->t[i] = b.t[i];
        }
    }
    return *this;
}
//Polynomial::~Polynomial() {
//    delete[] t;
//}

unsigned int Polynomial::operator[](unsigned int pow) const {
    if(this->degres < pow){
        std::cout << "Niepoprawny indeks wielomianu\n";
        return this->t[0].value;
    }
    return this->t[pow].value;
}

void shorten(Polynomial& a){
    unsigned int last_zero=0,last_not_zero=0;
    for(unsigned int i=0;i<=a.degres;i++){
        if(a.t[i].value != 0) last_not_zero = i;
        else last_zero = i;
    }
    if(last_zero == a.degres){
        Z3* new_tab = new Z3[last_not_zero+1];
        for(unsigned int i=0;i<=last_not_zero;i++){
            new_tab[i] = a.t[i];
        }
        a.degres = last_not_zero;
        delete[] a.t;
        a.t = new_tab;
    }
}

Polynomial& Polynomial::operator+=(const Polynomial & b) {
    *this = (*this) + b;
    return *this;
}

Polynomial& Polynomial::operator-=(const Polynomial & b) {
    *this = (*this) - b;
    return *this;
}
Polynomial& Polynomial::operator*=(const Polynomial & b) {
    *this = (*this) * b;
    return *this;
}

Polynomial& Polynomial::operator*=(Z3 a) {
    for(unsigned int i=0;i<=this->degres;i++){
        this->t[i] *= a;
    }
    shorten(*this);
    return *this;
}
Polynomial& Polynomial::operator/=(Z3 a) {
    if(a.value == 0){
        cout << "Dzielenie przez zero\n";
        return *this;
    }
    for(unsigned int i=0;i<=this->degres;i++){
        this->t[i] /= a;
    }
    shorten(*this);
    return *this;
}
unsigned int Polynomial::degree() const{
    return this->degres;
}

string Polynomial::toString(const std::string& napis) const{
    std::string answer;
    if(this->degres == 0){
        if(this->t[0].value==0)answer = '0';
        if(this->t[0].value==1)answer = '1';
        if(this->t[0].value==2)answer = '2';
    }
    else if(this->degres>=1){
        for(unsigned int i=0;i<=this->degres;i++){
            if(i == 0 && this->t[0].value != 0){
                answer += std::to_string(this->t[0].value);
                answer += "+";
            }
            if(i != 0 && i != this->degres && this->t[i].value != 0){
                answer += std::to_string(this->t[i].value);
                answer += "*"+ napis +"^" + to_string(i) + "+";
            }
            if(i == this->degres){
                answer += std::to_string(this->t[i].value);
                answer += "*"+ napis + "^" + to_string(i);
            }
        }
    }
    return answer;
}


Polynomial operator+(const Polynomial & a,const Polynomial & b){
    unsigned int degree=0;
    Polynomial c;
    Z3 *new_tab;
    if(a.degres >= b.degres){
        new_tab = new Z3[a.degres+1];
        for(unsigned int i=0;i<=a.degres;i++){
            if(i<=b.degres) new_tab[i] = a.t[i] + b.t[i];
            else new_tab[i] = a.t[i];
        }
        degree = a.degres;
    }
    else{
        new_tab = new Z3[b.degres+1];
        for(unsigned int i=0;i<=b.degres;i++){
            if(i<=a.degres) new_tab[i] = a.t[i] + b.t[i];
            else new_tab[i] = b.t[i];
        }
        degree = b.degres;
    }
    c.degres = degree;
    delete[] c.t;
    c.t = new_tab;
    shorten(c);
    return c;
}

Polynomial operator-(const Polynomial& a,const Polynomial &b){
    unsigned int degree=0;
    Z3 *new_tab = nullptr;
    Polynomial c;
    if(a.degres >= b.degres){
        new_tab = new Z3[a.degres+1];
        for(unsigned int i=0;i<=a.degres;i++){
            if(i<=b.degres) new_tab[i] = a.t[i] - b.t[i];
            else{
                new_tab[i] = a.t[i];
            }
        }
        degree = a.degres;
    }
    else if(a.degres < b.degres){
        new_tab = new Z3[b.degres+1];
        for(unsigned int i=0;i<=b.degres;i++){
            if(i<=a.degres) new_tab[i] = a.t[i] - b.t[i];
            else{
                new_tab[i] = Z3(0) - b.t[i];
            }
        }
        degree = b.degres;
    }
    c.degres = degree;
    delete[] c.t;
    c.t = new_tab;
    shorten(c);
    return c;
}

Polynomial operator*(const Polynomial&a,const Polynomial & b){
    if((a.degres == 0 && a.t[0].value == 0) or (b.degres == 0 && b.t[0].value==0)){
        Polynomial c;
        return c;
    }
    unsigned int degree = a.degres + b.degres;
    Z3* new_tab = new Z3[degree+1];
    for(unsigned int i=0;i<=degree;i++){
        new_tab[i] = Z3(0);
    }
    for(unsigned int i=0;i<=a.degres;i++){
        for(unsigned int j=0;j<=b.degres;j++){
            new_tab[i+j] += a.t[i] * b.t[j];
        }
    }
    Polynomial c(degree,new_tab);
    return c;
}

Polynomial operator*(Z3 a, const Polynomial & b) {
    if(a.value == 0){
        Polynomial d;
        return d;
    }
    else{
        unsigned int degree = b.degres;
        Z3* new_tab = new Z3[b.degres+1];
        for(unsigned int i=0;i<=b.degres;i++){
            new_tab[i] = a * b.t[i];
        }
        Polynomial d(degree,new_tab);
        return d;
    }
}

Polynomial operator/(const Polynomial & a,Z3 b) {
    if(b.value == 0){
        std::cout << "Dzielenie przez zero\n";
        return a;
    }
    else {
        unsigned int degree = a.degres;
        Z3 *new_tab = new Z3[a.degres + 1];
        for (unsigned int i = 0; i <= a.degres; i++) {
            new_tab[i] = a.t[i] / b;
        }
        Polynomial d(degree, new_tab);
        return d;
    }
}

ostream &operator<<(ostream & os, const Polynomial & a) {
    os << "{";
    for(unsigned int i=0;i<a.degres;i++){
        os << a.t[i].value << ",";
    }
    os << a.t[a.degres].value << "}";
    return os;
}

istream &operator>>(istream &is,Polynomial & a){
    unsigned int i=0,count=0;
    char b = 'a';
    string wielomian;
    while (b != '{'){
        is >> b;
    }
    while (b != '}'){
        is >> b;
        if(i%2==0) count += 1;
        i+=1;
        wielomian += b;
    }
    a.degres = count-1;
    delete[] a.t;
    a.t = new Z3[count];
    count = 0;
    for(i=0;i<wielomian.length();i++){
        if(i%2==0) {
            unsigned char lol = wielomian[i];
            int val = (int)lol;
            a.t[count] = Z3(val);
            count += 1;
        }
    }
    shorten(a);
    return is;
}
void mod(const Polynomial u,const Polynomial v,Polynomial& q ,Polynomial& r){
    if(v.degres == 0 && v.t[0].value == 0){ // dzielenie przez wielomian zerowy
        cout << "Dzielenie przez zero\n";
        return;
    }
    if(v.degres == 0 && v.t[0].value != 0){ // dzielenie przez wielomian 1 lub 2
        delete[] r.t;
        r.t = new Z3[1];
        r.t[0] = Z3(0);
        r.degres = 0;
        delete[] q.t;
        q.t = new Z3[u.degres + 1];
        q.degres = u.degres;
        for(unsigned int i=0;i<=q.degres;i++){
            q.t[i] = u.t[i] / v.t[0];
        }
        return;
    }
    delete[] q.t;
    delete[] r.t;
    q.degres = u.degres;
    r.degres = v.degres;
}