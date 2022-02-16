import csv

def judgement2(workshop_list1,signup):
    controller = True
    for i in range(len(workshop_list1)):
        if controller:
            if signup == workshop_list1[i][1] and int(workshop_list1[i][4]) > 0:
                controller = False
    return controller


def S1(student_user_ID):

    log = f'./admin/document/log.txt'
    data_file = f'./student/{student_user_ID}.txt'
    workshop_csv = f'./admin/document/workshop.csv'
    workshop_list1 = []

    with open(workshop_csv, 'r', encoding='utf-8') as file:
        lines = file.readlines()
        file.close()

    for line in lines:
        workshop_content = line.split(',')
        workshop_list1.append(workshop_content)
    
    while True:
        print()
        print("This is the list of workshops:")
        field_names = ['Index', 'Workshop_ID', 'Workshop_Name', 'Total','Remain']
        print('-' * 104)
        print('{0:^20}'.format(field_names[0]), '|', '{0:^20}'.format(field_names[1]),'|','{0:^20}'.format(field_names[2]),'|','{0:^20}'.format(field_names[3]),'|','{0:^20}'.format(field_names[4]),sep='')
        print('-' * 104)
        for line in lines:
            workshop_content = line.split(',')
            print('{0:^20}'.format(workshop_content[0]), '|', '{0:^20}'.format(workshop_content[1]),'|','{0:^20}'.format(workshop_content[2]), '|','{0:^20}'.format(workshop_content[3]), '|','{0:^20}'.format(workshop_content[4][0:-1]), sep='')

        ask = int(input("Enter 1 for Read Workshop Description; Enter 2 for Skip: "))
        if ask == 1:
            student_check_ws1()
            z = str(input('Enter "Q" or "q" to reselete: '))
        elif ask == 2:
            break

    with open(data_file,'r') as f:
        print()
        print('Your choice:')
        data = f.readlines()              
        f.close

    s1 = ''.join(data[1:])
    l = s1.split()
    l1 = l[::2]
    l2 = l[1::2]                    
    l2 = list(map(int,l2))

    if l1 == l2 == []:
        print("* You haven't chosen any workshop.")
        l4 = []
        for i in range(len(l2)):
            l4.append(l2[i])
    else:
        print('-' * 40)
        print("{0:^20}".format("Student_ID"),'|',"{0:^20}".format("Workshops"),sep='')
        print('-' * 40)
        l3 = []
        l4 = []
        for i in range(len(l2)):
            d1 = "{0:^20}".format(l1[i],l2[i])+'|'+"{1:^20}".format(l1[i],l2[i])
            l3.append(d1)
            l4.append(l2[i])
        l5 = sorted(l3)
        for i in l5:
            print(i)


    signup = str(input('Please give the ID of the workshop which you want to sign up(Enter "Q" or "q" to select again): '))
    if signup.lower() == "q":
        return
    else:
        while int(signup) in l4:
            print("You have chosen this workshop.")
            signup = str(input("Please give the number of the workshop which you want to sign up again: "))

        while judgement2(workshop_list1,signup):
            print("There is no remaining in this workshop.")
            signup = str(input("Please give the number of the workshop which you want to sign up: "))

        print("Please check your choice:",end="")
        check = int(input('(Put 1 for "Yes"; Put 2 for "No")\n'))

        while check != 1:
            signup = str(input("Please give the number of the workshop which you want to sign up again: "))
            
            while int(signup) in l4:
                print("You have chosen this workshop.")
                signup = str(input("Please give the number of the workshop which you want to sign up again: "))       

            while (workshop_list1,signup):
                print("There is no remaining in this workshop.")
                signup = str(input("Please give the number of the workshop which you want to sign up: "))

            print("Please check your choice:",end="")
            check = int(input('(Put 1 for "Yes"; Put 2 for "No")\n'))

        with open(data_file,'a') as f:
            f.write('\n')
            f.write(student_user_ID)
            f.write('\t')
            f.write(signup)

        with open(log,'a') as f:
            f.write('\n')
            f.write(student_user_ID)
            f.write('\t')
            f.write(signup)

        with open(workshop_csv, 'w', encoding='utf-8', newline='') as file:
            for b in workshop_list1:
                if signup != b[1]:
                    c = [str(int(b[0])), str(b[1]), str(b[2]),str(b[3]),str(b[4][:-1])]
                    csv_writer = csv.writer(file)
                    csv_writer.writerow(c)
                else:
                    c = [str(int(b[0])), str(b[1]), str(b[2]),str(b[3]),str(int(b[4][:-1])-1)]
                    csv_writer = csv.writer(file)
                    csv_writer.writerow(c)

        print("Enroll the workshop successfully --- Student Account", str(student_user_ID).upper())


