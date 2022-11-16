import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * @ClassName TestCode
 * @Deacription TODO
 * @Author wrx
 * @Date 2021/9/6/006 15:27
 * @Version 1.0
 **/
public class TestCode {

    public static void main(String[] args) {

//        List list=new ArrayList();
//        list.add(1);
//        list.add(2);
//        System.out.println(list);
//
//        List list2= Collections.unmodifiableList(list);
//        list2.add(3);
//        System.out.println(list2);

        CopyOnWriteArrayList cList=new CopyOnWriteArrayList();
        Map a=new HashMap();
        a.put(1,1);
        cList.add(a);
        System.out.println(cList);
        a.put(1,2);
        System.out.println(cList);
    }
}
