import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        try{
            start(SuiteTest.class);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public static void start(Class c){
        Method[] methods = c.getDeclaredMethods();
        Map<String, Integer> map = new HashMap<>();
        Map<Method, Integer> mapMethods = new HashMap<>();
        String nameAfterMethod = null;
        String nameBeforeMethod = null;

        for (Method m : methods) {

            if (m.isAnnotationPresent(AfterSuite.class)) {
                if(nameAfterMethod!=null){
                    throw new RuntimeException("Указано более одной аннотации AfterSuite");
                }else{
                    nameAfterMethod = m.getName();
                }
            }

            if (m.isAnnotationPresent(BeforeSuite.class)) {
                if(nameBeforeMethod!=null){
                    throw new RuntimeException("Указано более одной аннотации BeforeSuite");
                }else{
                    nameBeforeMethod = m.getName();
                }
            }

            
            if (m.isAnnotationPresent(Test.class)) {
                map.put(m.getName(),m.getAnnotation(Test.class).priority());
                mapMethods.put(m,m.getAnnotation(Test.class).priority());
                try{
                }catch (Exception e){
                    System.out.println(e);
                }
            }
        }

        if(nameAfterMethod!=null){
            try{
                Method method = c.getMethod(nameAfterMethod);
                method.invoke(null);
            }catch (Exception e){
                System.out.println("Ошибка выполнения метода: " + nameAfterMethod);
            }
        }

        /*
        for (int i = 1; i < 11; i++) {
            for (Map.Entry entry : map.entrySet()) {
                if ((Integer)entry.getValue() == i){
                    try{
                        Method method = c.getMethod(entry.getKey().toString());
                        method.invoke(null);
                    }catch (Exception e){
                        System.out.println("Ошибка выполнения метода: " + entry.getKey());
                    }
                }
            }
        }
*/

        Map<String, Integer>
                sortedMap = map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors
                        .toMap(Map.Entry::getKey,
                                Map.Entry::getValue,
                                (e1, e2) -> e1,
                                LinkedHashMap::new));

        for (Map.Entry entry : sortedMap.entrySet()) {
            Method method = null;
            try {
                method = c.getMethod(entry.getKey().toString());
                method.invoke(null);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        if(nameBeforeMethod!=null){
            try{
                Method method = c.getMethod(nameBeforeMethod);
                method.invoke(null);
            }catch (Exception e){
                System.out.println("Ошибка выполнения метода: " + nameBeforeMethod);
            }
        }
    }
}
