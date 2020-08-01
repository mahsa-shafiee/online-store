import config.BeanConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.testng.Assert;
import org.testng.annotations.Test;
import view.UserLoginMenu;

public class UserRegisterTest {
    private ApplicationContext applicationContext = new AnnotationConfigApplicationContext(BeanConfiguration.class);
    private UserLoginMenu userLoginMenu = applicationContext.getBean("userLoginMenu", UserLoginMenu.class);

    @Test
    public void isValidName() {
        Assert.assertFalse(userLoginMenu.isValidName("11122"));
        Assert.assertFalse(userLoginMenu.isValidName("1aaa1"));
        Assert.assertFalse(userLoginMenu.isValidName("aa-="));
        Assert.assertFalse(userLoginMenu.isValidName("aaa"));
        Assert.assertFalse(userLoginMenu.isValidName("aaaaabbbbbccccc"));
        Assert.assertTrue(userLoginMenu.isValidName("mahsa"));
    }

    @Test
    public void isValidAge() {
        Assert.assertFalse(userLoginMenu.isValidAge("abc"));
        Assert.assertFalse(userLoginMenu.isValidAge("10."));
        Assert.assertFalse(userLoginMenu.isValidAge("7"));
        Assert.assertFalse(userLoginMenu.isValidAge("120"));
        Assert.assertTrue(userLoginMenu.isValidAge("25"));
    }

    @Test
    public void isValidMobileNumber() {
        Assert.assertFalse(userLoginMenu.isValidMobileNumber("abc"));
        Assert.assertFalse(userLoginMenu.isValidMobileNumber("12345678-90"));
        Assert.assertFalse(userLoginMenu.isValidMobileNumber("12345678900"));
        Assert.assertTrue(userLoginMenu.isValidMobileNumber("09121234567"));
    }

    @Test
    public void isValidEmailAddress() {
        Assert.assertFalse(userLoginMenu.isValidEmailAddress("1234"));
        Assert.assertFalse(userLoginMenu.isValidEmailAddress("-==@gmail.com"));
        Assert.assertFalse(userLoginMenu.isValidEmailAddress("mhvg1@gmail"));
        Assert.assertTrue(userLoginMenu.isValidEmailAddress("mahsa123@gmail.com"));
    }

    @Test
    public void isValidHomeAddress() {
        Assert.assertFalse(userLoginMenu.isValidHomeAddress("1234", "abc", "abc", "1234567890"));
        Assert.assertFalse(userLoginMenu.isValidHomeAddress("abc", "1234", "abc", "1234567890"));
        Assert.assertFalse(userLoginMenu.isValidHomeAddress("abc", "abc", "1234", "1234567890"));
        Assert.assertFalse(userLoginMenu.isValidHomeAddress("abc", "abc", "abc", "p234567890"));
        Assert.assertTrue(userLoginMenu.isValidHomeAddress("abc", "abc", "abc", "1234567890"));
    }

    @Test
    public void isValidUserName() {
        Assert.assertFalse(userLoginMenu.isValidUserName("abcd"));
        Assert.assertFalse(userLoginMenu.isValidUserName("aaaabbbbbccccdddd"));
        Assert.assertFalse(userLoginMenu.isValidUserName("ab1cd"));
        Assert.assertTrue(userLoginMenu.isValidUserName("abcde"));
    }

    @Test
    public void isValidPassword() {
        Assert.assertFalse(userLoginMenu.isValidPassword("aa1"));
        Assert.assertFalse(userLoginMenu.isValidPassword("aa11aa11"));
        Assert.assertFalse(userLoginMenu.isValidPassword("aa-11"));
        Assert.assertTrue(userLoginMenu.isValidPassword("11111"));
        Assert.assertTrue(userLoginMenu.isValidPassword("aaaaa"));
        Assert.assertTrue(userLoginMenu.isValidPassword("aa1aa"));
    }
}