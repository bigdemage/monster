/**
 * @ClassName Kafka
 * @Deacription TODO
 * @Author wrx
 * @Date 2021/8/4/004 15:29
 * @Version 1.0
 **/
public class Kafka {
    private static ReplicaManager replicaManager = new ReplicaManager();

    public static void main(String[] args) {

        String phones="\"13006173815\",\"13898985210\",\"18510131360\",\"13212341111\",\"18766661885\",\"13895080307\",\"18835301367\",\"13286867027\",\"13163231090\",\"15732056368\",\"17522546253\",\"13581871130\",\"13284728044\",\"14558945830\",\"18353577473\",\"15002221111\",\"15011110001\",\"14940436538\",\"15271254767\",\"13857937315\",\"18788621688\",\"13710297343\",\"13581871119\",\"13581871118\",\"13581871117\",\"13581871116\",\"13033313950\",\"18788621674\",\"18788621675\",\"18788621672\",\"15712886965\",\"18788621673\",\"13335023305\",\"18788621670\",\"18788621671\",\"13703554220\",\"18687794380\",\"13895020307\",\"18788621678\",\"13410909850\",\"18788621676\",\"18788621677\",\"18103834960\",\"13581879999\",\"13581871129\",\"13581871128\",\"13509207967\",\"18214854484\",\"13581871127\",\"13810801120\",\"14729367873\",\"13581869123\",\"13581869369\",\"13837995458\",\"18788621669\",\"13413710509\",\"18788621668\",\"18046233881\",\"18211120000\",\"13581871122\",\"13581871121\",\"13200000039\",\"15811406762\",\"13581871120\",\"13581871126\",\"13581871125\",\"13581871124\",\"13581871123\"";
        System.out.println(phones.split(",").length);
    }
}

class ReplicaManager {
    private ReplicaFetcher replicaFetcher = new ReplicaFetcher();
}

class ReplicaFetcher {

}




