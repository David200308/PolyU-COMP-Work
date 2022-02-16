import os
def Sign_Up(b):

    if b == 1:
        judge = False
        while not judge:

            admin_user = str(input("Admin Username: "))
            admin_user = admin_user.lower()
            data_file = f'./admin/user/{admin_user}.txt'
            pathDir = os.listdir('./admin/user')
            T = admin_user + '.txt'

            if T in pathDir:
                print("Error! This Admin Account has been registered, please sign in or register a new account.")
            else:
                judge = True
                admin_passwd = str(input("Enter Admin Password: "))
                with open(data_file, 'w') as f:
                    f.write(admin_passwd)

    elif b == 2:
        judge = False
        while not judge:

            student_user_ID = str(input("Student User ID: "))
            student_user_ID = student_user_ID.lower()
            data_file = f'./student/{student_user_ID}.txt'
            pathDir = os.listdir('./student')
            T = student_user_ID + '.txt'

            if T in pathDir:
                print("Error! This Student Account has been registered, please sign in or register a new account.")
            else:
                judge = True
                student_passwd = str(input("Enter Student Password: "))
                with open(data_file, 'w') as f:
                    f.write(student_passwd)