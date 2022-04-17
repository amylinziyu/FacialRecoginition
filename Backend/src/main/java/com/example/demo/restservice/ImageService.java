package com.example.demo.restservice;

import com.example.demo.db.DatabaseRepository;
import com.example.demo.db.Image;
import com.example.demo.db.Person;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.util.Base64;
import java.util.Random;
import java.util.UUID;

@CrossOrigin(origins = "http://frontend.westus2.azurecontainer.io")
@RestController
public class ImageService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    DatabaseRepository dbRepo;

    @RequestMapping(value = "/image", method = RequestMethod.POST)
    public ResponseEntity<String> posting(@RequestBody String data) throws IOException {
        String base64 = data.replace("data:image/png;base64,", "");
        byte[] decode = Base64.getDecoder().decode(base64);

        String imageName = UUID.randomUUID() + ".png";
        saveImageToFile(decode, imageName);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_OCTET_STREAM_VALUE);
        headers.add("Training-Key", "5a585b5fabb24dddbe89f6406afbf71a");

        HttpEntity<byte[]> entity = new HttpEntity<>(decode, headers);
        String URL = "https://westus2.api.cognitive.microsoft.com/customvision/v3.3/training/projects/cd621fcb-704f-48ab-bdb0-ad1e4e72e2b2/images?tagIds=b3ce2a88-cbad-4fcb-916c-8a5513768359";
        //ResponseEntity<String> result = restTemplate.postForEntity(URL, entity, String.class);

        ResponseEntity<String> result = new ResponseEntity<String>(HttpStatus.OK);

        Image image = new Image(123, decode);
        /*
        image.setImage(decode);
        image.setUserID(123);
        */

        dbRepo.saveImage(image);

        return result;

    }


    // Prediction
    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    public ResponseEntity<String> validate(@RequestBody String data) throws IOException {
        String base64 = data.replace("data:image/png;base64,", "");
        byte[] decode = Base64.getDecoder().decode(base64);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/octet-stream");
        headers.add("Prediction-Key", "5a585b5fabb24dddbe89f6406afbf71a");

        HttpEntity<byte[]> entity = new HttpEntity<>(decode, headers);
        String URL = "https://westus2.api.cognitive.microsoft.com/customvision/v3.0/Prediction/cd621fcb-704f-48ab-bdb0-ad1e4e72e2b2/classify/iterations/Facial_Recognition/image";
        ResponseEntity<String> result = restTemplate.postForEntity(URL, entity, String.class);
        return result;
    }


    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public ResponseEntity<String> postForm(@RequestBody String data) throws JSONException {
        JSONObject jsonObject= new JSONObject(data);
        String name = jsonObject.getString("name");
        String email = jsonObject.getString("email");
        String base64 = jsonObject.getString("image").replace("data:image/png;base64,", "");
        byte[] decode = Base64.getDecoder().decode(base64);

        Random rand = new Random();
        int userId = rand.nextInt();

        Image image = new Image(userId, decode);
        /*
        image.setImage(decode);
        image.setUserID(userId);*/
        Person user = new Person(userId, name, email);
       /*
        user.setUserID(userId);
        user.setFullName(name);
        user.setEmail(email); */
        dbRepo.savePerson(user);
        dbRepo.saveImage(image);


        ResponseEntity<String> result = new ResponseEntity<String>(HttpStatus.OK);

        return result;

    }

    @GetMapping("/image/{userId}")
    ResponseEntity<Image> getImage(@PathVariable Integer userId) throws IOException {

        Image image = dbRepo.getImage(userId);

        String imageName = image.getUserID() + ".png";

        saveImageToFile(image.getImage(), imageName);

        return new ResponseEntity<Image>(image, HttpStatus.OK);

    }

    private void saveImageToFile(byte[] image, String imageName) throws IOException {
        if (new File("/images/").exists()) {
            Files.write(new File("/images/" + imageName).toPath(), image);
        }
        else {
            new File("/images/").mkdir();
            Files.write(new File("/images/" + imageName).toPath(), image);
        }
    }

}