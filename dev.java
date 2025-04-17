import com.jcraft.jsch.*;

import java.io.FileInputStream;
import java.io.File;

public class DeploySoftware {

    public static void main(String[] args) {
        String user = "remotemaster";
        String host = "remote.server.com";
        int port = 22;
        String privateKey = "/path/to/private/key";
        String localFile = "/path/to/your/app.jar";
        String remoteDir = "/remote/path/";

        try {
            JSch jsch = new JSch();
            jsch.addIdentity(privateKey);

            Session session = jsch.getSession(user, host, port);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            Channel channel = session.openChannel("sftp");
            channel.connect();

            ChannelSftp sftp = (ChannelSftp) channel;
            sftp.cd(remoteDir);
            sftp.put(new FileInputStream(new File(localFile)), "app.jar");

            System.out.println("Deployment successful!");

            sftp.exit();
            session.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
