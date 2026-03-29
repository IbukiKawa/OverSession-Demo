package com.oversession.dao.doma;

/** */
@org.springframework.stereotype.Repository()
@javax.annotation.Generated(value = { "Doma", "2.55.0" }, date = "2026-03-29T22:11:23.362+0900")
@org.seasar.doma.DaoImplementation
public class UserDomaDaoImpl implements com.oversession.dao.doma.UserDomaDao, org.seasar.doma.jdbc.ConfigProvider {

    static {
        org.seasar.doma.internal.Artifact.validateVersion("2.55.0");
    }

    private static final java.lang.reflect.Method __method0 = org.seasar.doma.internal.jdbc.dao.DaoImplSupport.getDeclaredMethod(com.oversession.dao.doma.UserDomaDao.class, "selectAll");

    private static final java.lang.reflect.Method __method1 = org.seasar.doma.internal.jdbc.dao.DaoImplSupport.getDeclaredMethod(com.oversession.dao.doma.UserDomaDao.class, "selectByUserId", java.lang.String.class);

    private static final java.lang.reflect.Method __method2 = org.seasar.doma.internal.jdbc.dao.DaoImplSupport.getDeclaredMethod(com.oversession.dao.doma.UserDomaDao.class, "searchByKeyword", java.lang.String.class);

    private static final java.lang.reflect.Method __method3 = org.seasar.doma.internal.jdbc.dao.DaoImplSupport.getDeclaredMethod(com.oversession.dao.doma.UserDomaDao.class, "insert", com.oversession.model.User.class);

    private static final java.lang.reflect.Method __method4 = org.seasar.doma.internal.jdbc.dao.DaoImplSupport.getDeclaredMethod(com.oversession.dao.doma.UserDomaDao.class, "update", com.oversession.model.User.class);

    private final org.seasar.doma.internal.jdbc.dao.DaoImplSupport __support;

    /**
     * @param config the config
     */
    @org.springframework.beans.factory.annotation.Autowired()
    public UserDomaDaoImpl(org.seasar.doma.jdbc.Config config) {
        __support = new org.seasar.doma.internal.jdbc.dao.DaoImplSupport(config);
    }

    @Override
    public org.seasar.doma.jdbc.Config getConfig() {
        return __support.getConfig();
    }

