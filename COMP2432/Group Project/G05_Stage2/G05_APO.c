#include <stdio.h>
#include <math.h>
#include <stdlib.h>
#include <time.h>
#include <string.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <stdbool.h>
#include<ctype.h>

#define NAME_SIZE 20
#define COLS 5
#define TIMESLOTS_PER_DAY 5
#define BUF_SIZE 10000
#define MAX_TIME_SLOTS 155
#define MAX_CHAR_SLOTNO 4
#define MAX_MSG_SIZE 2000

// The Structure of Appointment:
//
// sentence --> the user input
// type --> privateTime, groupStudy, etc.
// startTime --> the event start time (ex. 1400 -> 14:00)
// duration --> the time range of that event (ex. 2.0 -> 2 hours)
// date --> the date of the event (ex. 20230501)
// host_name --> the people who host this event
// all_name --> the people who attent this event
// all_num --> number of people
// able --> the status of the event is scheduled or not
// id --> the event id
// isRescheduled --> is the status of the event is rescheduled or not
// newStartTime --> the rescheduled start time
// newDate --> the rescheduled date
struct Appointment {
    char sentence[80];
    char type[20];
    int startTime;
    float duration;
    char date[9];
    char host_name[NAME_SIZE];
    char all_name[10][NAME_SIZE];
    int all_num;
    bool able;
    int id;
    //rescheduling
    int isRescheduled;
    int newStartTime;
    char newDate[9];

    struct Appointment* next;
};


// the linkedlist insert to the last
void insertlast(struct Appointment *newnode,struct Appointment **head){
    struct Appointment *first=*head;
    struct Appointment *second=first->next;
    while(second!=NULL){
        first=second;
        second=second->next;
    }
    first->next=newnode;
    newnode->next=NULL;
}

// create the linkedlist
struct Appointment* creation(char sentence[]){
    char temp_sen[80];
    struct Appointment *temp=(struct Appointment*)malloc(sizeof(struct Appointment));
    temp->able = true;
    strcpy(temp->sentence, sentence);
    strcpy(temp_sen, sentence);
    char* token = strtok (temp_sen, " ");
    int count = 0;
    while(token != NULL)
    {
        switch(count)
        {
            case 0: strcpy(temp->type, token); break;
            case 1: strcpy(temp->host_name, token); strcpy(temp->all_name[0], token); break;
            case 2: strcpy(temp->date, token); break;
            case 3: temp->startTime = atoi(token); break;
            case 4: temp->duration = atof(token); break;
            default: strcpy(temp->all_name[count - 4], token); break;
        }
        count++;
        token = strtok (NULL, " ");
    }
    temp->all_num = count - 4;
    temp->next = NULL;
    temp->id=-1;
    return temp;
}

// give the Appoinment Priority for schedule the events
void assingAppointmentPriority(int timeTable[][TIMESLOTS_PER_DAY], int priorityTable[][TIMESLOTS_PER_DAY], int taskID, int date, int startTime, float duration, int priority){
    int dayPos = dateToIndex(date);
    int startHours = startTime / 100;
    int startMinutes = startTime % 100;
    int durationHrs = (int) duration;
    int durationMinutes = (int) (( duration - durationHrs) * 60);
    int endHours = startHours + durationHrs;
    int endMinutes = startMinutes + durationMinutes;
    if(endMinutes >= 60){
        endHours += endMinutes / 60;
        endMinutes = endMinutes % 60;
    }
    int endTime = (endHours*100) + endMinutes;

    int i;
    for(i=startTimeToIndex(startTime); i<=endTimeToIndex(endTime); i++){
        timeTable[dayPos][i] = taskID;
        priorityTable[dayPos][i] = priority;
    }
}

int durationIndex(int time, float duration){
    int startMin = time % 100;
    int hour = (int) duration;
    int min = ((duration-hour) * 60);
    int res = hour;

    if(startMin + min > 60){
        res++;
    }
    return res;
}

// time to index
int timeToIndex(int time){
    if(time >= 1800 && time < 1900){
        return 0;
    }
    if(time >= 1900 && time < 2000){
        return 1;
    }
    if(time >= 2000 && time < 2100){
        return 2;
    }
    if(time >= 2100 && time < 2200){
        return 3;
    }
    if(time >= 2200 && time < 2300){
        return 4;
    }
}

// check which type of command user input
int isAppointmentCommand(char* command){
    int res = 0;
    if( strcmp("privateTime", command) == 0) {
        res = 1;
    }
    else if(strcmp("projectMeeting", command) == 0){
        res = 1;
    }
    else if(strcmp("groupStudy", command) == 0){
        res = 1;
    }
    else if(strcmp("gathering", command) == 0){
        res = 1;
    }

    return res;
}


/*-----------Check Availability----------------*/

// Print 2D array
// void print2Darray(int timetable[][COLS], int days){
//     int i, j;

//     for (i = 0; i < days; i++) {
//         for (j = 0; j < COLS; j++) {
//             printf("%d ", timetable[i][j]);
//         }
//         printf("\n");
//     }
// }

// Check the availability of child
int checkAv(char *days, int startTime, float duration, int timetable[][COLS]){
    int day=atoi(days)%100;
    int length = durationIndex(startTime, duration);
    int time = timeToIndex(startTime);
    int i=0;

    do{
        if(timetable[day-1][time] != -1){
            return 0;
        }
        time++;
        i++;
    }while(i<=length);

    return 1;
}

// Check the availability of child with priority
int checkAvP(char *days, int startTime, float duration, int timetable[][COLS], int priority){
    int day=atoi(days)%100;
    int length = durationIndex(startTime, duration);
    int time = timeToIndex(startTime);
    int i=0;

    do{
        if(timetable[day-1][time] > priority){
            return 0;
        }
        time++;
        i++;
    }while(i<=length);

    return 1;
}

// Calculate the total days 20230415 - 20230430
int daysCal(char *startDate, char *endDate){
    int start = atoi(startDate);
    int end = atoi(endDate);
    int days = end-start+1;
    return days;
}
/*---------------------------------------------*/

//--------------------------ASSIGN & REMOVE FUNCTIONS---------------------------------
int dateToIndex(int date){
    return ( date % 100) -1;
}

int startTimeToIndex(int time){
    /*
        This function returns the index number of the slot that a given time lies in timeTable.
        INPUT:
            time: a numerical representaion of time. e.g. 1800 => 18:00
        OUTPUT:
            Returns numerical index of the time slot it lies in. e.g. 1845 lies in 18:00 - 19:00 => 0(index)
    */
     if(time >= 1800 && time < 1900){
        return 0;
    }
    if(time >= 1900 && time < 2000){
        return 1;
    }
    if(time >= 2000 && time < 2100){
        return 2;
    }
    if(time >= 2100 && time < 2200){
        return 3;
    }
    if(time >= 2200 && time < 2300){
        return 4;
    }

}

int endTimeToIndex(int time){
    /*
        This function returns the index number of the slot that a given time lies in timeTable.
        INPUT:
            time: a numerical representaion of time. e.g. 1800 => 18:00
        OUTPUT:
            Returns numerical index of the time slot it lies in. e.g. 1845 lies in 18:00 - 19:00 => 0(index)
    */
     if(time > 1800 && time <= 1900){
        return 0;
    }
    if(time > 1900 && time <= 2000){
        return 1;
    }
    if(time > 2000 && time <= 2100){
        return 2;
    }
    if(time > 2100 && time <= 2200){
        return 3;
    }
    if(time > 2200 && time <= 2300){
        return 4;
    }
}

