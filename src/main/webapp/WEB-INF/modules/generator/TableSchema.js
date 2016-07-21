/**
 * 页面Js对象定义
 */
var pageJs = function (globalPath) {
    // 当前pageJs对象
    var _this = this;

    // 获取数据库表详细信息
    var getTableSchemaURL = globalPath.mvcPath + "/generator/matedata/getTableSchema.json";

    // 数据库名
    var labelSchemaName = $("#labelSchemaName");
    // 表名称
    var labelTableName = $("#labelTableName");
    // 表描述
    var labelDescription = $("#labelDescription");

    // 页面中部
    var centerPanel = $("#centerPanel");
    // 数据显示表格
    var dataTable = $("#dataTable");

    var paramSchemaName = null;
    var paramTableName = null;
    // 数据库表结构数据
    var tableSchema = null;

    /**
     * 页面初始化方法
     */
    this.init = function () {

        // 设置数据显示表格
        //noinspection JSUnusedLocalSymbols
        dataTable.datagrid({
            idField: 'columnName',
            fit: true,
            fitColumns: false,
            striped: true,
            rownumbers: true,
            singleSelect: true,
            nowrap: true,
            pagination: false,
            loadMsg: "正在加载，请稍候...",
            //toolbar: "#",
            pageSize: 30,
            pageList: [10, 20, 30, 50, 100, 150]
        });

        // 页面数据初始化
        _this.dataBind();
        // 界面事件绑定方法
        _this.eventBind();
    };

    //noinspection JSUnusedGlobalSymbols
    /**
     * 页面数据初始化
     */
    this.dataBind = function () {
        paramSchemaName = _this.getUrlParam("schemaName");
        paramTableName = _this.getUrlParam("tableName");
        if (paramSchemaName == null || paramTableName == null) {
            $.messager.alert("提示", "请求参数“schemaName”、“tableName”不存在！", "error");
            return;
        }
        _this.getTableSchema(paramSchemaName, paramTableName, dataTable);

        if(tableSchema != null){
            labelSchemaName.text(tableSchema.schemaName);
            labelTableName.text(tableSchema.tableName);
            labelDescription.text(tableSchema.description);
        }
    };

    //noinspection JSUnusedGlobalSymbols
    /**
     * 界面事件绑定方法
     */
    this.eventBind = function () {
    };

    // ---------------------------------------------------------------------------------------------------------

    // 获取 数据库表详细信息
    this.getTableSchema = function (schemaName, tableName, dataTable) {
        var param = {};
        param.schemaName = schemaName;
        param.tableName = tableName;
        $.ajax({
            type: "POST",
            //dataType: "JSON",
            url: getTableSchemaURL,
            data: param,
            async: false,
            success: function (data) {
                if (data.success == true) {
                    tableSchema = data.result;
                } else {
                    $.messager.alert("提示", data.failMessage, "error");
                }
            }
        });
        dataTable.datagrid("loadData", tableSchema.columnList);
    };

    // 获取URL参数
    this.getUrlParam = function (name) {
        //构造一个含有目标参数的正则表达式对象
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        //匹配目标参数
        var r = window.location.search.substr(1).match(reg);
        if (r != null) {
            return decodeURIComponent(r[2]);
        }
        return null;
    };

    // 格式化
    //noinspection JSUnusedLocalSymbols,JSUnusedGlobalSymbols
    this.ordinalPositionFormatter = function (value, rowData, rowIndex) {
        //<span class="tree-icon tree-folder icon-table"></span>
        var image = _this.getColumnImage(rowData.dataType);
        var result = '<span class="' + image + '" style="display: inline-block;width: 16px;height: 16px;"></span>';
        return result + " " + value;
    };

    // 根据数据库列类型返回 对应的图标
    this.getColumnImage = function (dataType){
        if(!dataType || dataType == null || dataType == ""){
            return "icon-unknown";
        }
        dataType = dataType.toLowerCase();
        if (dataType.indexOf("char") >= 0) {
            return "icon-string";
        }
        if (dataType.indexOf("int") >= 0
            || dataType.indexOf("number") >= 0
            || dataType.indexOf("float") >= 0
            || dataType.indexOf("double") >= 0
            || dataType.indexOf("decimal") >= 0) {
            return "icon-number";
        }
        if (dataType.indexOf("date") >= 0
            || dataType.indexOf("time") >= 0
            || dataType.indexOf("year") >= 0) {
            return "icon-datetime";
        }
        if (dataType.indexOf("bool") >= 0) {
            return "icon-boolean";
        }
        if (dataType.indexOf("clob") >= 0) {
            return "icon-binary";
        }
        if (dataType.indexOf("lob") >= 0) {
            return "icon-lob";
        }
        if (dataType.indexOf("text") >= 0) {
            //return "icon-text";
            return "icon-lob";
        }
        return "icon-unknown";
    };

    // 格式化
    //noinspection JSUnusedGlobalSymbols,JSUnusedLocalSymbols
    this.booleanFormatter = function (value, rowData, rowIndex) {
        if (value == true) {
            return '<input type="checkbox" onclick="return false;" checked="checked"/>';
        }
        if (value == false) {
            return "";
        }
        return value;
    }
};

/**
 * 页面Js对象
 */
var pageJsObject = null;
/**
 * 页面Js执行入口
 */
$(document).ready(function () {
    if (typeof(globalPath) == "undefined") {
        alert("系统全局路径对象未定义(globalPath)");
    } else {
        pageJsObject = new pageJs(globalPath);
        pageJsObject.init();
    }
});