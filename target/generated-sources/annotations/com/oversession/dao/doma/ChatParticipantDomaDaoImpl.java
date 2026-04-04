package com.oversession.dao.doma;

/** */
@org.springframework.stereotype.Repository()
@javax.annotation.Generated(value = { "Doma", "2.55.0" }, date = "2026-04-04T10:44:19.473+0900")
@org.seasar.doma.DaoImplementation
public class ChatParticipantDomaDaoImpl implements com.oversession.dao.doma.ChatParticipantDomaDao, org.seasar.doma.jdbc.ConfigProvider {

    static {
        org.seasar.doma.internal.Artifact.validateVersion("2.55.0");
    }

    private static final java.lang.reflect.Method __method0 = org.seasar.doma.internal.jdbc.dao.DaoImplSupport.getDeclaredMethod(com.oversession.dao.doma.ChatParticipantDomaDao.class, "findCommonChatIds", java.lang.String.class, java.lang.String.class);

    private static final java.lang.reflect.Method __method1 = org.seasar.doma.internal.jdbc.dao.DaoImplSupport.getDeclaredMethod(com.oversession.dao.doma.ChatParticipantDomaDao.class, "insert", com.oversession.model.ChatParticipant.class);

    private final org.seasar.doma.internal.jdbc.dao.DaoImplSupport __support;

    /**
     * @param config the config
     */
    @org.springframework.beans.factory.annotation.Autowired()
    public ChatParticipantDomaDaoImpl(org.seasar.doma.jdbc.Config config) {
        __support = new org.seasar.doma.internal.jdbc.dao.DaoImplSupport(config);
    }

    @Override
    public org.seasar.doma.jdbc.Config getConfig() {
        return __support.getConfig();
    }

    @Override
    public java.util.List<java.lang.String> findCommonChatIds(java.lang.String userId1, java.lang.String userId2) {
        __support.entering("com.oversession.dao.doma.ChatParticipantDomaDaoImpl", "findCommonChatIds", userId1, userId2);
        try {
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = __support.getQueryImplementors().createSqlFileSelectQuery(__method0);
            __query.setMethod(__method0);
            __query.setConfig(__support.getConfig());
            __query.setSqlFilePath("META-INF/com/oversession/dao/doma/ChatParticipantDomaDao/findCommonChatIds.sql");
            __query.addParameter("userId1", java.lang.String.class, userId1);
            __query.addParameter("userId2", java.lang.String.class, userId2);
            __query.setCallerClassName("com.oversession.dao.doma.ChatParticipantDomaDaoImpl");
            __query.setCallerMethodName("findCommonChatIds");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<java.util.List<java.lang.String>> __command = __support.getCommandImplementors().createSelectCommand(__method0, __query, new org.seasar.doma.internal.jdbc.command.BasicResultListHandler<java.lang.String>(org.seasar.doma.internal.wrapper.WrapperSuppliers.ofString()));
            java.util.List<java.lang.String> __result = __command.execute();
            __query.complete();
            __support.exiting("com.oversession.dao.doma.ChatParticipantDomaDaoImpl", "findCommonChatIds", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            __support.throwing("com.oversession.dao.doma.ChatParticipantDomaDaoImpl", "findCommonChatIds", __e);
            throw __e;
        }
    }

    @Override
    public int insert(com.oversession.model.ChatParticipant chatParticipant) {
        __support.entering("com.oversession.dao.doma.ChatParticipantDomaDaoImpl", "insert", chatParticipant);
        try {
            if (chatParticipant == null) {
                throw new org.seasar.doma.DomaNullPointerException("chatParticipant");
            }
            org.seasar.doma.jdbc.query.AutoInsertQuery<com.oversession.model.ChatParticipant> __query = __support.getQueryImplementors().createAutoInsertQuery(__method1, com.oversession.model._ChatParticipant.getSingletonInternal());
            __query.setMethod(__method1);
            __query.setConfig(__support.getConfig());
            __query.setEntity(chatParticipant);
            __query.setCallerClassName("com.oversession.dao.doma.ChatParticipantDomaDaoImpl");
            __query.setCallerMethodName("insert");
            __query.setQueryTimeout(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.setNullExcluded(false);
            __query.setIncludedPropertyNames();
            __query.setExcludedPropertyNames();
            __query.prepare();
            org.seasar.doma.jdbc.command.InsertCommand __command = __support.getCommandImplementors().createInsertCommand(__method1, __query);
            int __result = __command.execute();
            __query.complete();
            __support.exiting("com.oversession.dao.doma.ChatParticipantDomaDaoImpl", "insert", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            __support.throwing("com.oversession.dao.doma.ChatParticipantDomaDaoImpl", "insert", __e);
            throw __e;
        }
    }

}
