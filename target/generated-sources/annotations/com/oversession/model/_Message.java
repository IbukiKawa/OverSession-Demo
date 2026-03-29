package com.oversession.model;

/** */
@javax.annotation.Generated(value = { "Doma", "2.55.0" }, date = "2026-03-29T22:11:23.295+0900")
@org.seasar.doma.EntityTypeImplementation
public final class _Message extends org.seasar.doma.jdbc.entity.AbstractEntityType<com.oversession.model.Message> {

    static {
        org.seasar.doma.internal.Artifact.validateVersion("2.55.0");
    }

    private static final _Message __singleton = new _Message();

    private final org.seasar.doma.jdbc.entity.NamingType __namingType = org.seasar.doma.jdbc.entity.NamingType.SNAKE_LOWER_CASE;

    private final java.util.function.Supplier<org.seasar.doma.jdbc.entity.NullEntityListener<com.oversession.model.Message>> __listenerSupplier;

    private final boolean __immutable;

    private final String __catalogName;

    private final String __schemaName;

    private final String __tableName;

    private final boolean __isQuoteRequired;

    private final String __name;

    private final java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<com.oversession.model.Message, ?>> __idPropertyTypes;

    private final java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<com.oversession.model.Message, ?>> __entityPropertyTypes;

    private final java.util.Map<String, org.seasar.doma.jdbc.entity.EntityPropertyType<com.oversession.model.Message, ?>> __entityPropertyTypeMap;

    @SuppressWarnings("unused")
    private final java.util.Map<String, org.seasar.doma.jdbc.entity.EmbeddedPropertyType<com.oversession.model.Message, ?>> __embeddedPropertyTypeMap;

    private _Message() {
        __listenerSupplier = org.seasar.doma.internal.jdbc.entity.NullEntityListenerSuppliers.of();
        __immutable = false;
        __name = "Message";
        __catalogName = "";
        __schemaName = "";
        __tableName = "messages";
        __isQuoteRequired = false;
        java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<com.oversession.model.Message, ?>> __idList = new java.util.ArrayList<>();
        java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<com.oversession.model.Message, ?>> __list = new java.util.ArrayList<>(6);
        java.util.Map<String, org.seasar.doma.jdbc.entity.EntityPropertyType<com.oversession.model.Message, ?>> __map = new java.util.LinkedHashMap<>(6);
        java.util.Map<String, org.seasar.doma.jdbc.entity.EmbeddedPropertyType<com.oversession.model.Message, ?>> __embeddedMap = new java.util.LinkedHashMap<>(6);
        initializeMaps(__map, __embeddedMap);
        initializeIdList(__map, __idList);
        initializeList(__map, __list);
        __idPropertyTypes = java.util.Collections.unmodifiableList(__idList);
        __entityPropertyTypes = java.util.Collections.unmodifiableList(__list);
        __entityPropertyTypeMap = java.util.Collections.unmodifiableMap(__map);
        __embeddedPropertyTypeMap = java.util.Collections.unmodifiableMap(__embeddedMap);
    }

    private void initializeMaps(java.util.Map<String, org.seasar.doma.jdbc.entity.EntityPropertyType<com.oversession.model.Message, ?>> __map, java.util.Map<String, org.seasar.doma.jdbc.entity.EmbeddedPropertyType<com.oversession.model.Message, ?>> __embeddedMap) {
        __map.put("messageId", new org.seasar.doma.jdbc.entity.AssignedIdPropertyType<com.oversession.model.Message, java.lang.String, java.lang.String>(com.oversession.model.Message.class, org.seasar.doma.internal.jdbc.scalar.BasicScalarSuppliers.ofString(), "messageId", "", __namingType, false));
        __map.put("chatId", new org.seasar.doma.jdbc.entity.DefaultPropertyType<com.oversession.model.Message, java.lang.String, java.lang.String>(com.oversession.model.Message.class, org.seasar.doma.internal.jdbc.scalar.BasicScalarSuppliers.ofString(), "chatId", "", __namingType, true, true, false));
        __map.put("senderUserId", new org.seasar.doma.jdbc.entity.DefaultPropertyType<com.oversession.model.Message, java.lang.String, java.lang.String>(com.oversession.model.Message.class, org.seasar.doma.internal.jdbc.scalar.BasicScalarSuppliers.ofString(), "senderUserId", "", __namingType, true, true, false));
        __map.put("text", new org.seasar.doma.jdbc.entity.DefaultPropertyType<com.oversession.model.Message, java.lang.String, java.lang.String>(com.oversession.model.Message.class, org.seasar.doma.internal.jdbc.scalar.BasicScalarSuppliers.ofString(), "text", "", __namingType, true, true, false));
        __map.put("sentAt", new org.seasar.doma.jdbc.entity.DefaultPropertyType<com.oversession.model.Message, java.time.LocalDateTime, java.time.LocalDateTime>(com.oversession.model.Message.class, org.seasar.doma.internal.jdbc.scalar.BasicScalarSuppliers.ofLocalDateTime(), "sentAt", "", __namingType, true, true, false));
        __map.put("readAt", new org.seasar.doma.jdbc.entity.DefaultPropertyType<com.oversession.model.Message, java.time.LocalDateTime, java.time.LocalDateTime>(com.oversession.model.Message.class, org.seasar.doma.internal.jdbc.scalar.BasicScalarSuppliers.ofLocalDateTime(), "readAt", "", __namingType, true, true, false));
    }

