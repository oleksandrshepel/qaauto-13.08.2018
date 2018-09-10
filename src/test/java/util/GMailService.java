package util;


import com.sun.mail.imap.IdleManager;

import javax.mail.*;
import javax.mail.event.MessageCountAdapter;
import javax.mail.event.MessageCountEvent;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GMailService {
    String host = "imap.gmail.com";
    String user;
    String pass;

    private volatile boolean isMessageReceived;
    private volatile String messageString;

    private Folder inboxFolder;
    private IdleManager idleManager;

    /**
     * Example of util.GMailService usage
     * @param args - dummy args for main()
     */
    public static void main(String[] args) {
        String messageSubject = "Lesson 10";
        String messageTo = "postoltest@gmail.com";
        String messageFrom = "mykola.gladchenko@gmail.com";

        GMailService gMailService = new GMailService();
        gMailService.connect();
        String message = gMailService.waitMessage(messageSubject, messageTo, messageFrom, 60);
        System.out.println("Content: " + message);
    }

    /**
     * Default util.GMailService constructor with predefined user/pass credentials
     */
    public GMailService(){
        this.user = "qaauto13082018@gmail.com";
        this.pass = "COPYC@2t";
    }

    /**
     * Custom util.GMailService constructor that allows to set user/pass credentials
     * @param user - gMail acc username
     * @param pass - gMail acc pass
     */
    public GMailService(String user, String pass){
        this.user = user;
        this.pass = pass;
    }

    public synchronized void connect() {
        Properties properties = new Properties();
        properties.put("mail.imap.usesocketchannels", "true");
        properties.put("mail.imap.ssl.enable", "true");
        properties.put("mail.store.protocol", "imap");

        try {
            Session session = Session.getInstance(properties);
            Store store = session.getStore();
            try {
                store.connect(host, user, pass);
            } catch (AuthenticationFailedException e) {
                throw new Exception("Make sure 'Allow less secure apps' is 'ON' on gmail account here "
                        + "https://myaccount.google.com/lesssecureapps" + "\n" + e.getMessage());
            }

            ExecutorService executorService = Executors.newCachedThreadPool();
            idleManager = new IdleManager(session, executorService);

            inboxFolder = store.getFolder("inbox");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String waitMessage(final String messageSubject, final String messageTo, final String messageFrom,
                              long timeoutInSec){
        try {
            inboxFolder.open(Folder.READ_WRITE);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        inboxFolder.addMessageCountListener(new MessageCountAdapter() {
            public synchronized void messagesAdded(MessageCountEvent ev) {

                try {
                    Message[] messages = ev.getMessages();
                    for (Message message : messages) {
                        String from = message.getFrom()[0].toString();
                        String to = message.getAllRecipients()[0].toString();
                        String subject = message.getSubject().toString();

                        if (from.contains(messageFrom) && to.contains(messageTo) && subject.contains(messageSubject)) {
                            isMessageReceived = true;
                            messageString = getText(message);
                            idleManager.stop();
                        }
                    }

                } catch (MessagingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        try {
            idleManager.watch(inboxFolder);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        long startTime = System.currentTimeMillis();
        while (true) {
            if (isMessageReceived && messageString != null) {
                break;
            } else if ((System.currentTimeMillis() - startTime) > timeoutInSec * 1000) {
                idleManager.stop();
                break;
            }
        }

        return messageString;
    }

    private boolean textIsHtml = false;

    /**
     * Return the primary text content of the message.
     */
    private String getText(Part p) throws
            MessagingException, IOException {
        if (p.isMimeType("text/*")) {
            String s = (String)p.getContent();
            textIsHtml = p.isMimeType("text/html");
            return s;
        }

        if (p.isMimeType("multipart/alternative")) {
            // prefer html text over plain text
            Multipart mp = (Multipart)p.getContent();
            String text = null;
            for (int i = 0; i < mp.getCount(); i++) {
                Part bp = mp.getBodyPart(i);
                if (bp.isMimeType("text/plain")) {
                    if (text == null)
                        text = getText(bp);
                    continue;
                } else if (bp.isMimeType("text/html")) {
                    String s = getText(bp);
                    if (s != null)
                        return s;
                } else {
                    return getText(bp);
                }
            }
            return text;
        } else if (p.isMimeType("multipart/*")) {
            Multipart mp = (Multipart)p.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                String s = getText(mp.getBodyPart(i));
                if (s != null)
                    return s;
            }
        }

        return null;
    }

}