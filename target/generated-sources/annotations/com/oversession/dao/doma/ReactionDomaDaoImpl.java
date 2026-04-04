package com.oversession.dao.doma;

/** */
@org.springframework.stereotype.Repository()
@javax.annotation.Generated(value = { "Doma", "2.55.0" }, date = "2026-04-04T13:19:38.719+0900")
@org.seasar.doma.DaoImplementation
public class ReactionDomaDaoImpl implements com.oversession.dao.doma.ReactionDomaDao, org.seasar.doma.jdbc.ConfigProvider {

    static {
        org.seasar.doma.internal.Artifact.validateVersion("2.55.0");
    }

    private static final java.lang.reflect.Method __method0 = org.seasar.doma.internal.jdbc.dao.DaoImplSupport.getDeclaredMethod(com.oversession.dao.doma.ReactionDomaDao.class, "selectByMessageId", java.lang.String.class);

    private static final java.lang.reflect.Method __method1 = org.seasar.doma.internal.jdbc.dao.DaoImplSupport.getDeclaredMethod(com.oversession.dao.doma.ReactionDomaDao.class, "insertIgnoreConflict", com.oversession.model.Reaction.class);

    private static final java.lang.reflect.Method __method2 = org.seasar.doma.internal.jdbc.dao.DaoImplSupport.getDeclaredMethod(com.oversession.dao.doma.ReactionDomaDao.class, "delete", com.oversession.model.Reaction.class);

    private final org.seasar.doma.internal.jdbc.dao.DaoImplSupport __support;

    /**
     * @param config the config
     */
    @org.springframework.beans.factory.annotation.Autowired()
    public ReactionDomaDaoImpl(org.seasar.doma.jdbc.Config config) {
        __support = new org.seasar.doma.internal.jdbc.dao.DaoImplSupport(config);
    }

    @Override
    public org.seasar.doma.jdbc.Config getConfig() {
        return __support.getConfig();
    }

    @Override
    public java.util.List<com.oversession.model.Reaction> selectByMessageId(java.lang.String messageId) {
        __support.entering("com.oversession.dao.doma.ReactionDomaDaoImpl", "selectByMessageId", messageId);
        try {
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = __support.getQueryImplementors().createSqlFileSelectQuery(__method0);
            __query.setMethod(__method0);
            __query.setConfig(__support.getConfig());
            __query.setSqlFilePath("META-INF/com/oversession/dao/doma/ReactionDomaDao/selectByMessageId.sql");
            __query.setEntityType(com.oversession.model._Reaction.getSingletonInternal());
            __query.addParameter("messageId", java.lang.String.class, messageId);
            __query.setCallerClassName("com.oversession.dao.doma.ReactionDomaDaoImpl");
            __query.setCallerMethodName("selectByMessageId");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<java.util.List<com.oversession.model.Reaction>> __command = __support.getCommandImplementors().createSelectCommand(__method0, __query, new org.seasar.doma.internal.jdbc.command.EntityResultListHandler<com.oversession.model.Reaction>(com.oversession.model._Reaction.getSingletonInternal()));
            java.util.List<com.oversession.model.Reaction> __result = __command.execute();
            __query.complete();
            __support.exiting("com.oversession.dao.doma.ReactionDomaDaoImpl", "selectByMessageId", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            __support.throwing("com.oversession.dao.doma.ReactionDomaDaoImpl", "selectByMessageId", __e);
            throw __e;
        }
    }

    @Override
    public int insertIgnoreConflict(com.oversession.model.Reaction reaction) {
        __support.entering("com.oversession.dao.doma.ReactionDomaDaoImpl", "insertIgnoreConflict", reaction);
        try {
            if (reaction == null) {
                throw new org.seasar.doma.DomaNullPointerException("reaction");
            }
            org.seasar.doma.jdbc.query.SqlFileInsertQuery __query = __support.getQueryImplementors().createSqlFileInsertQuery(__method1);
            __query.setMethod(__method1);
            __query.setConfig(__support.getConfig());
            __query.setSqlFilePath("META-INF/com/oversession/dao/doma/ReactionDomaDao/insertIgnoreConflict.sql");
            __query.addParameter("reaction", com.oversession.model.Reaction.class, reaction);
            __query.setCallerClassName("com.oversession.dao.doma.ReactionDomaDaoImpl");
            __query.setCallerMethodName("insertIgnoreConflict");
            __query.setQueryTimeout(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.setEntityAndEntityType("reaction", reaction, com.oversession.model._Reaction.getSingletonInternal());
            __query.setNullExcluded(false);
            __query.setIncludedPropertyNames();
            __query.setExcludedPropertyNames();
            __query.prepare();
            org.seasar.doma.jdbc.command.InsertCommand __command = __support.getCommandImplementors().createInsertCommand(__method1, __query);
            int __result = __command.execute();
            __query.complete();
            __support.exiting("com.oversession.dao.doma.ReactionDomaDaoImpl", "insertIgnoreConflict", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            __support.throwing("com.oversession.dao.doma.ReactionDomaDaoImpl", "insertIgnoreConflict", __e);
            throw __e;
        }
    }

    @Override
    public int delete(com.oversession.model.Reaction reaction) {
        __support.entering("com.oversession.dao.doma.ReactionDomaDaoImpl", "delete", reaction);
        try {
            if (reaction == null) {
                throw new org.seasar.doma.DomaNullPointerException("reaction");
            }
            org.seasar.doma.jdbc.query.AutoDeleteQuery<com.oversession.model.Reaction> __query = __support.getQueryImplementors().createAutoDeleteQuery(__method2, com.oversession.model._Reaction.getSingletonInternal());
            __query.setMethod(__method2);
            __query.setConfig(__support.getConfig());
            __query.setEntity(reaction);
            __query.setCallerClassName("com.oversession.dao.doma.ReactionDomaDaoImpl");
            __query.setCallerMethodName("delete");
            __query.setQueryTimeout(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.setVersionIgnored(false);
            __query.setOptimisticLockExceptionSuppressed(false);
            __query.prepare();
            org.seasar.doma.jdbc.command.DeleteCommand __command = __support.getCommandImplementors().createDeleteCommand(__method2, __query);
            int __result = __command.execute();
            __query.complete();
            __support.exiting("com.oversession.dao.doma.ReactionDomaDaoImpl", "delete", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            __support.throwing("com.oversession.dao.doma.ReactionDomaDaoImpl", "delete", __e);
            throw __e;
        }
    }

}
