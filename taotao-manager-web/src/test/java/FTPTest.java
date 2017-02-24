import com.taotao.common.utils.FtpUtil;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created by zh on 2/23/2017.
 */
public class FTPTest {

    @Test
    public void testFtp() throws Exception {
        //1、连接ftp服务器
        FTPClient ftpClient = new FTPClient();
        ftpClient.connect("192.168.25.133", 21);
        //2、登录ftp服务器
        ftpClient.login("ftpuser", "ftpuser");
        //3、读取本地文件
        FileInputStream inputStream = new FileInputStream(new File("D:\\zedosu.jpg"));
        //4、上传文件
        //1）指定上传目录
        ftpClient.changeWorkingDirectory("/home/ftpuser/www/images");
        //2）指定文件类型
        ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
        //第一个参数：文件在远程服务器的名称
        //第二个参数：文件流
        ftpClient.storeFile("hello.jpg", inputStream);
        //5、退出登录
        ftpClient.logout();
    }

    @Test
    public void testFtpUtil() throws Exception{
        FileInputStream inputStream = new FileInputStream(new File("D:\\zedosu.jpg"));
        FtpUtil.uploadFile("192.168.25.133",21,"ftpuser","ftpuser","/home/ftpuser/www/images","/2017/02/23","hello.jpg",inputStream);
    }

}
