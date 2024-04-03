package com.jobRecomment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jobRecomment.domain.bean.Resume;

/**
 * ClassName: IResumeService
 * Package: com.jobRecomment.service
 * Description:
 * Author by: HUANG
 * Created by: 2024/3/17 11:13
 * Version 1.0
 */
public interface IResumeService  extends IService<Resume> {

    Resume getResume();

    void addResume(Resume resume);

    void deleteResume();
}
