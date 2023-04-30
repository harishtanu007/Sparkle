package com.mindbriks.sparkle;

import static org.junit.Assert.assertTrue;

import com.mindbriks.sparkle.model.DbUser;
import com.mindbriks.sparkle.model.DrinkingPreference;
import com.mindbriks.sparkle.model.EncryptedDbUser;
import com.mindbriks.sparkle.model.GenderPreference;
import com.mindbriks.sparkle.model.Interest;
import com.mindbriks.sparkle.model.Location;
import com.mindbriks.sparkle.model.SmokingPreference;
import com.mindbriks.sparkle.utils.EncryptionUtils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class EncryptionUtilsTest {
    private static final String SAMPLE_STRING = "This is a sample string";
    private static final String SAMPLE_KEY = "1234567890123456"; // 16-byte key

    @Test
    public void testEncryptionDecryption() {
        List<Interest> interestList = getInterests();

        DbUser.Builder builder = new DbUser.Builder();
        builder.id("123");
        builder.name("Harish Kunta");
        builder.email("harishtanu007@gmail.com");
        builder.gender(GenderPreference.MAN.name());
        builder.dob(123456789);
        builder.interests(interestList);
        builder.profile_image("test");
        builder.height("4.2");
        builder.smoke_preference(SmokingPreference.NEVER);
        builder.drinking_preference(DrinkingPreference.OCCASIONALLY);
        builder.location(new Location(0, 0));
        builder.encrypted(true);
        DbUser dbUser = builder.build();

        EncryptedDbUser encrypted = EncryptionUtils.encryptUser(dbUser, SAMPLE_KEY);
        DbUser decryptedDbUser = EncryptionUtils.decryptUser(encrypted, SAMPLE_KEY);
        assertTrue(dbUser.equals(decryptedDbUser));
    }

    private List<Interest> getInterests() {
        List<Interest> interestList = new ArrayList<>();

        interestList.add(new Interest("Sports"));
        interestList.add(new Interest("Movies"));
        interestList.add(new Interest("Music"));
        interestList.add(new Interest("Hiking"));
        interestList.add(new Interest("Fishing"));
        interestList.add(new Interest("Singing"));

        return interestList;
    }
}
