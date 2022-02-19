public class SuiteTest {

    @Test(priority = 5)
    public static void priority5(){
        System.out.println("Priority 5");
    };

    @Test(priority = 2)
    public static void priority2(){
        System.out.println("Priority 2");
    };

    @Test(priority = 1)
    public static void priority1(){
        System.out.println("Priority 1");
    };


    @Test(priority = 2)
    public static void priority7(){
        System.out.println("Priority 7");
    };

    @BeforeSuite
    public static void BeforeSuite(){
        System.out.println("Before suite");
    }

    @AfterSuite
    public static void AfterSuite(){
        System.out.println("After suite");
    }

}
