package sbs.practice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import sbs.practice.common.result.Result;
import sbs.practice.pojo.dto.OauthTokenRes;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * 调用今日校园Oauth认证
 */
@RestController
@RequestMapping("/oauth")
@Api(tags = "Oauth认证")
public class OauthController {


    // 直接创建 RestTemplate 实例
    private final RestTemplate restTemplate = new RestTemplate();
    private final String clientId = "17313155825670722";
    private final String clientSecret = "4Ds-35DN48Jbhhz0NSp00YMhvhkbJZ";
    //oauth域名，用以获取token
    private final String oauthUrl = "https://api.campushoy.com";


/**
     * 获取用户的token，根据此token可以调用今日校园接口
     * 真实业务场景需要对token进行缓存
     *
     * @param code
     * @param redirect_uri
     * @return
     */
    @GetMapping("/getToken")
    @ApiOperation("code换token")
    private Result<String> getToken(String code, String redirect_uri) {
        HashMap<String, String> tokenParam = new HashMap<>(5);
        //请求的类型，此值固定为“authorization_code”。
        tokenParam.put("grant_type", "authorization_code");
        //应用注册时候分配的 App Key
        tokenParam.put("client_id", clientId);
        //应用注册时候分配的 App Secret，请不要将其嵌入到服务端以外的代码里。
        tokenParam.put("client_secret", clientSecret);
        //调用 authorize 接口返回的 authorization code。
        tokenParam.put("code", code);
        //回调地址，必须是注册应用时填写的主域名下的地址，并且需要跟请求 code 的 redirect_uri 一致。
        tokenParam.put("redirect_uri", redirect_uri);

        String url = oauthUrl + "/connect/oauth2/token?";
        // 构造uri
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(url);
        for (Map.Entry<String, String> entry : tokenParam.entrySet()) {
            uriComponentsBuilder.queryParam(entry.getKey(), entry.getValue());
        }
        URI uri = uriComponentsBuilder.build().encode().toUri();

        OauthTokenRes oauthTokenRes = restTemplate.getForObject(uri, OauthTokenRes.class);
        String token = oauthTokenRes.getAccess_token();
        if (token == null || token.isEmpty()) {
            throw new RuntimeException(oauthTokenRes.getErrmsg());
        }
        return Result.success(token);
    }
}

