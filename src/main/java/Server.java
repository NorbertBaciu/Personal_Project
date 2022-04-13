import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

class Server
{
    ArrayList al=new ArrayList(); //ArrayList to store sockets for different users
    ArrayList users=new ArrayList(); //ArrayList to store active users
    ServerSocket ss;
    Socket s;

    public final static int PORT=10; //set TCP PORT
    public final static String UPDATE_USERS="updateuserslist:";
    public final static String LOGOUT_MESSAGE="@@logoutme@@:";
    public Server()
    {
        try{
            //Server is listening on PORT = 10
            ss=new ServerSocket(PORT);
            System.out.println("Server Started ");

            //Running infinite loop to get client request
            while(true)
            {
                s=ss.accept(); //accepting incoming requests
                Runnable r=new MyThread(s,al,users); //Polymorphism expression
                Thread t=new Thread(r);
                t.start();
                  System.out.println("Total alive clients : "+ss);
            }
        }
        catch(Exception e){System.err.println("Server constructor"+e);}
    }
    /////////////////////////
    public static void main(String [] args)
    {
        new Server();
    }
/////////////////////////
}
/*************************/
