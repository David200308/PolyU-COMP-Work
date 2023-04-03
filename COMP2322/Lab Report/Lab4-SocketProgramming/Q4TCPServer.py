# import the socket library
import socket
# create a socket object
serverSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
print("socket successfully created")
# reserve a port=12345 on your computer
serverPort = 12345
# bind to the port
# we have not typed any ip in the ip field, instead we have inputted an empty string.
# this makes the server listen to requests coming from other computers on the network
serverSocket.bind(('', serverPort))
print("socket binded to %s" % (serverPort))
# put the socket into listening mode
serverSocket.listen(5)
print("socket is listening")
# a forever loop until we interrupt it or an error occurs
while True:
    # establish connection with client.
    connectionSocket, addr = serverSocket.accept()
    print('got connection from', addr)
    # send a message to the client, using encode() to send byte type
    
    passwd = connectionSocket.recv(1024).decode()

    if (passwd == '3962'):
        sentence = 'Your password is correct!'
    else:
        sentence = 'Your password is incorrect!'
    connectionSocket.send(sentence.encode())
    # close the connection with the client
    connectionSocket.close()
    break
