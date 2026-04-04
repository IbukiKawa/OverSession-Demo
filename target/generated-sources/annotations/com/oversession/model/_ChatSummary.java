package com.oversession.model;

/** */
@javax.annotation.Generated(value = { "Doma", "2.55.0" }, date = "2026-04-04T10:44:19.327+0900")
@org.seasar.doma.EntityTypeImplementation
public final class _ChatSummary extends org.seasar.doma.jdbc.entity.AbstractEntityType<com.oversession.model.ChatSummary> {

    static {
        org.seasar.doma.internal.Artifact.validateVersion("2.55.0");
    }

    private static final _ChatSummary __singleton = new _ChatSummary();

    private final org.seasar.doma.jdbc.entity.NamingType __namingType = org.seasar.doma.jdbc.entity.NamingType.SNAKE_LOWER_CASE;

    private final java.util.function.Supplier<org.seasar.doma.jdbc.entity.NullEntityListener<com.oversession.model.ChatSummary>> __listenerSupplier;

    private final boolean __immutable;

    private final String __catalogName;

    private final String __schemaName;

    private final String __tableName;

    private final boolean __isQuoteRequired;

    private final String __name;

    private final java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<com.oversession.model.ChatSummary, ?>> __idPropertyTypes;

    private final java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<com.oversession.model.ChatSummary, ?>> __entityPropertyTypes;

    private final java.util.Map<String, org.seasar.doma.jdbc.entity.EntityPropertyType<com.oversession.model.ChatSummary, ?>> __entityPropertyTypeMap;

    @SuppressWarnings("unused")
    private final java.util.Map<String, org.seasar.doma.jdbc.entity.EmbeddedPropertyType<com.oversession.model.ChatSummary, ?>> __embeddedPropertyTypeMap;

    private _ChatSummary() {
        __listenerSupplier = org.seasar.doma.internal.jdbc.entity.NullEntityListenerSuppliers.of();
        __immutable = false;
        __name = "ChatSummary";
        __catalogName = "";
        __schemaName = "";
        __tableName = "";
        __isQuoteRequired = false;
        java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<com.oversession.model.ChatSummary, ?>> __idList = new java.util.ArrayList<>();
        java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<com.oversession.model.ChatSummary, ?>> __list = new java.util.ArrayList<>(7);
        java.util.Map<String, org.seasar.doma.jdbc.entity.EntityPropertyType<com.oversession.model.ChatSummary, ?>> __map = new java.util.LinkedHashMap<>(7);
        java.util.Map<String, org.seasar.doma.jdbc.entity.EmbeddedPropertyType<com.oversession.model.ChatSummary, ?>> __embeddedMap = new java.util.LinkedHashMap<>(7);
        initializeMaps(__map, __embeddedMap);
        initializeIdList(__map, __idList);
        initializeList(__map, __list);
        __idPropertyTypes = java.util.Collections.unmodifiableList(__idList);
        __entityPropertyTypes = java.util.Collections.unmodifiableList(__list);
        __entityPropertyTypeMap = java.util.Collections.unmodifiableMap(__map);
        __embeddedPropertyTypeMap = java.util.Collections.unmodifiableMap(__embeddedMap);
    }

