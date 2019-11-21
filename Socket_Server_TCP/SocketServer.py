from socket import * 
import sys

# Create a TCP server socket
#(AF_INET is used for IPv4 protocols)
#(SOCK_STREAM is used for TCP)

serverSocket = socket(AF_INET, SOCK_STREAM)	

serverPort = 12000

# server Binding
serverSocket.bind(('10.2.0.249', serverPort))

# 1 connection at one time
serverSocket.listen(1)

while True:
	print('The server is ready to receive')

	# Set up a new connection from the client
	connectionSocket,addr = serverSocket.accept()

	try:
		# Client message 받기
		message = connectionSocket.recv(1024).decode()
		# Extract the path of the requested object from the message
		# The path is the second part of HTTP header, identified by [1]
		filename = message.split()[1]
		# Because the extracted path of the HTTP request includes 
		# a character '\', we read the path from the second character 
		f = open(filename[1:])
		# Store the entire contenet of the requested file in a temporary buffer
		outputdata = f.read()
		# Send the HTTP response header line to the connection socket
		connectionSocket.send("HTTP/1.1 200 OK\r\n\r\n".encode()) 
 
		# Send the content of the requested file to the connection socket
		for i in range(0, len(outputdata)):  
			connectionSocket.send(outputdata[i].encode())
		connectionSocket.send("\r\n".encode()) 
		
		# Close the client connection socket
		connectionSocket.close()	
	

	except IOError:
			# Send HTTP response message for file not found
			connectionSocket.send("HTTP/1.1 404 Not Found\r\n\r\n".encode())
			connectionSocket.send("<html><head></head><body><h1>404 Not Found</h1></body></html>\r\n".encode())
			# Close the client connection socket
			connectionSocket.close()
			

serverSocket.close()  
sys.exit()
