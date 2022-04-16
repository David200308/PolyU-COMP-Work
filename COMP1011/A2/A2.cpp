#include <iostream>
#include <iomanip>
#include <cstring>
#include <fstream>

using namespace std;

// Count the row in the textfile
int row_in_textfile() {
    ifstream textfile;
    textfile.open("textfile.txt");
    textfile >> noskipws;
    int row_in_text = 1;
    char temp_char;
    while (textfile >> temp_char) {
        if (temp_char == '\n') {
            ++row_in_text;
        }
    }
    return row_in_text;
}

// Change the upper word to lower word
char *upper_to_lower(char *text, int length) {
    for (int i = 0; i <= length; i++) {
        if ((int)text[i] <= 90 && (int)text[i] >= 65) {
            text[i] += 32;
        }
    }
    return text;
}

// Count the words in file
int count_words(int *count, int row) {
    int total = 0;
    for (int i = 0; i < row; i++) {
        total += count[i];
    }
    return total;
}

// Count the row in the ignore file
int count_row_in_ignorefile() {
    ifstream textfile;
    textfile.open("ignore.txt");

    textfile >> noskipws;
    int row = 1;
    char char1;
    while (textfile >> char1) {
        if (char1 == '\n') {
            ++row;
        }
    }
    return row;
}


