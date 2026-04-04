package com.oversession.model;

/** */
@javax.annotation.Generated(value = { "Doma", "2.55.0" }, date = "2026-04-04T10:44:19.341+0900")
@org.seasar.doma.EntityTypeImplementation
public final class _ChatParticipant extends org.seasar.doma.jdbc.entity.AbstractEntityType<com.oversession.model.ChatParticipant> {

    static {
        org.seasar.doma.internal.Artifact.validateVersion("2.55.0");
    }

    private static final _ChatParticipant __singleton = new _ChatParticipant();

    private final org.seasar.doma.jdbc.entity.NamingType __namingType = org.seasar.doma.jdbc.entity.NamingType.SNAKE_LOWER_CASE;

    private final java.util.function.Supplier<org.seasar.doma.jdbc.entity.NullEntityListener<com.oversession.model.ChatParticipant>> __listenerSupplier;

    private final boolean __immutable;

    private final String __catalogName;

    private final String __schemaName;

    private final String __tableName;

    private final boolean __isQuoteRequired;

    private final String __name;

    private final java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<com.oversession.model.ChatParticipant, ?>> __idPropertyTypes;

    private final java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<com.oversession.model.ChatParticipant, ?>> __entityPropertyTypes;

    private final java.util.Map<String, org.seasar.doma.jdbc.entity.EntityPropertyType<com.oversession.model.ChatParticipant, ?>> __entityPropertyTypeMap;

    @SuppressWarnings("unused")
    private final java.util.Map<String, org.seasar.doma.jdbc.entity.EmbeddedPropertyType<com.oversession.model.ChatParticipant, ?>> __embeddedPropertyTypeMap;

    private _ChatParticipant() {
        __listenerSupplier = org.seasar.doma.internal.jdbc.entity.NullEntityListenerSuppliers.of();
        __immutable = false;
        __name = "ChatParticipant";
        __catalogName = "";
        __schemaName = "";
        __tableName = "chat_participants";
        __isQuoteRequired = false;
        java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<com.oversession.model.ChatParticipant, ?>> __idList = new java.util.ArrayList<>();
        java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<com.oversession.model.ChatParticipant, ?>> __list = new java.util.ArrayList<>(2);
        java.util.Map<String, org.seasar.doma.jdbc.entity.EntityPropertyType<com.oversession.model.ChatParticipant, ?>> __map = new java.util.LinkedHashMap<>(2);
        java.util.Map<String, org.seasar.doma.jdbc.entity.EmbeddedPropertyType<com.oversession.model.ChatParticipant, ?>> __embeddedMap = new java.util.LinkedHashMap<>(2);
        initializeMaps(__map, __embeddedMap);
        initializeIdList(__map, __idList);
        initializeList(__map, __list);
        __idPropertyTypes = java.util.Collections.unmodifiableList(__idList);
        __entityPropertyTypes = java.util.Collections.unmodifiableList(__list);
        __entityPropertyTypeMap = java.util.Collections.unmodifiableMap(__map);
        __embeddedPropertyTypeMap = java.util.Collections.unmodifiableMap(__embeddedMap);
    }

    private void initializeMaps(java.util.Map<String, org.seasar.doma.jdbc.entity.EntityPropertyType<com.oversession.model.ChatParticipant, ?>> __map, java.util.Map<String, org.seasar.doma.jdbc.entity.EmbeddedPropertyType<com.oversession.model.ChatParticipant, ?>> __embeddedMap) {
        __map.put("chatId", new org.seasar.doma.jdbc.entity.AssignedIdPropertyType<com.oversession.model.ChatParticipant, java.lang.String, java.lang.String>(com.oversession.model.ChatParticipant.class, org.seasar.doma.internal.jdbc.scalar.BasicScalarSuppliers.ofString(), "chatId", "", __namingType, false));
        __map.put("userId", new org.seasar.doma.jdbc.entity.AssignedIdPropertyType<com.oversession.model.ChatParticipant, java.lang.String, java.lang.String>(com.oversession.model.ChatParticipant.class, org.seasar.doma.internal.jdbc.scalar.BasicScalarSuppliers.ofString(), "userId", "", __namingType, false));
    }

    private void initializeIdList(java.util.Map<String, org.seasar.doma.jdbc.entity.EntityPropertyType<com.oversession.model.ChatParticipant, ?>> __map, java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<com.oversession.model.ChatParticipant, ?>> __idList) {
        __idList.add(__map.get("chatId"));
        __idList.add(__map.get("userId"));
    }

