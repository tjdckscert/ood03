/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.cse.spring_webmail.model;

import deu.cse.spring_webmail.Entity.Category;
import deu.cse.spring_webmail.Entity.Inbox;
import deu.cse.spring_webmail.Repository.InboxRepository;
import jakarta.mail.FetchProfile;
import jakarta.mail.Flags;
import jakarta.mail.Folder;
import jakarta.mail.Message;
import jakarta.mail.Session;
import jakarta.mail.Store;
import java.util.List;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author tjdckscert
 */
@Slf4j
@NoArgsConstructor        // 기본 생성자 생성
public class NewMakeTable {
    
    @Autowired
    private InboxRepository inboxrepository;
    // 220612 LJM - added to implement REPLY
   
    public static String makeMainTable(List<Inbox> mailists, int currentPage) {
        StringBuilder buffer = new StringBuilder();
        // 메시지 제목 보여주기
        buffer.append("<table>");  // table start
        buffer.append("<tr> "                
                + " <th> No. </td> "
                + " <th> 보낸 사람 </td>"
                + " <th> 제목 </td>     "
                + " <th> 보낸 날짜 </td>   "
                + " <th> 삭제 </td>   "                
                + " <th> 읽음 여부 </td>   "
                + " </tr>");
        for (int i = (currentPage-1)*7; i < currentPage*7; i++) {
            if (i>=mailists.size()) {
                break;
            }
            Inbox inbox = mailists.get(i);
            String readMark="읽지 않음";
            if (inbox.getIsRead()) readMark="읽음";            
            int a = inbox.getMessageBody().toString().indexOf("Subject: ");
            int b = inbox.getMessageBody().toString().indexOf("MIME-Version: 1.0");    
            String subject = inbox.getMessageBody().toString().substring(a+9, b-2);
            buffer.append("<tr> "
                    + " <td id=no>" + (i + 1) + " </td> "
                    + " <td id=reciver>" + inbox.getRecipients()+"</td>"
                    + " <td id=subject> "
                    + " <a href=show_message?msgid=" + (i + 1) + "&isread="+inbox.getIsRead()+"&mailIndex="+mailists.get(i).getMindex()+" title=\"메일 보기\"> "
                    + subject +"</td>"
                    + " <td id=date>" + inbox.getLastUpdated()+ "</td>"
                    + " <td id=delete>"
                    + "<a href=delete_mail.do"
                    + "?msgid=" + (i + 1) + "> 삭제 </a>" + "</td>"
                    + " <td id=is read>"+ readMark+"</td>"
                    + " </tr>");
        }
        buffer.append("</table>");

        return buffer.toString();
//        return "MessageFormatter 테이블 결과";
    }
    public static String makeIsReadTable(List<Inbox> mailists, int currentPage) {
        StringBuilder buffer = new StringBuilder();
        // 메시지 제목 보여주기
        buffer.append("<table>");  // table start
        buffer.append("<tr> "                
                + " <th> No. </td> "
                + " <th> 받은 사람 </td>"
                + " <th> 제목 </td>     "
                + " <th> 보낸 날짜 </td>   "                
                + " <th> 읽음 여부 </td>   "
                + " </tr>");
        for (int i = (currentPage-1)*7; i < currentPage*7; i++) {
            if (i>=mailists.size()) {
                break;
            }
            Inbox inbox = mailists.get(i);
            String readMark="읽지 않음";
            if (inbox.getIsRead()) readMark="읽음";            
            int a = inbox.getMessageBody().toString().indexOf("Subject: ");
            int b = inbox.getMessageBody().toString().indexOf("MIME-Version: 1.0");    
            String subject = inbox.getMessageBody().toString().substring(a+9, b-2);
            buffer.append("<tr> "
                    + " <td id=no>" + (i + 1) + " </td> "
                    + " <td id=reciver>" + inbox.getRecipients()+"</td>"
                    + " <td id=subject> "                    
                    + subject +"</td>"
                    + " <td id=date>" + inbox.getLastUpdated()+ "</td>"
                    + " <td id=is read>"+ readMark+"</td>"
                    + " </tr>");
        }
        buffer.append("</table>");

        return buffer.toString();
//        return "MessageFormatter 테이블 결과";
    }
    public static String makeCategoryTable(List<Inbox> mailists,  List<String> categorylist) {
        StringBuilder buffer = new StringBuilder();
        // 메시지 제목 보여주기
        buffer.append("<table>");  // table start
        buffer.append("<tr> "                
                + " <th> 보낸 사람 </td>"
                + " <th> 제목 </td>     "
                + " <th> 보낸 날짜 </td>   "
                + " <th> 삭제 </td>   "                
                + " <th> 읽음 여부 </td>   "
                + " </tr>");
        for (int i = 0; i < mailists.size(); i++) {
            if (i>=mailists.size()) {
                break;
            }
            Inbox inbox = mailists.get(i);
            String readMark="읽지 않음";
            if (inbox.getIsRead()) readMark="읽음";            
            int a = inbox.getMessageBody().toString().indexOf("Subject: ");
            int b = inbox.getMessageBody().toString().indexOf("MIME-Version: 1.0");
            String subject = inbox.getMessageBody().toString().substring(a+9, b-2);            
            
            log.info("value = "+String.valueOf(categorylist.contains(subject)));            
            if (categorylist.contains(subject)) {
                    buffer.append("<tr> "                    
                    + " <td id=reciver>" + inbox.getRecipients()+"</td>"
                    + " <td id=subject> "
                    + " <a href=show_message?msgid=" + (i + 1) + "&isread="+inbox.getIsRead()+"&mailIndex="+mailists.get(i).getMindex()+" title=\"메일 보기\"> "
                    + subject +"</td>"
                    + " <td id=date>" + inbox.getLastUpdated()+ "</td>"
                    + " <td id=delete>"
                    + "<a href=delete_mail.do"
                    + "?msgid=" + (i + 1) + "> 삭제 </a>" + "</td>"
                    + " <td id=is read>"+ readMark+"</td>"
                    + " </tr>");
            }
            }
        buffer.append("</table>");
        return buffer.toString();
//        return "MessageFormatter 테이블 결과";
    }
    