// Core Part and task 2 Part
void core_and_task_2(int choose) {
    if (choose == 1) {
        int row_in_text;
        int format_length;
        ifstream textfile;
        textfile.open("textfile.txt");
        textfile >> noskipws;
        row_in_text = row_in_textfile();
        char text[row_in_text][1001];
        for (int i = 0; i < row_in_text; i++) {
            textfile.getline(text[i], 1000, '\n');
        }
        textfile.close();

        int length[row_in_text];
        for (int i = 0; i < row_in_text; i ++) {
            length[i] = strlen(text[i]);
        }

        for (int i = 0; i < row_in_text; i ++) {
            *text[i] = *upper_to_lower(text[i], length[i]);
        }

        // split
        char data[row_in_text][1001][51];
        int count_c_word[row_in_text];
        for (int i = 0; i < row_in_text; i++) {
            int index1 = 0, index2 = 0;
            for (int j = 0; j < length[i]; j++) {
                if (text[i][j] == ' ') {
                    index1 ++;
                    index2 = 0;
                }
                else {
                    if (text[i][j] == '-' || (97 <= (int)text[i][j] && (int)text[i][j] <= 122)) {
                        data[i][index1][index2++] = text[i][j];
                    }
                }
            }
            count_c_word[i] = index1 + 1;
        }

        // De-duplication
        int total_word = count_words(count_c_word, row_in_text);
        char word[total_word][51];
        int index = 0;
        for (int i = 0; i < row_in_text; i++) {
            for (int j = 0; j < count_c_word[i]; j++) {
                strcpy(word[index++], data[i][j]);
                for (int w = 0; w < index - 1; w++) {
                    if (strcmp(data[i][j], word[w]) == 0)
                        index--;
                }
            }
        }
        total_word = index;

        // sorting
        for (int i = 0; i < total_word; i++) {
            char string[51];
            for (int j = 0, k = 0; j < total_word; j++) {
                if (strcmp(word[i], word[j]) < 0) {
                    strcpy(string, word[i]);
                    strcpy(word[i], word[j]);
                    strcpy(word[j], string);
                }
            }
        }

        // finding
        int core_word_found[total_word + 1][row_in_text];
        for (int w = 0; w < total_word; w++) {
            for (int i = 0, index = 0; i < row_in_text; i++) {
                for (int j = 0; j < count_c_word[i];) {
                    if (strcmp(word[w], data[i][j++]) == 0) {
                        core_word_found[w][index++] = i + 1;
                        break;
                    }
                }
            }
        } 

        // output
        cout << "Core Part:" << endl << "*******************************" << endl;
        for (int i = 0; i < total_word; i++) {
            if (strlen(word[i]) > format_length) {
                format_length = strlen(word[i]) + 5;
            }
        } 
        for (int i = 0; i < total_word; i++) {
            cout << setw(format_length) << left << word[i];
            for (int j = 0; j < row_in_text; j++) {
                if (core_word_found[i][j] != 0) {
                    cout << setw(4) << core_word_found[i][j];
                }
            }
            cout << endl;
        }
    } else if (choose == 2) {
        ifstream textfile;
        textfile.open("textfile.txt");
        textfile >> noskipws;
        int row_in_text = row_in_textfile();
        char text[row_in_text][1001];
        for (int i = 0; i < row_in_text; i++) {
            textfile.getline(text[i], 1000, '\n');
        }
        textfile.close();

        int length[row_in_text];
        for (int i = 0; i < row_in_text; i ++) {
            length[i] = strlen(text[i]);
        }

        for (int i = 0; i < row_in_text; i ++) {
            *text[i] = *upper_to_lower(text[i], length[i]);
        }

        char data[row_in_text][1001][51];
        int count_c_word[row_in_text];
        for (int i = 0; i < row_in_text; i++) {
            int index1 = 0, index2 = 0;
            for (int j = 0; j < length[i]; j++) {
                if (text[i][j] == ' ') {
                    index1 ++;
                    index2 = 0;
                }
                else {
                    if (text[i][j] == '-' || (97 <= (int)text[i][j] && (int)text[i][j] <= 122)) {
                        data[i][index1][index2++] = text[i][j];
                    }
                }
            }
            count_c_word[i] = index1 + 1;
        }

        int total_word = count_words(count_c_word, row_in_text);
        char word[total_word][51];
        int index = 0;
        for (int i = 0; i < row_in_text; i++) {
            for (int j = 0; j < count_c_word[i]; j++) {
                strcpy(word[index++], data[i][j]);
                for (int w = 0; w < index - 1; w++) {
                    if (strcmp(data[i][j], word[w]) == 0)
                        index--;
                }
            }
        }
        total_word = index;

        for (int i = 0; i < total_word; i++) {
            char string[51];
            for (int j = 0, k = 0; j < total_word; j++) {
                if (strcmp(word[i], word[j]) < 0) {
                    strcpy(string, word[i]);
                    strcpy(word[i], word[j]);
                    strcpy(word[j], string);
                }
            }
        }

        int word_found[total_word + 1][row_in_text];
        for (int w = 0; w < total_word; w++) {
            for (int i = 0, index = 0; i < row_in_text; i++) {
                for (int j = 0; j < count_c_word[i];) {
                    if (strcmp(word[w], data[i][j++]) == 0) {
                        word_found[w][index++] = i + 1;
                        break;
                    }
                }
            }
        }

        int format_length;
        for (int i = 0; i < total_word; i++) {
            if (strlen(word[i]) > format_length) {
                format_length = strlen(word[i]) + 5;
            }
        }        

        int ig_row;
        ig_row = count_row_in_ignorefile();
        int ig_index[ig_row];
        for (int i = 0; i < ig_row; i++) {
            ig_index[i] = total_word + 1;
        }

        ifstream file;
        file.open("ignore.txt");
        file >> noskipws;
        char ig_word[ig_row][51];
        for (int i = 0; i < ig_row; i++) {
            file.getline(ig_word[i], 51, '\n');
        }
        file.close();


        int word_len[ig_row];
        for (int i = 0; i < ig_row; i++) {
            word_len[i] = strlen(ig_word[i]);
        }
        for (int i = 0; i < ig_row; i++) {
            *ig_word[i] = *upper_to_lower(ig_word[i], word_len[i]);
        }

        for (int i = 0; i < ig_row; i++) {
            for (int j = 0; j < total_word;) {
                if (strcmp(ig_word[i], word[j++]) == 0) {
                    ig_index[i] = j - 1;
                }
            }
        }
        for (int i = 0; i < ig_row; i++) {
            for (int j = 0; j < ig_row; j++) {
                if (ig_index[i] < ig_index[j]) {
                    ig_index[i] = ig_index[i] ^ ig_index[j];
                    ig_index[j] = ig_index[i] ^ ig_index[j];
                    ig_index[i] = ig_index[i] ^ ig_index[j];
                }
            }
        }

        cout << "Task 2: " << endl << "-----------------------" << endl;

        for (int i = 0, k = 0; i < total_word; i++) {
            if (i == ig_index[k]) {
                    k++;
                    continue;
                } else {
                    cout << setw(format_length) << left << word[i];
                    for (int j = 0; j < row_in_text; j++) {
                        if (word_found[i][j] != 0) {
                            cout << setw(4) << word_found[i][j];
                        }
                    }
                    cout << endl;
                }
        }
    }
}

// Menu and Display
int main() {
    core_and_task_2(1);
    int choose_menu;
    int choose;
    while (true) { 
        cout << endl;
        cout << "------------------------" << endl;
        cout << "COMP 1011 Assignment 2" << endl << "------------------------" << endl;
        cout << "Name: JIANG Guanlin" << endl;
        cout << "SID: 21093962d" << endl;
        int num = 21093962 % 3;
        cout << "Addition: Task " << num << endl;
        cout << "------------------------" << endl << endl;
        cout << "Still Want to Check(1 / 2): (1) Yes; (2) No (Quit) : ";
        cin >> choose;
        if (choose == 1) {
            cout << "Select one:" << endl << "(1) Core Part; (2) Task 2; (3) Quit"<< endl;
            cin >> choose_menu;
            if (choose_menu == 1) {
                core_and_task_2(choose_menu);
            } else if (choose_menu == 2) {
                core_and_task_2(choose_menu);
            } else if (choose_menu == 3) {
                break;
            }
        } else if (choose == 2) {
            break;
        }
        
    }

    return 0;
}