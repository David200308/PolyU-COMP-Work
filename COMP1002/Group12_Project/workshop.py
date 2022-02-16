import csv

def retrieve_workshop():
    
    workshop_csv = f'./admin/document/workshop.csv'    
    workshop_list1 = []
    with open(workshop_csv, 'r', encoding='utf-8') as file:
        lines = file.readlines()
        file.close()
    if lines == []:
        print("* No workshop yet. Please add workshop first.")
        return
    else:
        field_names = ['Index', 'Workshop_ID', 'Workshop_Name', 'Total','Remain']
        print('-' * 104)
        print(
        '{0:^20}'.format(field_names[0]), '|', '{0:^20}'.format(field_names[1]), '|', '{0:^20}'.format(field_names[2]), '|',
        '{0:^20}'.format(field_names[3]),'|','{0:^20}'.format(field_names[4]),sep='')
        print('-' * 104)
        for line in lines:
            workshop_content = line.split(',')
            print('{0:^20}'.format(workshop_content[0]), '|', '{0:^20}'.format(workshop_content[1]), '|',
                '{0:^20}'.format(workshop_content[2]), '|','{0:^20}'.format(workshop_content[3]), '|','{0:^20}'.format(workshop_content[4][0:-1]), sep='')

    workshop = str(input("Please enter the name or ID of the workshop you want to find: "))

    for line in lines:
        workshop_content = line.split(',')
        workshop_list1.append(workshop_content)

    while judgement4(workshop_list1,workshop):
        print("The list does not have this workshop.")
        workshop = str(input("Please enter the name or ID of the workshop you want to find: "))

    find_name = ["Workshop_ID","Workshop_Name","Total","Remain"]
    print("This is the information for this course:")
    print('-' * 84)
    print('{0:^20}'.format(find_name[0]), '|', '{0:^20}'.format(find_name[1]), '|',
    '{0:^20}'.format(find_name[2]),'|','{0:^20}'.format(find_name[3]),sep='')
    print('-' * 84)
    for b in workshop_list1:
        if workshop == b[1] or workshop == b[2]:
            print('{0:^20}'.format(b[1]), '|', '{0:^20}'.format(b[2]), '|','{0:^20}'.format(b[3]),'|','{0:^20}'.format(b[4][0:-1]), sep='')
            workshop_ID = b[1]

    print()
    ask = int(input("Enter 1 for Read Workshop Description; Enter 2 for quit: "))
    if ask == 1:
        student_check_ws2(workshop_ID)
        z = str(input('Enter "Q" or "q" to quit:'))
        print()
    elif ask == 2:
        return


