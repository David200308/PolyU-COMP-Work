# import the socket library
import socket
# create a socket object
clientSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
# define the server's name and port on which you want to connect
serverName = '127.0.0.1'
serverPort = 12345
# connect to the server
clientPort = 33962
clientSocket.bind(('', clientPort))

clientSocket.connect((serverName, serverPort))
# receive data from the server and decode to get the string.
sentence = clientSocket.recv(1024).decode()
print ("from server:", sentence)
# close the connection
clientSocket.close()