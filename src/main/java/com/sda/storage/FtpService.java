package com.sda.storage;

import com.sda.entity.Picture;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class FtpService {

    private String FTP_HOSTNAME="wegiel-torun.pl";
    private String FTP_LOGIN="pizzeria";
    private String FTP_PASSWORD="blaipblaip";

    public FTPClient ftp = null;


    public FtpService() throws Exception {
        ftp = new FTPClient();
        ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
        int reply;
        ftp.connect(FTP_HOSTNAME);
        reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            throw new Exception("Exception in connecting to FTP Server");
        }
        ftp.login(FTP_LOGIN, FTP_PASSWORD);
        ftp.setFileType(FTP.BINARY_FILE_TYPE);
        ftp.enterRemotePassiveMode();
    }

    public Boolean uploadFileToFTP(File inputFile, Picture picture) throws SQLException, ClassNotFoundException, IOException {
        FTPClient client = new FTPClient();
        FileInputStream fis = null;
        client.connect(FTP_HOSTNAME);
        client.login(FTP_LOGIN, FTP_PASSWORD);
        client.enterLocalPassiveMode();
        client.changeWorkingDirectory("/devices_files");
        client.setFileType(FTP.BINARY_FILE_TYPE);

        fis = new FileInputStream(inputFile.getAbsolutePath());
        Boolean result = client.storeFile(picture.getFileName(), fis);
        client.logout();

        return result;
    }


    public void deleteFile(String remoteFilePath) throws IOException {
        this.ftp.deleteFile(remoteFilePath);
    }

    public void disconnect() {
        if (this.ftp.isConnected()) {
            try {
                this.ftp.logout();
                this.ftp.disconnect();
            } catch (IOException f) {
                // do nothing as file is already downloaded from FTP server
            }
        }
    }

}
