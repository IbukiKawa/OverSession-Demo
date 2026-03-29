package com.oversession.model;

/** */
@javax.annotation.Generated(value = { "Doma", "2.55.0" }, date = "2026-03-29T22:11:23.299+0900")
@org.seasar.doma.EntityTypeImplementation
public final class _User extends org.seasar.doma.jdbc.entity.AbstractEntityType<com.oversession.model.User> {

    static {
        org.seasar.doma.internal.Artifact.validateVersion("2.55.0");
    }

    private static final _User __singleton = new _User();

    private final org.seasar.doma.jdbc.entity.NamingType __namingType = org.seasar.doma.jdbc.entity.NamingType.SNAKE_LOWER_CASE;

    private final java.util.function.Supplier<org.seasar.doma.jdbc.entity.NullEntityListener<com.oversession.model.User>> __listenerSupplier;

    private final boolean __immutable;

    private final String __catalogName;

    private final String __schemaName;

    private final String __tableName;

    private final boolean __isQuoteRequired;

    private final String __name;

    private final java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<com.oversession.model.User, ?>> __idPropertyTypes;

    private final java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<com.oversession.model.User, ?>> __entityPropertyTypes;

    private final java.util.Map<String, org.seasar.doma.jdbc.entity.EntityPropertyType<com.oversession.model.User, ?>> __entityPropertyTypeMap;

    @SuppressWarnings("unused")
    private final java.util.Map<String, org.seasar.doma.jdbc.entity.EmbeddedPropertyType<com.oversession.model.User, ?>> __embeddedPropertyTypeMap;

    private _User() {
        __listenerSupplier = org.seasar.doma.internal.jdbc.entity.NullEntityListenerSuppliers.of();
        __immutable = false;
        __name = "User";
        __catalogName = "";
        __schemaName = "";
        __tableName = "users";
        __isQuoteRequired = false;
        java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<com.oversession.model.User, ?>> __idList = new java.util.ArrayList<>();
        java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<com.oversession.model.User, ?>> __list = new java.util.ArrayList<>(13);
        java.util.Map<String, org.seasar.doma.jdbc.entity.EntityPropertyType<com.oversession.model.User, ?>> __map = new java.util.LinkedHashMap<>(13);
        java.util.Map<String, org.seasar.doma.jdbc.entity.EmbeddedPropertyType<com.oversession.model.User, ?>> __embeddedMap = new java.util.LinkedHashMap<>(13);
        initializeMaps(__map, __embeddedMap);
        initializeIdList(__map, __idList);
        initializeList(__map, __list);
        __idPropertyTypes = java.util.Collections.unmodifiableList(__idList);
        __entityPropertyTypes = java.util.Collections.unmodifiableList(__list);
        __entityPropertyTypeMap = java.util.Collections.unmodifiableMap(__map);
        __embeddedPropertyTypeMap = java.util.Collections.unmodifiableMap(__embeddedMap);
    }