    @Override
    public java.util.List<com.oversession.model.User> selectAll() {
        __support.entering("com.oversession.dao.doma.UserDomaDaoImpl", "selectAll");
        try {
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = __support.getQueryImplementors().createSqlFileSelectQuery(__method0);
            __query.setMethod(__method0);
            __query.setConfig(__support.getConfig());
            __query.setSqlFilePath("META-INF/com/oversession/dao/doma/UserDomaDao/selectAll.sql");
            __query.setEntityType(com.oversession.model._User.getSingletonInternal());
            __query.setCallerClassName("com.oversession.dao.doma.UserDomaDaoImpl");
            __query.setCallerMethodName("selectAll");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<java.util.List<com.oversession.model.User>> __command = __support.getCommandImplementors().createSelectCommand(__method0, __query, new org.seasar.doma.internal.jdbc.command.EntityResultListHandler<com.oversession.model.User>(com.oversession.model._User.getSingletonInternal()));
            java.util.List<com.oversession.model.User> __result = __command.execute();
            __query.complete();
            __support.exiting("com.oversession.dao.doma.UserDomaDaoImpl", "selectAll", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            __support.throwing("com.oversession.dao.doma.UserDomaDaoImpl", "selectAll", __e);
            throw __e;
        }
    }

    @Override
    public java.util.Optional<com.oversession.model.User> selectByUserId(java.lang.String userId) {
        __support.entering("com.oversession.dao.doma.UserDomaDaoImpl", "selectByUserId", userId);
        try {
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = __support.getQueryImplementors().createSqlFileSelectQuery(__method1);
            __query.setMethod(__method1);
            __query.setConfig(__support.getConfig());
            __query.setSqlFilePath("META-INF/com/oversession/dao/doma/UserDomaDao/selectByUserId.sql");
            __query.setEntityType(com.oversession.model._User.getSingletonInternal());
            __query.addParameter("userId", java.lang.String.class, userId);
            __query.setCallerClassName("com.oversession.dao.doma.UserDomaDaoImpl");
            __query.setCallerMethodName("selectByUserId");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<java.util.Optional<com.oversession.model.User>> __command = __support.getCommandImplementors().createSelectCommand(__method1, __query, new org.seasar.doma.internal.jdbc.command.OptionalEntitySingleResultHandler<com.oversession.model.User>(com.oversession.model._User.getSingletonInternal()));
            java.util.Optional<com.oversession.model.User> __result = __command.execute();
            __query.complete();
            __support.exiting("com.oversession.dao.doma.UserDomaDaoImpl", "selectByUserId", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            __support.throwing("com.oversession.dao.doma.UserDomaDaoImpl", "selectByUserId", __e);
            throw __e;
        }
    }

    @Override
    public java.util.List<com.oversession.model.User> searchByKeyword(java.lang.String keyword) {
        __support.entering("com.oversession.dao.doma.UserDomaDaoImpl", "searchByKeyword", keyword);
        try {
            org.seasar.doma.jdbc.query.SqlFileSelectQuery __query = __support.getQueryImplementors().createSqlFileSelectQuery(__method2);
            __query.setMethod(__method2);
            __query.setConfig(__support.getConfig());
            __query.setSqlFilePath("META-INF/com/oversession/dao/doma/UserDomaDao/searchByKeyword.sql");
            __query.setEntityType(com.oversession.model._User.getSingletonInternal());
            __query.addParameter("keyword", java.lang.String.class, keyword);
            __query.setCallerClassName("com.oversession.dao.doma.UserDomaDaoImpl");
            __query.setCallerMethodName("searchByKeyword");
            __query.setResultEnsured(false);
            __query.setResultMappingEnsured(false);
            __query.setFetchType(org.seasar.doma.FetchType.LAZY);
            __query.setQueryTimeout(-1);
            __query.setMaxRows(-1);
            __query.setFetchSize(-1);
            __query.setSqlLogType(org.seasar.doma.jdbc.SqlLogType.FORMATTED);
            __query.prepare();
            org.seasar.doma.jdbc.command.SelectCommand<java.util.List<com.oversession.model.User>> __command = __support.getCommandImplementors().createSelectCommand(__method2, __query, new org.seasar.doma.internal.jdbc.command.EntityResultListHandler<com.oversession.model.User>(com.oversession.model._User.getSingletonInternal()));
            java.util.List<com.oversession.model.User> __result = __command.execute();
            __query.complete();
            __support.exiting("com.oversession.dao.doma.UserDomaDaoImpl", "searchByKeyword", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            __support.throwing("com.oversession.dao.doma.UserDomaDaoImpl", "searchByKeyword", __e);
            throw __e;
        }
    }

    @Override
    public int insert(com.oversession.model.User user) {
        __support.entering("com.oversession.dao.doma.UserDomaDaoImpl", "insert", user);
        try {
            if (user == null) {
                throw new org.seasar.doma.DomaNullPointerException("user");
            }
            org.seasar.doma.jdbc.query.AutoInsertQuery<com.oversession.model.User> __query = __support.getQueryImplementors().createAutoInsertQuery(__method3, com.oversession.model._User.getSingletonInternal());
            __query.setMethod(__method3);
            __query.setConfig(__support.getConfig());
            __query.setEntity(user);
            __query.setCallerClassName("com.oversession.dao.doma.UserDomaDaoImpl");
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
            __support.exiting("com.oversession.dao.doma.UserDomaDaoImpl", "insert", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            __support.throwing("com.oversession.dao.doma.UserDomaDaoImpl", "insert", __e);
            throw __e;
        }
    }

    @Override
    public int update(com.oversession.model.User user) {
        __support.entering("com.oversession.dao.doma.UserDomaDaoImpl", "update", user);
        try {
            if (user == null) {
                throw new org.seasar.doma.DomaNullPointerException("user");
            }
            org.seasar.doma.jdbc.query.AutoUpdateQuery<com.oversession.model.User> __query = __support.getQueryImplementors().createAutoUpdateQuery(__method4, com.oversession.model._User.getSingletonInternal());
            __query.setMethod(__method4);
            __query.setConfig(__support.getConfig());
            __query.setEntity(user);
            __query.setCallerClassName("com.oversession.dao.doma.UserDomaDaoImpl");
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
            __support.exiting("com.oversession.dao.doma.UserDomaDaoImpl", "update", __result);
            return __result;
        } catch (java.lang.RuntimeException __e) {
            __support.throwing("com.oversession.dao.doma.UserDomaDaoImpl", "update", __e);
            throw __e;
        }
    }

}
