/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.cse.spring_webmail.model;

import java.util.ArrayList;
//import javax.mail.Message;
import jakarta.mail.Message;
/**
 *
 * @author sooye
 */
public abstract class MessageAgent {
    private boolean needUpdate = false;
    private ArrayList<Integer> msgIdList = new ArrayList();

    public MessageAgent() {
    }
    
    public void printMsgIdInfo() {
        System.out.println("생성된 MSGID SIZE = " + this.getMsgIdSize());
        System.out.print("현재 msgid List = ");
        for (int i = 0; i < msgIdList.size(); ++i) {
            System.out.print(msgIdList.get(i) + " ");
        }
        System.out.print("\n");
    }

    protected boolean isNeedUpdate() {
        return this.needUpdate;
    }

    public void setNeedUpdate(boolean status) {
        this.needUpdate = status;
    }

    protected ArrayList<Integer> getMsgIdList() {
        return this.msgIdList;
    }

    protected void addMsgId(int msgid) {
        this.msgIdList.add(msgid);
    }

    protected void removeMsgId(int msgid) {
        this.msgIdList.remove(Integer.valueOf(msgid));
    }

    protected void updateMsgId(int index, int newMsgId) {
        this.msgIdList.set(index, newMsgId);
    }

    protected int getMsgIdSize() {
        return this.msgIdList.size();
    }

    protected int getMsgIdValue(int index) {
        return (Integer)this.msgIdList.get(index);
    }

    protected void resetMsgIdList() {
        this.msgIdList = null;
        this.msgIdList = new ArrayList();
    }

    protected abstract boolean getMsgIdListFromDB();

    public abstract ArrayList<Message> getMessageList(Message[] messages);   //var1=message, var2=msgIdList

    protected abstract ArrayList<Message> filter(Message[] messages, ArrayList<Integer> msgIdList);

    public abstract void updateMsgId(int msgid);

    public abstract boolean removeMessage(int msgid);

    protected abstract boolean insertMsgId(int msgid);

    protected abstract boolean deleteMsgId(int msgid);
}