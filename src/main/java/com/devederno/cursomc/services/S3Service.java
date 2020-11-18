package com.devederno.cursomc.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.devederno.cursomc.services.exeptions.FileException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

@Service
public class S3Service {

  private Logger LOG = LoggerFactory.getLogger(S3Service.class);

  @Autowired
  private AmazonS3 s3;

  @Value("${s3.bucket}")
  private String bucket;

  public URI uploadFile(MultipartFile multipartFile) {
    try {
      String filename = multipartFile.getOriginalFilename();
      InputStream is = multipartFile.getInputStream();
      String contentType = multipartFile.getContentType();
      return uploadFile(is, filename, contentType);
    } catch (IOException e) {
      throw new FileException("Erro de IO: " + e.getMessage());
    }
  }

  public URI uploadFile(InputStream is, String filename, String contentTYpe) {
    try {
      ObjectMetadata meta = new ObjectMetadata();
      meta.setContentType(contentTYpe);
      LOG.info("Iniciando upload");
      s3.putObject(new PutObjectRequest(bucket, filename, is, meta));
      LOG.info("Upload finalizado");
      return s3.getUrl(bucket, filename).toURI();
    } catch (URISyntaxException e) {
      throw new FileException("Erro ao converter URL para URI");
    }
  }
}
