package com.oversession.dao.doma;

/** */
@org.springframework.stereotype.Repository()
@javax.annotation.Generated(value = { "Doma", "2.55.0" }, date = "2026-04-04T10:44:19.469+0900")
@org.seasar.doma.DaoImplementation
public class ChatQueryDomaDaoImpl implements com.oversession.dao.doma.ChatQueryDomaDao, org.seasar.doma.jdbc.ConfigProvider {

    static {
        org.seasar.doma.internal.Artifact.validateVersion("2.55.0");
    }

    private static final java.lang.reflect.Method __method0 = org.seasar.doma.internal.jdbc.dao.DaoImplSupport.getDeclaredMethod(com.oversession.dao.doma.ChatQueryDomaDao.class, "selectChatsByUserId", java.lang.String.class);

    private static final java.lang.reflect.Method __method1 = org.seasar.doma.internal.jdbc.dao.DaoImplSupport.getDeclaredMethod(com.oversession.dao.doma.ChatQueryDomaDao.class, "selectByChatId", java.lang.String.class);

    private final org.seasar.doma.internal.jdbc.dao.DaoImplSupport __support;

    /**
     * @param config the config
     */
    @org.springframework.beans.factory.annotation.Autowired()
    public ChatQueryDomaDaoImpl(org.seasar.doma.jdbc.Config config) {
        __support = new org.seasar.doma.internal.jdbc.dao.DaoImplSupport(config);
    }

    @Override
    public org.seasar.doma.jdbc.Config getConfig() {
        return __support.getConfig();
    }

    @Override
    public java.util.List<com.oversession.model.ChatSummary> selectChatsByUserId(java.lang.String userId) {
        __support.entering("com.oversession.dao.doma.ChatQueryDomaDaoImpl", "selectChatsByUserId", userId);
        try {
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = __support.getQueryImplementors().createSqlFileSelectQuery(__method0);
            __query.setMethod(__method0);
            __query.setConfig(__support.getConfig());
            __query.setSqlFilePath("META-INF/com/oversession/dao/doma/ChatQueryDomaDao/selectChatsByUserId.sql");
            __query.setEntityType(com.oversession.model._ChatSummary.getSingletonInternal());
            __query.addParameter("userId", java.lang.String.class, userId);
            __query.setCallerClassName("com.oversession.dao.doma.ChatQueryDomaDaoImpl");
            __query.setCallerMethodName("selectChatsByUserId");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<java.util.List<com.oversession.model.ChatSummary>> __command = __support.getCommandImplementors().createSelectCommand(__method0, __query, new org.seasar.doma.internal.jdbc.command.EntityResultListHandler<com.oversession.model.ChatSummary>(com.oversession.model._ChatSummary.getSingletonInternal()));
            java.util.List<com.oversession.model.ChatSummary> __result = __command.execute();
            __query.complete();
            __support.exiting("com.oversession.dao.doma.ChatQueryDomaDaoImpl", "selectChatsByUserId", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            __support.throwing("com.oversession.dao.doma.ChatQueryDomaDaoImpl", "selectChatsByUserId", __e);
            throw __e;
        }
    }

    @Override
    public java.util.Optional<com.oversession.model.ChatSummary> selectByChatId(java.lang.String chatId) {
        __support.entering("com.oversession.dao.doma.ChatQueryDomaDaoImpl", "selectByChatId", chatId);
        try {
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = __support.getQueryImplementors().createSqlFileSelectQuery(__method1);
            __query.setMethod(__method1);
            __query.setConfig(__support.getConfig());
            __query.setSqlFilePath("META-INF/com/oversession/dao/doma/ChatQueryDomaDao/selectByChatId.sql");
            __query.setEntityType(com.oversession.model._ChatSummary.getSingletonInternal());
            __query.addParameter("chatId", java.lang.String.class, chatId);
            __query.setCallerClassName("com.oversession.dao.doma.ChatQueryDomaDaoImpl");
            __query.setCallerMethodName("selectByChatId");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<java.util.Optional<com.oversession.model.ChatSummary>> __command = __support.getCommandImplementors().createSelectCommand(__method1, __query, new org.seasar.doma.internal.jdbc.command.OptionalEntitySingleResultHandler<com.oversession.model.ChatSummary>(com.oversession.model._ChatSummary.getSingletonInternal()));
            java.util.Optional<com.oversession.model.ChatSummary> __result = __command.execute();
            __query.complete();
            __support.exiting("com.oversession.dao.doma.ChatQueryDomaDaoImpl", "selectByChatId", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            __support.throwing("com.oversession.dao.doma.ChatQueryDomaDaoImpl", "selectByChatId", __e);
            throw __e;
        }
    }

}
