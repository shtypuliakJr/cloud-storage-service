package edu.nau.css.service;

import edu.nau.css.domain.ObjectEntity;
import edu.nau.css.domain.ObjectMetaInfo;
import edu.nau.css.domain.User;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.OutputStream;
import java.util.List;

public interface ExternalDataTransferService {

    ObjectEntity downloadObject(String key);

    StreamingResponseBody downloadMultipleObjects(List<String> keys, User user, OutputStream responseOutputStream);

    List<ObjectMetaInfo> uploadObject(MultipartFile[] files, String path, User user);

}
