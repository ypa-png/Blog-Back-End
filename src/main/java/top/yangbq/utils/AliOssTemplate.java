package top.yangbq.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


public class AliOssTemplate {

    String endpoint = "oss-cn-hangzhou.aliyuncs.com";
    // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
    String accessKeyId = "LTAI4GJy6f6LuiUtFdBrJjUo";
    String accessKeySecret = "U2vbz29o6xlA95Wc0vjtaPkDFkU1Qi";
    String bucketDomain = "ybq-blog.oss-cn-hangzhou.aliyuncs.com";
    String bucketName = "ybq-blog";

//LTAI4GJy6f6LuiUtFdBrJjUo
//U2vbz29o6xlA95Wc0vjtaPkDFkU1Qi

    /**
     * @MethodName: upload
     * @Description dir参数说明：上传的文件存入那个文件夹
     * @Author yangbq
     * @MethodReturnType java.lang.String
     * @ParameterNames [file , dir]
     * @Date 2021/1/18
     * @Time 16:34
     */
    public String upload ( MultipartFile file , String dir ) throws IOException {
        InputStream inputStream = file.getInputStream ( );
        String fileName = file.getOriginalFilename ( );
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder ( ).build ( endpoint , accessKeyId , accessKeySecret );

        //当前日期文件夹
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat ( "yyyy-MM-dd" );
        String folderName = simpleDateFormat.format ( new Date ( ) );// 2021-01-12

        String fileExt = fileName.substring ( fileName.lastIndexOf ( "." ) );
        fileName = UUID.randomUUID ( ).toString ( ).replaceAll ( "-" , "" ) + fileExt;

        // 上传文件流。
        ossClient.putObject ( bucketName , "pic/" + folderName + "/" + dir + "/" + fileName , inputStream );
        try {
            inputStream.close ( );
        } catch (IOException e) {
            e.printStackTrace ( );
        }
        // 关闭OSSClient。
        ossClient.shutdown ( );

        String url = "https://" + bucketDomain + "/pic/" + folderName + "/" + dir + "/" + fileName;
        return url;
    }
}