def S2(student_user_ID):

    data_file = f'./student/{student_user_ID}.txt'
    log = f'./admin/document/log.txt'
    workshop_csv = f'./admin/document/workshop.csv'
    workshop_list1 = []

    with open(data_file,'r') as f:
        print()
        print('Your choice:')
        data = f.readlines()              
        f.close

    s1 = ''.join(data[1:])
    l = s1.split()
    l1 = l[::2]
    l2 = l[1::2]                    
    l2 = list(map(int, l2))

    if l1 == l2 == []:
        print("* You haven't chosen any workshop. Please choose workshop first.")
        return
    else:
        print('-' * 40)
        print("{0:^20}".format("Student_ID"),'|',"{0:^20}".format("Workshops"),sep='')
        print('-' * 40)
        l3 = []
        l4 = []
        for i in range(len(l2)):
            d1 = "{0:^20}".format(l1[i],l2[i])+'|'+"{1:^20}".format(l1[i],l2[i])
            l3.append(d1)
            l4.append(l2[i])    
        l5 = sorted(l3)
        for i in l5:
            print(i)

    cancel = str(input('Please give the number of the workshop which you want to cancel(Enter "Q" or "q" to select again): '))
    if cancel.lower() == "q":
        return
    else:
        while int(cancel) not in l4:
            print("You haven't chosen this workshop.")
            cancel = str(input("Please give the number of the workshop which you want to cancel: "))
        
        print('Please check your choice:',end='')
        check = int(input('(Put 1 for "Yes"; Put 2 for "No")\n'))
                    
        while check != 1:
            cancel = str(input("Please give the number of the workshop which you want to cancel: "))
            
            while int(cancel) not in l4:
                print("You haven't chosen this workshop.")
                cancel = str(input("Please give the number of the workshop which you want to cancel: "))
            
            print('Please check your choice:',end='')
            check = int(input('(Put 1 for "Yes"; Put 2 for "No")\n'))
                    
        with open(data_file,'r') as f:
            lines = f.readlines()
                    
        with open(data_file, "w") as f:
            for line in lines:
                if line.strip("\n") != student_user_ID+'\t'+cancel:
                    f.write(line)
                    
        with open(log,'r') as f:
            lines = f.readlines()
                    
        with open(log, "w") as f:
            for line in lines:
                if line.strip("\n") != student_user_ID+'\t'+cancel:
                    f.write(line)

        with open(workshop_csv, 'r', encoding='utf-8') as file:
            lines = file.readlines()
            file.close()

        for line in lines:
            workshop_content = line.split(',')
            workshop_list1.append(workshop_content)

        with open(workshop_csv, 'w', encoding='utf-8', newline='') as file:
            for b in workshop_list1:
                if cancel != b[1]:
                    c = [str(int(b[0])), str(b[1]), str(b[2]),str(b[3]),str(b[4][:-1])]
                    csv_writer = csv.writer(file)
                    csv_writer.writerow(c)
                else:
                    c = [str(int(b[0])), str(b[1]), str(b[2]),str(b[3]),str(int(b[4][:-1])+1)]
                    csv_writer = csv.writer(file)
                    csv_writer.writerow(c)

        print("Cancel the workshop successfully --- Student Account", str(student_user_ID).upper())


