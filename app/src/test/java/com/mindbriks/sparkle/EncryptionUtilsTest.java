package com.mindbriks.sparkle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.mindbriks.sparkle.model.DbUser;
import com.mindbriks.sparkle.model.DrinkingPreference;
import com.mindbriks.sparkle.model.EncryptedDbUser;
import com.mindbriks.sparkle.model.GenderPreference;
import com.mindbriks.sparkle.model.Interest;
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
        DbUser dbUser = new DbUser("123", "Harish Kunta", "harishtanu007@gmail.com", GenderPreference.MAN.name(), 123456789, interestList, "test", "4.2", SmokingPreference.NEVER, DrinkingPreference.OCCASIONALLY);

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