    private void initializeIdList(java.util.Map<String, org.seasar.doma.jdbc.entity.EntityPropertyType<com.oversession.model.Message, ?>> __map, java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<com.oversession.model.Message, ?>> __idList) {
        __idList.add(__map.get("messageId"));
    }

    private void initializeList(java.util.Map<String, org.seasar.doma.jdbc.entity.EntityPropertyType<com.oversession.model.Message, ?>> __map, java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<com.oversession.model.Message, ?>> __list) {
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
    public void preInsert(com.oversession.model.Message entity, org.seasar.doma.jdbc.entity.PreInsertContext<com.oversession.model.Message> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.preInsert(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void preUpdate(com.oversession.model.Message entity, org.seasar.doma.jdbc.entity.PreUpdateContext<com.oversession.model.Message> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.preUpdate(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void preDelete(com.oversession.model.Message entity, org.seasar.doma.jdbc.entity.PreDeleteContext<com.oversession.model.Message> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.preDelete(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void postInsert(com.oversession.model.Message entity, org.seasar.doma.jdbc.entity.PostInsertContext<com.oversession.model.Message> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.postInsert(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void postUpdate(com.oversession.model.Message entity, org.seasar.doma.jdbc.entity.PostUpdateContext<com.oversession.model.Message> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.postUpdate(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void postDelete(com.oversession.model.Message entity, org.seasar.doma.jdbc.entity.PostDeleteContext<com.oversession.model.Message> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.postDelete(entity, context);
    }

    @Override
    public java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<com.oversession.model.Message, ?>> getEntityPropertyTypes() {
        return __entityPropertyTypes;
    }

    @Override
    public org.seasar.doma.jdbc.entity.EntityPropertyType<com.oversession.model.Message, ?> getEntityPropertyType(String __name) {
        return __entityPropertyTypeMap.get(__name);
    }

    @Override
    public java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<com.oversession.model.Message, ?>> getIdPropertyTypes() {
        return __idPropertyTypes;
    }

    @SuppressWarnings("unchecked")
    @Override
    public org.seasar.doma.jdbc.entity.GeneratedIdPropertyType<com.oversession.model.Message, ?, ?> getGeneratedIdPropertyType() {
        return (org.seasar.doma.jdbc.entity.GeneratedIdPropertyType<com.oversession.model.Message, ?, ?>)__entityPropertyTypeMap.get("null");
    }

    @SuppressWarnings("unchecked")
    @Override
    public org.seasar.doma.jdbc.entity.VersionPropertyType<com.oversession.model.Message, ?, ?> getVersionPropertyType() {
        return (org.seasar.doma.jdbc.entity.VersionPropertyType<com.oversession.model.Message, ?, ?>)__entityPropertyTypeMap.get("null");
    }

    @SuppressWarnings("unchecked")
    @Override
    public org.seasar.doma.jdbc.entity.TenantIdPropertyType<com.oversession.model.Message, ?, ?> getTenantIdPropertyType() {
        return (org.seasar.doma.jdbc.entity.TenantIdPropertyType<com.oversession.model.Message, ?, ?>)__entityPropertyTypeMap.get("null");
    }

    @Override
    public com.oversession.model.Message newEntity(java.util.Map<String, org.seasar.doma.jdbc.entity.Property<com.oversession.model.Message, ?>> __args) {
        com.oversession.model.Message entity = new com.oversession.model.Message();
        if (__args.get("messageId") != null) __args.get("messageId").save(entity);
        if (__args.get("chatId") != null) __args.get("chatId").save(entity);
        if (__args.get("senderUserId") != null) __args.get("senderUserId").save(entity);
        if (__args.get("text") != null) __args.get("text").save(entity);
        if (__args.get("sentAt") != null) __args.get("sentAt").save(entity);
        if (__args.get("readAt") != null) __args.get("readAt").save(entity);
        return entity;
    }

    @Override
    public Class<com.oversession.model.Message> getEntityClass() {
        return com.oversession.model.Message.class;
    }

    @Override
    public com.oversession.model.Message getOriginalStates(com.oversession.model.Message __entity) {
        return null;
    }

    @Override
    public void saveCurrentStates(com.oversession.model.Message __entity) {
    }

    /**
     * @return the singleton
     */
    public static _Message getSingletonInternal() {
        return __singleton;
    }

    /**
     * @return the new instance
     */
    public static _Message newInstance() {
        return new _Message();
    }

}
