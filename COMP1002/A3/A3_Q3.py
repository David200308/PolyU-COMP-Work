# Input: a for apple! b for boy; and c for cat, d for dog. but e for egg? No grammar mistakes checking!!
# Output: ['a', 'for', 'apple', 'b', 'for', 'boy', 'and', 'c', 'for', 'cat', 'd', 'for', 'dog', 'but', 'e', 'for', 'egg', 'No', 'grammar', 'mistakes', 'checking']

def a3q3():
    para = input("Please input a paragraph: ")
    my_temp_Split = []
    mySplit = []
    a = ''
    for i in para:
        if i == ' ' or ord(i) < 48 or 58 <= ord(i) <= 64 or 91 <= ord(i) <= 96 or ord(i) >= 123:
            my_temp_Split.append(a)
            a = ''

        else:
            a += i

    if a:
        my_temp_Split.append(a)

    for b in my_temp_Split:
        if b != '':
            mySplit.append(b)
    print(mySplit)

a3q3()