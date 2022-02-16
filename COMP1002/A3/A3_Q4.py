def a3q3(string):
    para = string
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
    return mySplit


def a3q4():
    file = input("Please enter the text file name: ")
    text_list = []

    with open(file, 'r') as f:
        lines = f.readlines()
        print("Content in the text file: ")
        for i in lines:
            print(i, end='')

    print("\nWords and their Frequencies are shown as below: ")
    with open(file, 'r') as file:
        content = file.read()
    freq = dict()
    words = a3q3(content)

    Freq = {}
    freq = {}
    for word in words:
        if word in freq:
            freq[word] += 1
        else:
            freq[word] = 1

    for i in sorted(freq):
        Freq[i] = freq[i]
        f = sorted(Freq.items(), key = lambda x:x[1], reverse=True)
        dict_freq = dict(f)
    #print(dict_freq)
    print("{0:^15}".format("Words"), "|", "{0:^15}".format("Frequencies"), sep="")
    print(("-") * 30)
    for keys, values in dict_freq.items():
        print("{0:^15}".format(keys), "|", "{0:^15}".format(values),sep="", end='' "\n")

a3q4()