def S3(student_user_ID):
    
    data_file = f'./student/{student_user_ID}.txt'
    workshop_csv = f'./admin/document/workshop.csv'
    workshop_list1 = []
    
    with open(workshop_csv, 'r', encoding='utf-8') as file:
        lines = file.readlines()
        file.close()

    for line in lines:
        workshop_content = line.split(',')
        workshop_list1.append(workshop_content)
    
    print()
    print("This is the list of workshops:")
    field_names = ['Index', 'Workshop_ID', 'Workshop_Name', 'Total','Remain']
    print('-' * 104)
    print('{0:^20}'.format(field_names[0]), '|', '{0:^20}'.format(field_names[1]),'|','{0:^20}'.format(field_names[2]),'|','{0:^20}'.format(field_names[3]),'|','{0:^20}'.format(field_names[4]),sep='')
    print('-' * 104)
    for line in lines:
        workshop_content = line.split(',')
        print('{0:^20}'.format(workshop_content[0]), '|', '{0:^20}'.format(workshop_content[1]),'|','{0:^20}'.format(workshop_content[2]), '|','{0:^20}'.format(workshop_content[3]), '|','{0:^20}'.format(workshop_content[4][0:-1]), sep='')

    with open(data_file,'r') as f:
        print()
        print('Your choice:')
        data = f.readlines()              
        f.close

    s1 = ''.join(data[1:])
    l = s1.split()
    l1 = l[::2]
    l2 = l[1::2]                    
    l2 = list(map(int, l2))

    if l1 == l2 == []:
        print("* You haven't chosen any workshop. Please choose workshop first.")
        return
    else:
        print('-' * 40)
        print("{0:^20}".format("Student_ID"),'|',"{0:^20}".format("Workshops"),sep='')
        print('-' * 40)
        l3 = []
        l4 = []
        for i in range(len(l2)):
            d1 = "{0:^20}".format(l1[i],l2[i])+'|'+"{1:^20}".format(l1[i],l2[i])
            l3.append(d1)
            l4.append(l2[i])
        l5 = sorted(l3)
        for i in l5:
            print(i)
    
    print()
    ask1 = int(input("Enter 1 for Read Workshop Announcement; Enter 2 for Skip: "))
    if ask1 == 1:
        for b in sorted(l4):
            print()
            ws_announcement_read(b)
    elif ask1 == 2:
        pass
    
    print()
    ask2 = int(input("Enter 1 for Read Workshop Description; Enter 2 for Skip: "))
    if ask2 == 1:
        student_check_ws1()
        z = str(input('Enter "Q" or "q" to exit: '))
    elif ask2 == 2:
        pass


def S4(student_user_ID):

    data_file = f'./student/{student_user_ID}.txt'
    
    with open(data_file,'r') as f:
        contents = f.readline().splitlines()
        password0 = contents[0]
        f.close()

    password = str(input("Please enter the current password: "))

    while password != password0:
        print("The current password is incorrect, please re-enter.")
        password = str(input("Please enter the current password: "))
    
    if password == password0:
        print("The current password is correct.")

    password1 = str(input("Enter new Student_Password: "))
    password2 = str(input("Enter new Student_Password again: "))

    while password1 != password2:
        print("The password entered twice is different, please re-enter.")
        password1 = str(input("Enter new Student_Password: "))
        password2 = str(input("Enter new Student_Password again: "))

    while password0.strip("\n") == password1:
        print("Your password is the same, do you want to continue. Please check your choice:",end='')
        check = int(input('(Put 1 for "Yes"; Put 2 for "No")\n'))

        if check == 1:
            break                     
        if check == 2:
            password1 = str(input("Enter new Student_Password: "))
            password2 = str(input("Enter new Student_Password again: "))

            while password1 != password2:
                print("The password entered twice is different, please re-enter.")
                password1 = str(input("Enter new Student_Password: "))
                password2 = str(input("Enter new Student_Password again: "))

    with open(data_file, "r") as f:
        lines = f.readlines()

    with open(data_file, "w") as f:
        f.write(password1)
        f.write('\n')
        for line in lines[1:]:
            f.write(line)

    print("Password modified successfully --- Student Account", student_user_ID)

