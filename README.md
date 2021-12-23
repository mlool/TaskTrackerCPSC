# My Personal Project

## Software Construction Progress Tracker

**What will the application do?**

- A *todo list* aimed specifically for software development


- Each task will have multiple fields indicating type, urgency, start date,
  end date, estimated time, and the developer assigned to the task.
  

- Allows multiple users to modify the dataset
    
    - There will be different access levels for each users (eg. admin
      can access and modify everything whereas a developer would only
      be able to view the list and modify the tasks assigned to them by
      the admin)

    - Each user will also have a unique id and a password, and the application
      will have the ability to add new users
      

- Ability to filter and different views for the tasks based on fields like start dates,
  urgency, etc.
  

**Who will use it?**

Anyone who want to have a program to order their software construction progress, and since
it allows multiple users, it can also be used for group works.

**Why is this project of interest to you?**

I work better when I can visually see the things I need complete, since the programs we are
writing are getting larger and larger, it would be better if I can create a todo list directly
aimed at software construction. Furthermore, this project can be modified after the course so
that it can access the web, allowing multiple users to access it at once, maybe I will also
incorporate front-end user interfaces using Java and html so that I can create a even better and
cleaner graphical user interface.

## User stories

**As a admin**
- I want to be able to login into my unique account

- I want to be able to add tasks to my list

- I want to be able to complete and delete tasks to my list 

- I want to be able to assign my developers to tasks

- I want to be able to modify tasks

**As a developer**
- I want to be able to login into my unique account

- I want to be able to add tasks to my list

- I want to be able to complete tasks assigned to me

- I want to be able to take on tasks that is open to everyone

- I want to be able to see only the tasks that I need to do

**As a visitor**
- I want to be able to login into my unique account

- I want to be able to see tasks in my list

**As a user (admin, developer, and viewer)**
- I want to be able to filter my list based on different fields

- I want to be able to save my to-do list to file

- I want to be able to load my to-do list

## Phase 4: Task 2
Wed Nov 24 16:06:32 PST 2021

new admin list initiated

Wed Nov 24 16:06:32 PST 2021

new developer list initiated

Wed Nov 24 16:06:32 PST 2021

new visitor list initiated

Wed Nov 24 16:06:53 PST 2021

new admin: admin1 added to admin list


Wed Nov 24 16:06:59 PST 2021

new developer: dev1 added to developer list

Wed Nov 24 16:07:50 PST 2021

new developer: dev2 added to developer list

Wed Nov 24 16:07:57 PST 2021

new developer: dev3 added to developer list

Wed Nov 24 16:08:07 PST 2021

new visitor: vis1 added to visitor list

Wed Nov 24 16:08:14 PST 2021

new visitor: vis2 added to visitor list

Wed Nov 24 16:09:00 PST 2021

new task: task 1 added

Wed Nov 24 16:09:21 PST 2021

new task: task 2 added

Wed Nov 24 16:09:55 PST 2021

new task: task 3 added

Wed Nov 24 16:10:19 PST 2021

new task: task added by dev1 added

Wed Nov 24 16:10:45 PST 2021

new task: task added by dev2 added

Wed Nov 24 16:10:52 PST 2021

developer: dev2 assigned him/her-self to task : task 2

Wed Nov 24 16:10:59 PST 2021

developer: dev2 changed status of the task: task 2 to Completed

Wed Nov 24 16:11:16 PST 2021

admin: admin1 changed status of task: task 2 to In progress

Wed Nov 24 16:11:19 PST 2021

admin: admin1 changed hours required for task: task 3 to 9

Wed Nov 24 16:11:23 PST 2021

admin: admin1 changed status of task: task 3 to In progress

Wed Nov 24 16:11:25 PST 2021

admin: admin1 changed developer of task: task 3 to dev1

Wed Nov 24 16:11:28 PST 2021

admin: admin1 deleted task: task 1

Wed Nov 24 16:11:41 PST 2021

new developer: dev4 added to developer list

Wed Nov 24 16:12:07 PST 2021

admin: admin1 changed developer of task: task added by dev2 to dev4

Wed Nov 24 16:12:11 PST 2021

admin: admin1 changed developer of task: task added by dev1 to dev2

Wed Nov 24 16:12:29 PST 2021

developer: dev4 changed status of the task: task added by dev2 to Completed

Wed Nov 24 16:12:53 PST 2021

developer: dev2 changed status of the task: task 2 to Completed

Wed Nov 24 16:12:55 PST 2021

developer: dev2 changed status of the task: task added by dev1 to In progress

Wed Nov 24 16:13:09 PST 2021

admin: admin1 deleted task: task added by dev1

Wed Nov 24 16:13:18 PST 2021

admin: admin1 renamed task: task added by dev2 to task something

Wed Nov 24 16:13:25 PST 2021

admin: admin1 changed date of task: task something to 2022/12/1

Wed Nov 24 16:13:33 PST 2021

admin: admin1 deleted task: task 2



## Phase 4: Task 3

I think I can extract an abstract class my ListOfAdmin, ListOfDeveloper, and ListOfVisitor as
there still are some similar methods between them. Furthermore, I think I
can remove the connection between ListOfData and listOfTasks as User already contains ListOfTasks.

Furthermore, I should refactor the private classes within ProgressTrackerAppGUI to make the 
program much simpler, as there are currently significant number of duplicate codes that does
not necessary need to be there. Furthermore, many of the private classes contains connections 
to specific Users (Admin, Developer, Visitor, etc.) and I think I should refactor it so that they
are reference to User rather than the subclasses. 

I can also improve the implementation of the User class as there is no abstract methods despite 
the class being abstract and I believe there are enough methods with the same name to make it worth
while as well as giving me the ability to use the User class as input rather than its subclasses.
