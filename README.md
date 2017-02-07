# iconnect
This project is similar to the popular Team Viewer project. It was another single sleepless night project for 'Network Programming Lab' in the last year of university. 
I uploaded the code as it is submitted. So the code may not be upto the industry standard.

````
Year coded: 2015
Semester : 4th year 2nd semester
Course: Network Programming Lab
`````

### Technologies Used
- Java
- Java FX
- Eclipse
- OpenCV (Tried to manipulate the captured image to reduce the frame size for faster transmission)
- Core Java Socket programming ( TCP / IP was used instead of UDP since I wanted to have a reliable secure connection)
- Hulfman Algorithm (Due to limited time at the end it was not used) 


### How to Use
- Open in Eclipse as an existing project.
- Compile and run.
- Provide the IP address of the partener computer to connect.
- Start using the remote computer!


### Troubleshoot

- Make sure the remote pc is accessible via your computer. You can check it by executing "ping `IpAddressToCheck`" from the command line.
- If you have trouble connecting, disconnect the internet from the router / mobile hotspot, 
connect your pc and the partner pc to the same router.
- If you are connected to the internet, the IP address shown in IConnect will not be always the desired ip address as a 
machine may have many ip addresses and Iconnect shows only one of them.


### Future Improvements

- File transfer was not implemented.
- Allow control was not implemented.
- Image size should be reduced under 65KB maybe using OpenCV
