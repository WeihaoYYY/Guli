package com.Guli.oss.service;

import org.springframework.web.multipart.MultipartFile;

public interface OSSService {

    String uploadFileAvatar(MultipartFile file);
}
