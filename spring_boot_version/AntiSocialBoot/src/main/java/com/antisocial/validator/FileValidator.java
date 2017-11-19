package com.antisocial.validator;

import com.antisocial.model.FileUpload;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileValidator implements Validator {

    /**
     * File Upload Validator
     * Accepts only JPEG and PNG Image types lesser than 1048576 bytes.
     *
     *  @author Ant Kaynak - Github/Exercon
     * */

    private final String PNG_MIME_TYPE_JPEG="image/jpeg";
    private final String PNG_MIME_TYPE_PNG="image/png";
    private final long A_MB_IN_BYTES = 1048576;

    @Override
    public boolean supports(Class<?> validClass) {
        return FileUpload.class.isAssignableFrom(validClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {

        FileUpload fileUpload = (FileUpload) obj;
        MultipartFile file = fileUpload.getFile();

        if(file.isEmpty()){
            errors.rejectValue("file", "upload.file.required");
        }
        else if(!PNG_MIME_TYPE_PNG.equalsIgnoreCase(file.getContentType()) && !PNG_MIME_TYPE_JPEG.equalsIgnoreCase(file.getContentType())){
            errors.rejectValue("file", "upload.invalid.file.type");
        }

        else if(file.getSize() > A_MB_IN_BYTES){
            errors.rejectValue("file", "upload.exceeded.file.size");
        }

    }
}