# IHLP A2
Projects Exploring Internet and Higher Layer Protocols 

This project will explore packets and delays which you studied in Chapter 1. You will develop a packet receiver which will receive the file. Your receiver will be called Recv.java and it will work precisely like this:

[afs2] $ java Recv afsaccess3 5001 data.dat
  connected to 128.235.1.2 on port 5000
  Pkt 1  SEQ=1500, len=1500, delay = 1200 ms
    ...
  Pkt 14 SEQ=10500, len=220, delay = 1401 ms
  Total 14 packets / 20199 bytes recd. Total delay = 17920 ms, average = 1280 ms
  
The run shows a receiver connecting to a remote host and fetching a bunch of packets. A packet comprises a header and a payload (data). The receiver extracts the payload from each packet and writes it out to the output file 'data.dat' in the correct order. The receiver prints out packet related information in the order the packets are received, not necessarily their correct order in the file. The receiver closes the connection after receiving the entire file.

The structure of a packet is:
 ```
    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                        Sequence Number                        |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |           Length              |    Data (up to 1500 bytes)    |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 ```
   
The sequence number is 32-bit and the packet length is 16-bit, both in network byte order, RFC 1700. The sequence number is similar to a TCP sequence number, RFC 793; it indicates the position of the packet in the file.
