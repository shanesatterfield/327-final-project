# 327-final-project
CECS 327 Final Project

## Dependencies
- Java
- Gradle

## Setting up the project
You can clone this repository to get the source code. You can use the provided Gradle build script to build the project and install the dependencies.

## Running the Project
You can run the project with the following command:
```
gradle run -Ptype=client -Pport=9001 -Paddr="192.168.1.21"
```
The type property can be either "server" or "s" to run the server and "client" or "c" to run as client.

The port property will set the port number. The addr property will set the InetAddress. If you are running the server, the port property specifies the listening port. If you are running as a client, these properties specify the port and address of the server that you are communicating with.

## Note
Do not submit this assignment or plagiarize this code for your class project.

## Given Prompt
This assignment involves parallel operations on distributed data structures with networked computers. While it involves network programming, this is not an assignment on computer network programming, however. Nonetheless, students will use the client-server paradigm on this assignment. This is to be completed by a team of 2 or 3 students.

#### DATA STRUCTURE
The assignment is to maintain a fixed size set of 100 nodes, which are replicated (duplicated) on 3 computers. The distributed set is to be coherent at real time, i.e., each thread operates on the most up-to-date values. A client-server software can be used to maintain the set & its nodes.

#### THREADS
Each computer, that hosts a replica of the set, also hosts 100 threads. The threads concurrently operate (only update oper is supported) on the nodes of the set. Each thread chooses a random node each time to update (generate a random number). Also, each thread repeats this 100 times with 500 msec of sleep between each operation.

#### NODE UPDATE
Of course, each node operation requires mutual exclusion. Therefore, students need to determine how to use lock to do this. Moreover, mutex must be enforced across the network. That is, while a thread on computer A is updating node W, no other thread on any computer can update node W concurrently. Furthermore, the next thread to update node W must use the values modified by thread A.

#### NODES
Each node holds its distinct arrays of 100 characters (another option is to have different array lenghts). When a thread updates a node, it randomly shuffles the characters. (This is a simple task to give a thread something to do.)

#### SOME THOUGHTS
There are various design & implementation options for this assignment. Students should consider the pros & cons of their design & implementation in terms of: efficiency, performance, scalability, robustness, etc. Students are encouraged to implement the most effective software.

#### DELIVERABLE
Each team member will submit his/her individual written report for the project which outlines the principles and designs of the software, as well as the assumptions. The report should also address starvation & bottlenecks, especially if performance is less than desirable. Finally, the report needs to include an assessment of each team member and their contributions, including your own letter grade and a letter grade that you feel each member has earned on this assignment (with justifications).

Thanks for another fun semester & I hope that you've learned some useful skills.
