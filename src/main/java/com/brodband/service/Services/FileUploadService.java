package com.brodband.service.Services;

import com.brodband.service.DTO.FileUploadReturnDTO;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Map;

@Service @RequiredArgsConstructor
@Transactional
public class FileUploadService {
    public FileUploadReturnDTO uploadFile(MultipartFile file) throws RuntimeException, IOException {
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dzavgoc9w",
                "api_key", "842688657531372",
                "api_secret", "-djtDm1NRXVtjZ3L-HGaLfYnNBw",
                "secure", true));
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        return new FileUploadReturnDTO(uploadResult.get("url").toString(), uploadResult.get("public_id").toString());
    }

    public FileUploadReturnDTO deleteAndUploadFile(String publicId,MultipartFile file) throws RuntimeException, IOException {
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dzavgoc9w",
                "api_key", "842688657531372",
                "api_secret", "-djtDm1NRXVtjZ3L-HGaLfYnNBw",
                "secure", true));
        cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());

        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        return new FileUploadReturnDTO(uploadResult.get("url").toString(), uploadResult.get("public_id").toString());
    }

}


