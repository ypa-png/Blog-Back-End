package top.yangbq.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.yangbq.pojo.ArticleImage;
import top.yangbq.utils.IdWork;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
public class FileUploadController {

    @Value ( "${imgServiceUrl}" )
    private String imgServiceUrl;
    @Value ( "${locationTemp}" )
    private String locationTemp;

    @PostMapping ( value = "/upload/articleImgUpload" )
    public Map < String, Object > articleImgUpload ( MultipartFile file ) {
        Map < String, Object > map = new HashMap <> ( );

        String rootPath = locationTemp;

        String OriginalFilename = file.getOriginalFilename ( );
        String ext = OriginalFilename.substring ( OriginalFilename.lastIndexOf ( "." ) );

        String newFileName = "article_" + IdWork.getId ( ) + System.currentTimeMillis ( ) + ext;

        File FilePath = new File ( rootPath , File.separator + "article" + File.separator + "content" + File.separator + newFileName );

        File FileDir = new File ( rootPath , File.separator + "article" + File.separator + "content" );

        if (!FileDir.exists ( )) {
            FileDir.mkdirs ( );
        }

        try {
            file.transferTo ( FilePath );
            map.put ( "state" , true );
            String url=imgServiceUrl + File.separator + "article" + File.separator + "content" + File.separator + newFileName;
            url=url.replaceAll ( "\\\\","/" );
            map.put ( "url" , url );
        } catch (IOException e) {
            e.printStackTrace ( );
            map.put ( "state" , false );
            map.put ( "msg" , e.getMessage ( ) );
        }

        return map;
    }

    @PostMapping ( value = "/upload/commentImgUpload" )
    public Map < String, Object > commentImgUpload ( MultipartFile file ) {
        Map < String, Object > map = new HashMap <> ( );

        String rootPath = locationTemp;

        String OriginalFilename = file.getOriginalFilename ( );
        String ext = OriginalFilename.substring ( OriginalFilename.lastIndexOf ( "." ) );

        String newFileName = "article_" + IdWork.getId ( ) + System.currentTimeMillis ( ) + ext;

        File FilePath = new File ( rootPath , File.separator + "article" + File.separator + "comment" + File.separator + newFileName );

        File FileDir = new File ( rootPath , File.separator + "article" + File.separator + "comment" );

        if (!FileDir.exists ( )) {
            FileDir.mkdirs ( );
        }

        try {
            file.transferTo ( FilePath );
            map.put ( "state" , true );
            String url=imgServiceUrl + File.separator + "article" + File.separator + "comment" + File.separator + newFileName;
            url=url.replaceAll ( "\\\\","/" );
            map.put ( "url" , url );
        } catch (IOException e) {
            e.printStackTrace ( );
            map.put ( "state" , false );
            map.put ( "msg" , e.getMessage ( ) );
        }

        return map;
    }


    @PostMapping ( "/upload/delete" )
    public Map < String, Object > articleImgUpload (  String url ) {
        String fileName=url.substring ( url.lastIndexOf ( "/" )+1 );
        Map < String, Object > map = new HashMap <> ( );
        File FileDir = new File ( locationTemp , File.separator + "article" + File.separator + "content" );
        boolean delete = new File ( FileDir , fileName ).delete ( );
        if (delete) {
            map.put ( "state" , true );
            map.put ( "msg" , "图片已删除" );
        } else {
            map.put ( "state" , false );
            map.put ( "msg" , "图片未删除" );
        }
        return map;
    }



}