    private void initializeMaps(java.util.Map<String, org.seasar.doma.jdbc.entity.EntityPropertyType<com.oversession.model.User, ?>> __map, java.util.Map<String, org.seasar.doma.jdbc.entity.EmbeddedPropertyType<com.oversession.model.User, ?>> __embeddedMap) {
        __map.put("userId", new org.seasar.doma.jdbc.entity.AssignedIdPropertyType<com.oversession.model.User, java.lang.String, java.lang.String>(com.oversession.model.User.class, org.seasar.doma.internal.jdbc.scalar.BasicScalarSuppliers.ofString(), "userId", "", __namingType, false));
        __map.put("userName", new org.seasar.doma.jdbc.entity.DefaultPropertyType<com.oversession.model.User, java.lang.String, java.lang.String>(com.oversession.model.User.class, org.seasar.doma.internal.jdbc.scalar.BasicScalarSuppliers.ofString(), "userName", "", __namingType, true, true, false));
        __map.put("primaryHeadOfficeName", new org.seasar.doma.jdbc.entity.DefaultPropertyType<com.oversession.model.User, java.lang.String, java.lang.String>(com.oversession.model.User.class, org.seasar.doma.internal.jdbc.scalar.BasicScalarSuppliers.ofString(), "primaryHeadOfficeName", "", __namingType, true, true, false));
        __map.put("secondaryHeadOfficeName", new org.seasar.doma.jdbc.entity.DefaultPropertyType<com.oversession.model.User, java.lang.String, java.lang.String>(com.oversession.model.User.class, org.seasar.doma.internal.jdbc.scalar.BasicScalarSuppliers.ofString(), "secondaryHeadOfficeName", "", __namingType, true, true, false));
        __map.put("departmentName", new org.seasar.doma.jdbc.entity.DefaultPropertyType<com.oversession.model.User, java.lang.String, java.lang.String>(com.oversession.model.User.class, org.seasar.doma.internal.jdbc.scalar.BasicScalarSuppliers.ofString(), "departmentName", "", __namingType, true, true, false));
        __map.put("officeId", new org.seasar.doma.jdbc.entity.DefaultPropertyType<com.oversession.model.User, java.lang.Integer, java.lang.Integer>(com.oversession.model.User.class, org.seasar.doma.internal.jdbc.scalar.BasicScalarSuppliers.ofInteger(), "officeId", "", __namingType, true, true, false));
        __map.put("floor", new org.seasar.doma.jdbc.entity.DefaultPropertyType<com.oversession.model.User, java.lang.Integer, java.lang.Integer>(com.oversession.model.User.class, org.seasar.doma.internal.jdbc.scalar.BasicScalarSuppliers.ofInteger(), "floor", "", __namingType, true, true, false));
        __map.put("gender", new org.seasar.doma.jdbc.entity.DefaultPropertyType<com.oversession.model.User, java.lang.String, java.lang.String>(com.oversession.model.User.class, org.seasar.doma.internal.jdbc.scalar.BasicScalarSuppliers.ofString(), "gender", "", __namingType, true, true, false));
        __map.put("affiliationYear", new org.seasar.doma.jdbc.entity.DefaultPropertyType<com.oversession.model.User, java.lang.Integer, java.lang.Integer>(com.oversession.model.User.class, org.seasar.doma.internal.jdbc.scalar.BasicScalarSuppliers.ofInteger(), "affiliationYear", "", __namingType, true, true, false));
        __map.put("workingStatus", new org.seasar.doma.jdbc.entity.DefaultPropertyType<com.oversession.model.User, java.lang.String, java.lang.String>(com.oversession.model.User.class, org.seasar.doma.internal.jdbc.scalar.BasicScalarSuppliers.ofString(), "workingStatus", "", __namingType, true, true, false));
        __map.put("matchingUserId", new org.seasar.doma.jdbc.entity.DefaultPropertyType<com.oversession.model.User, java.lang.String, java.lang.String>(com.oversession.model.User.class, org.seasar.doma.internal.jdbc.scalar.BasicScalarSuppliers.ofString(), "matchingUserId", "", __namingType, true, true, false));
        __map.put("pictureName", new org.seasar.doma.jdbc.entity.DefaultPropertyType<com.oversession.model.User, java.lang.String, java.lang.String>(com.oversession.model.User.class, org.seasar.doma.internal.jdbc.scalar.BasicScalarSuppliers.ofString(), "pictureName", "", __namingType, true, true, false));
        __map.put("deleted", new org.seasar.doma.jdbc.entity.DefaultPropertyType<com.oversession.model.User, java.lang.Boolean, java.lang.Boolean>(com.oversession.model.User.class, org.seasar.doma.internal.jdbc.scalar.BasicScalarSuppliers.ofPrimitiveBoolean(), "deleted", "", __namingType, true, true, false));
    }

    private void initializeIdList(java.util.Map<String, org.seasar.doma.jdbc.entity.EntityPropertyType<com.oversession.model.User, ?>> __map, java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<com.oversession.model.User, ?>> __idList) {
        __idList.add(__map.get("userId"));
    }

