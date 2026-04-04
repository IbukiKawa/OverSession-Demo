package com.oversession.dao.doma;

/** */
@org.springframework.stereotype.Repository()
@javax.annotation.Generated(value = { "Doma", "2.55.0" }, date = "2026-04-04T13:19:38.681+0900")
@org.seasar.doma.DaoImplementation
public class MessageDomaDaoImpl implements com.oversession.dao.doma.MessageDomaDao, org.seasar.doma.jdbc.ConfigProvider {

    static {
        org.seasar.doma.internal.Artifact.validateVersion("2.55.0");
    }

    private static final java.lang.reflect.Method __method0 = org.seasar.doma.internal.jdbc.dao.DaoImplSupport.getDeclaredMethod(com.oversession.dao.doma.MessageDomaDao.class, "selectByChatId", java.lang.String.class);

    private static final java.lang.reflect.Method __method1 = org.seasar.doma.internal.jdbc.dao.DaoImplSupport.getDeclaredMethod(com.oversession.dao.doma.MessageDomaDao.class, "selectByChatIdWithCursor", java.lang.String.class, java.lang.String.class, int.class);

    private static final java.lang.reflect.Method __method2 = org.seasar.doma.internal.jdbc.dao.DaoImplSupport.getDeclaredMethod(com.oversession.dao.doma.MessageDomaDao.class, "selectByMessageId", java.lang.String.class);

    private static final java.lang.reflect.Method __method3 = org.seasar.doma.internal.jdbc.dao.DaoImplSupport.getDeclaredMethod(com.oversession.dao.doma.MessageDomaDao.class, "insert", com.oversession.model.Message.class);

    private static final java.lang.reflect.Method __method4 = org.seasar.doma.internal.jdbc.dao.DaoImplSupport.getDeclaredMethod(com.oversession.dao.doma.MessageDomaDao.class, "update", com.oversession.model.Message.class);

    private final org.seasar.doma.internal.jdbc.dao.DaoImplSupport __support;

    /**
     * @param config the config
     */
    @org.springframework.beans.factory.annotation.Autowired()
    public MessageDomaDaoImpl(org.seasar.doma.jdbc.Config config) {
        __support = new org.seasar.doma.internal.jdbc.dao.DaoImplSupport(config);
    }

    @Override
    public org.seasar.doma.jdbc.Config getConfig() {
        return __support.getConfig();
    }

