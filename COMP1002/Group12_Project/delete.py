def delete_workshop():
    
    workshop_list1 = []
    log = f'./admin/document/log.txt'
    workshop_csv = f'./admin/document/workshop.csv'    
    
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
    for line in lines:
        workshop_content = line.split(',')
        workshop_list1.append(workshop_content)
    
    workshop_ID = str(input('Please enter the workshop ID(Enter "Q" or "q" to select again): '))
    if workshop_ID.lower() == "q":
        return
    else:
        while judgement5(workshop_list1,workshop_ID):
            print("The list does not have this workshop.")
            workshop_ID = str(input("Please enter the workshop name: "))
        
        for b in workshop_list1:
            if workshop_ID == b[1]:
                a = int(b[0])

        with open(workshop_csv, 'w', encoding='utf-8', newline='') as file:

            for b in workshop_list1:
                if workshop_ID != b[1]:
                    if int(b[0]) > a:
                        c = [str(int(b[0]) - 1), str(b[1]), str(b[2]),str(b[3]), str(b[4][:-1])]
                        csv_writer = csv.writer(file)
                        csv_writer.writerow(c)
                    else:
                        c = [str(int(b[0])), str(b[1]), str(b[2]),str(b[3]),str(b[4][:-1])]
                        csv_writer = csv.writer(file)
                        csv_writer.writerow(c)

        ws_desc_file = f'./admin/document/workshop/{workshop_ID}.txt'
        os.remove(ws_desc_file)
        
        with open(workshop_csv, 'r', encoding='utf-8') as file:
            lines = file.readlines()
            file.close()
        print('-' * 104)
        print(
        '{0:^20}'.format(field_names[0]), '|', '{0:^20}'.format(field_names[1]), '|', '{0:^20}'.format(field_names[2]), '|',
        '{0:^20}'.format(field_names[3]),'{0:^20}'.format(field_names[4]),sep='')
        print('-' * 104)
        for line in lines:
            workshop_content = line.split(',')
            print('{0:^20}'.format(workshop_content[0]), '|', '{0:^20}'.format(workshop_content[1]), '|',
                '{0:^20}'.format(workshop_content[2]), '|','{0:^20}'.format(workshop_content[3]), '|','{0:^20}'.format(workshop_content[4][0:-1]), sep='')


        with open(log,'r') as f:
            data = f.readlines()
            f.close   

        s1 = ''.join(data)
        l = s1.split()
        l1 = l[::2]
        l2 = l[1::2]
        l2 = list(map(int, l2))
        if l1 == l2 == []:
            pass
        else:
            l4 = []
            for i in range(len(l2)):
                if int(workshop_ID) == l2[i]:
                    l4.append(l1[i])
        
            if l4 == []:
                return
            else:
                for c in l4:
                    with open(log,'r') as f:
                        lines = f.readlines()

                    with open(log, 'w') as f:
                        for line in lines:
                            if line.strip("\n") != c+'\t'+workshop_ID:
                                f.write(line)
                    
                    username = f'./student/{c}.txt'

                    with open(username,'r') as f:
                        lines = f.readlines()

                    with open(username, 'w') as f:
                        for line in lines:
                            if line.strip("\n") != c+'\t'+workshop_ID:
                                f.write(line)

        z = str(input('Enter "Q" or "q" to quit:')) 
