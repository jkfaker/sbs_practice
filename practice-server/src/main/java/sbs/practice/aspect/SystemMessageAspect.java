package sbs.practice.aspect;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import sbs.practice.annotation.SystemMessage;
import sbs.practice.common.constant.SystemMessageConstant;
import sbs.practice.common.enums.AnnounceLabel;
import sbs.practice.common.enums.FileLabel;
import sbs.practice.common.enums.NewsLabel;
import sbs.practice.common.enums.ProjectLabel;
import sbs.practice.pojo.dto.AnnounceDTO;
import sbs.practice.pojo.dto.LabelDTO;
import sbs.practice.pojo.dto.NewsVerifyDTO;
import sbs.practice.service.IAnnounceService;
import sbs.practice.service.IFilesService;
import sbs.practice.service.INewsService;
import sbs.practice.service.IProjectService;

@Slf4j
@Component
@Aspect
@RequiredArgsConstructor
/**
 * 当教师审核上传材料后，实现向announce自动存入该项目的系统提示消息
 */
public class SystemMessageAspect {

    private final IAnnounceService announceService;
    private final IFilesService filesService;
    private final INewsService newsService;

    @Pointcut("execution(* sbs.practice.service.*.*(..)) && @annotation(sbs.practice.annotation.SystemMessage)")
    public void systemMessagePointCut(){}

    @After("systemMessagePointCut()")
    public void systemMessage(JoinPoint joinPoint){
        log.info("systemMessage执行...");

        //获取到当前被拦截的方法上的操作类型
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();//方法签名对象
        SystemMessage systemMessage = signature.getMethod().getAnnotation(SystemMessage.class);//获得方法上的注解对象
        AnnounceLabel type = systemMessage.value();//获得操作类型


        //获取到当前被拦截的方法的参数--实体对象
        Object[] args = joinPoint.getArgs();
        if(args == null || args.length == 0){
            return;
        }

        Object entity = args[0];

        // 项目立项
        if (type == AnnounceLabel.SET_UP_PROJECT) {
            LabelDTO labelDTO = (LabelDTO) entity;
            Integer projectId = labelDTO.getId();
            Integer label = labelDTO.getLabel();

            // 教师立项审核已完成，你的项目已被评为 校级立项 / 院级立项

            String text = SystemMessageConstant.SET_UP_REVIEW + ProjectLabel.getInfoByLabel(label);

            // 添加到Announce表
            AnnounceDTO announceDTO = AnnounceDTO.builder()
                    .projectId(projectId)
                    .text(text)
                    .label(AnnounceLabel.SET_UP_PROJECT.getCode())
                    .build();

            // 发送消息 / 添加到announce数据库中
            announceService.sentSystemMessage(announceDTO);
        }
        // 中期文件上传
        else if (type == AnnounceLabel.MID_TERM_PROJECT) {

            LabelDTO labelDTO = (LabelDTO) entity;
            Integer fileId = labelDTO.getId();
            Integer label = labelDTO.getLabel();
            Integer projectId = filesService.getById(fileId).getProjectId();
            String text = SystemMessageConstant.MID_TERM_REVIEW + FileLabel.getInfoByCode(label);

            AnnounceDTO announceDTO = AnnounceDTO.builder()
                    .projectId(projectId)
                    .text(text)
                    .label(AnnounceLabel.MID_TERM_PROJECT.getCode())
                    .build();

            announceService.sentSystemMessage(announceDTO);
        }
        // 结项
        else if (type == AnnounceLabel.END_TERM_PROJECT) {
            // TODO
        }

        // 新闻稿审核

        else if (type == AnnounceLabel.NEWS) {
            NewsVerifyDTO newsVerifyDTO = (NewsVerifyDTO) entity;
            Integer newsId = newsVerifyDTO.getId();
            Integer label = newsVerifyDTO.getLabel();
            String rejectReason = newsVerifyDTO.getRejectReason();

            Integer projectId = newsService.getById(newsId).getProjectId();

            String text = SystemMessageConstant.NEWS_REVIEW + NewsLabel.getInfoByCode(label);

            AnnounceDTO announceDTO = AnnounceDTO.builder()
                    .projectId(projectId)
                    .text(text)
                    .label(AnnounceLabel.NEWS.getCode())
                    .build();

            announceService.sentSystemMessage(announceDTO);
        }
    }


}
