# import the socket library
import socket
# create a socket object
clientSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
# define the server's name and port on which you want to connect
serverName = '127.0.0.1'
serverPort = 12345
# connect to the server
clientSocket.connect((serverName, serverPort))
# receive data from the server and decode to get the string.
# password = 3962
password = input("Please input password: ")
clientSocket.send(str(password).encode())
sentence = clientSocket.recv(1024).decode()
print ("from server:", sentence)
# close the connection
clientSocket.close()