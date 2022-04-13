import java.io.DataInputStream;
import java.util.StringTokenizer;
import java.util.Vector;

class ClientThread implements Runnable
{
    DataInputStream dis;
    Client client;

    ClientThread(DataInputStream dis,Client client)
    {
        this.dis=dis;
        this.client=client;
    }
    //method to send and broadcast the message for all the users connected to this server
    public void run()
    {
        String s2="";
        do
        {
            try{
                s2=dis.readUTF();
                if(s2.startsWith(Server.UPDATE_USERS))
                    updateUsersList(s2);
                else if(s2.equals(Server.LOGOUT_MESSAGE))
                    break;
                else
                    client.txtBroadcast.append("\n"+s2);
                int lineOffset=client.txtBroadcast.getLineStartOffset(client.txtBroadcast.getLineCount()-1);
                client.txtBroadcast.setCaretPosition(lineOffset);
            }
            catch(Exception e){client.txtBroadcast.append("\nClientThread run : "+e);}
        }
        while(true);
    }
    // Method to update the user list when a user has connected(logged in) and delimit the username with the message
    public void updateUsersList(String ul)
    {
        Vector ulist=new Vector();

        ul=ul.replace("[","");
        ul=ul.replace("]","");
        ul=ul.replace(Server.UPDATE_USERS,"");
        StringTokenizer st=new StringTokenizer(ul,",");

        while(st.hasMoreTokens())
        {
            String temp=st.nextToken();
            ulist.add(temp);
        }
        client.usersList.setListData(ulist);
    }
/////////////////////////
}
/*********************************/
