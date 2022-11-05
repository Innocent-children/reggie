package com.itcast.reggie.controller;


import com.itcast.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.CoyoteOutputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

/**
 * 文件上传和下载
 */
@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {
    @Value("${reggie.path}")
    private String basePath;

    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) {
        //file是一个临时文件，需要转存到指定位置，否则本次请求完成后临时文件会被删除
        log.info(file.toString());
        //上传文件时的原始文件名
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf('.'));
        //使用UUID重新生成文件名防止文件名重复导致文件被覆盖
        String fileName = UUID.randomUUID().toString() + suffix;
        //创建一个目录对象
        File dir = new File(basePath);
        //判断当前目录是否存在，不存在则创建
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            //将临时文件转存到指定位置
            file.transferTo(new File(basePath + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.success(fileName);
    }

    /**
     * 文件下载
     * 上传之后紧接着执行的方法就是下载，就是将本地的图片展示到网页
     * fileInputStream取到了本地的图片，存储到形式是字节数组，非常的长
     * 将这个字节数组先给一个缓存区bytes，再由bytes给servletOutputStream，重复此过程
     * len就是本次送到缓存区的字节长度，本地图片在填满了若干个缓存区后，下一个缓存区就会
     * 有一部分没有任何数值，此时len就会比缓存区数组长度小（小于1024）
     * servletOutputStream.write(bytes, 0, len);
     * 第三个参数就是防止把该缓存区中没数值的后半部分写入到servletOutputStream
     * 再下一个缓存区将会是全0，此时len就会是-1，表示图片读完了也写完了
     *
     * @param name
     * @param httpServletResponse
     */
    @GetMapping("/download")
    public void download(String name, HttpServletResponse httpServletResponse) {
        try {
            //输入流，通过输入流读取文件内容
            FileInputStream fileInputStream = new FileInputStream(new File(basePath + name));
            //输出流，通过输出流将文件写回浏览器，在浏览器展示
            ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
            //设置响应的文件类型为图片
            httpServletResponse.setContentType("image/jpeg");
            /**
             * 若不使用while循环，因为缓存区是有限的且长度小于fileInputStream字节数组长度
             * 只会把本地图片的一部分写入到servletOutputStream
             * 网页端只会展示图片的一部分
             */
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1) {
                servletOutputStream.write(bytes, 0, len);
            }
            //关闭资源
            fileInputStream.close();
            servletOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
