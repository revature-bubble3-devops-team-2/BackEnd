package com.revature.utilities;

import com.revature.dto.ProfileDTO;
import com.revature.models.Post;
import com.revature.models.Profile;
import com.revature.utilites.SecurityUtil;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class SecurityUtilTest {
    private static final String PASSWORD = "abc123";
    private static final String PASSKEY = "c8ZLBnfDh3YsvZ2dW1KDWY6ZTak8+v+/L74e8Vfoydk1IfySsVCAZVKTZfrtPKodzUXEiR+69y" +
            "jOz1qqf7U4rA==jnW2sIxW7inUlQqGJCNrNa7Eavj5uMGQAYZ0S6xNz65p79QaOk8eZpOChJlFPvIadohhOuHg5PFGeewM2YmkVR260Y" +
            "PhJwK/GUR3YXsUH0+KjOQKuHAHY8CyLwpBsNV0DsPO56jn2As1CfBMJc9VSNsx37W4Vo5MCaOTisZwBWjUsTG9i+HecqcK9C0tIWC2Jn" +
            "44oX5BAS31Huev/ZIWf2OE3FjHSMLyJs0TmJAPxBg6IllwEhv75+ffGbZENZVkkHV3LRxUWKtGdQa5tSOt6pdzIZtdSO6o81zXD6BBDf" +
            "EZo22qCVCR2C2DQbsmaMJhInHwxEZ3RS3/1N+rz85STq/h+nKvqBcoPOFfq0o5tRRnOlRVFpWuONhiY7IUSqtTZCJmqHnALSRFJwMGFP" +
            "jmUkq1nQxux1rgxllacVb+AT4bS+Xbw8DyUwGepmTCiw4t75krGyCSBArcmfiFBtgDkVZTFIJ+GMFhFbpWv2darLcxKlgSdur/z9YCYo" +
            "ZcKm9vrrH+CaFykfIUdjnln5jhLoRmjeBIHgYWITG5J5/NCzAM+a3k4Y92/hbgDDE15GD1ud1EU8GHY4eb5LU1pAb2O7zbcW9pQbtVcb" +
            "qyJGNRFA6OAGcWb1R0+04d0+1DA6BjTDsxkltgsvUpLrVFBo4VaFAT6Jf4ZI2Pg39WjFY1an8=";
    private static final Profile PROFILE = new Profile(8756, "username", "passkey", "name", "name", "email", true);

    @Order(1)
    @Test
    void uninitializedDecrypter() {
        assertNull(SecurityUtil.validateToken("tokenadfjaohgioean;sdlnma;iba;cvneaih;igjaiedntia;hjdiha;toni;" +
                "aeoskdlnf;oadivn;fidshjgha;grfdiagknjvioaernva;ergovirea;rknjgajufhgaioerfgnvmaar;gfavnm;arihj;" +
                "aiogmjarnmgvi;orjf;oarmg;ajfkda;kfg;lkadjfdlksajfienvruahrgaio;." +
                ".avNdiaovbre3w409a0v4kjaldja-j34tjJIDJ;KLKAG;J493A0JG04JAEORIFGNJUAINL990Ejtg43ia" +
                ".kerjlnvap049oagnv498atghvaurgnblakwejtnbyiutolahgr8t4angvrjkfa" +
                "[gijakdtrfjewouioekafjajdkthiakjgahtioakdmel. atosposoatkjh;hlmejaoiabjiea;" +
                "oitjhaoihyaikerjtanijieakjdijflakdjahtkjmoelwka;kdfjaeialdkncaviaoeamt,jdjgioaidfjzLSxnfmner;" +
                "taivjzidcjv;hiHErtnmartg;laifgjvuchvERtna;ghuiejiONfewa.,mv;aoifSDIurhfjake4rkta;vf," +
                "gajdkOEDIrj3me4wa;lkdjfadioFUDrfnejt.adnjoiXCuifewka<MTRedjmol:DCIfvuiemake.,dfmcodeiajthgrtae," +
                "fdckljfuieo;afjklamd,fma;dkflajf"));
    }

    @Test
    void decryptInvalidToken() {
        String falseToken = "eyJlbmMiOiJBMTI4R0NNIiwiYWxnIjoiUlNBLU9BRVAtMjU2In0." +
                "f5zMU3Nwa5UbuxByikNyGraLXEo78izNqstQarVPotZt0RovvI79nZXYMrvvNZTLRXYlmnrUDoso86o4IF9j4gVRl83nfCgF0G65" +
                "mUQ4-By1fGjDDtR9yvLztFBHhAackVlcj5kwCrQlxVCwIqvw8dLEVmbWEM3npGo1z6k1QR8OVzS9vYS58k1uQMcQm3-5qmp8ftee" +
                "7DNSfmDoN40pVutQRupH-H1tyMZaHBRyA6meG1jl1GwIjmOZh8AKWcpjjxUQh7_njpgfj9AsN0TVJOCH-KGMs2PogKuw2O7-4mFe" +
                "T-qUXTlSrf87KZKunvLBkKP1fnBDTCxoYnA3pDL90Q.3R1CK_7QCxAq8iSk.mnJMOJxog_3FEHMHPyoY5IT6W-36xh-Co4LxAybp" +
                "KE1geanNyx9VuHPd5XZVTgudm6lb_eYGXZV2TUTyfCs_Z_-gK8i6yPzElexPXMom0ZGQEx9-sNgqtropSJGWhj0Byj30ipqKS9b9" +
                "2DHXfKMVTiI-yUBWmXH-hMYEBwxmKXw0OjPGdl2eWXM7T22NuJnSOqOVkXcDa8a-Sefbj_wPBdYuJxMHM_YvW5XB0xdmlTQZ6hz6" +
                "BCTeLpJNHBD0wakFLDs9CPXMEqjzcksEVBjlCSWyM8XENEUZCz1fZ8dbhcLl70WWkZ1bANvY5E4rWQFZIq_M5RKfuSDC0mxaTiOZ" +
                "uDNEfE8G9fhV5vFJsgKY637ZTg.dPx02Asj2IZ-nfx0ctsVoQ";
        assertAll(
                () -> assertNull(SecurityUtil.validateToken(null)),
                () -> assertNull(SecurityUtil.validateToken("")),
                () -> assertNull(SecurityUtil.validateToken("aninvalidtooshorttoken.thathassomecorrectparts")),
                () -> assertNull(SecurityUtil.validateToken(falseToken))
        );
    }
    
    @Test
    @Order(2)
    void generateValidToken() {
        assertNotNull(SecurityUtil.generateToken(new ProfileDTO(PROFILE)));
    }

    @Test
    void generateFromNull() {
        assertNull(SecurityUtil.generateToken(null));
    }

    @Test
    void generateFromIncomplete() {
        String val = "testdata";
        assertAll(

                () -> assertNull(SecurityUtil.generateToken(new ProfileDTO(new Profile(0, val, val, val, val, val, false)))),
                () -> assertNull(SecurityUtil.generateToken(new ProfileDTO(new Profile(867851386, null, val, val, val, val, false)))),
                () -> assertNull(SecurityUtil.generateToken(new ProfileDTO(new Profile(867851386, val, null, val, val, val, false)))),
                () -> assertNull(SecurityUtil.generateToken(new ProfileDTO(new Profile(867851386, val, val, null, val, val, false)))),
                () -> assertNull(SecurityUtil.generateToken(new ProfileDTO(new Profile(867851386, val, val, val, null, val, false)))),
                () -> assertNull(SecurityUtil.generateToken(new ProfileDTO(new Profile(867851386, val, val, val, val, null, false)))),
                () -> assertNull(SecurityUtil.generateToken(new ProfileDTO(new Profile(867851386, "", val, val, val, val, false)))),
                () -> assertNull(SecurityUtil.generateToken(new ProfileDTO(new Profile(867851386, val, "", val, val, val, false)))),
                () -> assertNull(SecurityUtil.generateToken(new ProfileDTO(new Profile(867851386, val, val, "", val, val, false)))),
                () -> assertNull(SecurityUtil.generateToken(new ProfileDTO(new Profile(867851386, val, val, val, "", val, false)))),
                () -> assertNull(SecurityUtil.generateToken(new ProfileDTO(new Profile(867851386, val, val, val, val, "", false))))
        );
    }

    @Test
    void hashValidPassword() {
        String key = SecurityUtil.hashPassword(PASSWORD);
        assertAll(
                () -> assertNotNull(key),
                () -> assertNotEquals(PASSKEY, key),
                () -> assertEquals(PASSKEY.length(), key.length())
        );
    }

    @Test
    void hashInvalidPassword() {
        assertAll(
                () -> assertNull(SecurityUtil.hashPassword(null)),
                () -> assertNull(SecurityUtil.hashPassword(""))
        );
    }

    @Test
    void idUniqueness() {
        ArrayList<Integer> ids = new ArrayList<>();
        for (int i = 0; i < 100; i++){
            ids.add(SecurityUtil.getId());
        }
        List<Integer> distinctIds = ids.stream().distinct().collect(Collectors.toList());
        assertEquals(ids.size(), distinctIds.size());
    }

    @Test
    void checkInvalidPassword() {
        assertAll(
                () -> assertFalse(SecurityUtil.isPassword("password", null)),
                () -> assertFalse(SecurityUtil.isPassword("password", "")),
                () -> assertFalse(SecurityUtil.isPassword(null, PASSKEY)),
                () -> assertFalse(SecurityUtil.isPassword("", PASSKEY)),
                () -> assertFalse(SecurityUtil.isPassword(PASSWORD+"4", PASSKEY)),
                () -> assertFalse(SecurityUtil.isPassword(PASSWORD, PASSKEY+"y"))
        );
    }
}