def storage_workshop():
    
    workshop_csv = f'./admin/document/workshop.csv'        
    workshop_list = []
    workshop_content = []
    print("Please do not enter more than 20 letters:")
    with open (workshop_csv,'r',encoding='utf-8') as file:
        lines = file.readlines()
    if lines == []:
        print("* No workshop yet. Please add workshop first.")
    else:
        field_names = ['Index','Workshop_ID','Workshop_Name','Total','Remain']

        print('-'*104)
        print('{0:^20}'.format(field_names[0]),'|','{0:^20}'.format(field_names[1]),'|','{0:^20}'.format(field_names[2]),'|','{0:^20}'.format(field_names[3]),'|','{0:^20}'.format(field_names[4]),sep='')
        print('-'*104)
        for line in lines:
            workshop_content = line.split(',')
            print('{0:^20}'.format(workshop_content[0]),'|','{0:^20}'.format(workshop_content[1]),'|','{0:^20}'.format(workshop_content[2]),'|','{0:^20}'.format(workshop_content[3]),'|','{0:^20}'.format(workshop_content[4][0:-1]),sep='')

    judge = False
    while not judge:
        with open (workshop_csv,'r',encoding='utf-8') as file:
            lines = file.readlines()
            file.close()

        length = len(lines)
        workshop_ID = str(input('Please enter the ID of the workshop: '))
        workshop_name = str(input("Please enter the workshop name: "))

        for line in lines:
            workshop_content = line.split(',')
            workshop_list.append(workshop_content)

        while not judgement6(workshop_list,workshop_ID,workshop_name):
            print("There is already a workshop with the same ID or name, please change the ID or name: ")
            workshop_ID = str(input("Please enter the ID of the workshop: "))
            workshop_name = str(input("Please enter the workshop name: "))

        remain = total = int(input("Please enter the total number of students: "))
        remind = int(input("Enter 1 for Continue, Enter 2 for Quit: "))

        if remind == 1:
            Index = length + 1
            data = [str(Index),str(workshop_ID),workshop_name,str(total),str(remain)]
            with open(workshop_csv,'a',encoding='utf-8',newline='') as file:
                csv_writer = csv.writer(file)
                csv_writer.writerow(data) 
            file.close()

            ws_file = f'./admin/document/workshop/{workshop_ID}.txt'
            with open(ws_file,'w') as f:
                f.write('\n')
                f.close()

        elif remind == 2:
            judge = True
            Index = length + 1
            data = [str(Index),str(workshop_ID),workshop_name,str(total),str(remain)]
            with open(workshop_csv,'a',encoding='utf-8',newline='') as file:
                csv_writer = csv.writer(file)
                csv_writer.writerow(data) 
            file.close()

            ws_file = f'./admin/document/workshop/{workshop_ID}.txt'
            with open(ws_file,'w') as f:
                f.write('\n')
                f.close()

    field_names = ['Index','Workshop_ID','Workshop_Name','Total','Remain']

    with open (workshop_csv,'r',encoding='utf-8') as file:
        lines = file.readlines()
        file.close()

    for line in lines:
        workshop_content = line.split(',')
        workshop_list.append(workshop_content)

    print('-'*104)
    print('{0:^20}'.format(field_names[0]),'|','{0:^20}'.format(field_names[1]),'|','{0:^20}'.format(field_names[2]),'|','{0:^20}'.format(field_names[3]),'|','{0:^20}'.format(field_names[4]),sep='')
    print('-'*104)
    for line in lines:
        workshop_content = line.split(',')
        print('{0:^20}'.format(workshop_content[0]),'|','{0:^20}'.format(workshop_content[1]),'|','{0:^20}'.format(workshop_content[2]),'|','{0:^20}'.format(workshop_content[3]),'|','{0:^20}'.format(workshop_content[4][0:-1]),sep='')


    z = str(input('Enter "Q" or "q" to exit: '))


def ws_description():

    workshop_csv = f"./admin/document/workshop.csv"
    workshop_list1 = []

    with open (workshop_csv,'r',encoding='utf-8') as file:
        lines = file.readlines()

    for line in lines:
        workshop_content = line.split(',')
        workshop_list1.append(workshop_content)

    if lines == []:
        print("* No workshop yet. Please add workshop first.")
        return
    else:
        field_names = ['Workshop_ID','Workshop_Name']

        print('-'*45)
        print('{0:^20}'.format(field_names[0]),'|','{0:^25}'.format(field_names[1]),sep='')
        print('-'*45)
        for line in lines:
            workshop_content = line.split(',')
            print('{0:^20}'.format(workshop_content[1]),'|','{0:^25}'.format(workshop_content[2]),sep='')

        i_ID = str(input('Enter the Workshop ID for Description need(Enter "Q" or "q" to select again): '))
        
        if i_ID.lower() == 'q':
            return

        while judgement5(workshop_list1,i_ID):
            i_ID = str(input('Enter the Workshop ID for Description need(Enter "Q" or "q" to select again): '))
            if i_ID.lower() == 'q':
                return

        desc = str(input("Please enter the description of the workshop with ID "+i_ID+": "))    
        desc_file = f'./admin/document/workshop/{i_ID}.txt'
        with open(desc_file,'r') as f:
            lines = f.readlines()

        lines[0] = desc + '\n'

        with open(desc_file,'w') as f:
            for line in lines:
                f.write(line)
            f.close()
