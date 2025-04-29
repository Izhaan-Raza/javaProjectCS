

# Chat Server Setup on Raspberry Pi

This guide will help you set up and run the **Chat Server** on a Raspberry Pi. The server is a Java-based application that uses SQLite for storing user data. Please follow the instructions below to run the server successfully.

## Prerequisites

- **Raspberry Pi** running **Ubuntu Server** or any other Linux-based distribution.
- **Java 8 or later** installed on your Raspberry Pi.
- **SQLite JDBC driver** (provided in the `lib` directory).

### Step 1: Install Java (if not already installed)

To install Java on your Raspberry Pi, run the following commands:

```bash
sudo apt update
sudo apt install openjdk-11-jdk -y
```

You can check if Java is installed by running:

```bash
java -version
```

### Step 2: Install SQLite JDBC Driver

Make sure you have the SQLite JDBC driver in the `lib` directory. If not, download it from [SQLite JDBC](https://github.com/xerial/sqlite-jdbc) or use the provided `sqlite-jdbc-3.36.0.3.jar` file.

Place the JDBC jar in your project directory at:

```
/home/izzup/javaProject/server/lib/sqlite-jdbc-3.36.0.3.jar
```

### Step 3: Compile the Server Code

Navigate to the directory where your `ChatServer.java` file is located. If you are following the example, it should be in `/home/izzup/javaProject/server`.

Run the following command to compile the server code, making sure to include the SQLite JDBC library:

```bash
javac -cp ".:/home/izzup/javaProject/server/lib/sqlite-jdbc-3.36.0.3.jar" ChatServer.java
```

This will compile the `ChatServer.java` file and generate the `ChatServer.class` file.

### Step 4: Run the Server

To start the server, use the following command:

```bash
java -cp ".:/home/izzup/javaProject/server/lib/sqlite-jdbc-3.36.0.3.jar" ChatServer
```

This will launch the chat server, and it will start listening on port `12345`. The server will be ready to accept incoming connections from the client.

### Step 5: Test the Server

Once the server is running, you can connect to it using the **Chat Client GUI** or **CLI Client** (depending on the setup you have). Make sure your Raspberry Pi's firewall allows incoming connections on port `12345`.

To test the server locally:

1. Run the **ChatClient** or **ChatClientGUI** on a device connected to the same network as your Raspberry Pi.
2. Enter the Raspberry Pi's local IP address as the **server address**.

### Step 6: Stop the Server

To stop the server, press `Ctrl+C` in the terminal window where the server is running.

---

