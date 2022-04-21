#include <cstring>
#include <iostream>
#include <fstream>
#include <ctime>
#include "SHA1.hpp"
#include "SHA1.cpp"

using namespace std;

int block_no;
string block_number;
int check_0;
int check_1;

const string path = "./blockchain/";

void hash_verify(int block_NO) {
    if (block_NO == 0) {
        cout << "No Prev-Block" << endl;
    } else {
        string prev_hash;
        ifstream prev_blockFile(path + "block_" + to_string(block_NO - 1) + ".txt");
        prev_blockFile.ignore(50, '\n');
        getline(prev_blockFile, prev_hash);
        prev_hash = "prev " + prev_hash;
        cout << prev_hash << endl;

        string this_Block_prev_hash;
        ifstream blockFile(path + "block_" + to_string(block_NO) + ".txt");
        blockFile.ignore(50, '\n');
        blockFile.ignore(50, '\n');
        blockFile.ignore(50, '\n');
        blockFile.ignore(50, '\n');
        blockFile.ignore(50, '\n');
        getline(blockFile, this_Block_prev_hash);
        cout << this_Block_prev_hash << endl;
        
        if (prev_hash == this_Block_prev_hash) {
            cout << "The Blockchain Verified, no changed." << endl << endl;
        } else {
            cout << "The Blockchain was been changed!!! Please Check it!!!" << endl;
            cout << "Block " << block_NO - 1 << " has been changed" << endl << endl;
            return;
        }

        cout << "check prev one" << endl;
        hash_verify(block_NO - 1);


    }
    
}

void block_info(string block_number) {
    string block_information;
    ifstream blockFile(path + "block_" + block_number + ".txt");
    cout << endl << "-----------------------------------------------------" << endl;
    while (getline(blockFile, block_information)) {
        cout << block_information << endl;
    }
    cout << "-----------------------------------------------------" << endl;

}

void block_index(int index) {
    ofstream index_file;
    index_file.open("index.txt");
    index_file << index + 1 << endl;
    index_file.close();

}


void add_block() {
    
    string data;
    cout << "Enter data into Blockchain: " << endl;
    getline(cin, data);
    char* dt;
    time_t now = time(0);
    tm *gmtm = gmtime(&now);
    dt = asctime(gmtm);

    SHA1 checksum;
	checksum.update(data + dt);
	char hash[41];
	strcpy(hash, checksum.final().c_str());

    cout << "The SHA-1 of \"" << data << "\" is: " << hash << endl;

    string index;
    ifstream index_file("index.txt");
    if (index_file) {
        getline(index_file, index);
        block_no = stoi(index);
    } else {
        block_no = 0;
    }

    ofstream block_file;
    block_file.open(path + "block_" + to_string(block_no) + ".txt");
    block_file << "Block Number: " << block_no << endl;
    block_file << "hash: " << hash << endl;
    block_file << "data: " << data << endl;

    block_file << "Timestamp (UTC): "<< dt << endl;

    if (block_no == 0) {
        block_file << "prev hash: NULL" << endl;
    } else {
        string prev_hash;
        ifstream prev_blockFile(path + "block_" + to_string(block_no - 1) + ".txt");
        prev_blockFile.ignore(50, '\n');
        getline(prev_blockFile, prev_hash);
        block_file << "prev " << prev_hash << endl;
    }
    
    block_file.close();
    
    block_index(block_no);

}

void look(int block_num) {
    for (block_num; block_num >= 0; block_num--){
        cout << " <-- Block [" << block_num << "] ";
    }
    
}

void menu(int choose) {
    if (choose == 1) {
        int block_num;
        string index;
        ifstream index_file("index.txt");
        getline(index_file, index);
        block_num = stoi(index) - 1;

        look(block_num);

        cout << endl << endl << "Which Block want to look? - ";
        cin >> block_number;
        block_info(block_number);

        cout << endl << "Want to choose or exit? - (y for yes, n for no) ";
        string yn;
        cin >> yn;
        if (yn == "y") {
            cout << endl;
            cout << "1. Check Blockchain Information" << endl << "2. Add A New Block into Blockchain" << endl << "3. Auto Blockchain Verify" << endl << "4. Exit" << endl;
            cout << "Choose One: ";
            cin >> choose;
            cout << endl;
            menu(choose);
        } else if (yn == "n") {
            cout << endl << "Blockchain Exit..." << endl;
        }

    } else if (choose == 2) {
        getchar();
        add_block();

        cout << endl << "Want to choose or exit? - (y for yes, n for no) ";
        string yn;
        cin >> yn;
        if (yn == "y") {
            cout << endl;
            cout << "1. Check Blockchain Information" << endl << "2. Add A New Block into Blockchain" << endl << "3. Auto Blockchain Verify" << endl << "4. Exit" << endl;
            cout << "Choose One: ";
            cin >> choose;
            cout << endl;
            menu(choose);
        } else if (yn == "n") {
            cout << endl << "Blockchain Exit..." << endl;
        }
        
    } else if (choose == 3) {
        string index;
        ifstream index_file("index.txt");
        getline(index_file, index);
        block_no = stoi(index);
        
        int block_num = block_no - 1;
        hash_verify(block_num);

        cout << endl << "Want to choose or exit? - (y for yes, n for no) ";
        string yn;
        cin >> yn;
        if (yn == "y") {
            cout << endl;
            cout << "1. Check Blockchain Information" << endl << "2. Add A New Block into Blockchain" << endl << "3. Auto Blockchain Verify" << endl << "4. Exit" << endl;
            cout << "Choose One: ";
            cin >> choose;
            cout << endl;
            menu(choose);
        } else if (yn == "n") {
            cout << endl << "Blockchain Exit..." << endl;
        }

    } else if (choose == 4) {
        cout << "Blockchain Exit..." << endl;
    }

}


int main() {
    int choose;
    cout << "--- Welcome to Blockchain (Guanlin Jiang Made it)---" << endl << endl;
    cout << "1. Check Blockchain Information" << endl << "2. Add A New Block into Blockchain" << endl << "3. Auto Blockchain Verify" << endl << "4. Exit" << endl;
    cout << "Choose One: ";
    cin >> choose;
    cout << endl;


    if (choose == 1 || choose == 2 || choose == 3 || choose == 4) {
        menu(choose);

    } else {
        cout << "Error!!! Please choose again: ";
        cin >> choose;
        menu(choose);
    }

    return 0;
}