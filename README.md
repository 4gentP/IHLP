# IHLP
Projects Exploring Internet and Higher Layer Protocols 

A2 Packets and Delays

This project will explore packets and delays which you studied in Chapter 1. You will develop a packet receiver which will receive the file. Your receiver will be called Recv.java and it will work precisely like this:

[afs2] $ java Recv afsaccess3 5001 data.dat
  connected to 128.235.1.2 on port 5000
  Pkt 1  SEQ=1500, len=1500, delay = 1200 ms
    ...
  Pkt 14 SEQ=10500, len=220, delay = 1401 ms
  Total 14 packets / 20199 bytes recd. Total delay = 17920 ms, average = 1280 ms
The run shows a receiver connecting to a remote host and fetching a bunch of packets. A packet comprises a header and a payload (data). The receiver extracts the payload from each packet and writes it out to the output file 'data.dat' in the correct order. The receiver prints out packet related information in the order the packets are received, not necessarily their correct order in the file. The receiver closes the connection after receiving the entire file.

The structure of a packet is:

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                        Sequence Number                        |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |           Length              |    Data (up to 1500 bytes)    |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
The sequence number is 32-bit and the packet length is 16-bit, both in network byte order, RFC 1700. The sequence number is similar to a TCP sequence number, RFC 793; it indicates the position of the packet in the file.

You will develop the receiver Recv.java based on the template provided in $CDIR/pub/Recv-template. We have provided a very simple sender which sends the same file in-order and with a fixed delay between packets. You can run this as:

[afsaccess3] $ send 5001   ## waits on port 5001 for any receiver
Note that the simple sender is merely a starting point for your code. We will use a more sophisticated sender to test your receiver. Our sender may send packets out of order and may send duplicate packets. Our sender may place a varying time delay between packets. But our sender will not drop or corrupt any packets. You must write your own sender that does all these things and thoroughly tests your receiver.

Your receiver must meet some conditions. It must not exceed 250 total lines excluding comments. It must use Java 8. You must use only InputStream.read(byte [], int, int) to read packets off the wire. All classes must be imported specifically. Wild cards like import java.net.* are not permitted. Follow all the directions in Recv-template.

Your receiver may not use any wrapper classes or packages. These include but are not limited to String, Byte, Char, Integer, Double, Array, nio, BufferedX, DataInputStream etc. and their derivatives like StringBuffer, CharArray, etc. The java.Math libraries and functions like Math.pow(), and the bit shift/mask operators like <<, >>, & etc. are not permitted. These list/s are not exhaustive so be sure to ASK before you start using a class or package or method. The goal of the assignment is to read raw byte [] data from the wire and process it in raw form. If your code contains any prohibited classes or packages we will not compile your code and you will get zero.

You may use java.util.Date and java.text.SimpleDateFormat to format dates. To sort packets you may use ArrayList.sort() or Collections.sort() with a compareTo() method added in the Packet class.A2 Packets and Delays

This project will explore packets and delays which you studied in Chapter 1. You will develop a packet receiver which will receive the file. Your receiver will be called Recv.java and it will work precisely like this:

[afs2] $ java Recv afsaccess3 5001 data.dat
  connected to 128.235.1.2 on port 5000
  Pkt 1  SEQ=1500, len=1500, delay = 1200 ms
    ...
  Pkt 14 SEQ=10500, len=220, delay = 1401 ms
  Total 14 packets / 20199 bytes recd. Total delay = 17920 ms, average = 1280 ms
The run shows a receiver connecting to a remote host and fetching a bunch of packets. A packet comprises a header and a payload (data). The receiver extracts the payload from each packet and writes it out to the output file 'data.dat' in the correct order. The receiver prints out packet related information in the order the packets are received, not necessarily their correct order in the file. The receiver closes the connection after receiving the entire file.

The structure of a packet is:

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                        Sequence Number                        |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |           Length              |    Data (up to 1500 bytes)    |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
The sequence number is 32-bit and the packet length is 16-bit, both in network byte order, RFC 1700. The sequence number is similar to a TCP sequence number, RFC 793; it indicates the position of the packet in the file.

You will develop the receiver Recv.java based on the template provided in $CDIR/pub/Recv-template. We have provided a very simple sender which sends the same file in-order and with a fixed delay between packets. You can run this as:

[afsaccess3] $ send 5001   ## waits on port 5001 for any receiver
Note that the simple sender is merely a starting point for your code. We will use a more sophisticated sender to test your receiver. Our sender may send packets out of order and may send duplicate packets. Our sender may place a varying time delay between packets. But our sender will not drop or corrupt any packets. You must write your own sender that does all these things and thoroughly tests your receiver.

Your receiver must meet some conditions. It must not exceed 250 total lines excluding comments. It must use Java 8. You must use only InputStream.read(byte [], int, int) to read packets off the wire. All classes must be imported specifically. Wild cards like import java.net.* are not permitted. Follow all the directions in Recv-template.

Your receiver may not use any wrapper classes or packages. These include but are not limited to String, Byte, Char, Integer, Double, Array, nio, BufferedX, DataInputStream etc. and their derivatives like StringBuffer, CharArray, etc. The java.Math libraries and functions like Math.pow(), and the bit shift/mask operators like <<, >>, & etc. are not permitted. These list/s are not exhaustive so be sure to ASK before you start using a class or package or method. The goal of the assignment is to read raw byte [] data from the wire and process it in raw form. If your code contains any prohibited classes or packages we will not compile your code and you will get zero.

You may use java.util.Date and java.text.SimpleDateFormat to format dates. To sort packets you may use ArrayList.sort() or Collections.sort() with a compareTo() method added in the Packet class.
