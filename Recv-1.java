/*
 * CS 656 Spring 2022
 * RECV TEMPLATE V 1.00
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.InetSocketAddress;
import java.util.Comparator;

/* no additional imports permitted UNLESS you have
   instructor permission
*/

/*
 Rules
 1. Do not change any existing field or method signature
 2. You may add code where indicated
 2. You may add your own private fields and local vars
    and private methods
 3. Do not change main()
*/

public class Recv {
  private Socket       socket = null;
  private InputStream  inputStream = null;
  private List<Packet> pList = new ArrayList<Packet>();

 public Recv(String host, int port) throws IOException {
   socket = new Socket();
   socket.connect( new InetSocketAddress(host,port) );
   System.out.println("Connetion successful!");
 }

 public void close() throws IOException {
   socket.close();
 }

 /* should not need to change this */
 public int run(String fname) {
   try {
     get_pkts();
     order_pkts();
     write_pkts(fname);
   } catch (Exception e) { e.printStackTrace(); }

   return 0;
 }

 /* get_pkts: pull all packets off the wire and store in pList
    this method also prints the packet info stats              */
 public int get_pkts ( ) throws IOException {
   int totalLen = 0;
   long total_time = 0;
   long time = 0;
   long start_time = System.nanoTime();
   byte[] a = new byte[1506];
   int npackets = 0;  // how many packets read
   /* loop: get all packets and capture stats
      must use getInputStream.read()          */  
     while(socket.getInputStream().read(a,0,1506) != -1) { // writes 1506 bytes from socket into a byte array
            Packet packet = new Packet(a); // create the packet
            pList.add(packet); // add packet to list
            totalLen += packet.len(); // keep track of the total length of all packets
            time = (System.nanoTime() - start_time)/1000000; // keep track of the delay between each packet
            total_time += time; // keep track of the total amount of time taken
            packet.print(time); // prints packet info
            start_time = System.nanoTime(); 
            npackets++; // keeps track of number of packets
     }
   npackets = pList.size(); // gets the size of packet list
   System.out.print("npackets=" + npackets);
   System.out.print("\tTotal size=" + totalLen + " bytes"); 
   System.out.print("\tTotal delay=" + total_time + "s");       // prints the number of packets, total time, and the avg delay
   System.out.println("\tAverage delay=" + (total_time/npackets) + "s");

  return npackets;
 }

 public void write_pkts(String f) throws Exception {
  FileOutputStream a = new FileOutputStream(f);
  for (Packet i : pList){                                // writes each packet in the data.dat file
    i.write(a);
  }
 }

 // put the pList in the correct order
 public void order_pkts() {
   pList.sort(Comparator.comparingInt(Packet::seqNo)); // sorts the list using a comparator method, by comparing the int value of the seq No. 
   int n = pList.size();
   int num = 1;
   for(int i = 0;i<n-1;i++){                           // bubble sort method used to eliminate any duplicate packets
     for (int j = 0; j<n-i-1;j++){
       if (pList.get(j).seqNo() == pList.get(j+1).seqNo()){
         pList.remove(j+1);
         n--; // used to reduce the size of the list when a packet is removed from it
       }
     }
   }
   System.out.println("------------------------------------------ Final pList ------------------------------------------");
   for (Packet p: pList){
     System.out.println("seqNo=" + p.seqNo() + "\tlen=" + p.len() + "\tpacket num=" + num);              // prints the filtered and sorted list of packets
     num++;
   }
 }

 // DO_NOT change main at all! String OK here
 public static void main(String[] args) {
   if(args.length != 3) {
     System.out.println("Usage: host  port filename");
     return;
   }

   try {
     Recv recv = new Recv( args[0],
                         Integer.parseInt(args[1]));
		recv.run ( args[2] );
   } catch (Exception e) { e.printStackTrace(); }
 } // main()

} // class Recv

/* Packet class */
class Packet{
 /* DO_NOT change these private fields */
 private byte[]      payload;
 private int         seqNo;
 private short       len;
 private PrintStream tty;

 /* this constructor is used to make a packet from
    a buffer that came off the wire         */
 public Packet(byte[] buf) {

  seqNo = get_seqno(buf); // must use only this method
  len   = get_len(buf);   // must use only this method
  payload = get_payload(buf);

  // ADD code here

 } // Packet CTOR

 private int get_seqno(byte []b) {
  int b0 = (b[0]<0 ? (b[0]+256) : b[0])*16777216;
  int b1 = (b[1]<0 ? (b[1]+256) : b[1])*665536;
  int b2 = (b[2]<0 ? (b[2]+256) : b[2])*256;                        // converts 4 bytes to a 32-bit integer. We shift each subsequent byte by multiplying with a power of 256,
  int b3 = (b[3]<0 ? (b[3]+256) : b[3]);                            // for 1 byte shift its 256^1 and so on. in the parentheses, we are checking if the byte is negative, if it is 
  return  b0 + b1 + b2 + b3;                                        // we add 256 to it
    // add code here
    // seqNo is first 4 bytes of packet, after send is performed, store first four bytes into "seqNo".
 }

 private short get_len(byte []b) {
  int b4 = (b[4]<0 ? (b[4]+256) : b[4])*256;
  int b5 = (b[5]<0 ? (b[5]+256) : b[5]);                             // same process as seqNo, just for the 4th and 5th byte
  return  (short)(b4 + b5);
 }
 
 private byte[] get_payload(byte []b) {
     byte[] payload = new byte[len];
     for (int i = 6; i < len+6; i++) {                              // We esentially return a byte array without the seqNo and len( the first 6 bytes)
         payload[i-6]= b[i];
     }
     return payload;
 }

  public int seqNo(){
    return seqNo;
  }
  
  public int len(){
    return len;
  }
    
 // write this Packet to file: no need to change this
 public void write(FileOutputStream f) throws IOException {
   f.write(payload);
 }
 
 public int print(long delay){
   System.out.println("SEQ=" + seqNo + ",  LEN=" + len + ", delay=" + delay + " ms"); // prints the info of the packet when called
   return 0;
 }

}  // class Packet