    public static String makeCategoryTable(List<Inbox> mailists, String categoryName) {
        StringBuilder buffer = new StringBuilder();
        // 메시지 제목 보여주기
        buffer.append("<table>");  // table start
        buffer.append("<tr> "                
                + " <th> 보낸 사람 </td>"
                + " <th> 제목 </td>     "
                + " <th> 보낸 날짜 </td>   "
                + " <th> 삭제 </td>   "                
                + " <th> 읽음 여부 </td>   "
                + " </tr>");
        for (int i = 0; i < mailists.size(); i++) {
            if (i>=mailists.size()) {
                break;
            }
            Inbox inbox = mailists.get(i);
            String readMark="읽지 않음";
            if (inbox.getIsRead()) readMark="읽음";            
            int a = inbox.getMessageBody().toString().indexOf("Subject: ");
            int b = inbox.getMessageBody().toString().indexOf("MIME-Version: 1.0");
            String subject = inbox.getMessageBody().toString().substring(a+9, b-2);            
            
            log.info("value = "+String.valueOf(categoryName.contains(subject)));            
            if (categoryName.contains(subject)) {
                    buffer.append("<tr> "                    
                    + " <td id=reciver>" + inbox.getRecipients()+"</td>"
                    + " <td id=subject> "
                    + " <a href=show_message?msgid=" + (i + 1) + "&isread="+inbox.getIsRead()+"&mailIndex="+mailists.get(i).getMindex()+" title=\"메일 보기\"> "
                    + subject +"</td>"
                    + " <td id=date>" + inbox.getLastUpdated()+ "</td>"
                    + " <td id=delete>"
                    + "<a href=delete_mail.do"
                    + "?msgid=" + (i + 1) + "> 삭제 </a>" + "</td>"
                    + " <td id=is read>"+ readMark+"</td>"
                    + " </tr>");
            }
            }
        buffer.append("</table>");
        return buffer.toString();
//        return "MessageFormatter 테이블 결과";
    }
}