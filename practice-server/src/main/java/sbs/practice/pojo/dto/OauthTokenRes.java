package sbs.practice.pojo.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("Oauth返回结果")
public class OauthTokenRes {
    private String access_token;
    private String token_type;
    private int expires_in;
    private String scope;
    private String errmsg;
}