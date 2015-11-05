package org.team10424102.whisky.models.mapping;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import org.team10424102.whisky.models.Profile;

import java.io.IOException;

/**
 * Created by yy on 11/8/15.
 */
public class ProfileDeserializer extends JsonDeserializer<Profile> {
    @Override
    public Profile deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
//        JsonNode root = jp.getCodec().readTree(jp);
//        JsonNode profile = root.get("profile");
//        String phone = root.get("phone").asText();
//        String username = profile.get("username").asText();
//        String email = root.get("email").asText();
//        JsonNode birthdayNode = profile.get("birthday");
//        LocalDate birthday = LocalDate.of(birthdayNode.get(0).asInt(),
//                birthdayNode.get(1).asInt(), birthdayNode.get(2).asInt());
//        String college = profile.get("college").get("name").asText();
//        String academy = profile.get("academy").get("name").asText();
//        String grade = profile.get("grade").asText();
//        String signature = profile.get("signature").asText();
//        String hometown = profile.get("hometown").asText();
//        String highschool = profile.get("highschool").asText();
//        Profile p = new Profile();
//        p.setPhone(phone);
//        p.setUsername(username);
//        p.setEmail(email);
//        p.setBirthday(birthday);
//        p.setCollege(college);
//        p.setAcademy(academy);
//        p.setGrade(grade);
//        p.setSignature(signature);
//        p.setHometown(hometown);
//        p.setHighschool(highschool);
//        return p;
        return null;
    }
}
