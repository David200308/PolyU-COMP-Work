def feedback_append():

    workshop_csv = f'./admin/document/workshop.csv'    
    workshop_list1 = []
    workshop_list2 = []
    
    with open(workshop_csv, 'r', encoding='utf-8') as file:
        lines = file.readlines()
        file.close()
    
    for line in lines:
        workshop_content = line.split(',')
        workshop_list1.append(workshop_content)

    for b in workshop_list1:
        workshop_list2.append(b[1])
    
    if lines == []:
        print("* No workshop yet. Please add workshop first.")
        return
    else:
        field_names = ['Workshop_ID', 'Workshop_Name']
        print('-' * 41)
        print('{0:^20}'.format(field_names[0]), '|', '{0:^20}'.format(field_names[1]),sep='')
        print('-' * 41)
        for line in lines:
            workshop_content = line.split(',')
            print('{0:^20}'.format(workshop_content[1]), '|',
                '{0:^20}'.format(workshop_content[2]),sep='')
    
        workshop_ID = str(input('Please enter the workshop ID which you want to score(Enter "Q" or "q" to select again): '))
        if workshop_ID.lower() == 'q':
            return
        else:
            feedback = f'./admin/document/feedback.txt'
            num = str(input('Please enter the score(1-5): '))
            
            with open(feedback,'a') as f:
                f.write(workshop_ID)
                f.write('\t')
                f.write(str(num))
                f.write('\n')
                f.close()
            
            print("Successfully scored the workshop with ID ---",workshop_ID)

def feedback_read():
    
    feedback = f'./admin/document/feedback.txt'
    
    with open(feedback,'r') as f:    
        data = f.readlines()            
        f.close   

    s1 = ''.join(data)
    l = s1.split()
    l1 = l[::2]
    l2 = l[1::2]                    
    l2 = list(map(int, l2))
    if l1 == l2 == []:
        print("* No students have rated the workshop yet.")
        return
    else:
        s = list()
        d = dict()
        for c in l1:
            s.append(c)

        l5 = []
        for e in s:
            if e not in l5:
                l5.append(e)
            else:
                continue
        
        
        for id in range(len(l5)):
            l[id] = []
            for i in range(len(l2)):
                d1 = {l1[i]:l2[i]}

                for key,val in d1.items():
                    if key == l5[id]:
                        l[id] += [val]
        
            total = 0
            for a in l[id]:
                total += a

            n = len(l[id])
            avg = total/n
            d.update({l5[id]:avg})

        print('-' * 40)
        print('{0:^20}'.format("Workshop_ID"),'|','{0:^15}'.format("Average"))
        print('-' * 40)
        for k,v in d.items():
            print('{0:^20}'.format(k),'|','{0:^15}'.format('{0:.1f}'.format(v)))

        workshop_ID = str(input('Please enter the workshop ID which you want to read(Enter "Q" or "q" to select again): '))
        if workshop_ID.lower() == 'q':
            return
        
        while workshop_ID not in l5:
            print("This workshop has not been rated yet.")
            workshop_ID = str(input('Please enter the workshop ID which you want to read(Enter "Q" or "q" to select again): '))

        l4 = []
        for i in range(len(l2)):
            d1 = {l1[i]:l2[i]}

            for key,val in d1.items():
                if key == workshop_ID:
                    l4 += [val]
        total = 0
        for a in l4:
            total += a
        
        n = len(l4)
        avg = total/n
        print("The average score of the workshop with ID ---",workshop_ID ,"is","{0:.1f}".format(avg))
        z = str(input('Enter "Q" or "q" to exit: '))       