    private void initializeMaps(java.util.Map<String, org.seasar.doma.jdbc.entity.EntityPropertyType<com.oversession.model.ChatSummary, ?>> __map, java.util.Map<String, org.seasar.doma.jdbc.entity.EmbeddedPropertyType<com.oversession.model.ChatSummary, ?>> __embeddedMap) {
        __map.put("chatId", new org.seasar.doma.jdbc.entity.AssignedIdPropertyType<com.oversession.model.ChatSummary, java.lang.String, java.lang.String>(com.oversession.model.ChatSummary.class, org.seasar.doma.internal.jdbc.scalar.BasicScalarSuppliers.ofString(), "chatId", "", __namingType, false));
        __map.put("partnerUserId", new org.seasar.doma.jdbc.entity.DefaultPropertyType<com.oversession.model.ChatSummary, java.lang.String, java.lang.String>(com.oversession.model.ChatSummary.class, org.seasar.doma.internal.jdbc.scalar.BasicScalarSuppliers.ofString(), "partnerUserId", "", __namingType, true, true, false));
        __map.put("partnerUserName", new org.seasar.doma.jdbc.entity.DefaultPropertyType<com.oversession.model.ChatSummary, java.lang.String, java.lang.String>(com.oversession.model.ChatSummary.class, org.seasar.doma.internal.jdbc.scalar.BasicScalarSuppliers.ofString(), "partnerUserName", "", __namingType, true, true, false));
        __map.put("partnerPictureName", new org.seasar.doma.jdbc.entity.DefaultPropertyType<com.oversession.model.ChatSummary, java.lang.String, java.lang.String>(com.oversession.model.ChatSummary.class, org.seasar.doma.internal.jdbc.scalar.BasicScalarSuppliers.ofString(), "partnerPictureName", "", __namingType, true, true, false));
        __map.put("lastMessage", new org.seasar.doma.jdbc.entity.DefaultPropertyType<com.oversession.model.ChatSummary, java.lang.String, java.lang.String>(com.oversession.model.ChatSummary.class, org.seasar.doma.internal.jdbc.scalar.BasicScalarSuppliers.ofString(), "lastMessage", "", __namingType, true, true, false));
        __map.put("lastMessageAt", new org.seasar.doma.jdbc.entity.DefaultPropertyType<com.oversession.model.ChatSummary, java.time.LocalDateTime, java.time.LocalDateTime>(com.oversession.model.ChatSummary.class, org.seasar.doma.internal.jdbc.scalar.BasicScalarSuppliers.ofLocalDateTime(), "lastMessageAt", "", __namingType, true, true, false));
        __map.put("unreadCount", new org.seasar.doma.jdbc.entity.DefaultPropertyType<com.oversession.model.ChatSummary, java.lang.Integer, java.lang.Integer>(com.oversession.model.ChatSummary.class, org.seasar.doma.internal.jdbc.scalar.BasicScalarSuppliers.ofInteger(), "unreadCount", "", __namingType, true, true, false));
    }

    private void initializeIdList(java.util.Map<String, org.seasar.doma.jdbc.entity.EntityPropertyType<com.oversession.model.ChatSummary, ?>> __map, java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<com.oversession.model.ChatSummary, ?>> __idList) {
        __idList.add(__map.get("chatId"));
    }

    private void initializeList(java.util.Map<String, org.seasar.doma.jdbc.entity.EntityPropertyType<com.oversession.model.ChatSummary, ?>> __map, java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<com.oversession.model.ChatSummary, ?>> __list) {
        __list.addAll(__map.values());
    }

    @Override
    public org.seasar.doma.jdbc.entity.NamingType getNamingType() {
        return __namingType;
    }

    @Override
    public boolean isImmutable() {
        return __immutable;
    }

    @Override
    public String getName() {
        return __name;
    }

    @Override
    public String getCatalogName() {
        return __catalogName;
    }

    @Override
    public String getSchemaName() {
        return __schemaName;
    }

    @Override
    @Deprecated
    public String getTableName() {
        return getTableName(org.seasar.doma.internal.jdbc.entity.TableNames.namingFunction);
    }

    @Override
    public String getTableName(java.util.function.BiFunction<org.seasar.doma.jdbc.entity.NamingType, String, String> namingFunction) {
        if (__tableName.isEmpty()) {
            return namingFunction.apply(__namingType, __name);
        }
        return __tableName;
    }

