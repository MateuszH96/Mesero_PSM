package Backend.Mail;

import android.content.Context;

public class SendMail {
    public static void sendMailMessage(Context context,String email,String subject,String mess){
        JavaMailAPI javaMailAPI = new JavaMailAPI(context,email,subject,mess);
        javaMailAPI.execute();
    }
}