    @Override
    public java.util.List<com.oversession.model.Message> selectByChatId(java.lang.String chatId) {
        __support.entering("com.oversession.dao.doma.MessageDomaDaoImpl", "selectByChatId", chatId);
        try {
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = __support.getQueryImplementors().createSqlFileSelectQuery(__method0);
            __query.setMethod(__method0);
            __query.setConfig(__support.getConfig());
            __query.setSqlFilePath("META-INF/com/oversession/dao/doma/MessageDomaDao/selectByChatId.sql");
            __query.setEntityType(com.oversession.model._Message.getSingletonInternal());
            __query.addParameter("chatId", java.lang.String.class, chatId);
            __query.setCallerClassName("com.oversession.dao.doma.MessageDomaDaoImpl");
            __query.setCallerMethodName("selectByChatId");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<java.util.List<com.oversession.model.Message>> __command = __support.getCommandImplementors().createSelectCommand(__method0, __query, new org.seasar.doma.internal.jdbc.command.EntityResultListHandler<com.oversession.model.Message>(com.oversession.model._Message.getSingletonInternal()));
            java.util.List<com.oversession.model.Message> __result = __command.execute();
            __query.complete();
            __support.exiting("com.oversession.dao.doma.MessageDomaDaoImpl", "selectByChatId", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            __support.throwing("com.oversession.dao.doma.MessageDomaDaoImpl", "selectByChatId", __e);
            throw __e;
        }
    }

    @Override
    public java.util.List<com.oversession.model.Message> selectByChatIdWithCursor(java.lang.String chatId, java.lang.String cursor, int limit) {
        __support.entering("com.oversession.dao.doma.MessageDomaDaoImpl", "selectByChatIdWithCursor", chatId, cursor, limit);
        try {
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = __support.getQueryImplementors().createSqlFileSelectQuery(__method1);
            __query.setMethod(__method1);
            __query.setConfig(__support.getConfig());
            __query.setSqlFilePath("META-INF/com/oversession/dao/doma/MessageDomaDao/selectByChatIdWithCursor.sql");
            __query.setEntityType(com.oversession.model._Message.getSingletonInternal());
            __query.addParameter("chatId", java.lang.String.class, chatId);
            __query.addParameter("cursor", java.lang.String.class, cursor);
            __query.addParameter("limit", int.class, limit);
            __query.setCallerClassName("com.oversession.dao.doma.MessageDomaDaoImpl");
            __query.setCallerMethodName("selectByChatIdWithCursor");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<java.util.List<com.oversession.model.Message>> __command = __support.getCommandImplementors().createSelectCommand(__method1, __query, new org.seasar.doma.internal.jdbc.command.EntityResultListHandler<com.oversession.model.Message>(com.oversession.model._Message.getSingletonInternal()));
            java.util.List<com.oversession.model.Message> __result = __command.execute();
            __query.complete();
            __support.exiting("com.oversession.dao.doma.MessageDomaDaoImpl", "selectByChatIdWithCursor", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            __support.throwing("com.oversession.dao.doma.MessageDomaDaoImpl", "selectByChatIdWithCursor", __e);
            throw __e;
        }
    }

    @Override
    public java.util.Optional<com.oversession.model.Message> selectByMessageId(java.lang.String messageId) {
        __support.entering("com.oversession.dao.doma.MessageDomaDaoImpl", "selectByMessageId", messageId);
        try {
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = __support.getQueryImplementors().createSqlFileSelectQuery(__method2);
            __query.setMethod(__method2);
            __query.setConfig(__support.getConfig());
            __query.setSqlFilePath("META-INF/com/oversession/dao/doma/MessageDomaDao/selectByMessageId.sql");
            __query.setEntityType(com.oversession.model._Message.getSingletonInternal());
            __query.addParameter("messageId", java.lang.String.class, messageId);
            __query.setCallerClassName("com.oversession.dao.doma.MessageDomaDaoImpl");
            __query.setCallerMethodName("selectByMessageId");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<java.util.Optional<com.oversession.model.Message>> __command = __support.getCommandImplementors().createSelectCommand(__method2, __query, new org.seasar.doma.internal.jdbc.command.OptionalEntitySingleResultHandler<com.oversession.model.Message>(com.oversession.model._Message.getSingletonInternal()));
            java.util.Optional<com.oversession.model.Message> __result = __command.execute();
            __query.complete();
            __support.exiting("com.oversession.dao.doma.MessageDomaDaoImpl", "selectByMessageId", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            __support.throwing("com.oversession.dao.doma.MessageDomaDaoImpl", "selectByMessageId", __e);
            throw __e;
        }
    }

    @Override
    public int insert(com.oversession.model.Message message) {
        __support.entering("com.oversession.dao.doma.MessageDomaDaoImpl", "insert", message);
        try {
            if (message == null) {
                throw new org.seasar.doma.DomaNullPointerException("message");
            }
            org.seasar.doma.jdbc.query.AutoInsertQuery<com.oversession.model.Message> __query = __support.getQueryImplementors().createAutoInsertQuery(__method3, com.oversession.model._Message.getSingletonInternal());
            __query.setMethod(__method3);
            __query.setConfig(__support.getConfig());
            __query.setEntity(message);
            __query.setCallerClassName("com.oversession.dao.doma.MessageDomaDaoImpl");
            __query.setCallerMethodName("insert");
            __query.setQueryTimeout(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.setNullExcluded(false);
            __query.setIncludedPropertyNames();
            __query.setExcludedPropertyNames();
            __query.prepare();
            org.seasar.doma.jdbc.command.InsertCommand __command = __support.getCommandImplementors().createInsertCommand(__method3, __query);
            int __result = __command.execute();
            __query.complete();
            __support.exiting("com.oversession.dao.doma.MessageDomaDaoImpl", "insert", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            __support.throwing("com.oversession.dao.doma.MessageDomaDaoImpl", "insert", __e);
            throw __e;
        }
    }

    @Override
    public int update(com.oversession.model.Message message) {
        __support.entering("com.oversession.dao.doma.MessageDomaDaoImpl", "update", message);
        try {
            if (message == null) {
                throw new org.seasar.doma.DomaNullPointerException("message");
            }
            org.seasar.doma.jdbc.query.AutoUpdateQuery<com.oversession.model.Message> __query = __support.getQueryImplementors().createAutoUpdateQuery(__method4, com.oversession.model._Message.getSingletonInternal());
            __query.setMethod(__method4);
            __query.setConfig(__support.getConfig());
            __query.setEntity(message);
            __query.setCallerClassName("com.oversession.dao.doma.MessageDomaDaoImpl");
            __query.setCallerMethodName("update");
            __query.setQueryTimeout(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.setNullExcluded(false);
            __query.setVersionIgnored(false);
            __query.setIncludedPropertyNames();
            __query.setExcludedPropertyNames();
            __query.setUnchangedPropertyIncluded(false);
            __query.setOptimisticLockExceptionSuppressed(false);
            __query.prepare();
            org.seasar.doma.jdbc.command.UpdateCommand __command = __support.getCommandImplementors().createUpdateCommand(__method4, __query);
            int __result = __command.execute();
            __query.complete();
            __support.exiting("com.oversession.dao.doma.MessageDomaDaoImpl", "update", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            __support.throwing("com.oversession.dao.doma.MessageDomaDaoImpl", "update", __e);
            throw __e;
        }
    }

}
