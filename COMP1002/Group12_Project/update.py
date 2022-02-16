import csv

def update_workshop():
    z = True
    while z:
        workshop_list1 = []
        workshop_csv = f'./admin/document/workshop.csv'    

        with open(workshop_csv, 'r', encoding='utf-8') as file:
            lines = file.readlines()
            file.close()
        
        if lines == []:
            print("* No workshop yet. Please add workshop first.")
            return
        else:
            field_names = ['Index', 'Workshop_ID', 'Workshop_Name', 'Total','Remain']
            print()
            print("The list of workshop is:")
            print('-' * 104)
            print(
            '{0:^20}'.format(field_names[0]), '|', '{0:^20}'.format(field_names[1]), '|', '{0:^20}'.format(field_names[2]), '|',
            '{0:^20}'.format(field_names[3]),'|','{0:^20}'.format(field_names[4]),sep='')
            print('-' * 104)
            for line in lines:
                workshop_content = line.split(',')
                print('{0:^20}'.format(workshop_content[0]), '|', '{0:^20}'.format(workshop_content[1]), '|',
                    '{0:^20}'.format(workshop_content[2]), '|','{0:^20}'.format(workshop_content[3]), '|','{0:^20}'.format(workshop_content[4][0:-1]), sep='')
        
        for line in lines:
            workshop_content = line.split(',')
            workshop_list1.append(workshop_content)
        
        workshop_ID = str(input('Please enter current workshop ID(Enter "Q" or "q" to select again): '))
        if workshop_ID.lower() == 'q':
            return
        workshop_name = str(input("Please enter current workshop name: "))
        
        while judgement3(workshop_list1,workshop_ID,workshop_name):
            print("The entered workshop_ID does not match the workshop_name.")
            workshop_ID = str(input("Please enter current workshop ID: "))
            workshop_name = str(input("Please enter current workshop name: "))    

        workshop_des = f'./admin/document/workshop/{workshop_ID}.txt'

        D1 = {1:"Change 'Workshop_Name'",2:"Change 'Total'",3:"Change Description",4:"Exit"}
        print("-"*35)
        print("{0:^10}".format("Number"),'|',"{0:^20}".format("Function"))
        print("-"*35)
        for k,v in D1.items():
            print("{0:^10}".format(k),'|',"{0:}".format(v))
        n = int(input('Enter the number:\n'))
        if n == 1:
            workshop_newName = str(input("Please enter a new workshop name: "))
                
            while not judgement1(workshop_list1,workshop_newName):
                print("The list have this workshop.")
                workshop_newName = str(input("Please enter a new workshop name: "))

            with open(workshop_csv, 'w', encoding='utf-8', newline='') as file:

                for b in workshop_list1:
                    if workshop_name == b[2] and str(workshop_ID) == b[1]:
                        c = [str(int(b[0])), str(b[1]), workshop_newName,str(b[3]),str(b[4][:-1])]     
                        csv_writer = csv.writer(file)
                        csv_writer.writerow(c)
                    else:
                        c = [str(int(b[0])), str(b[1]), str(b[2]),str(b[3]),str(b[4][:-1])]     
                        csv_writer = csv.writer(file)
                        csv_writer.writerow(c)

                file.close()
            print('The workshop with ID',workshop_ID,'has successfully changed its name.')
            
            with open(workshop_csv, 'r', encoding='utf-8') as file:
                lines = file.readlines()
                file.close()
            print('-' * 104)
            print(
            '{0:^20}'.format(field_names[0]), '|', '{0:^20}'.format(field_names[1]), '|', '{0:^20}'.format(field_names[2]), '|',
            '{0:^20}'.format(field_names[3]),'|','{0:^20}'.format(field_names[4]),sep='')
            print('-' * 104)
            for line in lines:
                workshop_content = line.split(',')
                print('{0:^20}'.format(workshop_content[0]), '|', '{0:^20}'.format(workshop_content[1]), '|',
                    '{0:^20}'.format(workshop_content[2]), '|','{0:^20}'.format(workshop_content[3]), '|','{0:^20}'.format(workshop_content[4][0:-1]), sep='')

            z = True

        elif n == 2:
            workshop_total = int(input('Please enter a new total number of students accommodated: '))

            with open(workshop_csv, 'w', encoding='utf-8', newline='') as file:

                for b in workshop_list1:
                    if workshop_name == b[2] and str(workshop_ID) == b[1]:
                        c = [str(int(b[0])), str(b[1]),str(b[2]),str(workshop_total),str(workshop_total-(int(b[3])-int(b[4][:-1])))]     
                        csv_writer = csv.writer(file)
                        csv_writer.writerow(c)
                    else:
                        c = [str(int(b[0])), str(b[1]), str(b[2]),str(b[3]),str(b[4][:-1])]     
                        csv_writer = csv.writer(file)
                        csv_writer.writerow(c)
                
                file.close()
            print('The workshop with ID',workshop_ID,'has successfully changed its total.')
            
            with open(workshop_csv, 'r', encoding='utf-8') as file:
                lines = file.readlines()
                file.close()
            print('-' * 104)
            print(
            '{0:^20}'.format(field_names[0]), '|', '{0:^20}'.format(field_names[1]), '|', '{0:^20}'.format(field_names[2]), '|',
            '{0:^20}'.format(field_names[3]),'|','{0:^20}'.format(field_names[4]),sep='')
            print('-' * 104)
            for line in lines:
                workshop_content = line.split(',')
                print('{0:^20}'.format(workshop_content[0]), '|', '{0:^20}'.format(workshop_content[1]), '|',
                    '{0:^20}'.format(workshop_content[2]), '|','{0:^20}'.format(workshop_content[3]), '|','{0:^20}'.format(workshop_content[4][0:-1]), sep='')

            z = True
        
        elif n == 3:
            with open(workshop_des,'r') as f:
                lines = f.readlines()

            desc = str(input("Please enter new Description: "))
            lines[0] = desc+'\n'

            with open(workshop_des,'w') as f:
                for line in lines:
                    f.write(line)
                f.close()

            z = True
        
        elif n == 4:
            z = False
