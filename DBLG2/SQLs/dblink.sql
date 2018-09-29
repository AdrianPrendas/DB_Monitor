CREATE PUBLIC DATABASE LINK a6r1an
CONNECT TO ADRIAN IDENTIFIED BY ADRIAN
USING '(description = 
(address = 
(protocol = tcp)
(host = 192.168.0.19)
(Port = 1521) )
(connect_data = 
(SERVICE_NAME = XE ) )
)';