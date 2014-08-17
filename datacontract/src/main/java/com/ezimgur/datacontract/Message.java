package com.ezimgur.datacontract;

import com.google.gson.annotations.SerializedName;

/**
 * Created by EggmanProjects.
 * User: matthewharris
 * Date: 12/14/12
 * Time: 8:15 PM
 */
public class Message extends NotificationContent {

    public int id;
    public String from;
    @SerializedName("account_id")
    public int accountId;
    @SerializedName("sender_id")
    public int senderId;
    public String body;
    @SerializedName("conversation_id")
    public int conversationId;
    public long datetime;

    /*
id	integer	The ID for the message
from	string	Account username of person sending the message
account_id	integer	The account ID of the person receiving the message
sender_id	integer	The account ID of the person who sent the message
body	string	Text of the message
conversation_id	integer	ID for the overall conversation
datetime	integer	Time message was sent, epoch time
     */
}
