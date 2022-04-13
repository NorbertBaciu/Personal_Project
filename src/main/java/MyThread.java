import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

class MyThread implements Runnable
{
    Socket s;
    ArrayList al;
    ArrayList users;
    String username;
    // Implementing Constructor
    MyThread (Socket s, ArrayList al,ArrayList users)
    {
        this.s=s;
        this.al=al;
        this.users=users;
        try{
            // obtain input streams
            DataInputStream dis=new DataInputStream(s.getInputStream());
            username=dis.readUTF();
            al.add(s); //Add in ArrayList the coressponding socket
            users.add(username); //Add in ArrayList coressponding user by username

            //Sow to all users who Logged in and when
            tellEveryOne("****** "+ username+" Logged in at "+(new Date())+" ******");
            sendNewUserList();
        }
        catch(Exception e){System.err.println("MyThread constructor  "+e);}
    }
    ///////////////////////
    public void run()
    {
        String s1;
        try{
            //obtaining input streams
            DataInputStream dis=new DataInputStream(s.getInputStream());
            do
            {    //recieve the string
                s1=dis.readUTF();
                if(s1.toLowerCase().equals(Server.LOGOUT_MESSAGE)) break; //break the program  if string == (Log out message)
                     //	System.out.println("received from "+s.getPort());
                tellEveryOne(username+" said: "+" : "+s1);  // Show to all users the messages we want to share
            }
            while(true);
            // obtaining output streams  and remove and display users who logged out
            DataOutputStream tdos=new DataOutputStream(s.getOutputStream());
            tdos.writeUTF(Server.LOGOUT_MESSAGE);
            tdos.flush();
            users.remove(username);
            tellEveryOne("****** "+username+" Logged out at "+(new Date())+" ******");
            sendNewUserList();
            al.remove(s);
            s.close();

        }
        catch(Exception e){System.out.println("MyThread Run"+e);}
    }
    ////////////////////////
    public void sendNewUserList()
    {
        tellEveryOne(Server.UPDATE_USERS+users.toString());

    }
    ///////////
    //method to display public to everyone the message using a temporary Socket and iteration.

    public void tellEveryOne(String s1) {
        Iterator i = al.iterator();

        while (i.hasNext()) {
            try {
                Socket temp = (Socket) i.next(); //storing in temp Socket variable the next socket in the ArrayLit al
                DataOutputStream dos = new DataOutputStream(temp.getOutputStream());
                dos.writeUTF(s1);
                dos.flush();
                //System.out.println("sent to : "+temp.getPort()+"  : "+ s1);
            } catch (Exception e) {
                System.err.println("TellEveryOne " + e);
            }
        }
    }
///////////////////////
}