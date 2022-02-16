# Q3A input: 4, -5, 6, 2, 0, -1, -7, 10, 3
# Q3A output: The minimum number is -7 .
#         Its location is 6 .
# Q3B input: 4, -5, 6, 2, 0, -1, -7, 10, 3
# Q3B output: A list of sorting values in ascending order:  [-7, -5, -1, 0, 2, 3, 4, 6, 10] .


def partA():
    """
partB() function is sorting function which from small number to big number
    """
    numbers_lst = list(eval(input("Please enter a list of differnt numbers separated by ',': ")))
    min_num = numbers_lst[0]
    for i in range(0, len(numbers_lst)):
        if numbers_lst[i] < min_num:
            min_num = numbers_lst[i]
    for j in range(0, len(numbers_lst)):
        if min_num == numbers_lst[j]:
            location = j
            
    print("The minimum number is", min_num, ".")
    print("Its location is", location, ".")
        

def partB():
    """
partB() function is sorting function which from small number to big number
    """

    numbers_lst = list(eval(input("Please enter a list of differnt numbers separated by ',': ")))
    for j in range(len(numbers_lst)):
        for z in range(1, len(numbers_lst)):
            if numbers_lst[z - 1] > numbers_lst[z]:
                numbers_lst[z - 1], numbers_lst[z] = numbers_lst[z], numbers_lst[z - 1]

    print("A list of sorting values in ascending order: ", numbers_lst, ".")


partA()
partB()
