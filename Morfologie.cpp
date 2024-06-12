//Filip Rutka
#include "Morfologia.h"
#include <string>
#include <iostream>
#include <vector>
using namespace std;

class BitmapaExt final : public Bitmapa{
public:
    BitmapaExt(unsigned x,unsigned y,unsigned z) : x(x),y(y),z(z){ // konstruktor z rozmiarem

        t = new bool**[x]; // alokacja pamieci
        for (unsigned i = 0; i<x; i++){
            t[i] = new bool*[y];
            for (unsigned j=0;j<y;j++){
                t[i][j] = new bool[z];
            }
        }

        for (unsigned i = 0; i < x; i++) {
            for (unsigned j = 0; j < y; j++) {
                for (unsigned k = 0; k < z; k++) {
                    t[i][j][k] = false; // bialy to false
                }
            }
        }
    }
    explicit BitmapaExt(const Bitmapa & a) : x(a.sx()),y(a.sy()),z(a.sz()){
        t = new bool**[a.sx()]; // alokacja pamieci
        for (unsigned i = 0; i<a.sx(); i++){
            t[i] = new bool*[a.sy()];
            for (unsigned j=0;j<a.sy();j++){
                t[i][j] = new bool[a.sz()];
            }
        }

        for (unsigned i = 0; i < a.sx(); i++) {
            for (unsigned j = 0; j < a.sy(); j++) {
                for (unsigned k = 0; k < a.sz(); k++) {
                    bool value = a(i,j,k);
                    t[i][j][k] = value;
                }
            }
        }
    }
    BitmapaExt& operator=(const Bitmapa & a){
        for (unsigned i = 0; i < a.sx(); i++) {
            for (unsigned j = 0; j < a.sy(); j++) {
                for (unsigned k = 0; k < a.sz(); k++) {
                    bool value = a(i,j,k);
                    this->t[i][j][k] = value; // bialy to false
                }
            }
        }
        return *this;
    }

    void Brzegowy(unsigned x_,unsigned y_,unsigned z_,Bitmapa &a,int mode) const {
        bool x_down, x_up, y_down, y_up, z_down, z_up;
        int white=0,black=0;

        if (x_ == 0) x_down = a(x_, y_, z_);
        else {
            x_down = a(x_ - 1, y_, z_);
            if(x_down) black += 1;
            else white += 1;
        }

        if (x_ == a.sx() - 1) x_up = a(x_, y_, z_);
        else {
            x_up = a(x_ + 1, y_, z_);
            if(x_up) black += 1;
            else white += 1;
        }

        if (y_ == 0) y_down = a(x_, y_, z_);
        else {
            y_down = a(x_, y_ - 1, z_);
            if(y_down) black += 1;
            else white += 1;
        }
        if (y_ == a.sy() - 1) y_up = a(x_, y_, z_);
        else {
            y_up = a(x_, y_ + 1, z_);
            if(y_up) black += 1;
            else white += 1;
        }

        if (z_ == 0) z_down = a(x_, y_, z_);
        else {
            z_down = a(x_, y_, z_ - 1);
            if(z_down) black += 1;
            else white += 1;
        }
        if (z_ == a.sz() - 1) z_up = a(x_, y_, z_);
        else {
            z_up = a(x_, y_, z_ + 1);
            if(z_up) black += 1;
            else white += 1;
        }
        if (mode == 1 && !(x_down && x_up && y_down && y_up && z_down && z_up)) this->t[x_][y_][z_] = true;
        if (mode == 0 && (x_down || x_up || y_down || y_up || z_down || z_up)) this->t[x_][y_][z_] = true;
        if (mode == 2 && ((black > 3 && !a(x_, y_, z_)) || (white > 3 && a(x_, y_, z_)))) this->t[x_][y_][z_] = true;
    }
    unsigned sx() const override { return this->x; }
    unsigned sy() const override { return this->y; }
    unsigned sz() const override { return this->z; }

    bool& operator()(unsigned x_, unsigned y_, unsigned z_) override { return this->t[x_][y_][z_]; }
    bool operator()(unsigned x_, unsigned y_, unsigned z_) const override { return this->t[x_][y_][z_]; }

    bool*** t;
    unsigned x,y,z;
};


