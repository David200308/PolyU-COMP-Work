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
