package com.jobRecomment;

import com.jobRecomment.domain.bean.Resume;
import com.jobRecomment.utils.FileExtract;
//import com.jobRecomment.utils.PdfFileUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

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
}
