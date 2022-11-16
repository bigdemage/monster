package com.monster.kafka.vo;

/**
 * @ClassName SyncConstant
 * @Deacription 同步相关常量
 * @Author wrx
 * @Date 2022/2/24/024 17:20
 * @Version 1.0
 **/
public class SyncConstant {

    public static class Topic{
        //同步修改客户信息（未实名用户）
        public final static String syncAlterCustomerInfo="uasc_sync_altercustomerinfo";
        //同步修改客户信息
        public final static String syncAlterClientInfo="uasc_sync_alterClientInfo";
        //同步修改密码信息
        public final static String syncModifyPassword="uasc_sync_modifypassword";
        //同步重置密码信息
        public final static String syncResetPassword="uasc_sync_resetPassword";
        //同步修改客户基本资料
        public final static String syncAlterClientBaseInfo="uasc_sync_alterClientBaseInfo";
        //同步修改税收居民身份
        public final static String syncAlterTaxResidentStatus="uasc_sync_alterTaxResidentStatus";
        //同步新增税收居民身份
        public final static String syncInsertTaxResidentStatus="uasc_sync_insertTaxResidentStatus";
        //同步个人修改资料审核
        public final static String syncUpdateAuditInfoPersonal="uasc_sync_updateAuditInfoPersonal";
        //同步审核信息
        public final static String syncSaveAudit="uasc_sync_saveAudit";

    }





}
