package org.cleverframe.core.controller;

import org.cleverframe.common.controller.BaseController;
import org.cleverframe.common.mapper.BeanMapper;
import org.cleverframe.common.persistence.Page;
import org.cleverframe.common.vo.response.AjaxMessage;
import org.cleverframe.core.CoreBeanNames;
import org.cleverframe.core.CoreJspUrlPath;
import org.cleverframe.core.entity.Template;
import org.cleverframe.core.service.EhCacheTemplateService;
import org.cleverframe.core.vo.request.*;
import org.cleverframe.webui.easyui.data.DataGridJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-6-17 16:00 <br/>
 */
@SuppressWarnings("MVCPathVariableInspection")
@Controller
@RequestMapping("/${mvcPath}/core/template")
public class TemplateController extends BaseController {

    @Autowired
    @Qualifier(CoreBeanNames.EhCacheTemplateService)
    private EhCacheTemplateService ehCacheTemplateService;

    @RequestMapping("/Template" + VIEW_PAGE_SUFFIX)
    public ModelAndView getTemplateJsp(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(CoreJspUrlPath.Template);
    }

    /**
     * 查询模版数据，使用分页
     *
     * @return EasyUI DataGrid控件的json数据
     */
    // @RequiresRoles("root")
    @RequestMapping("/findTemplateByPage")
    @ResponseBody
    public DataGridJson<Template> findTemplateByPage(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid TemplateQueryVo templateQueryVo,
            BindingResult bindingResult) {
        DataGridJson<Template> json = new DataGridJson<>();
        Page<Template> qLScriptPage = ehCacheTemplateService.findByPage(
                new Page<Template>(request, response),
                templateQueryVo.getName(),
                templateQueryVo.getLocale(),
                templateQueryVo.getId(),
                templateQueryVo.getUuid(),
                templateQueryVo.getDelFlag());
        json.setRows(qLScriptPage.getList());
        json.setTotal(qLScriptPage.getCount());
        return json;
    }

    /**
     * 保存模版数据对象<br>
     */
    // @RequiresRoles("root")
    @RequestMapping("/addTemplate")
    @ResponseBody
    public AjaxMessage<String> addTemplate(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid TemplateAddVo templateAddVo,
            BindingResult bindingResult) {
        addXSSExcludeUrl(request);
        AjaxMessage<String> message = new AjaxMessage<>(true, "模版数据保存成功", null);
        Template template = BeanMapper.mapper(templateAddVo, Template.class);

        if (beanValidator(bindingResult, message)) {
            ehCacheTemplateService.saveTemplate(template);
        }
        return message;
    }

    /**
     * 更新模版数据对象<br>
     */
    // @RequiresRoles("root")
    @RequestMapping("/updateTemplate")
    @ResponseBody
    public AjaxMessage<String> updateTemplate(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid TemplateUpdateVo templateUpdateVo,
            BindingResult bindingResult) {
        addXSSExcludeUrl(request);
        AjaxMessage<String> message = new AjaxMessage<>(true, "更新模版数据成功", null);
        Template template = BeanMapper.mapper(templateUpdateVo, Template.class);
        if (beanValidator(bindingResult, message)) {
            ehCacheTemplateService.updateTemplate(template);
        }
        return message;
    }

    /**
     * 删除模版数据对象<br>
     */
    // @RequiresRoles("root")
    @RequestMapping("/deleteTemplate")
    @ResponseBody
    public AjaxMessage<String> deleteTemplate(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid TemplateDelVo templateDelVo,
            BindingResult bindingResult) {
        AjaxMessage<String> message = new AjaxMessage<>(true, "模版数据删除成功", null);
        if (beanValidator(bindingResult, message)) {
            ehCacheTemplateService.deleteTemplate(templateDelVo.getName());
        }
        return message;
    }

    /**
     * 根据模版名称返回模版数据
     */
    @RequestMapping("/getTemplateByName")
    @ResponseBody
    public AjaxMessage<Template> getTemplateByName(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid TemplateGetByNameVo templateGetByNameVo,
            BindingResult bindingResult) {
        AjaxMessage<Template> message = new AjaxMessage<>(true, "查找成功", null);
        if (beanValidator(bindingResult, message)) {
            Template template = ehCacheTemplateService.getTemplateByName(templateGetByNameVo.getName());
            message.setResult(template);
        }
        return message;
    }
}
