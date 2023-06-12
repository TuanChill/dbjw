package com.ba.dbjw.Utils;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CloudinaryUtil {
    private static void getCloudinary() {
        // Configure
        Map<String, String> config = new HashMap<String, String>();
        config.put("cloud_name", "dzh6qetpl");
        config.put("api_key", "548996969994365");
        config.put("api_secret", "8DiQDY84P9-qA1e7nj8FoJYwRvQ");
        Cloudinary cloudinary = new Cloudinary(config);


        // Upload
        try {
            cloudinary.uploader().upload("https://upload.wikimedia.org/wikipedia/commons/a/ae/Olympic_flag.jpg", ObjectUtils.asMap("public_id", "olympic_flag"));
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }

        // Transform
        String url = cloudinary.url().transformation(new Transformation().width(100).height(150).crop("fill")).generate("olympic_flag");
        System.out.println(url);
    }
}