void assignFCFS(int timeTable[][TIMESLOTS_PER_DAY], int taskID, int date, int startTime, float duration){
    /*
        This function fills the timeTable with the ID of given appointment details.
        INPUT:
            timeTable: 2D array of availability
            taskID: an integer identifier of a given task.
            date: a numeric notation of date of the appointment. e.g. 20230421 => April 21, 2023
            startTime: a numeric notation of the starting time of the appointment. e.g. 1820 => 18:20
            duration: duration of the appointment. e.g. 4.5 => 4hrs 30 min
        OUTPUT:
            Function will fill the requested timeslots with the ID of the appointment.
    */
    int dayPos = dateToIndex(date);
    int startHours = startTime / 100;
    int startMinutes = startTime % 100;
    int durationHrs = (int) duration;
    int durationMinutes = (int) (( duration - durationHrs) * 60);
    int endHours = startHours + durationHrs;
    int endMinutes = startMinutes + durationMinutes;
    if(endMinutes >= 60){
        endHours += endMinutes / 60;
        endMinutes = endMinutes % 60;
    }
    int endTime = (endHours*100) + endMinutes;

    int i;
    for(i=startTimeToIndex(startTime); i<=endTimeToIndex(endTime); i++){
        timeTable[dayPos][i] = taskID;
    }

    // printf("Time: %d, Duration: %f, End: %d\n", startTime, duration, endTime);

}

// get the piority of events (for events piority scheduling)
int getPiority(char *appointment){
    if(strcmp(appointment, "privateTime")==0){
        return 4;
    }
    else if(strcmp(appointment, "projectMeeting")==0){
        return 3;
    }
    else if(strcmp(appointment, "groupStudy")==0){
        return 2;
    }
    else if(strcmp(appointment, "gathering")==0){
        return 1;
    }
    else return -1;
}

// remove the FCFS appointment from the timetable
void removeAppointmentFSFC(int timeTable[][TIMESLOTS_PER_DAY], int size, int id){
    /*
        This function removes an appointment with a given from the time table.
        It is done by filling the slots which were occupied by the appointment with the -1(tomb value)
        INPUT:
            - timeTable: 2D array representaion of user's timetable.
            - size: number of rows in timetable(days).
            - id: unique identifier of the appointment to be removed.
    */
    int r, c;

    for( r=0; r<size; r++){
        for( c=0; c<TIMESLOTS_PER_DAY; c++){
            if( timeTable[r][c] == id){
                timeTable[r][c] = -1;
            }
        }
    }
}

// remove the appointment from the timetable by priority
int removeAppointmentPriority(int timeTable[][TIMESLOTS_PER_DAY], int priorityTable[][TIMESLOTS_PER_DAY], int size, int id){
    int r, c;
    int is_removed=1;
    for( r=0; r<size; r++){
        for( c=0; c<TIMESLOTS_PER_DAY; c++){
            if( timeTable[r][c] == id){
                is_removed=0;
                timeTable[r][c] = -1;
                priorityTable[r][c] = -1;

            }
        }
    }
    return is_removed;
}

// print the timetable
// void printTimeTable(int timeTable[][TIMESLOTS_PER_DAY], int size){
//     int colSize = 10;
//     printf("%*s", colSize, "Day");
//     printf("%*s", colSize, "18-19");
//     printf("%*s", colSize, "19-20");
//     printf("%*s", colSize, "20-21");
//     printf("%*s", colSize, "21-22");
//     printf("%*s", colSize, "22-23");
//     printf("\n");

//     int r, c;
//     for( r=0; r<size; r++){
//         printf("%*d", colSize, r+1);
//         for( c=0; c<TIMESLOTS_PER_DAY; c++ ){
//             printf("%*d", colSize, timeTable[r][c]);
//         }
//         printf("\n");
//     }

// }
/*-------------------------RESCHEDULING METHODS-----------------------*/
// void printIntArray(int array[], int size){
//     int i;
//     for(i=0; i<size; i++){
//         printf("%d ", array[i]);
//     }
//     printf("\n");
// }

// find the time slot for the time table use rescheduling algorithm
int findRescheduleSlot(int map[][TIMESLOTS_PER_DAY], int days, int target, int n){
    int count=0;
    int isFound = 0;
    int slotNo=-1;
    int r, c;
    for(r=0; (r<days) && (isFound==0); r++){
        for(c=0; (c<TIMESLOTS_PER_DAY) && (isFound==0); c++){
            if( map[r][c] == target){
                count++;
            }
            else{
                count = 0;
            }
            if(count==n){
                isFound = 1;
                slotNo = (r*10) + (c-(n-1));
            }
        }
        count=0;
    }
    return slotNo;
}

// fill the time table with the task ID
void fillTimeTable(int timeTable[][TIMESLOTS_PER_DAY], int days, int taskID, int slotNo, int n){
    int row = getRowFromSlotNo(slotNo);
    int col = getColumnFromSlotNo(slotNo);

    int i=0;

    for(i=0; i<n; i++){
        timeTable[row][col+i] = taskID;
    }
}

// get the row from the slot number
int getRowFromSlotNo(int slotNo){
    return slotNo/10;
}

// get the column from the slot number
int getColumnFromSlotNo(int slotNo){
    return slotNo%10;
}

// fill the availability time slot in time table
void fillAvailability(int map[][TIMESLOTS_PER_DAY], int availabiltArray[], int n){
    int i;
    for( i=0; i<n; i++){
        int slotNo = availabiltArray[i];
        int r = getRowFromSlotNo(slotNo);
        int c = getColumnFromSlotNo(slotNo);
        map[r][c]++;
    }
}

// get the string from the int array
void getStringFromIntArray(int array[], int size, char output[]){
    int i;
    output[0] = 0;

    for( i=0; i<size; i++ ){
        if( i!=0){
            strcat(output, " ");
        }

        sprintf(output,"%s%d", output, array[i]);
    }
}

// get the int array from the string
int getIntArrayFromString(char str[], int output[]){
    int count = 0;
    char temp[MAX_MSG_SIZE];
    strcpy(temp, str);
    char* token;
    token = strtok(temp, " ");
    while( token != NULL){
        output[count++] = atoi(token);
        token = strtok(NULL, " ");
    }
    return count;
}

// get the available slots from the timetable
int getAvailableSlots(int timeTable[][TIMESLOTS_PER_DAY], int size, int output[MAX_TIME_SLOTS]){
    int count = 0;
    int i, j;
    for( i=0; i< size; i++){
        for(j=0; j<TIMESLOTS_PER_DAY; j++){
            if( timeTable[i][j] == -1){
                output[count++] = (i*10) + j;
            }
        }
    }
    return count;
}
/*--------------------------------------------------------------------*/

char* getMeeting(int cal[][COLS],int day_num){
    int i,j;
    char *result=(char*)malloc(sizeof(char)*BUF_SIZE);
    strcpy(result,"");
    for(i=0;i<day_num;i++){
        for(j=0;j<COLS;j++){
            if(j==0){
                if(cal[i][0]!=-1){
                    sprintf(result,"%s %d",result,cal[i][j]);
                }
            }
            else if(cal[i][j]!=cal[i][j-1]&&cal[i][j]!=-1){
                sprintf(result,"%s %d",result,cal[i][j]);
            }
        }
    }
    return result;
}

