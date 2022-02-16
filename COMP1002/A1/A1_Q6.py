a = eval(input("Triangle Side A = "))
b = eval(input("Triangle Side B = "))
c = eval(input("Triangle Side C = "))


if a + b > c and c + b > a and a + c > b:
    s = (a + b + c) / 2
    area = (s * (s - a) * (s - b) * (s - c)) ** (1/2)
    print("The triangle area is ", area)

else:
    print("Can't Calculate the Area")