    @Override
    public boolean isQuoteRequired() {
        return __isQuoteRequired;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void preInsert(com.oversession.model.ChatSummary entity, org.seasar.doma.jdbc.entity.PreInsertContext<com.oversession.model.ChatSummary> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.preInsert(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void preUpdate(com.oversession.model.ChatSummary entity, org.seasar.doma.jdbc.entity.PreUpdateContext<com.oversession.model.ChatSummary> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.preUpdate(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void preDelete(com.oversession.model.ChatSummary entity, org.seasar.doma.jdbc.entity.PreDeleteContext<com.oversession.model.ChatSummary> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.preDelete(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void postInsert(com.oversession.model.ChatSummary entity, org.seasar.doma.jdbc.entity.PostInsertContext<com.oversession.model.ChatSummary> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.postInsert(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void postUpdate(com.oversession.model.ChatSummary entity, org.seasar.doma.jdbc.entity.PostUpdateContext<com.oversession.model.ChatSummary> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.postUpdate(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void postDelete(com.oversession.model.ChatSummary entity, org.seasar.doma.jdbc.entity.PostDeleteContext<com.oversession.model.ChatSummary> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.postDelete(entity, context);
    }

    @Override
    public java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<com.oversession.model.ChatSummary, ?>> getEntityPropertyTypes() {
        return __entityPropertyTypes;
    }

    @Override
    public org.seasar.doma.jdbc.entity.EntityPropertyType<com.oversession.model.ChatSummary, ?> getEntityPropertyType(String __name) {
        return __entityPropertyTypeMap.get(__name);
    }

    @Override
    public java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<com.oversession.model.ChatSummary, ?>> getIdPropertyTypes() {
        return __idPropertyTypes;
    }

    @SuppressWarnings("unchecked")
    @Override
    public org.seasar.doma.jdbc.entity.GeneratedIdPropertyType<com.oversession.model.ChatSummary, ?, ?> getGeneratedIdPropertyType() {
        return (org.seasar.doma.jdbc.entity.GeneratedIdPropertyType<com.oversession.model.ChatSummary, ?, ?>)__entityPropertyTypeMap.get("null");
    }

    @SuppressWarnings("unchecked")
    @Override
    public org.seasar.doma.jdbc.entity.VersionPropertyType<com.oversession.model.ChatSummary, ?, ?> getVersionPropertyType() {
        return (org.seasar.doma.jdbc.entity.VersionPropertyType<com.oversession.model.ChatSummary, ?, ?>)__entityPropertyTypeMap.get("null");
    }

    @SuppressWarnings("unchecked")
    @Override
    public org.seasar.doma.jdbc.entity.TenantIdPropertyType<com.oversession.model.ChatSummary, ?, ?> getTenantIdPropertyType() {
        return (org.seasar.doma.jdbc.entity.TenantIdPropertyType<com.oversession.model.ChatSummary, ?, ?>)__entityPropertyTypeMap.get("null");
    }

    @Override
    public com.oversession.model.ChatSummary newEntity(java.util.Map<String, org.seasar.doma.jdbc.entity.Property<com.oversession.model.ChatSummary, ?>> __args) {
        com.oversession.model.ChatSummary entity = new com.oversession.model.ChatSummary();
        if (__args.get("chatId") != null) __args.get("chatId").save(entity);
        if (__args.get("partnerUserId") != null) __args.get("partnerUserId").save(entity);
        if (__args.get("partnerUserName") != null) __args.get("partnerUserName").save(entity);
        if (__args.get("partnerPictureName") != null) __args.get("partnerPictureName").save(entity);
        if (__args.get("lastMessage") != null) __args.get("lastMessage").save(entity);
        if (__args.get("lastMessageAt") != null) __args.get("lastMessageAt").save(entity);
        if (__args.get("unreadCount") != null) __args.get("unreadCount").save(entity);
        return entity;
    }

    @Override
    public Class<com.oversession.model.ChatSummary> getEntityClass() {
        return com.oversession.model.ChatSummary.class;
    }

    @Override
    public com.oversession.model.ChatSummary getOriginalStates(com.oversession.model.ChatSummary __entity) {
        return null;
    }

    @Override
    public void saveCurrentStates(com.oversession.model.ChatSummary __entity) {
    }

    /**
     * @return the singleton
     */
    public static _ChatSummary getSingletonInternal() {
        return __singleton;
    }

    /**
     * @return the new instance
     */
    public static _ChatSummary newInstance() {
        return new _ChatSummary();
    }

}
