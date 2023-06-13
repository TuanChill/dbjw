package com.ba.dbjw.Utils;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CloudinaryUtil {
    private static Cloudinary getCloudinary() {
        // Configure
        Map<String, String> config = new HashMap<String, String>();
        config.put("cloud_name", "dzh6qetpl");
        config.put("api_key", "548996969994365");
        config.put("api_secret", "8DiQDY84P9-qA1e7nj8FoJYwRvQ");
        return new Cloudinary(config);
    }


    public static String uploadImgToCloudinary(String file) {

        // Upload
        try {
            Map<String,String> uploadResult = getCloudinary().uploader().upload(file, ObjectUtils.asMap("public_id", "olympic_flag"));
            return uploadResult.get("url");
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
            return null;
        }
    }
}
