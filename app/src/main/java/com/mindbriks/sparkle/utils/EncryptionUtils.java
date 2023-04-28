package com.mindbriks.sparkle.utils;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.mindbriks.sparkle.model.DbUser;
import com.mindbriks.sparkle.model.DrinkingPreference;
import com.mindbriks.sparkle.model.EncryptedDbUser;
import com.mindbriks.sparkle.model.Interest;
import com.mindbriks.sparkle.model.Location;
import com.mindbriks.sparkle.model.SmokingPreference;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionUtils {
    private static final String SECRET_KEY_ALGORITHM = "AES";
    private static final String UTF_8 = "UTF-8";

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";

    static int keyLength = 128;

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static EncryptedDbUser encryptUser(DbUser user, String firebaseUserId) {
        try {
            //Key secretKey = new SecretKeySpec(firebaseUserId.getBytes(), SECRET_KEY_ALGORITHM);
            Key secretKey = getPasswordBasedKey(SECRET_KEY_ALGORITHM, keyLength, firebaseUserId);
            Cipher cipher = Cipher.getInstance(ALGORITHM, "BC");
            byte[] iv = new byte[cipher.getBlockSize()];
            IvParameterSpec ivParams = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParams);

            String idEncrypted = encryptField(cipher, user.getId());
            String nameEncrypted = user.getName();
            String emailEncrypted = encryptField(cipher, user.getEmail());
            String genderEncrypted = encryptField(cipher, user.getGender());
            String dobEncrypted = encryptField(cipher, user.getDob());

            List<Interest> encryptedInterests = new ArrayList<>();
            for (Interest interest : user.getInterests()) {
                encryptedInterests.add(new Interest(encryptField(cipher, interest.getName())));
            }

            String profileImageEncrypted = user.getProfile_image();
            String heightEncrypted = user.getHeight();
            String smokingPreferenceEncrypted = encryptField(cipher, user.getSmoke_preference());
            String drinkingPreferenceEncrypted = encryptField(cipher, user.getDrinking_preference());
            String locationEncrypted = encryptField(cipher, user.getLocation());
            return new EncryptedDbUser(idEncrypted, nameEncrypted, emailEncrypted, genderEncrypted, dobEncrypted, encryptedInterests, profileImageEncrypted, heightEncrypted, smokingPreferenceEncrypted, drinkingPreferenceEncrypted, locationEncrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Key getPasswordBasedKey(String cipher, int keySize, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] keyBytes = password.getBytes(StandardCharsets.UTF_8);
        byte[] hashedBytes = sha.digest(keyBytes);
        byte[] truncatedBytes = new byte[16];
        System.arraycopy(hashedBytes, 0, truncatedBytes, 0, 16);
        return new SecretKeySpec(truncatedBytes, "AES");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static DbUser decryptUser(EncryptedDbUser encryptedDbUser, String firebaseUserId) {
        try {
            Key secretKey = getPasswordBasedKey(SECRET_KEY_ALGORITHM, keyLength, firebaseUserId);
            Cipher cipher = Cipher.getInstance(ALGORITHM, "BC");
            byte[] iv = new byte[cipher.getBlockSize()];
            IvParameterSpec ivParams = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParams);

            String id = (String) decryptField(cipher, encryptedDbUser.getId(), String.class);
            String name = encryptedDbUser.getName();
            String email = (String) decryptField(cipher, encryptedDbUser.getEmail(), String.class);
            String gender = (String) decryptField(cipher, encryptedDbUser.getGender(), String.class);
            long dob = (long) decryptField(cipher, encryptedDbUser.getDob(), long.class);

            List<Interest> decryptedInterests = new ArrayList<>();
            for (Interest interest : encryptedDbUser.getInterests()) {
                String decryptedInterestName = (String) decryptField(cipher, interest.getName(), String.class);
                decryptedInterests.add(new Interest(decryptedInterestName));
            }

            String profileImage = encryptedDbUser.getProfile_image();
            String height = encryptedDbUser.getHeight();
            SmokingPreference smokingPreference = (SmokingPreference) decryptField(cipher, encryptedDbUser.getSmoke_preference(), SmokingPreference.class);
            DrinkingPreference drinkingPreference = (DrinkingPreference) decryptField(cipher, encryptedDbUser.getDrinking_preference(), DrinkingPreference.class);
            Location location = (Location) decryptField(cipher, encryptedDbUser.getLocation(), Location.class);

            DbUser dbUser = new DbUser(id, name, email, gender, dob, decryptedInterests, profileImage, height, smokingPreference, drinkingPreference, location);
            return dbUser;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Helper method to encrypt a single field
    @RequiresApi(api = Build.VERSION_CODES.O)
    private static String encryptField(Cipher cipher, Object field) throws IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        if (field == null) {
            return null;
        }
        if (field instanceof String) {
            byte[] encryptedValue = cipher.doFinal(((String) field).getBytes(UTF_8));
            return Base64.getEncoder().encodeToString(encryptedValue);
        } else if (field instanceof Long) {
            byte[] encryptedValue = cipher.doFinal(String.valueOf(field).getBytes(UTF_8));
            return Base64.getEncoder().encodeToString(encryptedValue);
        } else if (field instanceof Enum) {
            byte[] encryptedValue = cipher.doFinal(((Enum<?>) field).name().getBytes(UTF_8));
            return Base64.getEncoder().encodeToString(encryptedValue);
        } else {
            throw new IllegalArgumentException("Unsupported data type for encryption: " + field.getClass());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static String encryptLongField(Cipher cipher, long field) throws IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        byte[] encryptedBytes = cipher.doFinal(ByteBuffer.allocate(Long.BYTES).putLong(field).array());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // Helper method to decrypt a single field
    @RequiresApi(api = Build.VERSION_CODES.O)
    private static <T> Object decryptField(Cipher cipher, String encryptedField, Class<T> type) throws IllegalBlockSizeException, BadPaddingException {
        if (encryptedField == null) {
            return null;
        }

        byte[] encryptedData = Base64.getDecoder().decode(encryptedField);
        byte[] decryptedData = cipher.doFinal(encryptedData);

        if (type == String.class) {
            return type.cast(new String(decryptedData));
        } else if (type == long.class) {
            return (long) Long.parseLong(new String(decryptedData));
        } else if (type == SmokingPreference.class) {
            return type.cast(SmokingPreference.valueOf(new String(decryptedData)));
        } else if (type == DrinkingPreference.class) {
            return type.cast(DrinkingPreference.valueOf(new String(decryptedData)));
        } else {
            throw new IllegalArgumentException("Unsupported field type: " + type.getSimpleName());
        }
    }
}