struct Appointment* FAPO(struct Appointment **head,int m_id){
    struct Appointment *second=*head;
    while(second!=NULL){
        if(second->id==m_id){
            return second;
        }
        second=second->next;
    }
    return NULL;
}
char* removeWhite(char *str){
    while( isspace(*(++str)) ); // Trim leading space
    if( (*str) == '\0' )
    {
            return str;
    }

    int len;

    len = strlen(str);
    len--;

    while ( isspace(*(str +len) ))
    {
            len--;
    }

    *(str + len + 1) = '\0';

    return(str-1);
}
// will be return the string of the appointment for log to file
char* print_Appointment(struct Appointment *node){
    char *result=(char*)malloc(sizeof(char)*1000);
    strcpy(result, "");

    char type[17];
    if(strcmp(node->type, "privateTime") ==0){
        strcpy(type, "Private Time     "); 
    }
    else if(strcmp(node->type, "projectMeeting")==0){
        strcpy(type, "Project Meeting  ");
    }
    else if(strcmp(node->type, "groupStudy")==0){
        strcpy(type, "Group Study      ");
    }
    else if(strcmp(node->type, "gathering")==0){
        strcpy(type, "Gathering        ");
    }
    
    int start_h, start_m, end_h, end_m, dur, carry = 0;
    start_h = (node->startTime) / 100;
    start_m = (node->startTime) - start_h * 100;
    dur = (node->duration) * 60;
    end_m = start_m + dur % 60;
    if(end_m >= 60) { end_m -= 60; carry++; }
    end_h = start_h + dur / 60 + carry;

    // sprintf(result, "%s%c%c%c%c-%c%c-%c%c   %02d:%02d   %02d:%02d   %s", 
    //     result, node->date[0], node->date[1], node->date[2], node->date[3], node->date[4], node->date[5], 
    //     node->date[6], node->date[7], start_h, start_m, end_h, end_m, type);
    
    if( node->isRescheduled){
        sprintf(result, "%s%c%c%c%c-%c%c-%c%c   %02d:%02d   %02d:%02d   %s => %s %d", 
        result, node->date[0], node->date[1], node->date[2], node->date[3], node->date[4], node->date[5], 
        node->date[6], node->date[7], start_h, start_m, end_h, end_m, type, node->newDate, node->newStartTime);
    }
    else{
        sprintf(result, "%s%c%c%c%c-%c%c-%c%c   %02d:%02d   %02d:%02d   %s", 
        result, node->date[0], node->date[1], node->date[2], node->date[3], node->date[4], node->date[5], 
        node->date[6], node->date[7], start_h, start_m, end_h, end_m, type);
    }
    

    int i;
    char name[NAME_SIZE];
    for(i = 1; i < node->all_num; i++)
    {
        strcpy(name, node->all_name[i]);
        name[0] -= ('a' - 'A');
        sprintf(result, "%s %s",result, name);
    }

    sprintf(result, "%s\n",result);
    int len=strlen(result);
    result[len]='\0';
    return result;
}

// for get the index of the time table
int getPreID(char *date, int index, int timetable[][COLS]){
    int day=atoi(date)%100 -1;
    return timetable[day][index];
}

// for get the index of the time
char* generateTBR(char *date, int time, float duration, int timetable[][COLS]){
    int i=0;
    int index = timeToIndex(time);
    int dur=durationIndex(time, duration);
    char *toBeRemove=(char*)malloc(sizeof(char));
    strcpy(toBeRemove,"");
    while(i<dur){
        int cancel_id=getPreID(date, index, timetable);
        if(cancel_id!=-1){
            sprintf(toBeRemove,"%s %d",toBeRemove,cancel_id);
        }
        index++;
        i++;
    }
    return toBeRemove;
}

// for reset the able of the appointment
void clear_list(struct Appointment **head){
    struct Appointment *second=*head;
    while(second!=NULL){
        second->able=true;
        second=second->next;
    }
}

// for reset the timetable
void resetTtb(int timetable[][COLS],int pTimetable[][COLS], int days){
    int counter, j;
    for (counter = 0; counter < days; counter++) {
        for (j = 0; j < COLS; j++) {
            timetable[counter][j] = -1;
            pTimetable[counter][j] = -1;
        }
    }
}

// for calculating the ultilization rate
char* get_ultilization_rate(char name[],int timeTable[][COLS],int days){
    float size=COLS*days;
    int i,j;
    float used_slot=0;
    char *result=(char*)malloc(sizeof(char)*BUF_SIZE);
    for(i=0;i<days;i++){
        for(j=0;j<COLS;j++){
            if(timeTable[i][j]!=-1){
                used_slot++;
            }
        }
    }
    sprintf(result,"%s      - %.1f%%\n",name,used_slot/size*100.000);
    return result;
}

// read the seq we store in the seq file (seq.txt)
int readFileSeq(int algMode) {
    int i = 0;

    FILE *rd = rd;
    rd = fopen("./seq.txt", "r");
    
    int count = 0;
    int seqFCFS = 0; // 0
    int seqP = 0; // 1
    int seqALL = 0; //2

    fscanf(rd, "%d", &i);
    while (!feof(rd)) {        
        if (count == 0) {
            seqFCFS = i;
        }
        if (count == 1) {
            seqP = i;
        }
        if (count == 2) {
            seqALL = i;
        }
        fscanf(rd, "%d", &i);
        count++;
    }

    if (algMode == 0) {
        return seqFCFS;
    }
    if (algMode == 1) {
        return seqP;
    }
    if (algMode == 2) {
        return seqALL;
    }
    
    fclose(rd);

    return -1;
}

// update the seq we store in the seq file (seq.txt)
void updateFileSeq(int algMode) {
    int i = 0;

    FILE *rd = rd;
    FILE *wr = wr;
    rd = fopen("./seq.txt", "r");
    
    int count = 0;
    int seqFCFS = 0; // 0
    int seqP = 0; // 1
    int seqALL = 0; //2
    char buffer[20];
    fscanf(rd, "%d", &i);
    while (!feof(rd)) {        
        if (count == 0) {
            seqFCFS = i;
        }
        if (count == 1) {
            seqP = i;
        }
        if (count == 2) {
            seqALL = i;
        }
        fscanf(rd, "%d", &i);
        count++;
    }

    if (algMode == 0) {
        seqFCFS++;
    }
    if (algMode == 1) {
        seqP++;
    }
    if (algMode == 2) {
        seqALL++;
    }
    wr = fopen("./seq.txt", "w");
    sprintf(buffer, "%d %d %d\n", seqFCFS, seqP, seqALL);
    fputs(buffer, wr);
    fclose(rd);
    fclose(wr);
}
//------------------------------------------------------------------------------------
/* ----------------------FUNCTIONS FOR CHECKING WORKING DAYS--------------------------*/

int isHoliday(int date){
    // printf("&&&&date: %d\n", date);

    int mmdd = date % 10000;
    //mmdd
        switch(mmdd){
        case 102: // new year
        case 123: //lunar new year
        case 405: //Chung Min
        case 407: //good friday
        case 408: //day after good friday
        case 410: //easter monday
        case 501: // Labor day
        case 526: //Buddha bd
        case 622: //Tuen Ng Festival
        case 507: //sunday
        case 514: //sunday
        case 521: //sunday
        case 528: //sunday
            // printf("it is a holoday\n");
            return 1;
        default:
            // printf("it is not a holoday\n");
            return 0;
    }

}



/*-------------------------------------------------------------------------------------*/

