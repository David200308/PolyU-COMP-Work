#Input a string: Hello World
#Input a zero-based location in a string to be changed: 5
#Input a character to be updated in that location: @ 
#The string updated: Hello@World


def main():
    """
main() function which can help people to insert some character or words to replace some cahracters or words.
    """
    a = str(input("Input a string: "))
    b = int(input("Input a zero-based location in a string to be changed: "))
    c = str(input("Input a character to be updated in that location: "))
    
    a_list = list(a)
    a_list[b] = c
    last_list = ""
    for i in range(0, len(a_list)):
        last_list = last_list + a_list[i]

    print("The string updated:", last_list)

main()