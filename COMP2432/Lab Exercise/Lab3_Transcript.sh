#!/bin/bash

count=0
data=()
students=()
index=0

## Search the student grade
search() {
    grades=()
    grades_gpa=()
    gpa=0

    ## read the file and format the output
    for file in ${data[@]}
    do  
        grep -q "$studentId" $file && flag="yes" || flag="no"
        if [ $flag = "no" ]
        then
            continue
        fi

        fileIndex=0
        readarray -t filecontent < $file
        read -a courseInfo <<< ${filecontent[0]}
        read -a courseGrade <<< ${filecontent[@]}

        indexCourseInfo=0
        for val in "${courseInfo[@]}"
        do  
            if [ $val == "Subject" ]
            then
                printf ""
            elif [ "$indexCourseInfo" == 3 ]
            then
                printf "Sem $val "
                gradeIndex=0
                for grade in "${courseGrade[@]}"
                do
                    if [ "$grade" == "$studentId" ]
                    then
                        tg=${courseGrade[$gradeIndex+1]}
                        grades+=($tg)
                        if [[ ( "$tg" = "A+" ) || ( "$tg" = "A" ) || ( "$tg" = "A-" ) || ( "$tg" = "B+" ) || ( "$tg" = "B" ) || ( "$tg" = "B-" ) || ( "$tg" = "C+" ) || ( "$tg" = "C" ) || ( "$tg" = "C-" ) || ( "$tg" = "D+" ) || ( "$tg" = "D" ) || ( "$tg" = "F") ]]
                        then
                            printf "${courseGrade[$gradeIndex+1]}"
                        else
                            continue
                        fi
                    fi
                    gradeIndex=$((gradeIndex + 1))
                done
            else
                printf "$val "
            fi
            indexCourseInfo=$((indexCourseInfo + 1))
        done
        fileIndex=$((fileIndex + 1))
        printf "\n"
    done

    ## for grade and gpa
    for grade in "${grades[@]}"
    do
        if [ "$grade" = "A+" ]
        then
            grades_gpa+=(43)
        elif [ "$grade" = "A" ]
        then
            grades_gpa+=(40)
        elif [ "$grade" = "A-" ]
        then
            grades_gpa+=(37)
        elif [ "$grade" = "B+" ]
        then
            grades_gpa+=(33)
        elif [ "$grade" = "B" ]
        then
            grades_gpa+=(30)
        elif [ "$grade" = "B-" ]
        then
            grades_gpa+=(27)
        elif [ "$grade" = "C+" ]
        then
            grades_gpa+=(23)
        elif [ "$grade" = "C" ]
        then
            grades_gpa+=(20)
        elif [ "$grade" = "C-" ]
        then
            grades_gpa+=(17)
        elif [ "$grade" = "D+" ]
        then
            grades_gpa+=(13)
        elif [ "$grade" = "D" ]
        then
            grades_gpa+=(10)
        else
            continue
        fi
    done
    
    gpa=$( IFS="+"; bc <<< "${grades_gpa[*]}" )
    
    printf "GPA for ${#grades_gpa[@]} subjects "
    echo "scale=2; $gpa / (${#grades_gpa[@]} * 10)" | bc
}



## count the index of "student"
for aug in $*
do
    if [ $aug == "student" ]
    then
        break
    fi
    count=$((count + 1))
done

## For store files aug to array
index=0
for aug in $*
do
    if [ "$index" -lt "$count" ]
    then
        if [ "$aug" = *"*" ]
        then
            data=($(find -name "$aug.dat"))
            break
        else
            data+=($aug)
        fi
    fi
    index=$((index + 1))
done



## let studentId into students array
for aug in $*
do
    if [ "$index" -gt "$count" ]
    then
        students+=($aug)
    fi
    index=$((index + 1))
done


for studentId in ${students[@]}
do
    readarray -t studentInfo < student.dat
    index=0
    for val in "${studentInfo[@]}";
    do
        if [ "$index" -ge "${#studentInfo[@]}" ]
        then
            break
        else
            read -a value <<< ${studentInfo[$index]}
            if [ "$value" == "$studentId" ]
            then
                printf "Transcript for ${studentInfo[$index]} \n"
                search
                
                printf "\n"
            else
                printf ""
            fi
        fi
        index=$((index + 1))
    done

done