int main(int argc, char *argv[]) {
    // start the program
    int i;
    int isRescheduleOn = 0;
    int childNumber = argc-3; 
    float general_id=0.0;
    char pPretext[100];
    if (mkdir("output", 0777) == -1) {}
    sprintf(pPretext, "Parent message: ");
    FILE *create,*command_file;
    create = fopen("./seq.txt", "a+");
    fclose(create);
    command_file=fopen("./ALL_Requests.txt","w");
    fclose(command_file);
    // names list
    char(*name)[NAME_SIZE] = (char(*)[NAME_SIZE])malloc(sizeof(char) * (argc - 3) * NAME_SIZE);
    for(i = 0; i < childNumber; i++){
        strcpy(name[i], argv[i+3]);
    }

    // Dates
    char startDate[9];
    char endDate[9];
    strcpy(startDate, argv[1]);
    strcpy(endDate, argv[2]);

    // calculate days
    int days = daysCal(startDate, endDate);
    
    //The system should return an error message if the number of users is not in the range 3 to 10
    if(childNumber < 3 || childNumber > 10){
        printf("*the number of users is not in the range 3 to 10*\n"); 
        exit(1);
    }

/*--------pipe-------*/
    int p2c[childNumber][2];	// parent to child
    int c2p[childNumber][2];    // child to parent
    char p2cBuf[BUF_SIZE], c2pBuf[BUF_SIZE];
    //pipe error 
    for(i = 0; i < childNumber; i++){
        if (pipe(&p2c[i][0]) < 0) {
            printf("Pipe creation error\n");
            exit(1);
        }
        if (pipe(&c2p[i][0]) < 0) {
            printf("Pipe creation error\n");
            exit(1);
        }
    }
/*-------------------*/

    // printf("parent process %d:\n", getpid());

	for(i = 0; i < childNumber; i++) {
        pid_t childId = fork();
        
        if(childId < 0) {
            printf("*Create child process falied*\n");
            exit(1);
        }
        // child process
		else if(childId == 0) {
            // -------------------[CHILD] CLOSE UNUSED PIPES-------------------
			close(p2c[i][1]);
            close(c2p[i][0]);
            //-----------------------------------------------------------------
            char p2cBuf[BUF_SIZE];
            char c2pBuf[BUF_SIZE];

            // timeTable
            int counter;
            int j;
            char cPretext[100];
            // snprintf(cPretext, 100, "Child %s, message:" , name[i]);
            int (*timetable)[COLS] = malloc(sizeof(int[days][COLS]));
            int (*pTimetable)[COLS] = malloc(sizeof(int[days][COLS]));

            resetTtb( timetable,pTimetable, days);

            // to be remove buffer
            char *reject_list;
            int scheduled_meeting;
            int n;
            struct Appointment *appointment=(struct Appointment*)malloc(sizeof(struct Appointment));
            while((n = read(p2c[i][0], p2cBuf, BUF_SIZE)) > 0){
                p2cBuf[n] = 0;
                bool flag=false;
                // printf("%smessage: %s\n", cPretext, p2cBuf);
                if(strcmp(p2cBuf,"FCFS") == 0 || strcmp(p2cBuf,"PRIORITY")==0){
                    scheduled_meeting=0;
                    resetTtb( timetable,pTimetable, days);
                    // printf("%s fcfs and p block\n", cPretext);
                    if(strcmp(p2cBuf,"PRIORITY")==0){
                        flag=true;
                    }
                    write(c2p[i][1],"done",BUF_SIZE);
                }
                else if( strcmp(p2cBuf,"finish") == 0 ){
                    //reset the algorithm
                    char *child_meeting=getMeeting(timetable,days);
                    strcpy(c2pBuf,child_meeting);
                    write(c2p[i][1], c2pBuf, BUF_SIZE);
                    //the reject list stuffs
                    read(p2c[i][0],p2cBuf,BUF_SIZE);
                    sprintf(c2pBuf,"%s - %d\n",name[i],scheduled_meeting);
                    write(c2p[i][1],c2pBuf,BUF_SIZE);
                    read(p2c[i][0], p2cBuf,BUF_SIZE);
                    strcpy(c2pBuf,get_ultilization_rate(name[i],timetable,days));
                    write(c2p[i][1],c2pBuf,BUF_SIZE);//send the utilization rate
                }
            /*------------------[CHILD-FCFS] RESCHEDULING MODULE-----------------------------------------------------------------------------------*/
                else if( strcmp(p2cBuf,"reschedule") == 0 ){
                //   printf("%s reschuling now\n", cPretext);
                    int availSlots[MAX_TIME_SLOTS];
                    int numberOfAvail = getAvailableSlots(timetable,days,availSlots);
                    char strOut[MAX_CHAR_SLOTNO];
                    getStringFromIntArray(availSlots, numberOfAvail,strOut);
                    // printf("%s available: %s\n", cPretext, strOut);
                    strcpy(c2pBuf, strOut);
                    write(c2p[i][1], c2pBuf, BUF_SIZE);

                    n = read(p2c[i][0], p2cBuf, BUF_SIZE);
                    p2cBuf[n] =0;
                /*----------[CHILD] DECODING RESCHEDULING MESSAGE ---------------------*/
                    if(strcmp(p2cBuf, "NO_SLOTS") !=0){
                        int rescheduleCode = atoi(p2cBuf);
                        int slotNo = rescheduleCode % 100;
                        int taskID = rescheduleCode / 10000;
                        int slotsNeeded = (rescheduleCode / 1000) % 10;
                        // printf("%s slot no:%d needed slots:%d\n", cPretext, slotNo, slotsNeeded);
                        fillTimeTable(timetable, days, taskID, slotNo, slotsNeeded);
                        // printTimeTable(timetable, days);
                    }
                    
                    // printf("Rescheduling protocols done\n");
                    strcpy(c2pBuf, "reschedule response");
                    write(c2p[i][1], c2pBuf, BUF_SIZE);
                }
            /*---------------------------------------------------------------------------------------------------------------------------------*/

                else if(strcmp(p2cBuf,"endProgram") == 0){
                    /*------------------[CHILD] CLOSE USED PIPES------------------------------------*/
                    close(c2p[i][1]);
                    close(p2c[i][0]);
                    /*------------------------------------------------------------------------------*/
                    exit(0);
                }

                else{//when the incoming command is an appointment
                    char temp[80];
                    strcpy(temp,p2cBuf);
                    char *ID=strtok(temp," ");
                    char *information=strtok(NULL,"");
                    appointment=creation(information);
                    appointment->id=atoi(ID);
                    int availability=checkAv(appointment->date,appointment->startTime,appointment->duration,timetable);
                    if(availability==0){
                        write(c2p[i][1], "n", BUF_SIZE);
                    }
                    else{
                        write(c2p[i][1], "y", BUF_SIZE);
                    }
                    n = read(p2c[i][0],p2cBuf,BUF_SIZE);
                    p2cBuf[n] = 0;
                    if(strcmp(p2cBuf,"schedule")==0){
                        /*to schedule the meeting*/
                        scheduled_meeting++;
                        assignFCFS(timetable, appointment->id, atoi(appointment->date), appointment->startTime, appointment->duration);
                        // printTimeTable(timetable, days);
                        write(c2p[i][1],"finish",BUF_SIZE);
                    }
                    else{
                        write(c2p[i][1],"finish", BUF_SIZE);
                    }
                }
                //if it is priority
                while(flag){
                    n = read(p2c[i][0], p2cBuf, BUF_SIZE);
                    // printf("child %s receives: %s\n", name[i], p2cBuf);
                    if(strcmp(p2cBuf,"cancel")==0){
                        /*cancel the meeting*/
                        write(c2p[i][1],"done",BUF_SIZE);
                        read(p2c[i][0],p2cBuf,BUF_SIZE);//message of the meedting to be canceled
                        // printf("cacel the meeting%s\n",p2cBuf);
                        char temp[80];
                        struct Appointment *cancel=(struct Appointment*)malloc(sizeof(struct Appointment));
                        strcpy(temp,p2cBuf);
                        char *ID=strtok(temp," ");
                        char *information=strtok(NULL,"");
                        cancel=creation(information);
                        cancel->id=atoi(ID);
                        int d = atoi(cancel->date)%100;
                        int is_removed=removeAppointmentPriority(timetable, pTimetable, d, cancel->id);
                        if(is_removed==0){
                            scheduled_meeting--;
                        }
                        write(c2p[i][1],"done",BUF_SIZE);
                    }
                    else if(strcmp(p2cBuf,"schedule")==0){
                        /*schedule the meeting*/
                        scheduled_meeting++;
                        int priority=getPiority(appointment->type);
                        assingAppointmentPriority(timetable, pTimetable, appointment->id, atoi(appointment->date), appointment->startTime, appointment->duration, priority);
                        write(c2p[i][1],"done",BUF_SIZE);
                    }
                    else if(strcmp(p2cBuf,"request")==0){
                        char username[NAME_SIZE];
                        strcpy(username, name[i]);
                        username[0] -= ('a' - 'A');
                        sprintf(c2pBuf,"%s - %d\n",username,scheduled_meeting);
                        write(c2p[i][1],c2pBuf,BUF_SIZE);
                    }
                    else if(strcmp(p2cBuf,"utilization")==0){
                        char username[NAME_SIZE];
                        strcpy(username, name[i]);
                        username[0]-= ('a' - 'A');
                        strcpy(c2pBuf,get_ultilization_rate(username,pTimetable,days));
                        write(c2p[i][1],c2pBuf,BUF_SIZE);//send the utilization rate
                        break;
                    }
                    else if(strcmp(p2cBuf,"finish")==0){
                        char *child_meeting=getMeeting(timetable,days);
                        strcpy(c2pBuf,child_meeting);
                        write(c2p[i][1], c2pBuf, BUF_SIZE);//send the meeting been scheduled
                        // printf("%s\n",p2cBuf);
                    }
                    else if(strcmp(p2cBuf,"reject")==0){//if the meeting have been reject
                        write(c2p[i][1],"done",BUF_SIZE);
                    }
                /*-----------------------------[CHILD-PRIORITY] RESCHEDULING MODULE ----------------------------------------------------------------*/
                    else if(strcmp(p2cBuf, "reschedule") == 0){
                        // printf("%s reschuling now\n", cPretext);
                        int availSlots[MAX_TIME_SLOTS];
                        int numberOfAvail = getAvailableSlots(timetable,days,availSlots);
                        char strOut[MAX_CHAR_SLOTNO];
                        getStringFromIntArray(availSlots, numberOfAvail,strOut);
                        // printf("%s available: %s\n", cPretext, strOut);
                        strcpy(c2pBuf, strOut);
                        write(c2p[i][1], c2pBuf, BUF_SIZE);

                        n = read(p2c[i][0], p2cBuf, BUF_SIZE);
                        p2cBuf[n] =0;
                    /*----------[CHILD] DECODING RESCHEDULING MESSAGE ---------------------*/
                        if(strcmp(p2cBuf, "NO_SLOTS") !=0 ){
                            char temp[BUF_SIZE];
                            strcpy(temp, p2cBuf);
                            char *token;
                            token = strtok(temp, " ");
                            int rescheduleCode = atoi(token);
                            int slotNo = rescheduleCode % 100;
                            int taskID = rescheduleCode / 10000;
                            int slotsNeeded = (rescheduleCode / 1000) % 10;

                            token = strtok(NULL, " ");
                            int priority = atoi(token);
                            // printf("%s slot no:%d needed slots:%d priority: %d\n", cPretext, slotNo, slotsNeeded, priority);
                            fillTimeTable(timetable, days, taskID, slotNo, slotsNeeded);
                            fillTimeTable(pTimetable, days, priority, slotNo, slotsNeeded);
                            // printTimeTable(timetable, days);
                        }
                        // printf("Rescheduling finished\n");
                        strcpy(c2pBuf, "reschedule response");
                        write(c2p[i][1], c2pBuf, BUF_SIZE);
                    /*----------------------------------------------------------------------*/

                    }
                /*---------------------------------------------------------------------------------------------------------------------------------*/
                    else{
                        char temp[80];//To hold a meeting
                        strcpy(temp,p2cBuf);
                        char *ID=strtok(temp," ");
                        char *information=strtok(NULL,"");
                        appointment=creation(information);
                        appointment->id=atoi(ID);
                        int priority=getPiority(appointment->type);
                        int av=checkAvP(appointment->date, appointment->startTime, appointment->duration, pTimetable, priority);
                        if(av==1){/*add the code for available*/
                            reject_list=generateTBR(appointment->date, appointment->startTime, appointment->duration, timetable);
                            n=strlen(reject_list);
                            reject_list[n]='\0';
                            // printf("rejected list length: %d\n", strlen(reject_list));
                            if(strlen(reject_list)==0){
                                write(c2p[i][1],"y",BUF_SIZE);
                            }
                            else{
                                // printf("child %s To be removed: '%s' %d\n", name[i], reject_list, strlen(reject_list));
                                sprintf(c2pBuf,"%s",reject_list);
                                write(c2p[i][1],c2pBuf,BUF_SIZE);//rejct list will be send 
                            }
                            
                        }
                        else{
                            write(c2p[i][1],"n",BUF_SIZE);
                        }
                    }
                }

                // printf("%s waiting for command\n", cPretext);
            }
		}
	}
    
    // Program start
    char input[100];
    printf("~~WELCOME TO APO~~\n");
    float num_meeting=0;
    struct Appointment *head=(struct Appointment*)malloc(sizeof(struct Appointment));
    head->id=-1;
    int alive = 1;
    /*---------------[PARENT] PARENT CLOSING UNUSED PIPES--------------------------*/
    for(i=0;i<childNumber;i++){
        close(p2c[i][0]);
        close(c2p[i][1]);
    }
    /*-----------------------------------------------------------------------------*/
    while(alive == 1){
        printf("Please enter appointment:\n");

        //-----------------[PARENT] INPUT READING & PARSING -----------------------
        // gets(input);
        
        // int len=strlen(input);
        // input[len]='\0';
        // strcpy(p2cBuf, input);
        // char temp[BUF_SIZE];
        // strcpy(temp,input);
        // char* token=strtok (temp, " ");

        gets(input);
        printf("==> %s\n", input);
        
        int len=strlen(input);
        input[len]='\0';
        strcpy(p2cBuf, input);
        char temp[BUF_SIZE];
        strcpy(temp,input);
        char* token=strtok (temp, " ");
        char temp_input[80];
        strcpy(temp_input, input);
        char* temp_token = strtok(temp_input, " ");
        //------------------------------------------------------------------------
        if(strcmp("endProgram", input) == 0){ // end Program
            for(i=0;i<childNumber;i++){
                write(p2c[i][1],p2cBuf,len);
                close(p2c[i][1]);
                close(c2p[i][0]);
            }
            alive = 0;
            close(command_file);
            close(create);
            break;
        }
        else if(strcmp("import", temp_token) == 0){
            temp_token = strtok (NULL, " ");
            
            int length;
            FILE* filePointer;
            int bufferLength = 255;
            char buffer[bufferLength]; /* not ISO 90 compatible */

            filePointer = fopen(temp_token, "r");
            printf("The output is\n");
            command_file=fopen("./ALL_requests.txt","a+");
            while((length=fgets(buffer, bufferLength, filePointer))!=0) {
                strcpy(buffer, removeWhite(buffer));
                general_id++;
                char temp_input[BUF_SIZE];
                sprintf(temp_input,"%s\n",buffer);
                fputs(temp_input,command_file);
                printf("%s\n",temp_input);
                struct Appointment *appointment = creation(buffer);
                appointment->id=general_id;
                insertlast(appointment, &head);
            }
            fclose(filePointer);
            fclose(command_file);
        }
        else if(strcmp("rescheduling", input) == 0){
            printf("======>Rescheduling activated\n");
            isRescheduleOn = 1;
        }

        else if( isAppointmentCommand(token) ) {
            // printf("normal command: %s\n", token);
            general_id++;
            command_file=fopen("./ALL_requests.txt","a+");
            char temp_input[BUF_SIZE];
            sprintf(temp_input,"%s\n",input);
            fputs(temp_input,command_file);
            struct Appointment *appointment = creation(input);
            appointment->id=general_id;
            insertlast(appointment, &head);
            fclose(command_file);
        }

        else if(strcmp("printSchd", token) == 0){
            token = strtok(NULL, " ");
            struct Appointment *second=(struct Appointment*)malloc(sizeof(struct Appointment));
            /* clear function should be here */ 
            int index;
            second=head;
            char algorithms[2][20];
            char logFile[20];
            int seq = 0;
            int algNum = 1;
            char alg[BUF_SIZE];
            FILE *fp,*rj_file;
            if(strcmp(token,"FCFS")==0){
                seq=readFileSeq(0) + 1;
                sprintf(logFile,"./output/G05_%02d_FCFS.txt",seq);
                updateFileSeq(0);
                algNum=1;
            }
            else if(strcmp(token,"ALL")==0){
                seq=readFileSeq(2) + 1;
                sprintf(logFile, "./output/G05_%02d_ALL.txt", seq);
                updateFileSeq(2);
                algNum=2;
            }
            else if(strcmp(token,"PRIORITY")==0){
                seq=readFileSeq(1) + 1;
                sprintf(logFile, "./output/G05_%02d_PRIORITY.txt", seq);
                updateFileSeq(1);
                algNum=1;
            }
            fp=fopen(logFile,"a+");
            rj_file=fopen("./output/G05_rejected.dat","w");
            float request_accept;
            float request_reject;
            if(strcmp(token, "FCFS")==0 || strcmp(token,"ALL")==0){
                fputs("Period: ", fp);
                for (i = 0; i < 8; i++) {
                    if (i == 4 || i == 6) {
                        fputs("-", fp);
                        char temp[2];
                        temp[0] = startDate[i];
                        temp[1] = '\0';
                        fputs(temp, fp);
                    } else {
                        char temp[2];
                        temp[0] = startDate[i];
                        temp[1] = '\0';
                        fputs(temp, fp);
                    }
                }
                fputs(" to ", fp);
                for (i = 0; i < 9; i++) {
                    if (i == 4 || i == 6) {
                        fputs("-", fp);
                        char temp[2];
                        temp[0] = endDate[i];
                        temp[1] = '\0';
                        fputs(temp, fp);
                    } else {
                        char temp[2];
                        temp[0] = endDate[i];
                        temp[1] = '\0';
                        fputs(temp, fp);
                    }
                }
                fputs("\nAlgorithm used: FCFS", fp);
                fputs("\n\n", fp);
                fputs("***Appointment Schedule***\n\n", fp);
                char reject_num[BUF_SIZE];
                char utilization_time[BUF_SIZE];
                strcpy(reject_num,"");
                strcpy(utilization_time,"");
                fputs("\n*** Rejected List(FCFS)***\n",rj_file);
                request_accept=0;
                request_accept=0;
                clear_list(&head);
                printf("FCFS\n");
            //---------------------[PARENT] SENDING SCHEDULING SETUP MESSAGE---------------------
                strcpy(p2cBuf, "FCFS");
                int n;
                for(index = 0; index < childNumber; index++){
                    write(p2c[index][1], p2cBuf, BUF_SIZE);
                    read(c2p[index][0], c2pBuf, BUF_SIZE);
                }
            //-----------------------------------------------------------------------
            
            //------------------------[PARENT] DISPATCHING APPOINTMENTS TO USERS----------------------
                second = second->next;
                int count=0;
                while(second != NULL){
                    // printf("!!!!date:%d\n", atoi(second->date));
                    // int d = atoi(second->date);
                    if( isHoliday(atoi(second->date)) == 1) {
                        second->able = false;
                        second = second->next;
                        continue;
                    }
                    count++;
                    char id_str[BUF_SIZE];
                    sprintf(p2cBuf,"%d %s",second->id,second->sentence);
                    for(index = 0; index < second->all_num; index++){//only read once in the child in the process
                        int ID = getIDByName(second->all_name[index],name, childNumber);
                        // printf("sending message: %s\n", p2cBuf);
                        write(p2c[ID][1],p2cBuf, BUF_SIZE);
                        read(c2p[ID][0], c2pBuf, BUF_SIZE);
                        if(strcmp(c2pBuf, "n") == 0){//the child cannot schedule the meeting
                            // printf("Parent: %s is not available\n", name[ID]);
                            second->able = false;
                            // break;
                        }
                    }
                    int isRejected=0;
                    if(second->able){
                        strcpy(p2cBuf, "schedule");
                    }
                    else{
                        strcpy(p2cBuf, "reject");
                        isRejected = 1;
                    }
                    for(index=0; index < second->all_num; index++){//only read once in the child in the process
                        int ID = getIDByName(second->all_name[index],name, childNumber);
                        write(p2c[ID][1], p2cBuf, BUF_SIZE);
                        read(c2p[ID][0], c2pBuf, BUF_SIZE);
                    }
                /*----------------------------[PARENT-FCFS]RESCHEDULE MODULE-------------------------------------------------------------------*/
                    if( isRejected == 1 && isRescheduleOn == 1){
                        int (*availabilityMap)[TIMESLOTS_PER_DAY] = malloc(sizeof(int[days][TIMESLOTS_PER_DAY]));
                        // printf("=====>Rescheduling . . . \n");
                        for(index=0; index < second->all_num; index++){//only read once in the child in the process
                            int ID = getIDByName(second->all_name[index],name, childNumber);
                            strcpy(p2cBuf, "reschedule");
                            write(p2c[ID][1], p2cBuf, BUF_SIZE);
                            // printf("Parent: %s sent to %s\n", p2cBuf, name[index]);
                            //x is for number of bytes sent
                            int x = read(c2p[ID][0], c2pBuf, BUF_SIZE);
                            c2pBuf[x] = 0;
                            int slotsArr[MAX_TIME_SLOTS];
                            int nbrOfSlots = getIntArrayFromString(c2pBuf, slotsArr);
                            // printf("Parent: %s slots: ", name[ID]);
                            // printIntArray(slotsArr, nbrOfSlots);
                            // printTimeTable(availabilityMap, days);
                            fillAvailability(availabilityMap, slotsArr, nbrOfSlots);
                            // printTimeTable(availabilityMap, days);
                        }
                        int slotsNeeded = ceil(second->duration);
                        int commonSlot = findRescheduleSlot(availabilityMap,days,second->all_num,slotsNeeded);
                        
                        if(commonSlot < 0){
                            strcpy(p2c,"NO_SLOTS");

                            for(index=0; index < second->all_num; index++){//only read once in the child in the process
                                int ID = getIDByName(second->all_name[index],name, childNumber);
                                write(p2c[ID][1], p2cBuf, BUF_SIZE);
                                read(c2p[ID][0], c2pBuf, BUF_SIZE);
                            }
                            // printf("Rescheduling slots not found\n");
                        }
                        else{
                            int rescheduleCode = (second->id*10000) + (slotsNeeded*1000) + commonSlot;
                            sprintf(p2cBuf, "%d", rescheduleCode);
                            // printf("Parent: slot No: %d needed: %d\n", rescheduleCode, slotsNeeded);

                            for(index=0; index < second->all_num; index++){//only read once in the child in the process
                                int ID = getIDByName(second->all_name[index],name, childNumber);
                                write(p2c[ID][1], p2cBuf, BUF_SIZE);
                                read(c2p[ID][0], c2pBuf, BUF_SIZE);
                            }
                            //updating the structure
                            second->isRescheduled = 1;
                            second->able = true;
                            second->newStartTime = ((commonSlot%10) * 100) + 1800;
                            sprintf(second->newDate, "%d", atoi(startDate) + (commonSlot/10));
                        }
                        
                        
                    }
                /*---------------------------------------------------------------------------------------------------------------------------------*/
                    second = second->next;
                }
                //FCFS
                strcpy(p2cBuf, "finish");//all the meeting messeages finished sending
                for(index = 0; index < childNumber; index++){
                    write(p2c[index][1], p2cBuf, BUF_SIZE);
                    read(c2p[index][0], c2pBuf, BUF_SIZE);
                    // printf("%s %s\n",name[index],c2pBuf);
                    char temp[BUF_SIZE];
                    strcpy(temp,c2pBuf);
                    char *token=strtok(temp," ");
                    // printf("\n%s: %s\n",name[index], c2pBuf);
                    int count=0;
                    char written[BUF_SIZE];
                    char firstline[BUF_SIZE];
                    strcpy(written,"");
                    strcpy(firstline,"");
                    request_accept=0;
                    request_reject=0;
                    char username[NAME_SIZE];
                    strcpy(username, name[index]);
                    username[0] -= ('a' - 'A');
                    printf("\n=================%s===================\n",username);
                    fputs("Date         Start    End      Type           People\n",fp);
                    fputs("========================================================\n",fp);
                    while(token != NULL){
                        int m_id=atoi(token);
                        count++;
                        struct Appointment *meeting=FAPO(&head,m_id);
                        token = strtok (NULL, " ");
                        // printf("%d \n",m_id);
                        printf("%s",print_Appointment(meeting));
                        fputs(print_Appointment(meeting),fp);

                        // sprintf(written,"%s%s",written,print_Appointment(meeting));
                    }
                    sprintf(firstline,"%s, you have %d appointments\n",username,count);
                    printf("%s",firstline);
                    fputs(firstline,fp);
                    // fputs("Date         Start    End      Type           People\n",fp);
                    fputs("========================================================\n",fp);
                    // fputs(written,fp);
                    // sprintf(written,"               - End of %s's Schedule -\n",name[index]);
                    // fputs(written,fp);
                    // sprintf(written,"========================================================\n\n\n");
                    // fputs(written,fp);
                    write(p2c[index][1],"request",BUF_SIZE);
                    read(c2p[index][0],c2pBuf,BUF_SIZE);
                    // printf("%s",c2pBuf);
                    sprintf(reject_num,"%s%s",reject_num,c2pBuf);
                    write(p2c[index][1],"utilization",BUF_SIZE);
                    read(c2p[index][0],c2pBuf,BUF_SIZE);
                    printf("%s",c2pBuf);
                    sprintf(utilization_time,"%s%s",utilization_time,c2pBuf);
                    // printf("%s\n", utilization_time);
                }
                char temp_buff[BUF_SIZE];
                char firstline[BUF_SIZE];
                strcpy(firstline,"");
                strcpy(temp_buff,"");
                second=head;
                second=second->next;
                while(second!=NULL){
                    if(second->able==true){
                        request_accept++;
                    }
                    else{
                        request_reject++;
                        sprintf(temp_buff,"%.0f. %s\n",request_reject,second->sentence);
                        fputs(temp_buff,rj_file);
                    }
                    second=second->next;
                }
                sprintf(firstline,"Altogether there are %.0f appointments rejected.\n",request_reject);
                fputs(firstline,rj_file);
                fputs("\n*** Performance ***\n",rj_file);
                sprintf(temp_buff,"Total Number of Requests Received: %.1f(100%%)\n",general_id);
                sprintf(temp_buff,"%s      Number of Requests Accepted: %.1f(%.1f%%)\n",temp_buff,request_accept,request_accept/general_id*100.0);
                sprintf(temp_buff,"%s      Number of Requests Rejected: %.1f(%.1f%%)\n",temp_buff,request_reject,(request_reject)/(general_id)*100.0);
                fputs(temp_buff,rj_file);
                fputs("\nNumber of Requests Accepted by Individual:\n",rj_file);    
                fputs(reject_num,rj_file);
                fputs("\nUtilization of Time Slot:\n",rj_file);
                fputs(utilization_time,rj_file);
            //----------------------------------------------------------------------------------
            }
            if(strcmp(token, "PRIORITY") == 0 || strcmp(token,"ALL")==0){
                fputs("Period: ", fp);
                for (i = 0; i < 8; i++) {
                    if (i == 4 || i == 6) {
                        fputs("-", fp);
                        char temp[2];
                        temp[0] = startDate[i];
                        temp[1] = '\0';
                        fputs(temp, fp);
                    } else {
                        char temp[2];
                        temp[0] = startDate[i];
                        temp[1] = '\0';
                        fputs(temp, fp);
                    }
                }
                fputs(" to ", fp);
                for (i = 0; i < 9; i++) {
                    if (i == 4 || i == 6) {
                        fputs("-", fp);
                        char temp[2];
                        temp[0] = endDate[i];
                        temp[1] = '\0';
                        fputs(temp, fp);
                    } else {
                        char temp[2];
                        temp[0] = endDate[i];
                        temp[1] = '\0';
                        fputs(temp, fp);
                    }
                }
                fputs("\nAlgorithm used: PRIORITY", fp);
                fputs("\n\n", fp);
                fputs("***Appointment Schedule***\n\n", fp);
                // strcpy(Reject,"");
                fputs("\n*** Rejected List(PRIORITY)***\n",rj_file);
                request_accept=0;
                request_accept=0;
                clear_list(&head);
                strcpy(p2cBuf, "PRIORITY");
                printf("\nPRIORITY\n");
                for(index = 0; index < childNumber; index++){
                    write(p2c[index][1], p2cBuf, BUF_SIZE);
                    read(c2p[index][0], c2pBuf, BUF_SIZE);
                }
                second=head;
                second=second->next;
                int t=0;
                while(second!=NULL){
                    bool flag=false;
                    bool flag2=true;
                    // printf("!!!!date: %s\n", second->date);
                    if( isHoliday(atoi(second->date)) == 1) {
                        second->able = false;
                        second = second->next;
                        continue;
                    }
                    // printf("%d===========here========\n",t);
                    t++;
                    char Reject[BUF_SIZE];
                    strcpy(Reject,"");
                    char id_str[BUF_SIZE];
                    sprintf(p2cBuf,"%d %s",second->id,second->sentence);
                    // printf("Parent message: %s\n", p2cBuf);
                    for(index=0; index<second->all_num; index++){
                        int ID=getIDByName(second->all_name[index],name,childNumber);
                        write(p2c[ID][1],p2cBuf, BUF_SIZE);//tell the child the meeting
                        read(c2p[ID][0], c2pBuf, BUF_SIZE);//child reply message
                        // printf("Parent: child %s response : %s\n", name[ID], c2pBuf);
                        if(strcmp(c2pBuf,"n")==0){
                            second->able=false;
                            flag=false;
                            flag2=false;//if anyone reject then no one can arrange it
                        }
                        else if(strcmp(c2pBuf,"y") != 0){
                            char removeMesg[BUF_SIZE];
                            char *tok;
                            strcpy(removeMesg,c2pBuf);
                            tok=strtok(removeMesg," ");
                            while(tok != NULL){
                                sprintf(Reject,"%s %s",Reject,tok);
                                tok = strtok(NULL, " ");
                            }

                        }
                    }
                    if(second->able){//to remove all the meeting
                        int index_p;
                        char temp[BUF_SIZE];
                        strcpy(temp,Reject);
                        // printf("####%s\n", temp);
                        char *tok=strtok(temp," ");
                        // printf("flag111111\n");
                        while(tok!=NULL){
                            struct Appointment *iterator=head;

                            int id = atoi(tok);
                            // printf("Meeting to be canceled: %s\n", tok);
                            while(iterator!=NULL){//if the meeting if gonna to be reject, set the ability to false
                                if(iterator->id==atoi(tok)){
                                    iterator->able=false;
                                    break;
                                }
                                iterator=iterator->next;
                            }
                            sprintf(p2cBuf,"%d %s",iterator->id,iterator->sentence);
                            // printf("goto\n");
                            for(index_p=0;index_p<iterator->all_num;index_p++){
                                int child_id=getIDByName(iterator->all_name[index_p],name, childNumber);
                                write(p2c[child_id][1],"cancel",BUF_SIZE);
                                read(c2p[child_id][0],c2pBuf,BUF_SIZE);
                                write(p2c[child_id][1],p2cBuf,BUF_SIZE);
                                read(c2p[child_id][0],c2pBuf,BUF_SIZE);
                            }
                            // printf("canceled\n");
                            tok=strtok(NULL," ");
                        }
                    }
                    // printf("flag111122\n");
                    int isRejected=0;
                    if(second->able){
                        strcpy(p2cBuf, "schedule");
                    }
                    else{
                        strcpy(p2cBuf, "reject");
                        isRejected = 1;
                    }
                    for(index=0; index < second->all_num; index++){//only read once in the child in the process
                        int ID = getIDByName(second->all_name[index],name, childNumber);
                        write(p2c[ID][1], p2cBuf, BUF_SIZE);
                        read(c2p[ID][0], c2pBuf, BUF_SIZE);
                    }

                /*----------------------------[PARENT-PRIORITY] RESCHEDULE MODULE-------------------------------------------------------------*/
                    if( isRejected == 1 && isRescheduleOn == 1){
                        int (*availabilityMap)[TIMESLOTS_PER_DAY] = malloc(sizeof(int[days][TIMESLOTS_PER_DAY]));
                        // printf("--------->Rescheduling . . . \n");
                        for(index=0; index < second->all_num; index++){//only read once in the child in the process
                            int ID = getIDByName(second->all_name[index],name, childNumber);
                            strcpy(p2cBuf, "reschedule");
                            write(p2c[ID][1], p2cBuf, BUF_SIZE);
                            // printf("Parent: %s sent to %s\n", p2cBuf, name[ID]);
                            //x is for number of bytes sent
                            int x = read(c2p[ID][0], c2pBuf, BUF_SIZE);
                            c2pBuf[x] = 0;
                            int slotsArr[MAX_TIME_SLOTS];
                            int nbrOfSlots = getIntArrayFromString(c2pBuf, slotsArr);
                            // printf("Parent: %s slots: ", name[ID]);
                            // printIntArray(slotsArr, nbrOfSlots);
                            // printTimeTable(availabilityMap, days);
                            fillAvailability(availabilityMap, slotsArr, nbrOfSlots);
                            // printTimeTable(availabilityMap, days);
                        }
                        int slotsNeeded = ceil(second->duration);
                        int commonSlot = findRescheduleSlot(availabilityMap,days,second->all_num,slotsNeeded);
                        if(commonSlot < 0){
                            strcpy(p2cBuf, "NO_SLOTS");
                            for(index=0; index < second->all_num; index++){//only read once in the child in the process
                                int ID = getIDByName(second->all_name[index],name, childNumber);
                                write(p2c[ID][1], p2cBuf, BUF_SIZE);
                                read(c2p[ID][0], c2pBuf, BUF_SIZE);
                            }
                            // printf("Rescheduling slots not found\n");
                        }
                        else{
                            int rescheduleCode = (second->id*10000) + (slotsNeeded*1000) + commonSlot;
                            sprintf(p2cBuf, "%d %d", rescheduleCode, getPiority(second->type));
                            // printf("Parent: slot No: %d needed: %d\n", rescheduleCode, slotsNeeded);

                            for(index=0; index < second->all_num; index++){//only read once in the child in the process
                                int ID = getIDByName(second->all_name[index],name, childNumber);
                                write(p2c[ID][1], p2cBuf, BUF_SIZE);
                                read(c2p[ID][0], c2pBuf, BUF_SIZE);
                            }
                            //updating the structure
                            second->isRescheduled = 1;
                            second->able = false;
                            second->newStartTime = ((commonSlot%10) * 100) + 1800;
                            sprintf(second->newDate, "%d", atoi(startDate) + (commonSlot/10));
                        }
                        
                    }
                /*--------------------------------------------------------------------------------------------------------------------*/

                    second=second->next;
                }
                //------------------[PARENT] END-SCHEDULING MESSAGE TO CHILDREN-----------------------------
                strcpy(p2cBuf, "finish");//all the meeting messeages finished sending
                char reject_num[BUF_SIZE];
                char utilization_time[BUF_SIZE];
                strcpy(utilization_time,"");
                strcpy(reject_num,"");
                for(index = 0; index < childNumber; index++){
                    char username[NAME_SIZE];
                    strcpy(username, name[index]);
                    username[0] -= ('a' - 'A');
                    write(p2c[index][1], p2cBuf, BUF_SIZE);
                    read(c2p[index][0], c2pBuf, BUF_SIZE);
                    char temp[BUF_SIZE];
                    strcpy(temp,c2pBuf);
                    char *token=strtok(temp," ");
                    // printf("\n%s\n",name[index]);
                    // char buffer[BUF_SIZE];
                    char written[BUF_SIZE];
                    char firstline[BUF_SIZE];
                    strcpy(written,"");
                    strcpy(firstline,"");
                    int count=0;

                    printf("\n===========%s================\n",username);

                    fputs("Date         Start    End      Type           People\n",fp);
                    fputs("========================================================\n",fp);
                    while(token != NULL){
                        int m_id=atoi(token);
                        count++;
                        struct Appointment *meeting=FAPO(&head,m_id);
                        token = strtok (NULL, " ");
                        printf("%s",print_Appointment(meeting));
                        // strcpy(buffer,print_Appointment(meeting));
                        fputs(print_Appointment(meeting),fp);
                        // sprintf(written,"%s%s",written,print_Appointment(meeting));
                    }
                    sprintf(firstline,"%s,you have %d appointments\n",username,count);
                    fputs(firstline,fp);
                    
                    sprintf(written,"               - End of %s's Schedule -\n",name[index]);
                    fputs(written,fp);
                    sprintf(written,"========================================================\n\n\n");
                    fputs(written,fp);
                    write(p2c[index][1],"request",BUF_SIZE);
                    read(c2p[index][0],c2pBuf,BUF_SIZE);
                    sprintf(reject_num,"%s%s",reject_num,c2pBuf);
                    printf("%s\n",c2pBuf);
                    write(p2c[index][1],"utilization",BUF_SIZE);
                    read(c2p[index][0],c2pBuf,BUF_SIZE);
                    sprintf(utilization_time,"%s%s",utilization_time,c2pBuf);
                    printf("%s\n",c2pBuf);
                }
                char temp_buff[BUF_SIZE];
                char firstline[BUF_SIZE];
                strcpy(temp_buff,"");
                second=head;
                second=second->next;
                request_accept=0;
                request_reject=0;
                while(second!=NULL){
                    if(second->able==true){
                        request_accept++;
                    }
                    else{
                        request_reject++;
                        sprintf(temp_buff,"%.0f. %s\n",request_reject,second->sentence);
                        fputs(temp_buff,rj_file);
                    }
                    second=second->next;
                }
                sprintf(firstline,"Altogether there are %.0f appointments rejected.\n",request_reject);
                fputs(firstline,rj_file);
                fputs("\n*** Performance ***\n",rj_file);
                sprintf(temp_buff,"Total Number of Requests Received: %.1f(100%%)\n",general_id);
                sprintf(temp_buff,"%s      Number of Requests Accepted: %.1f(%.1f%%)\n",temp_buff,request_accept,request_accept/general_id*100.0);
                sprintf(temp_buff,"%s      Number of Requests Rejected: %.1f(%.1f%%)\n",temp_buff,request_reject,(request_reject)/(general_id)*100.0);
                fputs(temp_buff,rj_file);
                fputs("\nNumber of Requests Accepted by Individual:\n",rj_file);    
                fputs(reject_num,rj_file);
                fputs("\nUtilization of Time Slot:\n",rj_file);
                fputs(utilization_time,rj_file);
                //-------------------------------------------------------------------------------  
            }
            fclose(rj_file);
            fclose(fp); 
        }
        else{
            printf("Wrong command!\n");
            continue;
        }
    }
    /*------------------------[PARENT] CLOSING USED ENDS OF PIPE-----------------*/
    for( i=0; i< childNumber; i++){
        close(p2c[i][1]);
        close(c2p[i][0]);
    }
    /*----------------------------------------------------------------------------*/
    //----------------[PARENT] CHILDREN COLLECTION ---------------------------------
    for (i = 0; i < childNumber; i++) { 
        wait(NULL);
    }
    //------------------------------------------------------------------------------
    exit(0);
}

// get the userId by input na
int getIDByName(char singleName[NAME_SIZE], char names[][NAME_SIZE], int childNumber) {
    int i;
    for (i = 0; i < childNumber; i++) {
        if (strcmp(singleName, names[i]) == 0) {
            return i;
        }
    }
    return -1;
}
