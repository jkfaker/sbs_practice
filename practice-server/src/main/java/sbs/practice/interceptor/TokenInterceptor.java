package sbs.practice.interceptor;


import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import sbs.practice.common.constant.LoginConstant;
import sbs.practice.common.context.BaseContext;
import sbs.practice.common.result.Result;
import sbs.practice.pojo.dto.UserDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static sbs.practice.common.utils.TokenUtils.tokenToApi;
import static sbs.practice.common.utils.TokenUtils.tokenToClass;

@Component
@Slf4j
public class TokenInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(LoginConstant.TOKEN);
        log.info("token: {}", token);
        // 访问api，获取数据
        String result = tokenToApi(token);
        log.info("学生：{}", result);
        Integer errorCode = JSON.parseObject(result).getIntValue(LoginConstant.ERROR_CODE);
        Result error = Result.error(LoginConstant.NOT_LOGIN);
        String notLogin = JSON.toJSONString(error);
        if (errorCode == 40001) {
            response.getWriter().write(notLogin);
            return false;
        }
        // fastJSON JSON 反序列化 + 转驼峰命名
        UserDTO user = tokenToClass(result);
        if (user.getCampusUserId() == null || user.getCampusUserId().isEmpty()) {
            response.getWriter().write(notLogin);
            return false;
        }
        log.info("当前用户学号：{}", user.getCampusId());
        BaseContext.setCurrentUser(user);
        return true;
    }
}
