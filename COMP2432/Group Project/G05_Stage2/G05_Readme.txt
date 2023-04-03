Appointment Organizer (APO)

1. Programming Language
	- C Language


2. Program Usage:

- For normal input

	## Compile the G05_APO.c file & get the G05_APO executable file (use -lm because we use math library in c file)
	gcc G05_APO.c -o G05_APO -lm 

	## Run G05_APO by:
	## 20230401 -> Start Date, 20230430 -> End Date, john mary lucy paul -> Users (!user can replace it)
	./G05_APO 20230401 20230430 john mary lucy paul


- For batch file input

	## G05_tests.dat -> the file user want to input
	> import G05_tests.dat


- Use 4 types of commands to schedule meeting

	## Example (privateTime): 
	## paul -> host user, 20230401 1800 -> start time, 2.0 -> time usage
	> privateTime paul 20230401 1800 2.0

	## Example (projectMeeting): 
	## lucy -> host user, 20230402 1900 -> start time, 2.0 -> time usage, paul mary -> other user who join this event
	> projectMeeting john 20230402 1900 2.0 paul mary

	## Example (groupStudy): 
	## lucy -> host user, 20230403 1800 -> start time, 2.0 -> time usage, john lucy -> other user who join this event
	> groupStudy paul 20230403 1800 2.0 john lucy

	## Example (gathering): 
	## lucy -> host user, 20230404 1900 -> start time, 4.0 -> time usage, john paul mary -> other user who join this event
	> gathering lucy 20230404 1900 4.0 john paul mary


- Use reschedule command to automatically reschedule the appointment if rejected

	## if user want to open the automatically reschedule the appointment function, user need to input this command
	> rescheduling


- Use printSchd to export report file (after initial the executable file)

	## Example: use FCFS (print the schedules use the FCFS algorithms)
	> printSchd FCFS
	## output sample:
	
	"""
		Period: 2023-04-01 to 2023-04-30
		Algorithm used: FCFS

		***Appointment Schedule***

		john,you have 3 appointments
		Date         Start    End      Type           People
		========================================================
		2023-04-02   19:00   21:00   Project Meeting   => 20230401 2000 Paul Mary
		2023-04-03   18:00   20:00   Group Study       John Lucy
		2023-04-04   19:00   23:00   Gathering         John Paul Mary
               			- End of john's Schedule -
		========================================================
	
	"""

	## Example: use PRIORITY (print the schedules use the PRIORITY algorithms)
	> printSchd PRIORITY


	## Example: use ALL (print the schedules use all the algorithms)
	> printSchd ALL


3. Reminder

	- User can find all the output files in output folder !


4. COMP2432 Group Members (G05)

	- DENG Chun Yung (21084115D)
	- JIANG Guanlin (21093962D)
	- LI Tong (21101988D)
	- LIU Yuzhou (21100602D)
	- NIYITEGEKA Berwa Aime Noel (21104645D)



The copyright is belong to COMP2432 Group 5 in 2023.