    private void initializeList(java.util.Map<String, org.seasar.doma.jdbc.entity.EntityPropertyType<com.oversession.model.ChatParticipant, ?>> __map, java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<com.oversession.model.ChatParticipant, ?>> __list) {
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
    public void preInsert(com.oversession.model.ChatParticipant entity, org.seasar.doma.jdbc.entity.PreInsertContext<com.oversession.model.ChatParticipant> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.preInsert(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void preUpdate(com.oversession.model.ChatParticipant entity, org.seasar.doma.jdbc.entity.PreUpdateContext<com.oversession.model.ChatParticipant> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.preUpdate(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void preDelete(com.oversession.model.ChatParticipant entity, org.seasar.doma.jdbc.entity.PreDeleteContext<com.oversession.model.ChatParticipant> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.preDelete(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void postInsert(com.oversession.model.ChatParticipant entity, org.seasar.doma.jdbc.entity.PostInsertContext<com.oversession.model.ChatParticipant> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.postInsert(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void postUpdate(com.oversession.model.ChatParticipant entity, org.seasar.doma.jdbc.entity.PostUpdateContext<com.oversession.model.ChatParticipant> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.postUpdate(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void postDelete(com.oversession.model.ChatParticipant entity, org.seasar.doma.jdbc.entity.PostDeleteContext<com.oversession.model.ChatParticipant> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.postDelete(entity, context);
    }

    @Override
    public java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<com.oversession.model.ChatParticipant, ?>> getEntityPropertyTypes() {
        return __entityPropertyTypes;
    }

    @Override
    public org.seasar.doma.jdbc.entity.EntityPropertyType<com.oversession.model.ChatParticipant, ?> getEntityPropertyType(String __name) {
        return __entityPropertyTypeMap.get(__name);
    }

    @Override
    public java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<com.oversession.model.ChatParticipant, ?>> getIdPropertyTypes() {
        return __idPropertyTypes;
    }

    @SuppressWarnings("unchecked")
    @Override
    public org.seasar.doma.jdbc.entity.GeneratedIdPropertyType<com.oversession.model.ChatParticipant, ?, ?> getGeneratedIdPropertyType() {
        return (org.seasar.doma.jdbc.entity.GeneratedIdPropertyType<com.oversession.model.ChatParticipant, ?, ?>)__entityPropertyTypeMap.get("null");
    }

    @SuppressWarnings("unchecked")
    @Override
    public org.seasar.doma.jdbc.entity.VersionPropertyType<com.oversession.model.ChatParticipant, ?, ?> getVersionPropertyType() {
        return (org.seasar.doma.jdbc.entity.VersionPropertyType<com.oversession.model.ChatParticipant, ?, ?>)__entityPropertyTypeMap.get("null");
    }

    @SuppressWarnings("unchecked")
    @Override
    public org.seasar.doma.jdbc.entity.TenantIdPropertyType<com.oversession.model.ChatParticipant, ?, ?> getTenantIdPropertyType() {
        return (org.seasar.doma.jdbc.entity.TenantIdPropertyType<com.oversession.model.ChatParticipant, ?, ?>)__entityPropertyTypeMap.get("null");
    }

    @Override
    public com.oversession.model.ChatParticipant newEntity(java.util.Map<String, org.seasar.doma.jdbc.entity.Property<com.oversession.model.ChatParticipant, ?>> __args) {
        com.oversession.model.ChatParticipant entity = new com.oversession.model.ChatParticipant();
        if (__args.get("chatId") != null) __args.get("chatId").save(entity);
        if (__args.get("userId") != null) __args.get("userId").save(entity);
        return entity;
    }

    @Override
    public Class<com.oversession.model.ChatParticipant> getEntityClass() {
        return com.oversession.model.ChatParticipant.class;
    }

    @Override
    public com.oversession.model.ChatParticipant getOriginalStates(com.oversession.model.ChatParticipant __entity) {
        return null;
    }

    @Override
    public void saveCurrentStates(com.oversession.model.ChatParticipant __entity) {
    }

    /**
     * @return the singleton
     */
    public static _ChatParticipant getSingletonInternal() {
        return __singleton;
    }

    /**
     * @return the new instance
     */
    public static _ChatParticipant newInstance() {
        return new _ChatParticipant();
    }

}
