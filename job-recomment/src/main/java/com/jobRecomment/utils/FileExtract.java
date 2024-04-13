package com.jobRecomment.utils;

import com.jobRecomment.domain.bean.Resume;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileExtract {

    public static void setPDFFileToText(Resume resume) throws Exception {
        // 文件夹路径
//        String folderPath = System.getProperty("user.home") + "\\AppData\\Roaming\\ideatempfile\\";
        String folderPath = "D:\\data\\";
        // 要写入的文本内容
        String context = fileExtractText(folderPath + resume.getLinkName());
        // 文件路径
        String filePath = folderPath + "data.txt";
        try {
            // 检查文件夹是否存在，如果不存在则创建文件夹
            Path path = Paths.get(folderPath);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
            FileWriter fileWriter = new FileWriter(filePath);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(context);
            bufferedWriter.flush();
            bufferedWriter.close();
//            System.out.println("文本信息已成功写入到文件：" + filePath);
        } catch (IOException e) {
            System.out.println("写入文件时出错：" + e.getMessage());
            e.printStackTrace();
        }
    }
    public static String fileExtractText(String filePath) throws Exception {
        String text;
        if(filePath.isEmpty()){
            return "";
        }
        if(filePath.toLowerCase().endsWith("doc")){
            InputStream file = new FileInputStream(new File(filePath));
            WordExtractor wordExtractor = new WordExtractor(file);
            text = wordExtractor.getText();
            file.close();
            wordExtractor.close();
        }
        else if(filePath.toLowerCase().endsWith("docx")){
            OPCPackage opcPackage = POIXMLDocument.openPackage(filePath);
            POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);
            text = extractor.getText();
            opcPackage.close();
            extractor.close();
        }
        else if(filePath.toLowerCase().endsWith("pdf")){
            PDDocument pdDocument;
            InputStream file = new FileInputStream(new File(filePath));
            pdDocument = PDDocument.load(file);
            PDFTextStripper stripper = new PDFTextStripper();
            text = stripper.getText(pdDocument);
            file.close();
            pdDocument.close();
        }else {
            return "error file";
        }
        return text;
    }
}