inline ostream &operator<<(ostream& os, const Bitmapa & a){
    os << "{\n";
    for(unsigned i=0;i<a.sx();i++){
        os << " {\n";
        for(unsigned j=0;j<a.sy();j++){
            os << "  {";
            for(unsigned k=0;k<a.sz()-1;k++){
                os << a(i,j,k) << ",";
            }
            if(j < a.sy()-1) os << a(i,j,a.sz()-1) << "},\n";
            else os << a(i,j,a.sz()-1) << "}\n";
        }
        if(i < a.sx()-1) os << " },\n";
        else os << " }\n";
    }
    os << "}";
    return os;
}



class Inwersja : public Przeksztalcenie{
public:
    void przeksztalc(Bitmapa &a) override {
        for (unsigned i = 0; i < a.sx(); i++) {
            for (unsigned j = 0; j < a.sy(); j++) {
                for (unsigned k = 0; k < a.sz(); k++) {
                    bool negation = !a(i,j,k);
                    a(i,j,k) = negation;
                }
            }
        }
    }
};


class Erozja : public Przeksztalcenie{
public:
    void przeksztalc(Bitmapa &a) override{
        BitmapaExt help(a.sx(),a.sy(),a.sz());
        for (unsigned i = 0; i < a.sx(); i++) {
            for (unsigned j = 0; j < a.sy(); j++) {
                for (unsigned k = 0; k < a.sz(); k++) {
                    if(a(i,j,k)) help.Brzegowy(i,j,k,a,1); //brzegowe to sa czarne czyli 1
                }
            }
        }
        for (unsigned i = 0; i < a.sx(); i++) {
            for (unsigned j = 0; j < a.sy(); j++) {
                for (unsigned k = 0; k < a.sz(); k++) {
                    if(help(i,j,k)) a(i,j,k) = false; //ustawiamy kolor na bialy
                }
            }
        }
    }
};


class Dylatacja : public Przeksztalcenie{
public:
    void przeksztalc(Bitmapa &a) override{
        BitmapaExt help(a.sx(),a.sy(),a.sz());
        for (unsigned i = 0; i < a.sx(); i++) {
            for (unsigned j = 0; j < a.sy(); j++) {
                for (unsigned k = 0; k < a.sz(); k++) {
                    if(!a(i,j,k))help.Brzegowy(i,j,k,a,0); //brzegowe to sa biale czyli 0
                }
            }
        }
        for (unsigned i = 0; i < a.sx(); i++) {
            for (unsigned j = 0; j < a.sy(); j++) {
                for (unsigned k = 0; k < a.sz(); k++) {
                    if(help(i,j,k)) a(i,j,k) = true; //zamiana na biale
                }
            }
        }
    }
};

class Zerowanie : public Przeksztalcenie{
public:
    void przeksztalc(Bitmapa &a) override {
        for (unsigned i = 0; i < a.sx(); i++) {
            for (unsigned j = 0; j < a.sy(); j++) {
                for (unsigned k = 0; k < a.sz(); k++) {
                    bool negation = false;
                    a(i,j,k) = negation;
                }
            }
        }
    }
};

class Usrednianie: public Przeksztalcenie{
public:
    void przeksztalc(Bitmapa &a) override{
        BitmapaExt help(a.sx(),a.sy(),a.sz());
        for (unsigned i = 0; i < a.sx(); i++) {
            for (unsigned j = 0; j < a.sy(); j++) {
                for (unsigned k = 0; k < a.sz(); k++) {
                    help.Brzegowy(i,j,k,a,2); //brzegowe to sa biale czyli 0
                }
            }
        }
        for (unsigned i = 0; i < a.sx(); i++) {
            for (unsigned j = 0; j < a.sy(); j++) {
                for (unsigned k = 0; k < a.sz(); k++) {
                    if(help(i,j,k)){
                        bool negation = !a(i,j,k);
                        a(i,j,k) = negation;
                    }
                }
            }
        }
    }
};

class ZlozeniePrzeksztalcen : public Przeksztalcenie{
public:
    ZlozeniePrzeksztalcen() {};

    void przeksztalc(Bitmapa &a) override{
        for(Przeksztalcenie* przeksztalcenie : w){
            przeksztalcenie->przeksztalc(a);
        }
    }
    void dodajPrzeksztalcenie(Przeksztalcenie* p){
        this->w.push_back(p);
    }

    std::vector < Przeksztalcenie* > w;
};
