#include <iostream>
#include <vector>
#include <algorithm>
#include <iterator>
#include <numeric>
#include <cctype>
#include <locale>
#include <map>
#include <fstream>
using namespace std;

/**
 * Removes all non alphanumeric characters from given word and converts to lower case.
 * @param[in,out] word on return word consist only of lower case characters.
 */
void toLowerAlpha(std::string & s1){
    s1.erase(std::remove_if(s1.begin(), 
                              s1.end(),[](unsigned char c) { 
                    if (c < 'A' || c > 'Z' && c < 'a' || c > 'z') {return true;}
                    return false;}
                    ),s1.end());
    std::transform(s1.cbegin(), s1.cend(),s1.begin(),[](unsigned char c) {return (char)std::tolower(c);});
}

template<typename A, typename B>
pair<B,A> flip_pair(const pair<A,B> &p)
{
    return pair<B,A>(p.second, p.first);
}

template<typename A, typename B>
multimap<B,A> flip_map(const map<A,B> &src)
{
    multimap<B,A> dst;
    transform(src.begin(), src.end(), inserter(dst, dst.begin()), 
                   flip_pair<A,B>);
    return dst;
}

int main(){
    ifstream myfile;
    myfile.open("hamletEN.txt");
    int count = 0;
    std::string word;
    map<string, int> c;
    std::vector<int> v;
    while( myfile >> word) {
       toLowerAlpha(word);
       if(word != ""){
           c[word]++;
           count++;
       }
    }
    // ...
    multimap<int, string> m2=flip_map<string,int>(c);
    multimap<int, string>::const_iterator it;
    int i=0;
    cout << "Number of distinct words : " << count << endl;
    cout << "\nThe top 20 most popular words: \n";
    for (auto rit=m2.rbegin(); i<20;i++, ++rit) std::cout << rit->second << " : "  << rit->first << '\n';
    myfile.close();
    return 0;
}
/*
 * Expected output for ./words < hamletEN.txt

Number of distinct words : 4726

The top 20 most popular words:
the : 1145
and : 967
to : 745
of : 673
i : 569
you : 550
a : 536
my : 514
hamlet : 465
in : 437
it : 417
that : 391
is : 340
not : 315
lord : 311
this : 297
his : 296
but : 271
with : 268
for : 248
your : 242

 */