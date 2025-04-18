package sbs.practice.common.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import sbs.practice.common.exception.MessageSendException;
import sbs.practice.common.properties.MessageSendingProperties;
import sbs.practice.pojo.dto.MessageAccessTokenDTO;
import sbs.practice.pojo.dto.SendMessageDTO;
import sbs.practice.pojo.dto.UserDTO;

import javax.sound.midi.Receiver;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor()
public class MessageSendingUtils {
    private static WebClient webClient = WebClient.create(MessageSendingProperties.MESSAGE_SENDING_URL);
    private final CacheManager cacheManager;

    // 缓存配置：缓存名称为"accessTokenCache"，键为固定值"accessToken"
    @Cacheable(value = "accessTokenCache", key = "'accessToken'")
    public String getAccessToken() {

        return fetchAccessToken();
    }


    // 清除accessToken缓存的方法
    private void invalidateAccessTokenCache() {
        Cache cache = cacheManager.getCache("accessTokenCache");
        if (cache != null) {
            cache.evict("accessToken");
        }
    }

    /**
     * 使用WebClient调用接口获取AccessToken
     */
    private String fetchAccessToken() {
        String response = webClient.get()
                .uri(MessageSendingProperties.MESSAGE_SENDING_URL + MessageSendingProperties.GET_TOKEN_PATH)
                .header("appId", MessageSendingProperties.APPID)
                .header("appSecret", MessageSendingProperties.SECRET)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .block(); // 假定返回的是Map类型的数据结构
        // 反序列化
        MessageAccessTokenDTO accessTokenDTO = JSON.parseObject(response, MessageAccessTokenDTO.class, JSONReader.Feature.SupportSmartMatch);

        // 正确请求
        if (accessTokenDTO.getErrcode().equals("0")) {
            // 返回accessToken
            return accessTokenDTO.getData();
        }
        // 错误请求
        else {
            throw new MessageSendException(accessTokenDTO.getErrmsg());
        }
    }

    /**
     * 发送系统消息通知
     *
     * @param content     消息内容
     * @param subject     消息主题
     * @param userId      接收人学号
     * @return 响应结果
     */

    public String sendSystemMessage(String subject, String content, String userId) {
        // 获取缓存的accessToken
        String accessToken = getAccessToken();

        Map<String, Object> message = new HashMap<>();
        // sign: (accessToken + 第一个receivers的userID)的32位小写的MD5加密值
        String sign = DigestUtils.md5Hex(accessToken + userId);
        message.put("sign", sign);
        message.put("sendType", MessageSendingProperties.MESSAGE_SEND_TYPE);
        message.put("subject", subject);
        message.put("content", content);
        // 构建receivers
        message.put("receivers", List.of(Map.of("userId", userId)));
        message.put("pcUrl", MessageSendingProperties.PC_URL);
        message.put("mobileUrl", MessageSendingProperties.MOBILE_URL);
        message.put("urlDesc", "查看详情");

        log.info("send system message: {}", JSON.toJSONString(message));

        String response = webClient.post()
                .uri(MessageSendingProperties.MESSAGE_SENDING_URL + MessageSendingProperties.SEND_MESSAGE_PATH) // 假设这是发送消息的API路径
                .header("appId", MessageSendingProperties.APPID)
                .header("accessToken", accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(message)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        // 反序列化
        SendMessageDTO sendMessageDTO = JSON.parseObject(response, SendMessageDTO.class, JSONReader.Feature.SupportSmartMatch);

        // 正确请求
        if (sendMessageDTO.getErrcode() == null || sendMessageDTO.getErrcode().isEmpty()) {
            // 返回消息id
            return sendMessageDTO.getMsgId();
        }
        // accessToken过期
        else if (sendMessageDTO.getErrcode().equals("993")) {
            invalidateAccessTokenCache();
            return sendSystemMessage(content, subject, userId);
        }
        // 错误请求
        else {
            throw new MessageSendException(sendMessageDTO.getErrmsg());
        }
    }

    /**
     * 发送教师消息通知
     * @param content     消息内容
     * @param subject     消息主题
     * @param userId      接收人学号
     * @return 响应结果
     */
    public String sendTeacherMessage(String subject, String content, String userId, String senderId, String senderName) {
        // 获取缓存的accessToken
        String accessToken = getAccessToken();

        Map<String, Object> message = new HashMap<>();
        String sign = DigestUtils.md5Hex(accessToken + userId);
        message.put("sign", sign);
        message.put("sendType", MessageSendingProperties.MESSAGE_SEND_TYPE);
        message.put("senderId", senderId);
        message.put("senderName", senderName);
        message.put("content", content);
        message.put("subject", subject);
        // 构建receivers
        message.put("receivers", List.of(Map.of("userId", userId)));
        message.put("pcUrl", MessageSendingProperties.PC_URL);
        message.put("mobileUrl", MessageSendingProperties.MOBILE_URL);
        message.put("urlDesc", "查看详情");

        String response = webClient.post()
                .uri(MessageSendingProperties.MESSAGE_SENDING_URL + MessageSendingProperties.SEND_MESSAGE_PATH) // 假设这是发送消息的API路径
                .header("appId", MessageSendingProperties.APPID)
                .header("accessToken", accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(message)
                .retrieve()
                .bodyToMono(String.class)
                .block();


        // 反序列化
        SendMessageDTO sendMessageDTO = JSON.parseObject(response, SendMessageDTO.class, JSONReader.Feature.SupportSmartMatch);

        // 正确请求
        if (sendMessageDTO.getErrcode() == null || sendMessageDTO.getErrcode().isEmpty()) {
            // 返回消息id
            return sendMessageDTO.getMsgId();
        }
        // accessToken过期
        else if (sendMessageDTO.getErrcode().equals("993")) {
            invalidateAccessTokenCache();
            return sendTeacherMessage(content, subject, userId, senderId, senderName);
        }
        // 错误请求
        else {
            throw new MessageSendException(sendMessageDTO.getErrmsg());
        }
    }
}