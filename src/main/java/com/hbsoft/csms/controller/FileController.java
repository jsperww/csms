package com.hbsoft.csms.controller;

import com.google.gson.Gson;
import com.hb.bean.CallResult;
import com.hb.bean.FileValue;
import com.hb.util.FileUtil;
import com.hb.util.StringUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * ClassName:    FileController
 * Package:    com.hbsoft.csms.controller
 * Description:
 * Datetime:    2020/4/7   16:47
 * Author:  hwl
 */
@RestController
@RequestMapping(value = "", produces = "application/json;charset=UTF-8")
public class FileController {

    //@PostMapping("uploadFile")
    public String upload(MultipartFile fileName)  {
        CallResult<FileValue> result = new CallResult<FileValue>();
        try {
            if (fileName != null) {
                // 获取源文件名
                String name = fileName.getOriginalFilename();
                // 获取文件大小
                String size = StringUtil.getPrintSize(fileName.getSize());
                CallResult<FileValue> resultFile = FileUtil.getUploadPath(name);
                if (resultFile.getCode() == 0) {
                    FileValue fileValue = resultFile.getData();
                    File file = fileValue.getFile();
                    FileUtil.createFolders(file);
                    fileName.transferTo(file);
                    result.setSuccessResult("上传成功");
                    FileValue newFileValue = new FileValue();
                    newFileValue.setFileName(fileValue.getFileName());
                    newFileValue.setFileNewName(fileValue.getFileNewName());
                    newFileValue.setPath(fileValue.getPath());
                    newFileValue.setSize(size);
                    result.setData(newFileValue);
                } else {
                    result.setCode(resultFile.getCode());
                    result.setMsg(resultFile.getMsg());
                }
            } else {
                result.setFailResult("上传文件为空");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Gson().toJson(result);
    }
}
