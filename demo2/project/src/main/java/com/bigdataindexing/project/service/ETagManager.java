package com.bigdataindexing.project.service;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class ETagManager {

    public String getETag(JSONObject json) {

        String encoded=null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(json.toString().getBytes(StandardCharsets.UTF_8));
            encoded = Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "\""+encoded+"\"";
    }

    public boolean verifyETag(JSONObject json, List<String> etags) {
        if(etags.isEmpty())
            return false;
        String encoded=getETag(json);
        return etags.contains(encoded);

    }
}
