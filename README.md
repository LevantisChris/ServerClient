# ServerClient
Simple program in Java for handling files between a Server and a client. This project does not contain threads, so one client at a time.

<h1>COMMUNICATION PROTOCOL DIAGRAM</h1>

![diagramma_ask1_omadiki](https://user-images.githubusercontent.com/117188793/229207740-8af787ab-1a3e-4103-83dd-1e838e278c54.png)

<h1>EXECUTION INSTRUCTIONS</h1>

* Open in your preferred IDE the folder BigMain
* Open MainClient and MainServer
* First run the Server and then the Client

The server stays open for as long as you want. So we can close the
Client program and open another one, we won't have any problem
since every time it will connect to the Server. But if the Server program
close then we will have a problem with the Client, because it will not be able to connect to
a Server and it will throw us an exception.

