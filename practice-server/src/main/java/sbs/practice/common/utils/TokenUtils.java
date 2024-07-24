package sbs.practice.common.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import sbs.practice.common.constant.MessageConstant;
import sbs.practice.common.context.BaseContext;
import sbs.practice.common.exception.AuthenticationException;
import sbs.practice.common.properties.CampushoyProperties;
import sbs.practice.pojo.dto.UserDTO;
import sbs.practice.pojo.entity.SecTeacher;


@Slf4j
public class TokenUtils {

    private static final WebClient webClient = WebClient.create(CampushoyProperties.TOKEN_PARSE_API);

    /**
     * 发送api请求，并解析为json
     * @param token
     * @return
     */
    public static String tokenToApi(String token) {
        String result = webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(CampushoyProperties.TOKEN_PARSE_PATH)
                        .queryParam(CampushoyProperties.TOKEN_PARSE_PARAM, token)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return result;
    }

    /**
     * 在上面的基础上将JSON转换成Student类
     * @param json
     * @return
     */
    public static UserDTO tokenToClass(String json) {
        // fastJSON JSON 反序列化 + 转驼峰命名
        UserDTO student = JSON.parseObject(json, UserDTO.class, JSONReader.Feature.SupportSmartMatch);
        log.info("反序列化：{}", student);
        return student;
    }


    /**
     * 验证是否为老师
     */
    public static Boolean verifyTeacher() {
        String userId  = BaseContext.getCurrentUser().getCampusId();
        try {
            SecTeacher teacher = Db.getById(userId, SecTeacher.class);
            teacher.getId();
        } catch (Exception e) {
            throw new AuthenticationException(MessageConstant.AUTHENTICATION_FAILED);
        }

        return true;
    }

    public static Boolean verifyTeacher(Integer departId) {
        String userId  = BaseContext.getCurrentUser().getCampusId();
        verifyTeacher();
        if (departId != Db.getById(userId, SecTeacher.class).getDepartId()) {
            throw new AuthenticationException(MessageConstant.AUTHENTICATION_FAILED);
        }
        return true;
    }
}
