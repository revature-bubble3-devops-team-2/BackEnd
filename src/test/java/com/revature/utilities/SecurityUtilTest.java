package com.revature.utilities;

import com.revature.models.Profile;
import com.revature.utilites.SecurityUtil;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class SecurityUtilTest {
    private static final String PASSWORD = "abc123";
    private static final String PASSKEY = "c8ZLBnfDh3YsvZ2dW1KDWY6ZTak8+v+/L74e8Vfoydk1IfySsVCAZVKTZfrtPKodzUXEiR+69y" +
            "jOz1qqf7U4rA==jnW2sIxW7inUlQqGJCNrNa7Eavj5uMGQAYZ0S6xNz65p79QaOk8eZpOChJlFPvIadohhOuHg5PFGeewM2YmkVR260Y" +
            "PhJwK/GUR3YXsUH0+KjOQKuHAHY8CyLwpBsNV0DsPO56jn2As1CfBMJc9VSNsx37W4Vo5MCaOTisZwBWjUsTG9i+HecqcK9C0tIWC2Jn" +
            "44oX5BAS31Huev/ZIWf2OE3FjHSMLyJs0TmJAPxBg6IllwEhv75+ffGbZENZVkkHV3LRxUWKtGdQa5tSOt6pdzIZtdSO6o81zXD6BBDf" +
            "EZo22qCVCR2C2DQbsmaMJhInHwxEZ3RS3/1N+rz85STq/h+nKvqBcoPOFfq0o5tRRnOlRVFpWuONhiY7IUSqtTZCJmqHnALSRFJwMGFP" +
            "jmUkq1nQxux1rgxllacVb+AT4bS+Xbw8DyUwGepmTCiw4t75krGyCSBArcmfiFBtgDkVZTFIJ+GMFhFbpWv2darLcxKlgSdur/z9YCYo" +
            "ZcKm9vrrH+CaFykfIUdjnln5jhLoRmjeBIHgYWITG5J5/NCzAM+a3k4Y92/hbgDDE15GD1ud1EU8GHY4eb5LU1pAb2O7zbcW9pQbtVcb" +
            "qyJGNRFA6OAGcWb1R0+04d0+1DA6BjTDsxkltgsvUpLrVFBo4VaFAT6Jf4ZI2Pg39WjFY1an8=";
    private static final Profile PROFILE = new Profile(8756, "username", "passkey", "name", "name", "email");

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
    @Order(2)
    void generateValidToken() {
        assertNotNull(SecurityUtil.generateToken(PROFILE));
    }

    @Test
    void generateFromNull() {
        assertNull(SecurityUtil.generateToken(null));
    }

    @Test
    void generateFromIncomplete() {
        assertAll(
                () -> assertNull(SecurityUtil.generateToken(new Profile(867851386, null, null, null, null, null))),
                () -> assertNull(SecurityUtil.generateToken(new Profile(487351838, null, null, null, null, "e"))),
                () -> assertNull(SecurityUtil.generateToken(new Profile(487351838, null, null, null, "ln", "e"))),
                () -> assertNull(SecurityUtil.generateToken(new Profile(487351838, null, null, "fn", "ln", "e"))),
                () -> assertNull(SecurityUtil.generateToken(new Profile(487351838, null, "pk", "fn", "ln", "e"))),
                () -> assertNull(SecurityUtil.generateToken(new Profile(0, "", "", "", "", "")))
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
        for (int i = 0; i < 10000; i++){
            ids.add(SecurityUtil.getId());
        }
        List<Integer> distinctIds = ids.stream().distinct().collect(Collectors.toList());
        assertEquals(ids.size(), distinctIds.size());
    }
}
