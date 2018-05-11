/*
 Author: Rees de la Houssaye
 Date: 5/11/2018
 Description: The c++ counter-part to the java program. Not quite finished with this yet, but thought i'd post what I have. It works but it does not deal with invalid input in anyway.
 */

#include <iostream>
#include <string>
using namespace std;

int main()
{
    string word;
    cout << "Please enter an english word...";
    cin >> word;
    
    std::string::iterator it = word.begin();
    
    for(; *it != 'a' && *it != 'e' && *it != 'i' && *it != 'o' && *it != 'u'; it++)
    {
        
    }
    
    int i = word.find(*it);
    
    if(i == 0)
    {
        word.append("ay");
        cout << word << endl;
    }
    else
    {
        word = word.append(word.substr(0,i) + "ay");
        word.erase(0,i);
        cout << word << endl;
    }
}