    private void initializeList(java.util.Map<String, org.seasar.doma.jdbc.entity.EntityPropertyType<com.oversession.model.User, ?>> __map, java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<com.oversession.model.User, ?>> __list) {
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
    public void preInsert(com.oversession.model.User entity, org.seasar.doma.jdbc.entity.PreInsertContext<com.oversession.model.User> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.preInsert(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void preUpdate(com.oversession.model.User entity, org.seasar.doma.jdbc.entity.PreUpdateContext<com.oversession.model.User> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.preUpdate(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void preDelete(com.oversession.model.User entity, org.seasar.doma.jdbc.entity.PreDeleteContext<com.oversession.model.User> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.preDelete(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void postInsert(com.oversession.model.User entity, org.seasar.doma.jdbc.entity.PostInsertContext<com.oversession.model.User> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.postInsert(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void postUpdate(com.oversession.model.User entity, org.seasar.doma.jdbc.entity.PostUpdateContext<com.oversession.model.User> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.postUpdate(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void postDelete(com.oversession.model.User entity, org.seasar.doma.jdbc.entity.PostDeleteContext<com.oversession.model.User> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.postDelete(entity, context);
    }

    @Override
    public java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<com.oversession.model.User, ?>> getEntityPropertyTypes() {
        return __entityPropertyTypes;
    }

    @Override
    public org.seasar.doma.jdbc.entity.EntityPropertyType<com.oversession.model.User, ?> getEntityPropertyType(String __name) {
        return __entityPropertyTypeMap.get(__name);
    }

    @Override
    public java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<com.oversession.model.User, ?>> getIdPropertyTypes() {
        return __idPropertyTypes;
    }

    @SuppressWarnings("unchecked")
    @Override
    public org.seasar.doma.jdbc.entity.GeneratedIdPropertyType<com.oversession.model.User, ?, ?> getGeneratedIdPropertyType() {
        return (org.seasar.doma.jdbc.entity.GeneratedIdPropertyType<com.oversession.model.User, ?, ?>)__entityPropertyTypeMap.get("null");
    }

    @SuppressWarnings("unchecked")
    @Override
    public org.seasar.doma.jdbc.entity.VersionPropertyType<com.oversession.model.User, ?, ?> getVersionPropertyType() {
        return (org.seasar.doma.jdbc.entity.VersionPropertyType<com.oversession.model.User, ?, ?>)__entityPropertyTypeMap.get("null");
    }

    @SuppressWarnings("unchecked")
    @Override
    public org.seasar.doma.jdbc.entity.TenantIdPropertyType<com.oversession.model.User, ?, ?> getTenantIdPropertyType() {
        return (org.seasar.doma.jdbc.entity.TenantIdPropertyType<com.oversession.model.User, ?, ?>)__entityPropertyTypeMap.get("null");
    }

    @Override
    public com.oversession.model.User newEntity(java.util.Map<String, org.seasar.doma.jdbc.entity.Property<com.oversession.model.User, ?>> __args) {
        com.oversession.model.User entity = new com.oversession.model.User();
        if (__args.get("userId") != null) __args.get("userId").save(entity);
        if (__args.get("userName") != null) __args.get("userName").save(entity);
        if (__args.get("primaryHeadOfficeName") != null) __args.get("primaryHeadOfficeName").save(entity);
        if (__args.get("secondaryHeadOfficeName") != null) __args.get("secondaryHeadOfficeName").save(entity);
        if (__args.get("departmentName") != null) __args.get("departmentName").save(entity);
        if (__args.get("officeId") != null) __args.get("officeId").save(entity);
        if (__args.get("floor") != null) __args.get("floor").save(entity);
        if (__args.get("gender") != null) __args.get("gender").save(entity);
        if (__args.get("affiliationYear") != null) __args.get("affiliationYear").save(entity);
        if (__args.get("workingStatus") != null) __args.get("workingStatus").save(entity);
        if (__args.get("matchingUserId") != null) __args.get("matchingUserId").save(entity);
        if (__args.get("pictureName") != null) __args.get("pictureName").save(entity);
        if (__args.get("deleted") != null) __args.get("deleted").save(entity);
        return entity;
    }

    @Override
    public Class<com.oversession.model.User> getEntityClass() {
        return com.oversession.model.User.class;
    }

    @Override
    public com.oversession.model.User getOriginalStates(com.oversession.model.User __entity) {
        return null;
    }

    @Override
    public void saveCurrentStates(com.oversession.model.User __entity) {
    }

    /**
     * @return the singleton
     */
    public static _User getSingletonInternal() {
        return __singleton;
    }

    /**
     * @return the new instance
     */
    public static _User newInstance() {
        return new _User();
    }

}
