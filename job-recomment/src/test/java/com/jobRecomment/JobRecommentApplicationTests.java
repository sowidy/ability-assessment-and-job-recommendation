package com.jobRecomment;

import com.jobRecomment.domain.bean.Resume;
import com.jobRecomment.utils.FileExtract;
//import com.jobRecomment.utils.PdfFileUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootTest
class JobRecommentApplicationTests {

    @Test
    void contextLoads() throws IOException {
//        PdfFileUtil.getPdfFileText("C:\\Users\\黄小洪\\AppData\\Roaming\\ideatempfile\\84e89ba5-d443-410b-be41-8ffbcc2600a5.docx");
    }
    @Test
    public void FileExtractTest() throws Exception {
//        String filePath = "C:\\Users\\黄小洪\\AppData\\Roaming\\ideatempfile\\84e89ba5-d443-410b-be41-8ffbcc2600a5.docx";
//        FileExtract fileExtract = new FileExtract();
//        String text = null;
//        try {
//            text = fileExtract.fileExtractText(filePath);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println(text);
        Resume resume = new Resume();
        resume.setLinkName("84e89ba5-d443-410b-be41-8ffbcc2600a5.docx");
        FileExtract.setPDFFileToText(resume);
    }
    @Test
    public void test1(){
        String currentWorkingDirectory = System.getProperty("user.dir");

        // 使用 Paths.get() 方法将当前工作目录转换为 Path 对象
        Path currentPath = Paths.get(currentWorkingDirectory);

        // 获取上一级目录
        Path parentPath = currentPath.getParent();

        // 打印上一级目录的路径
        if (parentPath != null) {
            System.out.println("上一级目录: " + parentPath.toString());
        } else {
            System.out.println("无法找到上一级目录");
        }
    }
}
