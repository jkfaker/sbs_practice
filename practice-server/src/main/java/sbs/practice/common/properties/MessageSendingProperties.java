package sbs.practice.common.properties;

public class MessageSendingProperties {

    // 发送消息所需要的一些常量
    public static final String MESSAGE_SENDING_URL = "https://gateway.sbs.edu.cn"; // 假设这是获取token的API域名
    public static final String GET_TOKEN_PATH = "/token/gateway/accessToken";
    public static final String SEND_MESSAGE_PATH = "/mp/restful/v2/message/send";


    public static final String APPID = "1358801519623557120"; // 请替换为实际的appId
    public static final String SECRET = "1960ECE5DA57HR89R8BX"; // 请替换为实际的secre
    public static final String PC_URL = "https://yzs.sbs.edu.cn/practice/pages/student/announce/announce";
    public static final String MOBILE_URL = "https://yzs.sbs.edu.cn/practice/pages/student/announce/announce";
    // 发送至手机今日校园消息
    public static final Integer MESSAGE_SEND_TYPE = 0;

    // 暑期社会实践发送的消息固定主题格式
    public static final String SUBJECT_TEXT_HEAD = "社会实践:";
}
