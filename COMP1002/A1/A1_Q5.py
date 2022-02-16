# The 3 test cases are: 18, 15, 17

number = int(input("Enters a valid positive integer in base 10 = "))
answer = 0
times = 0

while(number > 0):
    a = number % 2
    number = number // 2
    answer += int(a) * (10 ** times)
    times += 1
print("The binary is", answer)
